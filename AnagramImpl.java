import java.util.Arrays;
import java.util.Map;
import java.util.TreeMap;
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

    Map<String, String> dictionary = new TreeMap<>();

    final int[] points = {
            1, 1, 2, 1, 1,  // A,B,C,D,E
            2, 1, 2, 1, 3,  // F,G,H,I,J
            3, 2, 2, 1, 1,  // K,L,M,N,O
            2, 3, 1, 1, 1,  // P,Q,R,S,T
            1, 2, 2, 3, 2, 3  // U,V,W,X,Y,Z
    };

    public void run(Scanner sc) {
        //sc = new Scanner(System.in);

        setDictionary();

        for (int round = 0; round < 10; round++) {
            char[] input_;
            while (true) {
                System.out.print("Please put characters: ");

                // get input characters and sort them
                input_ = sc.next().toLowerCase().toCharArray();

                int l = input_.length;
                if (l == 16) {
                    break;
                } else {
                    System.out.println("The number of input characters is " + l + ". Please put again.");
                }
            }
            Arrays.sort(input_);
            String input = String.valueOf(input_);

            String cand = "_";
            int maxScore = 0;

            boolean found = false;

            // all possible combination of input characters (include duplication)
            for (int i = (int)Math.pow(2., input.length()); i >= 0; i--) {
                String key = "";
                int score = 0;
                for (int j = 0; j < input.length(); j++) {
                    if ((1 & i >> j) == 1) {
                        char c = input_[j];

                        key += c;
                        score += points[(int)c - (int)'a'];
                    }
                }

                // search in the dictionary
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
                System.out.println("No possible word");
            } else {
                System.out.println("Best solution is: " + cand.toUpperCase());
                System.out.println("Score: " + (int)Math.pow((maxScore + 1), 2));
            }
        }
    }


    void setDictionary() {
        try {
            File file = new File("dictionary.txt");
            BufferedReader br = new BufferedReader(new FileReader(file));

            String s;
            while ((s = br.readLine()) != null) {
                s = s.toLowerCase();

                // change "qu" to "q"
                int qindex;
                while ((qindex = s.indexOf("qu")) >= 0) {
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

        } catch (FileNotFoundException e) {
            System.out.println(e);
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}
