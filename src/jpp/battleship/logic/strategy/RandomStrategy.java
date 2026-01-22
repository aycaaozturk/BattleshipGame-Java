package jpp.battleship.logic.strategy;

import jpp.battleship.logic.BoardState;
import jpp.battleship.model.Coordinate;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class RandomStrategy implements TargetStrategy{
 //   Diese Strategie wählt die nächste Position, die beschossen werden soll, zufällig aus.
    //   Dazu werden die Methoden des Interfaces wie folgt umgesetzt:


  //  Gibt eine zufällige Koordinate auf dem Spielfeld zurück, die noch beschossen werden kann.

    @Override
    public Coordinate next(BoardState state) {
       Set<Coordinate> availableTargets = state.availableTargets();
       List<Coordinate> targetList = new ArrayList<>(availableTargets);
       Random sec = new Random();
       int index = sec.nextInt(targetList.size());
       return targetList.get(index);

    }

 //   Gibt "Random" zurück.

    @Override
    public String name() {
        return "Random";
    }
}
