package simulator.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.table.AbstractTableModel;

import simulator.control.Controller;
import simulator.model.BodiesGroup;
import simulator.model.Body;
import simulator.model.SimulatorObserver;

public class GroupsTableModel extends AbstractTableModel implements SimulatorObserver {
	
	private static final long serialVersionUID = 1L;
	Controller ctrl;
	String[] _header = { "Id", "Force Laws", "Bodies" };
	List<BodiesGroup> _groups;
	
	public GroupsTableModel(Controller ctrl) {
		this.ctrl = ctrl;
		this._groups = new ArrayList<BodiesGroup>();
		ctrl.addObserver(this);
	}
	
	@Override
	public int getRowCount() {
		return _groups.size();
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
		BodiesGroup bg = _groups.get(rowIndex);
		switch(columnIndex) {
		case 0:
			res = bg.getId();
			break;
		case 1:
			res = bg.getForceLawsInfo();
			break;
		case 2:
			for(Body b : bg) {
				res = b.getId();
			}
			break;
		}
		return res;
	}

	@Override
	public void onAdvance(Map<String, BodiesGroup> groups, double time) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onReset(Map<String, BodiesGroup> groups, double time, double dt) {
		// TODO Auto-generated method stub
		this._groups.clear();
		fireTableStructureChanged();
	}

	@Override
	public void onRegister(Map<String, BodiesGroup> groups, double time, double dt) {
		// TODO Auto-generated method stub
		for(String i : groups.keySet()) {
			this._groups.add(groups.get(i));
		}
		fireTableStructureChanged();
	}

	@Override
	public void onGroupAdded(Map<String, BodiesGroup> groups, BodiesGroup g) {
		// TODO Auto-generated method stub
		_groups.add(g);
		fireTableStructureChanged();
	}

	//BUSCAR MANERA MÁS EFICIENTE
	@Override
	public void onBodyAdded(Map<String, BodiesGroup> groups, Body b) {
		// TODO Auto-generated method stub
		for(BodiesGroup bg : this._groups) {
			if(b.getgId() == bg.getId()) {
				bg = groups.get(b.getgId());
				break;
			}
		}
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
