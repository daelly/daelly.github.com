package org.daelly.oj.utils;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import java.util.Random;

import javax.imageio.ImageIO;

public class RandCodeCreater {

	//图片宽度
	private final static int IMAGEWIDTH = 18;
	
	//图片高度
	private final static int IMAGEHEIGHT = 26;
	
	private final static int FONTSIZE = 18;
	
	private final static int CODESIZE = 4;
	
	private final static char[] CHARS = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 
        'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 
        'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 
        'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n',
        'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x',
        'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7',
        '8', '9'};
	
	private static Random random = new Random();
	
	public static String getRandStr(){
		StringBuilder sb = new StringBuilder();
		for(int i=0;i<CODESIZE;i++)
			sb.append(CHARS[random.nextInt(CHARS.length)]);
		return sb.toString();
	}
	
	private static Color getRandColor(int ll,int ul){
		ll = ll > 255 ? 255 : ll;
		ll = ll < 1 ? 1: ll;
		ul = ul > 255 ? 255 :ul;
		ul = ul < 1 ? 1 :ul;
		int r = random.nextInt(ul-ll)+ll;
		int g = random.nextInt(ul-ll)+ll;
		int b = random.nextInt(ul=ll)+ll;
		Color color = new Color(r,g,b);
		return color;
	}
	
	public static BufferedImage getImage(String randCode){
		BufferedImage image = new BufferedImage(IMAGEWIDTH*CODESIZE, IMAGEHEIGHT, BufferedImage.TYPE_INT_RGB);
		Graphics graphics = image.getGraphics();
		//设置背景色
		graphics.setColor(getRandColor(1, 50));
		graphics.fillRect(0, 0, IMAGEWIDTH*CODESIZE, IMAGEHEIGHT);
		//设置边框颜色
		graphics.setColor(new Color(0, 255, 0));
		for(int i=0;i<2;i++)
			graphics.drawRect(i, i, IMAGEWIDTH*CODESIZE-i*2-1, IMAGEHEIGHT-i*2-1);
		//加些干扰的线
		graphics.setColor(getRandColor(50, 100));
		for(int i=0;i<50;i++){
			int x1 = random.nextInt(IMAGEWIDTH*CODESIZE-4)+2;
			int y1 = random.nextInt(IMAGEWIDTH-4)+2;
			int x2 = random.nextInt(IMAGEWIDTH*CODESIZE-2-x1)+x1;
			int y2 = y1;
			graphics.drawLine(x1, y1, x2, y2);
		}
		//验证码文本
		graphics.setFont(new Font("Times New Roman", Font.PLAIN, FONTSIZE));
		for(int i=0;i<CODESIZE;i++){
			String temp = randCode.substring(i, i+1);
			graphics.setColor(getRandColor(100, 255));
			graphics.drawString(temp, 13*i+6, 16);
		}
		graphics.dispose();
		return image;
	}
	
	public static void main(String[] args){
		try {
			FileOutputStream fos = new FileOutputStream("G:\\temp\\hehe.png");
			BufferedImage bi = getImage(getRandStr());
			ImageIO.write(bi, "PNG", fos);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
