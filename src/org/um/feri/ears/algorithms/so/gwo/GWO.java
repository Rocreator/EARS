package org.um.feri.ears.algorithms.so.gwo;

import org.um.feri.ears.algorithms.Algorithm;
import org.um.feri.ears.algorithms.AlgorithmInfo;
import org.um.feri.ears.algorithms.Author;
import org.um.feri.ears.algorithms.EnumAlgorithmParameters;
import org.um.feri.ears.problems.DoubleSolution;
import org.um.feri.ears.problems.EnumStopCriterion;
import org.um.feri.ears.problems.StopCriterionException;
import org.um.feri.ears.problems.Task;
import org.um.feri.ears.util.Comparator.TaskComparator;
import org.um.feri.ears.util.Util;

import java.util.ArrayList;

public class GWO extends Algorithm {

    private DoubleSolution alpha, beta, delta;

    private int popSize;
    private Task task;

    private double r1;
    private double r2;
    private double A;
    private double C;
    private double X1, X2, X3;

    private ArrayList<DoubleSolution> population;

    public GWO() {
        this(30);
    }

    public GWO(int popSize) {
        super();
        this.popSize = popSize;

        au = new Author("miha", "miha.ravber@um.si");
        ai = new AlgorithmInfo("GWO", "Gray Wolf Optimizer",
                "@article{mirjalili2014grey,"
                        + "  title={Grey wolf optimizer},"
                        + "  author={Mirjalili, Seyedali and Mirjalili, Seyed Mohammad and Lewis, Andrew},"
                        + "  journal={Advances in Engineering Software},"
                        + "  volume={69},"
                        + "  pages={46--61},"
                        + "  year={2014},"
                        + "  publisher={Elsevier}}"
        );
        ai.addParameter(EnumAlgorithmParameters.POP_SIZE, popSize + "");
    }

    @Override
    public DoubleSolution execute(Task taskProblem) throws StopCriterionException {
        task = taskProblem;
        initPopulation();
        int maxIt = 10000;
        if (task.getStopCriterion() == EnumStopCriterion.ITERATIONS) {
            maxIt = task.getMaxIterations();
        }

        if (task.getStopCriterion() == EnumStopCriterion.EVALUATIONS) {
            maxIt = task.getMaxEvaluations() / popSize;
        }


        while (!task.isStopCriterion()) {
            double a = 2.0 - task.getNumberOfIterations() * (2.0 / maxIt);

            for (int index = 0; index < popSize; index++) {
                DoubleSolution wolf = population.get(index);
                double[] newPosition = new double[task.getNumberOfDimensions()];
                for (int i = 0; i < task.getNumberOfDimensions(); i++) {

                    r1 = Util.nextDouble();
                    r2 = Util.nextDouble();

                    A = 2 * a * r1 - a; // Equation (3.3)
                    C = 2 * r2; // Equation (3.4)

                    double D_alpha = Math.abs(C * alpha.getValue(i) - wolf.getValue(i)); //Equation (3.5)-part 1
                    X1 = alpha.getValue(i) - A * D_alpha; //Equation (3.6)-part 1


                    r1 = Util.nextDouble();
                    r2 = Util.nextDouble();

                    A = 2 * a * r1 - a; // Equation (3.3)
                    C = 2 * r2; // Equation (3.4)

                    double D_beta = Math.abs(C * beta.getValue(i) - wolf.getValue(i)); //Equation (3.5)-part 2
                    X2 = beta.getValue(i) - A * D_beta; //Equation (3.6)-part 2


                    r1 = Util.nextDouble();
                    r2 = Util.nextDouble();

                    A = 2 * a * r1 - a; // Equation (3.3)
                    C = 2 * r2; // Equation (3.4)

                    double D_delta = Math.abs(C * delta.getValue(i) - wolf.getValue(i)); //Equation (3.5)-part 3
                    X3 = delta.getValue(i) - A * D_delta; //Equation (3.6)-part 3

                    newPosition[i] = (X1 + X2 + X3) / 3; // Equation (3.7)
                }
                newPosition = task.setFeasible(newPosition);

                if (task.isStopCriterion())
                    break;
                DoubleSolution newWolf = task.eval(newPosition);
                if (task.isFirstBetter(newWolf, population.get(index)))
                    population.set(index, newWolf);
            }
            updateABD();
            task.incrementNumberOfIterations();
        }
        return alpha;
    }

    private void initPopulation() throws StopCriterionException {
        population = new ArrayList<DoubleSolution>();

        for (int i = 0; i < popSize; i++) {
            population.add(task.getRandomEvaluatedSolution());
            if (task.isStopCriterion())
                break;
        }
        updateABD();
    }

    /**
     * Update alpha, beta and delta wolfs.
     */
    private void updateABD() {

        population.sort(new TaskComparator(task));
        alpha = population.get(0);
        beta = population.get(1);
        delta = population.get(2);
    }

    @Override
    public void resetToDefaultsBeforeNewRun() {
    }

}
