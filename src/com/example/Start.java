package com.example;

import java.io.*;
import java.util.*;
                     /////////////Chapter X////////////////
public class Start {

    public static final String ALPH = "абвгдеёжзийклмнопрстуфхцчшщъыьэюя";

    public static final Map<String, Double> mapFrequencyOfLetters = new HashMap<>(Map.ofEntries(Map.entry("а", 0.07998), Map.entry("б", 0.01592), Map.entry("в", 0.04533), Map.entry("г", 0.01687), Map.entry("д", 0.02977), Map.entry("е", 0.08483), Map.entry("ё", 0.00013), Map.entry("ж", 0.0094), Map.entry("з", 0.01641), Map.entry("и", 0.07367), Map.entry("й", 0.01208), Map.entry("к", 0.03486), Map.entry("л", 0.04343), Map.entry("м", 0.03203), Map.entry("н", 0.067), Map.entry("о", 0.10983), Map.entry("п", 0.02804), Map.entry("р", 0.04746), Map.entry("с", 0.05473), Map.entry("т", 0.06318), Map.entry("у", 0.02615), Map.entry("ф", 0.00267), Map.entry("х", 0.00966), Map.entry("ц", 0.00486), Map.entry("ч", 0.0145), Map.entry("ш", 0.00718), Map.entry("щ", 0.00361), Map.entry("ъ", 0.00037), Map.entry("ы", 0.01898), Map.entry("ь", 0.01735), Map.entry("э", 0.00331), Map.entry("ю", 0.00639), Map.entry("я", 0.02001)));

    public static Map<String, Integer> mapCountOfSymbol = new HashMap<>(Map.ofEntries(Map.entry("а", 0), Map.entry("б", 0), Map.entry("в", 0), Map.entry("г", 0), Map.entry("д", 0), Map.entry("е", 0), Map.entry("ё", 0), Map.entry("ж", 0), Map.entry("з", 0), Map.entry("и", 0), Map.entry("й", 0), Map.entry("к", 0), Map.entry("л", 0), Map.entry("м", 0), Map.entry("н", 0), Map.entry("о", 0), Map.entry("п", 0), Map.entry("р", 0), Map.entry("с", 0), Map.entry("т", 0), Map.entry("у", 0), Map.entry("ф", 0), Map.entry("х", 0), Map.entry("ц", 0), Map.entry("ч", 0), Map.entry("ш", 0), Map.entry("щ", 0), Map.entry("ъ", 0), Map.entry("ы", 0), Map.entry("ь", 0), Map.entry("э", 0), Map.entry("ю", 0), Map.entry("я", 0)));

    public static  Map<String, String> mapReplacementLetter = new HashMap<>(Map.ofEntries(Map.entry("я", ""), Map.entry("в", ""), Map.entry("а", ""), Map.entry("к", ""), Map.entry("б", ""), Map.entry("г", ""), Map.entry("д", ""), Map.entry("е", ""), Map.entry("ё", ""), Map.entry("ж", ""), Map.entry("з", ""), Map.entry("и", ""), Map.entry("й", ""), Map.entry("л", ""), Map.entry("м", ""), Map.entry("н", ""), Map.entry("о", ""), Map.entry("п", ""), Map.entry("р", ""), Map.entry("с", ""), Map.entry("т", ""), Map.entry("у", ""), Map.entry("ф", ""), Map.entry("х", ""), Map.entry("ц", ""), Map.entry("ч", ""), Map.entry("ш", ""), Map.entry("щ", ""), Map.entry("ъ", ""), Map.entry("ы", ""), Map.entry("ь", ""), Map.entry("э", ""), Map.entry("ю", "")));

    public static Integer countOfAllSymbols = 0;

    public static String encryptToCaesarCode(String message, String qword, Integer key) {

        //Проверка на то, что все символы ключа уникальны
        Collection<Character> qwordCollection = new HashSet<>();
        for(int i=0; i<qword.length(); i++) {
            if(qwordCollection.contains(qword.charAt(i))) {
               throw  new RuntimeException("Error");
            }
            qwordCollection.add(qword.charAt(i));
        }

        //Добавляем ключевое слово в начало
        StringBuilder modifyAlph = new StringBuilder(ALPH);
        List<String> qwordList = Arrays.asList(qword.split(""));
        for (String symbol : qwordList) {
            if(modifyAlph.indexOf(symbol)!=-1) {
                modifyAlph.deleteCharAt(modifyAlph.indexOf(symbol));
            }
        }
        modifyAlph.insert(0, qword);

        //Формируем результат
        String result = "";
        for(int i=0; i<message.length(); i++) {
            if(ALPH.indexOf(message.charAt(i))==-1) {
                result = result + message.charAt(i);
            }
            else {
                result = result + modifyAlph.charAt((33+ALPH.indexOf(String.valueOf(message.charAt(i)).toLowerCase())-key)%33);
            }
        }

        return new String(result);
    }

