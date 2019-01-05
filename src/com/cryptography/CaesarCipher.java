package com.cryptography;

import com.cryptography.utils.Utils;

public class CaesarCipher extends Utils {

    public String encrypt(String target, int key){
        key = key % 26;
        StringBuilder cipherText = new StringBuilder();
        for(int i=0;i<target.length();i++){
            int value = target.codePointAt(i);
            if (isAlphabet(value)){
                value = getValue(value + key, value, true);
            }
            cipherText.append((char)(value));
        }
        return cipherText.toString();
    }

    public String decrypt(String target, int key){
        key = key % 26;
        StringBuilder decryptedText = new StringBuilder();
        for(int i=0;i<target.length();i++){
            int value = target.codePointAt(i);
            if (isAlphabet(value)){
                value = getValue(value - key, value, false);
            }
            decryptedText.append((char)(value));
        }
        return decryptedText.toString();
    }

}
