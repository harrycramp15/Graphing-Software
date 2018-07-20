package com.harry.src.main;

import java.awt.Color;
import java.awt.Graphics;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class Curve {

	private String exp;
	private Color clr;
	
	private final char[] ops;
	
	private IntPoint[] points;
	
	public Curve(String exp, Color clr) {
		this.exp = exp;
		this.clr = clr;
		
		ops = new char[3];
		ops[0] = '+';
		ops[1] = '/';
		ops[2] = '*';
		//ops[3] = '-';
		final int count = 30;
		exp = exp.replace("-x", "-1x");
		plotPoints(" " + exp + " ", count);
	}
	
	private void plotPoints(String exp, int count) {
		points = new IntPoint[count * 2];
		exp = formatCoefficients(spacing(exp));
		for(int x = -count; x < points.length - count; x++) {
			String subExp = subValue(exp, x);
			int y = 0;
			try {
				y = eval(subExp);
			} catch (ScriptException e) {
				//e.printStackTrace();
				System.out.println("OOF");
			}
			points[x + count] = new IntPoint(x, y);
			System.out.println("SUB EXPRESSION FOR " + x + ": " + subExp + ", Y VALUE:" + y);
		}
	}
	
	private String subValue(String exp, int value) {
		return exp.replace("x", "" + value);
	}
	
	private int eval(String form) throws ScriptException {
		int answer;
		form = lexical(form);
		ScriptEngineManager manager = new ScriptEngineManager();
		ScriptEngine engine = manager.getEngineByName("JavaScript");
		String ansStr = engine.eval(form).toString();
		answer = Integer.valueOf(trim(ansStr));
		return answer;
	}
	
	private String lexical(String exp) {
		int opPoint = 0;
		int pointer = 0;
		int leftSpace = 0;
		int rightSpace = 0;
		String token = "";
		
		opPoint = locateOpPoint(exp);
		while(opPoint != -1) {
			pointer = opPoint;
			while(exp.charAt(pointer) != ' ')
				pointer--;
			leftSpace = pointer;
			
			pointer = opPoint;
			while(exp.charAt(pointer) != ' ')
				pointer++;
			rightSpace = pointer;
			token = exp.substring(leftSpace + 1, rightSpace);
			
			exp = powTranslate(token, exp, leftSpace, rightSpace);
			opPoint = locateOpPoint(exp);
		}
		
		return exp;
	}
	
	private String powTranslate(String token, String exp, int leftSpace, int rightSpace) {
		int opPoint = locateOpPoint(token);
		System.out.println("Token: " + token);
		int leftNum = Integer.valueOf(token.substring(0, opPoint));
		int rightNum = Integer.valueOf(token.substring(opPoint + 1, token.length()));
		String transToken = "Math.pow(" + leftNum + "," + rightNum + ")";
		String leftExp = exp.substring(0, leftSpace + 1);
		String rightExp = exp.substring(rightSpace, exp.length());
		String transExp = leftExp + transToken + rightExp;
		return transExp;
	}
	
	private int locateOpPoint(String exp) {
		for(int i = 0; i < exp.length(); i++) {
			char tempChar = exp.charAt(i);
			if(tempChar == '^')
				return i;
		}
		return -1;
	}
	
	private String trim(String exp) {
		for(int i = 0; i < exp.length(); i++) {
			char tempChar = exp.charAt(i);
			if(tempChar == '.')
				return exp.substring(0, i);
		}
		return exp;
	}
	
	private String spacing(String exp) {
		for(int i = 0; i < ops.length; i++) {
			char tempOp = ops[i];
			exp = exp.replace("" + tempOp, " " + tempOp + " ");
		}
		return exp;
	}
	
	private String formatCoefficients(String exp) {
		System.out.println("EXPRESSION: '" + exp + "'");
		for(int i = 1; i < exp.length(); i++) {
			char tempOp = exp.charAt(i);
			if(tempOp == 'x') {
				if(exp.charAt(i - 1) != ' ') {
					System.out.println("BINGO! " + i);
					String token = extractToken(exp, i);
					System.out.println("TOKEN: " + token);
					String replace = token + " * x";
					exp = exp.replace(token + "x", replace);
				}
			}
		}
		return exp;
	}
	
	private String extractToken(String exp, int index) {
		int start = index;
		int end = start;
		System.out.println("START:" + start);
		while(exp.charAt(start) != ' ')
			start--;
		System.out.println("TOKEN BETWEEN: " + start + " AND " + end);
		return exp.substring(start, end);
	}
	
	public void render(Graphics g) {
		g.setColor(clr);
		for(int i = 0; i < points.length - 1; i++) {
			int x1 = 300 + points[i].getX() * 10;
			int y1 = 300 - points[i].getY() * 10;
			int x2 = 300 + points[i + 1].getX() * 10;
			int y2 = 300 - points[i + 1].getY() * 10;
			g.drawLine(x1, y1, x2, y2);
		}
	}
	
	public String getExpression() {
		return exp;
	}
	
	public Color getColour() {
		return clr;
	}
	
	public IntPoint[] points() {
		return points;
	}
	
}
