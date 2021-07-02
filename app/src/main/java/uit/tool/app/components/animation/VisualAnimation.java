package uit.tool.app.components.animation;

import javafx.animation.Transition;
import uit.tool.app.components.visualizerComponent.graphComponent.VertexView;

public abstract class VisualAnimation {

	VisualAnimation(){

	}

	abstract public void setTransition(Transition transition);
	abstract public Transition getTransition();


	public void play(){
		System.out.println("Play an animation");
	}
}
