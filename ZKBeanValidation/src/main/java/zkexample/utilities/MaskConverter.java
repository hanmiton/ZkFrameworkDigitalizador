package zkexample.utilities;

import org.zkoss.bind.BindContext;
import org.zkoss.bind.Converter;
import org.zkoss.zk.ui.Component;

public class MaskConverter implements Converter {

	/**
	 * The method coerceToUi() is invoked when loading ViewModel's property to
	 * component and its return type should correspond to bound component
	 * attribute's value[1]. The coerceToBean() is invoked when saving. If you
	 * only need to one way conversion, you can leave unused method empty.
	 */

	public Object coerceToUi(Object val, Component comp, BindContext ctx) {
		// do nothing
		return val;
	}

	public Object coerceToBean(Object val, Component comp, BindContext ctx) {
		/*
		 * Here we will check only masking characters are present, if so, then
		 * return null
		 */
		final String propValue = (String) val;
		if (IsEmptyByMask(propValue))
			return null;
		else
			return val;

	}

	public boolean IsEmptyByMask(String s1) {
		if (isEmpty(s1) == false) {
			s1 = s1.replaceAll("_", "").replace("(", "").replace(")", "").replace("x", "")
					.replace("-", "").replace(" ", "").replace("/", "").trim();
			if (isEmpty(s1))
				return true;
			else
				return false;
		}
		return true;
	}

	public static boolean isEmpty(String s) {
		return s == null || s.trim().length() == 0;
	}
}

