import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Random;
import java.util.Timer;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class Windows implements KeyListener {

	public final static Windows windowsHandler = new Windows();

	private JFrame deniedFrame = new JFrame();
	private JButton ok = new JButton();
	private JFrame ueben = new JFrame();
	private Random random = new Random();
	private JLabel text = new JLabel();
	private Timer timer = new Timer();
	private int randomnumber;

	public void deniedandOk(int i) {

		deniedFrame.setSize(200, 200);
		deniedFrame.setTitle("Wrong Input");
		deniedFrame.setResizable(false);
		deniedFrame.setDefaultCloseOperation(deniedFrame.DISPOSE_ON_CLOSE);
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (d.width - deniedFrame.getSize().width) / 2;
		int y = (d.height - deniedFrame.getSize().height) / 2;
		deniedFrame.setLocation(x, y);
		Container cp = deniedFrame.getContentPane();
		cp.setLayout(null);
		// Button
		ok.setBounds(43, 55, 100, 45);
		ok.setLabel("Ok");
		ok.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				deniedFrame.dispose();

			}
		});
		if (i == 0) {
			text.setText("Falsche Eingabe");
		} else if (i == 1) {
			text.setText("Richtig");
		}
		text.setBounds(2, 2, 100, 20);
		cp.add(ok);
		cp.add(text);
		deniedFrame.setVisible(true);

	}

	// Anfang Attribute
	private JTextField txfInput = new JTextField();
	private JButton bcorrect = new JButton();
	private JLabel testdeutsch = new JLabel();
	// Ende Attribute

	public void uebenFrame(int i) {

		// Frame-Initialisierung
		if (i == 0) {
			ueben.setSize(1200, 800);
			Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
			int x = (d.width - ueben.getSize().width) / 2;
			int y = (d.height - ueben.getSize().height) / 2;
			// ueben.setDefaultCloseOperation(ueben.DISPOSE_ON_CLOSE);
			ueben.addWindowListener(new WindowAdapter() {
				public void windowClosing(WindowEvent evt) {
					ueben.dispose();
					;
				}
			});
			ueben.setLocation(x, y);
			ueben.setTitle("Ueben");
			ueben.setResizable(false);
			ueben.setFocusable(true);
			Container cp = ueben.getContentPane();
			cp.setLayout(null);
			// ueben.add(cp);
			// Anfang Komponenten

			txfInput.setBounds(132, 206, 400, 70);
			txfInput.setFont(new Font("serif", Font.BOLD, 60));
			txfInput.addKeyListener(this);
			txfInput.setBackground(Color.WHITE);
			txfInput.setText("");
			cp.add(txfInput);
			bcorrect.setBounds(518, 428, 155, 41);
			bcorrect.setFocusable(false);
			bcorrect.setLabel("Korrektur");
			bcorrect.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					bcorrect_ActionPerformed(evt);
				}
			});

			testdeutsch.setBounds(660, 208, 400, 70);
			testdeutsch.setFont(new Font("serif", Font.BOLD, 60));
			getData();
			cp.setBackground(new Color(0xC0C0C0));
			cp.addKeyListener(this);
			cp.setFocusable(true);
			cp.add(bcorrect);
			cp.add(testdeutsch);

			// Ende Komponenten

			ueben.setVisible(true);
		} else {
			ueben.show();

		}

	}

	private void getData() {
		randomnumber = random.nextInt(DatabaseHandler.databaseController.vocablesGerman.size()) + 1;

		testdeutsch.setText(DatabaseHandler.databaseController.vocablesGerman.get(randomnumber));

	}

	// Anfang Methoden

	public void bcorrect_ActionPerformed(ActionEvent evt) {

		String s = txfInput.getText();
		String s2 = DatabaseHandler.databaseController.vocablesEnglish.get(randomnumber);
		System.out.println(s);
		if (s.equals(s2)) {
			txfInput.setText("");
			getData();
			txfInput.setBackground(new Color(84, 255, 159));

			System.out.println(s);

		} else {

			txfInput.setBackground(new Color(255, 48, 48));

		}

	} // end of bcorrect_ActionPerformed

	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ENTER) {

			bcorrect_ActionPerformed(null);

		} else {

		}

	}

	@Override
	public void keyReleased(KeyEvent e) {

	}

}
