package zkexample.utilities;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.zkoss.bind.BindContext;
import org.zkoss.bind.Converter;
import org.zkoss.zk.ui.Component;

public class MyDateFormatConverter implements Converter {
	/**
	 * Convert Date to String.
	 * 
	 * @param val
	 *            date to be converted
	 * @param comp
	 *            associated component
	 * @param ctx
	 *            bind context for associate Binding and extra parameter (e.g.
	 *            format)
	 * @return the converted String
	 */

	/**
	 * The method coerceToUi() is invoked when loading ViewModel's property to
	 * component and its return type should correspond to bound component
	 * attribute's value[1]. The coerceToBean() is invoked when saving. If you
	 * only need to one way conversion, you can leave unused method empty.
	 */

	private static SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");

	public Object coerceToUi(Object val, Component comp, BindContext ctx) {
		final Date date = (Date) val;
		return date == null ? null : sdf.format(date);
	}

	/**
	 * Convert String to Date.
	 * 
	 * @param val
	 *            date in string form
	 * @param comp
	 *            associated component
	 * @param ctx
	 *            bind context for associate Binding and extra parameter (e.g.
	 *            format)
	 * @return the converted Date
	 */
	public Object coerceToBean(Object val, Component comp, BindContext ctx) {
		final String date = (String) val;
		sdf.setLenient(false);
		try {
			return date == null ? null : sdf.parse(date);
		} catch (ParseException e) {
			comp.invalidate();
			return null;

		}
	}

}
