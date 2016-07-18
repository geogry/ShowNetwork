package com.geogry.display;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class Display extends JFrame {
	
	public Display(String titile){
		String path = System.getProperty("user.dir") + "/bin/";
		this.setTitle(titile);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(new Dimension(1000, 600));
		int screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
		int screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
		this.setLocation((screenWidth - 1200)/2, (screenHeight - 700)/2);
		this.setVisible(true);
		
		JMenuItem exit = new JMenuItem("退出");
		exit.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
			
		});
		JMenuItem karate = new JMenuItem("karate");
		karate.addActionListener(new MyActionListener(this, path, "karate", "clusters-karate.txt"));
		JMenuItem strike = new JMenuItem("strike");
		strike.addActionListener(new MyActionListener(this, path, "strike", "clusters-strike.txt"));
		JMenuItem jazz = new JMenuItem("jazz");
		jazz.addActionListener(new MyActionListener(this, path, "jazz", "clusters-jazz.txt"));
		JMenuItem polbooks = new JMenuItem("polbooks");
		polbooks.addActionListener(new MyActionListener(this, path, "polbooks", "clusters-polbooks.txt"));
		JMenuItem football = new JMenuItem("football");
		football.addActionListener(new MyActionListener(this, path, "football", "clusters-football.txt"));
		JMenu start = new JMenu("开始");
		start.add(karate);
		start.add(strike);
		start.add(jazz);
		start.add(polbooks);
		start.add(football);
		start.addSeparator();
		start.add(exit);
		
		JMenuBar menu = new JMenuBar();
		menu.add(start);
		this.setJMenuBar(menu);
	}
	
	public static void main(String[] args) {
		Display myFrame = new Display("网络图");
		myFrame.show();
	}
	
	class MyActionListener implements ActionListener {

		private MyPanel panel;
		private String path;
		private String src;
		private String result;
		private Display myFrame;
		private DataInput dataInput;
		private DataOperator dataOperator;
		private FastNewman fn;
		
		public MyActionListener(Display myFrame, String path, String src, String result){
			this.myFrame = myFrame;
			this.path = path;
			this.src = src;
			this.result = result;
			dataInput = new DataInput();
			dataOperator = new DataOperator();
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			double[][] A = dataOperator.readFile(path + src);
			fn = new FastNewman(A, dataOperator.getMax_id(), dataOperator.getEdgeCount());
			fn.mFastNewman();
			dataOperator.writeFile("clusters-tmp.txt", fn.getClusters());
			//展示社区结构
			if(src.equals("karate") || src.equals("football"))
				dataInput.readFile1(path + src, path + result);
			else
				dataInput.readFile2(path + src, path + result);
			panel = new MyPanel(dataInput.getMax_id(), dataInput.getVertexs(), dataInput.getEdges());
			myFrame.setContentPane(panel);
		}
		
	}

}
