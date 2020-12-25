package com.aildemo.ailibrary;

public class Student {
    private  String SPINNER1;
    private String ID;
    private  String NAME;
    private String PNAME;
    private String MOBILE;
    private String ADDMISSION;
    private String ROLLNO;

    public Student() {
    }

    public Student(String SPINNER1, String ID, String NAME, String PNAME, String MOBILE, String ADDMISSION, String ROLLNO) {
        this.SPINNER1 = SPINNER1;
        this.ID = ID;
        this.NAME = NAME;
        this.PNAME = PNAME;
        this.MOBILE = MOBILE;
        this.ADDMISSION = ADDMISSION;
        this.ROLLNO = ROLLNO;
    }

    public String getSPINNER1() {
        return SPINNER1;
    }

    public void setSPINNER1(String SPINNER1) {
        this.SPINNER1 = SPINNER1;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getNAME() {
        return NAME;
    }

    public void setNAME(String NAME) {
        this.NAME = NAME;
    }

    public String getPNAME() {
        return PNAME;
    }

    public void setPNAME(String PNAME) {
        this.PNAME = PNAME;
    }

    public String getMOBILE() {
        return MOBILE;
    }

    public void setMOBILE(String MOBILE) {
        this.MOBILE = MOBILE;
    }

    public String getADDMISSION() {
        return ADDMISSION;
    }

    public void setADDMISSION(String ADDMISSION) {
        this.ADDMISSION = ADDMISSION;
    }

    public String getROLLNO() {
        return ROLLNO;
    }

    public void setROLLNO(String ROLLNO) {
        this.ROLLNO = ROLLNO;
    }
}
