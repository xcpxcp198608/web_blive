package com.wiatec.blive.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;

/**
 * @author patrick
 */
public class BigDecimalUtil {

    private final Logger logger = LoggerFactory.getLogger(BigDecimalUtil.class);

    public static BigDecimal add(double v1, double v2){
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.add(b2);
    }

    public static BigDecimal subtract(double v1, double v2){
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.subtract(b2);
    }

    public static BigDecimal multiply(double v1, double v2){
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.multiply(b2);
    }

    public static BigDecimal divide(double v1, double v2){
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        //四舍五入， 2位小数
        return b1.divide(b2, 2, BigDecimal.ROUND_HALF_UP);
    }

    public static void main (String [] args){

        BigDecimal add = add(0.28, 0.34);
        System.out.println(add);

        BigDecimal subtract = subtract(0.28, 0.34);
        System.out.println(subtract);

        BigDecimal multiply = multiply(0.28, 0.34);
        System.out.println(multiply);

        BigDecimal divide = divide(0.281, 0.34);
        System.out.println(divide);

        BigDecimal scale = multiply.setScale(2, BigDecimal.ROUND_HALF_UP);
        System.out.println(scale);
    }


}
