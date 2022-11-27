package view;

import javax.swing.JDialog;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class MenuBar extends JMenuBar {

	private static final long serialVersionUID = -111659266189879540L;

	public MenuBar(MainFrame fr, LoadDialogView.Callback onLoad, NewPlayerGameDialog.Callback onNewPlayerGame, NewRobotGameDialog.Callback onNewRobotGame) {
		// Past games
		JMenu pastGames = new JMenu("Past games");
		JMenuItem load = new JMenuItem("Load");
		pastGames.add(load);
		
		// New game
		JMenu newGame = new JMenu("New game");
		JMenuItem vsPlayer = new JMenuItem("Player vs Player");
		JMenuItem vsRobot = new JMenuItem("Player vs Robot");
		
		vsPlayer.addActionListener((e) -> {
			JDialog d = new NewPlayerGameDialog(fr, onNewPlayerGame);
			d.setVisible(true);
		});
		
		vsRobot.addActionListener((e) -> {
			JDialog d = new NewRobotGameDialog(fr, onNewRobotGame);
			d.setVisible(true);
		});
		
		load.addActionListener((e) -> {
			JDialog d = new LoadDialogView(fr, onLoad);
			d.setVisible(true);
		});
		
		newGame.add(vsPlayer);
		newGame.add(vsRobot);
		
		this.add(pastGames);
		this.add(newGame);
	}
	
}
