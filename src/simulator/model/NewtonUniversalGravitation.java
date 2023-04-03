package simulator.model;

import java.util.Iterator;
import java.util.List;
import simulator.misc.Vector2D;

public class NewtonUniversalGravitation implements ForceLaws{
	
	private double g;
	
	public NewtonUniversalGravitation(double g) {
		if(g <= 0) {
			throw new IllegalArgumentException("La constante gravitacional debe ser positiva");		
		}
		else this.g = g;
	}

	@Override
	public void apply(List<Body> bs) {
		Iterator<Body> it = bs.iterator();
		Body bi, bj;
		double p, d, f;
		Vector2D fc;
		
		while(it.hasNext()) {
			
			bi = it.next();
			Iterator<Body> itT = bs.iterator();
				
			while(itT.hasNext()) {
					
				bj = itT.next();
				if(!(bi == bj)) {
					p = g * bi.getMass() * bj.getMass();
					d = bj.getPosition().distanceTo(bi.getPosition());
					d *= d;
					f = p/d;
					fc = bj.getPosition().minus(bi.getPosition()).direction().scale(f); 
					bi.addForce(fc); 
				}
			}
		}
	}
	
	public String toString() {
		return "Newton’s Universal Gravitation with G=" + this.g;
	}
	
	
}
