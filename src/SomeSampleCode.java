import java.util.HashMap;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Stack;

public class SomeSampleCode {
    Stack stack2 = new Stack<SlidingTilePuzzle>();
    public static void main(String[] args) {


        // To construct an 8-puzzle first two params should be 3 (a 3 by 3 puzzle). 
        // The 3rd parameter is the length of the shortest path from this state to goal state.
        // E.g., 4 as the 3rd param will generate a random puzzle such that it is possible to
        // find a solution with length 4.
        SlidingTilePuzzle puzzle = new SlidingTilePuzzle(3, 3);

        // The SlidingTilePuzzle class overrides the toString method, so you can output
        // a puzzle state with a call to System.out.println as follows.
        // Don't do this in the middle of your A* search though.  You don't want to print every state
        // your search sees.  You will, however, want to print the states in the solution path once it is 
        // done.
        System.out.println(puzzle);


        // You can get a list of the successors (states that are one step away) with:
        ArrayList<SlidingTilePuzzle> successors = puzzle.getSuccessors();

        // You can check if the state is the goal state with
        if (puzzle.isGoalState()) {
            // do something
        }

        // You can get the tile in a specific location of the puzzle with the following
        // (example specifically shows row 0 and column 2).
        int tileNumber = puzzle.getTile(0, 2);

        // Your search algorithms will need to know the dimensions of the puzzle.
        // You can get the number of rows and columns with
        int rows = puzzle.numRows();
        int columns = puzzle.numColumns();


        // I've provided you with a priority queue implementation that supports changing priorities.
        // You'll need this.  The Priority Queue implementation in the Java API doesn't support that operation.
        // This works a bit differently than the one in the Java API.  Here are a few examples.

        // If you want an empty priority queue of SlidingTilePuzzles
        MinHeapPQ<SlidingTilePuzzle> pq = new MinHeapPQ<SlidingTilePuzzle>();

        // Use the offer method to add an element, value pair.  This is different than the Java API'f offer method
        // which relies on a Comparator.  What value you use will depend on the search algorithm you are implementing.
        pq.offer(puzzle, 7);

        // If you need to change the priority of an element already in the priority queue, simply call offer again.
        // If the priority is lower than the current one, it will decrease it.  Otherwise, it will leave it alone.
        // This implementation doesn't support increasing priority.
        pq.offer(puzzle, 4);

        // peek returns (but does not remove) the element with lowest priority.
        // peekPriority returns the priority of that element.
        SlidingTilePuzzle puzzle2 = pq.peek();
        int value = pq.peekPriority();

        // poll removes and returns the element with lowest priority (just the element... if you need its priority and don't already have it,
        // then use peekPriority first).
        SlidingTilePuzzle puzzle3 = pq.poll();


        // For both Uniform Cost Search and A*, you will need a MinHeapPQ as your frontier (the states awaiting expansion).
        // However, for every state we've ever generated, we need to keep track of the best f value (for A*) or the best g value (for uniform cost search)
        // that we have previously seen.  Additionally, we need to keep track of the backpointer that corresponds with that f or g value.
        // Backpointer is just the state we were at previously along the relevant path.
        // I recommend using Java's HashMap class for this.  You can either maintain one HashMap for the f values and one for the backpointers.
        // Or you can declare a cimple class that contains those two values and then just have a single HashMap.

        // Here's an example of how you might declare a HashMap to keep track of previous f values.
        // i.e., mapping puzzles to integers.
        HashMap<SlidingTilePuzzle, Integer> generatedSet = new HashMap<SlidingTilePuzzle, Integer>();

        // You can then add to it with put:
        generatedSet.put(puzzle, 4);

        // You can check if you've seen a puzzle state previously with something like:
        if (generatedSet.containsKey(puzzle)) {
            // do something here.
        }

        // you can find a state's f value with get 
        int v = generatedSet.get(puzzle);


        // Likewise, you can use a HashMap to store backpointers:
        HashMap<SlidingTilePuzzle, SlidingTilePuzzle> back = new HashMap<SlidingTilePuzzle, SlidingTilePuzzle>();

        for (SlidingTilePuzzle s : successors) {
            back.put(s, puzzle);
        }
        System.out.println(puzzle);

        ArrayList<SlidingTilePuzzle> path = new ArrayList<SlidingTilePuzzle>();
        ArrayList<SlidingTilePuzzle> path2 = new ArrayList<SlidingTilePuzzle>();
        SlidingTilePuzzleSolver solver = new SlidingTilePuzzleSolver();
        Stack stack = new Stack<SlidingTilePuzzle>();
        Stack stack2 = new Stack<SlidingTilePuzzle>();
        path = solver.uniformCostSearch(puzzle);

        int counter = 0;
        if(path == null){
            System.out.println("No solution");
        }
        else {

            for (SlidingTilePuzzle s : path) {
                if (s == null) {
                    break;
                }

                stack.push(s);
            }
            while (!stack.isEmpty()) {
                System.out.println("path Iteration: " + counter);
                System.out.println("UCS: " + '\n' + stack.pop() + "   " + SlidingTilePuzzleSolver.getNumExpandedStates()  + "   " + SlidingTilePuzzleSolver.getNumGeneratedStates());
                counter++;

            }

        }
        path2 = solver.AStarSearchManhattanDistance(puzzle);
        counter = 0;
        if(path == null){
            System.out.println("No solution");
        }
        else {
            for (SlidingTilePuzzle a : path2) {
                if (a == null) {
                    break;
                }
                stack2.push(a);
            }
            while (!stack2.isEmpty()) {
                System.out.println("path Iteration: " + counter);
                System.out.println("A*: " + '\n' + stack2.pop()+ "   " + SlidingTilePuzzleSolver.getNumExpandedStates() + "   " + SlidingTilePuzzleSolver.getNumGeneratedStates());
                counter++;

            }

        }

        /*
        for a star to print out.
         */



    }
}