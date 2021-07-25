package ucf.assignments;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


class ItemTest {

    public static Item item = new Item();


    @Test
    void getItemName() {

        String test = "test item"; //get an item name based on test
        item.setName(test);
        String itemDescription = item.getItemName(); //compare test and item description
        Assertions.assertTrue(test.equals(itemDescription));
    }

    @Test
    void setItemName() {

        String test = "test item"; //set an item based on test
        item.setName(test);
        String itemName = item.getItemName(); //compare test and item names
        Assertions.assertTrue(test.equals(itemName));
    }

    @Test
    void getItemSerialNumber() {


        String testSerialNumber = "0123456789";
        item.setItemSerialNumber(testSerialNumber); //set serial number
        String itemSerialNumber = item.getItemSerialNumber(); //get the serial number
        Assertions.assertTrue(testSerialNumber.equals(itemSerialNumber)); //compare
    }

    @Test
    void setItemSerialNumber() {
        //same process as getserialnumber

        String testSerialNumber = "0123456789";
        item.setItemSerialNumber(testSerialNumber);
        String itemSerialNumber = item.getItemSerialNumber();
        Assertions.assertTrue(testSerialNumber.equals(itemSerialNumber));
    }

    @Test
    void getItemPrice() {

        // getting an item value based on a item value
        // I expect my item value will be the same as item value
        // I first set the item value  via setValue method, then
        // get the item value via item's getvalue  method
        // compare if test value and item value  are equal

        double testValue = 10.567; //set a test price
        item.setItemPrice(testValue);
        double actualValue = item.getItemPrice();
        Assertions.assertTrue(actualValue == testValue); //compare both prices

    }

    @Test
    void setItemPrice() {

        //same process as getPrice
        double testValue = 10.567;
        item.setItemPrice(testValue);
        double actualValue = item.getItemPrice();
        Assertions.assertTrue(actualValue == testValue);

    }

    @Test
    void testEquals() {

        //returns false
        boolean test = equals(item);
    }


    @Test
    void testToString() {

        // "Name: " + itemName + "\n" + "Serial Number: " + itemSerialNumber + "\n" + "Value: " + "$" + itemValue;

        String itemName = "test";
        String itemSerialNumber = "0123456789";
        double itemValue = 50.50;

        item.setName(itemName);
        item.setItemSerialNumber(itemSerialNumber);
        item.setItemPrice(itemValue);

        String test1 = "Name: " + itemName + "\n" + "Serial Number: " + itemSerialNumber +
                "\n" + "Value: " + "$" + itemValue;

        Assertions.assertTrue(test1.equals(item.toString()));


    }
}