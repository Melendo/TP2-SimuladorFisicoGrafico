package simulator.factories;

import org.json.JSONObject;

import simulator.misc.Vector2D;
import simulator.model.ForceLaws;
import simulator.model.MovingTowardsFixedPoint;



public class MovingTowardsFixedPointBuilder extends Builder<ForceLaws> {
	
	private final static String _typeTag = "mtfp";
	private final static String _desc = "Moving towards a fixed point";

	public MovingTowardsFixedPointBuilder() {
		super(_typeTag, _desc);
		
	}
	
	protected ForceLaws createInstance(JSONObject data){
		double g;
		Vector2D c;
		
		if(data.has("g")) {
			g = data.getDouble("g");	
			
		}
		else {
			g = 9.81;
		}
		
		if(data.has("c")) {
			c = new Vector2D(data.getJSONArray("c").getDouble(0), data.getJSONArray("c").getDouble(1));	
			
		}
		else {
			c = new Vector2D();
		}

		return new MovingTowardsFixedPoint(c, g);
	}	
	
	public JSONObject getInfo() {
		JSONObject json = new JSONObject();
		JSONObject data = new JSONObject();
		json.put("type", _typeTag);
		json.put("desc", _desc);
		data.put("c", "the point towards which bodies move (e.g., [100.0,50.0])");
		data.put("g", "the length of the acceleration vector (a number)");
		json.put("data", data);
		return json;
	}
}
