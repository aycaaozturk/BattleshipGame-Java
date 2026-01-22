package jpp.battleship.game;

import jpp.battleship.board.Board;
import jpp.battleship.logic.BoardState;
import jpp.battleship.logic.BoardStateImpl;
import jpp.battleship.logic.strategy.TargetStrategy;
import jpp.battleship.model.Coordinate;

import java.util.*;

public class Battleship {
    Player p1;
    Player p2;
    Set<Player> players = new HashSet<>();
    List<Player> playerList= new ArrayList<>();
    List<Player> HamleSirasi= new ArrayList<>();
    int numberOfMoves =0;

  //  Player(String name, Board board, TargetStrategy strategy)

 //   Erstellt eine neue Partie Schiffe Versenken mit den Spielern p1 und p2. Es beginnt Spieler p1 mit dem ersten Spielzug.
 //   Sie können davon ausgehen, dass die Spielfelder beider Spieler valide sind und noch nicht beschossen wurden.
 //   Werfen Sie eine NullPointerException falls einer der beiden Spieler null ist. Werfen Sie außerdem eine IllegalArgumentException,
 //   falls die beiden Spieler gleich sind.

    public Battleship(Player p1, Player p2) {
        if(p1==null || p2==null){
            throw new NullPointerException("players null");
        }
        if(p1.equals(p2)){
            throw new IllegalArgumentException("players same");
        }
        this.p1=p1;
        this.p2=p2;
        players.add(p1);
        players.add(p2);
        playerList.add(p1);
        playerList.add(p2);

    }


    //     Gibt den Spieler p1 zurück, also den Spieler, der den ersten Zug macht.

    public Player getP1() {

       return p1;
    }



    //     Gibt den Spieler p2 zurück, also den Spieler, der den zweiten Zug macht.

    public Player getP2() {

        return p2;
    }



    //    Gibt ein unveränderliches Set mit beiden Spielern zurück.

    public Set<Player> getPlayers() {
       return Collections.unmodifiableSet(players);
    }



    //    Führt den nächsten Spielzug aus, indem der Spieler, der aktuell an der Reihe ist,
    //    eine Position auf dem gegnerischen Spielfeld beschießt. Das Ziel wird mithilfe der Beschussstrategie
    //    des aktiven Spielers und dem Spielfeldzustand des gegnerischen Feldes bestimmt.
    //    Verändern Sie den Zustand des gegnerischen Spielfeldes dementsprechend und geben Sie das gewählte Ziel zurück.
    //    Aktualisieren Sie außerdem den aktiven Spieler, sodass beim nächsten Aufruf von move der andere Spieler an der Reihe ist.

    public Coordinate move(){
       //hamle cift ise-> p1
       // hamle tek ise -> p2

       if(numberOfMoves % 2 ==0){  //p1 oynar
           TargetStrategy strategyP1 = p1.strategy();
           BoardState boardStateP2 = new BoardStateImpl(p2.board());
           Coordinate nextCoord = strategyP1.next(boardStateP2); //vurulacak
            p2.board().shoot(nextCoord);
            numberOfMoves++;
            return nextCoord;
       }
        else{  // tek sayi, p2 oynar
           TargetStrategy strategyP2 = p2.strategy();
           BoardState boardStateP1 = new BoardStateImpl(p1.board());
           Coordinate nextCoord = strategyP2.next(boardStateP1);
           p1.board().shoot(nextCoord);

           numberOfMoves++;
           return nextCoord;
       }

    }



    //     Gibt den Spieler zurück, der aktuell an der Reihe ist.

    public Player getCurrentPlayer() {
        if(numberOfMoves%2==0){
            return p1;
        }
        else{
            return p2;
        }
    }



    //    Gibt den Gewinner der Partie in einem Optional zurück. Gibt es noch keinen Gewinner,
    //    so geben Sie ein leeres Optional zurück.
    public Optional<Player> getWinner(){
        if(p1.board().isAllSunk()==true){
            return Optional.of(p2);
        }
        else if(p2.board().isAllSunk()==true){
            return Optional.of(p1);
        }
        else{
            return Optional.empty();
        }
    }



    //     Spielt eine komplette Partie Schiffe Versenken und gibt den Gewinner zurück.
    //     Führen Sie also so lange Spielzüge durch, bis ein Gewinner feststeht und geben Sie diesen zurück.
    public Player run() {
      boolean keepPlaying = true;
      Player gewinner=p1;
      while(keepPlaying==true){
          move();
          if(getWinner().isPresent()){
               gewinner = getWinner().get();
              keepPlaying=false;
          }
      }
      return gewinner;

    }
}
