import java.util.Scanner;

public class Anagram {

    public static void main(String args[]) {
        Scanner sc = new Scanner(System.in);
        AnagramImpl impl = new AnagramImpl();
        impl.run(sc);
    }
}
