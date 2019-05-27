import java.util.Arrays;
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

public class Anagram {

    public static void main(String args[]) {
        Scanner sc = new Scanner(System.in);
        AnagramImpl impl = new AnagramImpl();
        impl.run(sc);
    }
}
