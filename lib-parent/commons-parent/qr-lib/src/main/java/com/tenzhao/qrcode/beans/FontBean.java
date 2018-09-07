package com.tenzhao.qrcode.beans;

public class FontBean {
	private int x;
	private int y;
	private int fontSize;

	public FontBean(String txt, int width, int height, int imgHeight, int defaltHeight) {
		int txtLenth = txt.length();
		int fSize = (width - 10) / txtLenth;
		if (fSize >= 30)
			this.fontSize = 30;
		else {
			this.fontSize = fSize;
		}
		this.x = ((width - txtLenth * this.fontSize) / 2);
		this.y = ((height - width - 10 - this.fontSize) / 2 + defaltHeight);
	}

	public FontBean(int fontSize, int x, int defaltHeight) {
		this.fontSize = fontSize;
		this.x = x;
		this.y = defaltHeight;
	}

	public int getX() {
		return this.x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return this.y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getFontSize() {
		return this.fontSize;
	}

	public void setFontSize(int fontSize) {
		this.fontSize = fontSize;
	}
}