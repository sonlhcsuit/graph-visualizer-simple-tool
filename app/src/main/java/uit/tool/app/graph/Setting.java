package uit.tool.app.graph;

public class Setting {
	private String name;
	private String filepath;
	private boolean isWeighted;
	private boolean isDirected;

	Setting(String name, String filepath, boolean isWeighted, boolean isDirected) {
		this.name = name;
		this.filepath = filepath;
		this.isWeighted = isWeighted;
		this.isDirected = isDirected;
	}

	Setting() {
		this("blank", null, true, true);
	}

	Setting(String name) {
		this(name, null, true, true);
	}

	Setting(String name, String filepath) {
		this(name, filepath, true, true);
	}

	Setting(String name, String filepath, boolean isWeighted) {
		this(name, filepath, isWeighted, true);
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

	public void setDirected(boolean directed) {
		isDirected = directed;
	}

	public boolean isDirected() {
		return isDirected;
	}

	public void setWeighted(boolean weighted) {
		isWeighted = weighted;
	}

	public boolean isWeighted() {
		return isWeighted;
	}
}
