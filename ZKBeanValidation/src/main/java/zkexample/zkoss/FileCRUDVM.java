package zkexample.zkoss;



import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;

import javax.xml.bind.DatatypeConverter;

import org.apache.commons.lang3.RandomStringUtils;
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

import zkexample.domain.FileProfile;
import zkexample.service.CRUDService;
import zkexample.utilities.MyLib;

public class FileCRUDVM {

	private Center centerArea;
	private boolean fileuploaded = false;
	AMedia fileContent;
	   
	@WireVariable
	private CRUDService CRUDService;

	@Wire("#fileCRUD")
	private Window win;
	
	private FileProfile selectedRecord;
	private String recordMode;
	private boolean makeAsReadOnly;
	private DataFileFilter dataFileFilter = new DataFileFilter();
	private AImage myImage;
	 private String filePath = "C:\\Users\\RADEON\\workspace\\PDFs\\Eval2_Becerra_Bryan.pdf";
	 
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
   
   @Command
   @NotifyChange("fileuploaded")
  public void onUploadPDF(
           @ContextParam(ContextType.BIND_CONTEXT) BindContext ctx)
           throws IOException {
	   
       UploadEvent upEvent = null;
    Object objUploadEvent = ctx.getTriggerEvent();
     if (objUploadEvent != null && (objUploadEvent instanceof UploadEvent)) {
           upEvent = (UploadEvent) objUploadEvent;
    }
      if (upEvent != null) {
    	 //multi-archivos 
         Media[] medias = upEvent.getMedias();
         
         for (int x=0;x<medias.length;x++){

       	  Media media = medias[x];
       	  Calendar now = Calendar.getInstance();
             int year = now.get(Calendar.YEAR);
             int month = now.get(Calendar.MONTH); // Note: zero based!
              int day = now.get(Calendar.DAY_OF_MONTH);
             filePath = "C:\\Users\\RADEON\\Documents\\workspace-sts-3.9.2.RELEASE\\Proyecto";
             // filePath= "fad";
             String yearPath = "\\" + "PDFs" + "\\" + year + "\\" + month + "\\"
                    + day + "\\";
              filePath = filePath + yearPath;
              
            File baseDir = new File(filePath);
             if (!baseDir.exists()) {
                   baseDir.mkdirs();
              }
             
             
           // 
            
           
     	   
     	  
        	String hash = "35454B055CC325EA1AF2126E27707052";
            String password = "Java";
            String myHash;
        	MessageDigest md;
        	
			try {
				md = MessageDigest.getInstance("MD5");
				 md.update(password.getBytes());
		            byte[] digest = md.digest();
		             myHash = DatatypeConverter
		              .printHexBinary(digest).toUpperCase();
		             System.out.print(myHash);
		             String ext = "pdf";
		         	 String name = String.format("%s.%s", RandomStringUtils.randomAlphanumeric(8), ext);
		             selectedRecord.setNameFile(myHash + name);
		             filePath = filePath + myHash + name;
		             selectedRecord.setPathFile(filePath);
		             
		         	if (MyLib.IsValidBean(this.selectedRecord) == false) {
		         		return;
		         	}

		          	CRUDService.Save(this.selectedRecord);
		          	
		          	Files.copy(new File(filePath),
		                    media.getStreamData());
		          Messagebox.show("File Sucessfully uploaded in the path [ ."
		                + filePath + " ]");
		          fileuploaded = true;
		          	long j  =  this.selectedRecord.getId();
		        	MyLib.showSuccessmessage();
		        	
		        		this.selectedRecord = new FileProfile();
		        		BindUtils.postNotifyChange(null, null, FileCRUDVM.this,
		        				"selectedRecord");
		         	
		             
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
           
			
			
        	
        				
         }
         
         
       	  
         final HashMap<String, Object> map = new HashMap<String, Object>();
 		map.put("centerArea", centerArea);
 		centerArea.getChildren().clear();
 		Executions.createComponents("/digitalizador/fileList.zul", centerArea, map);
         
     }
  

	
  }

  @Command
   @NotifyChange("fileContent")
   public void showPDF(String path) throws IOException {
	 System.out.println(path);
	 System.out.println(path);
	 System.out.println(path);
	 System.out.println(path);
	 
	FileProfile fileSession = this.selectedRecord;
	 String filePath  = fileSession.getPathFile();
     File f = new File(filePath);
       Messagebox.show(" " + filePath);
       byte[] buffer = new byte[(int) f.length()];
    FileInputStream fs = new FileInputStream(f);
       fs.read(buffer);
       fs.close();
    ByteArrayInputStream is = new ByteArrayInputStream(buffer);
    fileContent = new AMedia("report", "pdf", "application/pdf", is);


  }

   
   //terminacion archivo
	
	
	
	public String getCurrentTheme() {
		return currentTheme;
	}

	@NotifyChange("currentTheme")
	public void setCurrentTheme(String currentTheme) {
		this.currentTheme = currentTheme;
	}
	
	

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public DataFileFilter getDataFileFilter() {
		return dataFileFilter;
	}

	public void setDataFileFilter(DataFileFilter dataFileFilter) {
		this.dataFileFilter = dataFileFilter;
	}

	public FileProfile getSelectedRecord() {
		return selectedRecord;
	}

	public void setSelectedRecord(FileProfile selectedRecord) {
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
			@ExecutionArgParam("selectedRecord") FileProfile fileProfile,
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
			this.selectedRecord = new FileProfile();
			//this.selectedRecord.setSystem(0);
		}

		if (recordMode.equals("EDIT")) {
			//setMakeAsReadOnly(true);
			this.selectedRecord = fileProfile;
		}

		if (recordMode.equals("READ")) {
			setMakeAsReadOnly(true);
			this.selectedRecord = fileProfile;
			
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
			Executions.createComponents("/digitalizador/fileList.zul", centerArea, map);
		}
		if (action == 1) {
			this.selectedRecord = new FileProfile();
			BindUtils.postNotifyChange(null, null, FileCRUDVM.this,
					"selectedRecord");

		}
	}

	@Command
	public void cancel() {
		final HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("centerArea", centerArea);
		centerArea.getChildren().clear();
		Executions.createComponents("/digitalizador/fileList.zul", centerArea, map);
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
