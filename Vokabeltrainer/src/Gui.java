import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

/**
 *
 * Beschreibung
 * Training API to JAVA language
 * @version 1.0 vom 14.03.2022
 * @author DD
 * @
 */

public class Gui {
	// Anfang Attribute

	private JList englischList = new JList();
	private DefaultListModel englischListModel = new DefaultListModel();
	private JScrollPane englischScrollPane = new JScrollPane(englischList);
	private JList germanList = new JList();
	private DefaultListModel germanListModel = new DefaultListModel();
	private JScrollPane germanScrollPane = new JScrollPane(germanList);
	// Lists^^
	private JButton bAdd = new JButton();
	private JButton bRemove = new JButton();
	private JLabel lEnglisch = new JLabel();
	private JTextField txfieldEnglisch = new JTextField();
	private JButton bSetLoeschen = new JButton();
	private JButton bUeben = new JButton();
	private JLabel lAnzahl = new JLabel();

	private JTextField txfieldDeutsch = new JTextField();
	private JLabel lDeutsch = new JLabel();
	private JLabel lEnglisch1 = new JLabel();
	private JLabel lDeutsch1 = new JLabel();
	private int i = 0;

	// Ende Attribute

	public void createGui(String title) {
		// Frame-Initialisierung

		JFrame frame = new JFrame();

		frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
		int frameWidth = 410;
		int frameHeight = 600;
		frame.setSize(frameWidth, frameHeight);
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (d.width - frame.getSize().width) / 2;
		int y = (d.height - frame.getSize().height) / 2;
		frame.setLocation(x, y);
		frame.setTitle(title);
		frame.setResizable(false);
		JPanel cp = new JPanel(null);
		frame.add(cp);
		// Anfang Komponenten

		bAdd.setBounds(163, 397, 67, 33);
		bAdd.setLabel("Add");
		bAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				bAdd_ActionPerformed(evt);
			}
		});
		cp.add(bAdd);

		bRemove.setBounds(307, 397, 80, 25);
		bRemove.setLabel("Remove");
		bRemove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				bRemove_ActionPerformed(evt);
			}
		});
		cp.add(bRemove);
		lEnglisch.setBounds(3, 47, 110, 20);
		lEnglisch.setText("Englisch");
		cp.add(lEnglisch);
		txfieldEnglisch.setBounds(3, 362, 190, 28);
		cp.add(txfieldEnglisch);
		bSetLoeschen.setBounds(280, 538, 110, 17);
		bSetLoeschen.setLabel("Set Löschen");
		bSetLoeschen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				bSetLoeschen_ActionPerformed(evt);
			}
		});
		cp.add(bSetLoeschen);
		bUeben.setBounds(6, 516, 99, 41);
		bUeben.setLabel("Üben");
		bUeben.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				bUeben_ActionPerformed(evt);
			}
		});
		bUeben.setBackground(new Color(204, 255, 204));
		bUeben.setForeground(Color.BLACK);
		cp.add(bUeben);
		lAnzahl.setBounds(170, 47, 78, 20);

		cp.add(lAnzahl);
		// Listen
		// --
		englischList.setModel(englischListModel);
		englischScrollPane.setBounds(3, 70, 190, 268);
		cp.add(englischScrollPane);
		germanList.setModel(germanListModel);
		germanScrollPane.setBounds(201, 70, 190, 268);
		cp.add(germanScrollPane);
		// Listen
		// --
		txfieldDeutsch.setBounds(201, 362, 190, 28);
		cp.add(txfieldDeutsch);
		lDeutsch.setBounds(343, 47, 94, 20);
		lDeutsch.setText("Deutsch");
		cp.add(lDeutsch);
		lEnglisch1.setBounds(3, 340, 110, 20);
		lEnglisch1.setText("Englisch");
		cp.add(lEnglisch1);
		lDeutsch1.setBounds(343, 341, 94, 20);
		lDeutsch1.setText("Deutsch");
		cp.add(lDeutsch1);
		germanList.setEnabled(false);
		germanList.setSelectedIndex(englischList.getSelectedIndex());

		// Vokablen laden
		
		DatabaseHandler.databaseController.loadVocs();
		englischListModel.addAll(DatabaseHandler.databaseController.vocablesEnglish.values());
		germanListModel.addAll(DatabaseHandler.databaseController.vocablesGerman.values());
		
		System.out.println(DatabaseHandler.counter);
		lAnzahl.setText("Anzahl: " + DatabaseHandler.databaseController.vocablesEnglish.size());
		// Ende laden

		// Ende Komponenten

		frame.setVisible(true);

	} // end of public

	// Anfang Methoden
	// -----------------------------------------------------------------------------------------------------

	public void bAdd_ActionPerformed(ActionEvent evt) {

		if (!txfieldDeutsch.getText().equals("") && !txfieldEnglisch.getText().equals("")) {
			DatabaseHandler.counter++;
			englischListModel.addElement(txfieldEnglisch.getText());
			DatabaseHandler.databaseController.vocablesEnglish.put(DatabaseHandler.counter-1, txfieldEnglisch.getText());

			germanListModel.addElement(txfieldDeutsch.getText());
			DatabaseHandler.databaseController.vocablesGerman.put(DatabaseHandler.counter-1, txfieldDeutsch.getText());

			DatabaseHandler.databaseController.insertdata(txfieldEnglisch.getText(), txfieldDeutsch.getText());

			txfieldDeutsch.setText("");
			txfieldEnglisch.setText("");
			lAnzahl.setText("Anzahl: " + DatabaseHandler.counter);
		} else {

			Windows.windowsHandler.deniedandOk(0);

		}

	} // end of bAdd_ActionPerformed

	public void bRemove_ActionPerformed(ActionEvent evt) {
		

		DatabaseHandler.databaseController.removeData(englischList.getSelectedIndex());
		
		DatabaseHandler.databaseController.vocablesGerman.remove(englischList.getSelectedIndex());
		DatabaseHandler.databaseController.vocablesEnglish.remove(englischList.getSelectedIndex());

		// System.out.println(DatabaseHandler.databaseController.vocablesEnglish.size());
		
		
		germanList.setSelectedIndex(englischList.getSelectedIndex());
		englischListModel.remove(englischList.getSelectedIndex());
		germanListModel.remove(germanList.getSelectedIndex());

		lAnzahl.setText("Anzahl: " + DatabaseHandler.counter);

	} // end of bRemove_ActionPerformed

	public void bSetLoeschen_ActionPerformed(ActionEvent evt) {

		DatabaseHandler.databaseController.setLoeschen();
		englischListModel.removeAllElements();
		germanListModel.removeAllElements();

	} // end of bSetLoeschen_ActionPerformed

	public void bUeben_ActionPerformed(ActionEvent evt) {

		
		Windows.windowsHandler.uebenFrame();
		
//		if (i == 0) {
//
//			Windows.windowsHandler.uebenFrame(i);
//			i = 1;
//		} else {
//			Windows.windowsHandler.uebenFrame(i);
//		}

	} // end of bUeben_ActionPerformed

	// Ende Methoden

} // end of class
