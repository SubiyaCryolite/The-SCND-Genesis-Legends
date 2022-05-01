package com.scndgen.legends;

import static com.sun.javafx.tk.Toolkit.getToolkit;

public class Utils {
    public static float computeStringWidth(String string, javafx.scene.text.Font font){
        var fontLoader =  getToolkit().getFontLoader();
        float width = 0;
        for(var c : string.toCharArray()){
            width+= fontLoader.getCharWidth(c, font);
        }
        return width;
    }
}
