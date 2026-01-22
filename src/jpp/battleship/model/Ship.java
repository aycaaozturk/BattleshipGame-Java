package jpp.battleship.model;

import java.util.Set;

public interface Ship{

    public Set<Coordinate> getCoordinates();

    public ShipClass getShipClass();

    public Alignment getAlignment();

    public Set<Coordinate> getHits();

    public boolean isSunk();

    public boolean shoot(Coordinate coordinate);

    public int hashCode();

    public boolean equals(Object other);


//    Gibt ein Schiff der Schiffsklasse shipType zurück, das an der Koordinate reference platziert wurde
//    mit der Ausrichtung alignment. Die Koordinate reference legt fest an welcher Position die "Mitte" des Schiffes
//    platziert wird, verschieben Sie die Objektkoordinaten der Schiffsklassen entsprechend,
//    um die durch das Schiff belegten Positionen auf dem Spielfeld zu erhalten.
//    Werfen Sie eine NullPointerException falls reference null ist.
//
    public static Ship of(ShipClass shipType, Coordinate reference, Alignment alignment) {
        if(reference==null  || alignment==null || shipType==null){
            throw new NullPointerException("ref null");
        }

        return new ShipImpl(shipType, reference,alignment);

    }

//    Gibt einen Flugzeugträger platziert an der Koordinate reference in der Orientierung alignment zurück.
//    Werfen Sie eine NullPointerException falls reference null ist.
//
    public static Ship Carrier(Coordinate reference, Alignment alignment ){
        if(reference==null){
            throw new NullPointerException(" reference is null");
        }

        return new ShipImpl(ShipClass.CARRIER, reference, alignment);
    }

    public static Ship Battleship(Coordinate reference, Alignment alignment) {
        if(reference==null){
            throw new NullPointerException(" reference is null");
        }

        return new ShipImpl(ShipClass.BATTLESHIP, reference, alignment);
    }

    public static Ship Cruiser(Coordinate reference, Alignment alignment) {
        if(reference==null){
            throw new NullPointerException(" reference is null");
        }

        return new ShipImpl(ShipClass.CRUISER, reference, alignment);
    }

    public static Ship Submarine(Coordinate reference, Alignment alignment) {
        if(reference==null){
            throw new NullPointerException(" reference is null");
        }

        return new ShipImpl(ShipClass.SUBMARINE, reference, alignment);
    }

    public static Ship Destroyer(Coordinate reference, Alignment alignment) {
        if(reference==null){
            throw new NullPointerException(" reference is null");
        }

        return new ShipImpl(ShipClass.DESTROYER, reference, alignment);
    }
}
