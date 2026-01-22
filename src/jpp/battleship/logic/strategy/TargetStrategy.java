package jpp.battleship.logic.strategy;

import jpp.battleship.logic.BoardState;
import jpp.battleship.model.Coordinate;
import jpp.battleship.model.Ship;
import jpp.battleship.model.ShipClass;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Set;

public interface TargetStrategy {
    //Boardstate metotlari:
//    public int getWidth();
//    public int getHeight();
//    public Set<Ship> getSunkShips();
//    public Set<ShipClass> remainingShipClasses();
//    public List<Coordinate> getShots();
//    public List<Coordinate> getHits();
//    public List<Coordinate> getDamaged();
//    public Set<Coordinate> availableTargets();

   public Coordinate next(BoardState state);
   public String name();

 //   Überprüfen Sie zunächst, ob das gegnerische Feld noch über erlaubte Ziele verfügt. Kann keine Position beschossen werden,
    //   so werfen Sie eine IllegalStateException. Ansonsten wird der Aufruf an die next Methode delegiert,
    //   um das nächste Ziel zu erhalten.

    public default Coordinate get(BoardState state) {
       Set<Coordinate> available = state.availableTargets();
       if(available.isEmpty()==true){
           throw new IllegalStateException("no available");
       }
       else{
        return next(state);
       }

    }


    public static TargetStrategy RandomStrategy(){
        return new RandomStrategy();
    }

    public static TargetStrategy RandomAndHuntStrategy(){
        return new RandomAndHuntStrategy();
    }

    public static TargetStrategy ProbabilityAndHuntStrategy(){
        return new ProbabilityAndHuntStrategy();
    }

    public static TargetStrategy UserChoiceStrategy(InputStream inputStream, OutputStream outputStream){
        return new UserChoiceStrategy(inputStream, outputStream);
    }
}
