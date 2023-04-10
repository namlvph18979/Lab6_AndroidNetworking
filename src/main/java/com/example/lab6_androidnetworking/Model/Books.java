package com.example.lab6_androidnetworking.Model;

import java.io.Serializable;

public class Books implements Serializable {
    String ID,name,message,author,link;
    int price;

    public Books() {
    }

    public Books(String ID, String name, String message, String author, String link, int price) {
        this.ID = ID;
        this.name = name;
        this.message = message;
        this.author = author;
        this.link = link;
        this.price = price;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
