package com.example.danielleonett.mvvmexample.util;

import com.google.gson.Gson;

/**
 * Created by daniel.leonett on 6/02/2018.
 */

public class StringUtils {

    public static String toString(Object object) {
        return new Gson().toJson(object);
    }

}
