/**************************************************************************

 The SCND Genesis: Legends is a fighting game based on THE SCND GENESIS,
 a webcomic created by Ifunga Ndana ((([http://www.scndgen.com]))).

 The SCND Genesis: Legends RMX  © 2017 Ifunga Ndana.

 The SCND Genesis: Legends is free software: you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation, either version 3 of the License, or
 (at your option) any later version.

 The SCND Genesis: Legends is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with The SCND Genesis: Legends. If not, see <http://www.gnu.org/licenses/>.

 **************************************************************************/
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

    public synchronized static Language get() {
        if (instance == null)
            instance = new Language(0);
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
