package jpp.battleship.board;

import javafx.scene.layout.CornerRadii;
import jpp.battleship.model.Alignment;
import jpp.battleship.model.Coordinate;
import jpp.battleship.model.Ship;
import jpp.battleship.model.ShipClass;

import java.util.*;

public class BoardFactory {

//    Die Klasse BoardFactory
//    Die Klasse BoardFactory stellt Methoden bereit zum Erzeugen von Board bzw. BoardBuilder Objekten.
//    Geben Sie hier jeweils Instanzen Ihrer Umsetzungen der Interfaces zurück.
//
//    Erzeugt einen leeren BoardBuilder für ein Spielfeld der Breite width und Höhe height.
//    Werfen Sie eine IllegalArgumentException falls width oder height kleiner als 2 sind.

    public static BoardBuilder empty(int width, int height) {
        if (width < 2 || height < 2) {
            throw new IllegalArgumentException("arg < 2");
        }
        return new BoardBuilderImpl(width, height);
    }


//    Erzeugt ein Spielfeld mit Breite width, Höhe height und den Schiffen aus ships.
//    Werfen Sie eine IllegalArgumentException falls width oder height kleiner als 2 sind.
//    Werfen Sie eine NullPointerException falls eines der Schiffe null ist.
//    Gibt es mehrere Schiffe einer Schiffsklasse, befindet sich ein Schiff außerhalb des Spielfeldes
//    oder überlappt mit einem anderen Schiff, so werfen Sie eine IllegalShipPlacementException.

    public static Board of(int width, int height, Collection<Ship> ships) {
        if (width < 2 || height < 2) {
            throw new IllegalArgumentException("mantikli mi sence");
        }
        for (Ship s : ships) {
            if (s == null) {
                throw new NullPointerException("gemi not found bro");
            }
            Set<Coordinate> sCoord = s.getCoordinates();
            for (Coordinate c : sCoord) {
                if (c.getX() < 0 || c.getY() < 0 || c.getX() >= width || c.getY() >= height) {
                    throw new IllegalShipPlacementException("Illegal ship position");
                }
            }

        }
        Set<ShipClass> shipType = new HashSet<>();   //ayni shipclasstan 2 tane varsa
        for (Ship schiff : ships) {
            ShipClass tip = schiff.getShipClass();
            boolean vorhanden = shipType.add(tip);
            if (vorhanden == false) {
                throw new IllegalShipPlacementException("Ship already exists");
            }
        }

        Set<Coordinate> allCoordinates = new HashSet<>();
        for (Ship blackPearl : ships) {
            Set<Coordinate> coord = blackPearl.getCoordinates();
            for(Coordinate c : coord){
                if(allCoordinates.add(c)==false){
                    throw new IllegalShipPlacementException("Overlaps with existing ship");
                }
            }





        }

        return new BoardImpl(width, height, new HashSet<>(ships));

    }

//    Erzeugt ein Spielfeld der Größe 10 x 10 mit zufällig platzierten Schiffen.
//    Dazu wird exakt ein Schiff jeder Schiffsklasse zufällig auf dem Spielfeld positioniert.
//    Diese Methode soll keine Fehlermeldungen werfen.

    public static Board random() {

        //   public static Ship of(ShipClass shipType, Coordinate reference, Alignment alignment)

        int width = 10;
        int height = 10;
        Random random = new Random();
        BoardBuilder builder = new BoardBuilderImpl(width, height);

        for (ShipClass type : ShipClass.values()) {
            boolean keepPlacing = true;

            while (keepPlacing) {
                int x = random.nextInt(width);
                int y = random.nextInt(height);
                Coordinate coord = new Coordinate(x, y);
                Alignment[] yonler = Alignment.values();
                int yon = random.nextInt(yonler.length);
                Alignment thisDirection = yonler[yon];
                Ship gemicik = Ship.of(type, coord, thisDirection);
                if (builder.canAddShip(gemicik)) {
                    builder.addShip(gemicik);
                    keepPlacing = false;
                }


            }


        }
        return builder.build();


    }
}
