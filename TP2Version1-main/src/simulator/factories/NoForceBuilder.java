package simulator.factories;

import org.json.JSONObject;

import simulator.model.ForceLaws;
import simulator.model.NoForce;

public class NoForceBuilder extends Builder<ForceLaws> {
	
	private final static String _typeTag = "nf";
	private final static String _desc = "No Force";

	public NoForceBuilder() {
		super(_typeTag, _desc);
	}

	@Override
	protected ForceLaws createInstance(JSONObject data) {
		return new NoForce();
	}

}
