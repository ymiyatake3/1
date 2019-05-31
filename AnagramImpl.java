import java.util.Arrays;
import java.util.Map;
import java.util.TreeMap;
import java.util.HashMap;
import java.util.Scanner;

import java.lang.String;
import java.lang.System;
import java.lang.Math;

import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.FileNotFoundException;

public class AnagramImpl {

    Scrap scrap = new Scrap();

    // map from original words to sorted words
    Map<String, String> dictionary = new TreeMap<>();

    // map from alphabets to points
    final Map<Character, Integer> charToPoints = new HashMap<>();


    public void run(Scanner sc) {
        setDictionary();
        setPointsArray();

        for (int round = 0; round < 10; round++) {

            String input = readAndSortInput(sc);
            //String input = scrap.read();

            // If scraping, you have to change Qu to Q
            //input = changeQuToQ(input.toLowerCase());


            String cand = "_";
            int maxScore = 0;

            boolean found = false;

            // the number of all possible combinations of input characters (include duplication and void)
            int maxIteration = (int) Math.pow(2., input.length());

            // think each combination of the characters as binary expression of i
            for (int i = 0; i < maxIteration; i++) {
                String key = "";
                int score = 0;

                // check each bit of i's binary expression
                for (int j = 0; j < input.length(); j++) {

                    // check if j-th bit from right is high
                    if ((1 & i >> j) == 1) {

                        // j-th bit's high means j-th character exists
                        char c = input.charAt(j);

                        // add the j-th character to key
                        key += c;

                        score += charToPoints.get(c);
                    }
                }

                // search in the dictionary
                String word;
                if (score > maxScore && (word = dictionary.get(key.toString())) != null) {
                    if (!found) {
                        found = true;
                    }
                    maxScore = score;
                    cand = word;
                }
            }

            // final cand is the best answer
            String ans = cand;
            ans = ans.toUpperCase();

            if (ans.indexOf('Q') >= 0) {
                ans = changeQToQu(ans);
            }

            if (!found) {
                System.out.println("No possible word");
            } else {
                System.out.println("Best solution is: " + ans);
                System.out.println("Score: " + (int)Math.pow((maxScore + 1), 2));
            }
        }
    }
    
    private String changeQuToQ(String s) {
        int qindex;
        while ((qindex = s.indexOf("qu")) >= 0) {
            if (qindex < s.length() - 2) {
                s = s.substring(0, qindex + 1) + s.substring(qindex + 2);
            } else {
                s = s.substring(0, qindex + 1);
            }
        }
        return s;
    }
    
    private String changeQToQu(String s) {
        String ret = "";
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == 'Q') {
                ret += "Qu";
            } else {
                ret += c;
            }
        }
        return ret;
    }


    private void setDictionary() {
        try {
            File file = new File("dictionary.txt");
            BufferedReader br = new BufferedReader(new FileReader(file));

            String s;
            while ((s = br.readLine()) != null) {
                s = s.toLowerCase();

                // change "qu" to "q"
                s = changeQuToQ(s);

                char[] original = s.toCharArray();
                char[] sorted = Arrays.copyOf(original, original.length);
                Arrays.sort(sorted);

                dictionary.put(String.valueOf(sorted), s);
            }

            br.close();

        } catch (IOException e) {
            System.out.println(e);
        }
    }


    private void setPointsArray() {
        for (int i = 0; i < 26; i++) {
            char[] twoPoints = {'c', 'f', 'h', 'l', 'm', 'p', 'v', 'w', 'Y'};
            char[] threePoints = {'j', 'k', 'q', 'x', 'z'};

            char alphabet = (char)((int)'a' + i);
            int point = 0;
            if (Arrays.binarySearch(threePoints, alphabet) >= 0) {
                point = 3;
            } else if (Arrays.binarySearch(twoPoints, alphabet) >= 0) {
                point = 2;
            } else {
                point = 1;
            }
            charToPoints.put(alphabet, point);
        }
    }

    private String readAndSortInput(Scanner sc) {
        System.out.print("Please put characters: ");

        // get input characters
        char[] input = sc.next().toLowerCase().toCharArray();

        // if the input was wrong
        // number of the 4 * 4 characters shown on the game display
        int correctInputNum = 16;
        if (input.length != correctInputNum) {
            System.out.println("Your input has " + input.length + " characters. Please input again.");
            readAndSortInput(sc);
        }

        // sort characters ex: rxpccem -> ccemprx
        Arrays.sort(input);
        return String.valueOf(input);
    }
}
