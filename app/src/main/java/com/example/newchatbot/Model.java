package com.example.newchatbot;

public class Model {

    String Name;
    String Desc;
    String Price;
    String image;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    String email;

    public  Model(){

    }

    public Model(String name, String desc, String price, String image ,String email) {
        Name = name;
        Desc = desc;
        Price = price;
        this.image = image;
        this.email=email;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getDesc() {
        return Desc;
    }

    public void setDesc(String desc) {
        Desc = desc;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
