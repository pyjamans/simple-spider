package com.pyjaman.web.simplespider.pojo;

public class CaseInfoVO {

    private String courtName;

    private String venue;

    private String openDate;

    private String caseZhNum;

    private String caseReason;

    private String departName;

    private String judgeName;

    private String plaintiff;

    private String defendant;

    public String getCourtName() {
        return courtName;
    }

    public void setCourtName(String courtName) {
        this.courtName = courtName;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public String getOpenDate() {
        return openDate;
    }

    public void setOpenDate(String openDate) {
        this.openDate = openDate;
    }

    public String getCaseZhNum() {
        return caseZhNum;
    }

    public void setCaseZhNum(String caseZhNum) {
        this.caseZhNum = caseZhNum;
    }

    public String getCaseReason() {
        return caseReason;
    }

    public void setCaseReason(String caseReason) {
        this.caseReason = caseReason;
    }

    public String getDepartName() {
        return departName;
    }

    public void setDepartName(String departName) {
        this.departName = departName;
    }

    public String getJudgeName() {
        return judgeName;
    }

    public void setJudgeName(String judgeName) {
        this.judgeName = judgeName;
    }

    public String getPlaintiff() {
        return plaintiff;
    }

    public void setPlaintiff(String plaintiff) {
        this.plaintiff = plaintiff;
    }

    public String getDefendant() {
        return defendant;
    }

    public void setDefendant(String defendant) {
        this.defendant = defendant;
    }

    @Override
    public String toString() {
        return "CaseInfoVO{" +
                "courtName='" + courtName + '\'' +
                ", venue='" + venue + '\'' +
                ", openDate='" + openDate + '\'' +
                ", caseZhNum='" + caseZhNum + '\'' +
                ", caseReason='" + caseReason + '\'' +
                ", departName='" + departName + '\'' +
                ", judgeName='" + judgeName + '\'' +
                ", plaintiff='" + plaintiff + '\'' +
                ", defendant='" + defendant + '\'' +
                '}';
    }
}
