package com.morethan.game.utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;

public class WebUtils {
	
	public static final String MYDOMAIN = "/";
	
	public static void setCookie(HttpServletResponse response, String key, String value, int days) {
		setCookie(response, key, value, days, MYDOMAIN);
	}

	/**设置Cookie值*/
	public static void setCookie(HttpServletResponse response, String key, String value, int days, String domain) {
		if ((key != null) && (value != null)) {
			Cookie cookie = new Cookie(key, value);
			cookie.setMaxAge(days * 24 * 60 * 60);
			cookie.setPath("/");
			if (org.apache.commons.lang.StringUtils.isNotEmpty(domain)) {
				cookie.setDomain(domain);
			}
			response.addCookie(cookie);
		}
	}

	/**获取Cookie值*/
	public static String getCookie(HttpServletRequest request, String key) {
		Cookie[] cookies = request.getCookies();
		String resValue = "";
		if ((cookies != null) && (cookies.length > 0)) {
			for (int i = 0; i < cookies.length; i++) {
				if ((!key.equalsIgnoreCase(cookies[i].getName()))
						|| (!org.apache.commons.lang.StringUtils
								.isNotEmpty(cookies[i].getValue())))
					continue;
				resValue = cookies[i].getValue();
			}

		}
		return resValue;
	}

	/**删除默认域名下的*/
	public static void deleteCookie(HttpServletRequest request,
			HttpServletResponse response, String name) {
		deleteCookieDomain(request, response, name, MYDOMAIN);
	}

	/**删除某域下的Cookie*/
	public static void deleteCookieDomain(HttpServletRequest request,
			HttpServletResponse response, String name, String domain) {
		Cookie[] cookies = request.getCookies();
		if ((cookies != null) && (cookies.length > 0))
			for (int i = 0; i < cookies.length; i++) {
				if (!name.equalsIgnoreCase(cookies[i].getName()))
					continue;
				Cookie ck = new Cookie(cookies[i].getName(), null);
				ck.setPath("/");
				if (org.apache.commons.lang.StringUtils.isNotEmpty(domain)) {
					ck.setDomain(domain);
				}
				ck.setMaxAge(0);
				response.addCookie(ck);
				return;
			}
	}


	/**获取请求IP地址*/
	public static String getIpAddr(HttpServletRequest request) {
		String ipAddress = null;
		ipAddress = request.getHeader("x-forwarded-for");
		if ((ipAddress == null) || (ipAddress.length() == 0)
				|| ("unknown".equalsIgnoreCase(ipAddress))) {
			ipAddress = request.getHeader("Proxy-Client-IP");
		}
		if ((ipAddress == null) || (ipAddress.length() == 0)
				|| ("unknown".equalsIgnoreCase(ipAddress))) {
			ipAddress = request.getHeader("WL-Proxy-Client-IP");
		}
		if ((ipAddress == null) || (ipAddress.length() == 0)
				|| ("unknown".equalsIgnoreCase(ipAddress))) {
			ipAddress = request.getRemoteAddr();
			if (ipAddress.equals("127.0.0.1")) {
				InetAddress inet = null;
				try {
					inet = InetAddress.getLocalHost();
				} catch (UnknownHostException e) {
					e.printStackTrace();
				}
				ipAddress = inet.getHostAddress();
			}

		}
		if ((ipAddress != null) && (ipAddress.length() > 15)) {
			if (ipAddress.indexOf(",") > 0) {
				ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
			}
		}
		return ipAddress;
	}


	/**获取请求URL*/
	public static String getServletRequestUrlParms(HttpServletRequest request) {
		StringBuffer sbUrlParms = request.getRequestURL();
		sbUrlParms.append("?");
		Enumeration<String> parNames = request.getParameterNames();
		while (parNames.hasMoreElements()) {
			String parName = parNames.nextElement().toString();
			try {
				sbUrlParms.append(parName).append("=")
						.append(URLEncoder.encode(request.getParameter(parName), "UTF-8"))
						.append("&");
			} catch (UnsupportedEncodingException e) {
				return "";
			}
		}
		return sbUrlParms.toString();
	}
	/**获取请求URI*/
	public static String getServletRequestUriParms(HttpServletRequest request) {
		StringBuffer sbUrlParms = new StringBuffer(request.getRequestURI());
		sbUrlParms.append("?");
		Enumeration<String> parNames = request.getParameterNames();
		while (parNames.hasMoreElements()) {
			String parName = parNames.nextElement().toString();
			try {
				sbUrlParms.append(parName).append("=")
				.append(URLEncoder.encode(request.getParameter(parName), "UTF-8"))
				.append("&");
			} catch (UnsupportedEncodingException e) {
				return "";
			}
		}
		return sbUrlParms.toString();
	}
	
	/**判断如果是ajax请求*/
	public static boolean isAjaxRequest(HttpServletRequest request) {
		String her = request.getHeader("x-requested-with");

		return org.apache.commons.lang.StringUtils.isNotEmpty(her);
	}
	/**判断如果不是ajax请求*/
	public static boolean isNotAjaxRequest(HttpServletRequest request) {
		return !isAjaxRequest(request);
	}
	
    /**去html*/
    public static String replaceTagHTML(String src) {
        String regex = "\\<(.+?)\\>";
        return org.apache.commons.lang.StringUtils.isNotEmpty(src)?src.replaceAll(regex, ""):"";
    }
    
    // AJAX输出，返回null
 	public static String ajax(String content, String type,HttpServletResponse response) {
 		try {
 			response.setContentType(type + ";charset=UTF-8");
 			response.setHeader("Pragma", "No-cache");
 			response.setHeader("Cache-Control", "no-cache");
 			response.setDateHeader("Expires", 0);
 			response.setCharacterEncoding("UTF-8");
 			response.getWriter().write(content);
 			response.getWriter().flush();
 		} catch (IOException e) {
 			e.printStackTrace();
 		}
 		return null;
 	}

 	// AJAX输出文本，返回null
 	public static String ajaxText(String text,HttpServletResponse response) {
 		return ajax(text, "text/plain",response);
 	}

 	// AJAX输出HTML，返回null
 	public static String ajaxHtml(String html,HttpServletResponse response) {
 		return ajax(html, "text/html",response);
 	}

 	// AJAX输出XML，返回null
 	public static String ajaxXml(String xml,HttpServletResponse response) {
 		return ajax(xml, "text/xml",response);
 	}

 	// 根据字符串输出JSON，返回null
 	public static String ajaxJson(String jsonString,HttpServletResponse response) {
 		return ajax(jsonString, "text/plain",response);
 	}
 	
 	// 根据Map输出JSON，返回null
 	public static String ajaxJson(Map<String, Object> jsonMap,HttpServletResponse response) {
 		JSONObject jsonObject = new JSONObject(jsonMap);
 		return ajax(jsonObject.toString(), "text/html",response);
 	}
 	
}