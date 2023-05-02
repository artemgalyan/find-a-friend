package by.fpmibsu.findafriend.controller;

import java.util.OptionalInt;

public class ParsingUtils {
    public static OptionalInt tryParseInt(String value) {
        try {
            return OptionalInt.of(
                    Integer.parseInt(value)
            );
        } catch (Exception e) {
            return OptionalInt.empty();
        }
    }
}
