package uit.tool.app.components.animation;

import uit.tool.app.components.animation.VisualAnimation;

import java.util.ArrayList;

public class AnimationOrder<T extends VisualAnimation> {

	private ArrayList<T> animations;
	private int i;

	public AnimationOrder() {

	}

	public void setAnimations(ArrayList<T> animations) {
		this.animations = animations;
		i = 0;
	}


	public void run() {
		for (int i = 0; i < animations.size(); i++) {
			VisualAnimation animation = animations.get(i);
			animation.getTransition().setOnFinished((event -> {
				if (this.i < animations.size()) {
					animations.get(this.i).getTransition().play();
					this.i = this.i + 1;
//					System.out.println(this.i);
				}
			}));
		}
		animations.get(0).getTransition().play();
	}

	public void addAnimation() {

	}

	void executeSynchronous() {

	}

	void executeAsynchronous() {

	}

}
