package view;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Popup;
import javax.swing.PopupFactory;

import logic.Party;

public class SavePopupView extends JPanel {

	private static final long serialVersionUID = 1167732662903006519L;
	
	private Party party;
	
	private JTextField field;
	
	private JButton submit;
	
	private JButton close;
	
	private Popup popup;

	public SavePopupView(Party party) {
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
		this.setBorder(BorderFactory.createLineBorder(Color.black));
	}
	
	public void launch(Component owner, int x, int y) {
		if(popup != null)
			return;
		popup = PopupFactory.getSharedInstance().getPopup(owner, this, x, y);
        popup.show();
	}
	
	class OnClose implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			popup.hide();
			popup = null;
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
				popup.hide();
				popup = null;
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
