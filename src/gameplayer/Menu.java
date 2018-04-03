package gameplayer;

import java.util.ArrayList;


import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import java.util.ResourceBundle;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
/**
 * 
 * @author Brandon Dalla Rosa, Dana Park
 *
 */
public class Menu {
	
	private HBox pane;
	private PulldownFactory pullDownFactory = new PulldownFactory();
	private VBox keyPrefMenu;
	private Button keyPrefButton;
	private Stage keyPrefStage;
	private DataManager dataManager;
	private boolean isReading;
	private KeyCode currentKey;
	private Button currentPrefButton;	
	private String currentPrefString;
	private static final String DEFAULT_RESOURCE_PACKAGE = "gamePlayerResources/";
	private ResourceBundle menuProperties = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + "menu_options");

	
	public Menu(DataManager data) {
		pane = new HBox(20);
		pane.setAlignment(Pos.CENTER);
		dataManager = data;
		isReading = false;
		currentKey = KeyCode.ENTER;
		currentPrefButton = new Button();
		pane.getChildren().add(pullDownFactory.SpeedBox());
		pane.getChildren().add(pullDownFactory.StatusBox());
		pane.getChildren().add(pullDownFactory.SaveLoadBox());
		keyPrefMenu = new VBox(10);
		keyPrefButton = new Button("Key Prefs");
		keyPrefButton.setOnAction(click->{showPrefMenu();});
		keyPrefButton.setPrefSize(160, 20);
		pane.getChildren().add(keyPrefButton);
		initKeyPrefMenu();
		keyPrefStage = new Stage();
		Scene scene = new Scene(keyPrefMenu);
		scene.setOnKeyPressed(click->checkForInput(click.getCode()));
		keyPrefStage.setScene(scene);
	}
	/**
	 * Method to add the menu into the VBox for the View Manager
	 * 
	 * @param root
	 */
	public void addMenu(Pane root) {
		root.getChildren().add(pane);
	}
	
	private void initKeyPrefMenu() {
		ArrayList<String> inputs = (ArrayList<String>) dataManager.getInputCommands();
		for(String s : inputs) {
			HBox toAdd = new HBox(10);
			toAdd.setAlignment(Pos.CENTER);
			Label label = new Label(s);
			Button button = new Button("ENTER");
			button.setOnAction(click->{setPref(button,s);});
			toAdd.getChildren().add(label);
			toAdd.getChildren().add(button);
			keyPrefMenu.getChildren().add(toAdd);
		}
	}
	private void setPref(Button button,  String string) {
		currentPrefButton = button;
		currentPrefString = string;
	}
	private void showPrefMenu() {
		keyPrefStage.show();
	}
	
	public void checkForInput(KeyCode code) {
		currentKey = code;
		currentPrefButton.setText(""+currentKey);
		dataManager.setKey(currentPrefString, code);
	}
	

}
