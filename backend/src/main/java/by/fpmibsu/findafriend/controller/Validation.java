package by.fpmibsu.findafriend.controller;

import java.util.Arrays;

public class Validation {
    public static boolean isNullOrEmpty(String s) {
        return s == null || s.length() == 0;
    }

    public static boolean isAnyNullOrEmpty(String... s) {
        return Arrays.stream(s)
                .anyMatch(Validation::isNullOrEmpty);
    }

    public static boolean in(String s, String... other) {
        return Arrays.asList(other).contains(s);
    }
}
