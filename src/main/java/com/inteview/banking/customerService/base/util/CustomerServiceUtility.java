package com.inteview.banking.customerService.base.util;

public class CustomerServiceUtility {

    public static String getAccountNumber() {
        return getRandomNumber(3);
    }

    public static String getCustomerId() {
        return getRandomNumber(2);
    }

    public static String getRandomNumber(int idSetCount) {
        int min = 1;
        int max = 9999;
        StringBuilder randomNumber = new StringBuilder();
        for (int i = 0; i < idSetCount; i++) {
            int randomInt = (int) (Math.random() * (max - min + 1) + min);
            randomNumber.append(String.format("%04d", randomInt));
        }
        System.out.println(randomNumber.toString());
        return randomNumber.toString();
    }
}
