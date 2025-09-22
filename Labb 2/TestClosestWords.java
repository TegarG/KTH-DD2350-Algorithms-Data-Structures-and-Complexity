import java.util.Arrays;

public class TestClosestWords {
    public static void main(String[] args) {
        ClosestWords cw = new ClosestWords("dummy", Arrays.asList("dummy")); // dummy-instans

        String w1 = "labd";
        String w2 = "blad";

        System.out.println("partDist-tabell för \"" + w1 + "\" och \"" + w2 + "\":");
        for (int i = 0; i <= 4; i++) {
            for (int j = 0; j <= 4; j++) {
                int d = cw.partDist(w1, w2, i, j);
                System.out.printf("%2d ", d);  // snygg utskrift i tabellform
            }
            System.out.println();
        }
    }
}
