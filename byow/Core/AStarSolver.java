package byow.Core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import edu.princeton.cs.algs4.Stopwatch;

public class AStarSolver<Vertex> implements ShortestPathsSolver<Vertex> {
    private ArrayHeapMinPQ<Vertex> fringe;
//    private ArrayList<Double> distTo;
//    private ArrayList<Integer> edgeTo;
//    private HashMap<Vertex, Integer> vertexToIndex; //add to this when disTo/edgeTo has added

    private HashMap<Vertex, Double> distTo;
    private HashMap<Vertex, Vertex> edgeTo;



    private SolverOutcome result;
    private List<Vertex> solution;
    private double totalWeight;
    private int dequeueCount; //done
    private double time; //done


    /**
     * Constructor which finds the solution, computing everything
     * necessary for all other methods to return their results in
     * constant time. Note that timeout passed in is in seconds.
     * */
    public AStarSolver(AStarGraph<Vertex> input, Vertex start, Vertex end, double timeout) {
        Stopwatch swOuter = new Stopwatch();
        this.fringe = new ArrayHeapMinPQ<>(); //priority queue fringe with start vertex only
        this.fringe.add(start, input.estimatedDistanceToGoal(start, end));

        this.distTo = new HashMap<>();
        this.distTo.put(start, 0.0);
        this.edgeTo = new HashMap<>();
        this.edgeTo.put(start, null);

        this.solution = new ArrayList<>(); //A list of vertices corresponding to a solution.
        this.totalWeight = 0.0;
        this.dequeueCount = 0;
        this.result = solveAStar(input, start, end, timeout); //run heavy duty constructor helper.
        this.time = swOuter.elapsedTime();
    }

    //helper for the constructor. Returns result for termination purposes
    private SolverOutcome solveAStar(AStarGraph<Vertex> input,
                                     Vertex start, Vertex end, double timeout) {
        Stopwatch sw = new Stopwatch();
        Vertex currVertex = start;

        while (!currVertex.equals(end)) { //DO NOT USE currVertex != end;
            if (sw.elapsedTime() > timeout) {
                this.solution.clear();
                this.totalWeight = 0;
                return SolverOutcome.TIMEOUT;
            }
            currVertex = this.fringe.removeSmallest();
            this.dequeueCount += 1;
            List<WeightedEdge<Vertex>> neighborEdges = input.neighbors(currVertex);
            //relaxing process
            for (WeightedEdge<Vertex> e : neighborEdges) {
                if (distTo.containsKey(e.to())) { //existing marked vertex
                    double h = input.estimatedDistanceToGoal(e.to(), end);
                    double pqOldEstimate = distTo.get(e.to()) + h; //fringe.priority(e.to())
                    double pqNewEstimate = distTo.get(currVertex) + e.weight() + h;
                    if (pqNewEstimate < pqOldEstimate) {
                        distTo.put(e.to(), distTo.get(currVertex) + e.weight());
                        edgeTo.put(e.to(), currVertex);
                        this.fringe.changePriority(e.to(), pqNewEstimate);
                    }
                } else { //newly visited vertex
                    distTo.put(e.to(), distTo.get(currVertex) + e.weight());
                    edgeTo.put(e.to(), currVertex);
                    this.fringe.add(e.to(), distTo.get(e.to())
                            + input.estimatedDistanceToGoal(e.to(), end));
                }
            }
            if (this.fringe.size() != 0) {
                currVertex = this.fringe.getSmallest();
            } else {
                this.solution.clear();
                this.totalWeight = 0;
                return SolverOutcome.UNSOLVABLE;
            }
        }

        //answers to return
        solutionHelper(start, end);
        this.totalWeight = distTo.get(end);
        return SolverOutcome.SOLVED;
    }

    //helper for finding solution
    private void solutionHelper(Vertex start, Vertex end) {
        Vertex curr = end;
        while (curr != null) {
            this.solution.add(curr);
            curr = edgeTo.get(curr);
        }

        List<Vertex> reverseTemp = new ArrayList<>();
        for (int i = 0; i < this.solution.size(); i++) {
            reverseTemp.add(this.solution.get(this.solution.size() - 1 - i));
        }
        this.solution = reverseTemp;
    }

    /** Returns one of SolverOutcome.SOLVED, SolverOutcome.TIMEOUT,
     * or SolverOutcome.UNSOLVABLE. Should be SOLVED if the AStarSolver
     * was able to complete all work in the time given. UNSOLVABLE if
     * the priority queue became empty. TIMEOUT if the solver ran out
     * of time. You should check to see if you have run out of time
     * every time you dequeue. Constant time.
     * */
    @Override
    public SolverOutcome outcome() {
        return result;
    }

    /** A list of vertices corresponding to a solution.
     * Should be empty if result was TIMEOUT or UNSOLVABLE.
     * Constant time.
     * */
    @Override
    public List<Vertex> solution() {
        return solution;
    }

    /** The total weight of the given solution, taking into
     * account edge weights. Should be 0 if result was
     * TIMEOUT or UNSOLVABLE. Constant time.
     * */
    @Override
    public double solutionWeight() {
        return totalWeight;
    }

    /** The total number of priority queue dequeue operations.
     * Constant time.
     * */
    @Override
    public int numStatesExplored() {
        return dequeueCount;
    }

    /** The total time spent in seconds by the constructor.
     * Constant time.
     * */
    @Override
    public double explorationTime() {
        return time;
    }
}
