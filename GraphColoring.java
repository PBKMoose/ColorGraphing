import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class GraphColoring { // Class name

    private static final int MAX_COLORS = 4; // Maximum allowed colours
    private static final double INITIAL_TEMPERATURE = 0.92; // Initial temperature
    private static final double COOLING_RATE = 0.87; // Cooling rate
    public static void main(String[] args) {

        int[][] graph = {
                {0, 1, 1, 1, 1, 1, 1, 1}, // Node 0 connected to all other nodes
                {1, 0, 1, 1, 1, 1, 1, 1}, // Node 1 connected to all other nodes
                {1, 1, 0, 1, 1, 1, 1, 1}, // Node 2 connected to all other nodes
                {1, 1, 1, 0, 1, 1, 1, 1}, // Node 3 connected to all other nodes
                {1, 1, 1, 1, 0, 1, 1, 1}, // Node 4 connected to all other nodes
                {1, 1, 1, 1, 1, 0, 1, 1}, // Node 5 connected to all other nodes
                {1, 1, 1, 1, 1, 1, 0, 1}, // Node 6 connected to all other nodes
                {1, 1, 1, 1, 1, 1, 1, 0},  // Node 7 connected to all other nodes
        };
        int iterations = 10000;

        // Validate graph
        int validationResult = validateGraph(graph);
        if (validationResult != 1) {
            System.out.println("Invalid graph input.");
            System.out.println(validationResult);
            return;
        }

        // Generate initial colouring
        ArrayList<Integer> colouring = initialColouring(graph.length);
        System.out.println("Initial Colouring: " + colouring);

        // Check for colour clashes in initial colouring
        int initialClashes = colourClashes(colouring, graph);
        if (initialClashes > 0) {
            System.out.println("Warning: Initial colouring has " + initialClashes + " clashes.");
        }

        // Calculate fitness of initial colouring (optional)
        int initialFitness = calculateFitness(graph, colouring);
        System.out.println("Initial Fitness: " + initialFitness);

        // Run Simulated Annealing
        ArrayList<Integer> bestColouring = colorGraph(graph, iterations);

        // Verify best colouring size
        if (bestColouring.size() != graph.length) {
            System.out.println("Error: Best colouring size mismatch.");
            return;
        }

        // Print best colouring and fitness
        System.out.println("After Simulated Annealing:");
        System.out.println("Best Colouring: " + bestColouring);
        int bestClashes = colourClashes(bestColouring, graph);
        System.out.println("Best Fitness: " + calculateFitness(graph, bestColouring));

        // Check for clashes in best colouring using your method
//        if (bestClashes > 0) {
//            System.out.println("Note: Best colouring still has " + bestClashes +  " clashes.");
//        } else {
//            System.out.println("Success: Best colouring has no clashes!");
//        }

        int randomNode = (int) (Math.random() * graph.length);
        int randomNodeClashes = colourClashes(bestColouring, graph);

        System.out.println("Checking random node: " + randomNode);
        System.out.println("Clashes at random node: " + randomNodeClashes);



    }


    public static int validateGraph(int[][] graph) {

        if (graph == null || graph.length == 0) {
            return 0; // Empty graph
        }

        int rows = graph.length;

        // Check if all rows have the same number of columns
        for (int i = 0; i < rows; i++) {
            if (graph[i].length != rows) {
                return -1; // Unequal columns
            }
        }

        // Check for valid values (0 or 1)
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < rows; j++) {
                if (graph[i][j] != 0 && graph[i][j] != 1) {
                    return -2; // Invalid value
                }
            }
        }

        return 1; // Valid graph
    }

    public static ArrayList<Integer> initialColouring(int n) {
        ArrayList<Integer> colouring = new ArrayList<>(n);
        Random random = new Random();
        for (int i = 0; i < n; i++) {
            colouring.add(random.nextInt(4) + 1); // Random colour between 1 and 4
        }
        return colouring;
    }

    public static int colourClashes(ArrayList<Integer> colouring, int[][] graph) {
        int randomNode = (int) (Math.random() * graph.length); // Generate a random node index

        int clashes = 0;
        for (int neighbour = 0; neighbour < graph.length; neighbour++) {
            if (neighbour != randomNode && graph[randomNode][neighbour] == 1 && colouring.get(randomNode) == colouring.get(neighbour)) {
                clashes++;

            }
        }
        return clashes;
    }

    public static int calculateFitness(int[][] graph, ArrayList<Integer> colouring) {
        int clashes = 0;
        boolean[] visited = new boolean[graph.length];

        for (int i = 0; i < graph.length; i++) {
            for (int j = i + 1; j < graph.length; j++) {
                if (graph[i][j] == 1 && colouring.get(i) == colouring.get(j) && !visited[j]) {
                    clashes++;
                    visited[j] = true;
                }
            }
        }
        return clashes;
    }

    public static ArrayList<Integer> smallChange(ArrayList<Integer> colouring) {
        ArrayList<Integer> newColouring = new ArrayList<>(colouring); // Copy the arrangement
        Random random = new Random();
        int nodeToChange = random.nextInt(colouring.size());
        newColouring.set(nodeToChange, random.nextInt(4) + 1); // Assign new random colour
        return newColouring;
    }

    public static int[][] createMatrix(int size) {
        int[][] matrix = new int[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (i == j) {
                    matrix[i][j] = 0; // No self-loops
                } else {
                    matrix[i][j] = 1; // Nodes i and j are connected
                    matrix[j][i] = 1; // Symmetric
                }
            }
        }
        return matrix;
    }

    public static ArrayList<Integer> colorGraph(int[][] graph, int iterations) {
        if (iterations <= 0) {
            throw new IllegalArgumentException("Iterations must be greater than 0");
        }

        if (validateGraph(graph) != 1) {
            throw new IllegalArgumentException("Invalid graph input");
        }

        // Initialize random colouring
        ArrayList<Integer> colouring = initialColouring(graph.length);

        int bestFitness = calculateFitness(graph, colouring);
        ArrayList<Integer> bestColouring = new ArrayList<>(colouring);

        // Simulated Annealing for specified iterations
        double temperature = INITIAL_TEMPERATURE;
        for (int i = 0; i < iterations; i++) {
            ArrayList<Integer> newColouring = smallChange(colouring);
            int newFitness = calculateFitness(graph, newColouring);

            // Calculate probability of accepting worse solution
            double deltaE = newFitness - bestFitness;
            double probability = accept(temperature, deltaE);

            // Accept worse solution with a probability
            if (Math.random() < probability) {
                colouring = new ArrayList<>(newColouring);
            } else {
                // Keep the current solution
            }

            // Update best if needed
            bestFitness = Math.min(bestFitness, newFitness);

            temperature *= COOLING_RATE; // Cool down the temperature
        }

        return bestColouring;
    }

    private static double accept(double temperature, double deltaE) {
        // Metropolis acceptance criterion
        if (deltaE < 0) { // Always accept improvement
            return 1.0;
        }

        return Math.exp(-deltaE / temperature);
    }

    }






