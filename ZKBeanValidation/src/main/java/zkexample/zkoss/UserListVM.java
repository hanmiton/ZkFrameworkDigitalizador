package zkexample.zkoss;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.hibernate.Session;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Center;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.West;
import zkexample.dao.CRUDDaoImpl;
import zkexample.domain.UserProfile;
import zkexample.service.CRUDService;
import zkexample.utilities.ConfirmResponse;
import zkexample.utilities.MyLib;

public class UserListVM implements ConfirmResponse {

	private Center centerArea;
	private West westArea;
	private DataFilter dataFilter = new DataFilter();

	@WireVariable
	private CRUDService CRUDService;

	private UserProfile selectedItem;
	
	private List<UserProfile> allReordsInDB = null;
	private List<UserProfile> userList = null;
	//private UserProfile user = FHSessionUtil.getCurrentUser();
	
	public UserProfile getSelectedItem() {
		return selectedItem;
	}

	
	/*Session session = Sessions.getCurrent();
	Object myStoredData = session.getAttribute("my_stored_data");
	*/
	
	public void setSelectedItem(UserProfile selectedItem) {
		this.selectedItem = selectedItem;
	}

	public DataFilter getDataFilter() {
		return dataFilter;
	}

	public void setDataFilter(DataFilter dataFilter) {
		this.dataFilter = dataFilter;
	}
	
	

	@AfterCompose
	public void initSetup(@ContextParam(ContextType.VIEW) Component view,
			@ExecutionArgParam("centerArea") Center centerArea, West westArea) {
		
	/*	UserProfile dbUser = CRUDService.GetUniqueEntityByNamedQuery(
				"UserProfile.findUserByUserID", username);
	 */
		Selectors.wireComponents(view, this, false);
		this.centerArea = centerArea;
		CRUDService = (CRUDService) SpringUtil.getBean("CRUDService");
		
		allReordsInDB = CRUDService.getAll(UserProfile.class);
		//userSession =  new ArrayList<UserProfile>((user));
		userList = new ArrayList<UserProfile>((allReordsInDB));

	}

	public List<UserProfile> getDataSet() {
		return allReordsInDB;
	}

	@Command
	public void onAddNew() {
		final HashMap<String, Object> map = new HashMap<String, Object>();
		
		map.put("selectedRecord", null);
		map.put("recordMode", "NEW");
		map.put("centerArea", centerArea);
		
		
		centerArea.getChildren().clear();
		Executions.createComponents("UserCRUD.zul", centerArea, map);
	
	}

	@Command
	public void onEdit(@BindingParam("userRecord") UserProfile userProfile) {
		
		
		final HashMap<String, Object> map = new HashMap<String, Object>();
		
		map.put("selectedRecord", userProfile);
		map.put("recordMode", "EDIT");
		map.put("centerArea", centerArea);
		centerArea.getChildren().clear();
		Executions.createComponents("UserCRUD.zul", centerArea, map);
	}
	
	
		@Command
		public void onMeta(@BindingParam("userRecord") UserProfile userProfile) {
			
			
			final HashMap<String, Object> map = new HashMap<String, Object>();
			
			map.put("selectedRecord", userProfile);
			map.put("recordMode", "META");
			map.put("centerArea", centerArea);
			centerArea.getChildren().clear();
			Executions.createComponents("UserMetaCRUD.zul", centerArea, map);
		}

	@Command
	public void openAsReadOnly(
			@BindingParam("userRecord") UserProfile userProfile) {

		final HashMap<String, Object> map = new HashMap<String, Object>();
		
		map.put("selectedRecord", userProfile);
		map.put("recordMode", "READ");
		map.put("centerArea", centerArea);
		centerArea.getChildren().clear();
		Executions.createComponents("UserCRUD.zul", centerArea, map);
	}

	@Command
	public void onDelete(@BindingParam("userRecord") UserProfile userProfile) {
		MyLib.confirm("deleteFirstConfirm", "The Selected user  \""
				+ selectedItem.getUserLoginID() + "\" will be deleted.?",
				"Confirmation", this);

	}

	@NotifyChange("dataSet")
	@Command
	public void doFilter() {
		allReordsInDB = new ArrayList<UserProfile>();
		
		for (Iterator<UserProfile> i = userList.iterator(); i.hasNext();) {
			UserProfile tmp = i.next();
			if (tmp.getFirstName().toLowerCase()
					.indexOf(dataFilter.getFirstName().toLowerCase()) == 0
					&& tmp.getLastName().toLowerCase()
							.indexOf(dataFilter.getLastName().toLowerCase()) == 0
					&& tmp.getEmail().toLowerCase()
							.indexOf(dataFilter.getEmail().toLowerCase()) == 0
					&& tmp.getUserLoginID().toLowerCase()
							.indexOf(dataFilter.getLoginID().toLowerCase()) == 0) {
				allReordsInDB.add(tmp);
			}
		}

	}

	@Command
	public void Logout() {
		Executions.sendRedirect("/j_spring_security_logout");
	}

	@Override
	public void onConfirmClick(String code, int button) {
		if (code.equals("deleteFirstConfirm") && button == Messagebox.YES) {
			MyLib.confirm(
					"deleteSecondConfirm",
					"The Selected user  \""
							+ selectedItem.getUserLoginID()
							+ "\" will be permanently deleted and the action cannot be undone..?",
					"Confirmation", this);
		}
		if (code.equals("deleteSecondConfirm") && button == Messagebox.YES) {

			CRUDService.delete(selectedItem);

			allReordsInDB.remove(allReordsInDB.indexOf(selectedItem));
			BindUtils.postNotifyChange(null, null, UserListVM.this, "dataSet");
			MyLib.showSuccessmessage();

		}
	}
	
	@Command
	public void usuarios() {
		final HashMap<String, Object> map = new HashMap<String, Object>();
		
	
		map.put("centerArea", centerArea);
		centerArea.getChildren().clear();
		Executions.createComponents("/admin/userList.zul", centerArea, map);
	}

	@Command
	public void metas() {
		final HashMap<String, Object> map = new HashMap<String, Object>();
		
	
		map.put("centerArea", centerArea);
		centerArea.getChildren().clear();
		Executions.createComponents("/metaList.zul", centerArea, map);
	}

	
	@Command
	public void digitalizacion() {
		
		final HashMap<String, Object> map = new HashMap<String, Object>();
		
		map.put("centerArea", centerArea);
		centerArea.getChildren().clear();
		Executions.createComponents("/digitalizador/fileList.zul", centerArea, map);
	}

	@Command
	public void indexador() {
		final HashMap<String, Object> map = new HashMap<String, Object>();
		
		map.put("centerArea", centerArea);
		centerArea.getChildren().clear();
		Executions.createComponents("/indexador/fileListIndex.zul", centerArea, map);
	}

}
