package com.enjoy.myorm.orm.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @description:
 * @author: lij
 * @create: 2019-10-06 11:02
 */
public class PropertyParseUtil {

    private static Pattern regex = Pattern.compile("\\$\\{([^}]*)\\}");

    public static String collect(String placeHolder){
        Matcher matcher = regex.matcher(placeHolder);
        while(matcher.find()) {
            return matcher.group(1);
        }
        return placeHolder;
    }
}
