package zkexample.zkoss;

import java.util.HashMap;

import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Center;

import zkexample.domain.UserProfile;
 

public class MainVM {

	@Wire("#mainlayout")
	private Borderlayout borderLayout;

	/**
	 * This method will be called after host component composition has been done
	 * (AfterCompose)
	 * 
	 * @param view
	 *            Root Component of the ZUL File.
	 */

	@AfterCompose
	public void initSetup(@ContextParam(ContextType.VIEW) Component view) {
			
		final HashMap<String, Object> map = new HashMap<String, Object>();

		Selectors.wireComponents(view, this, false);

		/* get an instance of the searched CENTER layout area */
		Center c1 = borderLayout.getCenter();

		/* clear the center child */
		c1.getChildren().clear();

		map.put("centerArea", c1);
		/* Load the left navigation menu for patient cases */
		//Executions.createComponents("userList.zul", c1, map);
		UserProfile User = FHSessionUtil.getCurrentUser();
		
		if(User.getSystem()==2){
			//Executions.createComponents("/jasperreport.zul", c1, map);
			Executions.createComponents("/admin/userList.zul", c1, map);
		} else if(User.getSystem()==1){
			Executions.createComponents("/indexador/fileListIndex.zul", c1, map);
		} else if(User.getSystem()==0){
			Executions.createComponents("digitalizador/fileList.zul", c1, map);
		}
		
		
		
		
	}
	 
}
