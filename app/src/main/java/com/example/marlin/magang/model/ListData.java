package com.example.marlin.magang.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "ListtData")
public class ListData {
    @PrimaryKey
    @NonNull
    private String id;
    private String trayek_id;
    private String status;
    private String image;

    public ListData (String id, String trayek_id, String status, String image) {
        this.id = id;
        this.trayek_id = trayek_id;
        this.status = status;
        this.image = image;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getTrayek_id() {
        return trayek_id;
    }
    public void setTrayek_id(String trayek_id) {
        this.trayek_id = trayek_id;
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
