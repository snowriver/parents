package com.tenzhao.qrcode.utils;

import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

public class QRCodeParams {
	private String txt;
	private String qrCodeUrl;
	private String filePath;
	private String fileName;
	private String logoPath;
	private Integer width = Integer.valueOf(300);
	private Integer height = Integer.valueOf(300);
	private Integer onColor = Integer.valueOf(-16777216);
	private Integer offColor = Integer.valueOf(-1);
	private Integer margin = Integer.valueOf(1);
	private ErrorCorrectionLevel level = ErrorCorrectionLevel.L;
	private boolean isExpImg = false;
	private String title = "欢迎使用助商通";
	private String description = "扫一扫二维码注册，领取29元话费";
	private String advertiserName;

	public String getTxt() {
		return this.txt;
	}

	public void setTxt(String txt) {
		this.txt = txt;
	}

	public String getFilePath() {
		return this.filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getFileName() {
		return this.fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public Integer getWidth() {
		return this.width;
	}

	public void setWidth(Integer width) {
		this.width = width;
	}

	public Integer getHeight() {
		return this.height;
	}

	public void setHeight(Integer height) {
		this.height = height;
	}

	public String getQrCodeUrl() {
		return this.qrCodeUrl;
	}

	public void setQrCodeUrl(String qrCodeUrl) {
		this.qrCodeUrl = qrCodeUrl;
	}

	public String getLogoPath() {
		return this.logoPath;
	}

	public void setLogoPath(String logoPath) {
		this.logoPath = logoPath;
	}

	public Integer getOnColor() {
		return this.onColor;
	}

	public void setOnColor(Integer onColor) {
		this.onColor = onColor;
	}

	public Integer getOffColor() {
		return this.offColor;
	}

	public void setOffColor(Integer offColor) {
		this.offColor = offColor;
	}

	public ErrorCorrectionLevel getLevel() {
		return this.level;
	}

	public void setLevel(ErrorCorrectionLevel level) {
		this.level = level;
	}

	public String getSuffixName() {
		String imgName = getFileName();
		if ((imgName != null) && (!"".equals(imgName))) {
			String suffix = this.fileName.substring(this.fileName.lastIndexOf(".") + 1);
			return suffix;
		}
		return "";
	}

	public Integer getMargin() {
		return this.margin;
	}

	public void setMargin(Integer margin) {
		this.margin = margin;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isExpImg() {
		return this.isExpImg;
	}

	public void setExpImg(boolean isExpImg) {
		this.isExpImg = isExpImg;
	}

	public String getAdvertiserName() {
		return this.advertiserName;
	}

	public void setAdvertiserName(String advertiserName) {
		this.advertiserName = advertiserName;
	}
}