package it.giacomobergami.facerei.utils.HTTP;

import com.sun.net.httpserver.HttpExchange;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by vasistas on 07/08/16.
 */
public class Requests {
    /**
     * returns the url parameters in a map
     * @param q
     * @return map
     */
    public static Map<String, String> queryGETToMap(HttpExchange q){
        String query = q.getRequestURI().getQuery();
        Map<String, String> result = new LinkedHashMap<>(); //Keeping the same order of the story telling
        String[] split = query.split("&");
        for (int i = 0, splitLength = split.length; i < splitLength; i++) {
            String param = split[i];
            String pair[] = param.split("=");
            if (pair.length > 1) {
                try {
                    result.put(java.net.URLDecoder.decode(pair[0], "UTF-8"), java.net.URLDecoder.decode(pair[1], "UTF-8"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    result.put(java.net.URLDecoder.decode(pair[0], "UTF-8"), "");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    public static Map<String, String> queryPOSTToMap(HttpExchange q){
        InputStreamReader isr = null;
        Map<String, String> result = new LinkedHashMap<>(); //Keeping the same order of the story telling
        try {
            isr = new InputStreamReader(q.getRequestBody(),"utf-8");
            BufferedReader br = new BufferedReader(isr);
            String query = br.readLine();
            String[] split = query.split("&");
            for (int i = 0, splitLength = split.length; i < splitLength; i++) {
                String param = split[i];
                String pair[] = param.split("=");
                if (pair.length > 1) {
                    try {
                        result.put(java.net.URLDecoder.decode(pair[0], "UTF-8"), java.net.URLDecoder.decode(pair[1], "UTF-8"));
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        result.put(java.net.URLDecoder.decode(pair[0], "UTF-8"), "");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
