package zkexample.zkoss;



import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;

import org.zkoss.bind.BindContext;
import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.image.AImage;
import org.zkoss.image.Image;
import org.zkoss.io.Files;
import org.zkoss.util.media.AMedia;
import org.zkoss.util.media.Media;
import org.zkoss.web.theme.StandardTheme.ThemeOrigin;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.UploadEvent;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Center;
import org.zkoss.zul.ListModel;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;
import org.zkoss.zul.theme.Themes;

import zkexample.domain.MetaProfile;
import zkexample.service.CRUDService;
import zkexample.utilities.MyLib;

public class MetaCRUDVM {

	private Center centerArea;
	private boolean fileuploaded = false;
	AMedia fileContent;
	   
	@WireVariable
	private CRUDService CRUDService;

	@Wire("#metaCRUD")
	private Window win;
	
	private MetaProfile selectedRecord;
	private String recordMode;
	private boolean makeAsReadOnly;
	private DataFileFilter dataFileFilter = new DataFileFilter();
	 
	// Available themes
	private ListModel<String> _themes = null;
	// Current theme name
	private String currentTheme = null;

	public ListModel<String> getThemes() {
		return _themes;
	}

	//archivo
	public AMedia getFileContent() {
        return fileContent;
 }
 
   public void setFileContent(AMedia fileContent) {
        this.fileContent = fileContent;
 }
 
   public boolean isFileuploaded() {
       return fileuploaded;
    }
 
   public void setFileuploaded(boolean fileuploaded) {
     this.fileuploaded = fileuploaded;
   }
   

   
   //terminacion archivo
	
	
	
	public String getCurrentTheme() {
		return currentTheme;
	}

	@NotifyChange("currentTheme")
	public void setCurrentTheme(String currentTheme) {
		this.currentTheme = currentTheme;
	}

	public DataFileFilter getDataFileFilter() {
		return dataFileFilter;
	}

	public void setDataFileFilter(DataFileFilter dataFileFilter) {
		this.dataFileFilter = dataFileFilter;
	}

	public MetaProfile getSelectedRecord() {
		return selectedRecord;
	}

	public void setSelectedRecord(MetaProfile selectedRecord) {
		this.selectedRecord = selectedRecord;
	}

	public String getRecordMode() {
		return recordMode;
	}

	public void setRecordMode(String recordMode) {
		this.recordMode = recordMode;
	}

	public boolean isMakeAsReadOnly() {
		return makeAsReadOnly;
	}

	public void setMakeAsReadOnly(boolean makeAsReadOnly) {
		this.makeAsReadOnly = makeAsReadOnly;
	}
	


	@AfterCompose
	@NotifyChange("myImage")
	public void initSetup(@ContextParam(ContextType.VIEW) Component view,
			@ExecutionArgParam("selectedRecord") MetaProfile metaProfile,
			@ExecutionArgParam("recordMode") String recordMode,
			@ExecutionArgParam("centerArea") Center centerArea)
			throws IOException {

		Selectors.wireComponents(view, this, false);
		setRecordMode(recordMode);
		CRUDService = (CRUDService) SpringUtil.getBean("CRUDService");
		this.centerArea = centerArea;

		Themes.register("bluetheme", ThemeOrigin.FOLDER);
		Themes.register("greentheme", ThemeOrigin.FOLDER);
		Themes.register("browntheme", ThemeOrigin.FOLDER);
		Themes.register("purpletheme", ThemeOrigin.FOLDER);
		Themes.register("redtheme", ThemeOrigin.FOLDER);

		String[] themes = Themes.getThemes();
		themes = Arrays.copyOf(themes, themes.length + 1);

		// Attempting to switch to a theme that is not registered
		// will switch to the default theme (i.e. breeze)
		themes[themes.length - 1] = "unknown";
		_themes = new ListModelList<String>(themes);

		currentTheme = Themes.getCurrentTheme();
		
		if (recordMode.equals("NEW")) {
			this.selectedRecord = new MetaProfile();
			//this.selectedRecord.setSystem(0);
		}

		if (recordMode.equals("EDIT")) {
			this.selectedRecord = metaProfile;
		}

		if (recordMode == "READ") {
			setMakeAsReadOnly(true);
			this.selectedRecord = metaProfile;
			
		}
	}

	@Command
	public void saveThis(@BindingParam("action") Integer action) {

		if (MyLib.IsValidBean(this.selectedRecord) == false) {
			return;
		}

		
		CRUDService.Save(this.selectedRecord);
		MyLib.showSuccessmessage();
		if (action == 0) {
			final HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("centerArea", centerArea);
			centerArea.getChildren().clear();
			Executions.createComponents("metaList.zul", centerArea, map);
		}
		if (action == 1) {
			this.selectedRecord = new MetaProfile();
			BindUtils.postNotifyChange(null, null, MetaCRUDVM.this,
					"selectedRecord");

		}
	}

	@Command
	public void cancel() {
		final HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("centerArea", centerArea);
		centerArea.getChildren().clear();
		Executions.createComponents("fileList.zul", centerArea, map);
	}
	
	@Command
	public void files() {
		final HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("centerArea", centerArea);
		centerArea.getChildren().clear();
		Executions.createComponents("fileList.zul", centerArea, map);
	}

	@Command
	public void filesIndex() {
		final HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("centerArea", centerArea);
		centerArea.getChildren().clear();
		Executions.createComponents("fileListIndex.zul", centerArea, map);
	}


	
}
