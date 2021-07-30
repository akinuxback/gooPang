package com.aki.goosinsa.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class MyFormat {

    public static String encode(String path) throws UnsupportedEncodingException {
        return URLEncoder.encode(path, "UTF-8");
    }

}
