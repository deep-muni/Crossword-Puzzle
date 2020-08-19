/* Class to store all the word coming in as input stream */

public class words {

    // Define private variables to store the word and its status

    private String word;
    private boolean used;

    // Initialize the variables by using a parameterized constructor

    public words(String word){
        this.word = word;
        this.used = false;
    }

    // Getter function to get the status of the word

    public boolean getUsed(){
        return this.used;
    }

    // Setter function to set the status of the word

    public void setUsed(boolean used){
        this.used = used;
    }

    // Getter function to get the word

    public String getWord(){
        return this.word;
    }

    // Getter function to get the length of the word

    public int getLen(){
        return this.word.length();
    }

}