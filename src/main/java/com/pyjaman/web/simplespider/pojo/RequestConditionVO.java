package com.pyjaman.web.simplespider.pojo;

/**
 * 请求条件封装类
 */
public class RequestConditionVO {

    /**
     * 起始URL
     */
    private String url;

    /**
     * 数据开始时间
     */
    private String startDate;

    /**
     * 数据结束时间
     */
    private String endDate;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return "RequestConditionVO{" +
                "url='" + url + '\'' +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                '}';
    }
}
