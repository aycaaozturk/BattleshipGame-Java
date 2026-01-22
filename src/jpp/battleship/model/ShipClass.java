package jpp.battleship.model;

import java.util.HashSet;
import java.util.Set;

public enum ShipClass {
    CARRIER,     // 5  A
    BATTLESHIP,  // 4  B
    CRUISER,     // 3  C
    SUBMARINE,   // 3  S
    DESTROYER    // 2  D
    ;

    public int length() {
        if(this==CARRIER){
            return 5;
        }
        else if(this==BATTLESHIP){
            return 4;
        }
        else if(this==CRUISER){
            return 3;
        }
        else if(this==SUBMARINE){
            return 3;
        }
        else{
            return 2;
        }

    }

    @Override
    public String toString()
    {
        if(this==CARRIER){
            return "A";
        }
        else if(this==BATTLESHIP){
            return "B";
        }
        else if(this==CRUISER){
            return "C";
        }
        else if(this==SUBMARINE){
            return "S";
        }
        else{
            return "D";
        }
    }

//    Gibt, abhängig von der Schiffsklasse und der gewünschten Orientierung auf dem Spielfeld,
//    die Positionen zurück die das entsprechend platzierte Schiff überdeckt.
//    Die Menge an Koordinaten wird in einem sogenannten Objekt-Koordinatensystem angegeben,
//    wobei der Ursprung hier möglichst in der Mitte des Schiffes liegt. Beachten Sie,
//    dass im Objekt-Koordinatensystem die y-Achse wie üblich nach oben zeigt.
//    Beispielsweise bedeckt ein vertikal bzw. horizontal ausgerichteter Zerstörer (Destroyer) folgende Koordinaten,
//    wobei der Ursprung (0,0) rot gefärbt ist:


    public Set<Coordinate> points(Alignment alignment) {
        Set<Coordinate> pointsOfShip = new HashSet<>();

        if(this==CARRIER && alignment==Alignment.VERTICAL){
            Coordinate c1 = new Coordinate(0, -2);
            Coordinate c2= new Coordinate(0, -1);
            Coordinate c3 = new Coordinate(0, 0);
            Coordinate c4= new Coordinate(0,1);
            Coordinate c5= new Coordinate(0, 2);
            pointsOfShip.add(c1);
            pointsOfShip.add(c2);
            pointsOfShip.add(c3);
            pointsOfShip.add(c4);
            pointsOfShip.add(c5);
            return  pointsOfShip;
        }
        else if(this==CARRIER && alignment==Alignment.HORIZONTAL){
            Coordinate c1 = new Coordinate(-2, 0);
            Coordinate c2= new Coordinate(-1, 0);
            Coordinate c3 = new Coordinate(0, 0);
            Coordinate c4= new Coordinate(1, 0);
            Coordinate c5= new Coordinate(2, 0);
            pointsOfShip.add(c1);
            pointsOfShip.add(c2);
            pointsOfShip.add(c3);
            pointsOfShip.add(c4);
            pointsOfShip.add(c5);
            return pointsOfShip;
        }

      else if(this==BATTLESHIP && alignment==Alignment.VERTICAL){
            Coordinate c2= new Coordinate(0, -1);
            Coordinate c3 = new Coordinate(0, 0);
            Coordinate c4= new Coordinate(0,1);
            Coordinate c5= new Coordinate(0, 2);

            pointsOfShip.add(c2);
            pointsOfShip.add(c3);
            pointsOfShip.add(c4);
            pointsOfShip.add(c5);
            return  pointsOfShip;


        }
      else if( this ==BATTLESHIP && alignment==Alignment.HORIZONTAL){

            Coordinate c2= new Coordinate(-1, 0);
            Coordinate c3 = new Coordinate(0, 0);
            Coordinate c4= new Coordinate(1, 0);
            Coordinate c5= new Coordinate(2, 0);

            pointsOfShip.add(c2);
            pointsOfShip.add(c3);
            pointsOfShip.add(c4);
            pointsOfShip.add(c5);
            return pointsOfShip;


        }
      else if( (this==SUBMARINE || this==CRUISER) && alignment==Alignment.VERTICAL ){
            Coordinate c2= new Coordinate(0, -1);
            Coordinate c3 = new Coordinate(0, 0);
            Coordinate c4= new Coordinate(0,1);


            pointsOfShip.add(c2);
            pointsOfShip.add(c3);
            pointsOfShip.add(c4);

            return  pointsOfShip;
        }

      else if( (this==SUBMARINE || this==CRUISER) && alignment==Alignment.HORIZONTAL ){
            Coordinate c2= new Coordinate(-1, 0);
            Coordinate c3 = new Coordinate(0, 0);
            Coordinate c4= new Coordinate(1, 0);


            pointsOfShip.add(c2);
            pointsOfShip.add(c3);
            pointsOfShip.add(c4);

            return pointsOfShip;

        }
      else if( this==DESTROYER && alignment==Alignment.VERTICAL){
            Coordinate c3 = new Coordinate(0, 0);
            Coordinate c4= new Coordinate(0,1);


            pointsOfShip.add(c3);
            pointsOfShip.add(c4);

            return  pointsOfShip;
        }
      else {
            Coordinate c3 = new Coordinate(0, 0);
            Coordinate c4= new Coordinate(1, 0);


            pointsOfShip.add(c3);
            pointsOfShip.add(c4);

            return pointsOfShip;
        }
    }
}
