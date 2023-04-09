package by.fpmibsu.find_a_friend.application;

public enum HttpMethod {
    POST("POST"),
    GET("GET"),
    DELETE("DELETE"),
    UPDATE("UPDATE"),
    PUT("PUT");
    private final String value;

    HttpMethod(String value) {
        this.value = value;
    }
}
