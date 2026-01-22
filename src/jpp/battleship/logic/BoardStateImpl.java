package jpp.battleship.logic;

import jpp.battleship.board.Board;
import jpp.battleship.model.Coordinate;
import jpp.battleship.model.Ship;
import jpp.battleship.model.ShipClass;

import java.util.*;

public class BoardStateImpl implements BoardState{
//    Dieses Interface definiert Methoden zur Abfrage von Informationen über das gegnerische Feld,
//    die während des Spielverlaufs gewonnen werden.
//    Dazu zählen bereits getroffene Positionen, versenkte Schiffe oder noch verfügbare Ziele.
//    Für die Berechnung dieser Informationen wird zwar das analysierte Spielfeld als Board Objekt übergeben,
//    jedoch können über die Schnittstelle BoardState nicht Informationen über noch verdeckte Schiffe preisgegeben werden.
//    Das Interface BoardState befindet sich im Paket jpp.battleship.logic.
//
//    Setzen Sie folgende Methoden in Ihrer Implementierung der Schnittstelle um.
//    Eine Instanz Ihrer Implementierung wird schließlich über die statische Methode create(Board board) erzeugt,
//    verwenden Sie das übergebene Board Objekt für sämtliche Berechnungen.
//    Ist es bei der Erstellung null, so werfen Sie eine NullPointerException.



    //   Gibt die Breite des Spielfeldes zurück.

    Board board;

    public BoardStateImpl(Board board){

        if(board==null){
            throw new NullPointerException("gemi yok");
        }
        this.board=board;
    }


    @Override
    public int getWidth() {
        return board.getWidth();
    }

    @Override
    public int getHeight() {
        return board.getHeight();
    }

//    Gibt ein nicht modifizierbares Set der bereits versenkten Schiffe zurück.

    @Override
    public Set<Ship> getSunkShips() {
        Set<Ship> sunk= new HashSet<>();
        for(Ship s : board.getShips()){
            if(s.isSunk()==true){
                sunk.add(s);
            }
        }
        return Collections.unmodifiableSet(sunk);
    }


//    Gibt ein nicht modifizierbares Set der noch nicht versenkten Schiffsklassen auf dem Spielfeld zurück.

    @Override
    public Set<ShipClass> remainingShipClasses() {
        Set<Ship> Notsunk= new HashSet<>();
        for(Ship s : board.getShips()){
            if(s.isSunk()==false){
                Notsunk.add(s);
            }
        }
        Set<ShipClass> NotSunkShipTypes = new HashSet<>();
        for(Ship ss : Notsunk){
            NotSunkShipTypes.add(ss.getShipClass());
        }
        return Collections.unmodifiableSet(NotSunkShipTypes);

    }

//    Gibt eine nicht modifizierbare Liste der bereits beschossenen Koordinaten zurück.
//    Dabei ist an erster Position der Liste, der erste Schuss auf das Spielfeld.

    @Override
    public List<Coordinate> getShots() {
        return Collections.unmodifiableList(board.getShots());
    }

//    Gibt eine nicht modifizierbare Liste der Schüsse zurück, die ein Schiff getroffen haben.
//    Dabei steht der älteste Treffer als Erstes in der Liste.

    @Override
    public List<Coordinate> getHits() {   //koordinat listesi ama eger bir gemiyi vurduysa
        List<Coordinate> hits = new ArrayList<>();
        List<Ship> shipList = new ArrayList<>(board.getShips());
        List<Coordinate> allCoordinates = new ArrayList<>();
        List<Coordinate> allShots = getShots();

        for(Ship s : shipList){
            List<Coordinate> coordOfShip = new ArrayList<>(s.getCoordinates());
            allCoordinates.addAll(coordOfShip);
        }
        for(Coordinate atis : allShots){
            if(allCoordinates.contains(atis)){
                hits.add(atis);
            }
        }
        return Collections.unmodifiableList(hits);


    }

//    Gibt die Positionen zurück, an denen Schiffe getroffen wurden, jedoch noch nicht versenkt.
//    Dabei steht der älteste Treffer als Erstes in der Liste. Die zurückgegebene Liste soll nicht modifizierbar sein.

    @Override
    public List<Coordinate> getDamaged() {
        List<Coordinate> hits = new ArrayList<>();
        List<Ship> shipList = new ArrayList<>(board.getShips());
        List<Coordinate> allCoordinates = new ArrayList<>();
        List<Coordinate> allShots = getShots();

        for(Ship s : shipList){
            if(s.isSunk()==true) {
                continue;
            }
            List<Coordinate> coordOfShip = new ArrayList<>(s.getCoordinates());
            allCoordinates.addAll(coordOfShip);
        }
        for(Coordinate atis : allShots){
            if(allCoordinates.contains(atis)){
                hits.add(atis);
            }
        }
        return Collections.unmodifiableList(hits);



    }

//    Gibt die Menge an Koordinaten zurück, auf die noch geschossen werden darf. Das sind alle Positionen innerhalb des Spielfeldes,
//    die vorher noch nicht als Ziel gewählt wurden. Falls bereits alle Schiffe auf dem Spielfeld versenkt wurden,
//    geben Sie stattdessen ein leeres Set zurück. Die zurückgegebene Menge darf nicht modifizierbar sein.


    //atis yapilabilecek bos fieldlar
    @Override
    public Set<Coordinate> availableTargets() {
        Set<Coordinate> available = new HashSet<>();
        Set<Coordinate> allCoordinatesOfTheBoard = new HashSet<>();

        if(board.isAllSunk()==true){
            return Collections.unmodifiableSet(available);
        }
        int width=getWidth();
        int height=getHeight();
        for(int x=0; x<width; x++){
            for(int y=0; y<height; y++){
                Coordinate coordinate = new Coordinate(x,y);
                allCoordinatesOfTheBoard.add(coordinate);
            }
        }
        for(Coordinate c: allCoordinatesOfTheBoard){
            if(board.canShoot(c)==true){
                available.add(c);
            }
        }
        return Collections.unmodifiableSet(available);



    }








    //   Die Fehlermeldungen in den Tests geben den Zustand des Spielfeldes auf der Konsole aus.
    //   Dabei haben nachfolgende Character folgende Bedeutung für eine Position auf dem Spielfeld:

//                           Character	Bedeutung
//                                 #	Verdeckt
//                                 ~	Wasser
//                                 +	Schiff angeschossen
//                                 X	Schiff versenkt
//                 ShipClass::toString	Schiff (nicht getroffen)


}
