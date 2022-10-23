package com.yellowstar.database;

public class Usermodel
{
    private  String Id;
    private  String userId;
    private  String Location;
    private  String State;
    private  String District;
    private  String Block;
    private  String Panchayat;
    private  String Ward;
    private  String Device;
    private  String Pole;
    private  String Latitude_Longitude;
    private  String DateTime;
    private  String Luminary;
    private  String Battery;
    private  String Panel;
    private  String ImgInfo;
    private  String Remark;
    private  byte[] Photo;

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public String getState() {
        return State;
    }

    public void setState(String state) {
        State = state;
    }

    public String getDistrict() {
        return District;
    }

    public void setDistrict(String district) {
        District = district;
    }

    public String getBlock() {
        return Block;
    }

    public void setBlock(String block) {
        Block = block;
    }

    public String getPanchayat() {
        return Panchayat;
    }

    public void setPanchayat(String panchayat) {
        Panchayat = panchayat;
    }

    public String getWard() {
        return Ward;
    }

    public void setWard(String ward) {
        Ward = ward;
    }

    public String getDevice() {
        return Device;
    }

    public void setDevice(String device) {
        Device = device;
    }

    public String getPole() {
        return Pole;
    }

    public void setPole(String pole) {
        Pole = pole;
    }

    public String getLatitude_Longitude() {
        return Latitude_Longitude;
    }

    public void setLatitude_Longitude(String latitude_Longitude) {
        Latitude_Longitude = latitude_Longitude;
    }

    public String getDateTime() {
        return DateTime;
    }

    public void setDateTime(String dateTime) {
        DateTime = dateTime;
    }

    public String getLuminary() {
        return Luminary;
    }

    public void setLuminary(String luminary) {
        Luminary = luminary;
    }

    public String getBattery() {
        return Battery;
    }

    public void setBattery(String battery) {
        Battery = battery;
    }

    public String getPanel() {
        return Panel;
    }

    public void setPanel(String panel) {
        Panel = panel;
    }

    public String getImgInfo() {
        return ImgInfo;
    }

    public void setImgInfo(String imgInfo) {
        ImgInfo = imgInfo;
    }

    public byte[] getPhoto() {
        return Photo;
    }

    public void setPhoto(byte[] photo) {
        Photo = photo;
    }

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String remark) {
        Remark = remark;
    }
}
