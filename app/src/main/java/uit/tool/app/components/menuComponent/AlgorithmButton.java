package uit.tool.app.components.menuComponent;


import javafx.beans.NamedArg;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.GridPane;
import uit.tool.app.interfaces.Loader;

public class AlgorithmButton extends Button implements Loader {
	public AlgorithmButton(@NamedArg("row") int row,@NamedArg("col") int col,
						   @NamedArg("text") String text,@NamedArg(value = "span",defaultValue = "1") int span){
		Loader.loadFXML(this);
		GridPane.setColumnIndex(this,col);
		GridPane.setRowIndex(this,row);
		GridPane.setColumnSpan(this,span);
		GridPane.setMargin(this,new Insets(10,10,0,10));
		setMaxWidth(Double.MAX_VALUE);
		setText(text);
		Tooltip t = new Tooltip();
		t.textProperty().bind(textProperty());
		setTooltip(t);
	}

	public void initialize(){
	}
}
