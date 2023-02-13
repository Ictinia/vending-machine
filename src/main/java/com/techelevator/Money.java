package com.techelevator;

import java.math.BigDecimal;

public class Money {
    private BigDecimal nickel = new BigDecimal("0.05");
    private BigDecimal dime = new BigDecimal("0.10");
    private BigDecimal quarter = new BigDecimal("0.25");

    public Integer[] changeAlgo(BigDecimal change) {
        Integer[] totalChange = new Integer[3];
        totalChange[0] = 0;
        totalChange[1] = 0;
        totalChange[2] = 0;

        if (change == null) {
            System.out.println("That's not right...");
            return totalChange;
        }

        int intChange = change.multiply(new BigDecimal("100")).intValue();

        while (intChange > 0) {
            if (intChange / 25 >= 1) {
                totalChange[0] = intChange / 25;
                intChange = intChange - (totalChange[0] * 25);
            } else if (intChange / 10 >= 1) {
                totalChange[1] = intChange / 10;
                intChange = intChange - (totalChange[1] * 10);
            } else if (intChange / 5 >= 1) {
                totalChange[2] = intChange / 5;
                intChange = intChange - (totalChange[2] * 5);
            } else {
                return totalChange;
            }
        }
        return totalChange;
    }

}
