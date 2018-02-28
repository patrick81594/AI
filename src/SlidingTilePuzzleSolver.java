
        import kotlin.reflect.jvm.internal.impl.types.checker.TypeIntersector;
        import org.jetbrains.annotations.NotNull;
        import java.util.Iterator;
        import java.util.ArrayList;
        import java.util.HashMap;
        import java.util.Set;

/**
 * In this assignment, you will implement Uniform Cost Search as well as A* Search for Sliding Tile Puzzles (i.e., 8-puzzle, 15-puzzle, etc).
 * Find the comments that begin with "STEP 1" etc for detailed instructions on what you are to implement.
 *
 * You may work on this either individually or in pairs (your choice).  If working on this as a pair, both get same grade.
 *
 * STEPS 1 through 6 are in this Java file.  I recommend doing them in the order I've numbered them.
 *
 * STEP 7 (Strongly recommended, but optional step): Implement some code to test your searches.  The constructor of SlidingTilePuzzle has a parameter
 *    for specifying the distance the puzzle's start is from the goal.  Since both Uniform Cost Search and A* with an admissible heuristic
 *    are optimal, your method from STEP 3 should return a path of that length.  Additionally, if your STEPS 1, 2, and 4 are correct, then
 *    the methods I provided (which depend on those), AStarSearchMisplacedTiles and AStarSearchManhattanDistance, should also produce
 *    the optimal path.
 *
 * STEPS 8 through 10 are in the file SlidingTileComparison.
 *
 * @author Your Name(s) Goes Here.
 */
public class SlidingTilePuzzleSolver {

    /**
     * Solves an instance of the Sliding Tile Puzzle using Uniform Cost Search.
     *
     * @param start The starting configuration of the Sliding Tile Puzzle to solve.
     *
     * @return The solution path, i.e., the sequence of states from the start state to the goal state.  Returns null if instance has no solution.
     */
    public static ArrayList<SlidingTilePuzzle> uniformCostSearch(SlidingTilePuzzle start) {
        // STEP 3: Implement Uniform Cost Search.  Read the comment above carefully for what this method should return.

        MinHeapPQ<SlidingTilePuzzle> pq = new MinHeapPQ<SlidingTilePuzzle>();
        HashMap<SlidingTilePuzzle, Integer> generatedSet = new HashMap<SlidingTilePuzzle, Integer>();
        HashMap<SlidingTilePuzzle, SlidingTilePuzzle> back = new HashMap<SlidingTilePuzzle, SlidingTilePuzzle>();
        ArrayList<SlidingTilePuzzle> path = new ArrayList<SlidingTilePuzzle>();
        SlidingTilePuzzle atStart;
        //back is the backpointers


        pq.offer(start, 0);
        //add start to generated set
        generatedSet.put(start, 0);
        back.put(start, null);    //start has a backpointer of null

        SlidingTilePuzzle goalState = null;    //goal state found at end of while loop
        while (!pq.isEmpty()) {
            int priority = pq.peekPriority() + 1;


            SlidingTilePuzzle leastCost = pq.poll();

            if (leastCost.isGoalState()) {
                back.put(leastCost, back.get(leastCost));
                if(leastCost == start){
                    path.add(start);
                }
                else {
                    do {

                        if(!path.contains(leastCost)) {
                            path.add(leastCost);
                        }
                        path.add(back.get(leastCost));

                        leastCost = back.get(leastCost);

                    } while (back.get(leastCost) != null);

                }
                return path;


            }

            ArrayList<SlidingTilePuzzle> neighborList = leastCost.getSuccessors();

            for (SlidingTilePuzzle successor : neighborList) {
                //before putting, check generated set to make sure that the priority that is already in there
                //is actually less than what you are trying to put. you dont want a higher priority inside generated set
                //if you find a smaller priority than make sure to update the backpointer (back) for the new smaller path.
                if (!generatedSet.containsKey(successor) && !pq.inPQ(successor)) {
                    pq.offer(successor, priority);
                    generatedSet.put(successor, priority);
                    back.put(successor, leastCost);
                } else if (generatedSet.containsKey(successor) && generatedSet.get(successor) > priority) {
                    generatedSet.replace(successor, priority);
                    back.replace(successor, leastCost);


                }


            }
        }
        return null;
    }

        // I added a bunch of things below where non of them worked too well. try them out and see if you can get one working

//		for(int i=0; i < back.size() - 1; i++)
//		{
//			System.out.println(back.get(i + 1));
//			path.add(back.get(i + 1));
//			
//		}


//		SlidingTilePuzzle backpoint = back.get(goalState);
//		while(backpoint != null)
//		{
//			path.add(backpoint);
//			System.out.println(backpoint);
//			backpoint = back.get(backpoint);
//		}



    /**
     * Solves an instance of the Sliding Tile Puzzle using A* Search.
     *
     * @param start The starting configuration of the Sliding Tile Puzzle to solve.
     * @param h The heuristic for use by A*
     *
     * @return The solution path, i.e., the sequence of states from the start state to the goal state.  Returns null if instance has no solution.
     */
    public static ArrayList<SlidingTilePuzzle> AStarSearch(SlidingTilePuzzle start, SlidingTilePuzzleHeuristic h) {
        // STEP 4: Implement A* Search.  Read the comment above carefully for what this method should return.

        /*****************************************************
         I probably did this all wrong. feel free to delete it all
         */
        return null;
    }

