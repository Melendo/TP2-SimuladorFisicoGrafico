package simulator.model;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONObject;

public class BodiesGroup {

	private String id;
	private ForceLaws fl;
	private List<Body> bs;
	
	BodiesGroup(String id, ForceLaws fl){
		if(id == null ||  id.trim().length() <= 0) { 
			throw new IllegalArgumentException("id debe contener al menos un caracter distinto del espacio en blanco");		
		}
		else this.id = id;
		if(fl == null ) { 
			throw new IllegalArgumentException("ForceLaws no puede ser NULL");		
		}
		else this.fl = fl;
		
		this.bs = new ArrayList<Body>();
	}

	public String getId() {
		return id;
	}

	public void setForceLaws(ForceLaws fl) {
		if(fl == null ) { 
			throw new IllegalArgumentException("ForceLaws no puede ser NULL");		
		}
		else this.fl = fl;
	}
	
	
	void addBody(Body b) {
		if(b == null || bs.contains(b)) {
			throw new IllegalArgumentException("Ha ocurrido un error al tratar de a�adir el cuerpo");
		}
		bs.add(b);
	}
	
	
	void advance(double dt) {
		if(dt <= 0) {
			throw new IllegalArgumentException("dt debe ser positivo");
		}
		else {
			Iterator<Body> it = bs.iterator();
			Body b;
			while(it.hasNext()) {
				b = it.next();
				b.resetForce();
			}
		
			fl.apply(bs);
		
			Iterator<Body> itAux = bs.iterator();
			while(itAux.hasNext()) {
				b = itAux.next();
				b.advance(dt);
			}
		}
	}
	
	
	public JSONObject getState() {
		JSONObject json = new JSONObject();
		JSONArray arrayBodies = new JSONArray();
		
		json.put("id", getId());
		
		Iterator<Body> it = bs.iterator();
		while(it.hasNext()) {
			arrayBodies.put(it.next().getState());
		}
		
		json.put("bodies", arrayBodies);
		return json;
	}


	public String toString() {
		return getState().toString();
	}
}
