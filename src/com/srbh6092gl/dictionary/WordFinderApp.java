package com.srbh6092gl.dictionary;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class WordFinderApp {
    static Predicate<String> startsAM, endN, palindrome, startAEndZ;

    public static void main(String[] args) {

        startsAM =  word-> Pattern.matches("[a-mA-M].*",word);
        endN=  word-> Pattern.matches(".*[nN]$",word);
        palindrome=  word-> word.equalsIgnoreCase(new StringBuilder(word).reverse().toString());
        startAEndZ=  word-> Pattern.matches("^[aA].*[zZ]$",word);

        try {
            List<String> words = Files.lines(Paths.get("dictionary.txt")).collect(Collectors.toList());

            System.out.println("1: Find words starting with letters a to m");
            System.out.println("2: Find words starting with the letter n at the end of the dictionary");
            System.out.println("3: Group words by their first three letters");
            System.out.println("4: Find palindromes in the dictionary");
            System.out.println("5: Count vowels used in words");
            System.out.println("6: Find words starting with the letter a and ending with the letter z");
            System.out.println("7: Find the longest word in the dictionary");
            System.out.println("8: Exit");

            Scanner S = new Scanner(System.in);
            boolean choseExit = false;
            while(!choseExit){
                System.out.println("Enter 1, 2, 3, 4, 5, 6, or 7");
                short choice = S.nextShort();
                switch (choice){
                    case 1: findAM(words); break;
                    case 2: findN(words); break;
                    case 3: group3(words); break;
                    case 4: findPalindrome(words); break;
                    case 5: countVowels(words); break;
                    case 6: findAZ(words); break;
                    case 7: findLongest(words); break;
                    case 8: choseExit=true; break;
                    default:
                        System.out.println("Invalid choice");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void findAM(List<String> words) {
        List<String> am = words.stream().filter(startsAM).collect(Collectors.toList());
        am.forEach(System.out::println);
    }

    private static void findN(List<String> words) {
        List<String> n = words.stream().filter(endN).collect(Collectors.toList());
        n.forEach(System.out::println);
    }

    private static void group3(List<String> words) {
        Map<String, List<String>> map =words.stream().collect(Collectors.groupingBy(word->word.substring(0,Math.min(word.length(),3))));
        System.out.println(map);
    }

    private static void findPalindrome(List<String> words) {
        List<String> pal = words.stream().filter(palindrome).collect(Collectors.toList());
        pal.forEach(System.out::println);
    }

    private static void countVowels(List<String> words) {
        words.forEach(word -> System.out.println("Vowel count in "+ word+ ": "+ word.replaceAll("[^AEIOUaeiou]","").length()));
    }

    private static void findAZ(List<String> words) {
        List<String> az = words.stream().filter(startAEndZ).collect(Collectors.toList());
        az.forEach(System.out::println);
    }

    private static void findLongest(List<String> words) {
        Optional<String> longest = words.stream().max(Comparator.comparingInt(String::length));
        longest.ifPresent(System.out::println);
    }
}
