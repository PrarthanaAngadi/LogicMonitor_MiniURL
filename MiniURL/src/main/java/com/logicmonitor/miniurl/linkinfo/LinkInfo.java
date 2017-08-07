package com.logicmonitor.miniurl.linkinfo;

public class LinkInfo {

	private String shortUrl;
	private String longUrl;
	private boolean isBlackListed;
	
	
	
	/**
	 * @return the shortUrl
	 */
	public String getShortUrl() {
		return shortUrl;
	}
	/**
	 * @param shortUrl the shortUrl to set
	 */
	public void setShortUrl(String shortUrl) {
		this.shortUrl = shortUrl;
	}
	/**
	 * @return the longUrl
	 */
	public String getLongUrl() {
		return longUrl;
	}
	/**
	 * @param longUrl the longUrl to set
	 */
	public void setLongUrl(String longUrl) {
		this.longUrl = longUrl;
	}
	/**
	 * @return the isBlackListed
	 */
	public boolean isBlackListed() {
		return isBlackListed;
	}
	/**
	 * @param isBlackListed the isBlackListed to set
	 */
	public void setBlackListed(boolean isBlackListed) {
		this.isBlackListed = isBlackListed;
	}
	
	
	
	
	
	
}
