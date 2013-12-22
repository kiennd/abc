package controller;

import java.net.URLEncoder;

public class Translator {
    public static String toVietnamese(String s){
        if (s.equals("") || s == null) {
            return "";
        }
        try {         
            s = URLEncoder.encode(s, "UTF-8");
            com.gtranslate.Translator translate = com.gtranslate.Translator.getInstance();
            return translate.translate(s, "auto", "vi");
        } catch (Exception ex) {
            System.out.println("Loi encoder" + ex);
        }
        return s;
    }
    
    
    
}
