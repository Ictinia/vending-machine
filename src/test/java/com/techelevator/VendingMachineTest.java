package com.techelevator;

import org.junit.Assert;
import org.junit.Test;

import java.io.*;
import java.math.BigDecimal;
import java.util.Scanner;

public class VendingMachineTest {
    @Test
    public void does_file_exist() {
        //arrange
        final File file = new File("src\\main\\java\\com\\techelevator\\vendingmachine.csv");
        final VendingMachine vending = new VendingMachine();

        //assert
        Assert.assertTrue(file.exists());
    }

    @Test
    public void normal_item_constructor_test() {
        //arrange
        String position = "A1";
        String name = "Lays";
        BigDecimal price = new BigDecimal("1.75");
        Item.ItemType item = Item.ItemType.CHIP;
        final Item newItem = new Item("A1", "Lays", new BigDecimal("1.75"), Item.ItemType.CHIP);

        //assert
        Assert.assertEquals(position, newItem.getPosition());
        Assert.assertEquals(name, newItem.getName());
        Assert.assertEquals(price, newItem.getPrice());
        Assert.assertEquals(item, newItem.getItem());
    }

    @Test
    public void normal_item_constructor_toString_test() {
        //arrange
        final Item newItem = new Item("A1", "Lays", new BigDecimal("1.75"), Item.ItemType.CHIP);

        //assert
        Assert.assertEquals("A1 Lays1.75      5", newItem.toString());
    }

    @Test
    public void null_item_constructor_test() {
        //arrange
        final Item newItem = new Item(null, null, null, null);

        //assert
        Assert.assertNull(newItem.getPosition());
        Assert.assertNull(newItem.getName());
        Assert.assertNull(newItem.getPrice());
        Assert.assertNull(newItem.getItem());
    }
}
