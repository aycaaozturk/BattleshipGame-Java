package jpp.battleship.board;

import jpp.battleship.model.Coordinate;
import jpp.battleship.model.Ship;
import jpp.battleship.model.ShipClass;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class BoardBuilderImpl implements BoardBuilder{

//    Dieses Interface definiert Methoden um auf einem zunächst leerem Spielfeld mit festgelegter Breite und Höhe,
//    Schiffe zu platzieren und anschließend als Board Objekt zurückzugeben. Dazu müssen folgende Methoden umgesetzt werden:

    int width;
    int height;
    Set<Ship> ships = new HashSet<>();
    Set<Coordinate> coveredCoord = new HashSet<>();

    public BoardBuilderImpl(int width, int height){
        this.width=width;
        this.height=height;
    }


    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    //   Prüft, ob das Schiff ship auf dem Spielfeld platziert werden. Dazu müssen sämtliche Koordinaten des Schiffes
    //   innerhalb des Spielfeldes liegen und das Schiff darf sich nicht mit einem bereits platziertem Schiff überlappen.
    //   Zusätzlich darf jede Schiffsklasse nur einmal platziert werden. Werfen Sie eine NullPointerException falls ship null ist.

    @Override
    public boolean canAddShip(Ship ship) {
        if(ship==null){
            throw new NullPointerException("ship null");
        }

        Set<Coordinate> coordOfShip = ship.getCoordinates();

        for( Coordinate c : coordOfShip){
            int cX =c.getX();
            int cY =c.getY();
            if(cX <0 || cX>= width || cY<0 || cY>=height){
                return false;
            }
        }
        for(Ship mevcut : ships){
            if (mevcut.getShipClass() == ship.getShipClass()) {
                return false;
            }
        }

        for(Coordinate c : coordOfShip){
            if(coveredCoord.contains(c)){
                return false;
            }
        }

        return true;



    }

//    Fügt ein Schiff zu dem Spielfeld hinzu. Werfen Sie eine NullPointerException falls ship null ist.
//    Wurde ein Schiff dieser Schiffsklasse bereits hinzugefügt, werfen Sie eine IllegalShipPlacementException
//    mit der Nachricht "Ship already exists". Liegt mindestens eine der Schiffspositionen außerhalb des Spielfeldes,
//    werfen Sie eine IllegalShipPlacementException mit der Nachricht "Illegal ship position".
//    Sollte ship mit einem bereits hinzugefügtem Schiff überlappen, werfen Sie eine Illegal IllegalShipPlacementException
//    mit der Nachricht "Overlaps with existing ship". Geben Sie, dem Builder-Pattern entsprechend die aktuelle BoardBuilder-Instanz zurück.

    @Override
    public BoardBuilder addShip(Ship ship) {
        if(ship==null){
            throw new NullPointerException("ship null");
        }
        for(Ship gemi : ships){
            if(gemi.getShipClass()==ship.getShipClass()){
                throw new IllegalShipPlacementException("Ship already exists");
            }
        }
        Set<Coordinate> coordOfShip = ship.getCoordinates();

        for( Coordinate c : coordOfShip){
            int cX =c.getX();
            int cY =c.getY();
            if(cX <0 || cX>= width || cY<0 || cY>=height){
                throw new IllegalShipPlacementException("Illegal ship position");
            }
        }
        for (Coordinate c : coordOfShip) {
            if (coveredCoord.contains(c)) {
                throw new IllegalShipPlacementException("Overlaps with existing ship");
            }
        }
        ships.add(ship);
        coveredCoord.addAll(coordOfShip);
        return this;



    }

//    Gibt die Menge der erfolgreich hinzugefügten Schiffe zurück. Das zurückgegebene Set soll nicht modifizierbar sein.

    @Override
    public Set<Ship> getShips() {
        return Collections.unmodifiableSet(ships);
    }

//    Erstellt ein Spielfeld mit der Höhe und Breite der aktuellen BoardBuilder und den bereits hinzugefügten Schiffen.
//    Kommen Sie zu dieser Methode zurück, sobald Sie Ihre Board Implementierung abgeschlossen haben.

    @Override
    public Board build() {
        return new BoardImpl(width, height,new HashSet<>(ships));  //doldur
    }
}
