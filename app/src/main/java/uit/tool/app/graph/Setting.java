package uit.tool.app.graph;

public class Setting {
	private String name;
	private String filepath;
	private boolean isChanges;
	Setting(){

	}
	Setting(String name,String filepath, boolean isChanges){

	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}

	public String getFilepath() {
		return filepath;
	}

	public void setChanges(boolean changes) {
		isChanges = changes;
	}

	public boolean isChanges() {
		return isChanges;
	}
}
