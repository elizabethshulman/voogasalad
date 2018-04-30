package game_engine.event.conditions;

import game_engine.Component;
import game_engine.Entity;
import game_engine.components.collision.CollidedComponent;

/**
 * @author Jeremy Chen
 * Collision condition that checks the existence of a certain type of component attached to a collided Entity
 */
public class ComponentCollisionCondition extends CollisionCondition{
	private Class<Component<?>> targetComponent;
	
	public ComponentCollisionCondition(Entity e1, Class<Component<?>>comp) {
		super(e1);
		targetComponent = comp;
	}
	
	@Override
	protected boolean findCollidedTarget(CollidedComponent sideComponent) {
		return sideComponent.contains(targetComponent);
	}
}