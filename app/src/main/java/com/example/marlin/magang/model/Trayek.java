package com.example.marlin.magang.model;

public class Trayek {
    public Trayek(String nama, String trayek_id, String company_id) {
        Nama = nama;
        Trayek_id = trayek_id;
        Company_id = company_id;
    }

    public String getNama() {
        return Nama;
    }

    public void setNama(String nama) {
        Nama = nama;
    }

    public String getTrayek_id() {
        return Trayek_id;
    }

    public void setTrayek_id(String trayek_id) {
        Trayek_id = trayek_id;
    }

    public String getCompany_id() {
        return Company_id;
    }

    public void setCompany_id(String company_id) {
        Company_id = company_id;
    }

    String Nama;
    String Trayek_id;
    String Company_id;

}
