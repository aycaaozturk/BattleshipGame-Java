package jpp.battleship.gui;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import jpp.battleship.board.Board;
import jpp.battleship.board.BoardBuilder;
import jpp.battleship.board.BoardFactory;
import jpp.battleship.logic.BoardState;
import jpp.battleship.logic.BoardStateImpl;
import jpp.battleship.logic.strategy.ProbabilityAndHuntStrategy;
import jpp.battleship.logic.strategy.RandomAndHuntStrategy;
import jpp.battleship.logic.strategy.RandomStrategy;
import jpp.battleship.logic.strategy.TargetStrategy;
import jpp.battleship.model.Coordinate;
import jpp.battleship.model.Ship;
import jpp.battleship.model.ShipClass;
import jpp.battleship.model.ShipImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BattleshipGUI extends Application {
    private static int PANE_SIZE = 30;
    private BoardBuilder boardBuilder;
    private GridPane grid;
    private GridPane userGrid;
    private GridPane computerGrid;
    private ShipWithImage selectedShip;
    private Button selectedShipButton;
    private List<ShipWithImage> shipImages = new ArrayList<>();
    private Map<Coordinate, Pane> panes = new HashMap<>();
    private Map<Coordinate, Pane> userPanes = new HashMap<>();
    private Map<Coordinate, Pane> computerPanes = new HashMap<>();
    private Pane currentCell;
    private Stage stage;
    private Board userBoard;
    private Board computerBoard;
    private BoardState userBoardState;
    private BoardState computerBoardState;
    private Image smoke;
    private Image miss;
    private Image hit;
    private int remaining = 5;
    private Button startGameButton;
    private Map<String, Button> userShipButtons = new HashMap<>();
    private Map<String, Button> computerShipButtons = new HashMap<>();
    private RadioButton easy;
    private RadioButton medium;
    private RadioButton hard;
    private TextField nameField;
    private CheckBox goFirstCheckBox;
    private Label moveLabel;
    private boolean usersTurn;
    private String gameWon;
    private TargetStrategy strategy;

    private GridPane createEmptyGrid(Map<Coordinate, Pane> panes, int who) {
        GridPane grid = new GridPane();
        for (int x = 0; x < 11; x++) {
            for (int y = 0; y < 11; y++) {
                if (x == 0 && y > 0) {
                    Pane cell = new Pane();
                    cell.setPrefSize(PANE_SIZE, PANE_SIZE);
                    cell.setStyle("-fx-border-color: black; ");
                    Label label = new Label("  " + y);
                    cell.getChildren().add(label);
                    grid.add(cell, y, x);
                } else if (y == 0 && x > 0) {
                    Pane cell = new Pane();
                    cell.setPrefSize(PANE_SIZE, PANE_SIZE);
                    cell.setStyle("-fx-border-color: black; ");
                    Label label = new Label("  " + (char) ('A' + x - 1));
                    cell.getChildren().add(label);
                    grid.add(cell, y, x);
                } else if (x > 0 && y > 0) {
                    Pane cell = new Pane();
                    cell.setPrefSize(30, 30);
                    cell.setStyle("-fx-border-color: white; -fx-background-color: darkblue;");
                    if (who == 0) {
                        cell.setOnMouseEntered(this::previewShipPlacement);
                        cell.setOnMouseClicked(this::placeShip);
                    } else if (who == 2) {
                        cell.setOnMouseEntered(this::previewShoot);
                        cell.setOnMouseClicked(this::shoot);
                    }

                    grid.add(cell, y, x);
                    panes.put(new Coordinate(y - 1, x - 1), cell);
                }
            }
        }
        return grid;
    }

    private Scene gameScene() {
        smoke = new Image(getClass().getResource("/effects/smoke.png").toExternalForm(), PANE_SIZE, PANE_SIZE, false, false);
        miss = new Image(getClass().getResource("/effects/miss.png").toExternalForm(), 20, 20, false, false);
        hit = new Image(getClass().getResource("/effects/explosion.png").toExternalForm(), PANE_SIZE, PANE_SIZE, false, false);

        userPanes = new HashMap<>();
        computerPanes = new HashMap<>();
        remaining = 5;
        userShipButtons = new HashMap<>();
        computerShipButtons = new HashMap<>();

        gameWon = "";
        if (goFirstCheckBox.isSelected()) {
            usersTurn = true;
        } else {
            usersTurn = false;
        }
        if (easy.isSelected()) {
            strategy = new RandomStrategy();
        } else if (medium.isSelected()) {
            strategy = new RandomAndHuntStrategy();
        } else {
            strategy = new ProbabilityAndHuntStrategy();
        }
        for (Ship s : boardBuilder.getShips()) {
            ((ShipImpl) s).reset();
        }
        userBoard = boardBuilder.build();
        computerBoard = BoardFactory.random();

        userBoardState = new BoardStateImpl(userBoard);
        computerBoardState = new BoardStateImpl(computerBoard);
        userPanes = new HashMap<>();
        computerPanes = new HashMap<>();
        userGrid = createEmptyGrid(userPanes, 1);
        computerGrid = createEmptyGrid(computerPanes, 2);
        drawBoardUser();
        drawBoardComputer();


        HBox moveBox = new HBox();
        moveBox.setAlignment(Pos.CENTER);

        Button restartButton = new Button("Restart");
        restartButton.setOnAction(e -> {
            this.stage.setScene(gameScene());
        });
        Button setupButton = new Button("Goto Setup");
        setupButton.setOnAction(e -> {
            this.stage.setScene(setUpScene());
        });

        Button exitButton = new Button("Exit");
        exitButton.setOnAction(e -> {
            this.stage.close();
        });

        moveBox.getChildren().addAll(restartButton, setupButton, exitButton);

        moveLabel = new Label("Your Move!");
        moveLabel.setStyle(" -fx-font-style: italic; -fx-font-size: 24");
        BorderPane borderPane = new BorderPane();
        borderPane.setStyle("-fx-border-color: black; -fx-background-color: lightgray; -fx-background-size: 1");
        borderPane.setCenter(moveLabel);
        borderPane.setRight(moveBox);

        VBox userBox = new VBox();
        userBox.setAlignment(Pos.CENTER);
        HBox userShipBox = new HBox(10);
        userShipBox.setAlignment(Pos.CENTER);
        for (ShipWithImage ship : shipImages) {
            ImageView shipView = new ImageView(ship.getImage());
            shipView.setFitWidth(30);
            shipView.setPreserveRatio(true);
            Button buttonShip = new Button();
            buttonShip.setGraphic(shipView);
            userShipButtons.put(ship.getShipClass().toString(), buttonShip);
            userShipBox.getChildren().add(buttonShip);
        }
        Label userLabel = new Label(nameField.getText());
        userLabel.setStyle(" -fx-font-weight: bold; -fx-font-size: 18");
        userBox.getChildren().addAll(userLabel, userGrid, userShipBox);

        VBox computerBox = new VBox();
        computerBox.setAlignment(Pos.CENTER);
        HBox computerShipBox = new HBox(10);
        computerShipBox.setAlignment(Pos.CENTER);
        for (ShipWithImage ship : shipImages) {
            ImageView shipView = new ImageView(ship.getImage());
            shipView.setFitWidth(PANE_SIZE);
            shipView.setPreserveRatio(true);
            Button buttonShip = new Button();
            buttonShip.setGraphic(shipView);
            computerShipButtons.put(ship.getShipClass().toString(), buttonShip);
            computerShipBox.getChildren().add(buttonShip);
        }
        Label computerLabel = new Label("Computer");
        computerLabel.setStyle(" -fx-font-weight: bold; -fx-font-size: 18");
        computerBox.getChildren().addAll(computerLabel, computerGrid, computerShipBox);


        HBox grids = new HBox();
        grids.setAlignment(Pos.CENTER);
        grids.setStyle("-fx-border-color: black; -fx-background-color: lightgray; -fx-background-size: 1");
        grids.setPadding(new Insets(10, 10, 10, 10));
        grids.setSpacing(30);
        grids.getChildren().addAll(userBox, computerBox);


        VBox root = new VBox();
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(10, 10, 10, 10));
        root.getChildren().addAll(borderPane, grids);

        if (usersTurn == false) {
            computerShoot();
        }

        // Scene Setup
        Scene scene = new Scene(root, 900, 700);
        scene.setOnScroll(this::rotateShip);
        return scene;
    }

    private Scene setUpScene() {
        boardBuilder = BoardFactory.empty(10, 10);
        shipImages = new ArrayList<>();
        panes = new HashMap<>();
        loadShipImages();

        HBox nameBox = new HBox(20);
        Label nameLabel = new Label("Name:");
        nameField = new TextField();

        nameBox.getChildren().addAll(nameLabel, nameField);

        ToggleGroup difficultyGroup = new ToggleGroup();
        easy = new RadioButton("Easy");
        medium = new RadioButton("Medium");
        hard = new RadioButton("Hard");
        easy.setToggleGroup(difficultyGroup);
        medium.setToggleGroup(difficultyGroup);
        hard.setToggleGroup(difficultyGroup);
        medium.setSelected(true);
        HBox difficultyBox = new HBox(20, new Label("Difficulty"), easy, medium, hard);

        goFirstCheckBox = new CheckBox("Go First");

        grid = createEmptyGrid(panes, 0);

        HBox shipSelection = new HBox(10);
        for (ShipWithImage ship : shipImages) {
            ImageView shipView = new ImageView(ship.getImage());
            shipView.setFitWidth(30);
            shipView.setPreserveRatio(true);
            Button buttonShip = new Button();
            buttonShip.setGraphic(shipView);
            buttonShip.setOnMouseClicked(event -> selectShip(ship, buttonShip));
            shipSelection.getChildren().add(buttonShip);
        }

        startGameButton = new Button("Start Game!");
        startGameButton.setDisable(true);
        startGameButton.setOnAction(e -> {
            this.stage.setScene(gameScene());

        });

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20, 20, 20, 20));
        layout.getChildren().addAll(nameBox, difficultyBox, grid, shipSelection, goFirstCheckBox, startGameButton);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout, 600, 700);
        scene.setOnScroll(this::rotateShip);
        return scene;
    }

    @Override
    public void start(Stage stage) {
        this.stage = stage;
        this.stage.setTitle("Battleship");

        this.stage.setScene(setUpScene());
        this.stage.show();
    }

    private void loadShipImages() {
        shipImages.add(new ShipWithImage(ShipClass.DESTROYER, new Image(getClass().getResource("/ships/Destroyer.png").toExternalForm(), PANE_SIZE, PANE_SIZE * ShipClass.DESTROYER.length(), false, false)));
        shipImages.add(new ShipWithImage(ShipClass.SUBMARINE, new Image(getClass().getResource("/ships/Submarine.png").toExternalForm(), PANE_SIZE, PANE_SIZE * ShipClass.SUBMARINE.length(), false, false)));
        shipImages.add(new ShipWithImage(ShipClass.CRUISER, new Image(getClass().getResource("/ships/Cruiser.png").toExternalForm(), PANE_SIZE, PANE_SIZE * ShipClass.CRUISER.length(), false, false)));
        shipImages.add(new ShipWithImage(ShipClass.BATTLESHIP, new Image(getClass().getResource("/ships/Battleship.png").toExternalForm(), PANE_SIZE, PANE_SIZE * ShipClass.BATTLESHIP.length(), false, false)));
        shipImages.add(new ShipWithImage(ShipClass.CARRIER, new Image(getClass().getResource("/ships/Carrier.png").toExternalForm(), PANE_SIZE, PANE_SIZE * ShipClass.CARRIER.length(), false, false)));

    }

    private void selectShip(ShipWithImage ship, Button shipButton) {
        selectedShip = ship;
        if (selectedShipButton != null) {
            selectedShipButton.setDisable(false);
        }
        selectedShipButton = shipButton;
        shipButton.setDisable(true);

    }

    private void drawBoard() {
        for (Coordinate c : panes.keySet()) {

            panes.get(c).getChildren().clear();
        }
        for (Ship ship : boardBuilder.getShips()) {
            Map<Coordinate, ImageView> imageViews = ((ShipWithImage) ship).getImageViews();
            for (Coordinate c : imageViews.keySet()) {

                panes.get(c).getChildren().add(imageViews.get(c));
            }
        }
    }

    private void previewPane() {
        if (selectedShip == null) return;
        if (this.currentCell == null) return;
        int y = GridPane.getRowIndex(currentCell) - 1;
        int x = GridPane.getColumnIndex(currentCell) - 1;
        Coordinate coordinate = new Coordinate(x, y);
        selectedShip.setReference(coordinate);
        if (boardBuilder.canAddShip(selectedShip)) {
            Map<Coordinate, ImageView> imageViews = selectedShip.getImageViews();
            for (Coordinate c : imageViews.keySet()) {
                panes.get(c).getChildren().add(imageViews.get(c));
            }
        }
    }

    private void previewShipPlacement(MouseEvent event) {
        if (selectedShip == null) return;
        drawBoard();
        this.currentCell = (Pane) event.getSource();
        previewPane();

    }

    private void placeShip(MouseEvent event) {
        if (selectedShip == null) return;
        Pane cell = (Pane) event.getSource();
        int y = GridPane.getRowIndex(cell) - 1;
        int x = GridPane.getColumnIndex(cell) - 1;
        Coordinate coordinate = new Coordinate(x, y);
        selectedShip.setReference(coordinate);

        if (boardBuilder.canAddShip(selectedShip)) {
            boardBuilder.addShip(selectedShip);
            selectedShip = null;
            this.currentCell = null;
            this.selectedShipButton.setDisable(true);
            this.selectedShipButton.setVisible(false);
            remaining--;
            drawBoard();
            if (remaining == 0) {
                startGameButton.setDisable(false);
            }
        }
    }

    private void shoot(MouseEvent event) {
        if (gameWon.equals("") && usersTurn) {
            Pane cell = (Pane) event.getSource();
            int y = GridPane.getRowIndex(cell) - 1;
            int x = GridPane.getColumnIndex(cell) - 1;
            Coordinate coordinate = new Coordinate(x, y);
            if (computerBoard.canShoot(coordinate)) {
                computerBoard.shoot(coordinate);
                drawBoardComputer();

                usersTurn = false;
                if (computerBoardState.remainingShipClasses().size() == 0) {
                    gameWon = "Winner : You!";
                    moveLabel.setText(gameWon);
                    drawBoardComputer();
                } else {
                    moveLabel.setText("Computers turn");
                }
                computerShoot();
            }
        }
    }

    private void previewShoot(MouseEvent event) {
        if (gameWon.equals("") && usersTurn) {
            Pane cell = (Pane) event.getSource();
            int y = GridPane.getRowIndex(cell) - 1;
            int x = GridPane.getColumnIndex(cell) - 1;
            Coordinate coordinate = new Coordinate(x, y);
            if (computerBoard.canShoot(coordinate)) {

                drawBoardComputer();

                Circle circle1 = new Circle(10, 10, 5);

                circle1.setStroke(Color.RED);
                circle1.setFill(Color.RED);

                Group group = new Group();
                group.getChildren().addAll(circle1);
                computerPanes.get(coordinate).getChildren().add(group);
            }
        }
    }

    private void computerShoot() {
        if (gameWon.equals("") && usersTurn == false) {
            Coordinate next = strategy.next(userBoardState);
            userBoard.shoot(next);
            drawBoardUser();
            usersTurn = true;
            if (userBoardState.remainingShipClasses().size() == 0) {
                gameWon = "Winner: Computer";
                moveLabel.setText(gameWon);
                drawBoardComputer();
            } else {
                moveLabel.setText("Your Move!");
            }
        }

    }

    private void drawBoardUser() {
        for (Coordinate c : userPanes.keySet()) {

            userPanes.get(c).getChildren().clear();
        }
        for (Ship ship : userBoard.getShips()) {
            Map<Coordinate, ImageView> imageViews = ((ShipWithImage) ship).getImageViews();
            for (Coordinate c : imageViews.keySet()) {

                userPanes.get(c).getChildren().add(imageViews.get(c));
            }
        }
        for (Coordinate c : userPanes.keySet()) {

            if (userBoardState.getHits().contains(c)) {
                userPanes.get(c).getChildren().add(new ImageView(hit));
            } else if (userBoardState.getShots().contains(c)) {
                userPanes.get(c).getChildren().add(new ImageView(miss));
            }
        }
        for (Ship ship : userBoardState.getSunkShips()) {
            userShipButtons.get(ship.getShipClass().toString()).setDisable(true);
        }
    }

    private void drawBoardComputer() {
        if (gameWon.equals("")) {
            for (Coordinate c : computerPanes.keySet()) {

                computerPanes.get(c).getChildren().clear();
                if (computerBoardState.availableTargets().contains(c)) {

                    computerPanes.get(c).getChildren().add(new ImageView(smoke));
                } else if (computerBoardState.getHits().contains(c)) {
                    computerPanes.get(c).getChildren().add(new ImageView(hit));
                } else {
                    computerPanes.get(c).getChildren().add(new ImageView(miss));
                }
            }

        } else {
            for (Coordinate c : computerPanes.keySet()) {

                computerPanes.get(c).getChildren().clear();
            }
            for (Ship ship : computerBoard.getShips()) {

                ShipWithImage shipWithImage = new ShipWithImage((ShipImpl) ship);
                Map<Coordinate, ImageView> imageViews = shipWithImage.getImageViews();
                for (Coordinate c : imageViews.keySet()) {

                    computerPanes.get(c).getChildren().add(imageViews.get(c));
                }
            }
            for (Coordinate c : computerPanes.keySet()) {

                if (computerBoardState.getHits().contains(c)) {
                    computerPanes.get(c).getChildren().add(new ImageView(hit));
                } else if (computerBoardState.getShots().contains(c)) {
                    computerPanes.get(c).getChildren().add(new ImageView(miss));
                }
            }
        }
        for (Ship ship : computerBoardState.getSunkShips()) {
            computerShipButtons.get(ship.getShipClass().toString()).setDisable(true);
        }

    }


    private void rotateShip(ScrollEvent event) {
        if (selectedShip != null) {
            drawBoard();
            selectedShip.rotate();
            previewPane();

        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
