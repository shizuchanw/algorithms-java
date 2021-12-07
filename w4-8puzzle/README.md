This is a program to solve the 8-puzzle problem using the A* search algorithm.\
\
the ![A* search algorithm](https://en.wikipedia.org/wiki/A*_search_algorithm): \
Define a `search node` of the game to be a board, the number of moves made to reach the board, and the previous search node. 
1. Insert the initial search node (the initial board, 0 moves, and a null previous search node) into a priority queue
2. Delete from the priority queue the search node with the minimum priority, and insert onto the priority queue all neighboring search nodes
3. Repeat this procedure until the search node dequeued corresponds to the goal board.

<br>

The efficacy depends on the choice of priority function:
1. the Hamming priority function (number of misplaced numbers)
2. the Manhattan priority function (number of total distance from goal
<hr>

Implement a 8-puzzle board: 
1. initialize the Board as a n\*n size int array (with n\*n size int matrix user input), use 0 to represent the empty space.
2. inside class Point, implement methods toString(), dimension(), isGoal(), twin(), and equals(Object y).
3. implement methods hamming() and  manhattan() to figure out how far away is the current board from goal.
4. implement Iterable\<Board> neighbors() for all the possible boards for next step.
