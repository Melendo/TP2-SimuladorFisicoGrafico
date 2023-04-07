package simulator.view;

import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import simulator.control.Controller;

public class MainWindow extends JFrame {

	private static final long serialVersionUID = 1L;
	private Controller _ctrl;
	
	public MainWindow(Controller ctrl) {
		super("Physics Simulator");
		_ctrl = ctrl;
		initGUI();
	}
	
	private void initGUI() {
		// TODO crear ControlPanel y añadirlo en PAGE_START de mainPanel
		// TODO crear StatusBar y añadirlo en PAGE_END de mainPanel
		JPanel mainPanel = new JPanel(new BorderLayout());
		setContentPane(mainPanel);
		mainPanel.add(new ControlPanel(_ctrl), BorderLayout.PAGE_START);
		mainPanel.add(new StatusBar(_ctrl), BorderLayout.PAGE_END);
	
		
		// Definición del panel de tablas (usa un BoxLayout vertical)
		JPanel contentPanel = new JPanel();
		contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
		mainPanel.add(contentPanel, BorderLayout.CENTER);

	// TODO crear la tabla de grupos y añadirla a contentPanel.
	// Usa setPreferredSize(new Dimension(500, 250)) para fijar su tamaño
		JTable groupsTable = new JTable(new GroupsTableModel(_ctrl));
		JPanel groupsView = createViewPanel(groupsTable, "Grupos");
		groupsTable.setShowGrid(false);
		groupsView.setPreferredSize(new Dimension(500, 250));
		contentPanel.add(groupsView);
		
		
	// TODO crear la tabla de cuerpos y añadirla a contentPanel.
	// Usa setPreferredSize(new Dimension(500, 250)) para fijar su tamaño
		JTable bodiesTable = new JTable(new BodiesTableModel(_ctrl));
		JPanel bodiesView = createViewPanel(bodiesTable, "Cuerpos");
		bodiesTable.setShowGrid(false);
		bodiesView.setPreferredSize(new Dimension(500, 250));
		contentPanel.add(bodiesView);
		
		
	// TODO llama a Utils.quit(MainWindow.this) en el método windowClosing
		//addWindowListener();
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		pack();
		setVisible(true);
	}
	
	private JPanel createViewPanel(JComponent c, String title) {
		JPanel p = new JPanel(new BorderLayout());
		JScrollPane scroll = new JScrollPane(c);
		scroll.getViewport().setBackground(Color.WHITE);
		p.add(scroll);
		return p;
	}
}
