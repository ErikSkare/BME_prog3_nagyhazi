package main;

import java.io.File;

import view.MainFrame;

public class Main {
	
	public static void main(String[] args) throws Exception {
		if(args.length != 1)
			throw new Exception("Invalid number of parameters. Only provide search directory!");
		MainFrame.searchDir = new File(args[0]);
		MainFrame fr = new MainFrame();
		fr.setVisible(true);
	}
	
}
