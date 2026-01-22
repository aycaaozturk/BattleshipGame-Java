package jpp.battleship.logic.strategy;

import jpp.battleship.logic.BoardState;
import jpp.battleship.model.Alignment;
import jpp.battleship.model.Coordinate;
import jpp.battleship.model.ShipClass;
import jpp.battleship.model.ShipImpl;

import java.util.*;

public class ProbabilityAndHuntStrategy implements TargetStrategy {
//    Gehen Sie vor wie in RandomAndHuntStrategy und bestimmen Sie, falls Targets leer ist, die nächste Position wie folgt:
//
//1    Iterieren Sie über die noch verbleibenden Schiffsklassen

//2    Prüfen Sie für jede Position auf dem Spielfeld in beiden Orientierungen, ob die aktuelle Schiffsklasse
// an dieser Referenzposition so auf dem Spielfeld platziert sein könnte.
// Explizit darf ein so platziertes Schiff nicht mit vorherigen Schüssen überlappen.

//3    Wäre die Platzierung gültig, so inkrementieren Sie den "Wert" (initial auf 0 gesetzt) von sämtlichen Koordinaten,
// die das Schiff abdeckt.

//4    Geben Sie eine Koordinate mit maximalen "Wert" zurück.
//5    Beispielsweise ergeben sich auf einem 2x2 Feld, bei dem die Position (1,0) schon beschossen wurde und noch ein Destroyer
// gesucht wird folgende Werte:
//
//    Y / X                    	0	                 1
//      0	                    1	                 ~
//      1	                    2	                 1
//    Somit wäre hier die Position (0,1) mit dem "Wert" 2 das beste nächste Ziel. Implementieren Sie die Methoden für diese Strategie:
//
//Setzen Sie das oben beschrieben Verfahren zur Bestimmung des optimalen nächsten Ziels um. Verwenden Sie dabei die HuntAndTarget Strategie, falls sich gerade ein beschädigtes Schiff auf dem Spielfeld befindet, ansonsten geben Sie eine Position mit maximalen "Wert" nach dem oben beschriebenen Verfahren zurück.

    @Override
    public Coordinate next(BoardState state) {
        Set<Coordinate> available = state.availableTargets();
        List<Coordinate> availableTargets = new ArrayList<>(available);
        List<Coordinate> damagedList = state.getDamaged();
        List<Coordinate> Target = new ArrayList<>();

        for (Coordinate damagedCoord : damagedList) {
            int x = damagedCoord.getX();
            int y = damagedCoord.getY();
            Coordinate down = new Coordinate(x, y - 1);
            Coordinate up = new Coordinate(x, y + 1);
            Coordinate left = new Coordinate(x - 1, y);
            Coordinate right = new Coordinate(x + 1, y);
            if (availableTargets.contains(down)) {
                Target.add(down);
            }
            if (availableTargets.contains(up)) {
                Target.add(up);
            }
            if (availableTargets.contains(left)) {
                Target.add(left);
            }
            if (availableTargets.contains(right)) {
                Target.add(right);
            }
        }
        if (Target.isEmpty()) {
            Map<Coordinate, Integer> ProbOfCoordinates = new HashMap<>();
            Set<Coordinate> availableTargetsSet = state.availableTargets();
            Set<ShipClass> remaininShipTypes = state.remainingShipClasses();
            int width = state.getWidth();
            int height = state.getHeight();
            for (ShipClass s : remaininShipTypes) {
                for (int w = 0; w < width; w++) {
                    for (int h = 0; h < height; h++) {
                        Coordinate current = new Coordinate(w, h);

                        for (Alignment direction : Alignment.values()) {  //bir gemiyi deniyoruz
                            ShipImpl ship = new ShipImpl(s, current, direction);
                            Set<Coordinate> coordinatesOfShip = ship.getCoordinates();
                            boolean valid = true;
                            for (Coordinate c : coordinatesOfShip) {
                                if (availableTargetsSet.contains(c) == false) {
                                    valid = false;
                                    break; //o geminin koordinatlari olmadi, gec
                                }
                            }
                            if (valid == true) {
                                for (Coordinate coord : coordinatesOfShip) {
                                    ProbOfCoordinates.put(coord, ProbOfCoordinates.getOrDefault(coord, 0) + 1);
                                }
                            }
                        }

                    }
                }
            }
            int max =0;
            for (int prob: ProbOfCoordinates.values()){
                if (prob>max){
                    max = prob;
                }
            }
            for (Coordinate c: ProbOfCoordinates.keySet()) {
                if (ProbOfCoordinates.get(c) == max) {
                    return c;
                }
            }

        }
        if (damagedList.size() == 1) {

            Coordinate damagedCoord = damagedList.get(0);
            int x = damagedCoord.getX();
            int y = damagedCoord.getY();
            Coordinate down = new Coordinate(x, y - 1);
            Coordinate up = new Coordinate(x, y + 1);
            Coordinate left = new Coordinate(x - 1, y);
            Coordinate right = new Coordinate(x + 1, y);
            if (Target.contains(down)) {
                return down;
            } else if (Target.contains(up)) {
                return up;
            } else if (Target.contains(left)) {
                return left;
            } else if (Target.contains(right)) {
                return right;
            }
        }
        else if (damagedList.size() > 1) {
            Coordinate last = damagedList.get(damagedList.size() - 1);
            Coordinate beforeLast = damagedList.get(damagedList.size() - 2);
            Optional<Alignment> yerlesim = last.computeAlignment(beforeLast);
            if (yerlesim.isPresent()) {                                                                    //first  second
                for (Coordinate t : Target) {
                    Optional<Alignment> yerlesimTarget = last.computeAlignment(t);
                    if (yerlesimTarget.isPresent() && yerlesimTarget.get() == yerlesim.get()) {
                        return t;
                    }
                }


            }
        }

        return Target.get(0);


    }

    @Override
    public String name() {
        return "ProbabilityAndHunt";
    }
}