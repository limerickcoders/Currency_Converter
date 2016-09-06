import java.util.Scanner;

/**
 * Created by Niko Osa on 9/4/16.
 */
public class Test {

    private static Scanner in = new Scanner(System.in);

    public static void main(String[] args) {

        Worker pracownik = new Worker(new Kantor());
        pracownik.work();

        in.close();

    }
}
