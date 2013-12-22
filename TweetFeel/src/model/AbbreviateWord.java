/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;


public class AbbreviateWord implements Serializable{
	
    /**
	 * 
	 */
	private static final long serialVersionUID = -3775163698525275437L;
	private String word; // Tu nguyen goc, VD: Khong
    private String Abbreviate; //Tu viet tat, VD: Ko
    // -> Trong Comment Ko duoc thay the bang Khong
    
    
    public AbbreviateWord(String word, String Abbreviate) {
        this.word = word;
        this.Abbreviate = Abbreviate;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getAbbreviate() {
        return Abbreviate;
    }

    public void setAbbreviate(String Abbreviate) {
        this.Abbreviate = Abbreviate;
    }
    
}
