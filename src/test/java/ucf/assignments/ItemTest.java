package ucf.assignments;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


class ItemTest {

    public static Item item = new Item();


    @Test
    void getItemName() {

        String test = "test"; //get an item name based on test
        item.setName(test);
        String itemDescription = item.getItemName(); //compare test and item description
        Assertions.assertEquals(test, itemDescription);
    }

    @Test
    void setItemName() {

        String test = "test"; //set an item based on test
        item.setName(test);
        String itemName = item.getItemName(); //compare test and item names
        Assertions.assertEquals(test, itemName);
    }

    @Test
    void getItemSerialNumber() {


        String testSerialNumber = "0123456789";
        item.setItemSerialNumber(testSerialNumber); //set serial number
        String itemSerialNumber = item.getItemSerialNumber(); //get the serial number
        Assertions.assertEquals(testSerialNumber, itemSerialNumber); //compare
    }

    @Test
    void setItemSerialNumber() {
        //same process as getSerialnumber

        String testSerialNumber = "0123456789";
        item.setItemSerialNumber(testSerialNumber);
        String itemSerialNumber = item.getItemSerialNumber();
        Assertions.assertEquals(testSerialNumber, itemSerialNumber);
    }

    @Test
    void getItemPrice() {


        double testValue = 10.567; //set a test price
        item.setItemPrice(testValue);
        double actualValue = item.getItemPrice();
        Assertions.assertEquals(actualValue, testValue); //compare both prices

    }

    @Test
    void setItemPrice() {

        //same process as getPrice
        double testValue = 10.567;
        item.setItemPrice(testValue);
        double actualValue = item.getItemPrice();
        Assertions.assertEquals(actualValue, testValue);

    }

    @Test
    void testEquals() {

        //returns false
        boolean test = equals(item);
    }


    @Test
    void testToString() {

        // "Name: " + itemName + "\n" + "Serial Number: " + itemSerialNumber + "\n" + "Price: " + "$" + itemPrice;

        String itemName = "test";
        String itemSerialNumber = "0123456789";
        double itemPrice = 10.567;

        item.setName(itemName);
        item.setItemSerialNumber(itemSerialNumber);
        item.setItemPrice(itemPrice);

        String test1 = "Name: " + itemName + "\n" + "Serial Number: " + itemSerialNumber +
                "\n" + "Price: " + "$" + itemPrice;

        Assertions.assertEquals(test1, item.toString());


    }
}