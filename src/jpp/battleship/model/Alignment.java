package jpp.battleship.model;

public enum Alignment {
//    Diese Aufzählung modelliert die beiden Möglichkeiten ein Schiff zu setzen in einem typsicheren Datentyp.
//    Ein Schiff soll später in einer Reihe (HORIZONTAL) oder in einer Spalte (VERTICAL) platziert werden können.
//    Füge Sie folgende Felder zu der Enumeration hinzu:
//
//    HORIZONTAL
//            VERTICAL
//    public Alignment orthogonal() Gibt, abhängig von der aktuellen Instanz, die jeweils andere Orientierung zurück.
//
//
    HORIZONTAL, VERTICAL;

    public Alignment orthogonal(){   //zit yönde yerlestiriyor
       if(this==HORIZONTAL){
           return Alignment.VERTICAL;
       }
       else{
           return Alignment.HORIZONTAL;
       }
    }



}
