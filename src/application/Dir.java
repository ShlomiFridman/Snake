package application;

import javafx.scene.input.KeyCode;

public enum Dir {
	
	UP(KeyCode.W,KeyCode.UP),DOWN(KeyCode.S,KeyCode.DOWN),LEFT(KeyCode.A,KeyCode.LEFT),RIGHT(KeyCode.D,KeyCode.RIGHT);

	final KeyCode arrow,letter;
	
	private Dir(KeyCode l,KeyCode k) {
		this.arrow=k;
		this.letter=l;
	}
	
	public static Dir getDir(KeyCode k) {
		for (Dir d:Dir.values())
			if (k.equals(d.arrow) || k.equals(d.letter))
				return d;
		return null;
	}
	
	public boolean isOp(Dir d) {
		return Math.abs(d.arrow.ordinal()-this.arrow.ordinal())==2;
	}
	@Override
	public String toString() {
		return String.format("Letter = %s | Arrow = %s\n", this.letter,this.arrow);
	}

}
