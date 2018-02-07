package zkexample.zkoss;

public class DataFileFilter {

	private String fileName = "";
	private String filePath = "";

	public String getFileName() {
		return fileName;
	}

	public void setNombre(String name) {
		this.fileName = fileName == null ? "" : fileName.trim();
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath == null ? "" : filePath.trim();
	}

 
}
