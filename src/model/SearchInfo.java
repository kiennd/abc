package model;

public class SearchInfo {
	private int type,count;
	private String keyword,language;
	private boolean translated;
	private boolean isRemoveUrl,isRemoveSpecial;
	private boolean isRemoveHtmlTag;
	
	
	
	public boolean isRemoveHtmlTag() {
		return isRemoveHtmlTag;
	}
	public void setRemoveHtmlTag(boolean isRemoveHtmlTag) {
		this.isRemoveHtmlTag = isRemoveHtmlTag;
	}
	public boolean isRemoveUrl() {
		return isRemoveUrl;
	}
	public void setRemoveUrl(boolean isRemoveUrl) {
		this.isRemoveUrl = isRemoveUrl;
	}
	public boolean isRemoveSpecial() {
		return isRemoveSpecial;
	}
	public void setRemoveSpecial(boolean isRemoveSpecial) {
		this.isRemoveSpecial = isRemoveSpecial;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public boolean isTranslated() {
		return translated;
	}
	public void setTranslated(boolean translated) {
		this.translated = translated;
	}
	
}
