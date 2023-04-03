package simulator.factories;

import org.json.JSONObject;

import simulator.model.ForceLaws;
import simulator.model.NewtonUniversalGravitation;

public class NewtonUniversalGravitationBuilder extends Builder<ForceLaws> {
	
	private final static String _typeTag = "nlug";
	private final static String _desc = "Newton' s law of universal gravitation";

	public NewtonUniversalGravitationBuilder() {
		super(_typeTag, _desc);
		
	}

	@Override
	protected ForceLaws createInstance(JSONObject data){
		if(data.has("G")) {
			
			double g = data.getDouble("G");
			return new NewtonUniversalGravitation(g);
		}
		return new NewtonUniversalGravitation(6.67E-11);
	}
	
	public JSONObject getInfo() {
		JSONObject json = new JSONObject();
		JSONObject data = new JSONObject();
		json.put("type", _typeTag);
		json.put("desc", _desc);
		data.put("G", "the gravitational constant (a number)");
		json.put("data", data);
		return json;
	}

}
