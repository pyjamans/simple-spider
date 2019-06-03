package com.pyjaman.web.simplespider.util;

public class DataUtil {

    public static String getRegionCode(String regionCode) {
        if(!regionCode.matches("^\\d+$")) {
            return null;
        }
        if(regionCode.endsWith("00")) {
            regionCode = regionCode.substring(0,regionCode.length()-2);
            return getRegionCode(regionCode);
        }
        return regionCode;
    }

}
