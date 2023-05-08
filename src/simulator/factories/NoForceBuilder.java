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
	
	public JSONObject getInfo() {
		JSONObject json = new JSONObject();
		JSONObject data = new JSONObject();
		json.put("type", _typeTag);
		json.put("desc", _desc);
		json.put("data", data);
		return json;
	}

	@Override
	protected void getData(JSONObject data) {
		// TODO Auto-generated method stub
		
	}

}
