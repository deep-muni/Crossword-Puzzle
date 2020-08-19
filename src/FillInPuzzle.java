import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/* Class to read the puzzle, solve the puzzle and print the solved puzzle */

public class FillInPuzzle {

    // Define private variables to store values related to puzzle

    private int noc, nor, guess = 0;
    private char grid[][];
    private boolean flag = false, flag_1 = false;

    private ArrayList<spaces> space = new ArrayList<>();    // ArrayList to store the blank spaces [ Class spaces ]
    private ArrayList<words> word = new ArrayList<>();  // ArrayList to store the words [ Class words ]
    private ArrayList<intersections> intersection = new ArrayList<>();  // ArrayList to store the intersections [ Class intersections ]

    /* Function to store the puzzle's input coming in as input stream */
    public boolean loadPuzzle(BufferedReader stream){

        try{

            String temp[] = stream.readLine().split(" ");

            // Reading the first line of stream which has total columns, rows and words

            noc = Integer.parseInt(temp[0]);
            nor = Integer.parseInt(temp[1]);
            int now = Integer.parseInt(temp[2]);

            // Condition to check if any of the above value is negative
            if(noc < 1 || nor < 1 || now < 1){
                return false;
            }

            /* Loop till number of words to read the next lines
               which has the initial row/column position, length
               and orientation
            */

            for(int i = 0; i < now; i++){
                temp = stream.readLine().split(" ");
                int rowPos = Integer.parseInt(temp[1]);
                int colPos = Integer.parseInt(temp[0]);
                int len = Integer.parseInt(temp[2]);
                char direction = temp[3].charAt(0);

                // Condition to check if any of the above value is negative
                if(rowPos < 0 || colPos < 0 || len <= 0){
                    return false;
                }

                // Condition to check if orientation is 'h' or 'v' not other than that
                if(direction != 'v' && direction != 'h'){
                    return false;
                }

                if(direction == 'h'){
                    // Condition to check horizontal words fits in the grid
                    if(!((colPos+len) <= noc)){
                        return false;
                    }
                }else{
                    // Condition to check vertical words fits in the grid
                    int temp_row = rowPos+1;
                    for(int j = 0; j < len; j++){
                        temp_row--;
                        if(temp_row < 0){
                            return false;
                        }
                    }
                }

                // Adding the parameters in object of 'spaces' which is stored in ArrayList 'space'
                space.add(new spaces(rowPos, colPos, len, direction, i));
            }

            // Loop to add the words in the object words which is stored in ArrayList word
            for(int i = 0; i < now; i++){
                word.add(new words(stream.readLine()));
            }

            // Condition to check if words are not equal to word number
            for(int i = 0; i < now; i++){
                if(word.get(i).getWord() == null){
                    return false;
                }
            }

            // Invoking the method the find the intersection
            findIntersection();

            // Created an empty grid (2D Array)
            grid = new char[nor][noc];

            // Invoking the method to create grid with blanks
            createGrid();

            flag = true;

            return true;
        }catch (Exception e){
            return false;
        }
    }

    /* Function to solve the puzzle */
    public boolean solve(){

        try{

            // Condition to check if load puzzle is called before calling solve
            if(!flag){
                return false;
            }

            // Setting the status as true
            boolean status = true;

            do{

                // Stopping condition to break the loop if no solution is found
                if(guess > 50000){
                    return false;
                }

                // Resetting the status of words to false every time the loop starts
                for(words aWordList: word) {
                    aWordList.setUsed(false);
                }

                // Creating a Boolean array of the size of total number of intersections
                Boolean checkAll[] = new Boolean[intersection.size()];
                for(int i = 0; i < checkAll.length; i++){
                    // Setting all the values to false
                    checkAll[i] = false;
                }

                // Loop to set the words in each space
                for(spaces sp : space) {
                    ArrayList<String> temp_words = getWordList(sp);

                    // Shuffling the words randomly to get a correct word list
                    Collections.shuffle(temp_words);

                    // Storing the 1st word from the list in the blanks
                    sp.setWord(temp_words.get(0));

                    /* Setting the status of that word in the main list as true,
                       so that the same word does not appear again */
                    for (words w : word) {
                        if (temp_words.get(0).equals(w.getWord())) {
                            w.setUsed(true);
                            break;
                        }
                    }
                }

                // Loop to check if all intersection have the same letters
                int i = 0;
                for (intersections anIntersection : intersection) {
                    // Condition to check if the intersections have same letter
                    if (space.get(anIntersection.getPos1()).getWord().charAt(anIntersection.getInd1()) ==
                            space.get(anIntersection.getPos2()).getWord().charAt(anIntersection.getInd2())) {

                        // If there is no conflict, then set the status as true in Boolean matrix
                        checkAll[i] = true;
                        i++;
                    }
                }

                // Stopping condition
                if(Arrays.asList(checkAll).contains(false)){
                    // If the Boolean array contains a false value, then this combination is not a solution
                    guess++;
                }else{
                    /* If the Boolean array does not contain a false value, then set status as false
                       to come out of loop, meaning we have found a solution */
                    status = false;
                }
            }while(status);

            // Once a solution is found, we call the fillGrid method to fill the grid
            fillGrid();

            flag_1 = true;
            return true;
        }catch (Exception e){
            return false;
        }
    }

