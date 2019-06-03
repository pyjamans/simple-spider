package com.pyjaman.web.simplespider.util;

import com.pyjaman.web.simplespider.model.AreaInfoModel;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SpiderUtil {

    /**
     * 通过url爬取网页，获取网页的document对象，解析html获取td元素，再获取td元素中的a标签元素
     * @param url
     * @return
     */
    public static List<Element> getElementFromHtml(String url) {
        //根据url获取html，获取document对象
        Document doc = null;
        try {
            doc = Jsoup
                    .connect(url)
                    .ignoreContentType(true)
                    .ignoreHttpErrors(true)
//                    .timeout(1000 * 30)
                    .userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64; rv:46.0) Gecko/20100101 Firefox/46.0")
                    .header("accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8")
                    .header("accept-encoding","gzip, deflate, br")
                    .header("accept-language","zh-CN,zh;q=0.9,en-US;q=0.8,en;q=0.7")
                    .get();
        } catch (IOException e) {
            return null;
        }
        //获取td标签元素
        Elements cells = doc.getElementsByTag("td");
        List<Element> allNeedElements = new ArrayList<>();
        //获取所有td标签中的a标签
        for(Element cell : cells) {
            Elements links = cell.getElementsByTag("a");
            for(Element link : links) {
                allNeedElements.add(link);
            }
        }
        return allNeedElements;
    }

    /**
     * 获取a标签中的href属性，即url
     * @param list a标签元素集合
     * @return
     */
    public static List<String> getUrlList(List<Element> list) {
        List<String> result = new ArrayList<>();
        for(Element element : list) {
            String url = element.attr("href");
            int count = 0;
            for(String temp : result) {
                if(null != temp && temp.equals(url)) {
                    count ++;
                    break;
                }
            }
            if(count == 0 && url.length()<=12) {
                result.add(url);
            }
            /*String code = DataUtil.getRegionCode(url.substring(1,13));
            if(code == null) {
                continue;
            }
            if(count == 0 && code.length()<6) {
                result.add(url);
            }*/
        }
        return result;
    }

    /**
     * 获取a标签元素中的text信息，即全国地区行政区划编码信息
     * @param allNeedElements a标签元素集合
     * @return
     */
    public static List<AreaInfoModel> getInfoFromElement(List<Element> allNeedElements) {
        List<AreaInfoModel> result = new ArrayList<>();
        for(Element element1 : allNeedElements) {
            for(Element element2 : allNeedElements) {
                if(null != element1.attr("href") &&
                        element1.attr("href").equals(element2.attr("href")) &&
                        !element1.text().equals(element2.text())) {
                    AreaInfoModel areaInfoModel = new AreaInfoModel();
                    String text1 = element1.text();
                    String text2 = element2.text();
                    if(text1.matches("^\\d+$")) {
                        String regionCode = DataUtil.getRegionCode(text1);
                        areaInfoModel.setRegionCode(regionCode);
                        areaInfoModel.setRegionName(text2);
                        areaInfoModel.setParentCode(regionCode.substring(0, regionCode.length()-2));
                    }else{
                        String regionCode = DataUtil.getRegionCode(text2);
                        areaInfoModel.setRegionCode(regionCode);
                        areaInfoModel.setRegionName(text1);
                        areaInfoModel.setParentCode(regionCode.substring(0, regionCode.length()-2));
                    }
                    int count = 0;
                    for(AreaInfoModel temp : result) {
                        if(temp.getRegionCode().equals(areaInfoModel.getRegionCode())) {
                            count ++;
                            break;
                        }
                    }
                    if(count == 0) {
                        result.add(areaInfoModel);
                    }
                }
            }
        }
        return result;
    }
}
