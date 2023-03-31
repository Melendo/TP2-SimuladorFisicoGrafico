package simulator.control;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import simulator.factories.Factory;
import simulator.model.Body;
import simulator.model.ForceLaws;
import simulator.model.PhysicsSimulator;

public class Controller {
	
	private PhysicsSimulator sim;
	private Factory<Body> fabB; 
	private Factory<ForceLaws> fabF;

	public Controller(PhysicsSimulator sim, Factory<Body> fabB, Factory<ForceLaws> fabF) {
		this.sim = sim;
		this.fabB = fabB;
		this.fabF = fabF;
	}
	
	public void loadData(InputStream in) {
		JSONObject jsonInupt = new JSONObject(new JSONTokener(in));
		JSONArray groups = jsonInupt.getJSONArray("groups");
		JSONArray bodies = jsonInupt.getJSONArray("bodies");
		
		for (int i = 0; i < groups.length(); i++) {
			sim.addGroup(groups.getString(i));
		}
		
		if(jsonInupt.has("laws")) {
			JSONArray laws = jsonInupt.getJSONArray("laws");
			for (int j = 0; j < laws.length(); j++) {
				sim.setForceLaws(laws.getJSONObject(j).getString("id"), this.fabF.createInstance(laws.getJSONObject(j).getJSONObject("laws")));
			}
		}
		for (int k = 0; k < bodies.length(); k++) {
			sim.addBody(this.fabB.createInstance(bodies.getJSONObject(k)));
		}
	}

	public void run(double n, OutputStream out) {
		JSONArray arrayStates = new JSONArray();		
		PrintStream p = new PrintStream(out);
		
		p.println("{");
		p.println("\"states\": [");
		for (int i = 0; i < n; i++) {
			sim.advance();
			arrayStates.put(sim.getState());
		}
		p.println(arrayStates);
		p.println("]");
		p.println("}");

	}
}
