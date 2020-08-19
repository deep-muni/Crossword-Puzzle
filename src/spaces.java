/* Class to store structure of the puzzle coming in as input stream */

public class spaces {

    // Define private variables to store the characteristic of the blank space

    private int rowPos, colPos, len, pos;
    private char direction;
    private String word;

    // Initialize the variables by using a parameterized constructor

    public spaces(int rowPos, int colPos, int len, char direction, int pos){
        this.rowPos = rowPos;
        this.colPos = colPos;
        this.len = len;
        this.direction = direction;
        this.pos = pos;
    }

    // Getter function to get the position of the blank space

    public int getPos(){
        return this.pos;
    }

    // Getter function to get the initial row position of the blank space

    public int getRowPos(){
        return this.rowPos;
    }

    // Getter function to get the initial column position of the blank space

    public int getColPos(){
        return this.colPos;
    }

    // Getter function to get the length of the blank space

    public int getLen(){
        return this.len;
    }

    // Getter function to get the orientation (Horizontal/Vertical) of the blank space

    public char getDirection(){
        return this.direction;
    }

    // Setter function to set the word in the given blank space

    public void setWord(String word){
        this.word = word;
    }

    // Getter function to get the word in the given blank space

    public String getWord(){
        return this.word;
    }

    // Getter function to get the all the co-ordinates of the blank space

    public String[] getPositions(){
        String coordinates[] = new String[this.len];
        if(this.direction == 'h'){
            for(int i = 0; i < this.len; i++){
                coordinates[i] = this.rowPos + " " + (this.colPos +i);  // Since orientation is 'h', column number will be incremented
            }
        }else if(this.direction == 'v'){
            for(int i = 0; i < this.len; i++){
                coordinates[i] = (this.rowPos-i) + " " + this.colPos;   // Since orientation is 'v', row number will be decremented
            }
        }
        return coordinates; // return the co-ordinates of the blank space
    }
}