package com.pyjaman.web.simplespider.model;

import lombok.Data;

/**
 * 地区行政区划编码信息实体类
 *
 * @author pyjaman
 * @date 2019-05-26
 */
@Data
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

}
