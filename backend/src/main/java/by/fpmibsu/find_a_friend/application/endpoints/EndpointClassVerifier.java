package by.fpmibsu.find_a_friend.application.endpoints;

import by.fpmibsu.find_a_friend.application.mediatr.Request;
import by.fpmibsu.find_a_friend.application.mediatr.RequestHandler;

import java.lang.reflect.ParameterizedType;

public class EndpointClassVerifier {

    public static boolean verifyRequestAndResponseTypes(Class<?> requestType, Class<?> responseType, Class<? extends RequestHandler<?, ?>> handlerType) {
        if (!(requestType.getGenericSuperclass() instanceof ParameterizedType requestParametrizedType)) {
            return false;
        }

        if (!Request.class.isAssignableFrom(requestType)) {
            return false;
        }

        var params = requestParametrizedType.getActualTypeArguments();
        if (params.length != 1) {
            return false;
        }
        var parameter = params[0];
        if (!(parameter instanceof Class<?> responseClazz)) {
            return false;
        }
        if (!responseType.isAssignableFrom(responseClazz)) {
            return false;
        }

        var rq = (ParameterizedType) handlerType.getGenericSuperclass();
        var genericParameters = rq.getActualTypeArguments();
        return genericParameters[0].equals(responseType) && genericParameters[1].equals(requestType);
    }

    private <T, R extends Request<? super T>, H extends RequestHandler<T, R>> boolean verifyRequestAndResponseTypesImpl(Class<T> response, Class<R> request, Class<H> requestHandler) {
        return true;
    }
}
