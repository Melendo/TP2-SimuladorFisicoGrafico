package simulator.factories;

import org.json.JSONObject;

import simulator.misc.Vector2D;
import simulator.model.Body;
import simulator.model.StationaryBody;

public class StationaryBodyBuilder extends Builder<Body> {
	private final static String _typeTag = "st_body";
	private final static String _desc = "Stationary Body";

	public StationaryBodyBuilder() {
		super(_typeTag, _desc);
	}

	@Override
	protected Body createInstance(JSONObject data){
		if(!data.has("id")) throw new IllegalArgumentException("Id no puede ser nulo en StationaryBody");		
		String id = data.getString("id");
		if(id == null) throw new IllegalArgumentException("Id no puede ser nulo en StationaryBody");
				
		if(!data.has("gid")) throw new IllegalArgumentException("Gid no puede ser nulo en StationaryBody");
		String gid = data.getString("gid");
		if(gid == null) throw new IllegalArgumentException("Gid no puede ser nulo en StationaryBody");
		
		if(!data.has("p") || data.getJSONArray("p").length() != 2) throw new IllegalArgumentException("La posicion no puede ser nula en StationaryBody");
		Vector2D p = new Vector2D(data.getJSONArray("p").getDouble(0), data.getJSONArray("p").getDouble(1));
		
		if(!data.has("m")) throw new IllegalArgumentException("Masa no puede ser nulo en StationaryBody");	
		double m = data.getDouble("m");
		if(m<=0 ) {
			throw new IllegalArgumentException("Masa no puede ser negativa en StationaryBody");
		}
		
		return new StationaryBody(id, gid, p, m);
}
}