package org.um.feri.ears.problems;

import org.um.feri.ears.algorithms.Algorithm;

public class EvaluationStorage {

    public EvaluationStorage(){}

    public EvaluationStorage(String randomGenerator, long seed, Algorithm algorithm, Problem problem, int resolution, int numberOfRuns, int evaluationsPerRun) {
        this.randomGenerator = randomGenerator;
        this.seed = seed;
        this.algorithmName = algorithm.getAlgorithmInfo().getAcronym();
        this.algorithmVersion = algorithm.getVersion();
        this.problemName = problem.getName();
        this.problemVersion = problem.getVersion();
        this.problemDimensions = problem.getNumberOfDimensions();
        this.upperLimit = problem.upperLimit.stream().mapToDouble(i->i).toArray();
        this.lowerLimit = problem.lowerLimit.stream().mapToDouble(i->i).toArray();
        this.resolution = resolution;
        this.numberOfRuns = numberOfRuns;
        this.evaluationsPerRun = evaluationsPerRun;
        evaluations = new Evaluation[numberOfRuns][evaluationsPerRun];
    }

    public String randomGenerator;
    public long seed;
    public String algorithmName;
    public String algorithmVersion;
    public String problemName;
    public String problemVersion;
    public int problemDimensions;
    public double[] upperLimit;
    public double[] lowerLimit;
    public int resolution; //store every nth evaluation
    public int numberOfRuns;
    public int evaluationsPerRun;
    public Evaluation[][] evaluations;

    public static class Evaluation {

        public Evaluation() {}

        public Evaluation(int evalNum, int iteration, long time, double fitness) {
            this.evalNum = evalNum;
            this.iteration = iteration;
            this.time = time;
            this.fitness = fitness;
        }

        public int evalNum;
        public int iteration;
        public long time;
        public double fitness;
    }
}