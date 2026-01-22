package jpp.battleship.game;

import jpp.battleship.board.Board;
import jpp.battleship.logic.strategy.TargetStrategy;

public record Player(String name, Board board, TargetStrategy strategy) {
//    Setzt die Eigenschaften eines Spielers. Werfen Sie eine NullPointerException, falls einer der Parameter null ist.
//    Werfen Sie eine IllegalArgumentException, falls name nur aus Whitespaces besteht.


//    Überschreiben Sie außerdem die equals und hashcode Methode,
//    sodass zwei Spieler gleich sind, genau dann, wenn sie den gleichen Namen haben.
//
//    Zusätzlich soll die toString Methode den Namen des Spielers zurückgeben.
//

    public Player(String name, Board board, TargetStrategy strategy) {
        if(name==null || board==null || strategy==null){
            throw new NullPointerException("arg null");
        }
        if(name.trim().isEmpty()==true){
            throw new IllegalArgumentException("empty");
        }
        this.name = name;
        this.board = board;
        this.strategy = strategy;

    }

    @Override
    public boolean equals(Object o) {
        if(o instanceof Player==false){
            return false;
        }
        Player other =(Player) o;
        if(name.equals(other.name)){
            return true;
        }
        else{
            return false;
        }
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public String toString() {

        return name;
    }
}
