package simulator.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public class PhysicsSimulator {

	private double dt ;
	private double ta ;
	private ForceLaws leyes;
	private Map<String,BodiesGroup> mp;
	List <String> listaId;
	
	public PhysicsSimulator( ForceLaws leyes, double dt) {
		if(dt >=0 && leyes != null) {
			this.dt = dt;
			this.leyes = leyes;
			mp = new HashMap<String, BodiesGroup>();
			listaId = new ArrayList<String>();
		} else {
			throw new IllegalArgumentException();
		}
	}
		
	public void advance() {
		
		for(String clave : listaId) {
			mp.get(clave).advance(this.dt);
		}
		this.ta += this.dt;
	}	
	
	
	public void addGroup(String id) {
		if(!mp.containsKey(id)) {
			mp.put(id, new BodiesGroup(id, leyes));
			listaId.add(id);
		}
		else {
			throw new IllegalArgumentException("Ya existe un BodyGroup con ese id");
		}
	}
	
	
	public void addBody(Body b) {
		if(mp.containsKey(b.getgId())) {
			mp.get(b.getgId()).addBody(b);
		}
		else {
			throw new IllegalArgumentException("Ya existe un Body con ese id en el Grupo");
		}
	}
	
	
	public void setForceLaws(String id, ForceLaws fl) {
		if (!mp.containsKey(id)) {
			throw new IllegalArgumentException("No existe un BG con este id");
		}
		else {
			mp.get(id).setForceLaws(fl);
		}
	}
	
	
	public JSONObject getState() {
		JSONObject jso = new JSONObject();
		JSONArray jsa = new JSONArray();

		

		for (String clave : listaId)
			jsa.put(mp.get(clave).getState());
		jso.put("groups", jsa);
		jso.put("time", this.ta);

		return jso;
	}
	
	
	public void reset() {
		this.mp.clear();
		this.listaId.clear();
		this.ta = 0;
	}
	
	public void setDeltaTime(double dt) {
		if(dt < 0) {
			throw new IllegalArgumentException("Dt debe ser positivo");
		}
		this.dt = dt;
	}
	
	
	public String toString() {
		return getState().toString();
	}


	
}
