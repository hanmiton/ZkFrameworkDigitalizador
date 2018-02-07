package zkexample.zkoss;

import javax.servlet.http.HttpSession;

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import zkexample.domain.UserProfile;

public class FHSessionUtil {

	public FHSessionUtil() {
	}

	/**
	 * EN: Gets the User Session.<br>
	 * DE: Gibt die User Session zurueck.<br>
	 * 
	 * @return
	 */
	public static HttpSession getSession() {
		RequestAttributes atr = RequestContextHolder.currentRequestAttributes();
		HttpSession session = (HttpSession) atr.getSessionMutex();
		return session;
	}

	public static UserProfile getCurrentUser() {
		UserProfile userProfile = (UserProfile) getSession().getAttribute(
				"CurrentUser");
		
		//userProfile = getSession().setMaxInactiveInterval(50);
		
		
		return userProfile;
	}

	public static void setCurrentUser(UserProfile userProfile) {
		getSession().setAttribute("CurrentUser", userProfile);
	}

}
