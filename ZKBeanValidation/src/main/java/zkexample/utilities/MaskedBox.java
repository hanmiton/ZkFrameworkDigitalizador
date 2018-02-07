package zkexample.utilities;

import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Textbox;

public class MaskedBox extends Textbox {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String format;
	private String waterMark = "";

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public String getWaterMark() {
		return waterMark;
	}

	public void setWaterMark(String waterMark) {
		this.waterMark = waterMark;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public MaskedBox() {
		setMold("rounded");
		this.addEventListener(Events.ON_CREATE, new EventListener() {
			@Override
			public void onEvent(Event event) throws Exception {
				String mask;
				if (waterMark.equals(""))
					mask = "jq(this.getInputNode()).mask('" + format + "');";
				else
					mask = "jq(this.getInputNode()).mask('" + format
							+ "');jq(this.getInputNode()).Watermark('"
							+ waterMark + "'," + "'" + "gray" + "'" + ");";
				setWidgetListener("onBind", mask);
			}
		});

	}

}
