package com.cryptography;

import com.cryptography.utils.Utils;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class ColumnarCipher extends Utils {

    private HashMap<Integer, StringBuilder> matrixMap = new HashMap<>();
    private ArrayList<Integer> keys = new ArrayList<>();

    public String encrypt(String plnText, String key){
        if ((plnText == null) || (plnText.isEmpty())) return "";
        int idx = 0;
        keys.clear();
        matrixMap.clear();
        for(char c: key.toCharArray()){
            keys.add((int)c);
        }
        for(char c: plnText.toCharArray()){
            if (idx >= keys.size()) idx = 0;
            if (matrixMap.containsKey(keys.get(idx))){
                matrixMap.replace(keys.get(idx), matrixMap.get(keys.get(idx)).append(c));
            } else {
                matrixMap.put(keys.get(idx), new StringBuilder(c + ""));
            }
            idx++;
        }
        ArrayList<Integer> sortedKeys = new ArrayList<>(keys);
        Collections.sort(sortedKeys);
        StringBuilder cipherText = new StringBuilder();
        for (int sKey: sortedKeys){
            cipherText.append(matrixMap.get(sKey));
        }

        return cipherText.toString();
    }

    public String decrypt(String encryptedStr){
        if ((encryptedStr == null) || (encryptedStr.isEmpty())) return "";
        int idx = 0;
        StringBuilder originalText = new StringBuilder();
        for(int k=0; k<encryptedStr.length(); k++){
            if (idx >= keys.size()) idx = 0;
            StringBuilder cSequence = matrixMap.get(keys.get(idx));
            if (cSequence.length() > 0){
                originalText.append(cSequence.charAt(0));
                cSequence.deleteCharAt(0);
            }
            idx++;
        }
        return originalText.toString();
    }

}
