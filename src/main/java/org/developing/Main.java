package org.developing;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {

    private static AtomicInteger lengthOf3Counter = new AtomicInteger(0);
    private static AtomicInteger lengthOf4Counter = new AtomicInteger(0);
    private static AtomicInteger lengthOf5Counter = new AtomicInteger(0);

    public static void main(String[] args) {

        Random random = new Random();
        String[] texts = new String[100_000];
        for (int i = 0; i < texts.length; i++) {
            texts[i] = generateText("abc", 3 + random.nextInt(3));
        }
        Runnable palindromeSeeker = () -> {
            for (String word : texts) {
                if (isPalindrome(word)) {
                    switch (word.length()) {
                        case 3:
                            lengthOf3Counter.getAndIncrement();
                            break;
                        case 4:
                            lengthOf4Counter.getAndIncrement();
                            break;
                        case 5:
                            lengthOf5Counter.getAndIncrement();
                            break;
                    }
                }
            }
        };
        //поток для одной буквы
        Runnable sameLetterSeeker = () -> {
            for (String word : texts) {
                if (isSameLetter(word)) {
                    switch (word.length()) {
                        case 3:
                            lengthOf3Counter.getAndIncrement();
                            break;
                        case 4:
                            lengthOf4Counter.getAndIncrement();
                            break;
                        case 5:
                            lengthOf5Counter.getAndIncrement();
                            break;
                    }
                }
            }
        };
        //поток для букв по возрастанию
        Runnable ascendingLettersSeeker = () -> {
            for (String word : texts) {
                if (ascendingLetters(word)) {
                    switch (word.length()) {
                        case 3:
                            lengthOf3Counter.getAndIncrement();
                            break;
                        case 4:
                            lengthOf4Counter.getAndIncrement();
                            break;
                        case 5:
                            lengthOf5Counter.getAndIncrement();
                            break;
                    }
                }
            }
        };

        new Thread(palindromeSeeker).start();
        new Thread(sameLetterSeeker).start();
        new Thread(ascendingLettersSeeker).start();

        System.out.println("Красивых слов с длиной 3: " + lengthOf3Counter);
        System.out.println("Красивых слов с длиной 4: " + lengthOf4Counter);
        System.out.println("Красивых слов с длиной 5: " + lengthOf5Counter);

    }

    //поток для палиндрома



    /*
    Сначала учим программу понимать "красивые слова", а пото подсчитываем
     */


    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }

    /**
     * @param str - проверяемая строка
     * @return - true, если строка является палиндромом,
     * в обратном случае - false
     */
    public static boolean isPalindrome(String str) {
        for (int i = 0, j = str.length() - 1; i <= j; i++, j--) {
            if (str.charAt(i) != str.charAt(j)) {
                return false;
            }
        }
        return true;
    }

    /**
     * @param str - проверяемая строка
     * @return - true, если строка состоит из одной и той же буквы,
     * в обратном случае - false
     */
    public static boolean isSameLetter(String str) {
        for (int i = 0; i < str.length() - 1; i++) {
            if (str.charAt(i) != str.charAt(i + 1)) {
                return false;
            }
        }
        return true;
    }

    /**
     * @param str - проверяемая строка
     * @return
     */
    public static boolean ascendingLetters(String str) {
        for (int i = 0; i < str.length() - 1; i++) {
            int c = str.charAt(i);
            if (c != str.charAt(i + 1)) {
                if (c > str.charAt(i + 1)) {
                    return false;
                }
            }
        }
        return true;
    }
}