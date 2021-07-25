/*
 *  UCF COP3330 Summer 2021 Assignment 5 Solution
 *  Copyright 2021 Tanushka Kommoju
 */

package ucf.assignments;

import java.io.Serializable;

public class Item  implements Serializable {

    String name;
    String serialNumber;
    Double price;

    public Item (String name, String serialNumber, Double price) { //stating task names
        this.name = name;
        this.serialNumber = serialNumber;
        this.price = price;
    }

    public Item() {
    }


    @Override
    public String toString(){
        return ("Price: " + price + " Serial Number: " + serialNumber + " Name: " + name);
    }

    public String getItemName() {
        return name;
    }

    public void setName(String name) {

        this.name = name;
    }

    public String getItemSerialNumber() {

        return serialNumber;
    }

    public void setItemSerialNumber(String serialNumber) {

        this.serialNumber = serialNumber;
    }

    public Double getItemPrice() {

        return price;
    }

    public Double setItemPrice(double v) {

        return price;
    }
}
