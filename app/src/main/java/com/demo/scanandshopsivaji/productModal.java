package com.demo.scanandshopsivaji;

import java.io.Serializable;

public class productModal implements Serializable {

    String name,price,image,discount,total;

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }


    public productModal()
    {

    }
    public productModal(String name, String price,String image,String total,String discount) {
        this.name = name;
        this.price = price;
        this.image =image;
        this.discount=discount;
        this.total=total;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }




}
