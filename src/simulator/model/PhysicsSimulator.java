package simulator.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public class PhysicsSimulator implements Observable<SimulatorObserver>{

	private double dt;
	private double ta;
	private ForceLaws leyes;
	private Map<String,BodiesGroup> mp;
	private Map<String,BodiesGroup> mpNM;
	private List<String> listaId;
	private List<SimulatorObserver> listaOb;
	
	public PhysicsSimulator( ForceLaws leyes, double dt) {
		if(dt >=0 && leyes != null) {
			this.dt = dt;
			this.leyes = leyes;
			mp = new HashMap<String, BodiesGroup>();
			mpNM = Collections.unmodifiableMap(mp);
			
			listaId = new ArrayList<String>();
			listaOb = new ArrayList<SimulatorObserver>();
		} else {
			throw new IllegalArgumentException();
		}
	}
		
	public void advance() {
		
		for(String clave : listaId) {
			mp.get(clave).advance(this.dt);
		}
		this.ta += this.dt;
		
		for(SimulatorObserver o : this.listaOb) {
			o.onAdvance(mpNM, ta);
		}
	}	
	
	public void addGroup(String id) {
		if(!mp.containsKey(id)) {
			mp.put(id, new BodiesGroup(id, leyes));
			listaId.add(id);
			
			for(SimulatorObserver o : this.listaOb) {
				o.onGroupAdded(mpNM, mpNM.get(id));
			}
		}
		else {
			throw new IllegalArgumentException("Ya existe un BodyGroup con ese id");
		}
		
	}
	
	public void addBody(Body b) {
		if(mp.containsKey(b.getgId())) {
			mp.get(b.getgId()).addBody(b);
			
			for(SimulatorObserver o : this.listaOb) {
				o.onBodyAdded(mpNM, b);
			}
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
			
			for(SimulatorObserver o : this.listaOb) {
				o.onForceLawsChanged(mpNM.get(id));
			}
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
	
	public String toString() {
		return getState().toString();
	}

	public void reset() {
		this.mp.clear();
		this.listaId.clear();
		this.ta = 0;
		
		for(SimulatorObserver o : this.listaOb) {
			o.onReset(mpNM, ta, dt);
		}
	}
	
	public void setDeltaTime(double dt) {
		if(dt < 0) {
			throw new IllegalArgumentException("Delta-time debe ser positivo");
		}
		this.dt = dt;
		
		for(SimulatorObserver o : this.listaOb) {
			o.onDeltaTimeChanged(dt);
		}
	}

	@Override
	public void addObserver(SimulatorObserver o) {
		if(!this.listaOb.contains(o)) {
			this.listaOb.add(o);
		}
		o.onRegister(mpNM, ta, dt);
	}

	@Override
	public void removeObserver(SimulatorObserver o) {
		this.listaOb.remove(o);
	}
}
