package com.example.marlin.magang.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "ListtData")
public class ListData {
    @PrimaryKey
    @NonNull
    private String id;
    private String trayek_nama;
    private String status;
    private String image;

    public ListData (String id, String trayek_nama, String status, String image) {
        this.id = id;
        this.trayek_nama = trayek_nama;
        this.status = status;
        this.image = image;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getTrayek_nama() {
        return trayek_nama;
    }
    public void setTrayek_nama(String trayek_id) {
        this.trayek_nama = trayek_nama;
    }
    public String getStatus(){
        return status;
    }
    public void setStatus(String status){
        this.status = status;
    }
    public String getImage(){
        return image;
    }
    public void setImage(String image){
        this.image = image;
    }

}
