/* This class is to solve the N-Puzzle Problem
 * by using a Uninformed Search Method. The method
 * used in this class to search is BFS.
 */


//import statements
import java.util.*;
import java.io.*;

public class BFS extends Node {
	
	//set up instance variables
	private Node gameBoard;
	int totalFrontierNodes;
	int totalExpansionNodes;
	int maxNodes;

	//constructor method
	public BFS(Node gameState){
		super(gameState.getState());
		this.gameBoard = gameState;
	}
	
	//method to carry out the BFS search
	public Node BFS(Node gameState) throws CloneNotSupportedException{
		//stack of gameBoards or "frontier"
		Queue<Node> gameStates = new PriorityQueue<Node>();
		//set up list of "explored" states
		HashSet<Node> explored = new HashSet<Node>();  //change data struct
		//int variables for performance metrics
		totalFrontierNodes = 0;
		totalExpansionNodes = 0;
		maxNodes = 0;
		
		//use generic graph traversal algorithm - picture as n X n grid 
		//add initial state to frontier
		gameStates.add(gameState);
		totalFrontierNodes++;
		
		Node stateToCheck = new Node(gameState.getState());
		
		//loop until all states in frontier explored w/o finding solution
		while(!gameStates.isEmpty()){
			//check for 100k limit
			if(totalExpansionNodes > 100000){
				gameStates.peek().setFailedSearch(true);
				System.out.println("no solution found (100k limit reached)");
				break;
			}	
			//check for 3rd performance metric
			if(gameStates.size() > maxNodes){
				maxNodes = gameStates.size();
			}
			
			//retrieve a state in frontier
			stateToCheck = gameStates.remove();
	
			//add state to explored set
			explored.add(stateToCheck);
			totalExpansionNodes++;
				
			//check if state is goal state
			if(equals(stateToCheck.getState(),gameState.getSolution())){
				//solution found
				gameStates.add(stateToCheck);
				break;
			}
				
			//add possible states to frontier
			ArrayList<Node> possibleStates = findPossibleStates(stateToCheck);
				
			//set the possibleStates parent node to the current game state
			for(int k = 0; k < possibleStates.size(); k++){
				possibleStates.get(k).setParent(stateToCheck);
			}

			for(int z = 0; z < possibleStates.size(); z++){
				if(!gameStates.contains(possibleStates.get(z)) && !explored.contains(possibleStates.get(z))){
					gameStates.add(possibleStates.get(z));
					totalFrontierNodes++;	
				}
			}
		}
		// Return
		return stateToCheck;
	}
	
	//getter for totalFrontierNodes
	public int getTotalFrontierNodes(){
		return this.totalFrontierNodes;
	}
	
	//getter for totalExpansionNodes
	public int getTotalExpansionNodes(){
		return this.totalExpansionNodes;
	}
	
	//getter for maxNodes
	public int getMaxNodes(){
		return this.maxNodes;
	}
}
