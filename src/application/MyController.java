package application;

import javafx.animation.Timeline;
import javafx.animation.KeyFrame;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

public class MyController {

	private Scene scene;

	private Stage stage;
    @FXML
    private AnchorPane mainPane;
    
    private GridPane grid;
    private Body body;
    private Square apple;
    private Timeline timeline;
    private long cooldown;

	public void setScene(Scene scene) {
		this.scene = scene;
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}

	public GridPane getGrid() {
		return this.grid;
	}
	
	public void setup() {
		buildGrid();
		body = new Body(grid,9,8);
		newApple();
		setEvents();
	}
	
	private void setEvents() {
		scene.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				long tmp = System.currentTimeMillis();
				if (cooldown+1000/6>tmp)
					return;
				else
					cooldown=tmp;
				Dir dir = Dir.getDir(event.getCode());
				if (dir==null) return;
				if (body.getDir()!=null && (dir.equals(body.getDir()) || body.getDir().isOp(dir))) return;
				body.setDir(dir);
				if (timeline!=null) {
					timeline.stop();
					timeline.getKeyFrames().clear();
				}
				else
					timeline = new Timeline();
				timeline.getKeyFrames().add(new KeyFrame(Duration.millis(1000/6), new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent event) {
						body.move();
						if (body.collide(body.getHead(), apple)) {
							body.setSize(body.getSize()+1);
							body.getHead().setColor(Color.GREENYELLOW);
							newApple();
						}
						if (body.eatenSelf())
							newGame();
					}
				}));
				timeline.setCycleCount(Timeline.INDEFINITE);
				timeline.play();
			}
			
		});
	}
	
	public void newGame() {
		body.collapse();
		body.changeColor(Color.ORANGE);
		//newApple();
	}
	
	public void newApple() {
		int i,j;
		Square tmp;
		do {
			i = (int) (Math.random()*17);
			j = (int) (Math.random()*17);
			tmp = new Square(i,j,Color.RED);
		} while (body.isOverlapping(tmp));
		if (apple!=null)
			grid.getChildren().remove(apple);
		this.apple=tmp;
		grid.add(apple, apple.getJ(),apple.getI());
	}
	
	public void buildGrid() {
		grid = new GridPane();
		grid.setPadding(new Insets(5));
		grid.setGridLinesVisible(false);
		grid.setStyle("-fx-background-color:black");
		for (int i=0;i<17;i++) {
			RowConstraints row = new RowConstraints(25);
			ColumnConstraints col = new ColumnConstraints(25);
			grid.getColumnConstraints().add(col);
			grid.getRowConstraints().add(row);
		}
		grid.setMouseTransparent(true);
		mainPane.getChildren().add(grid);
	}
}
