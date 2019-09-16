/* This class is to create a node data struct that
 * will store the gameState and other info such
 * as parent states and heuristic values
 */

//import statements
import java.util.*;

public class Node implements Comparable<Node> {
	
	//instance variables
	private String[][] gameState;
	private String[][] gameSolution;
	private int n_value;
	private Node parent = null;
	private int g;
	private int manhattanDistance;
	private int f;
	private boolean failedSearch = false;
	
	public Node(String[][] gameState1){
		this.gameState = gameState1;
	}
	
	//setter for parents
	public void setParent(Node parent){
		this.parent = parent; 
	}
	
	//setter for state
	public void setState(String[][] gameState){
		this.gameState = gameState;
	}
	
	//setter for solution
	public void setSolution(String[][] gameState){
		//get n_value
		int n_value = gameState[0].length;
		//create new solution [][]
		String [][] sol = new String[n_value][n_value];
		int num = 1;
		for(int a = 0; a < n_value; a++){
			for(int b = 0; b < n_value; b++){
				//set value for blank tile
				if(a == 0 && b == 0){
					sol[a][b] = ".";
				}
				else{
					sol[a][b] = Integer.toString(num);
					num++;
				}
			}
		}
		this.gameSolution = sol;
	}
	
	//setter for bool failed search
	public void setFailedSearch(boolean res){
		this.failedSearch = res;
	}
	
	//setter for g_value
	public void setGValue(Node gameState1){
		if(gameState1.getParent() != null){
			this.g = gameState1.getParent().getGValue() + 1; //due to how each successor state is just one move away
		}
		else{
			this.g = 0;
		}
		//System.out.println(this.g);
	}
	
	//setter for Manhattan Distance
	public void setManhattanDistance(Node gameState1){
		//set up variable for n_value of the gameState
		int n_value = gameState1.getState()[0].length;
		//set up int for sum
		int manhattanDistance = 0;
		//go through each square of tile
		for(int i=0; i < n_value; i++){
			for(int j=0; j < n_value; j++){
				//don't do anything for empty square
				if(!gameState1.getState()[i][j].equals(".")){  //TA SAID DONT NEED ERROR POINT
					//get number of square
					int num = Integer.parseInt(gameState1.getState()[i][j]);
					//find the target values of the coordinates of the number using formula 
					int targetX = num / n_value;
					int targetY = num % n_value;
					//find x-distance & y-distance to expected square
					int xDiff = i - targetX;
					int yDiff = i - targetY;
					//add to sum
					manhattanDistance += Math.abs(xDiff) + Math.abs(yDiff);
				}
			}
		}
		//System.out.println(manhattanDistance);
		this.manhattanDistance = manhattanDistance; 
	}
	
	//setter for f_value
	public void setFValue(Node gameState1){
		int f_value = gameState1.getGValue() + gameState1.getManhattanDistance();
		//System.out.println(f_value);
		this.f = f_value;
	}
	
	//getter for failedSearch
	public boolean getFailedSearch(){
		return this.failedSearch;
	}
	
	//getter for state
	public String[][] getState(){
		return this.gameState;
	}
	
	//getter for parent
	public Node getParent(){
		return this.parent;
	}
	
	//getter for solution
	public String[][] getSolution(){
		return this.gameSolution;
	}
	
	//getter for the n_value
	public int getNValue(){
		return this.n_value;
	}
	
	//method to get g-value or the value of path up until this state
	public int getGValue(){
		return this.g;
	}
	
	//method to get the Heuristic value or Manhattan Distance of the state
	public int getManhattanDistance(){
		return this.manhattanDistance;
	}
	
	//method to get the f_value of the state
	public int getFValue(){
		return this.f;
	}
	
	//method to get coordinates of empty square
	public int[] findEmpty(Node gameState){
		int[] res = new int[2];
		//set up variable for current game State
		String[][] state = gameState.getState();
		//set up variable for n_value of grid
		int n_value = state[0].length;
		//int variables used for dimensions of empty square
		int j = 0;
		int k = 0;
		//loop through state to find empty square
		for(j = 0; j < n_value; ++j){
			for(k =0; k < n_value; ++k){
				if(state[j][k].equals(".")){
					//empty square found
					res[0] = j;
					res[1] = k;
				}
			}
		}
		return res;
	}
	
