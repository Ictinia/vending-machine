package com.techelevator;

import java.math.BigDecimal;

public class Item {
    private String position;
    private String name;
    private BigDecimal price;
    private int quantity = 5;
    public enum ItemType {
        CHIP, CANDY, DRINK, GUM;
    }
    public ItemType item;
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String GREEN_BOLD_BRIGHT = "\033[1;92m"; // GREEN
    public static final String BLUE_BOLD_BRIGHT = "\033[1;94m";  // BLUE
    public static final String WHITE_BOLD_BRIGHT = "\033[1;97m"; // WHITE
    public static final String CYAN_BOLD_BRIGHT = "\033[1;96m";  // CYAN
    public static final String WHITE_BRIGHT = "\033[0;97m";  // WHITE

    public String getPosition() {
        return position;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }
    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    public ItemType getItem() {
        return item;
    }

    public Item(String position, String name, BigDecimal price, ItemType item) {
        this.position = position;
        this.name = name;
        this.price = price;
        this.item = item;
        this.quantity = getQuantity();
    }

    @Override
    public String toString() {
        return  (CYAN_BOLD_BRIGHT + position) + " " +
                (WHITE_BRIGHT + name) +
                (GREEN_BOLD_BRIGHT + price) +
                (BLUE_BOLD_BRIGHT + "      " + quantity);
    }
}
