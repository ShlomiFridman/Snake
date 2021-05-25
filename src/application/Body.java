package application;

import java.util.List;
import java.util.Iterator;
import java.util.LinkedList;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;


public class Body {
	
	private LinkedList<Square> body;
	private GridPane grid;
	private int size;
	private Dir dir;
	
	public Body(GridPane grid,int i,int j){
		size=3;
		setGrid(grid);
		body = new LinkedList<Square>();
		body.addFirst(new Square(i,j,Color.YELLOW));
		addToGrid(getHead());
	}
	
	public boolean move() {
		Square head=getHead(),next = atEnd(head);
		if (next==null) {
			switch (dir) {
				case UP:
					next = new Square(head.getI()-1,head.getJ(),Color.YELLOW);
					break;
				case DOWN:
					next = new Square(head.getI()+1,head.getJ(),Color.YELLOW);
					break;
				case LEFT:
					next = new Square(head.getI(),head.getJ()-1,Color.YELLOW);
					break;
				case RIGHT:
					next = new Square(head.getI(),head.getJ()+1,Color.YELLOW);
					break;
			}
		}
		body.addFirst(next);
		addToGrid(next);
		if (body.size()>size)
			removeFromGrid(body.removeLast());
		return true;
	};
	
	public boolean collide(Square a,Square b) {
		return a.getI()==b.getI() && a.getJ()==b.getJ();
	}
	
	private boolean checkSelf(Square s,List<Square> list) {	// recursive loop, for inner use
		if (list.isEmpty())
			return false;
		if (collide(s,list.get(0)))
			return true;
		return checkSelf(s,list.subList(1, list.size()));
	}
	
	public void changeColor(Color c) {
		Iterator<Square> it = body.iterator();
		while (it.hasNext())
			it.next().setColor(c);
	}
	
	public boolean eatenSelf() {
		if (this.size<5) return false;
		return checkSelf(getHead(),body.subList(2, body.size()));
	}
	
	public boolean isOverlapping(Square s) {
		return checkSelf(s,body);
	}
	
	public void collapse() {
		size = 3;
		while (body.size()>size)
			removeFromGrid(body.removeLast());
	}
	
	private Square atEnd(Square head) {
		switch (dir) {
			case UP: if (head.getI()-1==-1) return new Square(grid.getRowCount()-1,head.getJ(),Color.YELLOW); break;
			case DOWN: if (head.getI()+1==grid.getRowCount()) return new Square(0,head.getJ(),Color.YELLOW); break;
			case LEFT: if (head.getJ()==0)  return new Square(head.getI(),grid.getColumnCount()-1,Color.YELLOW); break;
			case RIGHT: if (head.getJ()+1==grid.getColumnCount())  return new Square(head.getI(),0,Color.YELLOW); break;
		}
		return null;
	}
	
	public Square getHead() {
		return body.peekFirst();
	}

	public void addToGrid(Square s) {
		grid.add(s, s.getJ(),s.getI());
	}
	public void removeFromGrid(Square s) {
		grid.getChildren().remove(s);
	}
	
	public void changeDir(Dir d) {
		if (this.dir.isOp(d))
			return;
		this.dir=d;
	}

	public Dir getDir() {
		return dir;
	}

	public void setDir(Dir dir) {
		this.dir = dir;
	}

	public List<Square> getBody() {
		return body;
	}

	public int getSize() {
		return size;
	}
	
	public void setSize(int size) {
		this.size = size;
	}

	public void setGrid(GridPane grid) {
		this.grid=grid;
	}

}
