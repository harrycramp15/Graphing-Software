package com.harry.src.main;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

import javax.swing.JPanel;

public class Main extends JPanel {
	
	private static final long serialVersionUID = 5526081372119360144L;
	
	private int stroke;
	private int clrPtr;
	
	public static final int WIDTH = 600;
	public static final int HEIGHT = WIDTH;
	
	private final Color bgClr = new Color(200, 200, 200);
	
	private Color[] clrList;
	
	private ArrayList<Curve> curves;
	
	public Main(String... terms) {
		clrList = new Color[6];
		clrList[0] = Color.RED;
		clrList[1] = Color.GREEN;
		clrList[2] = Color.BLUE;
		clrList[3] = Color.ORANGE;
		clrList[4] = Color.YELLOW;
		clrList[5] = Color.MAGENTA;
		
		curves = new ArrayList<Curve>();
		Window window = new Window("Graphing Calculator", WIDTH, HEIGHT, this);
		
		clrPtr = 0;
		for(int i = 0; i < terms.length; i++) {
			Color tmpClr = clrList[clrPtr % clrList.length];
			String tmpStr = terms[i];
			Curve tmpCrv = new Curve(tmpStr, tmpClr);
			curves.add(tmpCrv);
			clrPtr++;
		}
		
		stroke = 1;
		this.addKeyListener(new KeyInput(this, window.getFrame()));
		this.requestFocus();
	}
	
	public void paintComponent(Graphics g) {
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		this.drawGraphBg(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setStroke(new BasicStroke(stroke));
		this.drawCurves(g);
		this.drawHUD(g);
		this.repaint();
	}
	
	private void drawGraphBg(Graphics g) {
		int spacing = 10;
		for(int x = 0; x < WIDTH / spacing; x++) {
			g.setColor((x == (WIDTH / spacing / 2)) ? Color.BLACK : bgClr);
			g.drawLine(x * spacing, 0, x * spacing, HEIGHT);
		}
		for(int y = 0; y < HEIGHT / spacing; y++) {
			g.setColor((y == (HEIGHT / spacing / 2)) ? Color.BLACK : bgClr);
			g.drawLine(0, y * spacing, WIDTH, y * spacing);
		}
	}
	
	private void drawCurves(Graphics g) {
		for(int i = 0; i < curves.size(); i++) {
			Curve tempCurve = curves.get(i);
			tempCurve.render(g);
		}
	}
	
	private void drawHUD(Graphics g) {
		for(int i = 0; i < curves.size(); i++) {
			Curve tempCurve = curves.get(i);
			String exp = tempCurve.getExpression();
			Color clr = tempCurve.getColour();
			g.setColor(clr);
			g.drawString(exp, 10, HEIGHT - ((curves.size() - i) * 20) - 20);
		}
	}
	
	public void addNewExpression(String exp) {
		Color tmpClr = clrList[clrPtr % clrList.length];
		clrPtr++;
		Curve tmpCrv = new Curve(exp, tmpClr);
		curves.add(tmpCrv);
	}
	
	public void clearList() {
		curves.clear();
		clrPtr = 0;
	}
	
	public void popExp() {
		if(curves.size() > 0) {
			curves.remove(curves.size() - 1);
			clrPtr--;
		}
	}
	
	public int getStroke() {
		return stroke;
	}
	
	public void setStroke(int stroke) {
		this.stroke = (stroke > 6) ? 1 : stroke;
	}
	
	public static void main(String[] args) {
		new Main(args);
	}
	
}
