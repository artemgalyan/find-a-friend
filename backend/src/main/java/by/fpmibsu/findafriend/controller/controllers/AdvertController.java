package by.fpmibsu.findafriend.controller.controllers;

import by.fpmibsu.findafriend.application.HandleResult;
import by.fpmibsu.findafriend.application.controller.*;
import by.fpmibsu.findafriend.application.mediatr.Mediatr;
import by.fpmibsu.findafriend.controller.Validation;
import by.fpmibsu.findafriend.controller.commands.adverts.CreateAdvertCommand;
import by.fpmibsu.findafriend.controller.commands.adverts.DeleteAdvertCommand;
import by.fpmibsu.findafriend.controller.commands.adverts.UpdateAdvertCommand;
import by.fpmibsu.findafriend.controller.queries.users.GetUserByIdQuery;
import by.fpmibsu.findafriend.controller.queries.users.GetUsersQuery;
import by.fpmibsu.findafriend.dataaccesslayer.advert.AdvertDao;
import by.fpmibsu.findafriend.entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@ControllerRoute(route = "/adverts")
public class AdvertController extends Controller {
    private final Logger logger = LogManager.getLogger();
    private final Mediatr mediatr;
    private final AdvertDao advertDao;

    public AdvertController(Mediatr mediatr, AdvertDao advertDao) {
        this.mediatr = mediatr;
        this.advertDao = advertDao;
    }

    @Endpoint(path = "/getAll", method = HttpMethod.GET)
    public HandleResult getAll() {
        logger.trace("response is completed");
        return ok(mediatr.send(new GetUsersQuery()));
    }

    @Endpoint(path = "/getAdvert", method = HttpMethod.GET)
    public HandleResult getById(@FromQuery(parameterName = "id") int id) {
        logger.trace("response is completed");
        return ok(mediatr.send(new GetUserByIdQuery(id)));
    }

    @RequireAuthentication
    @Endpoint(path = "/createAdvert", method = HttpMethod.POST)
    public HandleResult createAdvert(@FromBody CreateAdvertCommand request) {
        if (Validation.isAnyNullOrEmpty(request.advertType, request.description, request.title)
                || (!"V".equals(request.advertType) && !"S".equals(request.advertType))) {
            logger.error("request does not exist or is incorrect");
            return badRequest();
        }
        logger.trace("response is completed");
        return ok(mediatr.send(request));
    }

    @RequireAuthentication
    @Endpoint(path = "/updateAdvert", method = HttpMethod.PUT)
    public HandleResult updateAdvert(@FromBody UpdateAdvertCommand request, @WebToken(parameterName = "id") int userId,
                                     @WebToken(parameterName = "role") String role) {
        if (Validation.isAnyNullOrEmpty(request.description, request.title)) {
            logger.error("request doesn't exist or is incorrect");
            return badRequest();
        }
        if (!User.Role.ADMINISTRATOR.toString().equals(role) && !User.Role.MODERATOR.equals(role)) {
            var advert = advertDao.getEntityById(request.advertId);
            if (advert == null) {
                logger.error("request doesn't exist or is incorrect");
                return badRequest();
            }
            if (advert.getOwner().getId() != userId) {
                logger.warn("non authorized user");
                return notAuthorized();
            }
        }
        logger.trace("response is completed");
        return ok(mediatr.send(request));
    }

    @Endpoint(path = "/deleteAdvert", method = HttpMethod.DELETE)
    public HandleResult deleteAdvertById(@FromQuery(parameterName = "id") int id, @WebToken(parameterName = "id") int userId,
                                         @WebToken(parameterName = "role") String role) {
        if (!User.Role.ADMINISTRATOR.toString().equals(role) && !User.Role.MODERATOR.equals(role)) {
            var advert = advertDao.getEntityById(id);
            if (advert == null) {
                logger.error("request doesn't exist or is incorrect");
                return badRequest();
            }
            if (advert.getOwner().getId() != userId) {
                logger.warn("non authorized user");
                return notAuthorized();
            }
        }
        logger.trace("response is completed");
        return ok(mediatr.send(new DeleteAdvertCommand(id)));
    }
}