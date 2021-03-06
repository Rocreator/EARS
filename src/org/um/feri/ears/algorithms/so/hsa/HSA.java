package org.um.feri.ears.algorithms.so.hsa;

import org.um.feri.ears.algorithms.Algorithm;
import org.um.feri.ears.algorithms.AlgorithmInfo;
import org.um.feri.ears.algorithms.Author;
import org.um.feri.ears.algorithms.EnumAlgorithmParameters;
import org.um.feri.ears.problems.DoubleSolution;
import org.um.feri.ears.problems.StopCriterionException;
import org.um.feri.ears.problems.Task;
import org.um.feri.ears.util.Util;

public class HSA extends Algorithm {

    private DoubleSolution best;
    private Task task;
    private int popSize;
    private DoubleSolution[] population;

    private double HMCR = 0.99; // harmony Memory Considering Rate
    private double PAR = 0.5; // Pitch Adjusting Rate
    private double BW = 0.5; // Band Width

    public HSA() {
        this(50);
    }

    public HSA(int popSize) {

        this.popSize = popSize;

        au = new Author("miha", "miha.ravber@um.si");
        ai = new AlgorithmInfo("HSA", "Harmony Search Algorithm",
                "@article{kim2016harmony, " +
                        "title={Harmony search algorithm: A unique music-inspired algorithm}, " +
                        "author={Kim, Joong Hoon}, " +
                        "journal={Procedia engineering}, " +
                        "volume={154}, " +
                        "pages={1401--1405}, " +
                        "year={2016}, " +
                        "publisher={Elsevier}}"
        );
        ai.addParameter(EnumAlgorithmParameters.POP_SIZE, popSize + "");
    }

    @Override
    public DoubleSolution execute(Task taskProblem) throws StopCriterionException {
        task = taskProblem;

        initPopulation();
        while (!task.isStopCriterion()) {
            double[] newHarmony = new double[task.getNumberOfDimensions()];

            for (int i = 0; i < task.getNumberOfDimensions(); i++) {
                if (Util.nextDouble() < HMCR) {
                    newHarmony[i] = population[(int) (popSize * Util.nextDouble())].getValue(i);
                    if (Util.nextDouble() < PAR) {
                        newHarmony[i] = pitchAdjust(newHarmony[i], i);
                    }
                } else {
                    newHarmony[i] = task.getLowerLimit(i) + (task.getUpperLimit(i) - task.getLowerLimit(i)) * Util.nextDouble();
                }
            }
            if (task.isStopCriterion())
                break;

            DoubleSolution newSolution = task.eval(newHarmony);
            //updateHarmonyMemory(newScore, newHarmony);

            //find worst harmony
            int worstIndex = 0;
            for (int i = 1; i < popSize; i++) {
                if (task.isFirstBetter(population[worstIndex], population[i]))
                    worstIndex = i;
            }

            if (task.isFirstBetter(newSolution, population[worstIndex])) {
                population[worstIndex] = newSolution;
            }
            //find best harmony
            if (task.isFirstBetter(newSolution, best)) {
                best = new DoubleSolution(newSolution);
            }
            task.incrementNumberOfIterations();
        }
        return best;
    }

    private double pitchAdjust(double value, int dimension) {
        double newValue;
        if (Util.nextDouble() < 0.5) {
            newValue = value + Util.nextDouble() * BW;
        } else {
            newValue = value - Util.nextDouble() * BW;
        }
        return task.setFeasible(newValue, dimension);
    }

    private void initPopulation() throws StopCriterionException {
        population = new DoubleSolution[popSize];

        best = task.getRandomEvaluatedSolution();
        population[0] = new DoubleSolution(best);
        for (int i = 1; i < popSize; i++) {
            if (task.isStopCriterion())
                break;
            population[i] = task.getRandomEvaluatedSolution();
            if (task.isFirstBetter(population[i], best)) {
                best = new DoubleSolution(population[i]);
            }
        }
    }

    @Override
    public void resetToDefaultsBeforeNewRun() {

    }
}