    /**
     * Solves an instance of the Sliding Tile Puzzle using A* Search using the heuristic number of misplaced tiles.
     *
     * @param start The starting configuration of the Sliding Tile Puzzle to solve.
     *
     * @return The solution path, i.e., the sequence of states from the start state to the goal state.  Returns null if instance has no solution.
     */
    public static ArrayList<SlidingTilePuzzle> AStarSearchMisplacedTiles(SlidingTilePuzzle start) {
        // DON'T MODIFY THIS METHOD
        return AStarSearch(start, new NumMisplacedTiles());
    }

    /**
     * Solves an instance of the Sliding Tile Puzzle using A* Search using the manhattan distance heuristic.
     *
     * @param start The starting configuration of the Sliding Tile Puzzle to solve.
     *
     * @return The solution path, i.e., the sequence of states from the start state to the goal state.  Returns null if instance has no solution.
     */
    public static ArrayList<SlidingTilePuzzle> AStarSearchManhattanDistance(SlidingTilePuzzle start) {
        // DON'T MODIFY THIS METHOD
        return AStarSearch(start, new ManhattanDistance());
    }


    /**
     * Gets the number of states expanded by the most recently executed search.
     */
    public static int getNumExpandedStates() {
        // DON'T MODIFY THIS METHOD
        return numExpanded;
    }

    /**
     * Gets the number of states generated by the most recently executed search.
     */
    public static int getNumGeneratedStates() {
        // DON'T MODIFY THIS METHOD
        return numGenerated;
    }

    // DON'T MODIFY THESE FIELD DECLARATIONS
    private static int numExpanded;
    private static int numGenerated;

    // STEP 5: Once you get your searches working in STEPS 3 and 4, add whatever code is necessary to your methods from STEPS 3 and 4
    //         to count the number of stated that are expanded by the search.  Specifically, at the very beginning of those two methods
    //         set numExpanded to 0 (this is the purpose of that field I've declared above).  And then, at the point in your code
    //		   where you get the successors of a state, increase numExpanded by 1 (generating the successors of a state is expanding the state).

    // STEP 6: Once you get your searches working in STEPS 3 and 4, add whatever code is necessary to your methods from STEPS 3 and 4
    //         to keep track of the total number of states generated by the searches.  Memory usage is proportional to this.
    //         Specifically, at the very beginning of those two methods, set numGenerated to 0.
    //         What you need to do next depends on how you keep track of states generated, such as f values, backpointers, etc.
    //         If you followed my suggestions in the comments in the SomeSampleCode.java file, then you likely used a HashMap.
    //         You can get the number of key-value pairs in a HashMap with the size method.  E.g., if map is a HashMap, then map.size()
    //         is the number of key-value pairs.
    //         Assuming this is what you did, then at all places in your methods from STEPS 3 and 4 where you have a return statement
    //         (including a return null if the problem is unsolvable), you will set numGenerated to whatever the size of your HashMap is.
    //         If you used two HashMaps, one for the f values and one for the backpointers, then just get the size of one of these (they should
    //         both have the same number of pairs if you didn't make any mistakes).
}

class NumMisplacedTiles implements SlidingTilePuzzleHeuristic {

    @Override
    public int h(SlidingTilePuzzle state) {
        // STEP 1: This method should compute the number of misplaced tiles,  i.e., the number of tiles
        // that are not currently in the correct place.  The SlidingTilePuzzle class provides methods
        // for accessing the tile numbers (see the get method in that class) as well as the dimensions
        // of the puzzle (number of rows and columns).

        int incorrect = 0;
        int k = 1;

        if(state.isGoalState())	// If state is already the goal state
            return incorrect;

        for(int i=0; i < state.numRows(); i++)
        {
            for(int j=0; j < state.numColumns(); j++)
            {
                if(state.getTile(i, j) != k)
                {
                    incorrect++;
                    k++;
                }
            }
        }

        return incorrect;
    }
}

class ManhattanDistance implements SlidingTilePuzzleHeuristic {

    @Override
    public int h(SlidingTilePuzzle state) {
        // STEP 2: This method should compute the sum of manhattan distance of the tiles from their
        // correct locations in the goal state.  The SlidingTilePuzzle class provides methods
        // for accessing the tile numbers (see the get method in that class) as well as the dimensions
        // of the puzzle (number of rows and columns).

        int sum = 0;
        int size = state.numRows();

        for(int i=0; i < size; i++)
        {
            for(int j=0; j < state.numColumns(); j++)
            {
                int value = state.getTile(i, j);

                if(value != 0)
                {
                    int targetRow = (int)Math.floor((value - 1) / size);
                    int targetCol = (value - 1) % size;
                    sum += Math.abs(i - targetRow) + Math.abs(j - targetCol);
                }
            }
        }

        return sum;
    }
}

// DON'T MODIFY THIS INTERFACE
interface SlidingTilePuzzleHeuristic {

    /**
     * Computes a heuruistic estimate of cost to get from state to goal.
     */
    int h(SlidingTilePuzzle state);
}	
