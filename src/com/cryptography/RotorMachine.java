package com.cryptography;

import com.google.common.collect.HashBiMap;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;

public class RotorMachine {

    private static ArrayList<Character> characterList;
    private static HashMap<String, String> characterMap;
    private ArrayList<ArrayList<Character>> rotors = new ArrayList<>();
    ArrayList<Character> rotorKey1 = new ArrayList<>();
    ArrayList<Character> rotorKey2 = new ArrayList<>();
    ArrayList<Character> rotorKey3 = new ArrayList<>();
    private ArrayList<Integer> resetIdx;
//    private ArrayList<ArrayList<Character>> operationalRotors;

    public RotorMachine() {
        characterMap = new HashMap<>();
        for (int i = 32; i < 127; i++) {
            char c = (char) i;
            characterMap.put(c + "", (i - 32) + "");
            characterMap.put((i - 32) + "", c + "");
            rotorKey1.add(c);
            rotorKey2.add(c);
            rotorKey3.add(c);
        }
        System.out.println("superset: \n" + characterMap);
    }

    public ArrayList<ArrayList<Character>> generateKeys() {
        Collections.shuffle(rotorKey1);
        Collections.shuffle(rotorKey2);
        Collections.shuffle(rotorKey3);
        rotors.add(rotorKey1);
        rotors.add(rotorKey2);
        rotors.add(rotorKey3);
        resetIdx = new ArrayList<>(Collections.nCopies(rotors.size(), 0));
        return new ArrayList<>() {{
            addAll(rotors);
        }};
    }

    public void setKeys(ArrayList<ArrayList<Character>> keys) {
        rotors = new ArrayList<>();
        rotors.addAll(keys);
        resetIdx = new ArrayList<>(Collections.nCopies(rotors.size(), 0));
    }

    public String encrypt(String plnText) {
        int rotorCounter = 1;
        StringBuilder cipherTextBuilder = new StringBuilder();
        for (int i = 0; i < plnText.length(); i++) {
            char targetChar = plnText.charAt(i);
            for (int k = 0; k < rotors.size(); k++) {
                String idx = characterMap.get(targetChar + "");
                targetChar = rotors.get(k).get(Integer.parseInt(idx));
            }
            cipherTextBuilder.append(targetChar);
            if (i >= Math.pow(95, rotorCounter)) rotorCounter++;
            rotate(i, rotorCounter, 0);
        }
        resetRotors(false);
        return cipherTextBuilder.toString();
    }

    public String decrypt(String cipherText) {
        Collections.reverse(rotors);
        int rotorCounter = 1;
        StringBuilder plnTextBuilder = new StringBuilder();
        for (int i = 0; i < cipherText.length(); i++) {
            char targetChar = cipherText.charAt(i);
            for (int k = 0; k < rotors.size(); k++) {
                int idx = rotors.get(k).indexOf(targetChar);
                targetChar = characterMap.get(idx + "").charAt(0);
            }
            plnTextBuilder.append(targetChar);
            if (i >= Math.pow(95, rotorCounter)) rotorCounter++;
            rotate(i, rotorCounter, 1);
        }
        resetRotors(true);

        return plnTextBuilder.toString();
    }

    private void resetRotors(boolean performReverse) {
        System.out.println();
        System.out.println();
        if (performReverse) Collections.reverse(rotors);
        for (int i = 0; i < rotors.size(); i++) {
            Collections.rotate(rotors.get(rotors.size() - (i + 1)), -resetIdx.get(i));
        }
    }

    private void rotate(int idx, int rCounter, int mode) {
        rCounter = rCounter % rotors.size();
        switch (mode) {
            case 0:
                for (int i = 0; i < rCounter; i++) {
                    if (idx % Math.pow(95, i) == 0) {
                        Collections.rotate(rotors.get(rotors.size() - (i + 1)), 1);
                        resetIdx.set(i, (resetIdx.get(i) + 1) % 95);
                    }
                }
                break;
            case 1:
                for (int i = 0; i < rCounter; i++) {
                    if (idx % Math.pow(95, i) == 0) {
                        Collections.rotate(rotors.get(i), 1);
                    }
                }
                break;
        }
    }
}
