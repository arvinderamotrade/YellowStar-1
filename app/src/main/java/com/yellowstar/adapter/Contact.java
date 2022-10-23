package com.yellowstar.adapter;

public class Contact {

    private String Id;
    private String location;
    private String date;
    private String luminary;
    private String battery;
    private String panel;
    private byte[] photo;

    public Contact(String id, String location, String date, String luminary, String battery, String panel, byte[] photo)
    {
        Id = id;
        this.location = location;
        this.date = date;
        this.luminary = luminary;
        this.battery = battery;
        this.panel = panel;
        this.photo = photo;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLuminary() {
        return luminary;
    }

    public void setLuminary(String luminary) {
        this.luminary = luminary;
    }

    public String getBattery() {
        return battery;
    }

    public void setBattery(String battery) {
        this.battery = battery;
    }

    public String getPanel() {
        return panel;
    }

    public void setPanel(String panel) {
        this.panel = panel;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }
}