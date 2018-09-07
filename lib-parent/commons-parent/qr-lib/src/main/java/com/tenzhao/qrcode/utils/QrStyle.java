package com.tenzhao.qrcode.utils;

import com.google.zxing.client.j2se.MatrixToImageConfig;
/**
 * 二维码的样式
 * @author chenxj
 */
public class QrStyle {
	
	/**
	 * 此处不用默认黑色(0xFF000000),解决zxing二维码中间带logo时,BufferedImage.TYPE_BYTE_BINARY类型,即,只有黑白二色的位图
	 */
	private  int onColor = 0xFF000001;//
	private  int offColor  = MatrixToImageConfig.WHITE;
	private int width = 300 ;
	private int margin = 2;
	private int height = 300 ;
	private String borderTopTxt ;
	private String borderBottomTxt ;
	/** 获取二维码高度样式 */
	public int getHeight() {
		return height;
	}
	/** 设置二维码高度样式 */
	public QrStyle setHeight(int height) {
		this.height = height;
		return this;
	}
	
	/** 获取二维码宽度样式 */
	public int getWidth() {
		return width;
	}
	/** 设置二维码宽度样式 */
	public QrStyle setWidth(int width) {
		this.width = width;
		return this;
	}
	/** 获取二维码边框空白样式 */
	public int getMargin() {
		return margin;
	}
	/** 设置二维码边框空白样式 */
	public QrStyle setMargin(int margin) {
		this.margin = margin;
		return this;
	}

	public QrStyle(){}
	public QrStyle(int height,int width){
		this( height,width,0);
	}
	public QrStyle(int height,int width,int margin){
		this.height = height ;
		this.width = width ;
		if(margin >= 0){
			this.margin = margin ;
		}
	}
	/** 二维码 前景色(十六进制)*/
	public int getOnColor() {
		return onColor;
	}
	/** 二维码 前景色(十六进制)*/
	public QrStyle setOnColor(int onColor) {
		this.onColor = onColor;
		return this;
	}
	/** 二维码背景色(十六进制)*/
	public int getOffColor() {
		return offColor;
	}
	/** 二维码背景色(十六进制)*/
	public QrStyle setOffColor(int offColor) {
		this.offColor = offColor;
		return this;
	}
	
	/** 获取上边框空白处文字 */
	public String getBorderTopTxt() {
		return borderTopTxt;
	}
	/** 设置二维码上边框空白处文字 */
	public QrStyle setBorderTopTxt(String borderTopTxt) {
		this.borderTopTxt = borderTopTxt;
		return this ;
	}
	
	/** 获取下边框空白处文字 */
	public String getBorderBottomTxt() {
		return borderBottomTxt;
	}
	/** 设置二维码下边框空白处文字 */
	public QrStyle setBorderBottomTxt(String borderBottomTxt) {
		this.borderBottomTxt = borderBottomTxt;
		return this ;
	};
}
