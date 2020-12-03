package com.demo.mobile_test_droid.pojo;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;
import com.demo.mobile_test_droid.converters.Converter;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "contacts")
@TypeConverters(value = Converter.class)
public class Contacts {
    @PrimaryKey(autoGenerate = true)
    private int idTable;

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("height")
    @Expose
    private double height;
    @SerializedName("biography")
    @Expose
    private String biography;
    @SerializedName("temperament")
    @Expose
    private String temperament;

    @SerializedName("educationPeriod")
    @Expose
    private EducationPeriod educationPeriod;

    public Contacts(String id, String name, String phone, double height, String biography, String temperament) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.height = height;
        this.biography = biography;
        this.temperament = temperament;
    }

    public int getIdTable() {
        return idTable;
    }

    public void setIdTable(int idTable) {
        this.idTable = idTable;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    public String getTemperament() {
        return temperament;
    }

    public void setTemperament(String temperament) {
        this.temperament = temperament;
    }

    public EducationPeriod getEducationPeriod() {
        return educationPeriod;
    }

    public void setEducationPeriod(EducationPeriod educationPeriod) {
        this.educationPeriod = educationPeriod;
    }
}
