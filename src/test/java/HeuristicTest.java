import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import tp.optimisation.metaheuristics.HillClimbingMetaheuristic;
import tp.optimisation.metaheuristics.Metaheuristic;
import tp.optimisation.neighbours.NeighboursCalculator;

import java.util.stream.Stream;

public class HeuristicTest {

    @ParameterizedTest(name = "Test Hill Climbing Metaheuristic")
    @MethodSource("testHillClimbingMetaheuristicValues")
    public void testHillClimbingMetaheuristic() {
        //TODO: Do this test
        Metaheuristic heuristic = new HillClimbingMetaheuristic(new NeighboursCalculator());
    }

    private static Stream<Arguments> testHillClimbingMetaheuristicValues() {
        return Stream.of(
                Arguments.of(1, 2),
                Arguments.of(2, 3),
                Arguments.of(3, 4)); // TODO: Add test cases
    }
}
