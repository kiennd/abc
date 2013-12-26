/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;


public class AbbreviationWord implements Serializable{
	
    /**
	 * 
	 */
	private static final long serialVersionUID = -3775163698525275437L;
	private String original; // Tu nguyen goc, VD: Khong
    private String Abbreviation; //Tu viet tat, VD: Ko
    // -> Trong Comment Ko duoc thay the bang Khong
	public String getOriginal() {
		return original;
	}
	public void setOriginal(String original) {
		this.original = original;
	}
	public String getAbbreviation() {
		return Abbreviation;
	}
	public void setAbbreviation(String abbreviation) {
		Abbreviation = abbreviation;
	}
    
    
    
}
