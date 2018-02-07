package zkexample.zkoss;

import java.util.Collection;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.zkoss.zk.ui.Execution;
import org.zkoss.zk.ui.util.ThemeProvider;

public class MyThemeProvider implements ThemeProvider {

	public Collection<Object> getThemeURIs(Execution exec, List<Object> uris) {

		if ("greentheme".equals(getSkinCookie(exec))) {
			uris.add("/css/greenTheme.css");
		}
		if ("bluetheme".equals(getSkinCookie(exec))) {
			uris.add("/css/blueTheme.css");
		}
		if ("browntheme".equals(getSkinCookie(exec))) {
			uris.add("/css/brownTheme.css");
		}
		if ("purpletheme".equals(getSkinCookie(exec))) {
			uris.add("/css/purpleTheme.css");
		}
		if ("redtheme".equals(getSkinCookie(exec))) {
			uris.add("/css/redTheme.css");
		}

		return uris;
	}

	public int getWCSCacheControl(Execution exec, String uri) {
		return -1;
	}

	public String beforeWCS(Execution exec, String uri) {
		return uri;
	}

	public String beforeWidgetCSS(Execution exec, String uri) {
		return uri;
	}

	/** Returns the skin specified in cookie. */
	private static String getSkinCookie(Execution exec) {
		Cookie[] cookies = ((HttpServletRequest) exec.getNativeRequest())
				.getCookies();
		if (cookies != null)

			for (int i = 0; i < cookies.length; i++) {

				if ("zktheme".equals(cookies[i].getName()))
					return cookies[i].getValue();
			}
		return "";
	}

}
