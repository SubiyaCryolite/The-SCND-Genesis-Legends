/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scndgen.legends;

import java.io.*;

/**
 * Contains language translations
 *
 * @author Ndana
 */
public final class Language {

    private static Language instance;
    private int lines, langz;
    private String langStr, start, stop, infile, s, t;
    private String[] text;
    private String[] fullyImplementedLanguages;
    private ClassLoader cl;
    private BufferedReader inR;
    private InputStream fin;
    private FileOutputStream fout;
    private byte[] b;
    private int noOfBytes;

    /**
     * Argument constructor
     *
     * @param lang
     */
    private Language(int lang) {
        writeLanguageFile();
        setLanguage(lang);
        prepLanguageList();
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
        try {
            return text[dex];
        } catch (Exception e) {
            return "No " + langStr + " translation for line " + dex;
        }
    }

    /**
     * Set language defined in save file
     *
     * @param l
     */
    public void setLanguage(int l) {
        infile = System.getProperty("user.home") + File.separator + ".config" + File.separator + "scndgen" + File.separator + "translations.xml";
        s = t = "";
        try {
            inR = new BufferedReader(new InputStreamReader(new FileInputStream(infile), "UTF8"));
            do {
                s = inR.readLine();
                if (s != null) {
                    t = t + s + "";
                }
            } while (s != null);
            inR.close();
        } catch (IOException e) {
            System.out.println(">>> loadsave: " + infile + " not found");
        }
        try {
            start = "<lines>";
            stop = "</lines>";
            lines = Integer.parseInt(t.substring(t.indexOf(start) + start.length(), t.indexOf(stop)));
            System.err.println("Lines of text:: " + lines);

            start = "<langz>";
            stop = "</langz>";
            langz = Integer.parseInt(t.substring(t.indexOf(start) + start.length(), t.indexOf(stop)));
            System.err.println("Languages:: " + langz);
            fullyImplementedLanguages = new String[langz];

            for (int i = 0; i < langz; i++) {
                start = "<lang" + i + ">";
                stop = "</lang" + i + ">";
                fullyImplementedLanguages[i] = t.substring(t.indexOf(start) + start.length(), t.indexOf(stop));
            }

            langStr = fullyImplementedLanguages[l];

            text = new String[lines];

            for (int i = 0; i < lines; i++) {
                start = "<" + langStr + i + ">";
                stop = "</" + langStr + i + ">";
                if (t.contains(start) && t.contains(stop)) {
                    text[i] = t.substring(t.indexOf(start) + start.length(), t.indexOf(stop));

                } else {
                    text[i] = "No " + langStr + " translation for line " + i;
                }
            }

        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
    }

    /**
     * Only add FULLY implemented languages to this array
     * The language shall be available in the option screen
     */
    public void prepLanguageList() {
        fullyImplementedLanguages = new String[langz];
    }

    /**
     * Get the fully supported languages
     *
     * @return languages
     */
    public String[] getSupportedLanguages() {
        return fullyImplementedLanguages;
    }

    private void writeLanguageFile() {
        try {
            cl = getClass().getClassLoader();
            fin = cl.getResourceAsStream("xml/translations.xml");
            fout = new FileOutputStream(System.getProperty("user.home") + File.separator + ".config" + File.separator + "scndgen" + File.separator + "translations.xml");
            b = new byte[1024];
            noOfBytes = 0;
            while ((noOfBytes = fin.read(b)) != -1) {
                fout.write(b, 0, noOfBytes);
            }
            fin.close();
            fout.close();
            System.out.println("Extracted lang file");

        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
    }
}
