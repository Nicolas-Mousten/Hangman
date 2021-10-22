import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;
import java.util.Scanner;
import java.io.File;

public class Hangman {
    public static Scanner sc(String type){
        if(type == "input"){
            return new Scanner(System.in);

        }else if(type == "file"){
            File saveData = new File("Resources/SaveData.csv");
            Scanner sc = null;
            try {
                sc = new Scanner(saveData);
            }
            catch(FileNotFoundException e){
                System.out.println("File not found");
            }
            return sc;
        }else if(type == "file2"){
            File wordFile = new File("Resources/WordFile.txt");
            Scanner sc = null;
            try {
                sc = new Scanner(wordFile);
            }
            catch(FileNotFoundException e){
                System.out.println("File not found");
            }
            return sc;
        }
        else{
            return null;
        }
    }
    public static int randomGen(int max, int min){
        Random random = new Random();
        int randomNumber = random.nextInt(max-min)+min;
        return randomNumber;
    }
    //----------------------------Tools Above-------------------------------------
    public static void main(String[] args) {
        Scanner wordFile = sc("file2");

        String[] words = getWordsFromFile(wordFile);
        int randomNum = randomGen(words.length, 0);
        randomNum = 2;
        gameFlow(words[randomNum]);
    }
    //----------------------------Methods Under-----------------------------------
    public static void gameFlow(String word){
        System.out.println("Welcome to Hang Man");
        System.out.println("-_-¯-_-¯-_-¯-_-¯-_-");
        ArrayList<String> charArray = new ArrayList<>();
        ArrayList<String> wordInPieces = new ArrayList<>();
        for (int i = 0; i < word.length(); i++) {
            wordInPieces.add(String.valueOf(word.charAt(i)));
        }


        charArray.add("a");
        charArray.add("s");

        charArray = wordsInCharacters(charArray, word);
        for (int i = 0; i < charArray.size(); i++) {
            System.out.print(charArray.get(i));
        }

    }
    public static ArrayList<String> addGuess(ArrayList<String> charArray, String word){
        Scanner scanner = sc("input");
        System.out.println("What is your guess?: ");
        String guess = scanner.nextLine();
        guess = guess.toLowerCase();
        charArray.add(guess);
        return charArray;
    }
    public static ArrayList<String> wordsInCharacters(ArrayList<String> guessedLetter, String word){
        //This method is only for the visual of the line the words get written on.
        ArrayList<String> characters = new ArrayList<>();
        System.out.println(word);
        ArrayList<Integer> charIndex = new ArrayList<>();
        for (int i = 0; i < word.length(); i++) {
            characters.add("_");
            }
        for (int i = 0; i < word.length(); i++) {
            for (int j = 0; j < guessedLetter.size(); j++) {
                if(String.valueOf(word.charAt(i)).equalsIgnoreCase(guessedLetter.get(j))){
                    charIndex.add(i);
                    int indexOfLetter = charIndex.get(charIndex.size()-1); //Denne er ikke i variablen siden den hopper over hvis der ingen værdi findes på indexet siden man ikke har gættet på det enu.
                    String letterOfIndex = guessedLetter.get(j);
                    characters.set(indexOfLetter,letterOfIndex);
                }
            }
        }
        charIndex.clear();
        return characters;
    }

    public static String[] getWordsFromFile(Scanner file){
        String[] arrayOfWords = new String[0];
        while(file.hasNextLine()){
            String line = file.nextLine();
            arrayOfWords = line.split(" ");
        }
        return arrayOfWords;
    }


}
/*
 O
/|\
/¯\
int numOfSpecificLetter = count(guessedLetter.get(i), word);

                for (int j = 0; j < word.length(); j++) {
                    charIndex.add(word.indexOf(guessedLetter.get(i)));
                }
                for (int j = 0; j < numOfSpecificLetter; j++) {
                    System.out.println(j);
                    characters.set(charIndex.get(j),guessedLetter.get(i));
                    System.out.println(charIndex.get(j) + guessedLetter.get(i));
                }
*/

