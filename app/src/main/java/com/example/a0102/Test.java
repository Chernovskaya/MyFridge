package com.example.a0102;

public class Test {
    public static void main(String[] args) {
        int number;
        int zero;
        try {
            number = 1;
            zero = 0;
            int result = number / zero;
            System.out.println("Message1");
        } catch (ArithmeticException e) {
            System.out.println("Message2");
        }
        System.out.printf("Message3");
    }
}
