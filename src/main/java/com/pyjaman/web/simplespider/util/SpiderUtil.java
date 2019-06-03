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
     * @param url 爬取网页地址
     * @return a标签元素集合
     */
    public static List<Element> getAElementFromHtml(String url) {
        //根据url获取html，获取document对象
        Document doc = null;
        try {
            doc = Jsoup
                    .connect(url)
                    .ignoreContentType(true)
                    .ignoreHttpErrors(true)
                    .timeout(1000 * 30)
                    .userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64; rv:46.0) Gecko/20100101 Firefox/46.0")
                    .header("accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8")
                    .header("accept-encoding","gzip, deflate, br")
                    .header("accept-language","zh-CN,zh;q=0.9,en-US;q=0.8,en;q=0.7")
                    .get();
        } catch (IOException e) {
            //会出现连接超时的情况导致报错，直接返回null，去执行下一次
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
     * @return 返回url集合
     */
    public static List<String> getUrlList(List<Element> list) {
        List<String> result = new ArrayList<>();
        for(Element element : list) {
            String url = element.attr("href");
            int count = 0;
            for(String temp : result) {
                //如果url重复或者为空，则不加入url集合
                if(null != temp && temp.equals(url)) {
                    count ++;
                    break;
                }
            }
            //控制url的数量（这里只爬取区县级以上地区编码，控制url长度小于13）
            if(count == 0 && url.length()<=12) {
                result.add(url);
            }
        }
        return result;
    }

    /**
     * 获取a标签元素中的text信息，即全国地区行政区划编码信息
     * @param allNeedElements a标签元素集合
     * @return 返回地区行政区划信息对象集合
     */
    public static List<AreaInfoModel> getInfoFromElement(List<Element> allNeedElements) {
        List<AreaInfoModel> result = new ArrayList<>();
        //遍历2次元素集合来去重
        for(Element element1 : allNeedElements) {
            for(Element element2 : allNeedElements) {
                //只取url相同，且text不同的信息
                if(null != element1.attr("href") &&
                        element1.attr("href").equals(element2.attr("href")) &&
                        !element1.text().equals(element2.text())) {
                    AreaInfoModel areaInfoModel = new AreaInfoModel();
                    String text1 = element1.text();
                    String text2 = element2.text();
                    if(text1.matches("^\\d+$")) {//如果text1为地区编码
                        String regionCode = DataUtil.getRegionCode(text1);
                        areaInfoModel.setRegionCode(regionCode);
                        areaInfoModel.setRegionName(text2);
                        areaInfoModel.setParentCode(regionCode.substring(0, regionCode.length()-2));
                    }else{//如果text1不为地区编码，即text2为地区编码
                        String regionCode = DataUtil.getRegionCode(text2);
                        areaInfoModel.setRegionCode(regionCode);
                        areaInfoModel.setRegionName(text1);
                        areaInfoModel.setParentCode(regionCode.substring(0, regionCode.length()-2));
                    }
                    int count = 0;
                    //遍历地区行政区划信息对象集合，如果已存在则除去
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
