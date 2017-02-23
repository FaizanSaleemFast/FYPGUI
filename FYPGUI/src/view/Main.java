package view;
	
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			HBox root = (HBox)FXMLLoader.load(getClass().getResource("MainGUI.fxml"));
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
			
			primaryStage.setScene(scene);
			primaryStage.setTitle("GUI Test Case Repairer");
			
//			primaryStage.getIcons().add(new Image("icon.png"));
			
			primaryStage.show();
			
			
//			letterbox(scene, root);
//			primaryStage.setFullScreen(true);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	private void letterbox(final Scene scene, final Pane contentPane) {
	    final double initWidth  = scene.getWidth();
	    final double initHeight = scene.getHeight();
	    final double ratio      = initWidth / initHeight;

	    SceneSizeChangeListener sizeListener = new SceneSizeChangeListener(scene, ratio, initHeight, initWidth, contentPane);
	    scene.widthProperty().addListener(sizeListener);
	    scene.heightProperty().addListener(sizeListener);
	  }

	  private static class SceneSizeChangeListener implements ChangeListener<Number> {
	    private final Scene scene;
	    private final double ratio;
	    private final double initHeight;
	    private final double initWidth;
	    private final Pane contentPane;

	    public SceneSizeChangeListener(Scene scene, double ratio, double initHeight, double initWidth, Pane contentPane) {
	      this.scene = scene;
	      this.ratio = ratio;
	      this.initHeight = initHeight;
	      this.initWidth = initWidth;
	      this.contentPane = contentPane;
	    }

		@Override
		public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {
			final double newWidth  = scene.getWidth();
		      final double newHeight = scene.getHeight();

		      double scaleFactor =
		          newWidth / newHeight > ratio
		              ? newHeight / initHeight
		              : newWidth / initWidth;

		      if (scaleFactor >= 1) {
		        Scale scale = new Scale(scaleFactor, scaleFactor);
		        scale.setPivotX(0);
		        scale.setPivotY(0);
		        scene.getRoot().getTransforms().setAll(scale);

		        contentPane.setPrefWidth (newWidth  / scaleFactor);
		        contentPane.setPrefHeight(newHeight / scaleFactor);
		      } else {
		        contentPane.setPrefWidth (Math.max(initWidth,  newWidth));
		        contentPane.setPrefHeight(Math.max(initHeight, newHeight));
		      }
			
		}
	  }
	
	public static void main(String[] args) {
		launch(args);
	}
}
