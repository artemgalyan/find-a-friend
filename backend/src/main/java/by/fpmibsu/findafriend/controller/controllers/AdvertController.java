package by.fpmibsu.findafriend.controller.controllers;

import by.fpmibsu.findafriend.application.HandleResult;
import by.fpmibsu.findafriend.application.controller.*;
import by.fpmibsu.findafriend.application.mediatr.Mediatr;
import by.fpmibsu.findafriend.controller.AuthUtils;
import by.fpmibsu.findafriend.controller.Logging;
import by.fpmibsu.findafriend.controller.Validation;
import by.fpmibsu.findafriend.controller.commands.adverts.CreateAdvertCommand;
import by.fpmibsu.findafriend.controller.commands.adverts.DeleteAdvertCommand;
import by.fpmibsu.findafriend.controller.commands.adverts.UpdateAdvertCommand;
import by.fpmibsu.findafriend.controller.queries.adverts.GetAdvertByIdQuery;
import by.fpmibsu.findafriend.controller.queries.adverts.GetAdvertsQuery;
import by.fpmibsu.findafriend.dataaccesslayer.advert.AdvertDao;
import by.fpmibsu.findafriend.entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@ControllerRoute(route = "/adverts")
public class AdvertController extends Controller {
    private final Logger logger = LogManager.getLogger(AdvertController.class);
    private final Mediatr mediatr;

    public AdvertController(Mediatr mediatr) {
        this.mediatr = mediatr;
    }

    @Endpoint(path = "/getAll", method = HttpMethod.GET)
    public HandleResult getAll() throws Exception {
        return ok(mediatr.send(new GetAdvertsQuery()));
    }

    @Endpoint(path = "/getById", method = HttpMethod.GET)
    public HandleResult getById(@FromQuery(parameterName = "id") int id) throws Exception {
        return ok(mediatr.send(new GetAdvertByIdQuery(id)));
    }

    @RequireAuthentication
    @Endpoint(path = "/create", method = HttpMethod.POST)
    public HandleResult createAdvert(@FromBody CreateAdvertCommand request) throws Exception {
        if (Validation.isAnyNullOrEmpty(request.description, request.title)
                || (request.advertType < 0 || request.advertType > 1)) {
            return badRequest();
        }
        return ok(mediatr.send(request));
    }

    @RequireAuthentication
    @Endpoint(path = "/update", method = HttpMethod.PUT)
    public HandleResult updateAdvert(@FromBody UpdateAdvertCommand request, @WebToken(parameterName = "id") int userId,
                                     @WebToken(parameterName = "role") String role) throws Exception {
        if (Validation.isAnyNullOrEmpty(request.description, request.title)) {
            return badRequest();
        }
        if (!AuthUtils.allowRoles(role, User.Role.ADMINISTRATOR, User.Role.MODERATOR)) {
            var advertDao = serviceProvider.getRequiredService(AdvertDao.class);
            var advert = advertDao.getEntityById(request.advertId);
            if (advert == null) {
                return badRequest();
            }
            if (advert.getOwner().getId() != userId) {
                Logging.warnNonAuthorizedAccess(this.request, logger);
                return notAuthorized();
            }
        }
        return ok(mediatr.send(request));
    }

    @RequireAuthentication
    @Endpoint(path = "/delete", method = HttpMethod.DELETE)
    public HandleResult deleteAdvertById(@FromQuery(parameterName = "id") int id,
                                         @WebToken(parameterName = "id") int userId,
                                         @WebToken(parameterName = "role") String role) throws Exception {
        if (!AuthUtils.allowRoles(role, User.Role.ADMINISTRATOR, User.Role.MODERATOR)) {
            var advertDao = serviceProvider.getRequiredService(AdvertDao.class);
            var advert = advertDao.getEntityById(id);
            if (advert == null) {
                return badRequest();
            }
            if (advert.getOwner().getId() != userId) {
                Logging.warnNonAuthorizedAccess(this.request, logger);
                return notAuthorized();
            }
        }
        logger.trace("response is completed");
        return ok(mediatr.send(new DeleteAdvertCommand(id)));
    }
}