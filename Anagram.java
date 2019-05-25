import java.util.*;
import java.lang.*;
import java.io.*;

public class Anagram {

    static TreeMap<String, String> dictionary = new TreeMap<>();

    static final int[] points = {
            1, 1, 2, 1, 1,  // A,B,C,D,E
            2, 1, 2, 1, 3,  // F,G,H,I,J
            3, 2, 2, 1, 1,  // K,L,M,N,O
            2, 3, 1, 1, 1,  // P,Q,R,S,T
            1, 2, 2, 3, 2, 3  // U,V,W,X,Y,Z
    };

    public static void main(String args[]) {
        Scanner sc = new Scanner(System.in);

        setDictionary();

        for (int round = 0; round < 10; round++) {

            // get usable characters from standard input
            char[] charArray = sc.next().toCharArray();
            Arrays.sort(charArray);
            String input = String.valueOf(charArray);

            String cand = "_";
            int maxScore = 0;

            boolean found = false;
            for (int i = 0; i < (int) Math.pow(2., input.length()); i++) {
                String key = "";
                int score = 0;
                for (int j = 0; j < input.length(); j++) {
                    if ((1 & i >> j) == 1) {
                        char c = charArray[j];
                        key += c;
                        score += points[(int)c - (int)'a'];
                    }
                }

                String word;
                if (score > maxScore && (word = dictionary.get(key)) != null) {
                    if (!found) {
                        found = true;
                    }
                    maxScore = score;
                    cand = word;
                }
            }

            if (!found) {
                System.out.println("Not found");
            } else {
                System.out.println("Best solution is: " + cand);
            }
        }
    }

    static int calcScore(String s) {
        int score = 0;
        for (int i = 0; i < s.length(); i++) {
            score += points[(int)s.charAt(i) - (int)'a'];
        }
        return score;
    }

    static void setDictionary() {
        try {
            File file = new File("dictionary.txt");
            BufferedReader br = new BufferedReader(new FileReader(file));

            String s;
            while ((s = br.readLine()) != null) {
                s = s.toLowerCase();

                // change "qu" to "q"
                int qindex;
                while((qindex = s.indexOf("qu")) >= 0) {
                    if (qindex < s.length() - 2) {
                        s = s.substring(0, qindex + 1) + s.substring(qindex + 2);
                    } else {
                        s = s.substring(0, qindex + 1);
                    }
                }

                char[] original = s.toCharArray();
                char[] sorted = Arrays.copyOf(original, original.length);
                Arrays.sort(sorted);

                dictionary.put(String.valueOf(sorted), s);
            }

            br.close();

        } catch(FileNotFoundException e){
            System.out.println(e);
        } catch(IOException e){
            System.out.println(e);
        }
    }

    /*
    static int binarySearch(String target) {
        int right = dictionary_sorted.size() - 1;
        int left = 0;
        int mid = (left + right) / 2;
        while(right >= left) {
            mid = (left + right) / 2;
            String value = dictionary_sorted.get(mid);
            int k = target.compareTo(value);
            if (k == 0) {
                return mid;
            } else if (k < 0) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        return -1;
    }
    */
}