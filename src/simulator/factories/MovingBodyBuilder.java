package simulator.factories;

import org.json.JSONArray;
import org.json.JSONObject;

import simulator.misc.Vector2D;
import simulator.model.Body;
import simulator.model.MovingBody;

public class MovingBodyBuilder extends Builder<Body> {
	
	private final static String _typeTag = "mv_body";
	private final static String _desc = "Moving body";

	public MovingBodyBuilder() {
		super(_typeTag, _desc);
	}

	@Override
	protected Body createInstance(JSONObject data){
		JSONArray js;
		if(!data.has("id")) throw new IllegalArgumentException("Id no puede ser nulo en MovingBody");		
		String id = data.getString("id");
		if(id == null) throw new IllegalArgumentException("Id no puede ser nulo en MovingBody");
				
		if(!data.has("gid")) throw new IllegalArgumentException("Gid no puede ser nulo en MovingBody");
		String gid = data.getString("gid");
		if(gid == null) throw new IllegalArgumentException("Gid no puede ser nulo en MovingBody");
		
		if(!data.has("p") || data.getJSONArray("p").length() != 2) throw new IllegalArgumentException("La posicion no puede ser nula en MovingBody");
		js = data.getJSONArray("p");
		Vector2D p = new Vector2D(js.getDouble(0), js.getDouble(1));
		
		if(!data.has("v") || data.getJSONArray("v").length() != 2) throw new IllegalArgumentException("Velocidad no puede ser nula en MovingBody");
		Vector2D v = new Vector2D(data.getJSONArray("v").getDouble(0), data.getJSONArray("v").getDouble(1));
		
		if(!data.has("m")) throw new IllegalArgumentException("Masa no puede ser nulo en MovingBody");	
		double m = data.getDouble("m");
		if(m<=0 ) {
			throw new IllegalArgumentException("Masa no puede ser negativa en StationaryBody");
		}
		
		return new MovingBody(id, gid, p, v, m);
		
	}

	@Override
	protected void getData(JSONObject data) {
		// TODO Auto-generated method stub
		
	}

}
