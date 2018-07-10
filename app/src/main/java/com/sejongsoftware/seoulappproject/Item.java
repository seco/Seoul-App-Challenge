package com.sejongsoftware.seoulappproject;

/**
 * Created by choyoushin on 2018. 7. 9..
 */

public class Item {
    String SVCID; // 서비스 ID
    String SVCNM; // 서비스
    String MAXCLASSNM, MINCLASSNM; // 대분류명, 소분류명
    String AREANM; // 지역(구)
    String SVCSTATNM; // 접수 상태
    String PAYATNM; // 결제 방법
    String PLACENM; // 장소명
    String USETGTINFO; // 대상

    public String getSVCNM() {
        return SVCNM;
    }

    public void setSVCNM(String SVCNM) {
        this.SVCNM = SVCNM;
    }

    public String getSVCID() {
        return SVCID;
    }

    public void setSVCID(String SVCID) {
        this.SVCID = SVCID;
    }

    public String getMAXCLASSNM() {
        return MAXCLASSNM;
    }

    public void setMAXCLASSNM(String MAXCLASSNM) {
        this.MAXCLASSNM = MAXCLASSNM;
    }

    public String getMINCLASSNM() {
        return MINCLASSNM;
    }

    public void setMINCLASSNM(String MINCLASSNM) {
        this.MINCLASSNM = MINCLASSNM;
    }

    public String getAREANM() {
        return AREANM;
    }

    public void setAREANM(String AREANM) {
        this.AREANM = AREANM;
    }

    public String getSVCSTATNM() {
        return SVCSTATNM;
    }

    public void setSVCSTATNM(String SVCSTATNM) {
        this.SVCSTATNM = SVCSTATNM;
    }

    public String getPAYATNM() {
        return PAYATNM;
    }

    public void setPAYATNM(String PAYATNM) {
        this.PAYATNM = PAYATNM;
    }

    public String getPLACENM() {
        return PLACENM;
    }

    public void setPLACENM(String PLACENM) {
        this.PLACENM = PLACENM;
    }

    public String getUSETGTINFO() {
        return USETGTINFO;
    }

    public void setUSETGTINFO(String USETGTINFO) {
        this.USETGTINFO = USETGTINFO;
    }

    public Item(String svcid, String svcnm, String maxclassnm, String minclassnm, String areanm, String svcstatnm, String payatnm, String placenm, String usetgtinfo)
    {
        this.SVCID = svcid;
        this.SVCNM = svcnm;
        this.MAXCLASSNM = maxclassnm;
        this.MINCLASSNM = minclassnm;
        this.AREANM = areanm;
        this.SVCSTATNM = svcstatnm;
        this.PAYATNM = payatnm;
        this.PLACENM = placenm;
        this.USETGTINFO = usetgtinfo;
    }

}
