package jpp.battleship.model;

import java.util.Optional;

public class Coordinate {
    int x;
    int y;

    public Coordinate(int x, int y) {
     this.x=x;
     this.y=y;
    }

    public int getX() {
       return x;
    }

    public int getY() {

     return y;
    }


 //   Gibt das Ergebnis der komponentenweisen Additionen von der aktuellen Instanz
 //   mit der Koordinate other in einer neuen Coordinate-Instanz zurück.
 //   Werfen Sie eine NullPointerException falls other null ist.
    public Coordinate add(Coordinate other) {
        if(other==null){
            throw new NullPointerException("other coordinate is null");
        }

       Coordinate newCoor = new Coordinate(getX()+ other.getX(), getY()+other.getY());
       return newCoor;

    }

   // Berechnet die Orientierung der aktuellen Koordinate zu der Koordinate other.
    // Stehen die Koordinaten vertikal oder horizontal zueinander,
    // so soll ein Optional der entsprechenden Orientierung zurückgegeben werden.
    // Andernfalls oder wenn die beiden Koordinaten identisch soll ein leeres Optional zurückgegeben werden.
    // Werfen Sie eine NullPointerException falls other null ist.

    public Optional<Alignment> computeAlignment(Coordinate other) { //simdiki koordinatimiz (this) ile other koordinatin
                                                                    // birbirine göre nasil konumlandigini veriyor
        if(other==null){
            throw new NullPointerException("other is null");
        }
        if(other.equals(this)){
            return Optional.empty();
        }
        if(other.getY()==this.getY()){
            return Optional.of(Alignment.HORIZONTAL);   //yatay
        }
        else if(other.getX()==this.getX()){
            return Optional.of(Alignment.VERTICAL);  //dikey
        }
        else{
            return Optional.empty();
        }


    }

    @Override
    public boolean equals(Object o) {
        if(o instanceof Coordinate ==false){
            return false;
        }
        Coordinate other = (Coordinate) o;
        if(other.getX()==getX()  && other.getY()==getY()){
            return true;
        }
        return false;

    }

    @Override
    public int hashCode() {
       return 31*x+y;   //genel bir hash yöntemiymis

    }

 //   (<x>,<y>)
    @Override
    public String toString() {
        String xx = String.valueOf(getX());
        String yy = String.valueOf(getY());

        return "("+xx+","+yy+")";
    }


  //  Erstellt eine neue Koordinate mit den Koordinaten x und y.
    public static Coordinate of(int x, int y) {
        return new Coordinate(x,y);
    }


//    Erstellt eine Koordinate aus der Battleship-Notation von Positionen.
//    Der erste Buchstabe bestimmt hierbei die y-Koordinate und die darauffolgende Zahl die x-Koordinate.
    //                                     y x                x y
//    Beispielsweise soll für die Eingabe "B3" die Koordinate (2,1) zurückgegeben werden.
//    Dabei sollen nur y Koordinaten bis Z (=25) unterstützt werden.
//    Ist das parsen zu einer Koordinate nicht möglich soll stets ein leeres Optional zurückgegeben werden.
//    Werfen Sie außerdem eine NullPointerException falls coordinate null ist.

    public static Optional<Coordinate> fromBattleshipString(String coordinate) {
        if(coordinate==null){
            throw new NullPointerException("coordinate is null");

        }
        if(coordinate.length()<2){
            return Optional.empty();
        }  //ilk harf y

        String[] ALFABE = {
                "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M",
                "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
        char[] alphabet = {
                'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
                'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'
        };  //size =26
        char yKoordinate = coordinate.charAt(0);

        String xPart = coordinate.substring(1);
        int xVal =0;
        try{
            xVal= Integer.parseInt(xPart) -1;
        }
        catch (IllegalArgumentException e){
            return Optional.empty();
        }


        if(xVal<0){
            return Optional.empty();
        }





      //  int xx = Integer.parseInt(xStringYETER) -1; //KOORDINATA BUNU KOY
        int yy =0;

        boolean eq =false;

        for(int i=0; i<alphabet.length; i++){   //length 26
            if(yKoordinate==alphabet[i]){
                yy=i;
                eq=true;
            }
        }
        if(eq==false){
            return Optional.empty();
        }
        Coordinate returnCoord = new Coordinate(xVal, yy);
        return Optional.of(returnCoord);


    }
}
