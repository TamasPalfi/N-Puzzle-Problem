N-Puzzle Problem Search Algorithms
__________________________________

Code Property of Tamas Palfi
Start Date: 9/21/2018
End Date: 
__________________________________

Fun Fact: Born in Budapest, Hungary
__________________________________

I worked on the project completely 
on my own without any peer
assistance. Please factor this in
for grading and be lenient.
___________________________________

The uninformed search method I 
implemented was DFS.  A second bonus
uninformed search method that was 
implemented was BFS.  
___________________________________

The Heuristic I used for the A* search
was the Manhattan Distance which is the 
value of the total sum of the 
(X_current - X_goal) + (Y_current - Y_goal)
for each square. 
___________________________________

After analyzing both simple and complex
test cases, the following results were 
found.  The slowest search was by far
DFS which reached the 100k limit several
times on complex cases and simple ones (if
the optimal moves were pushed on stack 
first).  This lead to high node counts for 
each metric.  The second slowest was BFS which
still added a lot of nodes for the metrics
but usually less than DFS and with a quicker
runtime.  A* performed the best with most
cases passing and in quick time with low 
metrics (<100 or so). 
___________________________________

The way to run this program is to compile 
it from the command prompt using 
javac Main.java
and then running it with:
java Main.java C:\path\to\txtfile (path of text file)

All the searches will run when Main is called
and output the results.  To run only one search, simply 
comment out the search methods which are to be skipped, 
recompile, and run. The reason for doing it like this
instead of different main methods is for 
printing properties and making the display look 
better even though it may make testing more difficult.
____________________________________

After spending nearly 50+ hours on this project and
losing quite a bit of sleep, I am proud to say that
some of this project is working.

The background parts all work including reading in the
file, storing it in a Node data struct, and printing output
& metrics. 

The A* search seems to work the best due to being
the best search algorithm of the three.  It 
usually returns the correct solution and does so
substantially fast with low states explored.

*******IMPORTANT*************
The DFS and BFS algorithms both work on some simple cases
yet fail to reach a solution state on other cases that 
have increased difficulty.  Either way, they run fast
due to the use of a HashSet to store explored.  The likely
reason of DFS failing so much is due to the method
findPossibleStates(gameState) which would find the future
states from one state.  My logic in that analyzed moves in the 
order of up, right, left, and down so once the DFS went down
an incorrect path it likely continued on that until fail.  
