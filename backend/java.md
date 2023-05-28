classDiagram
direction BT
class AddUserToShelterCommand
class AddUserToShelterHandler {
  + handle(AddUserToShelterCommand) Void
}
class Advert {
  + hashCode() int
  + toString() String
  + equals(Object) boolean
   String description
   Place place
   Date creationDate
   User owner
   String title
   AdvertType advertType
}
class AdvertController {
  + deleteAdvertById(int, int, String) HandleResult
  + updateAdvert(UpdateAdvertCommand, int, String) HandleResult
  + getById(int) HandleResult
  + createAdvert(CreateAdvertCommand) HandleResult
   HandleResult all
}
class AdvertDao {
<<Interface>>
  + deleteByUserId(int) boolean
  + deleteAdvertById(int) boolean
  + getUsersAdverts(int) List~Advert~
}
class AdvertModel {
  + of(Advert) AdvertModel
  + ownerId() int
  + description() String
  + title() String
  + id() int
  + creationDate() Date
  + place() Place
  + advertType() String
}
class AdvertType {
<<enumeration>>
  + fromValue(String) AdvertType
  + values() AdvertType[]
  + valueOf(String) AdvertType
   char value
}
class AdvertsSetup {
  + applyTo(ApplicationBuilder) void
}
class AnimalAdvert {
  + toString() String
  + equals(Object) boolean
  + hashCode() int
   String description
   String title
   List~Photo~ photos
   Sex sex
   boolean isCastrated
   Place place
   String animalType
   Date creationDate
   User owner
   Date birthdate
}
class AnimalAdvertDao {
<<Interface>>
  + getUsersAdverts(int) List~AnimalAdvert~
  + deleteByUserId(int) boolean
}
class AnimalAdvertModel {
  + of(AnimalAdvert, int, String) AnimalAdvertModel
}
class AnimalAdvertSetup {
  + applyTo(ApplicationBuilder) void
}
class AnimalAdvertsController {
  + getByUserId(int) HandleResult
  + create(CreateAnimalAdvertCommand) HandleResult
  + getById(int) HandleResult
  + getByShelterId(int) HandleResult
  + delete(int, int, String) HandleResult
  + getMy(int) HandleResult
   HandleResult all
}
class Application {
  + send(HttpServletRequest, HttpServletResponse) void
}
class ApplicationBuilder {
  + build() Application
  + services() GlobalServiceProvider
  + registerHandler(Class~RequestHandler~T, R~~, Class~R~) ApplicationBuilder
  + addAuthentication(JwtConsumer) ApplicationBuilder
  + addAuthentication() ApplicationBuilder
  + readKeys(String) ApplicationBuilder
  + mapController(Class~Controller~) ApplicationBuilder
  + addPipelineHandler(PipelineHandler) ApplicationBuilder
   GlobalServiceProvider serviceProvider
}
class AuthController {
  + signIn(String, String) HandleResult
}
class AuthSetup {
  + applyTo(ApplicationBuilder) void
}
class AuthUtils {
  + allowRoles(String, Role[]) boolean
}
class AuthenticationData {
  + getClaim(String) String
   JwtClaims claims
   boolean isValid
   String token
}
class CORSInterceptor {
  - isAllowedOrigin(String) boolean
  + doFilter(ServletRequest, ServletResponse, FilterChain) void
  + destroy() void
  + init(FilterConfig) void
}
class ClassEntry~T~
class ConnectionPool {
  ~ freeConnection(PooledConnection) void
  - createConnection() PooledConnection
  + init(int, int, int) void
  + destroy() void
  + close() void
   Connection connection
}
class ConstructedObject~T~
class Contacts {
  + hashCode() int
  + toString() String
  + equals(Object) boolean
   String surname
   String name
   String email
   String phoneNumber
}
class Controller {
  # notAuthorized(Object) HandleResult
  # ok() HandleResult
  # badRequest(Object) HandleResult
  # badRequest() HandleResult
  # ok(Object) HandleResult
  # notAuthorized() HandleResult
   ServiceProvider serviceProvider
   HttpServletRequest request
   HttpServletResponse response
}
class ControllerMapper {
  + mapController(Class~Controller~) List~EndpointInfo~
}
class ControllerMethodInvoker {
  - readFromBody(HttpServletRequest, Class~?~) Object
  + invoke(HttpServletRequest, HttpServletResponse, EndpointInfo, ScopedServiceProvider) HandleResult
  - tryParseObject(String, Class~?~) Object
  + asPipelineHandler() PipelineHandler
  - readFromQuery(String, HttpServletRequest, Class~?~) Object
}
class ControllerRoute {
  + route() String
}
class CreateAdvertCommand
class CreateAdvertHandler {
  + handle(CreateAdvertCommand) Boolean
}
class CreateAnimalAdvertCommand
class CreateAnimalAdvertsHandler {
  + handle(CreateAnimalAdvertCommand) Boolean
}
class CreatePlaceCommand
class CreatePlaceHandler {
  + handle(CreatePlaceCommand) Boolean
}
class CreateShelterCommand
class CreateShelterHandler {
  + handle(CreateShelterCommand) Boolean
}
class CreateUserCommand
class CreateUserHandler {
  + handle(CreateUserCommand) Boolean
}
class Dao~K, T~ {
<<Interface>>
  + delete(K) boolean
  + close(Statement) void
  + create(T) boolean
  + getEntityById(K) T
  + close(Connection) void
  + update(T) T
  + delete(T) boolean
   List~T~ all
}
class DaoException
class DaoSetup {
  + applyTo(ApplicationBuilder) void
}
class DbAdvertDao {
  + create(Advert) boolean
  + getUsersAdverts(int) List~Advert~
  + close() void
  + deleteAdvertById(int) boolean
  + getEntityById(Integer) Advert
  + update(Advert) Advert
  + delete(Integer) boolean
  + delete(Advert) boolean
  + deleteByUserId(int) boolean
   List~Advert~ all
}
class DbAnimalAdvertDao {
  + close() void
  + getEntityById(Integer) AnimalAdvert
  + update(AnimalAdvert) AnimalAdvert
  + getUsersAdverts(int) List~AnimalAdvert~
  + deleteByUserId(int) boolean
  + create(AnimalAdvert) boolean
  + delete(AnimalAdvert) boolean
  + delete(Integer) boolean
   List~AnimalAdvert~ all
}
class DbPhotoDao {
  + delete(Integer) boolean
  + update(Photo) Photo
  + close() void
  + create(Photo) boolean
  + deleteByAnimalAdvertId(int) boolean
  + create(List~Photo~) void
  + getAdvertPhotos(int) List~Photo~
  + getEntityById(Integer) Photo
  + delete(Photo) boolean
   List~Photo~ all
}
class DbPlaceDao {
  + getEntityById(Integer) Place
  + delete(Integer) boolean
  + update(Place) Place
  + close() void
  + delete(Place) boolean
  + create(Place) boolean
   List~Place~ all
}
class DbShelterDao {
  + delete(Integer) boolean
  + deleteByPlaceId(int) boolean
  + deleteShelterById(int) boolean
  + close() void
  + create(Shelter) boolean
  + delete(Shelter) boolean
  + update(Shelter) Shelter
  + getEntityById(Integer) Shelter
   List~Shelter~ all
}
class DbUserDao {
  + updateRole(int, Role) void
  + delete(User) boolean
  + deleteUserById(int) boolean
  + create(User) boolean
  + getEntityById(Integer) User
  + findByLogin(String) User
  + update(User) User
  + delete(Integer) boolean
  + close() void
   List~User~ all
}
class DbUserShelterDao {
  + getShelterId(int) int
  + close() void
  + remove(int, int) void
  + getUsersId(int) List~Integer~
  + removeAll(int) void
  + removeUser(int) void
  + add(int, int) void
   List~Pair~ all
}
class DbValidTokensDao {
  + addValidToken(String, int) void
  + isValidToken(String, int) boolean
  + close() void
  + invalidateTokens(int) void
}
class DefaultGlobalServiceProvider {
  - tryGetObject(Class~T~) Optional~T~
  + addService(Class~T~, ServiceType, Function~ServiceProvider, T~) DefaultGlobalServiceProvider
  + getRequiredService(Class~T~) T
  + hasService(Class~T~) boolean
  - findClassEntry(Class~?~) Optional~ClassEntry~?~~
   ScopedServiceProvider requestServiceProvider
}
class DeleteAdvertCommand {
  + tryCreate(Map~String, String~) Optional~DeleteAdvertCommand~
   int advertId
}
class DeleteAdvertHandler {
  + handle(DeleteAdvertCommand) Boolean
}
class DeletePlaceCommand {
  + tryCreate(Map~String, String~) Optional~DeletePlaceCommand~
   int placeId
}
class DeletePlaceHandler {
  + handle(DeletePlaceCommand) Boolean
}
class DeleteShelterCommand {
  + tryCreate(Map~String, String~) Optional~DeleteShelterCommand~
   int shelterId
}
class DeleteShelterHandler {
  + handle(DeleteShelterCommand) Boolean
}
class DeleteUserCommand {
  + tryCreate(Map~String, String~) Optional~DeleteUserCommand~
   int userId
}
class DeleteUserHandler {
  + handle(DeleteUserCommand) Boolean
}
class DispatcherServlet {
  - checkTokenHandler() PipelineHandler
  + destroy() void
  # service(HttpServletRequest, HttpServletResponse) void
  + init() void
}
class Endpoint {
  + method() HttpMethod
  + path() String
}
class EndpointInfo {
  + controller() Class~Controller~
  + httpMethod() HttpMethod
  + requiresAuthentication() boolean
  + method() Method
  + path() String
}
class Entity {
   int id
}
class EntityProducer {
  + makePlace(ResultSet) Place
  + makePhoto(ResultSet) Photo
  + makeAdvert(ResultSet) Advert
  + makeUser(ResultSet) User
  + makeAnimalAdvert(ResultSet) AnimalAdvert
  + makeShelter(ResultSet) Shelter
  - booleanFromString(String) boolean
}
class FromBody
class FromQuery {
  + parameterName() String
}
class GetAdvertByIdHandler {
  + handle(GetAdvertByIdQuery) AdvertModel
}
class GetAdvertByIdQuery {
  + tryCreate(Map~String, String~) Optional~GetAdvertByIdQuery~
   int advertId
}
class GetAdvertPreviewHandler {
  + handle(GetAdvertPreviewRequest) PhotoModel
}
class GetAdvertPreviewRequest
class GetAdvertsHandler {
  + handle(GetAdvertsQuery) AdvertModel[]
}
class GetAdvertsQuery
class GetAllAnimalAdvertsHandler {
  + handle(GetAllAnimalAdvertsQuery) List~AnimalAdvertModel~
}
class GetAllAnimalAdvertsQuery
class GetAnimalAdvertHandler {
  + handle(GetAnimalAdvertQuery) AnimalAdvertModel
}
class GetAnimalAdvertQuery
class GetAnimalAdvertsByShelterIdHandler {
  + handle(GetAnimalAdvertsByShelterIdQuery) List~AnimalAdvertModel~
}
class GetAnimalAdvertsByShelterIdQuery
class GetAnimalAdvertsByUserIdHandler {
  + handle(GetAnimalAdvertsByUserIdQuery) List~AnimalAdvertModel~
}
class GetAnimalAdvertsByUserIdQuery
class GetPhotosByAdvertIdHandler {
  + handle(GetPhotosByAdvertIdQuery) List~PhotoModel~
}
class GetPhotosByAdvertIdQuery
class GetPlaceByIdHandler {
  + handle(GetPlaceByIdQuery) PlaceModel
}
class GetPlaceByIdQuery {
  + tryCreate(Map~String, String~) Optional~GetPlaceByIdQuery~
   int placeId
}
class GetPlacesHandler {
  + handle(GetPlacesQuery) PlaceModel[]
}
class GetPlacesQuery
class GetShelterByIdHandler {
  + handle(GetShelterByIdQuery) ShelterModel
}
class GetShelterByIdQuery {
  + tryCreate(Map~String, String~) Optional~GetShelterByIdQuery~
   int shelterId
}
class GetSheltersHandler {
  + handle(GetSheltersQuery) ShelterModel[]
}
class GetSheltersQuery
class GetUserByIdHandler {
  + handle(GetUserByIdQuery) UserModel
}
class GetUserByIdQuery {
  + tryCreate(Map~String, String~) Optional~GetUserByIdQuery~
   int userId
}
class GetUserIdAndLoginHandler {
  + handle(GetUserIdAndLoginQuery) GetUserIdAndLoginResponse
}
class GetUserIdAndLoginQuery
class GetUserIdAndLoginResponse
class GetUsersHandler {
  + handle(GetUsersQuery) UserModel[]
}
class GetUsersQuery
class GlobalServiceProvider {
<<Interface>>
  + addScoped(Class~T~) GlobalServiceProvider
  + addScoped(Class~T~, Supplier~T~) GlobalServiceProvider
  + addService(Class~T~, ServiceType, Class~T~) GlobalServiceProvider
  + addSingleton(Class~T~, Function~ServiceProvider, T~) GlobalServiceProvider
  + addService(Class~T~, ServiceType) GlobalServiceProvider
  + addScoped(Class~T~, Function~ServiceProvider, T~) GlobalServiceProvider
  + addScoped(Class~T~, Class~T~) GlobalServiceProvider
  + addTransient(Class~T~) GlobalServiceProvider
  + addSingleton(Class~T~, Supplier~T~) GlobalServiceProvider
  + addSingleton(Class~T~, Class~T~) GlobalServiceProvider
  + addService(Class~T~, ServiceType, Function~ServiceProvider, T~) GlobalServiceProvider
  + addTransient(Class~T~, Class~T~) GlobalServiceProvider
  + addSingleton(Class~T~) GlobalServiceProvider
  + addTransient(Class~T~, Supplier~T~) GlobalServiceProvider
  + addTransient(Class~T~, Function~ServiceProvider, T~) GlobalServiceProvider
  + addService(Class~T~, ServiceType, Supplier~T~) GlobalServiceProvider
   ScopedServiceProvider requestServiceProvider
}
class HandleResult {
   Optional~?~ responseObject
   int code
}
class HandlersDataList {
  + findHandler(Class~?~) Optional~Class~RequestHandler~?, ?~~~
  + registerHandler(Class~RequestHandler~T, R~~, Class~R~) void
}
class HashPasswordHasher {
  + hashPassword(String) String
  + verifyPassword(User, String) PasswordVerificationResult
  - hash(String, byte[]) byte[]
}
class HttpMethod {
<<enumeration>>
  + valueOf(String) HttpMethod
  + values() HttpMethod[]
}
class JwtSigner {
  + signJwt(String) String
}
class Keys {
  + publicKey() RSAPublicKey
  + privateKey() RSAPrivateKey
}
class Logging {
  + warnNonAuthorizedAccess(HttpServletRequest, Logger) void
}
class Mediatr {
  + send(R) T
}
class NoHandlerException
class ObjectConstructor {
  + createInstance(Class~T~, ServiceProvider) T?
}
class Pair {
   int shelterId
   int userId
}
class ParsingUtils {
  + tryParseInt(String) OptionalInt
}
class PasswordHasher {
  + verifyPassword(User, String) PasswordVerificationResult
  + hashPassword(String) String
}
class PasswordVerificationResult {
<<enumeration>>
  + valueOf(String) PasswordVerificationResult
  + values() PasswordVerificationResult[]
}
class Photo {
  + toString() String
  + equals(Object) boolean
  + hashCode() int
   byte[] data
   int advertId
}
class PhotoController {
  + getPreview(int) HandleResult
  + getByAdvertId(int) HandleResult
}
class PhotoDao {
<<Interface>>
  + deleteByAnimalAdvertId(int) boolean
  + create(List~Photo~) void
  + getAdvertPhotos(int) List~Photo~
}
class PhotoModel {
  + of(Photo) PhotoModel
}
class PhotoSetup {
  + applyTo(ApplicationBuilder) void
}
class PipelineHandler {
<<Interface>>
  + handle(HttpServletRequest, HttpServletResponse, ScopedServiceProvider, EndpointInfo, PipelineHandler) HandleResult
}
class PipelineSender {
  + handle(HttpServletRequest, HttpServletResponse, ScopedServiceProvider, EndpointInfo, PipelineHandler) HandleResult
}
class Place {
  + equals(Object) boolean
  + hashCode() int
  + toString() String
   String city
   String region
   String district
   String country
}
class PlaceController {
  + updatePlace(UpdatePlaceCommand, String) HandleResult
  + getById(int) HandleResult
  + deletePlaceById(int, String) HandleResult
  + createPlace(CreatePlaceCommand, String) HandleResult
   HandleResult all
}
class PlaceDao {
<<Interface>>

}
class PlaceModel {
  + region() String
  + of(Place) PlaceModel
  + id() int
  + city() String
  + district() String
  + country() String
}
class PlacesSetup {
  + applyTo(ApplicationBuilder) void
}
class PooledConnection {
  + isValid(int) boolean
  + setClientInfo(String, String) void
  + releaseSavepoint(Savepoint) void
  + createNClob() NClob
  + prepareStatement(String) PreparedStatement
  + createBlob() Blob
  + commit() void
  + compareTo(PooledConnection) int
  + createSQLXML() SQLXML
  + createStruct(String, Object[]) Struct
  + createStatement(int, int, int) Statement
  + setSavepoint(String) Savepoint
  + getClientInfo(String) String
  + prepareCall(String, int, int, int) CallableStatement
  + prepareStatement(String, int[]) PreparedStatement
  + nativeSQL(String) String
  + prepareStatement(String, int, int, int) PreparedStatement
  + createClob() Clob
  + prepareCall(String) CallableStatement
  + isWrapperFor(Class~?~) boolean
  + clearWarnings() void
  + abort(Executor) void
  + setSavepoint() Savepoint
  + prepareCall(String, int, int) CallableStatement
  + prepareStatement(String, int, int) PreparedStatement
  + close() void
  + prepareStatement(String, int) PreparedStatement
  + rollback(Savepoint) void
  + createArrayOf(String, Object[]) Array
  + setNetworkTimeout(Executor, int) void
  + prepareStatement(String, String[]) PreparedStatement
  + rollback() void
  + createStatement(int, int) Statement
  + createStatement() Statement
  + unwrap(Class~T~) T
   Connection connection
   boolean autoCommit
   int holdability
   String schema
   boolean closed
   SQLWarning warnings
   Map~String, Class~?~~ typeMap
   Properties clientInfo
   int networkTimeout
   String catalog
   DatabaseMetaData metaData
   int transactionIsolation
   boolean readOnly
}
class RemoveUserFromShelterCommand
class RemoveUserFromShelterHandler {
  + handle(RemoveUserFromShelterCommand) Void
}
class Request~T~
class RequestHandler~T, R~ {
  + handle(R) T
}
class RequestHandlerInfo~T, R~
class RequestServiceProvider {
  + hasService(Class~T~) boolean
  + getRequiredService(Class~T~) T
  + addScoped(Class~T~, T) ScopedServiceProvider
  + close() void
}
class RequireAuthentication
class Role {
<<enumeration>>
  + toInt() int
  + valueOf(String) Role
  + fromInt(int) Role
  + values() Role[]
}
class Runner {
  + main(String[]) void
  - test() void
}
class ScopedServiceProvider {
<<Interface>>
  + addScoped(Class~T~, T) ScopedServiceProvider
}
class ServiceAlreadyRegisteredException
class ServiceNotFoundException
class ServiceProvider {
<<Interface>>
  + getRequiredService(Class~T~) T
  + hasService(Class~T~) boolean
}
class ServiceType {
<<enumeration>>
  + valueOf(String) ServiceType
  + values() ServiceType[]
}
class ServletUtils {
  + getBody(HttpServletRequest) String
  + writeResponse(Object, ServletOutputStream) void
   HttpServletResponse responseHeaders
}
class SetUserRoleCommand
class SetUserRoleHandler {
  + handle(SetUserRoleCommand) Void
}
class Setup {
  + applyTo(ApplicationBuilder) void
}
class Sex {
<<enumeration>>
  + values() Sex[]
  + valueOf(String) Sex
  + fromValue(String) Sex
   char value
}
class Shelter {
  + hashCode() int
  + toString() String
  + equals(Object) boolean
   String name
   Place place
   String website
   String address
   List~AnimalAdvert~ animalAdverts
   List~User~ administrators
}
class ShelterController {
  + updateShelter(UpdateShelterCommand, String, int) HandleResult
  + deleteShelterById(int, String) HandleResult
  + getById(int) HandleResult
  + createShelter(CreateShelterCommand, String) HandleResult
   HandleResult all
}
class ShelterDao {
<<Interface>>
  + deleteByPlaceId(int) boolean
  + deleteShelterById(int) boolean
}
class ShelterModel {
  + of(Shelter) ShelterModel
  + place() Place
  + address() String
  + name() String
  + website() String
  + id() int
}
class SheltersSetup {
  + applyTo(ApplicationBuilder) void
}
class SignInCommand
class SignInHandler {
  + handle(SignInCommand) SignInResult
}
class SignInResult {
  + userId() int
  + token() String
  + role() String
  + shelterId() int
}
class SimplePasswordHasher {
  + hashPassword(String) String
  + verifyPassword(User, String) PasswordVerificationResult
}
class StatementBuilder {
  + prepareStatement(String, Object[]) PreparedStatement
  - set(PreparedStatement, int, Object) void
}
class UpdateAdvertCommand
class UpdateAdvertHandler {
  + handle(UpdateAdvertCommand) Boolean
}
class UpdatePlaceCommand
class UpdatePlaceHandler {
  + handle(UpdatePlaceCommand) Boolean
}
class UpdateShelterCommand
class UpdateShelterHandler {
  + handle(UpdateShelterCommand) Boolean
}
class UpdateUserCommand
class UpdateUserHandler {
  + handle(UpdateUserCommand) Boolean
}
class User {
  + equals(Object) boolean
  + hashCode() int
  + toString() String
   String password
   Role role
   List~Advert~ adverts
   String login
   List~AnimalAdvert~ animalAdverts
   Contacts contacts
}
class UserController {
  + getSelfId(int) HandleResult
  + getById(int) HandleResult
  + updateUser(UpdateUserCommand, int, String) HandleResult
  + deleteUserById(int, int, String) HandleResult
  - isAnyEmpty(String[]) boolean
  + getSelfInfo(int) HandleResult
  + createUser(CreateUserCommand) HandleResult
  + updateRole(SetUserRoleCommand, String) HandleResult
   HandleResult all
}
class UserDao {
<<Interface>>
  + updateRole(int, Role) void
  + deleteUserById(int) boolean
  + findByLogin(String) User
}
class UserModel {
  + email() String
  + phoneNumber() String
  + name() String
  + surname() String
  + of(User) UserModel
  + id() int
}
class UserShelterController {
  + addUserToShelter(int, int, String) HandleResult
  + removeFromShelter(int, String) HandleResult
}
class UserShelterDao {
<<Interface>>
  + close(Connection) void
  + getUsersId(int) List~Integer~
  + removeUser(int) void
  + remove(int, int) void
  + removeAll(int) void
  + add(int, int) void
  + close(Statement) void
  + getShelterId(int) int
   List~Pair~ all
}
class UserShelterSetup {
  + applyTo(ApplicationBuilder) void
}
class UsersSetup {
  + applyTo(ApplicationBuilder) void
}
class ValidTokensDao {
<<Interface>>
  + invalidateTokens(int) void
  + close(Statement) void
  + close(Connection) void
  + isValidToken(String, int) boolean
  + addValidToken(String, int) void
}
class Validation {
  + isAnyNullOrEmpty(String[]) boolean
  + isNullOrEmpty(String) boolean
  + in(String, String[]) boolean
}
class WebToken {
  + parameterName() String
}

