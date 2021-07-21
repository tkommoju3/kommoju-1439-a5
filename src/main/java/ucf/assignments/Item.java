package ucf.assignments;

import java.io.Serializable;

public class Item  implements Serializable {

    String name;
    String serialNumber;
    Double price;

    public Item(String name, String serialNumber, Double price) { //stating task names
        this.name = name;
        this.serialNumber = serialNumber;
        price = price;
    }
//pseudocode
    public String getName() { 
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        price = price;
    }
}