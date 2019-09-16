/* This class is the Main Class for the project that will
 * include all the shared code (methods, functions, etc.) that
 * are needed for both of the searches. It will also handle 
 * reading in the initial input of the game state from text file
 * and storing it in a data struct. as to be able to search properly.
 */

//import statements
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.*;

public class Main {

	//main method in the start process of project. args[0] will be the path name to the problem in .txt format
	public static void main(String[] args) throws FileNotFoundException, CloneNotSupportedException {
		//set up the file
		//System.out.println(args.length);
		/*File file = new File(args[0]); 
		
		//conditions to check for file integrity
		if(!file.exists()){
			//File doesn't exist
			System.out.println("ERROR: File does not exist.  Please input a proper text file"); //ERROR MSG #1
		} 
		if(!file.isFile()){
			//File is not a "regular" file.  May be a directory.
			System.out.println("ERROR: File is not a proper file format.  Please input a proper text file"); //ERROR MSG #2
		}
		if(!file.canRead()){
			//File can't be read
			System.out.println("ERROR: File cannot be read. Please input a proper text file"); //ERROR MSG #3
		}*/
		
		//set up variable for board dimension
		int n_value = 0;
		
		//use a queue to temporarily store the values
		Queue<String> queue = new LinkedList<String>();
		
		//read in the file and store data 
		Scanner scanner = new Scanner(file);
		//go through each line of file
		while(scanner.hasNextLine()){
			String line = scanner.nextLine();
			//go through each #  in the line
			String [] numbersInLine = line.split(" ");
			//value of board dimensions (usually 3  x 3, n = 3)
			n_value = numbersInLine.length;
			for(int i=0; i < n_value; i++){
				queue.add(numbersInLine[i]);
			}
		}
		//close scanner
		scanner.close();
		
		/*set up multi-dimensional array data struct. to hold game
		* state.  Need to read file before able to specify size due to varying values of n
		*/
		String [][] gameBoard = new String[n_value][n_value];
		
		//store the numbers from the queue into the gameBoard 2-D array
		for(int j = 0; j < n_value; j++){
			for(int k = 0; k < n_value; k++){
				gameBoard[j][k] =  queue.remove();
			}
		}
		
		//put gameBoard in a node
		Node gameInitial = new Node(gameBoard);
		//create the "Solution" game state.  Blank tile is top left, then increasing numbers from left to right.
		gameInitial.setSolution(gameInitial.getState());	
		
		//at this point the data has been read from the file and is stored in our personal data struct. Time to search!
		
		//Search # 1: Uninformed Search Method
		System.out.println("DFS search: ");
		//run DFS search
		UninformedSearch a = new UninformedSearch(gameInitial);
		Stack<Node> pathStack = a.DFS(gameInitial);
		//check for failed search and don't print states if fail
		if(!pathStack.peek().getFailedSearch()){
			//create stack to store the path in correct order
			Stack<Node> path = new Stack<Node>();
			//pathStack.push(gameInitial);
			//get top of stack or solution state and backtrack to fill path
			Node n = pathStack.peek();
			while(n != null){
				path.push(n);
				n = n.getParent();
			}
			//print out correct order by popping out stack
			while(!path.isEmpty()){
				Node toPrint = path.pop();
				toPrint.printState(toPrint.getState());
				System.out.println();
			}
		}
		
		//print metrics
		System.out.println("Total # of search nodes added to frontier: " + a.getTotalFrontierNodes());
		System.out.println("Total # of search nodes seleced for expansion: " + a.getTotalExpansionNodes());
		System.out.println("Max Size of the search struct at anytime: " + a.getMaxNodes());
		System.out.println();
		
		//search 2: A* search
		System.out.println("A* Search: ");
		//run A* search
		A_Star b = new A_Star(gameInitial);
		Node m = b.A_StarSearch(gameInitial);
		
		//check if failed search and don't print states if failed
		
		if(!m.getFailedSearch()){
			//get correct order by tracing parents
			Stack<Node> toPrint2 = new Stack<Node>();
			while(m != null){
				toPrint2.push(m);
				m = m.getParent();
			}
			//print out stack trace
			while(!toPrint2.isEmpty()){
				Node n1 = toPrint2.pop();
				n1.printState(n1.getState());
				System.out.println();
			}
		}
		
		//print metrics
		System.out.println("Total # of search nodes added to frontier: " + b.getTotalFrontierNodes());
		System.out.println("Total # of search nodes seleced for expansion: " + b.getTotalExpansionNodes());
		System.out.println("Max Size of the search struct at anytime: " + b.getMaxNodes());
		System.out.println();
		
		//Search 3: BFS
		System.out.println("BFS Search: ");
		//run BFS
		BFS bfs = new BFS(gameInitial);
		Node c = bfs.BFS(gameInitial);
		
		//check if failed search and don't print states if failed
		
				if(!bfs.getFailedSearch()){
					//get correct order by tracing parents
					Stack<Node> toPrint3 = new Stack<Node>();
					while(c != null){
						toPrint3.push(c);
						c = c.getParent();
					}
					//print out stack trace
					while(!toPrint3.isEmpty()){
						Node n1 = toPrint3.pop();
						n1.printState(n1.getState());
						System.out.println();
					}
				}
		//print metrics
		System.out.println("Total # of search nodes added to frontier: " + bfs.getTotalFrontierNodes());
		System.out.println("Total # of search nodes seleced for expansion: " + bfs.getTotalExpansionNodes());
		System.out.println("Max Size of the search struct at anytime: " + bfs.getMaxNodes());
		System.out.println();
	}
	
}
