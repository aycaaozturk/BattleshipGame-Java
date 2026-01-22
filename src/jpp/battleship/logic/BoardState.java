package jpp.battleship.logic;

import jpp.battleship.board.Board;
import jpp.battleship.model.Coordinate;
import jpp.battleship.model.Ship;
import jpp.battleship.model.ShipClass;

import java.util.List;
import java.util.Set;

public interface BoardState {
//    In diesem Aufgabenteil modellieren wir die Informationen, die ein Spieler über das gegnerische Spielfeld hat
//    und wie er davon abhängig seien nächstes Ziel wählt.
//    Da nicht das komplette Spielfeld einsehbar ist, modellieren wir die Sicht auf das gegnerische Spielfeld durch
//    eine weitere Schnittstelle BoardState.
//    Die Wahl des nächsten Ziels für einen Schuss auf das gegnerische Feld wird
//    durch Implementierung der TargetStrategy Schnittstelle realisiert.
//    Diese setzt das Strategy Entwurfsmuster um und ermöglicht verschiedene Strategien für den nächsten Schuss
//    auf das Feld des Gegners, unter der Berücksichtigung des aktuellen BoardState.




    public int getWidth();

    public int getHeight();

    public Set<Ship> getSunkShips();

    public Set<ShipClass> remainingShipClasses();

    public List<Coordinate> getShots();

    public List<Coordinate> getHits();

    public List<Coordinate> getDamaged();

    public Set<Coordinate> availableTargets();

    public static BoardState create(Board board){



       return new BoardStateImpl(board);
    }
}