    public static String zadanie2(String message, String qword) {
        String str = "";
        while(str.length()<message.length()) {
            str = str + qword;
        }
        str = str.substring(0,message.length());
        System.out.println("Alphabet cipher:  " + str);
        String result = "";
        for(int i=0; i<message.length(); i++) {
            int position1 = ALPH.indexOf(message.charAt(i));
            int position2 = ALPH.indexOf(str.charAt(i));
            result = result + ALPH.charAt((position1 + position2 + 1)%ALPH.length());
        }
        return result;
    }

    public static String decodeByFrequencyAnalysis(String message) {
        for(int i=0; i<message.length(); i++) {
            if(ALPH.indexOf(message.charAt(i))!=-1) {
                String symbol = String.valueOf(message.charAt(i));
                mapCountOfSymbol.put(symbol, mapCountOfSymbol.get(symbol)+1);
                countOfAllSymbols++;
            }
        }

        for(Map.Entry<String, Integer> entryOfMapCountOfSymbol : mapCountOfSymbol.entrySet()) {
            double minDifference = 100.0;
            String key = "";
            for(Map.Entry<String, Double> entryOfMapFrequencyOfLetters : mapFrequencyOfLetters.entrySet()) {
                double check = entryOfMapCountOfSymbol.getValue()/(countOfAllSymbols+0.0);
                double difference = Math.abs(entryOfMapFrequencyOfLetters.getValue()-(entryOfMapCountOfSymbol.getValue()/(countOfAllSymbols+0.0)));
                if(difference<minDifference && !mapReplacementLetter.containsValue(entryOfMapFrequencyOfLetters.getKey())) {
                    minDifference = difference;
                    key = entryOfMapFrequencyOfLetters.getKey();
                }
            }
            mapReplacementLetter.put(entryOfMapCountOfSymbol.getKey(), key);
        }

        String result = "";
        for(int i=0; i<message.length(); i++) {
            if(ALPH.indexOf(message.charAt(i))==-1) {
                result = result + message.charAt(i);
            }
            else {
                result = result + mapReplacementLetter.get(String.valueOf(message.charAt(i)));
            }
        }
        return result;
    }

    public static void main(String[] args) throws IOException {
        FileInputStream fileInputStream1 = new FileInputStream("resources/Chapter10.txt");
        BufferedInputStream bufferedInputStream1 = new BufferedInputStream(fileInputStream1);

        byte[] bytesArrayFromChapter10 = bufferedInputStream1.readAllBytes();
        String stringFromChapter10 = new String(bytesArrayFromChapter10, "UTF-8");
        String encode = encryptToCaesarCode(stringFromChapter10, "вак", 1);

        FileOutputStream fileOutputStream1 = new FileOutputStream("resources/Chapter10Encode.txt");
        BufferedOutputStream bufferedOutputStream1 = new BufferedOutputStream(fileOutputStream1);
        bufferedOutputStream1.write(encode.getBytes());

        fileInputStream1.close();
        fileOutputStream1.close();

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        FileInputStream fileInputStream2 = new FileInputStream("resources/Chapter10Encode.txt");
        BufferedInputStream bufferedInputStream2 = new BufferedInputStream(fileInputStream2);

        byte[] bytesArrayFromChapter10Encode = bufferedInputStream2.readAllBytes();
        String stringFromChapter10Encode = new String(bytesArrayFromChapter10Encode, "UTF-8");
        String decode = decodeByFrequencyAnalysis(stringFromChapter10);

        FileOutputStream fileOutputStream2 = new FileOutputStream("resources/Chapter10Decode.txt");
        BufferedOutputStream bufferedOutputStream2 = new BufferedOutputStream(fileOutputStream2);
        bufferedOutputStream2.write(decode.getBytes());

        fileInputStream2.close();
        fileOutputStream2.close();

    }
}
