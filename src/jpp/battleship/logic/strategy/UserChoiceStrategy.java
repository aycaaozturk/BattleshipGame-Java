package jpp.battleship.logic.strategy;

import jpp.battleship.logic.BoardState;
import jpp.battleship.model.Coordinate;

import java.io.*;
import java.util.Locale;
import java.util.Optional;

public class UserChoiceStrategy implements TargetStrategy {
    //    Schließlich möchten wir noch eine Strategie zur Verfügung stellen, mit der der User das nächste Ziel bestimmen kann. Für den Dialog mit dem User werden InputStream und OutputStream über die Factory Methode public static TargetStrategy UserChoiceStrategy(InputStream inputStream, OutputStream outputStream) bereitgestellt. Setzen Sie die Methoden der Schnittstelle TargetStrategy wie folgt um:
//
    BufferedReader reader;
    PrintWriter writer;

    public UserChoiceStrategy(InputStream inputStream, OutputStream outputStream) {
        this.reader = new BufferedReader(new InputStreamReader(inputStream));
        this.writer = new PrintWriter(new OutputStreamWriter(outputStream), true);
    }

//1    Setzen Sie folgendes Verfahren um:
//2    Geben Sie "Enter a coordinate: " auf dem outputStream aus.
//3    Der User gibt eine Koordinate im Battleship Format ein, also zum Beispiel "B3"
// 4   Lesen Sie die Eingabe vom inputStream
//5    Kann die Eingabe nicht in eine Koordinate übersetzt werden, oder kann die Position nicht beschossen werden, gehen Sie zu 1.
//6    Geben Sie die Koordinate zurück.

    @Override
    public Coordinate next(BoardState state) {
        boolean sor = true;
        while (sor) {
            writer.print("Enter a coordinate: ");
            writer.flush();
            try {
                String line = reader.readLine();
                if (line == null) {
                    continue;
                }
                String coord = line.trim().toUpperCase();
                Optional<Coordinate> isItCoord = Coordinate.fromBattleshipString(coord);
                if(isItCoord.isPresent()){
                    Coordinate userCoordinate = isItCoord.get();
                    if(state.availableTargets().contains(userCoordinate)){
                        return userCoordinate;
                    }
                }



            } catch (Exception e) {
                writer.println("Invalid input. Try again.");
            }

        }
        return new Coordinate(0,0);
    }

    @Override
    public String name() {
        return "UserChoice";
    }
}
