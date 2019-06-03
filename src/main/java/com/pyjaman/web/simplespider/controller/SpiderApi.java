package com.pyjaman.web.simplespider.controller;

import com.pyjaman.web.simplespider.service.SpiderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 爬虫接口类
 *
 * @author pyjaman
 * @date 2019-05-26
 */
@RestController
@RequestMapping("api/spider")
public class SpiderApi {

    @Autowired
    private SpiderService spiderService;

    /**
     * 全国地区行政区划编码信息爬取
     *
     * @param url 爬虫起始URL [url = http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2018/]
     * @return 地区编码信息集合
     */
    @GetMapping("/area-info")
    public String spiderAreaInformation(String url) {

        return spiderService.spiderAreaInformation(url);
    }
}
