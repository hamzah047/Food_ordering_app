package com.example.newchatbot.model;



public class Order {
    String FoodName;
    String Quantity;
    String size;
    String image;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    String Useremail;
    String Resturantemail;
    String devliberyTime;
    String status;
    String price;
    String TotalPrice;
    String deliveryCharges;
    String orderID;
    String rating;
    String RiderId;

    public String getRiderId() {
        return RiderId;
    }

    public void setRiderId(String riderId) {
        RiderId = riderId;
    }

    String address;
    String extra;
    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getTotalPrice() {
        return TotalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        TotalPrice = totalPrice;
    }

    public String getDeliveryCharges() {
        return deliveryCharges;
    }

    public void setDeliveryCharges(String deliveryCharges) {
        this.deliveryCharges = deliveryCharges;
    }

    public Order(){

    }
    public String getFoodName() {
        return FoodName;
    }

    public void setFoodName(String foodName) {
        FoodName = foodName;
    }

    public String getQuantity() {
        return Quantity;
    }

    public void setQuantity(String quantity) {
        Quantity = quantity;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getUseremail() {
        return Useremail;
    }

    public void setUseremail(String useremail) {
        Useremail = useremail;
    }

    public String getResturantemail() {
        return Resturantemail;
    }

    public void setResturantemail(String resturantemail) {
        Resturantemail = resturantemail;
    }

    public String getDevliberyTime() {
        return devliberyTime;
    }

    public void setDevliberyTime(String devliberyTime) {
        this.devliberyTime = devliberyTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Order(String foodName, String quantity, String size, String useremail, String resturantemail, String devliberyTime, String status, String price, String totalPrice, String deliveryCharges, String orderID, String rating, String address, String extra) {
        FoodName = foodName;
        Quantity = quantity;
        this.size = size;
        Useremail = useremail;
        Resturantemail = resturantemail;
        this.devliberyTime = devliberyTime;
        this.status = status;
        this.price = price;
        TotalPrice = totalPrice;
        this.deliveryCharges = deliveryCharges;
        this.orderID = orderID;
        this.rating = rating;
        this.address = address;
        this.extra = extra;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }
}
