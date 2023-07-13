using Backend;
using Backend.Database;
using Backend.Entities;
using Backend.Repository;
using Backend.Utils;
using Microsoft.AspNetCore.Authentication.JwtBearer;
using Microsoft.AspNetCore.Identity;
using Microsoft.EntityFrameworkCore;
using Microsoft.IdentityModel.Protocols.OpenIdConnect;
using Microsoft.IdentityModel.Tokens;

var km = new KeyManager();


var builder = WebApplication.CreateBuilder(args);

var issuer = builder.Configuration["Issuer"];

var producer = new JwtProducer(
    tokenExpirationTime: TimeSpan.FromDays(31),
    rsaKey: km.RsaKey,
    issuer: issuer
);

builder.Services.AddAuthentication()
       .AddJwtBearer(b =>
       {
           b.TokenValidationParameters = new TokenValidationParameters {
               ValidateActor = false,
               ValidateIssuer = true,
               ValidateAudience = false,
               ValidateIssuerSigningKey = true,
               RequireExpirationTime = true,
               ClockSkew = TimeSpan.FromDays(31),
               ValidIssuer = issuer
           };

           b.Events = new JwtBearerEvents {
               OnMessageReceived = (ctx) =>
               {
                   var token = ctx.Request.Query.TryGetValue("token", out var t)
                       ? t.FirstOrDefault()
                       : null;
                   ctx.Token = token;
                   return Task.CompletedTask;
               }
           };
           b.Configuration = new OpenIdConnectConfiguration { SigningKeys = { new RsaSecurityKey(km.RsaKey) } };
       });

builder.Services.AddDbContext<ApplicationDbContext>(
    b => b.UseSqlServer(builder.Configuration["ConnectionString"])
);

builder.Services.AddAuthentication();

builder.Services.AddSingleton<IJwtProducer>(producer);
builder.Services.AddSingleton<IPasswordHasher<User>, PasswordHasher>();
builder.Services.AddRepositories();

builder.Services.AddAutoMapper(profileAssemblyMarkerTypes: typeof(MappingProfile));

var config = new MediatRServiceConfiguration { Lifetime = ServiceLifetime.Scoped };
config.RegisterServicesFromAssembly(typeof(Program).Assembly);
builder.Services.AddMediatR(config);

builder.Services.AddControllers();

builder.Services.AddCors(options =>
{
    options.AddPolicy("myPolicy", b =>
    {
        b.AllowAnyMethod()
         .AllowAnyHeader()
         .AllowCredentials()
         .SetIsOriginAllowed(_ => true);
    });
});

var app = builder.Build();

app.UseCors("myPolicy");

app.UseMiddleware<DbTokenValidator>();

app.UseAuthentication();
app.UseAuthorization();

app.UseMiddleware<TransactionMiddleware<ApplicationDbContext>>();

app.MapControllers();

app.Run();