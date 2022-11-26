package main;

import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JFrame;

import logic.Party;
import logic.players.RealPlayer;
import view.PartyView;

public class Main {
	public static void main(String[] args) {
		// Setup party
		RealPlayer p1 = new RealPlayer();
		RealPlayer p2 = new RealPlayer();
		Party p = new Party(p1, p2);
		p1.setPlaying(p);
		p2.setPlaying(p);
		
		PartyView pv = new PartyView(p);
		pv.setPreferredSize(new Dimension(600, 500));
		
		JFrame frame = new JFrame("FrameDemo");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setLayout(new FlowLayout());
		frame.add(pv);;
		frame.setSize(700, 700);
		frame.setResizable(false);
		frame.setVisible(true);
	}
}
