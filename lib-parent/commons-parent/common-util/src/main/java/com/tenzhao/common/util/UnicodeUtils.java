package com.tenzhao.common.util;

import org.apache.commons.lang.StringEscapeUtils;

/**
 * Unicode处理类
 * @author chenxj
 *
 */
public class UnicodeUtils {
	
	/**
	 * 中文转unicode字符串
	 * @param cnStr
	 * @return
	 */
	public static String encode(String cnStr){
		char[] chars = cnStr.toCharArray();
		String returnStr = "";
		for (int i = 0; i < chars.length; i++) {
			returnStr += "\\u" + Integer.toString(chars[i], 16);
		}
		return returnStr;
	}
	/**
	 * unicode字符串转中文
	 * @param unicodeStr
	 * @return
	 */
	public static String decode(String unicodeStr) {
		int len = unicodeStr.length();
		StringBuffer outBuffer = new StringBuffer(len);
		for (int x = 0; x < len; )
		{
			char aChar = unicodeStr.charAt(x++);

			if (aChar == '\\')
			{
				aChar = unicodeStr.charAt(x++);

				if (aChar == 'u')
				{
					int value = 0;

					for (int i = 0; i < 4; i++)
					{
						aChar = unicodeStr.charAt(x++);

						switch (aChar)
						{
						case '0':
						case '1':
						case '2':
						case '3':
						case '4':
						case '5':
						case '6':
						case '7':
						case '8':
						case '9':
							value = (value << 4) + aChar - 48;
							break;
						case 'a':
						case 'b':
						case 'c':
						case 'd':
						case 'e':
						case 'f':
							value = (value << 4) + 10 + aChar - 97;
							break;
						case 'A':
						case 'B':
						case 'C':
						case 'D':
						case 'E':
						case 'F':
							value = (value << 4) + 10 + aChar - 65;
							break;
						case ':':
						case ';':
						case '<':
						case '=':
						case '>':
						case '?':
						case '@':
						case 'G':
						case 'H':
						case 'I':
						case 'J':
						case 'K':
						case 'L':
						case 'M':
						case 'N':
						case 'O':
						case 'P':
						case 'Q':
						case 'R':
						case 'S':
						case 'T':
						case 'U':
						case 'V':
						case 'W':
						case 'X':
						case 'Y':
						case 'Z':
						case '[':
						case '\\':
						case ']':
						case '^':
						case '_':
						case '`':
						default:
							throw new IllegalArgumentException("Malformed   \\uxxxx   encoding.");
						}
					}

					outBuffer.append((char)value);
					continue;
				}if (aChar == 't')
					aChar = '\t';
				else if (aChar == 'r') {
					aChar = '\r';
				}
				else if (aChar == 'n')
				{
					aChar = '\n';
				}
				else if (aChar == 'f')
				{
					aChar = '\f';
				}
				outBuffer.append(aChar); continue;
			}

			outBuffer.append(aChar);
		}

		return outBuffer.toString();
	}

}
