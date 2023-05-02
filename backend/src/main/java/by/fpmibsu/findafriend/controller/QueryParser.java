package by.fpmibsu.findafriend.controller;

import java.util.Map;
import java.util.TreeMap;

public class QueryParser {
    public Map<String, String> parseQuery(String query) {
        if (query.indexOf('?')==-1){
            return new TreeMap<String, String>();
        }
        Map<String, String> map = new TreeMap<>();
        query = query.substring(query.indexOf('?')+1, query.length());
        String[] params = query.split("&");
        for (String param : params) {
            String name = param.split("=")[0];
            String value = param.split("=")[1];
            map.put(name, value);
        }
        return map;
    }
}
