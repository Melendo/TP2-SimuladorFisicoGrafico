package simulator.view;

import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;

import java.awt.Dimension;
import java.awt.FlowLayout;

import simulator.control.Controller;
import simulator.model.BodiesGroup;
import simulator.model.Body;
import simulator.model.SimulatorObserver;

public class StatusBar extends JPanel implements SimulatorObserver {

	private static final long serialVersionUID = 1L;
	Controller ctrl;
	JLabel lTime, lGroups;
	double time;
	int groups;
	private int pizqx = 100;
	private int pizqy = 25;
	
	public StatusBar(Controller ctrl) {
		this.ctrl = ctrl;
		initGUI();
		ctrl.addObserver(this);
	}
	
	private void initGUI() {
		this.setLayout(new FlowLayout(FlowLayout.LEFT));
		this.setBorder(BorderFactory.createBevelBorder(1));
		
		// TODO Utilizar el siguiente código para añadir un separador vertical
		JSeparator s1 = new JSeparator(JSeparator.VERTICAL);
		s1.setPreferredSize(new Dimension(10, 20));
		JSeparator s2 = new JSeparator(JSeparator.VERTICAL);
		s2.setPreferredSize(new Dimension(10, 20));
		
		// TODO Crear una etiqueta de tiempo y añadirla al panel
		lTime = new JLabel("Time: " + getTime());
		lTime.setPreferredSize(new Dimension(pizqx, pizqy));
		this.add(lTime);
		this.add(s1);
		
		// TODO Crear la etiqueta de número de grupos y añadirla al panel
		lGroups = new JLabel("Groups: " + getNGroups());
		lGroups.setPreferredSize(new Dimension(pizqx, pizqy));
		this.add(lGroups);
		this.add(s2);
		
		
	}

	private double getTime() {
		// TODO Auto-generated method stub
		return time;
	}
	
	private void setTime(double time) {
		this.time = time;
	}
	
	private int getNGroups() {
		// TODO Auto-generated method stub
		return groups;
	}
	
	private void setNGroups(int groups) {
		this.groups = groups;
	}
	
	

	@Override
	public void onAdvance(Map<String, BodiesGroup> groups, double time) {
		// TODO Auto-generated method stub
		setTime(time);
		lTime.setText("Time: " + getTime());
		setNGroups(groups.size());
		lGroups.setText("Groups: " + getNGroups());
	}

	@Override
	public void onReset(Map<String, BodiesGroup> groups, double time, double dt) {
		// TODO Auto-generated method stub
		setTime(time);
		lTime.setText("Time: " + getTime());
		setNGroups(groups.size());
		lGroups.setText("Groups: " + getNGroups());
	}

	@Override
	public void onRegister(Map<String, BodiesGroup> groups, double time, double dt) {
		// TODO Auto-generated method stub
		setTime(time);
		lTime.setText("Time: " + getTime());
		setNGroups(groups.size());
		lGroups.setText("Groups: " + getNGroups());
	}

	@Override
	public void onGroupAdded(Map<String, BodiesGroup> groups, BodiesGroup g) {
		// TODO Auto-generated method stub
		setNGroups(groups.size());
		lGroups.setText("Groups: " + getNGroups());
	}

	@Override
	public void onBodyAdded(Map<String, BodiesGroup> groups, Body b) {
		// TODO Auto-generated method stub

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
