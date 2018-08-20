/*     */ package cn.org.upthink.persistence.mybatis.util;
/*     */ 
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.net.URLDecoder;
/*     */ import java.net.URLEncoder;
/*     */ import javax.servlet.http.Cookie;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ 
/*     */ public class CookieUtils
/*     */ {
/*     */   public static void setCookie(HttpServletResponse response, String name, String value)
/*     */   {
/*  22 */     setCookie(response, name, value, 86400);
/*     */   }
/*     */ 
/*     */   public static void setCookie(HttpServletResponse response, String name, String value, String path)
/*     */   {
/*  33 */     setCookie(response, name, value, path, 86400);
/*     */   }
/*     */ 
/*     */   public static void setCookie(HttpServletResponse response, String name, String value, int maxAge)
/*     */   {
/*  44 */     setCookie(response, name, value, "/", maxAge);
/*     */   }
/*     */ 
/*     */   public static void setCookie(HttpServletResponse response, String name, String value, String path, int maxAge)
/*     */   {
/*  55 */     Cookie cookie = new Cookie(name, null);
/*  56 */     cookie.setPath(path);
/*  57 */     cookie.setMaxAge(maxAge);
/*     */     try {
/*  59 */       cookie.setValue(URLEncoder.encode(value, "utf-8"));
/*     */     } catch (UnsupportedEncodingException e) {
/*  61 */       e.printStackTrace();
/*     */     }
/*  63 */     response.addCookie(cookie);
/*     */   }
/*     */ 
/*     */   public static String getCookie(HttpServletRequest request, String name)
/*     */   {
/*  72 */     return getCookie(request, null, name, false);
/*     */   }
/*     */ 
/*     */   public static String getCookie(HttpServletRequest request, HttpServletResponse response, String name)
/*     */   {
/*  80 */     return getCookie(request, response, name, true);
/*     */   }
/*     */ 
/*     */   public static String getCookie(HttpServletRequest request, HttpServletResponse response, String name, boolean isRemove)
/*     */   {
/*  91 */     String value = null;
/*  92 */     Cookie[] cookies = request.getCookies();
/*  93 */     if (cookies != null) {
/*  94 */       for (Cookie cookie : cookies) {
/*  95 */         if (cookie.getName().equals(name)) {
/*     */           try {
/*  97 */             value = URLDecoder.decode(cookie.getValue(), "utf-8");
/*     */           } catch (UnsupportedEncodingException e) {
/*  99 */             e.printStackTrace();
/*     */           }
/* 101 */           if (isRemove) {
/* 102 */             cookie.setMaxAge(0);
/* 103 */             response.addCookie(cookie);
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/* 108 */     return value;
/*     */   }
/*     */ }

/* Location:           /Users/rover/Downloads/mus-persistence.mybatis-1.0.1.jar
 * Qualified Name:     CookieUtils
 * JD-Core Version:    0.6.2
 */