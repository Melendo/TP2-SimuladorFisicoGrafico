package simulator.view;

import java.util.Map;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.filechooser.FileSystemView;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

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
	private JButton _quitButton, fileButton, stopButton;
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
		fileButton = new JButton();
		fileButton.setToolTipText("Open file");
		fileButton.setIcon(new ImageIcon("resources/icons/open.png"));
		fileButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				loadFile();
			}
		});
		_toolaBar.add(fileButton);
		_toolaBar.addSeparator();
		
		
		stopButton = new JButton();
		stopButton.setToolTipText("Stop simulation");
		stopButton.setIcon(new ImageIcon("resources/icons/stop.png"));
		stopButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				stop();
			}
		});
		stopButton.setEnabled(false);
		_toolaBar.add(stopButton);
		_toolaBar.addSeparator();
		
		
		// Quit Button
		_toolaBar.add(Box.createGlue()); // this aligns the button to the right
		_toolaBar.addSeparator();
		_quitButton = new JButton();
		_quitButton.setToolTipText("Quit");
		_quitButton.setIcon(new ImageIcon("resources/icons/exit.png"));
		_quitButton.addActionListener((e) -> Utils.quit(this));
		_toolaBar.add(_quitButton);
	
	}
	
	
	private void loadFile() {
		_fc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
		int r = _fc.showOpenDialog(Utils.getWindow(this));
		if(r == JFileChooser.APPROVE_OPTION) {
			File file = _fc.getSelectedFile();
			try {
				//Pasarlo a Main el _inFile???
				_ctrl.reset();
				_ctrl.loadData(new FileInputStream(file));
			} catch (FileNotFoundException fnf) {
				Utils.showErrorMsg("No se encontro el archivo");
			}
		}
	}
	
	
	private void stop() {
		_stopped = true;
		enableToolBar(true);
	}
	
	
	private void enableToolBar(boolean enable) {
		fileButton.setEnabled(enable);
		//ForceLaws
		//Run
		stopButton.setEnabled(!enable);
		_quitButton.setEnabled(enable);
		
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
