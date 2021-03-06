import java.util.ArrayList;

public class SaveGame {
    private int id;
    private String word;
    private ArrayList<String> chosenLetters;
    private int wrongAnswers;
    private static int totalGames;

    public SaveGame(String word, ArrayList<String> chosenLetters, int wrongAnswers) {
        this.id = totalGames;
        this.word = word;
        this.chosenLetters = chosenLetters;
        this.wrongAnswers = wrongAnswers;
        totalGames++;
    //-------------------Get methods for class
    }
    public int getId(){
        return id;
    }
    public String getWord(){
        return word;
    }
    public ArrayList<String> getChosenLetters(){
        return chosenLetters;
    }
    public int getWrongAnswers(){
        return wrongAnswers;
    }
    //----------------------To string method

    @Override
    public String toString() {
        return "SavedGame "+ id +
                "[chosenLetters=" + chosenLetters +
                ", wrongAnswers=" + wrongAnswers +
                ']';
        //The word is not in this toString method beacuse the player is not meant to know what the word is.
    }
}
