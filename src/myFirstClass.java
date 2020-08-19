import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;

public class myFirstClass {
    public static void main(String args[]) throws FileNotFoundException {

        BufferedReader stream = new BufferedReader(new FileReader("input.txt"));
        FillInPuzzle fp = new FillInPuzzle();

        System.out.println("\n########################");
        System.out.println("### Crossword Puzzle ###");
        System.out.println("########################");

        fp.loadPuzzle(stream);
        fp.solve();
        System.out.println();
        fp.print(new PrintWriter(System.out));
        System.out.println("\nNumber of attempts: " + fp.choices());

    }
}