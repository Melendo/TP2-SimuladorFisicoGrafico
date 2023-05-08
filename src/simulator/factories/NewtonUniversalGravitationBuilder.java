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
		double g = 6.67E-11;
		
		if(data.has("G")) {
			g = data.getDouble("G");
		}
		
		return new NewtonUniversalGravitation(g);
	}

	@Override
	protected void getData(JSONObject data) {
		// TODO Auto-generated method stub
		data.put("G", "the gravitational constant (a number)");
	}

}
