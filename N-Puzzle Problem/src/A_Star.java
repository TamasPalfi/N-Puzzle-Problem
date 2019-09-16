/* This class is used to solve the N-Puzzle problem using
 * an A* search method.  Heuristics are used to optimize
 * a path problem following this evaluation function: 
 * 
 * f(n) = g(n) + h(n)
 * 
 * where h(n) is the heuristic used for the estimated cost
 * of the cheapest path from n to the goal. For this class,
 * we are using the h(n) of Manhattan Distance which is 
 * the value of (X_current - X_goal) + (Y_current - Y_goal)
 * for each square. 
 *
 */

//import
import java.util.*;
import java.io.*;

public class A_Star extends Node {
	
	//set up instance variables
	Node gameBoard;
	int totalFrontierNodes;
	int totalExpansionNodes;
	int maxNodes;
	
	//constructor method
	public A_Star(Node gameState){
		super(gameState.getState());
		this.gameBoard = gameState;
	}
	
	//method to carry out the A* search
	public Node A_StarSearch(Node gameState) throws CloneNotSupportedException{ 
		//initialize priority queue for open list
		PriorityQueue<Node> o_list = new PriorityQueue<Node>(); 
		//initialize Hashtable for closed list representing Explored states
		ArrayList<Node> c_list = new ArrayList<Node>();
		//set up variables
		totalFrontierNodes = 0;
		totalExpansionNodes = 0;
		maxNodes = 0;
		
		//place starting node on open list
		o_list.add(gameState);
		totalFrontierNodes++;
		
		//loop through until all states in frontier explored w/o finding solution
		while(!o_list.isEmpty()){
			//check for 100k limit
			if(totalExpansionNodes > 100000){
				o_list.peek().setFailedSearch(true);
				System.out.println("no solution found (100k limit reached)");
				break;
			}	
			//check for 3rd performance metric
			if(o_list.size() > maxNodes){
				maxNodes = o_list.size();
			}
			
			//get state with smallest f(n) value - first in queue 
			Node stateToCheck = o_list.remove();
			//set the currentNode GValue and ManhattanDistance
			stateToCheck.setGValue(stateToCheck);
			stateToCheck.setManhattanDistance(stateToCheck);
			stateToCheck.setFValue(stateToCheck);
			
			//printState(stateToCheck.getState());
			//check if is the solution state 
			if(equals(stateToCheck.getState(),gameState.getSolution())){
				//set the currentNode FValue
				stateToCheck.setFValue(stateToCheck);
				//solution found
				o_list.add(stateToCheck);
				//return something to end
				return stateToCheck;
			}
			
			//put the current state on closed list
			c_list.add(stateToCheck);
			totalExpansionNodes++;
						
			//get the possible states from current state
			ArrayList<Node> possibleStates = findPossibleStates(stateToCheck);
			//set the possibleStates parent node to the current game state
			for(int k = 0; k < possibleStates.size(); k++){
				possibleStates.get(k).setParent(stateToCheck);
				//set the possibleStates GValue, ManhattanDistance, and FValue
				possibleStates.get(k).setGValue(possibleStates.get(k));
				possibleStates.get(k).setManhattanDistance(possibleStates.get(k));
				possibleStates.get(k).setFValue(possibleStates.get(k));
			}
			
			//check each possible state for heuristic calculations 
			for(int i = 0; i < possibleStates.size(); i++){
				//state that could succeed the current state
				Node stateFuture = possibleStates.get(i);
				//boolean variable used for keeping track of skip
				boolean skip = false;
				//boolean used to see if add to open list
				boolean add = false;
				
				//i: check if state is goal
				if(equals(stateFuture.getState(),gameState.getSolution())){
					//solution found
					o_list.add(stateFuture); 
					//return something to end
					return stateFuture;
				}
				
				//check if need to skip
				//check if on the lists
				if(!o_list.contains(stateFuture) && !c_list.contains(stateFuture)){
					//check if closed list has same state in already with lower f value.
					if(c_list.contains(stateFuture)){
						//get index of state
						int index = c_list.indexOf(stateFuture);
						if(c_list.get(index).getFValue() <= stateFuture.getFValue()){
							//skip
							continue;
						}
					}
					else{
						//add state to open list
						o_list.add(stateFuture);
						totalFrontierNodes++;
					}
				}
			}
		}
		//return
		Node p = new Node(gameState.getState());
		p.setFailedSearch(true);
		return p;
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
