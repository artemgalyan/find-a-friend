using Microsoft.AspNetCore.Mvc;

namespace Backend.Utils;

public class ApiController : ControllerBase
{
    protected CancellationToken CancellationToken => HttpContext.RequestAborted;
    protected T Service<T>() where T : notnull => HttpContext.RequestServices.GetRequiredService<T>();
    protected readonly AuthData AuthData;

    public ApiController()
    {
        AuthData = new AuthData(this);
    }
}