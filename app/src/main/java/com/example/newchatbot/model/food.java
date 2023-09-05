package com.example.newchatbot.model;

public class food {
    String Desc;
    String Price;
    String Name;
    String image;
    String email;
    String FoodID;

    public food(String desc, String price, String name, String image, String email, String foodID) {
        Desc = desc;
        Price = price;
        Name = name;
        this.image = image;
        this.email = email;
        FoodID = foodID;
    }

    public String getFoodID() {
        return FoodID;
    }

    public void setFoodID(String foodID) {
        FoodID = foodID;
    }

    public food(){

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

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
