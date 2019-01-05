package com.cryptography;

import com.google.common.collect.HashBiMap;
import java.util.ArrayList;

public class PlayFairCipher {
    private ArrayList<String> alphabets = new ArrayList<>();
    private ArrayList<Character> chars = new ArrayList<>();
    private ArrayList<Integer> indexes = new ArrayList<>();
    private Boolean isOdd = false;
    private Boolean shouldGenerateMatrix = true;
    private String key = "";
    private HashBiMap<String, String> biMatrixMap = HashBiMap.create();

    public void setKey(String key) {
        this.key = key;
    }

    public String encrypt(String plainText){
        if (shouldGenerateMatrix) generateMatrix(key);
        StringBuilder encryptedText = new StringBuilder();
        if (plainText.length() % 2 != 0){
            plainText = plainText + "k";
            isOdd  = true;
        }
        for(int i=0;i<plainText.length();i++){
            if (plainText.charAt(i) == alphabets.get(0).charAt(0)) indexes.add(i);
        }
        plainText = plainText.replaceAll(alphabets.get(0), biMatrixMap.get("44"));
        for(int i=0;i<plainText.length();i=i+2){
            String pair = plainText.substring(i, i+2);
            String loc1 = biMatrixMap.inverse().get(pair.substring(0,1));
            String loc2 = biMatrixMap.inverse().get(pair.substring(1));
            if (loc1.charAt(0) == loc2.charAt(0)){
                encryptedText.append(biMatrixMap.get(loc1.charAt(0) +
                        String.valueOf(modulo(Integer.parseInt(loc1.charAt(1)+"")+1))));
                encryptedText.append(biMatrixMap.get(loc2.charAt(0) +
                        String.valueOf(modulo(Integer.parseInt(loc2.charAt(1)+"")+1))));
            } else if (loc1.charAt(1) == loc2.charAt(1)){
                encryptedText.append(biMatrixMap.get(String.valueOf(modulo(Integer.parseInt(loc1.charAt(0)+"")+1)) +
                        loc1.charAt(1)));
                encryptedText.append(biMatrixMap.get(String.valueOf(modulo(Integer.parseInt(loc2.charAt(0)+"")+1)) +
                        loc2.charAt(1)));
            } else {
                encryptedText.append(biMatrixMap.get(loc1.substring(0,1) + loc2.substring(1)));
                encryptedText.append(biMatrixMap.get(loc2.substring(0,1) + loc1.substring(1)));
            }
        }
        return encryptedText.toString();
    }

    public String decrypt(String cipherText){
        StringBuilder plainText = new StringBuilder();
        for(int i=0;i<cipherText.length();i=i+2){
            String pair = cipherText.substring(i, i+2);
            String loc1 = biMatrixMap.inverse().get(pair.substring(0,1));
            String loc2 = biMatrixMap.inverse().get(pair.substring(1));
            if (loc1.charAt(0) == loc2.charAt(0)){
                plainText.append(biMatrixMap.get(loc1.charAt(0) +
                        String.valueOf(modulo(Integer.parseInt(loc1.charAt(1)+"") - 1))));
                plainText.append(biMatrixMap.get(loc2.charAt(0) +
                        String.valueOf(modulo(Integer.parseInt(loc2.charAt(1)+"") - 1))));
            } else if (loc1.charAt(1) == loc2.charAt(1)){
                plainText.append(biMatrixMap
                        .get(String.valueOf(modulo(Integer.parseInt(loc1.charAt(0)+"") - 1) + loc1.charAt(1))));
                plainText.append(biMatrixMap
                        .get(String.valueOf(modulo(Integer.parseInt(loc2.charAt(0)+"") - 1) + loc2.charAt(1))));
            } else {
                plainText.append(biMatrixMap.get(loc1.substring(0,1) + loc2.substring(1)));
                plainText.append(biMatrixMap.get(loc2.substring(0,1) + loc1.substring(1)));
            }
        }
        char[] plnText = plainText.toString().toCharArray();
        for(int i: indexes) plnText[i] = alphabets.get(0).charAt(0);
        String finalText = new String(plnText);
        if (isOdd) finalText = finalText.substring(0, finalText.length()-1);
        isOdd = false;
        return finalText;
    }

    private void generateMatrix(String key){
        int k=0;
        for (int i=0;i<key.length();i++) {
            if (!chars.contains(key.charAt(i))) chars.add(key.charAt(i));
        }
        for(int i=97;i<123;i++) {
            alphabets.add(String.valueOf((char)i));
        }
        for(int i=0;i<5;i++){
            for(int j=0;j<5;j++){
                if(k < chars.size()){
                    biMatrixMap.put((i+""+j), chars.get(k)+"");
                    k++;
                } else {
                    biMatrixMap.put((i+""+j), alphabets.get(0));
                }
                alphabets.remove(biMatrixMap.get(i+""+j));
            }
        }
        shouldGenerateMatrix = false;
    }

    private int modulo(int dividend){
        int mod = dividend % 5;
        return (mod < 0) ? mod +=5 : mod;
    }
}
