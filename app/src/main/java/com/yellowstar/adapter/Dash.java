package com.yellowstar.adapter;

public class Dash
{
    private String Id;
    private String name;
    private String desc;
    private String photo;

    public Dash(String id, String name, String desc, String photo) {
        Id = id;
        this.name = name;
        this.desc = desc;
        this.photo = photo;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}
