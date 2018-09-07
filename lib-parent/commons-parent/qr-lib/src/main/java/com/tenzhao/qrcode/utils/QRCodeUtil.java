package com.tenzhao.qrcode.utils;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.EncodeHintType;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.Result;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageConfig;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.tenzhao.qrcode.beans.FontBean;
import com.tenzhao.qrcode.exception.QRParamsException;

public class QRCodeUtil {
	private static int width = 300;
	private static int height = 300;
	private static int onColor  = 0xFF000001;
	private static int offColor = 0xFFFFFFFF;
	private static int margin = 3;
	private static ErrorCorrectionLevel level = ErrorCorrectionLevel.L;
	private static String welconZstTxt = "欢迎使用助商通";
	private static int imgY = 10;
	private static boolean isExp = false;
	private static String charset = "UTF-8" ;
	/**
	 * 
	 * @param content 二维码中文本内容
	 * @param logo 中间的logo
	 * @throws WriterException 
	 */
	public static InputStream createQrCode(String content,InputStream logo,QrStyle style) throws Exception{
		InputStream inputStream = null ;
		Map<EncodeHintType,Object> hints = new HashMap<EncodeHintType,Object>();
		hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
		hints.put(EncodeHintType.CHARACTER_SET, charset);
		hints.put(EncodeHintType.MARGIN, style.getMargin());
		MatrixToImageConfig config = new MatrixToImageConfig(style.getOnColor(), style.getOffColor());
		BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, style.getWidth(), style.getHeight(), hints);
		BufferedImage qrImg = MatrixToImageWriter.toBufferedImage(bitMatrix,config);
		appendLogo(qrImg,logo);
		
//		if((style.getBorderTopTxt() != null && !"".equals(style.getBorderTopTxt()))
//				|| (style.getBorderBottomTxt() != null && !"".equals(style.getBorderBottomTxt()))){
//			qrImg = appendBorderTxt(qrImg,style);
//		}
		ByteArrayOutputStream os = new ByteArrayOutputStream();  
		ImageIO.write(qrImg, "png", os);  
		inputStream = new ByteArrayInputStream(os.toByteArray()); 
		return inputStream ;
	
	}
	
	public static InputStream createQrCode(String content,InputStream logo,QrStyle style,FontBean borderTopTxtFont,Font borderBottomTxtFont) throws Exception{
		InputStream inputStream = null ;
		Map<EncodeHintType,Object> hints = new HashMap<EncodeHintType,Object>();
		hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
		hints.put(EncodeHintType.CHARACTER_SET, charset);
		hints.put(EncodeHintType.MARGIN, 1);
		MatrixToImageConfig config = new MatrixToImageConfig(style.getOnColor(), style.getOffColor());
		BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, style.getWidth(), style.getHeight(), hints);
		BufferedImage qrImg = MatrixToImageWriter.toBufferedImage(bitMatrix,config);
		appendLogo(qrImg,logo);
		
		if((style.getBorderTopTxt() != null && !"".equals(style.getBorderTopTxt()))
				|| (style.getBorderBottomTxt() != null && !"".equals(style.getBorderBottomTxt()))){
			qrImg = appendBorderTxt(qrImg,style);
		}
		ByteArrayOutputStream os = new ByteArrayOutputStream();  
		ImageIO.write(qrImg, "png", os);  
		inputStream = new ByteArrayInputStream(os.toByteArray()); 
		return inputStream ;
	
	}
	public static void main(String[] args) throws FileNotFoundException, Exception{
	}
	
	/**
	 * 
	 * @param image
	 * @param logo
	 * @throws IOException
	 * @see {@link #createQrCode(String, InputStream, QrStyle)}
	 */
	public static void appendLogo(BufferedImage image, InputStream logo) throws IOException{
		if(logo == null){return;}
		Graphics2D gs = image.createGraphics();
		int ratioWidth = image.getWidth()/ 5;
		int ratioHeight = image.getWidth()/5;
		Image img = ImageIO.read(logo);
		int logoWidth = img.getWidth(null) > ratioWidth ? ratioWidth : img.getWidth(null);
		int logoHeight = img.getHeight(null) > ratioHeight ? ratioHeight : img.getHeight(null);
		int x = (image.getWidth() - logoWidth) / 2;
		int y = (image.getHeight() - logoHeight) / 2;
		gs.drawImage(img, x, y, logoWidth, logoHeight, null);
		gs.setColor(Color.black);
		gs.setBackground(Color.WHITE);
		gs.dispose();
		img.flush();
		
	}
	
	/**
	 * 在二维码边框追加文字,实现方式创建一个空白的画布,画布的高度大于二维码
	 * @param style 
	 * @param qrImg 
	 */
	public static BufferedImage appendBorderTxt(BufferedImage qrImg, QrStyle style){
		BufferedImage _qrImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		//将图片整成纯白色
		for(int i=0;i<_qrImg.getWidth();i++){
			for(int y = 0 ;y<_qrImg.getHeight();y++){
				_qrImg.setRGB(i, y, Color.white.getRGB());
			}
		}
		
		//将生成好的二维码贴在画布上
		Graphics2D graphics = _qrImg.createGraphics();
		graphics.drawImage(qrImg, 0, 30, null);
		graphics.setColor(Color.BLACK);
		graphics.setBackground(Color.WHITE);
		qrImg.flush();
		
		graphics.setFont(new Font("黑体", 0, 18));
		graphics.drawString(style.getBorderTopTxt(), 5f, 20f);
		
		graphics.setColor(Color.PINK);
		graphics.setFont(new Font("黑体",0, 12));
		graphics.drawString(style.getBorderBottomTxt(), 5f, 370f);
		
		graphics.dispose();
		return _qrImg ;
	}
	public static InputStream createQRCode(String txt, Integer onColor, Integer offColor, String logoPath,
			boolean isExp, String advertiserName, Integer imgHeight) throws QRParamsException {
		QRCodeParams params = new QRCodeParams();
		params.setTxt(txt);
		if (onColor != null) {
			params.setOnColor(onColor);
		}
		if (offColor != null) {
			params.setOffColor(offColor);
		}
		if (imgHeight != null) {
			params.setHeight(imgHeight);
		}
		params.setLogoPath(logoPath);
		params.setExpImg(isExp);
		params.setAdvertiserName(advertiserName);
		return generateQRImageToStream(params);
	}
	
	public static InputStream createQRCode(String txt, String logoPath) throws QRParamsException {
		QRCodeParams params = new QRCodeParams();
		params.setTxt(txt);
		params.setLogoPath(logoPath);
		return generateQRImageToStream(params);
	}
	public static InputStream createQRCode(String txt, String logoPath,QrStyle qrStyle) throws QRParamsException {
		QRCodeParams params = new QRCodeParams();
		params.setTxt(txt);
		params.setLogoPath(logoPath);
		return generateQRImageToStream(params);
	}
	public InputStream createQRCode(String txt) throws QRParamsException {
		QRCodeParams params = new QRCodeParams();
		params.setTxt(txt);
		return generateQRImageToStream(params);
	}

	private static InputStream generateQRImageToStream(QRCodeParams params) throws QRParamsException {
		if ((params == null) || (params.getTxt() == null)) {
			throw new QRParamsException("参数错误");
		}
		try {
			initData(params);
			String txt = params.getTxt();
			if ((params.getLogoPath() != null) && (!"".equals(params.getLogoPath().trim()))) {
				return generateQRImage(txt, params.getLogoPath(), params.getSuffixName(), params);
			}
			return generateQRImage(txt, params.getSuffixName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private static InputStream generateQRImage(String txt, String suffix) throws WriterException, IOException {
		Map<EncodeHintType,Object> hints = new HashMap<EncodeHintType,Object>();
		hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
		hints.put(EncodeHintType.CHARACTER_SET, charset);
		hints.put(EncodeHintType.MARGIN, Integer.valueOf(margin));
		MatrixToImageConfig config = new MatrixToImageConfig(onColor, offColor);
		BitMatrix bitMatrix = new MultiFormatWriter().encode(txt, BarcodeFormat.QR_CODE, width, height, hints);
		BufferedImage image = MatrixToImageWriter.toBufferedImage(bitMatrix, config);
		return backInputStream(image);
	}

	private static InputStream generateQRImage(String txt, String logoPath, String suffix, QRCodeParams params)
			throws Exception {
		Map<EncodeHintType,Object> hints = new HashMap<EncodeHintType,Object>();
		hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
		hints.put(EncodeHintType.CHARACTER_SET, charset);
		hints.put(EncodeHintType.MARGIN, Integer.valueOf(margin));
		BitMatrix bitMatrix = new MultiFormatWriter().encode(txt, BarcodeFormat.QR_CODE, width, height, hints);
		return writeToFile(bitMatrix, suffix, logoPath, params.getAdvertiserName());
		
	}

	private static InputStream writeToFile(BitMatrix matrix, String format, String logoPath, String advertiserName)
			throws IOException {
		BufferedImage image = toBufferedImage(matrix);

		Graphics2D gs = image.createGraphics();
		int ratioWidth = image.getWidth() * 2 / 10;
		int ratioHeight = image.getWidth() * 2 / 10;

		Image img = ImageIO.read(new URL(logoPath));
		int logoWidth = img.getWidth(null) > ratioWidth ? ratioWidth : img.getWidth(null);
		int logoHeight = img.getHeight(null) > ratioHeight ? ratioHeight : img.getHeight(null);

		int x = (image.getWidth() - logoWidth) / 2;
		int y = (image.getHeight() - logoHeight) / 2;

		gs.drawImage(img, x, y, logoWidth, logoHeight, null);
		gs.setColor(Color.black);
		gs.setBackground(Color.WHITE);
		gs.dispose();
		img.flush();
		if (isExp) {
			Graphics2D txtgs = image.createGraphics();
			txtgs.setColor(Color.BLUE);
			txtgs.setBackground(Color.white);
			FontBean font1 = new FontBean(welconZstTxt, image.getWidth(), image.getHeight(), height, 0);
			txtgs.setFont(new Font("黑体", 0, font1.getFontSize()));
			txtgs.drawString(welconZstTxt, font1.getX(), font1.getY());
			txtgs.dispose();

			Graphics2D txtgs0 = image.createGraphics();
			txtgs0.setColor(Color.BLUE);
			txtgs0.setBackground(Color.white);
			String msg = advertiserName;
			FontBean font2 = new FontBean(msg, image.getWidth(), image.getHeight(), height, font1.getFontSize());
			txtgs0.setFont(new Font("黑体", 0, font2.getFontSize()));
			txtgs0.drawString(msg, font2.getX(), font2.getY());
			txtgs0.dispose();
			imgY = font2.getY() + 20;

			Graphics2D txtgs2 = image.createGraphics();
			txtgs2.setColor(Color.black);
			txtgs2.setBackground(Color.white);
			FontBean font3 = new FontBean(18, 25, font2.getY() + 270);
			txtgs2.setFont(new Font("黑体", 0, 18));
			txtgs2.drawString("扫一扫二维码，注册并完善资料", font3.getX(), font3.getY());
			txtgs2.drawString("最高可获得29元话费", font3.getX(), font3.getY() + font3.getFontSize());
			txtgs2.dispose();
		}

		return backInputStream(image);
	}

	private static InputStream backInputStream(BufferedImage image) throws IOException {
		ByteArrayOutputStream bs = new ByteArrayOutputStream();
		ImageOutputStream imOut = ImageIO.createImageOutputStream(bs);
		ImageIO.write(image, "png", imOut);
		InputStream is = new ByteArrayInputStream(bs.toByteArray());
		return is;
	}

	private static BufferedImage toBufferedImage(BitMatrix matrix) {
		int width = matrix.getWidth();
		int height = matrix.getHeight();
		BufferedImage image = new BufferedImage(width, height, 1);

		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				image.setRGB(x, y, matrix.get(x, y) ? onColor : offColor);
			}
		}
		return image;
	}

	private static BitMatrix deleteWhite(BitMatrix matrix) {
		int[] rec = matrix.getEnclosingRectangle();
		int resWidth = rec[2] + 1;
		int resHeight = rec[3] + 1;

		BitMatrix resMatrix = new BitMatrix(resWidth, resHeight);
		resMatrix.clear();
		for (int i = 0; i < resWidth; i++) {
			for (int j = 0; j < resHeight; j++) {
				if (matrix.get(i + rec[0], j + rec[1]))
					resMatrix.set(i, j);
			}
		}
		return resMatrix;
	}

	private static void initData(QRCodeParams params) {
		if (params.getWidth() != null) {
			width = params.getWidth().intValue();
		}
		if (params.getHeight() != null) {
			height = params.getHeight().intValue();
		}
		if (params.getOnColor() != null) {
			onColor = params.getOnColor().intValue();
		}
		if (params.getOffColor() != null) {
			offColor = params.getOffColor().intValue();
		}
		if (params.getLevel() != null) {
			level = params.getLevel();
		}
		isExp = params.isExpImg();
	}
	
	/**
	 * 流图片解码
	 * @param 	input
	 * @return 	QRResult
	 */
	public static Result qrDecode(InputStream input,String decodeCharset) {
		BufferedImage image;
		Result result = null;
		try {
			image = ImageIO.read(input);
			LuminanceSource source = new BufferedImageLuminanceSource(image);
			BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
			
			Map<DecodeHintType,Object> hints = new LinkedHashMap<DecodeHintType,Object>();
			// 解码设置编码方式为：utf-8，
			hints.put(DecodeHintType.CHARACTER_SET, decodeCharset);
			//优化精度
			hints.put(DecodeHintType.TRY_HARDER, Boolean.TRUE);
			//复杂模式，开启PURE_BARCODE模式
			hints.put(DecodeHintType.PURE_BARCODE, Boolean.TRUE);
			result = new MultiFormatReader().decode(bitmap, hints);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}