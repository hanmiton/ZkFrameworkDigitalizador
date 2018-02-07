package zkexample.zkoss;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

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
import org.zkoss.util.media.Media;
import org.zkoss.web.theme.StandardTheme.ThemeOrigin;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.UploadEvent;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Center;
import org.zkoss.zul.ListModel;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.theme.Themes;

import zkexample.domain.MetaProfile;
import zkexample.domain.UserProfile;
import zkexample.service.CRUDService;
import zkexample.utilities.MyLib;

public class UserCRUDVM {

	private Center centerArea;

	@WireVariable
	private CRUDService CRUDService;

	private UserProfile selectedRecord;
	private String recordMode;
	private boolean makeAsReadOnly;
	private DataFilter dataFilter = new DataFilter();
	private AImage myImage;
	
	private DataFileFilter dataFileFilter = new DataFileFilter();
	private MetaProfile selectedItem;
	private List<MetaProfile> allReordsInDB = null;
	private List<MetaProfile> metaList = null;
	
	public AImage getMyImage() {
		return myImage;
	}

	public MetaProfile getSelectedItem() {
		return selectedItem;
	}

	public void setSelectedItem(MetaProfile selectedItem) {
		this.selectedItem = selectedItem;
	}
	public void setMyImage(AImage myImage) {
		this.myImage = myImage;
	}

	// Available themes
	private ListModel<String> _themes = null;
	// Current theme name
	private String currentTheme = null;

	public ListModel<String> getThemes() {
		return _themes;
	}

	public String getCurrentTheme() {
		return currentTheme;
	}

	@NotifyChange("currentTheme")
	public void setCurrentTheme(String currentTheme) {
		this.currentTheme = currentTheme;
	}

	public DataFilter getDataFilter() {
		return dataFilter;
	}

	public void setDataFilter(DataFilter dataFilter) {
		this.dataFilter = dataFilter;
	}

	public UserProfile getSelectedRecord() {
		return selectedRecord;
	}

	
	
	public void setSelectedRecord(UserProfile selectedRecord) {
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
			@ExecutionArgParam("selectedRecord") UserProfile userProfile,
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
		myImage = new AImage(Executions.getCurrent().getDesktop().getWebApp()
				.getRealPath("/images")
				+ "/male.png");
		if (recordMode.equals("NEW")) {
			this.selectedRecord = new UserProfile();
			this.selectedRecord.setSystem(0);
		}

		if (recordMode.equals("EDIT")) {
			this.selectedRecord = userProfile;
			if (this.selectedRecord.getUserPhoto() != null)
				myImage = new AImage("userPhoto",
						this.selectedRecord.getUserPhoto());
		}

		if (recordMode == "READ") {
			setMakeAsReadOnly(true);
			this.selectedRecord = userProfile;
			if (this.selectedRecord.getUserPhoto() != null)
				myImage = new AImage("userPhoto",
						this.selectedRecord.getUserPhoto());
		}
	}

	@Command
	public void saveThis(@BindingParam("action") Integer action) {

		if (MyLib.IsValidBean(this.selectedRecord) == false) {
			return;
		}

		if (myImage != null) {
			byte[] bFile = myImage.getByteData();
			this.selectedRecord.setUserPhoto(bFile);
		} else
			this.selectedRecord.setUserPhoto(null);
		CRUDService.Save(this.selectedRecord);
		MyLib.showSuccessmessage();
		if (action == 0) {
			final HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("centerArea", centerArea);
			centerArea.getChildren().clear();
			Executions.createComponents("/admin/userList.zul", centerArea, map);
		}
		if (action == 1) {
			this.selectedRecord = new UserProfile();
			BindUtils.postNotifyChange(null, null, UserCRUDVM.this,
					"selectedRecord");

		}
	}

	@Command
	public void cancel() {
		final HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("centerArea", centerArea);
		centerArea.getChildren().clear();
		Executions.createComponents("/admin/userList.zul", centerArea, map);
	}
	
	public List<MetaProfile> getDataSet() {
		return allReordsInDB;
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
	@NotifyChange("myImage")
	public void removeImage() {

		myImage = null;
	}

	@Command
	@NotifyChange("myImage")
	public void upload(@ContextParam(ContextType.BIND_CONTEXT) BindContext ctx) {

		UploadEvent upEvent = null;
		Object objUploadEvent = ctx.getTriggerEvent();
		if (objUploadEvent != null && (objUploadEvent instanceof UploadEvent)) {
			upEvent = (UploadEvent) objUploadEvent;
		}
		if (upEvent != null) {
			Media media = upEvent.getMedia();
			int lengthofImage = media.getByteData().length;
			if (media instanceof Image) {
				if (lengthofImage > 500 * 1024) {
					Messagebox
							.show("Please Select a Image of size less than 500Kb.");
					return;
				} else {
					myImage = (AImage) media;// Initialize the bind object to
												// show image in zul page and
												// Notify it also
				}
			} else {
				Messagebox.show("The selected File is not an image.");
			}
		}
	}

}
