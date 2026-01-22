package jpp.battleship.board;

import jpp.battleship.model.Coordinate;
import jpp.battleship.model.Ship;

import java.util.*;

public class BoardImpl implements Board{
 //   Dieses Interface definiert die Schnittstellen, die ein Spielfeld bietet.
    //   Ein Spielfeld speichert seine Breite (x) und Höhe (y) als auch die platzierten Schiffsobjekte.
    //   Zusätzlich wird die Logik zum Schießen auf eine Koordinate implementiert und die Reihenfolge der Schüsse gespeichert.
    //   Setzen Sie folgende Methoden in Ihrer Implementierung der Schnittstelle um:

    int width;
    int height;
    Set<Ship> ships;
    List<Coordinate> shots = new ArrayList<>();


    public BoardImpl(int width, int height, Set<Ship> ships){
        this.width=width;
        this.height=height;
        this.ships=ships;
     //   this.shots=shots;
    }


    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    //    Gibt ein Optional des Schiffes zurück, das sich (unter anderem) an der Position coordinate befindet.
    //    Ansonsten soll ein leeres Optional zurückgegeben werden.

    @Override
    public Optional<Ship> getShip(Coordinate coordinate) {
        for(Ship s : ships){
            Set<Coordinate> sCoordinates = s.getCoordinates();
            for(Coordinate c : sCoordinates){
                if(c.equals(coordinate)){
                    return Optional.of(s);
                }
            }
        }

        return Optional.empty();
    }

    //   Gibt alle Schiffe auf dem Spielfeld zurück. Das zurückgegeben Set soll nicht modifizierbar sein.

    @Override
    public Set<Ship> getShips() {
        return Collections.unmodifiableSet(ships);
    }

    //    Gibt die Liste der bisherigen Schüsse zurück. Dabei steht der erste Schuss an erster Position der Liste.
    //    Die Liste darf nicht modifizierbar sein.

    @Override
    public List<Coordinate> getShots() {
        return Collections.unmodifiableList(shots);
    }

    //    Gibt an, ob alle Schiffe bereits versenkt wurden.

    @Override
    public boolean isAllSunk() {
        boolean battik = true;
        for(Ship s : ships){
            if(s.isSunk()==false){
                battik = false;
            }
        }
        return battik;
    }

    //   Überprüft die Position coordinate beschossen werden kann. Dies ist der Fall, wenn sich die Koordinate
    //   innerhalb des Spielfeldes befindet und nicht bereits vorher beschossen wurde.

    @Override
    public boolean canShoot(Coordinate coordinate) {
        if (coordinate.getX() < 0 || coordinate.getY() < 0 || coordinate.getY() >= height || coordinate.getX()>=width ) {
            return false;
        }
        if(shots.contains(coordinate)){
            return false;
        }
        return true;

    }

    //    Beschießt die Position coordinate auf dem Spielfeld. Darf diese Position nicht beschossen werden,
    //    wird eine InvalidTargetException mit der Fehlermeldung "Invalid target: <target>" geworfen.
    //    Speichern Sie den Schuss und falls sich ein Schiff an der beschossenen Position befindet,
    //    aktualisieren Sie dessen Zustand entsprechend.

    @Override
    public void shoot(Coordinate coordinate) {
        if(canShoot(coordinate)==false){
            throw new InvalidTargetException(coordinate);
        }
        shots.add(coordinate);

        for(Ship s: ships){
            Set<Coordinate> shipCoordinates = s.getCoordinates();
            if(shipCoordinates.contains(coordinate)){
                s.shoot(coordinate);
            }
        }


    }
}
