package game_engine.components.physics;

import game_engine.Component;
import game_engine.event.conditions.DataConditionable;

@DataConditionable
public class YAccelComponent extends Component<Double> {
	
	public YAccelComponent(String arg) {
		super(Double.parseDouble(arg));
	}

}