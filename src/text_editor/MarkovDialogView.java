package text_editor;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

public class MarkovDialogView extends BorderPane {

	public GridPane userInputGrid;
	
	public Text txtStartingWord;
	public Text txtNumOfWords;
	public TextField tfStartingWord;
	public TextField tfNumOfWords;
	public Button btnGenerate;
	
	public MarkovDialogView() {
		
		userInputGrid = new GridPane();
		userInputGrid.setPadding(new Insets(30));
		txtStartingWord = new Text("Starting Word");
		tfStartingWord = new TextField();
		txtNumOfWords = new Text("Number of Words");
		tfNumOfWords = new TextField();
		btnGenerate = new Button("Generate");
		btnGenerate.setPrefSize(90, 30);
		userInputGrid.setAlignment(Pos.CENTER);
		userInputGrid.setVgap(10);
		userInputGrid.setHgap(20);
		userInputGrid.add(txtStartingWord, 0, 0);
		userInputGrid.add(tfStartingWord, 0, 1);
		userInputGrid.add(txtNumOfWords, 2, 0);
		userInputGrid.add(tfNumOfWords, 2, 1);
		userInputGrid.add(btnGenerate, 1, 3);

		this.setCenter(userInputGrid);
	}
}
