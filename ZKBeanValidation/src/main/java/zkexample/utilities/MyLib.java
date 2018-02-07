package zkexample.utilities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.validation.ConstraintViolation;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Messagebox.ClickEvent;

public final class MyLib {

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void confirm(final String code, final String msg,
			final String title, final ConfirmResponse cnf) {

		Map params = new HashMap();
		params.put("width", 500);

		Messagebox.Button[] messageBoxButtons;
		messageBoxButtons = new Messagebox.Button[] { Messagebox.Button.YES,
				Messagebox.Button.NO };

		Messagebox.show(msg, "Confirmation", messageBoxButtons, null,
				Messagebox.QUESTION, null, new EventListener<ClickEvent>() {
					@Override
					public void onEvent(ClickEvent e) throws Exception {
						if (null == cnf) {
							return;
						}
						if (Messagebox.ON_YES.equals(e.getName())) {
							cnf.onConfirmClick(code, Messagebox.YES);
						} else if (Messagebox.ON_NO.equals(e.getName())) {
							cnf.onConfirmClick(code, Messagebox.NO);
						}

					}
				}, params);
	}

	public static void showSuccessmessage() {
		Clients.showNotification("Operation Completed Successfully",
				Clients.NOTIFICATION_TYPE_INFO, null, "top_center", 4100);
	}

	public static <T> boolean IsValidBean(final T instance) {

		List<ValidationMessage> vList = new ArrayList<ValidationMessage>();

		final Set<ConstraintViolation<?>> constraints = new ValidatorUtil()
				.validate(instance);
		if (constraints == null) {
			return true;
		}
		for (final ConstraintViolation<?> violation : constraints) {
			vList.add(new ValidationMessage(violation.getMessage()));
		}
		if (vList.size() > 0) {
			final HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("vList", vList);
			Executions.createComponents("ValidateWindow.zul",
					null, map);
			return false;
		} else
			return true;
	}

}
