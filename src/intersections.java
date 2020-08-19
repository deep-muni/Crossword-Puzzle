/* Class to store all the possible intersections in the puzzle */

public class intersections {

    // Define private variables to store the blank's position and index at which intersection occurs

    private int pos1, ind1, pos2, ind2;

    // Initialize the variables by using a parameterized constructor

    public intersections(int pos1, int ind1, int pos2, int ind2){
        this.pos1 = pos1;
        this.ind1 = ind1;
        this.pos2 = pos2;
        this.ind2 = ind2;
    }

    // Getter function to get the position of 1st blank space

    public int getPos1(){
        return this.pos1;
    }

    // Getter function to get the position of 2nd blank space

    public int getPos2(){
        return this.pos2;
    }

    // Getter function to get the index at which intersection occurs in 1st blank space

    public int getInd1(){
        return this.ind1;
    }

    // Getter function to get the index at which intersection occurs in 2nd blank space

    public int getInd2(){
        return this.ind2;
    }

}