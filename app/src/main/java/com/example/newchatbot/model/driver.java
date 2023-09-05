package com.example.newchatbot.model;

public class driver {
    String Desc;
    String Name;

    public String getDriverID() {
        return DriverID;
    }

    public void setDriverID(String driverID) {
        DriverID = driverID;
    }

    String DriverID;
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    String Phone;
    String image;
    String ResturantID;
    String status;
    public String getResturantID() {
        return ResturantID;
    }

    public void setResturantID(String resturantID) {
        ResturantID = resturantID;
    }

    public driver(String desc, String name, String phone, String image, String resturantID, String status) {
        Desc = desc;
        Name = name;
        Phone = phone;
        this.image = image;
        ResturantID = resturantID;
        this.status = status;
    }

    public driver(){}

    public String getDesc() {
        return Desc;
    }

    public void setDesc(String desc) {
        Desc = desc;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
