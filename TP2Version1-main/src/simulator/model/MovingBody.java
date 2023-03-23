package simulator.model;

import simulator.misc.Vector2D;

public class MovingBody extends Body {
	
	private Vector2D a;

	public MovingBody(String id, String gid,  Vector2D p, Vector2D v, double m) {
		super(id, gid, p, v, m);
		this.a = new Vector2D();
	}

	@Override
	void advance(double dt) {
		if(this.m == 0) {
			this.a = new Vector2D();
			//ANYADIR SET VELOCIDAD = 0 ???
		}
		else {
			this.a = this.f.scale(1/m);
		}
		this.p = this.p.plus((this.v.scale(dt)).plus(this.a.scale(dt*dt/2)));
		this.v = this.v.plus(this.a.scale(dt));
	}

}
