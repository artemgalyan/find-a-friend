package by.fpmibsu.find_a_friend.application;

import by.fpmibsu.find_a_friend.utils.Request;

public record Endpoint<T, R extends Request<? super T>>(String path, Class<R> requestType, Class<T> responseType,
                                                        String method) {
}
