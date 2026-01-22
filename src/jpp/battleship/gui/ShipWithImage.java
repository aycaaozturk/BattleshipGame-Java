package jpp.battleship.gui;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import jpp.battleship.model.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

class ShipWithImage extends ShipImpl {
    private Image shipImage;
    private static int PANE_SIZE = 30;

    public ShipWithImage(ShipClass shipType, Image image) {
        super(shipType, new Coordinate(0, 0), Alignment.VERTICAL);
        this.shipImage = image;
    }

    public ShipWithImage(ShipImpl ship) {
        super(ship.getShipClass(), ship.getReference(), ship.getAlignment()) ;
        if (ship.getShipClass() == ShipClass.DESTROYER){
            this.shipImage = new Image(getClass().getResource("/ships/Destroyer.png").toExternalForm(),PANE_SIZE,PANE_SIZE*ShipClass.DESTROYER.length(),false,false);
        } else if (ship.getShipClass() == ShipClass.SUBMARINE){
            this.shipImage = new Image(getClass().getResource("/ships/Submarine.png").toExternalForm(),PANE_SIZE,PANE_SIZE*ShipClass.SUBMARINE.length(),false,false);
        }  else if (ship.getShipClass() == ShipClass.CRUISER){
            this.shipImage = new Image(getClass().getResource("/ships/Cruiser.png").toExternalForm(),PANE_SIZE,PANE_SIZE*ShipClass.CRUISER.length(),false,false);
        }  else if (ship.getShipClass() == ShipClass.BATTLESHIP){
            this.shipImage = new Image(getClass().getResource("/ships/Battleship.png").toExternalForm(),PANE_SIZE,PANE_SIZE*ShipClass.BATTLESHIP.length(),false,false);
        }  else if (ship.getShipClass() == ShipClass.CARRIER){
            this.shipImage = new Image(getClass().getResource("/ships/Carrier.png").toExternalForm(),PANE_SIZE,PANE_SIZE*ShipClass.CARRIER.length(),false,false);
        }
    }


    public Image getImage() {
        return shipImage;
    }

    public Map<Coordinate, ImageView> getImageViews() {
        Map<Coordinate, ImageView> map = new HashMap<Coordinate, ImageView>();

        double segmentWidth = 30;
        double segmentHeight = 30;

        Set<Coordinate> coords = getCoordinates();
        ArrayList<Coordinate> coordList = new ArrayList<Coordinate>(coords);
        if (getAlignment() == Alignment.VERTICAL) {
            coordList.sort((x, y) -> x.getY() - y.getY());
        } else {
            coordList.sort((x, y) -> y.getX() - x.getX());
        }

        for (int i = 0; i < coordList.size(); i++) {
            Coordinate c = coordList.get(i);
            ImageView segment = new ImageView(shipImage);
            segment.setFitHeight(this.getShipClass().length()*30);
            segment.setFitWidth(segmentWidth);
            segment.setFitHeight(segmentHeight);
            segment.setViewport(new javafx.geometry.Rectangle2D(
                    0,
                    i * segmentHeight,
                    segmentWidth,
                    segmentHeight
            ));

            if (getAlignment() == Alignment.HORIZONTAL) segment.setRotate(90);
            map.put(c, segment);
        }
        return map;
    }

}

