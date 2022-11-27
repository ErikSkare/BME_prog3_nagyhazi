package view;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class MenuBar extends JMenuBar {

	private static final long serialVersionUID = -111659266189879540L;

	public MenuBar() {
		// Past games
		JMenu pastGames = new JMenu("Past games");
		JMenuItem load = new JMenuItem("Load");
		pastGames.add(load);
		
		// New game
		JMenu newGame = new JMenu("New game");
		JMenuItem vsPlayer = new JMenuItem("vs player");
		JMenuItem vsRobot = new JMenuItem("vs robot");
		newGame.add(vsPlayer);
		newGame.add(vsRobot);
		
		this.add(pastGames);
		this.add(newGame);
	}
	
}
