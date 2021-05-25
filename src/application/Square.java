package application;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

public class Square extends Rectangle {
	
	private int i,j;
	private Color color;

	public Square(int i,int j,Color c){
		setI(i);
		setJ(j);
		setColor(c);
		this.setColor(c);
		this.setWidth(23);
		this.setHeight(23);
		this.setStroke(Color.TRANSPARENT);
	}

	public int getI() {
		return i;
	}

	public void setI(int i) {
		this.i = i;
	}

	public int getJ() {
		return j;
	}

	public void setJ(int j) {
		this.j = j;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.setFill(color);
	}
	
	@Override
	public String toString() {
		return String.format("(%d,%d)\n", i,j);
	}
}
