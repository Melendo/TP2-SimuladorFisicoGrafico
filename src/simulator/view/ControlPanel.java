package simulator.view;

import java.util.Map;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import java.awt.BorderLayout;

import simulator.control.Controller;
import simulator.model.BodiesGroup;
import simulator.model.Body;
import simulator.model.SimulatorObserver;

public class ControlPanel extends JPanel implements SimulatorObserver {

	private static final long serialVersionUID = 1L;
	private Controller _ctrl;
	private JToolBar _toolaBar;
	private JFileChooser _fc;
	private boolean _stopped = true; // utilizado en los botones de run/stop
	private JButton _quitButton;
	// TODO añade más atributos aquí …
	
	
	ControlPanel(Controller ctrl) {
		this._ctrl = ctrl;
		_ctrl.addObserver(this);
		initGUI();
	
	}
	private void initGUI() {
	setLayout(new BorderLayout());
	_toolaBar = new JToolBar();
	add(_toolaBar, BorderLayout.PAGE_START);
	// TODO crear los diferentes botones/atributos y añadirlos a _toolaBar.
	// Todos ellos han de tener su correspondiente tooltip. Puedes utilizar
	// _toolaBar.addSeparator() para añadir la línea de separación vertical
	// entre las componentes que lo necesiten
	// Quit Button
	_toolaBar.add(Box.createGlue()); // this aligns the button to the right
	_toolaBar.addSeparator();
	_quitButton = new JButton();
	_quitButton.setToolTipText("Quit");
	_quitButton.setIcon(new ImageIcon("resources/icons/exit.png"));
	_quitButton.addActionListener((e) -> Utils.quit(this));
	_toolaBar.add(_quitButton);
	
	// TODO crear el selector de ficheros
	//_fc = …
	}
	
	@Override
	public void onAdvance(Map<String, BodiesGroup> groups, double time) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onReset(Map<String, BodiesGroup> groups, double time, double dt) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onRegister(Map<String, BodiesGroup> groups, double time, double dt) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onGroupAdded(Map<String, BodiesGroup> groups, BodiesGroup g) {
		// TODO Auto-generated method stub

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
