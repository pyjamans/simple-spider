package com.pyjaman.web.simplespider.util;

public class DataUtil {

    /**
     * 行政区划编码处理（12位保留6位）
     * @param regionCode 12位行政区划编码
     * @return 6位行政区划编码
     */
    public static String getRegionCode(String regionCode) {
        //正则判断是否全是数字，如果不是则编码有错，直接返回null
        if(!regionCode.matches("^\\d+$")) {
            return null;
        }
        //递归去除多余编码
        if(regionCode.endsWith("00")) {
            regionCode = regionCode.substring(0,regionCode.length()-2);
            return getRegionCode(regionCode);
        }
        return regionCode;
    }

}