AddUserToShelterCommand  -->  Request~T~ 
AddUserToShelterHandler  -->  RequestHandler~T, R~ 
Advert  -->  Entity 
AdvertController  -->  Controller 
ControllerRoute  ..  AdvertController 
AdvertDao  -->  Dao~K, T~ 
Advert  -->  AdvertType 
AdvertsSetup  -->  Setup 
AnimalAdvert  -->  Entity 
AnimalAdvertDao  -->  Dao~K, T~ 
AnimalAdvertSetup  -->  Setup 
AnimalAdvertsController  -->  Controller 
ControllerRoute  ..  AnimalAdvertsController 
AuthController  -->  Controller 
ControllerRoute  ..  AuthController 
AuthSetup  -->  Setup 
DefaultGlobalServiceProvider  -->  ClassEntry~T~ 
DefaultGlobalServiceProvider  -->  ConstructedObject~T~ 
CreateAdvertCommand  -->  Request~T~ 
CreateAdvertHandler  -->  RequestHandler~T, R~ 
CreateAnimalAdvertCommand  -->  Request~T~ 
CreateAnimalAdvertsHandler  -->  RequestHandler~T, R~ 
CreatePlaceCommand  -->  Request~T~ 
CreatePlaceHandler  -->  RequestHandler~T, R~ 
CreateShelterCommand  -->  Request~T~ 
CreateShelterHandler  -->  RequestHandler~T, R~ 
CreateUserCommand  -->  Request~T~ 
CreateUserHandler  -->  RequestHandler~T, R~ 
Dao~K, T~  ..>  Entity 
DaoSetup  -->  Setup 
DbAdvertDao  ..>  AdvertDao 
DbAnimalAdvertDao  ..>  AnimalAdvertDao 
DbPhotoDao  ..>  PhotoDao 
DbPlaceDao  ..>  PlaceDao 
DbShelterDao  ..>  ShelterDao 
DbUserDao  ..>  UserDao 
DbUserShelterDao  ..>  UserShelterDao 
DbValidTokensDao  ..>  ValidTokensDao 
DefaultGlobalServiceProvider  ..>  GlobalServiceProvider 
DeleteAdvertCommand  -->  Request~T~ 
DeleteAdvertHandler  -->  RequestHandler~T, R~ 
DeletePlaceCommand  -->  Request~T~ 
DeletePlaceHandler  -->  RequestHandler~T, R~ 
DeleteShelterCommand  -->  Request~T~ 
DeleteShelterHandler  -->  RequestHandler~T, R~ 
DeleteUserCommand  -->  Request~T~ 
DeleteUserHandler  -->  RequestHandler~T, R~ 
GetAdvertByIdHandler  -->  RequestHandler~T, R~ 
GetAdvertByIdQuery  -->  Request~T~ 
GetAdvertPreviewHandler  -->  RequestHandler~T, R~ 
GetAdvertPreviewRequest  -->  Request~T~ 
GetAdvertsHandler  -->  RequestHandler~T, R~ 
GetAdvertsQuery  -->  Request~T~ 
GetAllAnimalAdvertsHandler  -->  RequestHandler~T, R~ 
GetAllAnimalAdvertsQuery  -->  Request~T~ 
GetAnimalAdvertHandler  -->  RequestHandler~T, R~ 
GetAnimalAdvertQuery  -->  Request~T~ 
GetAnimalAdvertsByShelterIdHandler  -->  RequestHandler~T, R~ 
GetAnimalAdvertsByShelterIdQuery  -->  Request~T~ 
GetAnimalAdvertsByUserIdHandler  -->  RequestHandler~T, R~ 
GetAnimalAdvertsByUserIdQuery  -->  Request~T~ 
GetPhotosByAdvertIdHandler  -->  RequestHandler~T, R~ 
GetPhotosByAdvertIdQuery  -->  Request~T~ 
GetPlaceByIdHandler  -->  RequestHandler~T, R~ 
GetPlaceByIdQuery  -->  Request~T~ 
GetPlacesHandler  -->  RequestHandler~T, R~ 
GetPlacesQuery  -->  Request~T~ 
GetShelterByIdHandler  -->  RequestHandler~T, R~ 
GetShelterByIdQuery  -->  Request~T~ 
GetSheltersHandler  -->  RequestHandler~T, R~ 
GetSheltersQuery  -->  Request~T~ 
GetUserByIdHandler  -->  RequestHandler~T, R~ 
GetUserByIdQuery  -->  Request~T~ 
GetUserIdAndLoginHandler  -->  RequestHandler~T, R~ 
GetUserIdAndLoginQuery  -->  Request~T~ 
GetUsersHandler  -->  RequestHandler~T, R~ 
GetUsersQuery  -->  Request~T~ 
GlobalServiceProvider  -->  ServiceProvider 
HashPasswordHasher  -->  PasswordHasher 
Application  -->  Keys 
UserShelterDao  -->  Pair 
PasswordHasher  -->  PasswordVerificationResult 
Photo  -->  Entity 
PhotoController  -->  Controller 
ControllerRoute  ..  PhotoController 
PhotoDao  -->  Dao~K, T~ 
PhotoSetup  -->  Setup 
PipelineSender  ..>  PipelineHandler 
Place  -->  Entity 
PlaceController  -->  Controller 
ControllerRoute  ..  PlaceController 
PlaceDao  -->  Dao~K, T~ 
PlacesSetup  -->  Setup 
RemoveUserFromShelterCommand  -->  Request~T~ 
RemoveUserFromShelterHandler  -->  RequestHandler~T, R~ 
RequestHandler~T, R~  ..>  Request~T~ 
HandlersDataList  -->  RequestHandlerInfo~T, R~ 
RequestHandlerInfo~T, R~  ..>  Request~T~ 
DefaultGlobalServiceProvider  -->  RequestServiceProvider 
RequestServiceProvider  ..>  ScopedServiceProvider 
User  -->  Role 
ScopedServiceProvider  -->  ServiceProvider 
GlobalServiceProvider  -->  ServiceType 
SetUserRoleCommand  -->  Request~T~ 
SetUserRoleHandler  -->  RequestHandler~T, R~ 
AnimalAdvert  -->  Sex 
Shelter  -->  Entity 
ShelterController  -->  Controller 
ControllerRoute  ..  ShelterController 
ShelterDao  -->  Dao~K, T~ 
SheltersSetup  -->  Setup 
SignInCommand  -->  Request~T~ 
SignInHandler  -->  RequestHandler~T, R~ 
SimplePasswordHasher  -->  PasswordHasher 
UpdateAdvertCommand  -->  Request~T~ 
UpdateAdvertHandler  -->  RequestHandler~T, R~ 
UpdatePlaceCommand  -->  Request~T~ 
UpdatePlaceHandler  -->  RequestHandler~T, R~ 
UpdateShelterCommand  -->  Request~T~ 
UpdateShelterHandler  -->  RequestHandler~T, R~ 
UpdateUserCommand  -->  Request~T~ 
UpdateUserHandler  -->  RequestHandler~T, R~ 
User  -->  Entity 
UserController  -->  Controller 
ControllerRoute  ..  UserController 
UserDao  -->  Dao~K, T~ 
UserShelterController  -->  Controller 
ControllerRoute  ..  UserShelterController 
UserShelterSetup  -->  Setup 
UsersSetup  -->  Setup 
