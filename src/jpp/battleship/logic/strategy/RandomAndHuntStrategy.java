package jpp.battleship.logic.strategy;

import jpp.battleship.logic.BoardState;
import jpp.battleship.model.Alignment;
import jpp.battleship.model.Coordinate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class RandomAndHuntStrategy implements TargetStrategy {
//1    Bestimmen Sie zunächst mithilfe von state sämtliche Positionen Damaged, an denen ein Schiff getroffen
// aber nicht versenkt wurde. Damaged ist eine Liste und speichert somit auch die Reihenfolge der Schüsse,
// die ein noch nicht versenktes Schiff beschädigt haben.

//2    Berechnen Sie zu diesen Koordinaten, alle umliegenden Positionen Targets (oben, unten, links und rechts)
// die zudem noch als mögliches nächstes Ziel infrage kommen. Explizit müssen diese Positionen im Spielfeld liegen und
// dürfen nicht bereits beschossen worden sein.

//3    Ist diese Menge Targets leer, so verwenden Sie die RandomStrategy um das nächste Ziel zu bestimmen.

//4    Besteht die Liste Damaged nur aus einer einzigen Position, so beschießen Sie eine beliebige, aber zulässige, umliegende Position.

//5    Besteht die Liste Damaged aus mehreren Positionen, so berechnen Sie, ob es eine Orientierung (Alignment) der letzten beiden Treffer gab.

//6    Falls es eine Orientierung gibt, so geben Sie eine Position aus Targets zurück, die ebenfalls genauso orientiert ist.
// Damit nutzen Sie aus, dass Sie mit zwei Treffern (meistens) schon die Orientierung des Schiffes kennen.
// Gibt es keine solche Position, so gehen Sie zu Schritt 7.

//7    Falls keine Orientierung über die beiden letzten Treffer in Damaged festgestellt wurde oder kein gültiges Ziel
// in dieser Orientierung vorliegt, wählen Sie eine beliebige Koordinate aus Targets.
//

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
            RandomStrategy r = new RandomStrategy();
            return r.next(state);
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
        return "RandomAndHunt";
    }
}