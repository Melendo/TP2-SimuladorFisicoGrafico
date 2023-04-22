package simulator.view;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import simulator.control.Controller;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import org.json.JSONObject;

import simulator.model.BodiesGroup;
import simulator.model.Body;
import simulator.model.SimulatorObserver;

class ForceLawsDialog extends JDialog implements SimulatorObserver {
	private DefaultComboBoxModel<String> _lawsModel;
	private DefaultComboBoxModel<String> _groupsModel;
	private DefaultTableModel _dataTableModel;
	private Controller _ctrl;
	private List<JSONObject> _forceLawsInfo;
	private String[] _headers = { "Key", "Value", "Description" };
	private JTable tabla;
	private JButton botonOk, botonCancel;
	private JScrollPane scroll;
	private Box vox, botonVox;
	private JComboBox Lcombovox, Gcombovox ;
	private JLabel texto1, texto2;
	private int _status;
	private int _selectedLawsIndex;
	
	// TODO en caso de ser necesario, añadir los atributos aquí…
	
	ForceLawsDialog(Frame parent, Controller ctrl) {
			super(parent, true);
				_ctrl = ctrl;
					initGUI();
					load(0);
					ctrl.addObserver(this);
	}
	
	private void initGUI() {
		setTitle("Force Laws Selection");
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		setContentPane(mainPanel);
		// _forceLawsInfo se usará para establecer la información en la tabla
		_forceLawsInfo = _ctrl.getForceLawsInfo();
		// TODO crear un JTable que use _dataTableModel, y añadirla al panel
		
		
		_dataTableModel = new DefaultTableModel() {
			
			@Override
			public boolean isCellEditable(int row, int column) {
				// TODO hacer editable solo la columna 1
				return column == 1;
			}
		};
		
		texto1 = new JLabel("Select a force law and provide values for the parameters in the Value column");
		texto1.setAlignmentX(RIGHT_ALIGNMENT);
		texto2 = new JLabel("(default values are used for parameter with no value).");
		texto2.setAlignmentX(RIGHT_ALIGNMENT);
		mainPanel.add(texto1);
		mainPanel.add(texto2);
		_dataTableModel.setColumnIdentifiers(_headers);
		tabla = new JTable(_dataTableModel);
		mainPanel.add(tabla);
		scroll = new JScrollPane(tabla);
		mainPanel.add(scroll);		
		
		_lawsModel = new DefaultComboBoxModel<>();
		// TODO añadir la descripción de todas las leyes de fuerza a _lawsModel
		
		for(JSONObject obj: _forceLawsInfo) {
			_lawsModel.addElement(obj.get("desc").toString());
		}
		
		
		// TODO crear un combobox que use _lawsModel y añadirlo al panel
		
		vox = Box.createHorizontalBox();
		Lcombovox = new JComboBox<>(_lawsModel);
		Lcombovox.setAlignmentX(Component.CENTER_ALIGNMENT);
		Lcombovox.addActionListener(new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
		lawsComboBoxAction(e);

		}

		});
		
		vox.add(new JLabel("Force Law: "));
		vox.add(Lcombovox);
		vox.add(new JLabel("Group: "));
		
		
		_groupsModel = new DefaultComboBoxModel<>();
		// TODO crear un combobox que use _groupsModel y añadirlo al panel
	
		Gcombovox = new JComboBox<>(_groupsModel);
		vox.add(Gcombovox);
		
		mainPanel.add(vox);
		// TODO crear los botones OK y Cancel y añadirlos al panel
		
		
		botonOk = new JButton("Ok");
		botonOk.setAlignmentX(CENTER_ALIGNMENT);
		botonOk.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
		okAction(e);
		}
		});
		
		botonCancel = new JButton("Cancel");
		botonCancel.setAlignmentX(CENTER_ALIGNMENT);
		botonCancel.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
		cancelAction(e);
		}
		});
		
		botonVox =  Box.createHorizontalBox();
		botonVox.add(botonCancel);
		botonVox.add(botonOk);
		mainPanel.add(botonVox);
		
		
		setPreferredSize(new Dimension(700, 400));
		pack();
		setResizable(false);
		setVisible(false);
	}
	
	private void lawsComboBoxAction(ActionEvent e) {
		int seleccion = Lcombovox.getSelectedIndex();
		this.load(seleccion);
	}
	
	private void load(int sel) {
		//borramos la tabla
		int rowCount = _dataTableModel.getRowCount();;
		for (int i = 0; i < rowCount; i++) {
			_dataTableModel.removeRow(0);
		}
		
		JSONObject dataLaw = _forceLawsInfo.get(sel).getJSONObject("data");
		
		for (String str : dataLaw.keySet()) {
			
			Vector<String> vector = new Vector<String>();
			
			vector.add(str);
			vector.add("");
			vector.add(dataLaw.getString(str));
			
			_dataTableModel.addRow(vector);
		}
		_selectedLawsIndex = sel;
		_dataTableModel.fireTableStructureChanged();
		
	}
	
	private void okAction(ActionEvent e) {
		_status = 1;
	}
	
	private void cancelAction(ActionEvent e) {
		_status = 0;
		this.setVisible(false);
	}
	
	public int open() {
		if (_groupsModel.getSize() == 0)
		return _status;
		// TODO Establecer la posición de la ventana de diálogo de tal manera que se
		// abra en el centro de la ventana principal
		this.setLocationRelativeTo(getParent());
		pack();
		setVisible(true);
		return _status;
	}
	
	// TODO el resto de métodos van aquí…

	@Override
	public void onAdvance(Map<String, BodiesGroup> groups, double time) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onReset(Map<String, BodiesGroup> groups, double time, double dt) {
		// TODO Auto-generated method stub
		_groupsModel.removeAllElements();
	}

	@Override
	public void onRegister(Map<String, BodiesGroup> groups, double time, double dt) {
		// TODO Auto-generated method stub
		for (String i : groups.keySet()) 
			_groupsModel.addElement(groups.get(i).getId());
		
		_selectedLawsIndex = 0;
	}
		

	@Override
	public void onGroupAdded(Map<String, BodiesGroup> groups, BodiesGroup g) {
		// TODO Auto-generated method stub
		_groupsModel.addElement(g.getId());
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
