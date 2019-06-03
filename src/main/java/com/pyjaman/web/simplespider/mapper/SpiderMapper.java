package com.pyjaman.web.simplespider.mapper;

import com.pyjaman.web.simplespider.model.AreaInfoModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 爬虫mapper接口类
 *
 * @author pyjaman
 * @date 2019-05-26
 */
@Repository
public interface SpiderMapper {

    /**
     * 全国地区行政区划编码信息入库
     *
     * @param dataList 地区行政区划编码信息集合
     */
    int insertAreaInformation(@Param(value = "map")List<AreaInfoModel> dataList);

}
