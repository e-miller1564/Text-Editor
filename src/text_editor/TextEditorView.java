package text_editor;

import java.io.File;

import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

public class TextEditorView extends BorderPane {
	public VBox menuBarBox;
	public FileChooser fileChooser;
	
	public MenuBar menuBar;
	public Menu menuFile;
	public Menu menuView;
	public Menu menuEdit;
	public Menu menuAction;
	public ContextMenu contextMenuEdit;
	
	public MenuItem menuItemNew;
	public MenuItem menuItemOpen;
	public MenuItem menuItemClose;
	public MenuItem menuItemSave;
	public MenuItem menuItemExit;
	
	public CheckMenuItem menuItemWordCount;
	public CheckMenuItem menuItemSentenceCount;
	public CheckMenuItem menuItemFleschScore;
	
	public MenuItem menuItemCopy;
	public MenuItem menuItemCut;
	public MenuItem menuItemDelete;
	public MenuItem menuItemPaste;
	
	public MenuItem menuItemMarkov;
	public MenuItem menuItemTruncate;
	public MenuItem menuItemRunAlgorithms;
	public MenuItem menuItemShow;
	public MenuItem menuItemPlot;
	public MenuItem menuItemDiscussion;
	public CheckMenuItem menuItemSpellChecker;
	
	public MenuItem menuItemCopy2;
	public MenuItem menuItemCut2;
	public MenuItem menuItemDelete2;
	public MenuItem menuItemPaste2;
	
	public TextArea txtArea;
	
	public HBox statusBar;
	public HBox statusWordsBox;
	public HBox statusSentencesBox;
	public HBox statusFleschScoreBox;
	public Text statusWords;
	public Text statusSentences;
	public Text statusFleschScore;
	
	public TextArea txtAreaSpellCheck;
	
	public TextEditorView() {
		fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Text Files", "*.txt"));
		fileChooser.setInitialDirectory(new File("."));
		menuBar = new MenuBar();
		
		menuFile = new Menu("File");
		menuItemNew = new MenuItem("New");
		menuItemOpen = new MenuItem("Open");
		menuItemClose = new MenuItem("Close");
		menuItemSave = new MenuItem("Save");
		menuItemExit = new MenuItem("Exit");
		menuFile.getItems().addAll(menuItemNew, menuItemOpen, menuItemClose, menuItemSave, menuItemExit);
		
		menuView = new Menu("View");
		menuItemWordCount = new CheckMenuItem("Word Count");
		menuItemSentenceCount = new CheckMenuItem("Sentence Count");
		menuItemFleschScore = new CheckMenuItem("Flesch Score");
		menuView.getItems().addAll(menuItemWordCount, menuItemSentenceCount, menuItemFleschScore);
		
		menuEdit = new Menu("Edit");
		contextMenuEdit = new ContextMenu();
		menuItemCopy = new MenuItem("Copy");
		menuItemCopy2 = new MenuItem("Copy");
		menuItemCut = new MenuItem("Cut");
		menuItemCut2 = new MenuItem("Cut");
		menuItemDelete = new MenuItem("Delete");
		menuItemDelete2 = new MenuItem("Delete");
		menuItemPaste = new MenuItem("Paste");
		menuItemPaste2 = new MenuItem("Paste");
		menuEdit.getItems().addAll(menuItemCopy, menuItemCut,  menuItemDelete, menuItemPaste);
		contextMenuEdit.getItems().addAll(menuItemCopy2, menuItemCut2,  menuItemDelete2, menuItemPaste2);
		
		menuAction = new Menu("Action");
		menuItemMarkov = new MenuItem("Markov");
		menuItemTruncate = new MenuItem("Truncate");
		menuItemRunAlgorithms = new MenuItem("Run Algorithms");
		menuItemShow = new MenuItem("Show");
		menuItemPlot = new MenuItem("Plot");
		menuItemDiscussion = new MenuItem("Discussion");
		menuItemSpellChecker = new CheckMenuItem("Spell Checker");
		menuAction.getItems().addAll(menuItemMarkov, menuItemTruncate, menuItemRunAlgorithms, menuItemShow, menuItemPlot, menuItemDiscussion, menuItemSpellChecker);
		
		menuBar.getMenus().addAll(menuFile, menuView, menuEdit, menuAction);
		menuBarBox = new VBox(menuBar);

		txtArea = new TextArea();
		txtArea.setWrapText(true);
		txtArea.setFont(Font.font("monospace", FontWeight.NORMAL, 16));
		txtArea.setContextMenu(contextMenuEdit);
		
		statusBar = new HBox();
		
		statusWordsBox = new HBox();
		statusWordsBox.setPrefWidth(200);
		statusWordsBox.setBorder(new Border(new BorderStroke(Color.LIGHTGREY, BorderStrokeStyle.SOLID, null, null)));
		statusSentencesBox = new HBox();
		statusSentencesBox.setPrefWidth(200);
		statusSentencesBox.setBorder(new Border(new BorderStroke(Color.LIGHTGREY, BorderStrokeStyle.SOLID, null, null)));
		statusFleschScoreBox = new HBox();
		statusBar.setHgrow(statusFleschScoreBox, Priority.ALWAYS);
		statusFleschScoreBox.setBorder(new Border(new BorderStroke(Color.LIGHTGREY, BorderStrokeStyle.SOLID, null, null)));
		
		
		statusWords = new Text("Word Count: " + 0);
		statusWords.setVisible(false);
		statusWordsBox.getChildren().add(statusWords);
		
		statusSentences = new Text("Sentence Count: " + 0);
		statusSentences.setVisible(false);
		statusSentencesBox.getChildren().add(statusSentences);
		
		statusFleschScore = new Text("Flesch Score: " + 0);
		statusFleschScore.setVisible(false);
		statusFleschScoreBox.getChildren().add(statusFleschScore);
		
		statusBar.getChildren().addAll(statusWordsBox, statusSentencesBox, statusFleschScoreBox);
		
		txtAreaSpellCheck = new TextArea();
		txtAreaSpellCheck.setEditable(false);
		
		this.setTop(menuBarBox);
		this.setCenter(txtArea);
		this.setBottom(statusBar);
		this.setRight(null);
	}
}
