package pandeyDanceAcademy.pda_backend.global;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class Utlity {
	public static void setCookie(HttpServletResponse response, String name, String value, String domain,
			boolean isSecure) {
		Cookie cookie = new Cookie(name, value);
//		cookie.setPath("/");
//		cookie.setDomain(domain);
//		if (isSecure)
//			cookie.setSecure(true);
//		cookie.setHttpOnly(true);
		response.addCookie(cookie);
	}

	public static Cookie getCookie(HttpServletRequest request, String name) {
		Cookie[] cookies = request.getCookies();
		System.out.println("Cookies count: " +cookies.length);
		if (cookies != null) {
			for (Cookie cookie : cookies) {
//				System.out.println(cookie.getName());
				if (name.equals(cookie.getName())) {
					return cookie;
				}
			}
		}
		return null;
	}

	public static URL getDomainName(HttpServletRequest request) {
		String refererHeader = request.getHeader("referer");
		if (refererHeader != null) {
			try {
				return new URI(refererHeader).toURL();
			} catch (MalformedURLException | URISyntaxException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
}
