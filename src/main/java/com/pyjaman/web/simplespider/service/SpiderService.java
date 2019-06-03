package com.pyjaman.web.simplespider.service;

import com.pyjaman.web.simplespider.mapper.SpiderMapper;
import com.pyjaman.web.simplespider.model.AreaInfoModel;
import com.pyjaman.web.simplespider.util.SpiderUtil;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


/**
 * 爬虫业务类
 *
 * @author pyjaman
 * @date 2019-05-26
 */
@Service
public class SpiderService {


    @Autowired
    private SpiderMapper spiderMapper;


    /**
     * 全国地区行政区划编码信息爬取
     *
     * @param url 爬虫起始URL
     * @return 地区编码信息集合
     */
    public String spiderAreaInformation(String url) {
        List<String> urlList = new ArrayList<>();
        int count = 0;
        List<Element> allNeedElements = new ArrayList<>();
        urlList.add("index.html");
        while(urlList.size() != 0){

            List<Element> list = SpiderUtil.getElementFromHtml(url+urlList.get(0));
            if(list == null) {
                String error = urlList.get(0);
                urlList.remove(0);
                urlList.add(error);
                continue;
            }
            allNeedElements.addAll(list);
            urlList.addAll(SpiderUtil.getUrlList(list));
            urlList.remove(0);

        }
        List<AreaInfoModel> result = SpiderUtil.getInfoFromElement(allNeedElements);
        count += spiderMapper.insertAreaInformation(result);
        return count+"";
    }
}
