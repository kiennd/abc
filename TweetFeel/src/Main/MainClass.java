package Main;

import view.TweetFeelView;
import controller.TweetFeelController;

public class MainClass {
	public MainClass() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		TweetFeelView tfv = new TweetFeelView();
		TweetFeelController e = new TweetFeelController();
		e.initTweetFieldView(tfv);
		System.out.println("done");
	}
}

