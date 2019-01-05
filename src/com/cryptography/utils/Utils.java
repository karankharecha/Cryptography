package com.cryptography.utils;

public class Utils {

    protected int getValue(int value, int code, boolean operation){
        return operation ?
                (value > 122 && code >= 97 && code <= 122
                        || value > 90 && code >= 65 && code <= 90) ?
                        value -= 26 : value :
                (value < 97 && code >= 97 && code <= 122
                        || value < 65 && code >= 65 && code <= 90) ?
                        value += 26 : value;

    }

    protected boolean isAlphabet(int value){
        return (value >=65 && value <= 90 || value >=97 && value <=122);
    }

}