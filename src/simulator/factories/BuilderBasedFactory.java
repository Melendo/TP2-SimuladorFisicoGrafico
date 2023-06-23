package simulator.factories;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

public class BuilderBasedFactory<T> implements Factory<T> {
	private Map<String,Builder<T>> _builders;
	private List<JSONObject> _buildersInfo;
	
	//Constructor 
	public BuilderBasedFactory() {
		// Create a HashMap for _builders, a LinkedList _buildersInfo
		this._builders = new HashMap<String, Builder<T>>(); 
		this._buildersInfo = new LinkedList<JSONObject>();
	}

	//Añade todas los builders del parametro a _builders y la info a _buildersInfo
	public BuilderBasedFactory(List<Builder<T>> builders) {
		this();
		// call addBuilder(b) for each builder b in builder
		for (Builder<T> b : builders) {
			addBuilder(b);
		}
	}
	
	//Añade un builder en especifico, se llama en la de arriba
	public void addBuilder(Builder<T> b) {
		// add and entry �� b.getTag() -> b�� to _builders.
		this._builders.put(b.getTypeTag(), b);
		// add b.getInfo () to _buildersInfo
		this._buildersInfo.add(b.getInfo());
	}
		
	@Override
	public T createInstance(JSONObject info) {
		if (info == null) {
			throw new IllegalArgumentException("Invalid value for createInstance:null");
		}
		
		// Search for a builder with a tag equals to info . getString ("type"), call its
		// createInstance method and return the result if it is not null . The value you
		// pass to createInstance is :
		// info . has("data") ? info . getJSONObject("data") : new getJSONObject()
			
			if(info.getString("type") != null && _builders.containsKey(info.getString("type"))) { 
				T o = _builders.get(info.getString("type")).createInstance(info.has("data") ? info.getJSONObject("data") : new JSONObject());
				if (o != null)
					return o;
			}
		
		// If no builder is found or thr result is null ...
		throw new IllegalArgumentException("Invalid value for createInstance: " + info.toString());
	}
	
		
	@Override
	public List<JSONObject> getInfo() {
		return Collections.unmodifiableList(_buildersInfo);
	}
	
	
	
	

}
