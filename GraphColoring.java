import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;


public class GraphColoring { // Class name

//    private static final int MAX_COLORS = 4; // Maximum allowed colours
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
        for (double temperature = 0.0; temperature <= 0.9; temperature += temperatureStep) {
            for (double coolingRate = 0.0; coolingRate <= 0.9; coolingRate += coolingRateStep) {
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
            return 0;
        }

        int rows = graph.length;


        for (int[] ints : graph) {
            if (ints.length != rows) {
                return -1;
            }
        }


        for (int[] ints : graph) {
            for (int j = 0; j < rows; j++) {
                if (ints[j] != 0 && ints[j] != 1) {
                    return -2;
                }
            }
        }

        return 1;
    }

    public static ArrayList<Integer> initialColouring(int[][] graph) {

        if (graph.length == 0) {
            throw new IllegalArgumentException("Graph cannot be empty for colouring.");
        }

        ArrayList<Integer> colouring = new ArrayList<>(graph.length);
        Random random = new Random();
        for (int i = 0; i < graph.length; i++) {
            int colour = random.nextInt(4) + 1;


            if (colour < 1 || colour > 4) {
                throw new IllegalStateException("Unexpected colour generated: " + colour);
            }
            colouring.add(colour);
        }
        return colouring;
    }

    public static int colourClashes(ArrayList<Integer> colouring, int[][] graph) {

        if (colouring.size() != graph.length) {
            throw new IllegalArgumentException("Colouring size mismatch with graph size.");
        }


        if (colouring.isEmpty()) {
            throw new IllegalArgumentException("Colouring cannot be empty.");
        }

        int randomNode = (int) (Math.random() * graph.length);

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
                if (graph[i][j] == 1 && Objects.equals(colouring.get(i), colouring.get(j)) && !visited[j]) {
                    clashes++;
                    visited[j] = true;
                }
            }
        }
        return clashes;
    }

    public static ArrayList<Integer> smallChange(ArrayList<Integer> colouring) {
        ArrayList<Integer> newColouring = new ArrayList<>(colouring);
        Random random = new Random();
        int nodeToChange = random.nextInt(colouring.size());
        newColouring.set(nodeToChange, random.nextInt(4) + 1);
        return newColouring;
    }



    public static ArrayList<Integer> colorGraph(int[][] graph, int iterations) {


        ArrayList<Integer> colouring = initialColouring(graph);
        int bestFitness = calculateFitness(graph, colouring);
        ArrayList<Integer> bestColouring = new ArrayList<>(colouring);

        double temperature = INITIAL_TEMPERATURE;
        for (int i = 0; i < iterations; i++) {
            ArrayList<Integer> newColouring = smallChange(colouring);
            int newFitness = calculateFitness(graph, newColouring);


            double deltaE = newFitness - bestFitness;
            double probability = Math.exp(-deltaE / temperature);


            if (Math.random() < probability) {
                colouring = new ArrayList<>(newColouring);
            }


            bestFitness = Math.min(bestFitness, newFitness);

            temperature *= COOLING_RATE;
        }

        return bestColouring;
    }


    }






