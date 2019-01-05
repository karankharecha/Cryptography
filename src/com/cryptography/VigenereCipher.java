package com.cryptography;

import com.cryptography.utils.Utils;

public class VigenereCipher extends Utils {

    public String encrypt(String target, String key){
        StringBuilder cipherText = new StringBuilder();
        for(int i=0;i<target.length();i++){
            int value = target.codePointAt(i);
            if (isAlphabet(value)){
                value = (value + key.codePointAt(i%(key.length())) - 194);
                if (value > 25) value %= 26;
                cipherText.append((char)(value+97));
            } else {
                cipherText.append((char)(value));
            }
        }
        return cipherText.toString();
    }

    public String decrypt(String target, String key){
        StringBuilder decryptedText = new StringBuilder();
        for(int i=0;i<target.length();i++){
            int value = target.codePointAt(i);
            if (isAlphabet(value)){
                value = (value - key.codePointAt(i%(key.length())));
                if (value < 0) value += 26;
                decryptedText.append((char)(value+97));
            } else {
                decryptedText.append((char)(value));
            }
        }
        return decryptedText.toString();
    }

}
