package view;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import DOM_Difference.Difference;
import DOM_Difference.PageParser;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class MainGUIController {
	
	final String projectPath = System.getProperty("user.dir");
	
	static ArrayList<String> oldVersionEffectedLines = new ArrayList<>();
	static ArrayList<String> newVersionEffectedLines = new ArrayList<>();
	
	// For Version 1
	final ToggleGroup version1ToggleGroup = new ToggleGroup();

	@FXML ToggleButton version1WebView = new ToggleButton("Web");
	@FXML ToggleButton version1HTMLView = new ToggleButton("HTML");
	
	// For Version 2
		final ToggleGroup version2ToggleGroup = new ToggleGroup();

	@FXML ToggleButton version2WebView = new ToggleButton("Web");
	@FXML ToggleButton version2HTMLView = new ToggleButton("HTML");

	@FXML private ScrollPane oldVersion = new ScrollPane();
	@FXML private ScrollPane oldVersionLines = new ScrollPane();
	
	@FXML private ScrollPane newVersion = new ScrollPane();
	@FXML private ScrollPane newVersionLines = new ScrollPane();
	
	@FXML private Button selectV1 = new Button();
	@FXML private Button selectV2 = new Button();
	
	@FXML private TextField urlVersion1=new TextField();
	@FXML private TextField urlVersion2=new TextField();
	
	private FileChooser selectFile = new FileChooser();
	
	private File file01=null, file02=null;

	private VBox oldDataDefault = new VBox(), newDataDefault = new VBox();
	private VBox oldData = new VBox(), newData = new VBox();
	private VBox oldDataLines = new VBox(), newDataLines = new VBox();
	
	
	
	@FXML private Button findDifferences = new Button();
	@FXML ListView<String> differenceCategories = new ListView<>();
	
	private ArrayList<String> oldVersionHtmlLines = new ArrayList<String>();
	private ArrayList<String> newVersionHtmlLines = new ArrayList<String>();

	protected PageParser pageParser = new PageParser();

	protected ArrayList<String> allCategories;
	
	private void readFromFile(File file, ArrayList<String> HtmlLines) {
		
		try {
			
			/*
			BufferedReader br = new BufferedReader(new FileReader(file));
			
			String line;
			while((line = br.readLine()) != null) {
				HtmlLines.add(line);
			}
			*/
			
			/*
			Source source = new Source(file);
			SourceFormatter sourceFormatter = new SourceFormatter(source);
			sourceFormatter.setIndentString("  ");
			sourceFormatter.setIndentAllElements(true);
			sourceFormatter.setTidyTags(true);
			sourceFormatter.setCollapseWhiteSpace(false);
			String sourceHtml = sourceFormatter.toString();
			String lines[] = sourceHtml.split("\\r?\\n");
			
//			String lines[] = new SourceFormatter(source).setIndentString("  ").setIndentAllElements(true).setTidyTags(true).setCollapseWhiteSpace(false).getIndentString().split("\\r?\\n");

			
			for (String line : lines) {
				HtmlLines.add(line);
			}

		   */
			
			Document doc = Jsoup.parse(file, "UTF-8");
			String lines[] = doc.toString().split("\\r?\\n");
			for (String line : lines) {
				HtmlLines.add(line);
			}

			
			
			System.out.println("---------------------------------------------------------");

//			System.out.println(doc.toString());
//			System.out.println(sourceHtml);
			
			System.out.println("---------------------------------------------------------");
			
			
			
			
//			doc1 = Jsoup.parse(input, "UTF-8");

//			oldVersionData = doc1.toString();
//			Jsoup.clean(oldVersionData, "", Whitelist.none(), new OutputSettings().prettyPrint(false));

			
//			System.out.println(oldVersionData);
//			System.out.println("===========================================================");
			
//			input = new File(projectPath + "\\after.html");
			
//			BufferedReader br1 = new BufferedReader(new FileReader(input));
			
//			while((line = br1.readLine()) != null) {
//				newVersionHtmlLines.add(line);
//			}
			
			
//			doc2 = Jsoup.parse(input, "UTF-8");
			
//			newVersionData = doc2.toString();

//			System.out.println(newVersionData);
//			System.out.println("===========================================================");

		
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	
	private void showWebView(ScrollPane version, ArrayList<String> versionData) {
		
		WebView browser = new WebView();
        WebEngine webEngine = browser.getEngine();
		
        version.setContent(browser);
        
        
        String data = "";
        
        
        for(String s : versionData) {
        	data += s + "\r\n";
        }
        
        webEngine.loadContent(data);
	}
	
	
	private void showHTMLView(ScrollPane version, VBox vBox, ArrayList<String> data) {
		
		version.setContent(vBox);
		if(vBox.getChildren().size() <= 0)
		{
			for(int i=0; i < data.size(); i++) {
				Label singleLine = new Label(data.get(i));
				vBox.getChildren().add(singleLine);
			}
		}
	}
	
	
	private void showHTMLViewLines(ScrollPane version, VBox vBox, ArrayList<String> data) {
		
		version.setContent(vBox);
		if(vBox.getChildren().size() <= 0)
		{
			for(int i=0; i < data.size(); i++) {
				Label singleLine = new Label(String.valueOf((i+1)));
				vBox.getChildren().add(singleLine);
			}
		}
	}
	
	private void copyVBoxes(VBox source, VBox target) {
		
		for(int i =0; i < source.getChildren().size(); i++) {
			target.getChildren().add(source.getChildren().get(i));
		}
		
	}
	
	
	@FXML
    private void initialize() {
		oldVersion.vvalueProperty().bindBidirectional(newVersion.vvalueProperty());
		oldVersion.vvalueProperty().bindBidirectional(oldVersionLines.vvalueProperty());
		
		
		oldVersion.hvalueProperty().bindBidirectional(newVersion.hvalueProperty());
		
		if(file01==null) {
			version1WebView.setDisable(true);
			version1HTMLView.setDisable(true);
		}
		
		
		if(file02==null) {
			version2WebView.setDisable(true);
			version2HTMLView.setDisable(true);
		}
		
		if(file01==null || file02==null) {
//			findDifferences.setDisable(true);
		}		
		
		newVersion.vvalueProperty().bindBidirectional(newVersionLines.vvalueProperty());

//		readFromFile();
		
		/* Don't Delete */
		/*
		WebView browser = new WebView();
        WebEngine webEngine = browser.getEngine();
        
        WebView browser1 = new WebView();
        WebEngine webEngine1 = browser1.getEngine();
        

        oldVersion.setContent(browser);
        webEngine.loadContent(oldVersionData);

        newVersion.setContent(browser1);
        webEngine1.loadContent(newVersionData);
        */
		
		
		// Selecting and Displaying Version 1
		selectV1.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
				file01 = selectFile.showOpenDialog(new Stage());
				
				oldData = new VBox();
				oldVersionHtmlLines = new ArrayList<>();
				
				showHTMLView(oldVersion, oldData, oldVersionHtmlLines);
				showHTMLViewLines(oldVersionLines, oldDataLines, oldVersionHtmlLines);
			
				
				
				if(file01!=null) {
					selectV1.setText(file01.getName());
					
				

					
					
					readFromFile(file01, oldVersionHtmlLines);					
					
					showHTMLView(oldVersion, oldData, oldVersionHtmlLines);
					showHTMLViewLines(oldVersionLines, oldDataLines, oldVersionHtmlLines);
					
					if(oldDataDefault.getChildren().size() <= 0)
						copyVBoxes(oldData, oldDataDefault);
					
					version1WebView.setDisable(false);
					version1HTMLView.setDisable(false);
					
					version1HTMLView.setSelected(true);
				}
				
				
			}

		});
		//*****************************************************************
		
		/*
		urlVersion1.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {

				try{
				// TODO Auto-generated method stub
				 URL oracle = new URL(urlVersion1.getText());
			     BufferedReader in = new BufferedReader(
			     new InputStreamReader(oracle.openStream()));
			     
			     file01=new File("version1.html");
			     Util.copy(in, new FileWriter(file01));
			     
					if(file01!=null) {
						selectV1.setText(file01.getName());
						readFromFile(file01, newVersionHtmlLines);
						
						showHTMLView(oldVersion, oldData, oldVersionHtmlLines);
						showHTMLViewLines(oldVersionLines, oldDataLines, oldVersionHtmlLines);
						
						
						version1HTMLView.setSelected(true);
					}



				}
				catch(Exception b)
				{
					
				}
				

			}
				
		});
		

		urlVersion2.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
				try{
				// TODO Auto-generated method stub
				 URL oracle = new URL(urlVersion2.getText());
			     BufferedReader in = new BufferedReader(
			     new InputStreamReader(oracle.openStream()));
			     
			     file02=new File("version2.html");
			     Util.copy(in, new FileWriter(file02));
			     
					if(file02!=null) {
						selectV2.setText(file02.getName());
						readFromFile(file02, newVersionHtmlLines);
						
						showHTMLView(newVersion, newData, newVersionHtmlLines);
						showHTMLViewLines(newVersionLines, newDataLines, newVersionHtmlLines);
						version2HTMLView.setSelected(true);
					}



				}
				catch(Exception b)
				{
					
				}
				
				
			}
		});
		
		*/
//*******************************************************************************************

		 
		// For Version 1
		 version1WebView.setToggleGroup(version1ToggleGroup);
		 version1WebView.setUserData("Version1WebView");
		 
		 version1HTMLView.setToggleGroup(version1ToggleGroup);
		 version1HTMLView.setUserData("Version1HTMLView");
		 
		 
		 version1WebView.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				
				if(version1WebView.isSelected()) {
					System.out.println("Selected Version1WebView");
					showWebView(oldVersion, oldVersionHtmlLines);
				}
				
				
				if(!version1WebView.isSelected()) {
					version1HTMLView.setSelected(true);
					
				}
				
			}
		});
		 
		 version1HTMLView.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				
				if(version1HTMLView.isSelected()) {
					System.out.println("Selected Version1HTMLView");
					showHTMLView(oldVersion, oldData, oldVersionHtmlLines);
					showHTMLViewLines(oldVersionLines, oldDataLines, oldVersionHtmlLines);
					
					if(newDataDefault.getChildren().size() <= 0)
						copyVBoxes(newData, newDataDefault);
				}
				
				
				if(!version1HTMLView.isSelected()) {
					version1WebView.setSelected(true);
				}
				
			}
		});
		 
		 
		 
		
		
		// Selecting and Displaying Version 2
		selectV2.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
				file02 = selectFile.showOpenDialog(new Stage());
				
				if(file02!=null) {
					selectV2.setText(file02.getName());
					
					newData = new VBox();
					newVersionHtmlLines = new ArrayList<>();
					
					readFromFile(file02, newVersionHtmlLines);
					
					showHTMLView(newVersion, newData, newVersionHtmlLines);
					showHTMLViewLines(newVersionLines, newDataLines, newVersionHtmlLines);
					
					if(newDataDefault.getChildren().size() <= 0)
						copyVBoxes(newData, newDataDefault);
					
					version2WebView.setDisable(false);
					version2HTMLView.setDisable(false);

					version2HTMLView.setSelected(true);
					
					if(file01 != null && file02 != null) {
						findDifferences.setDisable(false);
					}
				}
				
				
			}
		});
		
		
		
		
		
		// For Version 2
		
				 version2WebView.setToggleGroup(version2ToggleGroup);
				 version2WebView.setUserData("Version2WebView");
				 
				 version2HTMLView.setToggleGroup(version2ToggleGroup);
				 version2HTMLView.setUserData("Version2HTMLView");
				 
				 version2WebView.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent arg0) {
						
						if(version2WebView.isSelected()) {
							System.out.println("Selected Version2WebView");
							showWebView(newVersion, newVersionHtmlLines);
						}
						
						
						if(!version2WebView.isSelected()) {
							version2HTMLView.setSelected(true);
							
						}
						
					}
				});
				 
				 version2HTMLView.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent arg0) {
						
						if(version2HTMLView.isSelected()) {
							System.out.println("Selected Version2HTMLView");
							showHTMLView(newVersion, newData, newVersionHtmlLines);
							showHTMLViewLines(newVersionLines, newDataLines, newVersionHtmlLines);
						}
						
						
						if(!version2HTMLView.isSelected()) {
							version2WebView.setSelected(true);
						}
						
					}
				});
		
		
		
		
		
		
		
		
		
					
		
		
		
		
		
		
		
		
		
		
		
		// Find Differences
		
		findDifferences.setOnAction(new EventHandler<ActionEvent>() {

			
			
			@Override
			public void handle(ActionEvent arg0) {
				
				System.out.println("Here");
				
				
				if((file01 == null && file02 == null) && (urlVersion1.getText().isEmpty() && urlVersion2.getText().isEmpty())) {
					Util.infoBox("Please select a file or url first", "Sorry, Please select a file or url first", "No file or url selected");
				}
				
				
				
				if((file01==null||file02==null) && (!urlVersion1.getText().isEmpty()||!urlVersion2.getText().isEmpty()))
				{
					
					if(!urlVersion1.getText().isEmpty())
					{
						file01=Util.setUrlToFile(urlVersion1.getText(), 1);
						if(file01!=null) {
							selectV1.setText(file01.getName());
							readFromFile(file01, oldVersionHtmlLines);
							
							showHTMLView(oldVersion, oldData, oldVersionHtmlLines);
							showHTMLViewLines(oldVersionLines, oldDataLines, oldVersionHtmlLines);
							version1HTMLView.setSelected(true);
						}
					}

					if(!urlVersion2.getText().isEmpty())
					{
						file02=Util.setUrlToFile(urlVersion2.getText(), 2);
						if(file02!=null) {
							selectV2.setText(file02.getName());
							readFromFile(file02, newVersionHtmlLines);
							
							showHTMLView(newVersion, newData, newVersionHtmlLines);
							showHTMLViewLines(newVersionLines, newDataLines, newVersionHtmlLines);
							version2HTMLView.setSelected(true);
						}
					}
					if(urlVersion1.getText().equals(urlVersion2.getText()))
					{
						Util.infoBox("Both File Pages same", "Sorry , No change can be there in same page", "Not Possible");
						return;
					}
					
										
				}
				if(file01 != null && file02!= null){

					pageParser = new PageParser();
					pageParser.setup(file01, file02);
					
					allCategories = pageParser.getAllDifferenceTypesCategories();
					
//					listCategoryModel.removeAllElements();
//					list_categories.removeAll();
					
					
					ObservableList<String> items = FXCollections.observableArrayList();
					
					for (int i = 0; i < allCategories.size(); i++) {
						items.add(allCategories.get(i));
					}
					differenceCategories.setItems(items);
					
				}else{
//					 JOptionPane.showMessageDialog(null, "Please choose files first");
//					System.out.println("Ithaey");
				}
				
				
				
			}
		});
		
		
		differenceCategories.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				ListView<String> list = (ListView<String>)event.getSource();
				
				if (event.getClickCount() == 2) {
		        	
		        	//JOptionPane.showMessageDialog(null, "Please choose files first");
					
					UpdateVersionChanges((list.getSelectionModel().getSelectedIndex()));
		        	
		        }
			}
		});
		
		
		
		
		
		
		
		
	}
	
	
	private void UpdateVersionChanges(int catID){
		
		
		
		/*
		VBox oldData = new VBox();
		oldVersion.setContent(oldData);
		Label singleLine = new Label("");
		oldData.getChildren().add(singleLine);

		VBox newData = new VBox();
		newVersion.setContent(newData);
		newData.getChildren().add(singleLine);
*/
		
		
		String diffType = allCategories.get(catID);
		
		ArrayList<Difference> differentElements = pageParser.getElementByDifferenceType(diffType);
		
		Set<Difference> hs = new HashSet<>();
		hs.addAll(differentElements);
		differentElements.clear();
		differentElements.addAll(hs);
		
		
		System.out.println("Difference Type: " + diffType);
		System.err.println("Size: " + differentElements.size());
		
		
//		ChangesGUIController secondaryController = new ChangesGUIController();
		
		for (int i = 0; i < differentElements.size(); i++) {
			
			
//			showHTMLView(oldVersion, oldDataDefault, oldVersionHtmlLines);
//			showHTMLViewLines(oldVersionLines, oldDataLines, oldVersionHtmlLines);
			
			String effectedLine = null;

			
			// For Old Version
			if(differentElements.get(i).getTagV1() != null) {
				effectedLine = differentElements.get(i).getTagV1().toString();
	
				System.out.println("Effected Line: " + effectedLine);
				
				if(effectedLine != null || differentElements.get(i).getTagV2() != null) {
	
					for(int j=0; j < oldData.getChildren().size(); j++) {
						
						System.out.println(oldData.getChildren().get(j).toString().trim());
						
	
						if(oldData.getChildren().get(j).toString().trim().contains(effectedLine)) {
							
							System.out.println("Yahan");
					
							Label effectedLabel = (Label) oldData.getChildren().get(j);
							
							System.out.println("Label: " + effectedLabel.getText());
						
							if(diffType.toLowerCase().contains("deleted")) {
								effectedLabel.setTextFill(Color.web("#ff0000"));
							}
							else if(diffType.toLowerCase().contains("updated")) {
								effectedLabel.setTextFill(Color.web("#ffa500"));
							}
							else if(diffType.toLowerCase().contains("added")) {
								effectedLabel.setTextFill(Color.web("#00ff00"));
							}
							else {
								effectedLabel.setTextFill(Color.web("#CCCC00"));
							}
							
							oldVersionEffectedLines.add(effectedLine);
						}
					}
				}
			}
			
//			secondController.setTextArea1Data(oldVersionEffectedLines);
			
			
			
			
			
//			showHTMLView(newVersion, newDataDefault, newVersionHtmlLines);
//			showHTMLViewLines(newVersionLines, newDataLines, newVersionHtmlLines);
			
			
			// For New Version
			if(differentElements.get(i).getTagV2() != null) {
				effectedLine = differentElements.get(i).getTagV2().toString();
	
				System.out.println("Effected Line: " + effectedLine);
				
				if(effectedLine != null || differentElements.get(i).getTagV1() != null) {
	
					for(int j=0; j < newData.getChildren().size(); j++) {
						
						System.out.println(newData.getChildren().get(j).toString().trim());
						
	
						if(newData.getChildren().get(j).toString().trim().contains(effectedLine)) {
							
							System.out.println("Yahan");
					
							Label effectedLabel = (Label) newData.getChildren().get(j);
							
							System.out.println("Label: " + effectedLabel.getText());
						
							if(diffType.toLowerCase().contains("deleted")) {
								effectedLabel.setTextFill(Color.web("#ff0000"));
							}
							else if(diffType.toLowerCase().contains("updated")) {
								effectedLabel.setTextFill(Color.web("#ffa500"));
							}
							else if(diffType.toLowerCase().contains("added")) {
								effectedLabel.setTextFill(Color.web("#00ff00"));
							}
							else {
								effectedLabel.setTextFill(Color.web("#CCCC00"));
							}
							
							newVersionEffectedLines.add(effectedLine);
						}
					}
				}
			}
			
			

			
		}
		
	}
	
	
}