    /* Function to print the puzzle */
    public void print(PrintWriter outstream) {

        // Condition to check if solution if found or print function is invoked before load and solve
        if(!(guess > 50000 || !flag || !flag_1)){
            for(int i = nor-1; i >= 0; i--){
                for(int j = 0; j < noc ; j++){
                    outstream.append(grid[i][j] + "  ");
                }
                outstream.append("\n\n");
            }
            outstream.flush();
        }
    }

    /* Function to return a value of the number of guess the program had to make to solve the puzzle */
    public int choices(){
        // Condition to check if solution if found or choice function is invoked before load and solve
        if(guess > 50000 || !flag || !flag_1){
            return -1;
        }
        return guess;
    }

    /* Function to find the intersection between the blank spaces in the puzzle */
    private void findIntersection(){
        for(spaces sp1 : space){
            for(spaces sp2 : space){

                // Proceeds if both objects points to different blank spaces
                if(!(sp1 == sp2)){

                    // Gets all the co-ordinates stored in an array
                    String temp1[] = sp1.getPositions();
                    String temp2[] = sp2.getPositions();

                    for (int i = 0; i < temp1.length; i++) {
                        String temp_1 = temp1[i];
                        for (int j = 0; j < temp2.length; j++) {
                            String temp_2 = temp2[j];

                            // Condition to check if the co-ordinates are equal in both blank space
                            if (temp_1.equals(temp_2)) {

                                // Adding the parameters in object of 'intersections' which is stored in ArrayList 'intersection'
                                intersection.add(new intersections(sp1.getPos(), i, sp2.getPos(), j));
                            }
                        }
                    }
                }
            }
        }
    }

    /* Function to create an empty 2-D grid to later store the solved puzzle */
    private void createGrid(){
        for(int i = 0; i < nor; i++){
            for(int j = 0; j < noc; j++){
                // Storing the grid with blanks
                grid[i][j] = ' ';
            }
        }
    }

    /* Function to get the word list eligible to insert in the blank space */
    private ArrayList<String> getWordList(spaces sp){
        ArrayList<String> temp_words = new ArrayList<>();
        for (words aWordList : word) {
            // Condition to check if the blank space's length ie equal to the word length
            if (sp.getLen() == aWordList.getLen()) {
                // Condition to check if the word is already used or not
                if (!aWordList.getUsed()) {
                    // If word is not used earlier, adding the word to the list
                    temp_words.add(aWordList.getWord());
                }
            }
        }
        // return the eligible list
        return temp_words;
    }

    /* Function to store the characters in correct placeafter solving the puzzle */
    private void fillGrid(){
        for (spaces sp : space) {
            // Get x and y co-ordinate of filled blanks
            int x = sp.getRowPos();
            int y = sp.getColPos();
            for (int j = 0; j < sp.getLen(); j++) {
                // Take each character and store it in the grid
                grid[x][y] = sp.getWord().charAt(j);
                if (sp.getDirection() == 'h'){
                    y++;    // If orientation is 'h', column number is incremented
                }else if (sp.getDirection() == 'v'){
                    x--;    // If orientation is 'v', row number is decremented
                }
            }
        }
    }

}