import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.io.File;

public class Hangman {
    public static ArrayList<String> characters = new ArrayList<>();
    public static int wrongAnswers = 0;
    public static ArrayList<SaveGame> savedGames = new ArrayList<>();
    public static File saveData;
    public static String firstLine;

    public static Scanner input(){
        Scanner scanner = new Scanner(System.in);
        return scanner;
    }
    public static Scanner csvFile() {
        saveData = new File("Resources/SaveData.csv");
        Scanner scanner = null;
        try{
            scanner = new Scanner(saveData);
        }catch(FileNotFoundException e){
            System.out.println("File not found");
        }
        return scanner;
    }
    public static Scanner txtFile() {
        File wordData = new File("Resources/WordFile.txt");
        Scanner scanner = null;
        try {
            scanner = new Scanner(wordData);
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        }
        return scanner;
    }

    public static int randomGen(int max, int min){
        Random random = new Random();
        int randomNumber = random.nextInt(max-min)+min;
        return randomNumber;
    }
    //----------------------------Tools Above-------------------------------------
    public static void main(String[] args) {

        ArrayList<String> charArray = new ArrayList<>();
        String word = "";
        System.out.println("Do you want to continue a saved game? press 1\nElse press any other button");
        String choice = input().nextLine();
        initializeSaveGameStorage();
        if(choice.equalsIgnoreCase("1")){
            SaveGame chosenSave = saveGame();
            word = chosenSave.getWord();
            charArray = chosenSave.getChosenLetters();
            wrongAnswers = chosenSave.getWrongAnswers();
        }else{
            Scanner wordFile = txtFile();
            String[] words = getWordsFromFile(wordFile);
            int randomNum = randomGen(words.length, 0);
            word = words[randomNum];
        }
        //Wrong answers is global, use if statement to assign it when saved game is loaded
        //Change the saved word here
        gameFlow(word,charArray);
    }
    //----------------------------Methods Under-----------------------------------
    public static SaveGame saveGame(){
        Scanner sc = csvFile();
        int count = 0;
        if(savedGames == null) {
            while (sc.hasNextLine()) {
                ArrayList<String> tempCharArray = new ArrayList<>();
                if (count >= 1) {
                    String line = sc.nextLine();
                    String[] savedGamesArray = line.split(";");
                    int id = Integer.parseInt(savedGamesArray[0]);
                    String word = savedGamesArray[1];
                    for (int i = 0; i < savedGamesArray[2].length(); i++) {
                        if (!String.valueOf(savedGamesArray[2].charAt(i)).equalsIgnoreCase("[")) {
                            if (!String.valueOf(savedGamesArray[2].charAt(i)).equalsIgnoreCase("_")) {
                                if (!String.valueOf(savedGamesArray[2].charAt(i)).equalsIgnoreCase(",")) {
                                    if (!String.valueOf(savedGamesArray[2].charAt(i)).equalsIgnoreCase(" ")) {
                                        if (!String.valueOf(savedGamesArray[2].charAt(i)).equalsIgnoreCase("]")) {
                                            tempCharArray.add(String.valueOf(savedGamesArray[2].charAt(i)));
                                        }
                                    }
                                }
                            }
                        }
                    }
                    int wrongAnswers = Integer.parseInt(savedGamesArray[3]);
                    SaveGame saved = new SaveGame(word, tempCharArray, wrongAnswers);
                    savedGames.add(saved);
                } else {
                    firstLine = sc.nextLine();
                }
                count++;
            }
        for (int i = 0; i < savedGames.size(); i++) {
            System.out.println(savedGames.get(i));
        }
        }
        System.out.println("Choose the games id to continue the game: ");

        int choice = input().nextInt();

        return savedGames.get(choice);


    }
    public static void initializeSaveGameStorage(){
        Scanner sc = csvFile();
        int count = 0;
        while(sc.hasNextLine()){
            if(count >= 1) {
                String line = sc.nextLine();
            }else{
                firstLine = sc.nextLine();
            }
            count++;
        }


    }
    public static void gameFlow(String word,ArrayList<String> charArray){
        System.out.println("Welcome to Hang Man");
        System.out.println("-_-¯-_-¯-_-¯-_-¯-_-");
        //-------------------------------------------
        for (int i = 0; i < word.length(); i++) {
            characters.add("_");
        }
        String showLine = "";
        for (int i = 0; i < characters.size(); i++) {
            showLine += characters.get(i);
        }

        if(charArray != null){
            charArray = wordsInCharacters(charArray,word);
            for (int i = 0; i < charArray.size(); i++) {
                System.out.print(charArray.get(i));
            }
            System.out.println("\nYou have guessed wrong: "+wrongAnswers+" times.");
        }else {
            System.out.println(showLine);
        }
        //-------------------------------------------

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
        System.out.println("What is your guess?: (You can save here by writing: Save)");
        String guess = input().nextLine();
        guess = guess.toLowerCase();

        if(guess.equalsIgnoreCase("save")){
            try {
                //-----------------------------------------get the info from the file to save for new game.
                Scanner sc = csvFile();
                int count = 0;

                if(savedGames == null) {
                    while (sc.hasNextLine()) {
                        ArrayList<String> tempCharArray = new ArrayList<>();
                        if (count >= 1) {
                            String line = sc.nextLine();
                            String[] arrayOfSavedGames = line.split(";");
                            int id = Integer.parseInt(arrayOfSavedGames[0]);
                            String inputWord = arrayOfSavedGames[1];
                            for (int i = 0; i < arrayOfSavedGames[2].length(); i++) {
                                if (!String.valueOf(arrayOfSavedGames[2].charAt(i)).equalsIgnoreCase("[")) {
                                    if (!String.valueOf(arrayOfSavedGames[2].charAt(i)).equalsIgnoreCase("_")) {
                                        if (!String.valueOf(arrayOfSavedGames[2].charAt(i)).equalsIgnoreCase(",")) {
                                            if (!String.valueOf(arrayOfSavedGames[2].charAt(i)).equalsIgnoreCase(" ")) {
                                                if (!String.valueOf(arrayOfSavedGames[2].charAt(i)).equalsIgnoreCase("]")) {
                                                    tempCharArray.add(String.valueOf(arrayOfSavedGames[2].charAt(i)));
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                            int wrongAnswers = Integer.parseInt(arrayOfSavedGames[3]);
                            SaveGame saved = new SaveGame(inputWord, tempCharArray, wrongAnswers);
                            savedGames.add(saved);
                        } else {
                            sc.nextLine();
                        }
                        count++;
                    }
                }
                //----------------------------------------
                FileWriter saveFileWriter = new FileWriter(saveData);

                String inputWord = word;
                ArrayList<String> inputCharArray = charArray;
                int inputWrongAnswers = wrongAnswers;
                SaveGame saveTheGame = new SaveGame(inputWord,inputCharArray,inputWrongAnswers);
                savedGames.add(saveTheGame);

                saveFileWriter.write(firstLine+"\n");//------------------------------------------------------------------------------Header for the csv file input here
                for (int i = 0; i < savedGames.size(); i++) {
                    SaveGame temp = savedGames.get(i);
                    int writeId = temp.getId();
                    String writeWord = temp.getWord();
                    ArrayList<String> writeCharArray = temp.getChosenLetters();
                    int writeWrongAnswers = temp.getWrongAnswers();
                    saveFileWriter.write(writeId+";"+writeWord+";"+writeCharArray+";"+writeWrongAnswers+"\n");
                }
                saveFileWriter.close();


            }catch(IOException k){
                System.out.println("Something is wrong with your storage");
            }

            //needs to leave the path
            System.exit(0);

        }
        else {
            charArray.add(guess);

            if (wordInPieces.contains(guess)) {
                System.out.println("This exist in the word");
            } else {
                wrongAnswers++;
            }
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

