package zkexample.zkoss;

import java.io.File;
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
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Center;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.West;

import zkexample.domain.FileProfile;
import zkexample.service.CRUDService;
import zkexample.utilities.ConfirmResponse;
import zkexample.utilities.MyLib;

public class FileListIndexVM implements ConfirmResponse {

	private Center centerArea;
	//private West westArea;
	private DataFileFilter dataFileFilter = new DataFileFilter();

	@WireVariable
	private CRUDService CRUDService;

	private FileProfile selectedItem;
	private List<FileProfile> allReordsInDB = null;
	private List<FileProfile> fileList = null;

	//aqui me quedo 
	
	public FileProfile getSelectedItem() {
		return selectedItem;
	}

	public void setSelectedItem(FileProfile selectedItem) {
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
			@ExecutionArgParam("centerArea") Center centerArea) {
		Selectors.wireComponents(view, this, false);
		this.centerArea = centerArea;
		CRUDService = (CRUDService) SpringUtil.getBean("CRUDService");
		allReordsInDB = CRUDService.getAll(FileProfile.class);
		fileList = new ArrayList<FileProfile>((allReordsInDB));

	}

	public List<FileProfile> getDataSet() {
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
		Executions.createComponents("FileIndexCRUD.zul", centerArea, map);
	
	}

	@Command
	public void onEdit(@BindingParam("fileRecord") FileProfile fileProfile) {

		final HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("selectedRecord", fileProfile);
		map.put("recordMode", "EDIT");
		map.put("centerArea", centerArea);
		centerArea.getChildren().clear();
		Executions.createComponents("FileIndexCRUD.zul", centerArea, map);
	}

	@Command
	public void openAsReadOnly(
			@BindingParam("fileRecord") FileProfile fileProfile) {

		final HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("selectedRecord", fileProfile);
		map.put("recordMode", "READ");
		map.put("centerArea", centerArea);
		centerArea.getChildren().clear();
		Executions.createComponents("/FileIndexCRUD.zul", centerArea, map);
	}

	@Command
	public void onDelete(@BindingParam("fileRecord") FileProfile fileProfile) {
		MyLib.confirm("deleteFirstConfirm", "Archivo seleccionado \""
				+ selectedItem.getNameFile() + "\" para ser borrado.?",
				"Confirmation", this);
		try{
			String path = fileProfile.getPathFile();
    		File file = new File(path);

    		if(file.delete()){
    			System.out.println(file.getName() + " is deleted!");
    		}else{
    			System.out.println("Delete operation is failed.");
    		}

    	}catch(Exception e){

    		e.printStackTrace();

    	}


	}

	@NotifyChange("dataSet")
	@Command
	public void doFilter() {
		allReordsInDB = new ArrayList<FileProfile>();
		;
		for (Iterator<FileProfile> i = fileList.iterator(); i.hasNext();) {
			FileProfile tmp = i.next();
			if (tmp.getNameFile().toLowerCase()
					.indexOf(dataFileFilter.getFileName().toLowerCase()) == 0
					&& tmp.getPathFile().toLowerCase()
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
							+ selectedItem.getNameFile()
							+ "\" will be permanently deleted and the action cannot be undone..?",
					"Confirmation", this);
		}
		if (code.equals("deleteSecondConfirm") && button == Messagebox.YES) {

			CRUDService.delete(selectedItem);

			allReordsInDB.remove(allReordsInDB.indexOf(selectedItem));
			BindUtils.postNotifyChange(null, null, FileListIndexVM.this, "dataSet");
			MyLib.showSuccessmessage();

		}
	}
	
	@Command
	public void usuario() {
		final HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("centerArea", centerArea);
		centerArea.getChildren().clear();
		Executions.createComponents("userList.zul", centerArea, map);
	}

	@Command
	public void digitalizacion() {
		final HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("centerArea", centerArea);
		centerArea.getChildren().clear();
		Executions.createComponents("fileList.zul", centerArea, map);
	}

}
