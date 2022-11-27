package view;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import logic.Party;

public class SaveDialogView extends JDialog {

	private static final long serialVersionUID = 1167732662903006519L;
	
	private Party party;
	
	private JTextField field;
	
	private JButton submit;
	
	private JButton close;

	public SaveDialogView(JFrame fr, Party party) {
		super(fr, "Save game");
		
		this.setLayout(new FlowLayout());
		this.setSize(new Dimension(300, 100));
		this.setResizable(false);
		this.setLocationRelativeTo(fr);
		
		this.party = party;
		
		JLabel text = new JLabel("Name");
		
		field = new JTextField(20);
		submit = new JButton("Save");
		close = new JButton("Close");
		
		submit.addActionListener(new OnSubmit());
		close.addActionListener(new OnClose());
		
		this.add(text);
		this.add(field);
		this.add(submit);
		this.add(close);
	}
	
	class OnClose implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			setVisible(false);
		}
		
	}
	
	class OnSubmit implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			FileOutputStream fout = null;
			ObjectOutputStream oos = null;;
			try {
				fout = new FileOutputStream(field.getText() + ".parti");
				oos = new ObjectOutputStream(fout);
				oos.writeObject(party);
				setVisible(false);
			} catch (IOException e1) {
				e1.printStackTrace();
			} finally {
				try {
					if(oos != null)
						oos.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
		
	}
}
