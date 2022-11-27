package view;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;

import logic.Party;
import logic.players.RealPlayer;

/**
 * @author Skáre Erik
 * A dialógus, ami egy új játékos vs játékos partit hoz létre.
 */
public class NewPlayerGameDialog extends JDialog {

	private static final long serialVersionUID = -4412067804663928027L;
	
	/**
	 * Egy callback, ami a parti létrehozása után hívódik meg.
	 */
	public interface Callback {
		public void run(Party p);
	}
	
	/**
	 * Egy callback példány.
	 */
	private Callback cb;

	/**
	 * @param fr a tartalmazó frame.
	 * @param cb callback.
	 */
	public NewPlayerGameDialog(MainFrame fr, Callback cb) {
		super(fr, "New Player game");
		
		this.cb = cb;
		
		this.setLayout(new FlowLayout());
		this.setSize(new Dimension(200, 100));
		this.setResizable(false);
		this.setLocationRelativeTo(fr);
		
		JLabel caution = new JLabel("Do you want to start the game?");
		JButton submit = new JButton("Yes");
		JButton cancel = new JButton("No");
		submit.addActionListener(new OnNewGame());
		submit.addActionListener((e) -> { setVisible(false); });
		cancel.addActionListener((e) -> { setVisible(false); });
		
		this.add(caution);
		this.add(submit);
		this.add(cancel);
	}
	
	class OnNewGame implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			RealPlayer p1 = new RealPlayer(true);
			RealPlayer p2 = new RealPlayer(false);
			Party p = new Party(p1, p2);
			p1.setPlaying(p);
			p2.setPlaying(p);
			p.startMatch();
			cb.run(p);
		}
		
	}
	
}
