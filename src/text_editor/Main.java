package text_editor;

import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {

	public static void main(String[] args) {
		Application.launch(args);

	}

	public void start(Stage stage) throws Exception {
		Controller controller = new Controller(stage);
		
		stage.getIcons().add(new Image("file:textures//textEditorIcon.png"));
		stage.setTitle("Text Editor - New Document");
		
		controller.start();		
	}

}
