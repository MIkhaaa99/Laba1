package com.example;

import java.io.*;
import java.util.*;
                     /////////////Chapters I-X////////////////
public class Start {

    public static final String ALPH = "абвгдеёжзийклмнопрстуфхцчшщъыьэюя";

    public static final Map<String, Double> mapFrequencyOfLetters = new LinkedHashMap<>();

    public static Map<String, Integer> mapCountOfSymbol = new LinkedHashMap<>(Map.ofEntries(Map.entry("а", 0), Map.entry("б", 0), Map.entry("в", 0), Map.entry("г", 0), Map.entry("д", 0), Map.entry("е", 0), Map.entry("ё", 0), Map.entry("ж", 0), Map.entry("з", 0), Map.entry("и", 0), Map.entry("й", 0), Map.entry("к", 0), Map.entry("л", 0), Map.entry("м", 0), Map.entry("н", 0), Map.entry("о", 0), Map.entry("п", 0), Map.entry("р", 0), Map.entry("с", 0), Map.entry("т", 0), Map.entry("у", 0), Map.entry("ф", 0), Map.entry("х", 0), Map.entry("ц", 0), Map.entry("ч", 0), Map.entry("ш", 0), Map.entry("щ", 0), Map.entry("ъ", 0), Map.entry("ы", 0), Map.entry("ь", 0), Map.entry("э", 0), Map.entry("ю", 0), Map.entry("я", 0)));

    public static  Map<String, String> mapReplacementLetter = new LinkedHashMap<>();

    public static Integer countOfAllSymbols = 0;

    static {
        mapFrequencyOfLetters.put("о", 0.10983);
        mapFrequencyOfLetters.put("е", 0.08483);
        mapFrequencyOfLetters.put("а", 0.07998);
        mapFrequencyOfLetters.put("и", 0.07367);
        mapFrequencyOfLetters.put("н", 0.06700);
        mapFrequencyOfLetters.put("т", 0.06318);
        mapFrequencyOfLetters.put("с", 0.05473);
        mapFrequencyOfLetters.put("р", 0.04746);
        mapFrequencyOfLetters.put("в", 0.04533);
        mapFrequencyOfLetters.put("л", 0.04343);
        mapFrequencyOfLetters.put("к", 0.03486);
        mapFrequencyOfLetters.put("м", 0.03203);
        mapFrequencyOfLetters.put("д", 0.02977);
        mapFrequencyOfLetters.put("п", 0.02804);
        mapFrequencyOfLetters.put("у", 0.02615);
        mapFrequencyOfLetters.put("я", 0.02001);
        mapFrequencyOfLetters.put("ы", 0.01898);
        mapFrequencyOfLetters.put("ь", 0.01735);
        mapFrequencyOfLetters.put("г", 0.01687);
        mapFrequencyOfLetters.put("з", 0.01641);
        mapFrequencyOfLetters.put("б", 0.01592);
        mapFrequencyOfLetters.put("ч", 0.01450);
        mapFrequencyOfLetters.put("й", 0.01208);
        mapFrequencyOfLetters.put("х", 0.00966);
        mapFrequencyOfLetters.put("ж", 0.00940);
        mapFrequencyOfLetters.put("ш", 0.00718);
        mapFrequencyOfLetters.put("ю", 0.00639);
        mapFrequencyOfLetters.put("ц", 0.00486);
        mapFrequencyOfLetters.put("щ", 0.00361);
        mapFrequencyOfLetters.put("э", 0.00331);
        mapFrequencyOfLetters.put("ф", 0.00267);
        mapFrequencyOfLetters.put("ъ", 0.00037);
        mapFrequencyOfLetters.put("ё", 0.00013);
    }

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
            if(ALPH.indexOf(Character.toLowerCase(message.charAt(i)))==-1) {
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

        Map<String, Integer> map = new LinkedHashMap<>();
        mapCountOfSymbol.entrySet().stream()
                            .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                            .forEach(entry -> map.put(entry.getKey(), entry.getValue()));

        mapCountOfSymbol.clear();
        mapCountOfSymbol.putAll(map);

        String[] arrKeysByMapCountOfSymbol = (String[]) map.keySet().toArray(new String[map.size()]);
        String[] arrKeysByMapFrequencyOfLetters = (String[]) mapFrequencyOfLetters.keySet().toArray(new String[map.size()]);

        for(int i=0; i<ALPH.length(); i++) {
            mapReplacementLetter.put(arrKeysByMapCountOfSymbol[i], arrKeysByMapFrequencyOfLetters[i]);
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

        bufferedOutputStream1.close();
        bufferedInputStream1.close();

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        FileInputStream fileInputStream2 = new FileInputStream("resources/Chapter10Encode.txt");
        BufferedInputStream bufferedInputStream2 = new BufferedInputStream(fileInputStream2);

        byte[] bytesArrayFromChapter10Encode = bufferedInputStream2.readAllBytes();
        String stringFromChapter10Encode = new String(bytesArrayFromChapter10Encode, "UTF-8");
        String decode = decodeByFrequencyAnalysis(stringFromChapter10Encode);

        FileOutputStream fileOutputStream2 = new FileOutputStream("resources/Chapter10Decode.txt");
        BufferedOutputStream bufferedOutputStream2 = new BufferedOutputStream(fileOutputStream2);
        bufferedOutputStream2.write(decode.getBytes());

        bufferedOutputStream2.close();
        bufferedInputStream2.close();

        System.out.println(mapFrequencyOfLetters);
        System.out.println(mapCountOfSymbol);
        System.out.println(countOfAllSymbols);
        System.out.println(mapReplacementLetter);
    }
}
