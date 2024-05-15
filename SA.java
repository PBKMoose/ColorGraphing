import java.util.ArrayList;

public class SimulatedAnnealingColoring {

    public static final double DEFAULT_COOLING_RATE = 0.9;

    private final double coolingRate;

    public SimulatedAnnealingColoring(double initialTemperature, double coolingRate) {
        validateTemperature(initialTemperature);
        validateCoolingRate(coolingRate);
        this.coolingRate = coolingRate;
    }

    private void validateTemperature(double temperature) {
        if (temperature <= 0) {
            throw new IllegalArgumentException("Initial temperature must be positive.");
        }
    }

    private void validateCoolingRate(double rate) {
        if (rate <= 0 || rate >= 1) {
            throw new IllegalArgumentException("Cooling rate must be between 0 and 1 (exclusive).");
        }
    }

    public ArrayList<Integer> colorGraph(int[][] graph, int iterations, double initialTemperature) {
        if (iterations <= 0) {
            throw new IllegalArgumentException();
        }

        ArrayList<Integer> colouring = initialColouring(graph); // Assuming initialColouring exists elsewhere
        int bestFitness = calculateFitness(graph, colouring); // Assuming calculateFitness exists elsewhere
        ArrayList<Integer> bestColouring = new ArrayList<>(colouring);

        double temperature = initialTemperature;
        for (int i = 0; i < iterations; i++) {
            ArrayList<Integer> newColouring = smallChange(colouring); // Assuming smallChange exists elsewhere
            int newFitness = calculateFitness(graph, newColouring);

            double deltaE = newFitness - bestFitness;
            double probability = Math.exp(-deltaE / temperature);

            if (Math.random() < probability) {
                colouring = new ArrayList<>(newColouring);
                bestFitness = newFitness; // Update bestFitness only if accepted
            }

            temperature *= coolingRate;
        }

        return bestColouring;
    }
}
