package com.pyjaman.web.simplespider.service;

import com.pyjaman.web.simplespider.mapper.SpiderMapper;
import com.pyjaman.web.simplespider.model.AreaInfoModel;
import com.pyjaman.web.simplespider.pojo.CaseInfoVO;
import com.pyjaman.web.simplespider.pojo.RequestConditionVO;
import com.pyjaman.web.simplespider.util.AreaSpiderUtil;
import com.pyjaman.web.simplespider.util.CaseSpiderUtil;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 爬虫业务类
 *
 * @author pyjaman
 * @date 2019-05-26
 */
@Service
public class SpiderService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SpiderService.class);

    @Autowired
    private SpiderMapper spiderMapper;


    /**
     * 全国地区行政区划编码信息爬取
     *
     * @param condition 爬虫爬取条件
     * @return 地区编码信息集合
     */
    public String spiderAreaInformation(RequestConditionVO condition) {
        List<String> urlList = new ArrayList<>();
        int count = 0;
        List<Element> allNeedElements = new ArrayList<>();
        String url = condition.getUrl();
        urlList.add("index.html");
        //循环爬取urlList中的url，直到urlList中没有url
        while(urlList.size() != 0){
            //获取a标签元素对象集合
            List<Element> list = AreaSpiderUtil.getAElementFromHtml(url+urlList.get(0));
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
            urlList.addAll(AreaSpiderUtil.getUrlList(list));
            //移除已经成功访问的url
            urlList.remove(0);

        }
        //从a标签集合中获取地区编码信息对象集合
        List<AreaInfoModel> result = AreaSpiderUtil.getInfoFromElement(allNeedElements);
        //将数据入库
        count += spiderMapper.insertAreaInformation(result);
        //返回入库的数据量
        return count+"";
    }

    public String spiderCaseInformation(RequestConditionVO condition) {
        List<Element> allNeedElements = new ArrayList<>();
        Map<String, String> data = new HashMap<>();
        data.put("yzm", "YTNd");
        data.put("ktrqks", condition.getStartDate());
        data.put("ktrqjs", condition.getEndDate());
        data.put("pagesnum", "1");
        int totalNum = CaseSpiderUtil.getMaxPageNum(data);
        LOGGER.info("===共{}条数据===", totalNum);
        if(totalNum == 0) {
            return "===数据为0！===";
        }
        int pageNum = totalNum %15 ==0 ? totalNum/15 : totalNum/15 +1;

        for(int current = 1; current <= pageNum; current++) {
            data.put("pagesnum", current+"");
            allNeedElements.addAll(CaseSpiderUtil.getAElementFromHtml(data));

        }
        //解析数据
        List<CaseInfoVO> result = CaseSpiderUtil.getInfoFromElement(allNeedElements);
        //将数据入库
        int cnt = 0;
        int size = result.size();
        while(cnt < size) {
            int subCnt = cnt + 2000 > size ? size : cnt+2000;
            spiderMapper.insertCaseInformation(result.subList(cnt, subCnt));
            cnt = subCnt;
        }
        return size+"";
    }
}
