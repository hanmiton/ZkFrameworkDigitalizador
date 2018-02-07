package zkexample.utilities;
import java.util.List;

import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Window;

public class formValidationVM {

	@Wire("#ValidateWindow")
	private Window win;
	
	private List<ValidationMessage> allMessages = null;

	
	public List<ValidationMessage> getAllMessages() {
		return allMessages;
	}

	public void setAllMessages(List<ValidationMessage> allMessages) {
		this.allMessages = allMessages;
	}
	
	@AfterCompose
	public void init(@ContextParam(ContextType.VIEW) Component view,@ExecutionArgParam("vList") List<ValidationMessage> vList) {
		Selectors.wireComponents(view, this, false);
		setAllMessages(vList);
	}

	@Command
	public void closeThis() {
		win.detach();
	}
}
