package simulator.model;

import java.util.Iterator;
import java.util.List;
import simulator.misc.Vector2D;

public class MovingTowardsFixedPoint implements ForceLaws {
	
	private Vector2D c;
	private double g;
	
	public MovingTowardsFixedPoint (Vector2D c, double g) {
		if(c == null) {
			throw new IllegalArgumentException("El vector c no debe ser null");
		}
		else this.c = c;
		
		if(g <= 0) {
			throw new IllegalArgumentException("La constante gravitacional debe ser positiva");
		}
		else this.g = g;
	}

	@Override
	public void apply(List<Body> bs) {
		Iterator<Body> it = bs.iterator();
		Body bi;
		Vector2D fc;
		
		while(it.hasNext()) {
			bi = it.next();
			fc = this.c.minus(bi.getPosition()).direction().scale(g * bi.getMass());
			bi.addForce(fc);	
		}
		

	}
	
	public String toString() {
	return "Moving towards " + this.c + " with constant acceleration " + this.g;
	}

}
