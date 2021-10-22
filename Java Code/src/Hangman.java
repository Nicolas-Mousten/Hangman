import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.io.File;

public class Hangman {
    public static ArrayList<String> characters = new ArrayList<>();
    public static int wrongAnswers = 0;
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

        while(wrongAnswers!= 6){
            charArray = addGuess(charArray, wordInPieces, word);
            charArray = wordsInCharacters(charArray, word);
            for (int i = 0; i < charArray.size(); i++) {
                System.out.print(charArray.get(i));
            }
            System.out.println("\nYou have guessed wrong: "+wrongAnswers+" times.");
            if(wordInPieces.equals(characters)){ //Check the 2 arrays if they are equal then you have guessed the word and win.
                System.out.println("you win");
                break;
            }
        }
        if(wrongAnswers == 6){
            System.out.println("You lost");
        }


    }
    public static ArrayList<String> addGuess(ArrayList<String> charArray,ArrayList<String> wordInPieces, String word){
        Scanner scanner = sc("input");
        System.out.println("What is your guess?: ");
        String guess = scanner.nextLine();
        guess = guess.toLowerCase();
        charArray.add(guess);

        if(wordInPieces.contains(guess)){
            System.out.println("This exist in the word");
        }else{
            wrongAnswers++;
        }

        return charArray;
    }
    public static ArrayList<String> wordsInCharacters(ArrayList<String> guessedLetter, String word){
        //This method is only for the visual of the line the words get written on.
        characters = new ArrayList<>();
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
*/

