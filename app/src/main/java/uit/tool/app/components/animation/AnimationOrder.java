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
		for (T animation : animations) {
			System.out.println(animation);
			System.out.println(animation.getClass().getSimpleName());
			System.out.println(((VertexAnimation) animation).getTarget());
		}
		for (int i = 0; i < animations.size(); i++) {
			VertexAnimation animation = (VertexAnimation) animations.get(i);

			animation.getTransition().setOnFinished((event -> {
//				System.out.printf("Run animation in ", animation.getVertex());
//				System.out.println(this.i);
				this.i = this.i + 1;
				if (this.i < animations.size()) {
					animations.get(this.i).getTransition().play();

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
