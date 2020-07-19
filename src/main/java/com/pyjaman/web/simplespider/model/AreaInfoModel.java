package com.pyjaman.web.simplespider.model;


/**
 * 地区行政区划编码信息实体类
 *
 * @author pyjaman
 * @date 2019-05-26
 */
public class AreaInfoModel {

    /**
     * 地区编码
     */
    private String regionCode;

    /**
     * 地区名称
     */
    private String regionName;

    /**
     * 父级地区编码
     */
    private String parentCode;

    public String getRegionCode() {
        return regionCode;
    }

    public void setRegionCode(String regionCode) {
        this.regionCode = regionCode;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public String getParentCode() {
        return parentCode;
    }

    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }

    @Override
    public String toString() {
        return "AreaInfoModel{" +
                "regionCode='" + regionCode + '\'' +
                ", regionName='" + regionName + '\'' +
                ", parentCode='" + parentCode + '\'' +
                '}';
    }
}
