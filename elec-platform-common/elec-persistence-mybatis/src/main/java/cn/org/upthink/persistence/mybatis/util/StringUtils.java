/*     */ package cn.org.upthink.persistence.mybatis.util;
/*     */ 
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import org.apache.commons.lang3.StringEscapeUtils;
/*     */ 
/*     */ public class StringUtils extends org.apache.commons.lang3.StringUtils
/*     */ {
/*     */   private static final char SEPARATOR = '_';
/*     */   private static final String CHARSET_NAME = "UTF-8";
/*     */ 
/*     */   public static byte[] getBytes(String str)
/*     */   {
/*  28 */     if (str != null) {
/*     */       try {
/*  30 */         return str.getBytes("UTF-8");
/*     */       } catch (UnsupportedEncodingException e) {
/*  32 */         return null;
/*     */       }
/*     */     }
/*  35 */     return null;
/*     */   }
/*     */ 
/*     */   public static String toString(byte[] bytes)
/*     */   {
/*     */     try
/*     */     {
/*  46 */       return new String(bytes, "UTF-8"); } catch (UnsupportedEncodingException e) {
/*     */     }
/*  48 */     return "";
/*     */   }
/*     */ 
/*     */   public static boolean inString(String str, String[] strs)
/*     */   {
/*  59 */     if (str != null) {
/*  60 */       for (String s : strs) {
/*  61 */         if (str.equals(trim(s))) {
/*  62 */           return true;
/*     */         }
/*     */       }
/*     */     }
/*  66 */     return false;
/*     */   }
/*     */ 
/*     */   public static String replaceHtml(String html)
/*     */   {
/*  73 */     if (isBlank(html)) {
/*  74 */       return "";
/*     */     }
/*  76 */     String regEx = "<.+?>";
/*  77 */     Pattern p = Pattern.compile(regEx);
/*  78 */     Matcher m = p.matcher(html);
/*  79 */     String s = m.replaceAll("");
/*  80 */     return s;
/*     */   }
/*     */ 
/*     */   public static String replaceMobileHtml(String html)
/*     */   {
/*  89 */     if (html == null) {
/*  90 */       return "";
/*     */     }
/*  92 */     return html.replaceAll("<([a-z]+?)\\s+?.*?>", "<$1>");
/*     */   }
/*     */ 
/*     */   public static String abbr(String str, int length)
/*     */   {
/* 102 */     if (str == null) {
                return "";
               }
/*     */     try
/*     */     {
/* 106 */       StringBuilder sb = new StringBuilder();
/* 107 */       int currentLength = 0;
/* 108 */       for (char c : replaceHtml(StringEscapeUtils.unescapeHtml4(str)).toCharArray()) {
/* 109 */         currentLength += String.valueOf(c).getBytes("GBK").length;
/* 110 */         if (currentLength <= length - 3) {
/* 111 */           sb.append(c);
/*     */         } else {
/* 113 */           sb.append("...");
/* 114 */           break;
/*     */         }
/*     */       }
/* 117 */       return sb.toString();
/*     */     } catch (UnsupportedEncodingException e) {
/* 119 */       e.printStackTrace();
/*     */     }
/* 121 */     return "";
/*     */   }

            static Pattern patternStr = Pattern.compile("<([a-zA-Z]+)[^<>]*>");
/*     */ 
/*     */   public static String abbr2(String param, int length) {
/* 125 */     if (param == null) {
/* 126 */       return "";
/*     */     }
/* 128 */     StringBuffer result = new StringBuffer();
/* 129 */     int n = 0;
/*     */ 
/* 131 */     boolean isCode = false;
/* 132 */     boolean isHTML = false;
/* 133 */     for (int i = 0; i < param.length(); i++) {
/* 134 */       char temp = param.charAt(i);
/* 135 */       if (temp == '<') {
/* 136 */         isCode = true;
/* 137 */       } else if (temp == '&') {
/* 138 */         isHTML = true;
/* 139 */       } else if ((temp == '>') && (isCode)) {
/* 140 */         n -= 1;
/* 141 */         isCode = false;
/* 142 */       } else if ((temp == ';') && (isHTML)) {
/* 143 */         isHTML = false;
/*     */       }
/*     */       try {
/* 146 */           if ((!isCode) && (!isHTML)) {
                        n += String.valueOf(temp).getBytes("GBK").length;
                    }
/*     */       }
/*     */       catch (UnsupportedEncodingException e) {
/* 150 */         e.printStackTrace();
/*     */       }
/*     */ 
/* 153 */       if (n <= length - 3) {
/* 154 */         result.append(temp);
/*     */       } else {
/* 156 */         result.append("...");
/* 157 */         break;
/*     */       }
/*     */     }
/*     */ 
/* 161 */     String temp_result = result.toString().replaceAll("(>)[^<>]*(<?)", "$1$2");
/*     */ 
/* 165 */     temp_result = temp_result.replaceAll("</?(AREA|BASE|BASEFONT|BODY|BR|COL|COLGROUP|DD|DT|FRAME|HEAD|HR|HTML|IMG|INPUT|ISINDEX|LI|LINK|META|OPTION|P|PARAM|TBODY|TD|TFOOT|TH|THEAD|TR|area|base|basefont|body|br|col|colgroup|dd|dt|frame|head|hr|html|img|input|isindex|li|link|meta|option|p|param|tbody|td|tfoot|th|thead|tr)[^<>]*/?>", "");
/*     */ 
/* 169 */     temp_result = temp_result.replaceAll("<([a-zA-Z]+)[^<>]*>(.*?)</\\1>", "$2");
/*     */ 
/* 172 */
/* 173 */     Matcher m = patternStr.matcher(temp_result);
/* 174 */     List endHTML = new ArrayList();
/* 175 */     while (m.find()) {
/* 176 */       endHTML.add(m.group(1));
/*     */     }
/*     */ 
/* 179 */     for (int i = endHTML.size() - 1; i >= 0; i--) {
/* 180 */       result.append("</");
/* 181 */       result.append((String)endHTML.get(i));
/* 182 */       result.append(">");
/*     */     }
/* 184 */     return result.toString();
/*     */   }
/*     */ 
/*     */   public static Double toDouble(Object val)
/*     */   {
/* 191 */     if (val == null)
/* 192 */       {return Double.valueOf(0.0D);}
/*     */     try
/*     */     {
/* 195 */       return Double.valueOf(trim(val.toString())); } catch (Exception e) {
/*     */     }
/* 197 */     return Double.valueOf(0.0D);
/*     */   }
/*     */ 
/*     */   public static Float toFloat(Object val)
/*     */   {
/* 205 */     return Float.valueOf(toDouble(val).floatValue());
/*     */   }
/*     */ 
/*     */   public static Long toLong(Object val)
/*     */   {
/* 212 */     return Long.valueOf(toDouble(val).longValue());
/*     */   }
/*     */ 
/*     */   public static Integer toInteger(Object val)
/*     */   {
/* 219 */     return Integer.valueOf(toLong(val).intValue());
/*     */   }
/*     */ 
/*     */   public static String getRemoteAddr(HttpServletRequest request)
/*     */   {
/* 236 */     String remoteAddr = request.getHeader("X-Real-IP");
/* 237 */     if (isNotBlank(remoteAddr))
/* 238 */       {remoteAddr = request.getHeader("X-Forwarded-For");}
/* 239 */     else if (isNotBlank(remoteAddr))
/* 240 */       {remoteAddr = request.getHeader("Proxy-Client-IP");}
/* 241 */     else if (isNotBlank(remoteAddr)) {
/* 242 */       {remoteAddr = request.getHeader("WL-Proxy-Client-IP");}
/*     */     }
/* 244 */     return remoteAddr != null ? remoteAddr : request.getRemoteAddr();
/*     */   }
/*     */ 
/*     */   public static String toCamelCase(String s)
/*     */   {
/* 255 */     if (s == null) {
/* 256 */       return null;
/*     */     }
/*     */ 
/* 259 */     s = s.toLowerCase();
/*     */ 
/* 261 */     StringBuilder sb = new StringBuilder(s.length());
/* 262 */     boolean upperCase = false;
/* 263 */     for (int i = 0; i < s.length(); i++) {
/* 264 */       char c = s.charAt(i);
/*     */ 
/* 266 */       if (c == '_') {
/* 267 */         upperCase = true;
/* 268 */       } else if (upperCase) {
/* 269 */         sb.append(Character.toUpperCase(c));
/* 270 */         upperCase = false;
/*     */       } else {
/* 272 */         sb.append(c);
/*     */       }
/*     */     }
/*     */ 
/* 276 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public static String toCapitalizeCamelCase(String s)
/*     */   {
/* 287 */     if (s == null) {
/* 288 */       return null;
/*     */     }
/* 290 */     s = toCamelCase(s);
/* 291 */     return new StringBuilder().append(s.substring(0, 1).toUpperCase()).append(s.substring(1)).toString();
/*     */   }
/*     */ 
/*     */   public static String toUnderScoreCase(String s)
/*     */   {
/* 302 */     if (s == null) {
/* 303 */       return null;
/*     */     }
/*     */ 
/* 306 */     StringBuilder sb = new StringBuilder();
/* 307 */     boolean upperCase = false;
/* 308 */     for (int i = 0; i < s.length(); i++) {
/* 309 */       char c = s.charAt(i);
/*     */ 
/* 311 */       boolean nextUpperCase = true;
/*     */ 
/* 313 */       if (i < s.length() - 1) {
/* 314 */         nextUpperCase = Character.isUpperCase(s.charAt(i + 1));
/*     */       }
/*     */ 
/* 317 */       if ((i > 0) && (Character.isUpperCase(c))) {
/* 318 */         if ((!upperCase) || (!nextUpperCase)) {
/* 319 */           sb.append('_');
/*     */         }
/* 321 */         upperCase = true;
/*     */       } else {
/* 323 */         upperCase = false;
/*     */       }
/*     */ 
/* 326 */       sb.append(Character.toLowerCase(c));
/*     */     }
/*     */ 
/* 329 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public static String jsGetVal(String objectString)
/*     */   {
/* 339 */     StringBuilder result = new StringBuilder();
/* 340 */     StringBuilder val = new StringBuilder();
/* 341 */     String[] vals = split(objectString, ".");
/* 342 */     for (int i = 0; i < vals.length; i++) {
/* 343 */       val.append(new StringBuilder().append(".").append(vals[i]).toString());
/* 344 */       result.append(new StringBuilder().append("!").append(val.substring(1)).append("?'':").toString());
/*     */     }
/* 346 */     result.append(val.substring(1));
/* 347 */     return result.toString();
/*     */   }
/*     */ 
/*     */   public static String getRandomStr(int length)
/*     */   {
/* 356 */     char[] ss = new char[length];
/* 357 */     int i = 0;
/* 358 */     while (i < length) {
/* 359 */       int f = (int)(Math.random() * 3.0D);
/* 360 */       if (f == 0)
/* 361 */         {ss[i] = ((char)(int)(65.0D + Math.random() * 26.0D));}
/* 362 */       else if (f == 1)
/* 363 */         {ss[i] = ((char)(int)(97.0D + Math.random() * 26.0D));}
/*     */       else
/* 365 */         {ss[i] = ((char)(int)(48.0D + Math.random() * 10.0D));}
/* 366 */       i++;
/*     */     }
/* 368 */     String is = new String(ss);
/* 369 */     return is;
/*     */   }
/*     */ 
/*     */   public static String changeStrArr2String(String[] strSrc, String split) {
/* 373 */     if ((strSrc == null) || (strSrc.length == 0)) {
/* 374 */       return "";
/*     */     }
/* 376 */     StringBuffer buf = new StringBuffer();
/* 377 */     for (String str : strSrc) {
/* 378 */       buf.append(str).append(split);
/*     */     }
/* 380 */     return buf.substring(0, buf.length() - 1);
/*     */   }

            static Pattern patternInt = Pattern.compile("^[-\\+]?[\\d]*$");
/*     */ 
/*     */   public static boolean isInteger(String str)
/*     */   {
/* 387 */     return patternInt.matcher(str).matches();
/*     */   }

            static Pattern patternMobile = Pattern.compile("^[1][3,4,5,8,7][0-9]{9}$");
/*     */ 
/*     */   public static boolean isMobile(String str) {
/* 392 */     Matcher m = null;
/* 393 */     boolean b = false;
/* 395 */     m = patternMobile.matcher(str);
/* 396 */     b = m.matches();
/* 397 */     return b;
/*     */   }

            static Pattern patternEmail = Pattern.compile("^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$");
/*     */ 
/*     */   public static boolean email(String str) {
/* 402 */     Matcher m = null;
/* 403 */     boolean b = false;
/* 406 */     m = patternEmail.matcher(str);
/* 407 */     b = m.matches();
/* 408 */     return b;
/*     */   }
/*     */ 
/*     */   public static String getRandomStrFromArray(String[] arras) {
/* 412 */     if (arras == null) {return null;}
/*     */ 
/* 414 */     String random = "";
/* 415 */     int index = (int)(Math.random() * arras.length);
/* 416 */     random = arras[index];
/* 417 */     return random;
/*     */   }
/*     */ 
/*     */   public static String getRandomNumber(int length)
/*     */   {
/* 426 */     String str = "";
/* 427 */     int beginChar = 97;
/* 428 */     int endChar = 122;
/*     */ 
/* 430 */     beginChar = 123;
/* 431 */     endChar = 132;
/*     */ 
/* 434 */     Random random = new Random();
/* 435 */     for (int i = 0; i < length; i++) {
/* 436 */       int tmp = beginChar + random.nextInt(endChar - beginChar);
/*     */ 
/* 438 */       if (tmp > 122) {
/* 439 */         tmp = 48 + (tmp - 122);
/*     */       }
/* 441 */       str = new StringBuilder().append(str).append((char)tmp).toString();
/*     */     }
/*     */ 
/* 444 */     return str;
/*     */   }
/*     */ }