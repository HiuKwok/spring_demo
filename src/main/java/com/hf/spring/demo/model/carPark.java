package com.hf.spring.demo.model;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class carPark {

    @Id
    private String id;
    private String name;
    private String nature;
    private String address;
    private String district;
    private Boolean evFriendly;

    public carPark() {
    }

    public carPark(String id, String name, String nature, String address, String district, Boolean evFriendly) {
        this.id = id;
        this.name = name;
        this.nature = nature;
        this.address = address;
        this.district = district;
        this.evFriendly = evFriendly;
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

    public String getNature() {
        return nature;
    }

    public void setNature(String nature) {
        this.nature = nature;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public Boolean getEvFriendly() {
        return evFriendly;
    }

    public void setEvFriendly(Boolean evFriendly) {
        this.evFriendly = evFriendly;
    }
}
