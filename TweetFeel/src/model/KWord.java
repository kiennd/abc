package model;

import java.io.Serializable;

public class KWord implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1993459343275748047L;
	private String word,type;
	private int countneg,countpos;
	private int total; // sum of countneg and countpos
	private String label;
	
	
	
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public String getWord() {
		return word;
	}
	public void setWord(String word) {
		this.word = word;
	}
	public int getCountneg() {
		return countneg;
	}
	public void setCountneg(int countneg) {
		this.countneg = countneg;
	}
	public int getCountpos() {
		return countpos;
	}
	public void setCountpos(int countpos) {
		this.countpos = countpos;
	}
	public void increasePos(){
		this.countpos++;
	}
	public void increaseNeg(){
		this.countneg++;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	
}
