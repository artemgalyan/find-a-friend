package by.fpmibsu.findafriend.controller;

import java.util.Map;
import java.util.TreeMap;

public class QueryParser {
    public Map<String, String> parseQuery(String query) {
        if (query.indexOf('?') == -1) {
            return new TreeMap<>();
        }
        Map<String, String> map = new TreeMap<>();
        query = query.substring(query.indexOf('?') + 1);
        String[] params = query.split("&");
        for (String param : params) {
            String[] nameValue = param.split("=");
            String name = nameValue[0];
            String value = nameValue.length == 1 ? "" : nameValue[1];
            map.put(name, value);
        }
        return map;
    }
}
