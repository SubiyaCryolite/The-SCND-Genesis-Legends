/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scndgen.legends;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;

/**
 * Contains language translations
 *
 * @author Ndana
 */
public final class Language {

    private static Language instance;
    private final HashMap<Integer, String> text = new HashMap<>();
    private final HashMap<Integer, String> languageImplementation = new HashMap<>();
    private final HashMap<Integer, String> fullyImplementedLanguages = new HashMap<>();
    private String currentLanguage;

    /**
     * Argument constructor
     *
     * @param lang
     */
    private Language(int lang) {
        //writeLanguageFile();
        languageImplementation.put(0, "translations/english.json");
        fullyImplementedLanguages.put(0, "English");
        setLanguage(lang);
    }

    public synchronized static Language getInstance() {
        if (instance == null)
            instance = new Language(0);
        return instance;
    }

    public synchronized static Language getInstance(int lang) {
        if (instance == null)
            instance = new Language(lang);
        return instance;
    }

    /**
     * Get a specific line
     *
     * @param dex - index of global text array
     * @return line of text
     */
    public String get(int dex) {
        return text.getOrDefault(dex, "No translation :: " + dex);
    }

    /**
     * Set language defined in save file
     *
     * @param index
     */
    public void setLanguage(int index) {
        text.clear();
        text.putAll(getTranslation(languageImplementation.get(index)));
        currentLanguage = fullyImplementedLanguages.get(index);
    }

    /**
     * Get the fully supported languages
     *
     * @return languages
     */
    public Collection<String> getSupportedLanguages() {
        return fullyImplementedLanguages.values();
    }

    public HashMap<Integer, String> getTranslation(String location) {
        ObjectMapper mapper = new ObjectMapper();
        HashMap<Integer, String> jsonMap = null;
        try {
            TypeReference<HashMap<Integer, String>> typeRef = new TypeReference<HashMap<Integer, String>>() {
            };
            jsonMap = mapper.readValue(Thread.currentThread().getContextClassLoader().getResourceAsStream(location), typeRef);
        } catch (IOException e) {
            e.printStackTrace(System.err);
        }
        return jsonMap;
    }
}
