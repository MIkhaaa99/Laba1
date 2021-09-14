package com.example;

import java.io.*;
import java.util.*;
                     /////////////Chapter X////////////////
public class Start {

    //public static final String ALPH = "abcdefghijklmnopqrstuvwxyz";
    public static final String ALPH = "абвгдеёжзийклмнопрстуфхцчшщъыьэюя";

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
            if(List.of(".", ",", "!", "?", "—", "«", "»"," ").contains(String.valueOf(message.charAt(i)))) {
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
        while(str.length()<26) {
            str = str + qword;
        }
        str = str.substring(0,26);
        System.out.println("Alphabet cipher:  " + str);
        String result = "";
        for(int i=0; i<message.length(); i++) {
            int position1 = ALPH.indexOf(message.charAt(i));
            char symbolAlph = str.charAt(position1);
            int position2 = ALPH.indexOf(symbolAlph);
            result = result + ALPH.charAt((position1 + position2 + 1)%26);
        }
        return result;
    }

    public static void main(String[] args) throws IOException {
        FileReader fileReader = new FileReader("C:\\Users\\ms199\\Desktop\\JavaProject\\StartEncryption\\resources\\Chapter31Encode.txt");
        BufferedReader bufferedReader = new BufferedReader(fileReader);

        FileWriter fileWriter = new FileWriter("C:\\Users\\ms199\\Desktop\\JavaProject\\StartEncryption\\resources\\Chapter31Decode.txt");
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

        String str = "";
        while((str = bufferedReader.readLine()) != null) {

            bufferedWriter.write(encryptToCaesarCode(str, "вак", 1));
        }
        bufferedWriter.close();

/*        System.out.println("\n\t\tZadanie1\nCurrent alphabet: " + ALPH);
        System.out.println("Result: " + encryptToCaesarCode("az", "cam", 1));*/
/*        System.out.println("\n\t\tZadanie2\nCurrent alphabet: " + ALPH);
        System.out.println("Result: " + zadanie2("yzy", "ab"));*/
    }
}
