package jpp.battleship.board;

import jpp.battleship.model.Coordinate;

public class InvalidTargetException extends RuntimeException {
//    Diese Klasse erbt von RuntimeException und wird verwendet als Fehlermeldung für unerlaubte Schüsse auf ein Spielfeld.
//
//    public InvalidTargetException()
//    Erstellt eine InvalidTargetException. Delegieren Sie an den Konstruktor der Oberklasse RuntimeException.
//    public InvalidTargetException(Coordinate target)
//    Erstellt eine InvalidTargetException mit der Fehlermeldung "Invalid target: <target>".
//    Delegieren Sie dazu an den Konstruktor der Oberklasse RuntimeException.


    public InvalidTargetException() {
        super("invalid");
    }

    public InvalidTargetException(Coordinate target){
        super("Invalid target" + target.toString());
    }
}
