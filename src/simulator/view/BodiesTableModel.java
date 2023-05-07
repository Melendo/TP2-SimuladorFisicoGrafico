package simulator.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.table.AbstractTableModel;

import simulator.control.Controller;
import simulator.model.BodiesGroup;
import simulator.model.Body;
import simulator.model.SimulatorObserver;

public class BodiesTableModel extends AbstractTableModel implements SimulatorObserver {

	private static final long serialVersionUID = 1L;
	Controller ctrl;
	String[] _header = { "Id", "gId", "Mass", "Velocity", "Position", "Force" };
	List<Body> _bodies;
	
	public BodiesTableModel(Controller ctrl) {
		_bodies = new ArrayList<>();
		this.ctrl = ctrl;
		ctrl.addObserver(this);
	}
	
	
	@Override
	public int getRowCount() {
		return _bodies.size();
	}

	@Override
	public int getColumnCount() {
		return _header.length;
	}
	
	public String getColumnName(int i) {
	    return _header[i];
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Object res = null;
		Body b = _bodies.get(rowIndex);
		switch(columnIndex) {
		case 0:
			res = b.getId();
			break;
		case 1:
			res = b.getgId();
			break;
		case 2:
			res = b.getMass();
			break;
		case 3:
			res = b.getVelocity();
			break;
		case 4:
			res = b.getPosition();
			break;
		case 5:
			res = b.getForce();
			break;
		}
		return res;
	}

	@Override
	public void onAdvance(Map<String, BodiesGroup> groups, double time) {
		// TODO Auto-generated method stub
		fireTableStructureChanged();
	}

	@Override
	public void onReset(Map<String, BodiesGroup> groups, double time, double dt) {
		// TODO Auto-generated method stub
		this._bodies.clear();
		fireTableStructureChanged();
	}

	@Override
	public void onRegister(Map<String, BodiesGroup> groups, double time, double dt) {
		// TODO Auto-generated method stub
		for(String i : groups.keySet()) {
			for(Body b : groups.get(i))
				this._bodies.add(b);
		}
		fireTableStructureChanged();
	}

	@Override
	public void onGroupAdded(Map<String, BodiesGroup> groups, BodiesGroup g) {
		for(Body b : g)
			this._bodies.add(b);
		fireTableStructureChanged();
	}

	@Override
	public void onBodyAdded(Map<String, BodiesGroup> groups, Body b) {
		// TODO Auto-generated method stub
		_bodies.add(b);
		fireTableStructureChanged();
	}

	@Override
	public void onDeltaTimeChanged(double dt) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onForceLawsChanged(BodiesGroup g) {
		// TODO Auto-generated method stub

	}

}
