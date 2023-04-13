package simulator.view;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.TitledBorder;
import javax.swing.table.TableModel;

public class InfoTable extends JPanel {

	private static final long serialVersionUID = 1L;
	String _title;
	TableModel _tableModel;
	
	InfoTable(String title, TableModel tableModel) {
		_title = title;
		_tableModel = tableModel;
		initGUI();
	}
	
	private void initGUI() {
	// TODO cambiar el layout del panel a BorderLayout()
		JPanel p = new JPanel(new BorderLayout());
		
	// TODO añadir un borde con título al JPanel, con el texto _title
		p.setBorder(new TitledBorder(_title));
		
	// TODO añadir un JTable (con barra de desplazamiento vertical) que use 
	// _tableModel
		JTable _table = new JTable(_tableModel);
		_table.setShowGrid(false);
		JScrollPane scroll = new JScrollPane(_table);
		scroll.getViewport().setBackground(Color.WHITE);
		p.add(scroll);
	
	}
	
	
}
