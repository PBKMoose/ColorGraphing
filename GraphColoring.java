import java.util.ArrayList;
import java.util.Random;

public class GraphColoring { // Class name

    private static final int MAX_COLORS = 4; // Maximum allowed colours
    private static final double INITIAL_TEMPERATURE = 0.99; // Initial temperature
    private static final double COOLING_RATE = 0.72; // Cooling rate
    public static void main(String[] args) {

        int[][] graph = {
                {0,1,1,0,0,0,1,0,1,0,1,1,0,1,1,1,0,0,1,1,0,1,0,0,1,0,1,1,0,0,0,0},
                {1,0,0,0,0,1,1,0,1,1,0,0,1,1,1,0,1,0,1,0,1,0,0,0,0,0,1,0,1,0,1,1},
                {1,0,0,1,1,0,0,1,1,1,1,0,1,0,1,0,1,0,0,0,1,0,0,1,0,1,1,1,0,1,1,1},
                {0,0,1,0,0,1,1,0,0,0,1,0,1,0,1,1,1,0,1,0,0,0,0,1,0,0,1,0,1,0,0,0},
                {0,0,1,0,0,0,1,0,1,0,1,0,1,0,0,1,1,0,1,0,1,1,1,0,0,0,0,1,0,0,0,1},
                {0,1,0,1,0,0,1,1,0,1,1,0,1,1,1,1,0,1,0,1,0,1,1,0,0,0,0,1,1,1,1,1},
                {1,1,0,1,1,1,0,0,0,1,0,1,1,0,0,0,0,0,1,1,0,0,0,1,1,1,0,1,0,0,1,1},
                {0,0,1,0,0,1,0,0,1,1,1,1,0,1,0,0,1,1,0,0,0,1,0,1,0,0,0,1,0,0,1,1},
                {1,1,1,0,1,0,0,1,0,1,0,0,0,0,0,0,0,1,0,0,1,0,0,1,1,1,1,0,0,0,1,0},
                {0,1,1,0,0,1,1,1,1,0,1,0,1,0,1,1,1,0,1,0,1,0,1,1,0,0,1,1,0,0,0,1},
                {1,0,1,1,1,1,0,1,0,1,0,1,1,0,1,1,0,0,0,1,0,1,1,0,1,1,1,1,1,0,1,0},
                {1,0,0,0,0,0,1,1,0,0,1,0,1,0,1,0,0,0,0,0,1,1,0,0,1,1,1,0,0,1,1,1},
                {0,1,1,1,1,1,1,0,0,1,1,1,0,0,0,0,0,1,0,0,1,0,0,0,0,0,1,1,1,1,1,0},
                {1,1,0,0,0,1,0,1,0,0,0,0,0,0,1,0,1,1,1,0,1,0,1,1,1,1,1,0,1,0,0,0},
                {1,1,1,1,0,1,0,0,0,1,1,1,0,1,0,1,0,1,1,1,0,0,0,1,0,0,1,1,0,1,1,0},
                {1,0,0,1,1,1,0,0,0,1,1,0,0,0,1,0,0,0,0,0,1,0,0,1,0,1,1,0,0,0,0,1},
                {0,1,1,1,1,0,0,1,0,1,0,0,0,1,0,0,0,0,0,1,1,1,0,0,1,1,0,0,0,0,0,0},
                {0,0,0,0,0,1,0,1,1,0,0,0,1,1,1,0,0,0,1,0,0,1,1,0,1,1,1,0,0,0,0,0},
                {1,1,0,1,1,0,1,0,0,1,0,0,0,1,1,0,0,1,0,0,1,0,0,0,1,0,0,0,1,1,0,0},
                {1,0,0,0,0,1,1,0,0,0,1,0,0,0,1,0,1,0,0,0,0,0,0,0,0,1,0,1,1,0,0,0},
                {0,1,1,0,1,0,0,0,1,1,0,1,1,1,0,1,1,0,1,0,0,1,0,0,0,0,1,1,0,1,0,1},
                {1,0,0,0,1,1,0,1,0,0,1,1,0,0,0,0,1,1,0,0,1,0,0,0,0,0,0,1,1,1,1,1},
                {0,0,0,0,1,1,0,0,0,1,1,0,0,1,0,0,0,1,0,0,0,0,0,0,0,1,1,1,0,0,1,1},
                {0,0,1,1,0,0,1,1,1,1,0,0,0,1,1,1,0,0,0,0,0,0,0,0,1,0,0,0,1,1,0,0},
                {1,0,0,0,0,0,1,0,1,0,1,1,0,1,0,0,1,1,1,0,0,0,0,1,0,1,0,1,0,0,0,1},
                {0,0,1,0,0,0,1,0,1,0,1,1,0,1,0,1,1,1,0,1,0,0,1,0,1,0,1,1,1,1,0,0},
                {1,1,1,1,0,0,0,0,1,1,1,1,1,1,1,1,0,1,0,0,1,0,1,0,0,1,0,1,1,1,1,0},
                {1,0,1,0,1,1,1,1,0,1,1,0,1,0,1,0,0,0,0,1,1,1,1,0,1,1,1,0,1,1,1,0},
                {0,1,0,1,0,1,0,0,0,0,1,0,1,1,0,0,0,0,1,1,0,1,0,1,0,1,1,1,0,0,1,0},
                {0,0,1,0,0,1,0,0,0,0,0,1,1,0,1,0,0,0,1,0,1,1,0,1,0,1,1,1,0,0,1,0},
                {0,1,1,0,0,1,1,1,1,0,1,1,1,0,1,0,0,0,0,0,0,1,1,0,0,0,1,1,1,1,0,0},
                {0,1,1,0,1,1,1,1,0,1,0,1,0,0,0,1,0,0,0,0,1,1,1,0,1,0,0,0,0,0,0,0}
        };

        int iterations = 10000;



        // Define step size for temperature and cooling rate (adjust as needed)
        double temperatureStep = 0.01;
        double coolingRateStep = 0.01;

        // Validate graph
        int validationResult = validateGraph(graph);
        if (validationResult != 1) {
            System.out.println("Invalid graph input.");
            System.out.println(validationResult);
            return;
        }

        // Generate initial colouring
        ArrayList<Integer> colouring = initialColouring(graph);
        System.out.println("Initial Colouring: " + colouring);

        // Check for colour clashes in initial colouring
        int initialClashes = colourClashes(colouring, graph);
        if (initialClashes > 0) {
            System.out.println("Warning: Initial colouring has " + initialClashes + " clashes.");
        }

        // Calculate fitness of initial colouring (optional)
        int initialFitness = calculateFitness(graph, colouring);
        System.out.println("Initial Fitness: " + initialFitness);

        // Loop through different temperatures and cooling rates
        for (double temperature = 0.0; temperature <= 1.0; temperature += temperatureStep) {
            for (double coolingRate = 0.0; coolingRate <= 1.0; coolingRate += coolingRateStep) {
                // Run Simulated Annealing with current parameters
                ArrayList<Integer> bestColouring = colorGraph(graph, iterations);


                // Verify best colouring size
                if (bestColouring.size() != graph.length) {
                    System.out.println("Error: Best colouring size mismatch.");
                    return;
                }

                // Print results for this temperature and cooling rate
                System.out.println("\nTemperature: " + temperature + ", Cooling Rate: " + coolingRate);
                System.out.println("Best Colouring: " + bestColouring);
                int bestClashes = colourClashes(bestColouring, graph);
                System.out.println("Best Fitness: " + calculateFitness(graph, bestColouring));
                System.out.println("Checking random node: " + (int) (Math.random() * graph.length));

                // You can add further analysis of bestColouring here (e.g., number of clashes)
            }
        }
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

    public static ArrayList<Integer> initialColouring(int[][] graph) {
        // Check for valid graph size
        if (graph.length == 0) {
            throw new IllegalArgumentException("Graph cannot be empty for colouring.");
        }

        ArrayList<Integer> colouring = new ArrayList<>(graph.length); // Use graph.length for number of nodes
        Random random = new Random();
        for (int i = 0; i < graph.length; i++) {
            int colour = random.nextInt(4) + 1; // Random colour between 1 and 4

            // Check for valid colour range (optional)
            if (colour < 1 || colour > 4) {
                throw new IllegalStateException("Unexpected colour generated: " + colour);
            }
            colouring.add(colour);
        }
        return colouring;
    }

    public static int colourClashes(ArrayList<Integer> colouring, int[][] graph) {
        // Check for valid colouring size (optional)
        if (colouring.size() != graph.length) {
            throw new IllegalArgumentException("Colouring size mismatch with graph size.");
        }

        // Check for empty colouring (optional)
        if (colouring.isEmpty()) {
            throw new IllegalArgumentException("Colouring cannot be empty.");
        }

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



    public static ArrayList<Integer> colorGraph(int[][] graph, int iterations) {
        if (iterations <= 0) {
            throw new IllegalArgumentException("Iterations must be greater than 0");
        }

        if (validateGraph(graph) != 1) {
            throw new IllegalArgumentException("Invalid graph input");
        }

        // Initialize random colouring
        ArrayList<Integer> colouring = initialColouring(graph);


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






