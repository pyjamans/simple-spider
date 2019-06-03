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
        //循环爬取urlList中的url，直到urlList中没有url
        while(urlList.size() != 0){
            //获取a标签元素对象集合
            List<Element> list = SpiderUtil.getAElementFromHtml(url+urlList.get(0));
            //如果出现超时连接，则url访问失败，将url移至集合末尾，继续执行下一个url
            if(list == null) {
                String error = urlList.get(0);
                urlList.remove(0);
                urlList.add(error);
                continue;
            }
            //将获取的a标签集合汇总
            allNeedElements.addAll(list);
            //将获取的url汇聚到urlList中
            urlList.addAll(SpiderUtil.getUrlList(list));
            //移除已经成功访问的url
            urlList.remove(0);

        }
        //从a标签集合中获取地区编码信息对象集合
        List<AreaInfoModel> result = SpiderUtil.getInfoFromElement(allNeedElements);
        //将数据入库
        count += spiderMapper.insertAreaInformation(result);
        //返回入库的数据量
        return count+"";
    }
}
