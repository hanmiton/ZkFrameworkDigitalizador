package zkexample.zkoss;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.image.AImage;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Center;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.West;

import zkexample.domain.MetaProfile;
import zkexample.domain.UserProfile;
import zkexample.service.CRUDService;
import zkexample.utilities.ConfirmResponse;
import zkexample.utilities.MyLib;

public class MetaListVM implements ConfirmResponse {

	private Center centerArea;
	//private West westArea;
	private DataFileFilter dataFileFilter = new DataFileFilter();

	@WireVariable
	private CRUDService CRUDService;
	private UserProfile selectedRecord;
	private MetaProfile selectedItem;
	private List<MetaProfile> allReordsInDB = null;
	private List<MetaProfile> metaList = null;
	boolean makeAsReadOnly;
	
	//aqui me quedo 
	public UserProfile getSelectedRecord() {
		
		return selectedRecord;
	}


	public boolean isMakeAsReadOnly() {
		
		return makeAsReadOnly;
	}

	public void setMakeAsReadOnly(boolean makeAsReadOnly) {
		this.makeAsReadOnly = makeAsReadOnly;
	}
	
	
	public void setSelectedRecord(UserProfile selectedRecord) {
		this.selectedRecord = selectedRecord;
	}
	
	public MetaProfile getSelectedItem() {
		return selectedItem;
	}

	public void setSelectedItem(MetaProfile selectedItem) {
		this.selectedItem = selectedItem;
	}

	public DataFileFilter getDataFilter() {
		return dataFileFilter;
	}

	public void setDataFileFilter(DataFileFilter dataFileFilter) {
		this.dataFileFilter = dataFileFilter;
	}

	@AfterCompose
	public void initSetup(@ContextParam(ContextType.VIEW) Component view,
			@ExecutionArgParam("selectedRecord") UserProfile userProfile,
			@ExecutionArgParam("recordMode") String recordMode,
			@ExecutionArgParam("centerArea") Center centerArea) {

		
			this.selectedRecord = userProfile;
		
		

		
		Selectors.wireComponents(view, this, false);
		this.centerArea = centerArea;
		CRUDService = (CRUDService) SpringUtil.getBean("CRUDService");
		allReordsInDB = CRUDService.getAll(MetaProfile.class);
		metaList = new ArrayList<MetaProfile>((allReordsInDB));

	}

	public List<MetaProfile> getDataSet() {
		return allReordsInDB;
	}

	@Command
	public void onAddNew() {
		final HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("selectedRecord", null);
		map.put("recordMode", "NEW");
		map.put("centerArea", centerArea);
		
		
		centerArea.getChildren().clear();
		//falta creacion de file crud
		Executions.createComponents("MetaCRUD.zul", centerArea, map);
	
	}

	@Command
	public void onEdit(@BindingParam("metaRecord") MetaProfile metaProfile) {

		final HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("selectedRecord", metaProfile);
		map.put("recordMode", "EDIT");
		map.put("centerArea", centerArea);
		centerArea.getChildren().clear();
		Executions.createComponents("MetaCRUD.zul", centerArea, map);
	}

	@Command
	public void openAsReadOnly(
			@BindingParam("metaRecord") MetaProfile metaProfile) {

		final HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("selectedRecord", metaProfile);
		map.put("recordMode", "READ");
		map.put("centerArea", centerArea);
		centerArea.getChildren().clear();
		Executions.createComponents("MetaCRUD.zul", centerArea, map);
	}

	@Command
	public void onDelete(@BindingParam("fileRecord") MetaProfile metaProfile) {
		MyLib.confirm("deleteFirstConfirm", "Archivo seleccionado \""
				+ selectedItem.getNameMeta() + "\" para ser borrado.?",
				"Confirmation", this);

	}

	@NotifyChange("dataSet")
	@Command
	public void doFilter() {
		allReordsInDB = new ArrayList<MetaProfile>();
		
		for (Iterator<MetaProfile> i = metaList.iterator(); i.hasNext();) {
			MetaProfile tmp = i.next();
			if (tmp.getNameMeta().toLowerCase()
					.indexOf(dataFileFilter.getFileName().toLowerCase()) == 0
					&& tmp.getValorMeta().toLowerCase()
							.indexOf(dataFileFilter.getFilePath().toLowerCase()) == 0) {
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
							+ selectedItem.getNameMeta()
							+ "\" will be permanently deleted and the action cannot be undone..?",
					"Confirmation", this);
		}
		if (code.equals("deleteSecondConfirm") && button == Messagebox.YES) {

			CRUDService.delete(selectedItem);

			allReordsInDB.remove(allReordsInDB.indexOf(selectedItem));
			BindUtils.postNotifyChange(null, null, MetaListVM.this, "dataSet");
			MyLib.showSuccessmessage();

		}
	}

}
