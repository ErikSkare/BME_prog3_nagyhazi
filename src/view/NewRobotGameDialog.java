package view;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;

import logic.Party;
import logic.players.RealPlayer;
import logic.players.RobotPlayer;

public class NewRobotGameDialog extends JDialog {

	private static final long serialVersionUID = 404747377637798238L;
	
	enum Playing {
		WHITE,
		BLACK
	}
	
	public interface Callback {
		public void run(Party p);
	}
	
	private Callback cb;
	
	private JComboBox<Playing> color;
	
	private JButton submit;
	
	private JButton cancel;
	
	public NewRobotGameDialog(MainFrame fr, Callback cb) {
		super(fr, "New Robot game");
		
		this.cb = cb;
		
		this.setLayout(new FlowLayout());
		this.setSize(new Dimension(200, 100));
		this.setResizable(false);
		this.setLocationRelativeTo(fr);
		
		this.color = new JComboBox<Playing>(Playing.values());
		this.submit = new JButton("Start");
		this.cancel = new JButton("Cancel");
		
		this.submit.addActionListener(new OnCreateGame());
		this.submit.addActionListener((e) -> { setVisible(false); });
		
		this.cancel.addActionListener((e) -> { setVisible(false); });
		
		this.add(color);
		this.add(submit);
		this.add(cancel);
	}
	
	class OnCreateGame implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			Playing selected = (Playing) color.getSelectedItem();
			RobotPlayer ap;
			RealPlayer rp;
			if(selected == Playing.WHITE) {
				ap = new RobotPlayer(false);
				rp = new RealPlayer(true);
			} else {
				ap = new RobotPlayer(true);
				rp = new RealPlayer(false);
			}
			Party p = new Party(ap, rp);
			ap.setPlaying(p);
			rp.setPlaying(p);
			p.startMatch();
			cb.run(p);
		}
		
	}
	
}