	//This method gets the possible states from a given state
	public ArrayList<Node> findPossibleStates(Node gameState) throws CloneNotSupportedException {
		//list for possible states
		ArrayList<Node> possibleStates = new ArrayList<Node>();
		//set up variable for current game State
		String[][] state = gameState.getState();
		//set up variable for n_value of grid
		int n_value = state[0].length;
		//get empty square coordinates
		int x = findEmpty(gameState)[0];
		int y = findEmpty(gameState)[1];
		
		// Check the four possible states that can occur: moves from either up, right, down, or left.

		//check for upState
		if(x != 0){
			//create new game state for move from up
			String[][] stateUp = copy(state);
			//create new node for that game state
			Node state_Up = new Node(stateUp);
			//value of number in spot above empty square
			String temp = state[x-1][y];
			//switch values
			stateUp[x-1][y] = "."; 
			stateUp[x][y]= temp;
			//reset the game state in the node
			state_Up.setState(stateUp);
			//add to possibleStates
			possibleStates.add(state_Up);
		}
		
		//check for rightState
		if(y != n_value-1){
			//create new game state for move from right   
			String[][] stateRight = copy(state);
			//create new node for that game state
			Node state_Right = new Node(stateRight);
			//value of number in spot right of empty square
			String temp = state[x][y+1];
			//switch values
			stateRight[x][y+1] = ".";
			stateRight[x][y]= temp;
			//reset the game state in the node
			state_Right.setState(stateRight);
			//add to possibleStates
			possibleStates.add(state_Right);
		}
		
		//check for down state
		if(x != n_value - 1){
			//create new game state for move from down
			String[][] stateDown = copy(state);
			//create new node for that game state
			Node state_Down = new Node(stateDown);
			//value of number in spot below empty square
			String temp = state[x+1][y];
			//switch values
			stateDown[x+1][y] = ".";
			stateDown[x][y]= temp;
			//reset the game state in the node
			state_Down.setState(stateDown);
			//add to possibleStates
			possibleStates.add(state_Down);
		}
			
		//check for left state
		if(y != 0){
			//create new game state for move from left
			String[][] stateLeft = copy(state);
			//create new node for that game state
			Node state_Left = new Node(stateLeft);
			//value of number in spot left of empty square
			String temp = state[x][y-1];
			//switch values
			stateLeft[x][y-1] = ".";
			stateLeft[x][y]= temp;
			//reset the game state in the node
			state_Left.setState(stateLeft);
			//add to possibleStates
			possibleStates.add(state_Left);
		}
			
		//all possible states searched. return list
		return possibleStates;
	}
	
	//method to print out the game State
	public void printState(String[][] gameState){
		//get n value
		int n_value = gameState[0].length;
		//loop through each row
		for(int i =0; i < n_value; i++){
			//loop through columns
			for(int j =0; j < n_value; j++){
				//print out element
				System.out.print(gameState[i][j] + " ");
			}
			System.out.println();
		}
	}
	
	//method to create a copy of current gameState
	public String[][] copy(String[][] gameState){
		//get n value
		int n_value = gameState[0].length;
		//set up new String dbl array
		String[][] copy = new String[n_value][n_value];
		//fill copy
		for(int i = 0; i < n_value; i++){
			for(int j = 0; j < n_value; j++){
				copy[i][j] = gameState[i][j];
			}
		}
		return copy;
	}
	
	//method to check if a gameState is equal to another state
	public boolean equals(String[][] gameState1, String[][] gameState2){
		//set up boolean variable for if equal
		boolean isEqual = true;
		//get n value
		int n_value = gameState1[0].length;
		//check each element
		for(int i = 0; i < n_value; i++){
			for(int j = 0; j < n_value; j++){
				//check equals
				if(!gameState1[i][j].equals((gameState2[i][j]))){
					isEqual = false;
					return isEqual;
				}
			}
		}
		return isEqual;
		
	}

	//method to compare two nodes used for A* search
	public int compareTo(Node stateFuture) {
		//get fvalues of two nodes to compare
		Integer f1 = this.f;
		Integer f2 = stateFuture.f;
		//compare to
		int res = f1.compareTo(f2); 
		return res;
	}
	
	//overwrite method for HashSet function
	public int hashCode(){
		int hashCode = 0;
		for(int i = 0; i < this.n_value; i++){
			for(int j =0; j < this.n_value; j++){
				if(!this.gameState[i][j].equals(".")){
					hashCode += Integer.valueOf(this.gameState[i][j]);
				} else{
					hashCode += 0;
				}	
			}
		}
		return hashCode;
	}
	
	
}
