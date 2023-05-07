package simulator.view;

import java.util.Map;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.JToolBar;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import simulator.control.Controller;
import simulator.model.BodiesGroup;
import simulator.model.Body;
import simulator.model.SimulatorObserver;
import javax.swing.SwingUtilities;

public class ControlPanel extends JPanel implements SimulatorObserver {

	private static final long serialVersionUID = 1L;
	private Controller _ctrl;
	private JToolBar _toolaBar;
	private JFileChooser _fc;
	private boolean _stopped = true; // utilizado en los botones de run/stop
	private JButton _quitButton, fileButton, stopButton, runButton, viewButton, ForceLawsDialogButton;
	private JTextField deltaTime;
	private JSpinner spinner;
	private ForceLawsDialog fld;
	// TODO a�ade m�s atributos aqu� �
	
	
	ControlPanel(Controller ctrl) {
		this._ctrl = ctrl;
		_ctrl.addObserver(this);
		initGUI();
	
	}
	
	private void initGUI() {
		setLayout(new BorderLayout());
		_toolaBar = new JToolBar();
		add(_toolaBar, BorderLayout.PAGE_START);
		
		// TODO crear los diferentes botones/atributos y a�adirlos a _toolaBar.
		// Todos ellos han de tener su correspondiente tooltip. Puedes utilizar
		// _toolaBar.addSeparator() para a�adir la l�nea de separaci�n vertical
		// entre las componentes que lo necesiten
		
		//Boton open file
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
		
		//ForceLawsDialog Button
				ForceLawsDialogButton = new JButton();
				ForceLawsDialogButton.setToolTipText("Select force laws from groups");
				ForceLawsDialogButton.setIcon(new ImageIcon("resources/icons/physics.png"));
				ForceLawsDialogButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						forceLawsDialogAction();
					}
				});
				_toolaBar.add(ForceLawsDialogButton);
				_toolaBar.addSeparator();
		
		//Viewer Button
		viewButton = new JButton();
		viewButton.setToolTipText("View Simulation");
		viewButton.setIcon(new ImageIcon("resources/icons/viewer.png"));
		viewButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				viewSimulation();
			}
		});
		_toolaBar.add(viewButton);
		_toolaBar.addSeparator();
		
		
		//RunButton
		runButton = new JButton();
		runButton.setToolTipText("Run simulation");
		runButton.setIcon(new ImageIcon("resources/icons/run.png"));
		runButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				run();
			}
		});
		_toolaBar.add(runButton);
		_toolaBar.addSeparator();
				
				
		//Boton StopButon
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
		
		
		//JSpinner
		JLabel texto2 = new JLabel("Steps:");
		_toolaBar.add(texto2);
		_toolaBar.addSeparator();
		spinner = new JSpinner();
		spinner.setPreferredSize(new Dimension(100, 50));
		_toolaBar.add(spinner);
		_toolaBar.addSeparator();
		
		
		//JTextField
		JLabel texto = new JLabel("DeltaTime:");
		_toolaBar.add(texto);
		_toolaBar.addSeparator();
		deltaTime = new JTextField(10);
		deltaTime.setEditable(true);
		deltaTime.setPreferredSize(new Dimension(100, 50));
		_toolaBar.add(deltaTime);
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
		_fc = new JFileChooser();
		int r = _fc.showOpenDialog(Utils.getWindow(this));
		if(r == JFileChooser.APPROVE_OPTION) {
			File file = _fc.getSelectedFile();
			try {
				_ctrl.reset();
				_ctrl.loadData(new FileInputStream(file));
			} catch (FileNotFoundException fnf) {
				Utils.showErrorMsg("No se encontro el archivo");
			}
		}
	}
	
	private void forceLawsDialogAction() {
		if(fld == null) {
			fld = new ForceLawsDialog(Utils.getWindow(this), _ctrl);
		}
		fld.open();
	}
	
	private void viewSimulation() {
		ViewerWindow viewer = new ViewerWindow((JFrame) Utils.getWindow(this), _ctrl);
	}
	
	
	private void stop() {
		_stopped = true;
		enableToolBar(_stopped);
		
	}
	
	private void run() {
		
		_stopped = false;
		enableToolBar(_stopped);
		_ctrl.setDeltaTime(Double.parseDouble(deltaTime.getText().toString()));
		run_sim(Integer.parseInt(spinner.getValue().toString()));
		
	}
	
	private void run_sim(int n) {
		if (n > 0 && !_stopped) {
			try {
			_ctrl.run(1);
			} catch (Exception e) {
			// TODO llamar a Utils.showErrorMsg con el mensaje de error que
			// corresponda
				Utils.showErrorMsg(e.getMessage());
			// TODO activar todos los botones
			_stopped = true;
			enableToolBar(_stopped);
			return;
			}
			SwingUtilities.invokeLater(() -> run_sim(n - 1));
		} else {
		// TODO activar todos los botones
		_stopped = true;
		enableToolBar(_stopped);
		}
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
