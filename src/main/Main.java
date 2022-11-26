package main;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;

import javax.swing.JFrame;

import logic.Party;
import view.PartyView;

public class Main {
	public static void main(String[] args) {
		Party p = null;
	      try (FileInputStream fis = new FileInputStream("Test.parti");
	           ObjectInputStream ois = new ObjectInputStream(fis)) {
	          p = (Party) ois.readObject();
	      } catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	      
		
		// Setup party
		
		PartyView pv = new PartyView(p);
		pv.setPreferredSize(new Dimension(620, 500));
		
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
