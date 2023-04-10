package simulator.model;

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
		double p, d, f;
		Vector2D fc;
		
		for(Body bi : bs) {
			for(Body bj : bs) {
				
				if(bi != bj) {
					p = g * bi.getMass() * bj.getMass();
					d = bj.getPosition().distanceTo(bi.getPosition());
					if(d > 0) {
						d *= d;
						f = p/d;
						fc = bj.getPosition().minus(bi.getPosition()).direction().scale(f); 
						bi.addForce(fc); 
					}
					else throw new ArithmeticException("D debe ser mayor a cero");
				}
			}
		}
	}
	
	public String toString() {
		return "Newton’s Universal Gravitation with G=" + this.g;
	}
	
	
}
