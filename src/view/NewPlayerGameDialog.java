package view;

import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;

public class NewPlayerGameDialog extends JDialog {

	private static final long serialVersionUID = -4412067804663928027L;

	public NewPlayerGameDialog(MainFrame fr) {
		super(fr, "New Player game");
		
		this.setLayout(new FlowLayout());
		this.setSize(new Dimension(200, 100));
		this.setResizable(false);
		this.setLocationRelativeTo(fr);
		
		JLabel caution = new JLabel("Do you want to start the game?");
		JButton submit = new JButton("Yes");
		JButton cancel = new JButton("No");
		submit.addActionListener(fr.new OnNewPlayerGame());
		submit.addActionListener((e) -> { setVisible(false); });
		cancel.addActionListener((e) -> { setVisible(false); });
		
		this.add(caution);
		this.add(submit);
		this.add(cancel);
	}
	
}
