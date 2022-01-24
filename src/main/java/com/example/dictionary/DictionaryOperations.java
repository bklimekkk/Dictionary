package com.example.dictionary;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Hashtable;

public class DictionaryOperations {
    private static Hashtable<String, String> languagesMap;

    public static Hashtable<String, String> getEnglishToPolish() {
        return languagesMap;
    }

    public static void setEnglishToPolish(Hashtable<String, String> englishToPolish) {
        DictionaryOperations.languagesMap = englishToPolish;
    }
    public static Hashtable<String, String> loadEnglishToPolish(String firstName, String secondName) throws IOException {
        languagesMap = new Hashtable<>();
        BufferedReader englishBufferedReader = Files.newBufferedReader(Path.of(firstName));
        BufferedReader polishBufferedReader = Files.newBufferedReader(Path.of(secondName));

        String englishInput;
        String polishInput;
        while((englishInput = englishBufferedReader.readLine()) != null && (polishInput = polishBufferedReader.readLine()) != null) {
            languagesMap.put(englishInput.toUpperCase(),polishInput.toUpperCase());
        }
        return languagesMap;
    }
}
