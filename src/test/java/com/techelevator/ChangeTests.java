package com.techelevator;

import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.math.BigInteger;

public class ChangeTests {
    @Test
    public void change_given_1_35() {
        //arrange
        final BigDecimal input = new BigDecimal("1.35");
        final Money money = new Money();

        //act
        Integer[] changeArray = money.changeAlgo(input);

        //assert
        Assert.assertArrayEquals(new Integer[] {5, 1, 0}, changeArray);
    }

    @Test
    public void change_given_nothing() {
        //arrange
        final BigDecimal input = new BigDecimal("0.00");
        final Money money = new Money();

        //act
        Integer[] changeArray = money.changeAlgo(input);

        //assert
        Assert.assertArrayEquals(new Integer[] {0, 0, 0}, changeArray);
    }
    @Test
    public void change_given_null() {
        //arrange
        final BigDecimal input = new BigDecimal(BigInteger.ZERO);
        final Money money = new Money();

        //act
        Integer[] changeArray = money.changeAlgo(input);

        //assert
        Assert.assertArrayEquals(new Integer[] {0, 0, 0}, changeArray);
    }

}
