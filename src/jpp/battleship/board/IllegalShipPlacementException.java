package jpp.battleship.board;

public class IllegalShipPlacementException extends RuntimeException {

//    Diese Klasse erbt von RuntimeException und wird verwendet als Fehlermeldung für falsch-platzierte Schiffe.
//
//    public IllegalShipPlacementException()
//    Erstellt eine IllegalShipPlacementException. Delegieren Sie an den Konstruktor der Oberklasse RuntimeException.
//    public IllegalShipPlacementException(String message)
//    Erstellt eine IllegalShipPlacementException mit der Nachricht message.
//    Delegieren Sie an den Konstruktor der Oberklasse RuntimeException.
//
//
    public IllegalShipPlacementException() {
        super("illegal");
      }

    public IllegalShipPlacementException(String message) {
       super(message);
    }
}
