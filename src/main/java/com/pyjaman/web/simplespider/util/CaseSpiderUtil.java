package com.pyjaman.web.simplespider.util;

import com.pyjaman.web.simplespider.pojo.CaseInfoVO;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class CaseSpiderUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(CaseSpiderUtil.class);

    /**
     * 通过url爬取网页，获取网页的document对象，解析html获取td元素，再获取td元素中的a标签元素
     * @param data 爬取固定网页的数据
     * @return TR标签元素集合
     */
    public static List<Element> getAElementFromHtml(Map<String, String> data) {
        Document doc = getDocument(data);
        //获取td标签元素
        Elements cells = doc.getElementsByTag("TR");
        return cells;
    }

    public static int getMaxPageNum(Map<String, String> data) {
        Document doc = getDocument(data);
        //获取strong标签元素
        Elements cells = doc.getElementsByTag("strong");
        String pageNum = cells.get(0).text();
        return Integer.parseInt(pageNum);
    }

    public static Document getDocument(Map<String, String> data) {
        //根据url获取html，获取document对象
        Document doc = null;
        while(doc == null) {
            try {
                doc = Jsoup
                        .connect("http://www.hshfy.sh.cn/shfy/gweb2017/ktgg_search_content.jsp")
                        .ignoreContentType(true)
                        .ignoreHttpErrors(true)
                        .timeout(1000 * 30)
                        .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/83.0.4103.116 Safari/537.36")
                        .header("accept","*/*")
                        .header("accept-encoding","gzip, deflate")
                        .header("accept-language","zh-CN,zh;q=0.9")
                        .header("Connection", "keep-alive")
                        .header("Host", "www.hshfy.sh.cn")
                        .header("Referer", "http://www.hshfy.sh.cn/shfy/gweb2017/ktgg_search.jsp?zd=splc")
                        .data(data)
                        .post();
            } catch (IOException e) {
                //会出现连接超时的情况导致报错，直接返回null，去执行下一次
                LOGGER.error("===连接失败！===", e);
            }
        }
        return doc;
    }

    public static List<CaseInfoVO> getInfoFromElement(List<Element> allNeedElements) {
        if(allNeedElements == null || allNeedElements.isEmpty()) {
            return Collections.EMPTY_LIST;
        }
        List<CaseInfoVO> result = new ArrayList<>();
        for(Element element : allNeedElements) {
            Elements childEle = element.getElementsByTag("TD");
            if(childEle.size() != 9) {
                LOGGER.info("===元素个数不对！===");
                continue;
            }
            if("法院".equals(childEle.get(0).text().trim())) {
                continue;
            }
            CaseInfoVO caseInfo = new CaseInfoVO();
            caseInfo.setCourtName(childEle.get(0).getElementsByTag("FONT").text().trim());
            caseInfo.setVenue(childEle.get(1).getElementsByTag("FONT").text().trim());
            caseInfo.setOpenDate(childEle.get(2).text().trim());
            caseInfo.setCaseZhNum(childEle.get(3).text().trim());
            caseInfo.setCaseReason(childEle.get(4).text().trim());
            caseInfo.setDepartName(childEle.get(5).getElementsByTag("div").text().trim());
            caseInfo.setJudgeName(childEle.get(6).getElementsByTag("div").text().trim());
            caseInfo.setPlaintiff(childEle.get(7).text().trim());
            caseInfo.setDefendant(childEle.get(8).text().trim());
            result.add(caseInfo);
        }
        return result;
    }
}
