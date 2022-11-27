package view;

import java.awt.Dimension;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;

import javax.swing.JFrame;

import logic.Party;

public class MainFrame extends JFrame {
	
	private static final long serialVersionUID = 8456560429229699542L;

	public MainFrame() {
		this.setSize(new Dimension(700, 600));
		this.setResizable(false);
		this.setLocationByPlatform(true);
		
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
	      
	    this.add(new PartyView(this, p));
	    this.setJMenuBar(new MenuBar());
	}
}
