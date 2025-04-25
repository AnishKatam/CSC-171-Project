import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Cards extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Ace of Spade Left and Ace of Diamond Right");

        Canvas canvas = new Canvas(400, 400);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        drawAceOfSpadeLeft(gc);
        drawAceOfDiamondRight(gc);

        Pane root = new Pane();
        root.getChildren().add(canvas);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    //card drawing methods
    private void drawAceOfSpadeLeft(GraphicsContext gc) {
        drawCard(gc, 0, "A", "♠", Color.BLACK);
    }

    private void drawAceOfDiamondLeft(GraphicsContext gc) {
        drawCard(gc, 0, "A", "♦", Color.RED);
    }

    private void drawAceOfHeartLeft(GraphicsContext gc) {
        drawCard(gc, 0, "A", "♥", Color.RED);
    }

    private void drawAceOfClubLeft(GraphicsContext gc) {
        drawCard(gc, 0, "A", "♣", Color.BLACK);
    }
    private void drawAceOfDiamondRight(GraphicsContext gc) {
        drawCard(gc, 200, "A", "♦", Color.RED);
    }
    private void drawAceOfHeartRight(GraphicsContext gc) {
        drawCard(gc, 200, "A", "♥", Color.RED);
    }
    private void drawAceOfClubRight(GraphicsContext gc) {
        drawCard(gc, 200, "A", "♣", Color.BLACK);
    }
    private void drawAceOfSpadeRight(GraphicsContext gc) {
        drawCard(gc, 200, "A", "♠", Color.BLACK);
    }



    private static void drawCard(GraphicsContext gc, double xOffset, String value, String suit, Color color) {
        // x, y, width, height, arcWidth, arcHeight DRAWS THE CARD
        gc.setFill(Color.WHITE);
        gc.fillRoundRect(xOffset + 10, 30, 180, 280, 10, 10);
        gc.setStroke(Color.BLACK);
        gc.strokeRoundRect(xOffset + 10, 30, 180, 280, 10, 10);

        // poker has *slab serif* cards WHATS DRAWN IN THE CARD
        gc.setFill(color);
        gc.setFont(Font.font("Roboto Slab", 30));
        gc.fillText(value, xOffset + 20, 80);
        gc.fillText(suit, xOffset + 20, 120);
        gc.fillText(suit, xOffset + 160, 280);
    }
}