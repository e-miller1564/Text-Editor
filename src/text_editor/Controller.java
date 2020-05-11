package text_editor;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.scene.Scene;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.stage.Stage;

public class Controller {
	
	private Stage mainStage;
	private Stage markovDialog;
	private Stage resultsPlot;
	private Scene scene;
	private TextEditorView textEditorView;
	private MarkovDialogView markovDialogView;
	private ResultsPlotView resultsPlotView;
	
	private File file;
	private File dictionaryFile;
	
	Clipboard clipboard = Clipboard.getSystemClipboard();
	ClipboardContent content = new ClipboardContent();
	
	private int numOfWords = 0;
	private int numOfSentences = 0;
	private int numOfSyllables = 0;
	private double fleschScore = 0;
	
	private LinkedList<MasterLink> masterLinkedList;
	private FileReader fileReader;
	private String firstWordMarkov;
	private String numOfWordsMarkov;
	private File[] truncatedFiles;
	private long[][] algorithmTimings;
	private Dictionary spellCheckDictionary;
	
	public Controller(Stage stage) {
		this.textEditorView = new TextEditorView();
		this.markovDialogView = new MarkovDialogView();
		this.resultsPlotView = new ResultsPlotView();
		
		truncatedFiles = new File[20];
		algorithmTimings = new long[20][2];
		dictionaryFile = new File("data//dictionary.txt");
		try {
			spellCheckDictionary = new Dictionary(new FileReader(dictionaryFile));
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		
		markovDialog = new Stage();
		markovDialog.setTitle("Markov");
		markovDialog.setScene(new Scene(markovDialogView, 500, 200));
		
		resultsPlot = new Stage();
		resultsPlot.setTitle("Single vs Triple Loop Algorithm Results");
		resultsPlot.setScene(new Scene(resultsPlotView, 500, 400));
		
		this.scene = new Scene(textEditorView, 1270, 720);
		this.mainStage = stage;
		stage.setOnCloseRequest(evt->{
			askSave();
			
			if(markovDialog.isShowing()) {
				markovDialog.close();
			}
			if(resultsPlot.isShowing()) {
				resultsPlot.close();
			}
		});
		
		configureActions();
		stage.setScene(scene);
	}
	
	public void configureActions() {
		this.textEditorView.menuItemNew.setOnAction(evt->{
			askSave();
			this.textEditorView.txtArea.setText("");
			this.mainStage.setTitle("Text Editor - New Document");
			spellCheck();
			updateStatusBar();
		});
		
		this.textEditorView.menuItemOpen.setOnAction(evt->{
			this.file = textEditorView.fileChooser.showOpenDialog(this.mainStage);
			if(this.file != null && this.file.isFile()) {
				FileReader fileReader = new FileReader(this.file);
				this.mainStage.setTitle("Text Editor - " + this.file.getName());
				try {
					this.textEditorView.txtArea.setText(fileReader.readFile());
					spellCheck();
					updateStatusBar();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
			}
		});
		
		this.textEditorView.menuItemClose.setOnAction(evt->{
			//Isn't this the same as exit?
			askSave();
			System.exit(0);
		});
		
		this.textEditorView.menuItemSave.setOnAction(evt->{
			save(textEditorView.fileChooser.showSaveDialog(this.mainStage));
		});
		
		this.textEditorView.menuItemExit.setOnAction(evt->{
			askSave();
			System.exit(0);
		});
		
		this.textEditorView.menuItemWordCount.setOnAction(evt->{
			if(this.textEditorView.statusWords.isVisible()) {
				this.textEditorView.statusWords.setVisible(false);
			}
			else {
				this.textEditorView.statusWords.setVisible(true);
			}
		});
		
		this.textEditorView.menuItemSentenceCount.setOnAction(evt->{
			if(this.textEditorView.statusSentences.isVisible()) {
				this.textEditorView.statusSentences.setVisible(false);
			}
			else {
				this.textEditorView.statusSentences.setVisible(true);
			}
		});

		this.textEditorView.menuItemFleschScore.setOnAction(evt->{
			if(this.textEditorView.statusFleschScore.isVisible()) {
				this.textEditorView.statusFleschScore.setVisible(false);
			}
			else {
				this.textEditorView.statusFleschScore.setVisible(true);
			}
		});
		
		this.textEditorView.menuItemCopy.setOnAction(evt->{
			String selectedText = this.textEditorView.txtArea.getSelectedText().toString();
			content.putString(selectedText);
			clipboard.setContent(content);
		});
		
		this.textEditorView.menuItemCopy2.setOnAction(evt->{
			String selectedText = this.textEditorView.txtArea.getSelectedText().toString();
			content.putString(selectedText);
			clipboard.setContent(content);
		});
		
		this.textEditorView.menuItemCut.setOnAction(evt->{
			String selectedText = this.textEditorView.txtArea.getSelectedText().toString();
			this.textEditorView.txtArea.deleteText(this.textEditorView.txtArea.getSelection());
			content.putString(selectedText);
			clipboard.setContent(content);
		});
		
		this.textEditorView.menuItemCut2.setOnAction(evt->{
			String selectedText = this.textEditorView.txtArea.getSelectedText().toString();
			this.textEditorView.txtArea.deleteText(this.textEditorView.txtArea.getSelection());
			content.putString(selectedText);
			clipboard.setContent(content);
		});
		
		this.textEditorView.menuItemDelete.setOnAction(evt->{
			this.textEditorView.txtArea.deleteText(this.textEditorView.txtArea.getSelection());
		});
		
		this.textEditorView.menuItemDelete2.setOnAction(evt->{
			this.textEditorView.txtArea.deleteText(this.textEditorView.txtArea.getSelection());
		});
		
		this.textEditorView.menuItemPaste.setOnAction(evt->{
			this.textEditorView.txtArea.deleteText(this.textEditorView.txtArea.getSelection());
			this.textEditorView.txtArea.insertText(this.textEditorView.txtArea.getCaretPosition(), clipboard.getString());
		});
		
		this.textEditorView.menuItemPaste2.setOnAction(evt->{
			this.textEditorView.txtArea.deleteText(this.textEditorView.txtArea.getSelection());
			this.textEditorView.txtArea.insertText(this.textEditorView.txtArea.getCaretPosition(), clipboard.getString());
		});
		
		this.textEditorView.menuItemMarkov.setOnAction(evt->{
			markov();
		});
		
		this.textEditorView.menuItemTruncate.setOnAction(evt->{
			try {
				truncate();
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
		
		this.textEditorView.menuItemRunAlgorithms.setOnAction(evt->{
			try {
				runAlgorithms();
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
		
		this.textEditorView.menuItemShow.setOnAction(evt->{
			File results = new File("results//results.txt");
			if(results != null) {
				FileReader fileReader = new FileReader(results);
				this.mainStage.setTitle("Text Editor - " + results.getName());
				try {
					this.textEditorView.txtArea.setText(fileReader.readFile());
					updateStatusBar();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		
		this.textEditorView.menuItemPlot.setOnAction(evt->{
			if(algorithmTimings[0][0] == 0) {
				createAlert("No Results to Display", "Please open a file and run Truncate followed by Run Algorithms!").show();
			}
			else {
				insertData();
				resultsPlot.show();
			}
		});
		
		this.textEditorView.menuItemDiscussion.setOnAction(evt->{
			File discussion = new File("results//discussion.txt");
			if(discussion != null) {
				FileReader fileReader = new FileReader(discussion);
				this.mainStage.setTitle("Text Editor - " + discussion.getName());
				try {
					this.textEditorView.txtArea.setText(fileReader.readFile());
					updateStatusBar();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		
		this.markovDialogView.btnGenerate.setOnAction(evt->{
			firstWordMarkov = this.markovDialogView.tfStartingWord.getText();
			numOfWordsMarkov = this.markovDialogView.tfNumOfWords.getText();
			markovDialog.close();
		});
		
		this.textEditorView.menuItemSpellChecker.setOnAction(evt->{
			if(this.textEditorView.getRight() == null) {
				this.textEditorView.setRight(this.textEditorView.txtAreaSpellCheck);
			}
			else {
				this.textEditorView.setRight(null);
			}
		});
		
		this.textEditorView.txtArea.setOnKeyReleased(evt->{
			updateStatusBar();
			spellCheck();
		});
		
	}
	
	public void calculateFleschScore() {
		if(numOfSentences != 0 && numOfWords != 0) {
			fleschScore = ((0.39 * ((double)numOfWords/numOfSentences)) + (11.8 * ((double)numOfSyllables/numOfWords)) - 15.59);
			if(fleschScore < 0) {
				fleschScore = 0;
			}
		}
		else {
			fleschScore = 0;
		}
	}
	
	public void save(File file) {
		PrintWriter pw = null;
		try {
			if(file != null) {
				pw = new PrintWriter(file);
				pw.print(this.textEditorView.txtArea.getText());
				pw.close();
				this.mainStage.setTitle("Text Editor - " + file.getName());
				this.file = file;
			}
		}
		catch(FileNotFoundException fe) {
			fe.printStackTrace();
		}
	}
	
	public void saveLinkedList(File file, LinkedList<MasterLink> masterList) {
		try {
			ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(file));
			output.writeObject(masterList);
			output.close();
		} 
		catch (IOException e) {
			e.printStackTrace();	
		}
	}
	
	public LinkedList<MasterLink> loadLinkedList(File file) {
		LinkedList<MasterLink> masterList = null;
		if(file.exists()) {
			try {
				ObjectInputStream input = new ObjectInputStream(new FileInputStream(file));
				Object o = input.readObject();
				if(o.getClass() == LinkedList.class) {
					masterList = (LinkedList<MasterLink>) o;
				}
				input.close();
			}
			catch (IOException e) {
				e.printStackTrace();
			} 
			catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		return masterList;
	}
	
	public void insertData() {
		if(!this.resultsPlotView.singleLoopSeries.getData().isEmpty() || !this.resultsPlotView.tripleLoopSeries.getData().isEmpty()) {
			this.resultsPlotView.singleLoopSeries.getData().clear();
			this.resultsPlotView.tripleLoopSeries.getData().clear();
		}
		this.resultsPlotView.yAxis.setLowerBound(algorithmTimings[0][1] - 10);
		this.resultsPlotView.yAxis.setUpperBound(algorithmTimings[19][0] + 10);
		this.resultsPlotView.yAxis.setTickUnit(100);
		int percentOfOriginalFile = 5;
		for(int i = 0; i < algorithmTimings.length; i++) {
			for(int j = 0; j < algorithmTimings[i].length; j++) {		
				if(j == 0) {
					this.resultsPlotView.singleLoopSeries.getData().add(new XYChart.Data(percentOfOriginalFile, algorithmTimings[i][j]));
				}
				else {
					this.resultsPlotView.tripleLoopSeries.getData().add(new XYChart.Data(percentOfOriginalFile, algorithmTimings[i][j]));
				}
			}
				percentOfOriginalFile += 5;
		}
	}

	
	public long singleLoopAlgorithm(String words) {
		long start = System.currentTimeMillis();
		numOfWords = 0;
		numOfSentences = 0;
		numOfSyllables = 0;
		
		String wordAndSentenceRegex = "([.?!]+)|(([a-zA-Z0-9]+'?[a-zA-Z0-9]*))";
		Pattern wordAndSentencePattern = Pattern.compile(wordAndSentenceRegex);
		Matcher wordAndSentenceMatcher = wordAndSentencePattern.matcher(words);
		
		while(wordAndSentenceMatcher.find()) {
			Word word = new Word(wordAndSentenceMatcher.group());
			if(word.isWord()) {
				numOfWords++;
				numOfSyllables += word.getNumOfSyllables();
			}
			else {
				numOfSentences++;
			}
		}
		calculateFleschScore();
		return (System.currentTimeMillis() - start);
	}

	
	public long tripleLoopAlgorithm(String words) {
		long start = System.currentTimeMillis();
		numOfWords = 0;
		numOfSentences = 0;
		numOfSyllables = 0;
		
		String wordRegex = "([a-zA-Z0-9]+'?[a-zA-Z0-9]*)";
		Pattern wordPattern = Pattern.compile(wordRegex);
		Matcher matcher = wordPattern.matcher(words);
		
		String sentenceRegex = "[.?!]+";
		Pattern sentencePattern = Pattern.compile(sentenceRegex);
		
		String syllableRegex = "[bcdfghjklmnpqrstvwxzBCDFGHJKLMNPQRSTVWXZ]*[aeiouyAEIOUY]+[bcdfghjklmnpqrstvwxzBCDFGHJKLMNPQRSTVWXZ]*";
		Pattern syllablePattern = Pattern.compile(syllableRegex);
		
		while(matcher.find()) {
			numOfWords++;
		}
		
		matcher = sentencePattern.matcher(words);
		while(matcher.find()) {
			numOfSentences++;
		}
		
		matcher = syllablePattern.matcher(words);
		while(matcher.find()) {
			if(!(matcher.group().equals("e"))) {
				numOfSyllables++;
			}
		}
		calculateFleschScore();
		return (System.currentTimeMillis() - start);
	}
	
	public void markov() {
		File trainingFile = textEditorView.fileChooser.showOpenDialog(this.mainStage);
		if(trainingFile != null) {
			File fastLoad = new File("data//" + trainingFile.getName().substring(0, trainingFile.getName().length() - 4) + ".dat");
			if(fastLoad.exists()) {
				this.masterLinkedList = loadLinkedList(fastLoad);
			}
			else {
				fileReader = new FileReader(trainingFile);
				LinkListFactory linkListFactory = new LinkListFactory(fileReader);
				try {
					linkListFactory.linkListCreator();
				} catch (IOException e) {
					e.printStackTrace();
				}
				this.masterLinkedList = linkListFactory.getMasterLinkedList();
				saveLinkedList(fastLoad, this.masterLinkedList);
			}
			markovDialog.showAndWait();
			ListIterator<MasterLink> masterListIterator = masterLinkedList.listIterator();
			MasterLink masterLink = null;
			while(masterListIterator.hasNext()) {
				masterLink = masterListIterator.next();
				if(masterLink.getKeyword().equalsIgnoreCase(firstWordMarkov)) {
					break;
				}
			}
			if(masterLink != null) {
				ParagraphFactory paragraphFactory = new ParagraphFactory(Integer.valueOf(numOfWordsMarkov), firstWordMarkov, masterLinkedList);
				String markov = paragraphFactory.generateParagraph();
				this.textEditorView.txtArea.setText(markov);
				spellCheck();
				updateStatusBar();
			}
			else {
				createAlert("Word Not in Data", "The starting word you chose was not used in the data!\nPlease choose a different starting word or change your data file.").show();
			}
		}
		else {
			createAlert("No File Chosen", "No file has been selected as data, please choose a file!").show();
		}
	}
	
	public void truncate() throws IOException {
		if(file != null) {
			File folder = new File("truncate//" + file.getName().substring(0, file.getName().length() - 4) + " Subfiles");
			if(!folder.exists()) {
				folder.mkdir();
			}
			String[] words = this.textEditorView.txtArea.getText().split(" ");
			
			int percentOfWords = words.length / 20;
			int remainder = words.length % 20;
			
			for(int i = 0; i < 20; i++) {
				File subfile = new File("truncate//" + file.getName().substring(0, file.getName().length() - 4) + " Subfiles//" + " subfile " + (i + 1) + ".txt");
				PrintWriter pw = null;
				
				if(i == 19) {
					percentOfWords += remainder;
				}
				
				try {
					pw = new PrintWriter(subfile);
					for(int j = 0; j < percentOfWords; j++)
						pw.print(words[j] + " ");
						pw.close();
				}
				
				catch(FileNotFoundException fe) {
					
				}
				
				truncatedFiles[i] = subfile;
				percentOfWords += words.length / 20;
			}
		}
		else {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("No File");
			alert.setContentText("Please save or open a new file to continue!");
			alert.show();
		}
	}
	
	public void runAlgorithms() throws IOException {
		File results = new File("results//results.txt");
		if(truncatedFiles[0] == null) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Truncate Not Run");
			alert.setContentText("Please run truncate before attempting to run algorithms!");
			alert.show();
		}
		else {
			for(int i = 0; i < truncatedFiles.length; i++) {
				FileReader fileReader = new FileReader(truncatedFiles[i]);
				String subfileWords = fileReader.readFile();
				for(int j = 0; j < 2; j++) {
					if(j == 0) {
						algorithmTimings[i][j] = singleLoopAlgorithm(subfileWords);
					}
					else {
						algorithmTimings[i][j] = tripleLoopAlgorithm(subfileWords);
					}
				}
			}
		}
		PrintWriter pw = null;
		try {
			if(file != null) {
				pw = new PrintWriter(results);
				pw.println(String.format("%s%s%-40s|%40s", "File%", "|", "Single Loop Algorithm Timing (ms)", "Triple Loop Algorithm Timing (ms)"));
				pw.println("---------------------------------------------------------------------------------------");
				int percentOfFile = 5;
				for(int i = 0; i < algorithmTimings.length; i++) {
					pw.println(String.format("%-5s%s%-40d|%40d", percentOfFile + "%", "|", algorithmTimings[i][0], algorithmTimings[i][1]));
					pw.println("---------------------------------------------------------------------------------------");
					percentOfFile += 5;
				}
				pw.close();
			}
		}
		catch(FileNotFoundException fe) {
			fe.printStackTrace();
		}
		
	}
	
	public void spellCheck(){
		String listOfMisspelledWords = new String();
		String[] wordsInTextArea = this.textEditorView.txtArea.getText().split("[\\W]+");
		listOfMisspelledWords = spellCheckDictionary.checkSpelling(wordsInTextArea);
		this.textEditorView.txtAreaSpellCheck.setText(listOfMisspelledWords);
	}
	
	public void updateStatusBar() {
		tripleLoopAlgorithm(this.textEditorView.txtArea.getText());
		this.textEditorView.statusWords.setText("Word Count: " + numOfWords);
		this.textEditorView.statusSentences.setText("Sentence Count: " + numOfSentences);
		this.textEditorView.statusFleschScore.setText(String.format("%s%.1f","Flesch Score: ", fleschScore));
	}
	
	public void askSave() {
		if(this.textEditorView.txtArea.getText() != null) {
			ButtonType save = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
			ButtonType discard = new ButtonType("Don't Save", ButtonBar.ButtonData.CANCEL_CLOSE);
			Alert alertNewDoc = new Alert(AlertType.WARNING,
			        "You are about to exit Text Editor, would you like to save your current progress?",
			        save,
			        discard);
			alertNewDoc.setHeaderText("Save Current Progress?");
			alertNewDoc.setTitle("Exiting Program");
			Optional<ButtonType> result = alertNewDoc.showAndWait();
			if (result.get() == save) {
				save(textEditorView.fileChooser.showSaveDialog(mainStage));
			}
		}
	}
	
	public Alert createAlert(String title, String contentText) {
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle(title);
		alert.setContentText(contentText);
		return alert;
	}
	
	
	public void start() {
		mainStage.show();
	}

}
