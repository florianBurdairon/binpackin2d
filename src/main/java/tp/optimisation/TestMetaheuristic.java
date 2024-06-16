package tp.optimisation;

import tp.optimisation.metaheuristics.*;
import tp.optimisation.neighbours.AbstractNeighboursCalculator;
import tp.optimisation.neighbours.NeighboursCalculator;

public class TestMetaheuristic {

    public static void main(String[] args) {
        AbstractNeighboursCalculator neighboursCalculator = new NeighboursCalculator();
        Metaheuristic metaheuristic = new HillClimbingMetaheuristic(neighboursCalculator);
        metaheuristic.setEpsilon(0);
        //metaheuristic.setMaxIterations(1000);

        String[] files = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13"};
        double[] theoreticalResults = {3, 5, 7, 12, 3, 5, 9, 12, 3, 6, 7, 13, 2};

        StringBuilder sb = new StringBuilder();

        for (int f = 0; f < files.length; f++) {
            String datasetFile = "binpacking2d-" + files[f] + ".bp2d";
            Dataset dataset = Dataset.fromFile("data/" + datasetFile);

            int nbProcess = 20;
            BinPacking bp = new BinPacking(dataset);
            bp.setRunOnUpdate(() -> {});
            bp.setMetaheuristic(metaheuristic);

            int nbBinsTotal = 0;
            int nbIterationsTotal = 0;
            long processTimeTotal = 0;
            for (int i = 0; i < nbProcess; i++) {
                bp.reset();
                processTimeTotal -= System.currentTimeMillis();
                bp.processUntilConvergence();
                processTimeTotal += System.currentTimeMillis();
                nbBinsTotal += bp.getBins().size();
                nbIterationsTotal += bp.getNbIterations();
            }

            double nbBinsMean = (double)nbBinsTotal / nbProcess;
            double nbIterationsMean = (double)nbIterationsTotal / nbProcess;
            double processTimeMean = (double)processTimeTotal / nbProcess;
            double scoreMean = (int)Math.round(nbBinsMean / theoreticalResults[f] * 10000) / 100.0;

            sb.append("| ").append(datasetFile)
                    .append(" | ").append(nbBinsMean)
                    .append(" | ").append(processTimeMean)
                    .append("ms | ").append(nbIterationsMean)
                    .append(" | ").append(scoreMean)
                    .append("% |\n");
        }
        System.out.println(sb);
    }
}
