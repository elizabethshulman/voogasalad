package authoring.right_components;

import java.util.ArrayList;
import java.util.List;

import authoring.AddActionPane;
import authoring.AddConditionPane;
import authoring.controllers.LevelController;
import authoring.right_components.EntityComponent.EntityWrapper;
import frontend_utilities.ButtonFactory;
import frontend_utilities.ImageBuilder;
import frontend_utilities.UserFeedback;
import game_engine.event.Action;
import game_engine.event.Condition;
import game_engine.event.Event;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import resources.keys.AuthRes;

/**
 * @author Elizabeth Shulman
 * @author Liam Pulsifer
 * Event menu that extends BasePane, which implements GUINode. Menu for toggling 
 * relationships between multiple entities
 * 
 * MIGRATING DESIGN CODE TO CSS
 */
public class EventPane extends BasePane {
		
	private Pane newEventPane;
	private Pane viewEvents;
	private VBox box;
	private VBox subBox;
	private VBox comboBoxView;
	private List<Event> eventList;
	private Event currentEvent;
	private LevelController levelController;
	private AddActionPane addActionPane;
	private AddConditionPane addConditionPane;
	private Stage stage;

	public EventPane(Stage stage){
		this.stage = stage;
		eventList = new ArrayList<>();
		currentEvent = new Event();
		
		box = buildBasicView(AuthRes.getString("EventTitle"));
		subBox = new VBox(AuthRes.getInt("VBPadding"));
		box.getChildren().add(subBox);
		
		comboBoxView = new VBox(AuthRes.getInt("VBPadding"));
		comboBoxView.setFillWidth(true);
		comboBoxView.setPrefWidth(AuthRes.getInt("PrefVBoxWidth"));
				
		initStart();
		addConditionPane = new AddConditionPane(currentEvent, levelController, stage, this);
		addActionPane = new AddActionPane(currentEvent, stage, this);
		initNewEventPane();
		initViewEvents();
	}
	
	/**
	 * GUINode method that returns the highest-level view of this Pane.
	 * @return Pane
	 */
	@Override
	public Pane getView() {
		return box;
	}
	
	public void addToEntityBox(EntityWrapper wrapper){
		addConditionPane.addToEntityBox(wrapper);
		addActionPane.addToEntityBox(wrapper);
	}
	
	public Event getCurrentEvent() {
		return currentEvent;
	}
	
	public void setLevelController(LevelController controller){
		levelController = controller;
		addConditionPane.setLevelController(controller);
		addActionPane.setLevelController(controller);
	}

	//Initializers
	private void initStart() {
		Pane start = new Pane();
		VBox startBox = new VBox(AuthRes.getInt("VBPadding"));
		
		startBox.getChildren().addAll(
				ButtonFactory.makeHBox(
					AuthRes.getString("CreateNewEvent"), null, 
					ButtonFactory.makeButton(e -> {
						clearAndAdd(newEventPane);
					})),
				ButtonFactory.makeHBox(
					AuthRes.getString("ViewExistingEvents"), null,
					ButtonFactory.makeButton(a -> {
						clearAndAdd(viewEvents);
					})),
				ButtonFactory.makeHBox(
					AuthRes.getString("AddEventToLevel"), null, 
					ButtonFactory.makeButton(e -> {
						levelController.addEvent(currentEvent);
						eventList.add(currentEvent);
						initViewEvents();
						currentEvent = new Event();
						UserFeedback.getInfoMessage(AuthRes.getString("AddEventHeader"), AuthRes.getString("AddEventContent"), stage).showAndWait();
						}))
				);
		
		start.getChildren().add(startBox);
		clearAndAdd(start);
	}
	
	private void initNewEventPane() {
		currentEvent = new Event();
		newEventPane = new Pane();
		
		VBox eventBox = new VBox(AuthRes.getInt("VBPadding"));
		eventBox.getChildren().add(addConditionPane.getView());
		
		addConditionPane.getView().setOnMousePressed(e -> {
			addConditionPane.getStyleClass().add("event-sub-pane");
			addConditionPane.setSelected(true);
			addActionPane.setSelected(false);
		});
		
		eventBox.getChildren().add(addActionPane.getView());
		addActionPane.getView().setOnMousePressed(e -> {
			addActionPane.getStyleClass().add("event-sub-pane");
			addActionPane.setSelected(true);
			addConditionPane.setSelected(false);
		});
			
		newEventPane.getChildren().add(addBackButton(eventBox));
	}
	
	private void initViewEvents() {
		viewEvents = new Pane();
		VBox events = new VBox(AuthRes.getInt("VBPadding"));

		VBox box = new VBox();
		box.getChildren().addAll(buildEventSubLabels(eventList));
		events.getChildren().add(new ScrollPane(box));
		events.getChildren().add(addBackButton(events));

		events.getChildren().add(
				ButtonFactory.makeButton(e -> {
					levelController.getEngine().getLevel().removeLastEvent();
					eventList.remove(eventList.size() - 1);
					initViewEvents();
				}));
		
		viewEvents.getChildren().add(events);
	}
	
	//INITIALIZER HELPER METHODS
	private Pane addBackButton(Pane box) {
		box.getChildren().add(
				ButtonFactory.makeIconButton(
					AuthRes.getString("Back"), 
					ImageBuilder.resize(new ImageView(new Image(AuthRes.getString("backimage"))), AuthRes.getInt("PaneButton")), 
					e -> {
						initStart();
				}));
		return box;
	}
	
	private void clearAndAdd(Node n){
		subBox.getChildren().clear();
		subBox.getChildren().add(n);
	}
	
	private VBox buildEventSubLabels(List<Event> list){
		VBox labelList = new VBox();
		for (Event element : list){
			VBox box = new VBox(AuthRes.getInt("Padding"));
			box.setPrefWidth(AuthRes.getInt("PrefVBWidth"));
			labelList.getChildren().add(box);
			
			generateLabel(AuthRes.getString("ConditionsLabel"),"event-label", box);
			for (Condition condition : element.getConditions()){
				generateLabel(condition.toString(), "event-label2", box);
			}
			
			generateLabel(AuthRes.getString("ActionsLabel"), "event-label", box);
			for (Action action : element.getActions()){
				generateLabel(action.toString(), "event-label2", box);
			}
		}
		return labelList;
	}
	
	private void generateLabel(String s, String stylesheet, Pane box) {
		Label label = new Label(s);
		label.getStyleClass().add(stylesheet);
		box.getChildren().add(label);
	}
}