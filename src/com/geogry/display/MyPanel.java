package com.geogry.display;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.List;

import javax.swing.JPanel;

public class MyPanel extends JPanel {

	private int max_id = 0;
	
	private Object[][] vertexs = null;
	
	private List<int[]> edges = null;
	
	public MyPanel(int max_id, Object[][] vertexs, List<int[]> edges){
		this.setBackground(new Color(210, 210, 210));
		this.setSize(new Dimension(1000, 600));
		this.setVisible(true);
		
		this.max_id = max_id;
		this.vertexs = vertexs;
		this.edges = edges;
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D graphic = (Graphics2D) g;
		
		for(int i = 0; i < edges.size(); i++){
			graphic.setColor(Color.BLACK);
			graphic.setStroke(new BasicStroke(0.4f));
			graphic.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);//Ïû³ý¾â³Ý×´±ßÔµ
			graphic.drawLine((int)vertexs[edges.get(i)[0] - 1][0], (int)vertexs[edges.get(i)[0] - 1][1]
					, (int)vertexs[edges.get(i)[1] - 1][0], (int)vertexs[edges.get(i)[1] - 1][1]);
		}
		for(int i = 0; i < max_id; i++){
			g.setColor((Color)vertexs[i][2]);
			g.fillOval((int)vertexs[i][0] - 3, (int)vertexs[i][1] - 5, 10, 10);
			g.setColor(Color.BLACK);
			g.drawOval((int)vertexs[i][0] - 3, (int)vertexs[i][1] - 5, 10, 10);
			g.drawString("" + (i+1), (int)vertexs[i][0] - 6, (int)vertexs[i][1] - 8);
		}
		//this.repaint();
	}
	
}
