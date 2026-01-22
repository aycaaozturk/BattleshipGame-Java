package jpp.battleship.model;

import javax.print.attribute.UnmodifiableSetException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class ShipImpl implements Ship {
//    Das Interface Ship gibt vor welche Funktionalitäten Schiffe in unserer Modellierung leisten müssen.
//    Ein Schiff speichert einerseits seine Schiffsklasse, Orientierung und die überdeckten Positionen auf
//    dem Spielfeld. Zusätzlich soll die Logik für den Beschuss und das Versenken eines Schiffes implementiert werden.
//    Dazu müssen folgende Methoden umgesetzt werden:

    ShipClass shipType;   //length, toString, points (coordinate set)
    Coordinate reference;
    Alignment alignment;
    Set<Coordinate> vuruldun = new HashSet<>();   //vurulan koordinatlari

    public ShipImpl(ShipClass shipType, Coordinate reference, Alignment alignment) {
        this.shipType = shipType;
        this.reference = reference;
        this.alignment = alignment;

    }

//    Gibt die vom Schiff überdeckten Postionen auf dem Spielfeld zurück.
//    Das zurückgegebene Set soll nicht modifizierbar sein.

    @Override
    public Set<Coordinate> getCoordinates() {  //coor add ile yap
        Set<Coordinate> pointsOfShip = shipType.points(alignment);
        Set<Coordinate> coordsOnTheField = new HashSet<>();

        for (Coordinate c : pointsOfShip) {
            Coordinate added = reference.add(c);
            coordsOnTheField.add(added);
        }
        return Collections.unmodifiableSet(coordsOnTheField);

    }

    //    Gibt die Schiffsklasse des Schiffes zurück.
    @Override
    public ShipClass getShipClass() {
        return this.shipType;
    }

    //   Gibt die Ausrichtung auf dem Spielfeld zurück.
    @Override
    public Alignment getAlignment() {
        return alignment;
    }

    //   Gibt die Koordinaten des Schiffes zurück, die bereits getroffen wurden.
    //   Das zurückgegebene Set soll nicht modifizierbar sein.

    @Override
    public Set<Coordinate> getHits() {
        return Collections.unmodifiableSet(vuruldun);
    }

//    Gibt an, ob das Schiff versenkt wurde.

    @Override
    public boolean isSunk() {
        Set<Coordinate> shipCoords = getCoordinates();
        if (vuruldun.containsAll(shipCoords)) {
            return true;
        } else {
            return false;
        }

    }

    //   Feuert auf die Position coordinate. Befindet sich dort eine Koordinate des Schiffes,
    //   aktualisiert sich entsprechend der Zustand des Objekts und es wird true zurückgegeben.
    //   Geht der Schuss ins Leere wird, schlicht false zurückgegeben.
    //   Werfen Sie eine NullPointerException falls coordinate null ist.

    @Override
    public boolean shoot(Coordinate coordinate) {    //vurabiliyorsa vuruyor ve true dönüyor
        // o koordinat gemininse true dön
        //bos attiysa false dön
        if (coordinate == null) {
            throw new NullPointerException("coord is null");
        }
        Set<Coordinate> shipCoord = getCoordinates();
        boolean elendin = false;

        for (Coordinate c : shipCoord) {
            if (c.equals(coordinate)) {
                elendin = true;
                vuruldun.add(coordinate);
            }
        }
        return elendin;


    }

    public int hashCode(){
        int result = 17;
        result = 31 * result + shipType.hashCode();
        result = 31 * result + reference.hashCode();
        result = 31 * result + alignment.hashCode();
        return result;
    }


    public boolean equals(Object other) {
        if (other instanceof ShipImpl == false) {
            return false;

        }
      ShipImpl otherShip = (ShipImpl) other;
       if(otherShip.getShipClass()==this.shipType  && otherShip.reference.equals(this.reference) && otherShip.getAlignment()==this.alignment ){
           return true;
       }
       else{
           return false;
       }
    }

    public void setReference(Coordinate reference) {
        this.reference = reference;
    }

    public void rotate(){
        if (alignment == Alignment.HORIZONTAL) {
            alignment = Alignment.VERTICAL;
        } else {
            alignment = Alignment.HORIZONTAL;
        }
    }

    public Coordinate getReference() {
        return reference;
    }

    public void reset(){
        this.vuruldun = new HashSet<>();
    }
}