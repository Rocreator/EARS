package org.um.feri.ears.algorithms.so.pso;

import org.um.feri.ears.algorithms.Algorithm;
import org.um.feri.ears.algorithms.AlgorithmInfo;
import org.um.feri.ears.algorithms.Author;
import org.um.feri.ears.algorithms.EnumAlgorithmParameters;
import org.um.feri.ears.problems.DoubleSolution;
import org.um.feri.ears.problems.StopCriterionException;
import org.um.feri.ears.problems.Task;
import org.um.feri.ears.util.Util;

import java.util.ArrayList;

public class PSOOmega extends Algorithm {

    private int popSize;
    private ArrayList<MyPSOSolution> x;
    private Task task;
    private MyPSOSolution g; //global best
    private double omega, phiG, phiP;

    public PSOOmega() {
        this(10, 0.7, 2, 2);
    }

    public PSOOmega(int popSize, double om, double p1, double p2) {
        super();
        this.popSize = popSize;
        this.omega = om;
        this.phiP = p1;
        this.phiG = p2;
        setDebug(debug);  //EARS prints some debug info
        ai = new AlgorithmInfo("PSOomega", "Particle Swarm Optimization Omega", "");
        ai.addParameter(EnumAlgorithmParameters.POP_SIZE, popSize + "");
        ai.addParameter(EnumAlgorithmParameters.C1, p1 + "");
        ai.addParameter(EnumAlgorithmParameters.C2, p2 + "");
        ai.addParameter(EnumAlgorithmParameters.UNNAMED1, om + "");
        //ai.addParameter(EnumAlgorithmParameters., F + "");
        au = new Author("Matej", "matej.crepinsek@um.si");
    }

    @Override
    public DoubleSolution execute(Task taskProblem) throws StopCriterionException {
        task = taskProblem;
        initPopulation();
        double rp, rg;
        double[] v;
        while (!task.isStopCriterion()) {
            for (int i = 0; i < popSize; i++) {
                rp = Util.rnd.nextDouble();
                rg = Util.rnd.nextDouble();
                v = new double[task.getNumberOfDimensions()];
                // r*vec(x) double r = Util.rnd.nextDouble();
                for (int d = 0; d < task.getNumberOfDimensions(); d++) {
                    //http://www.atscience.org/IJISAE/article/view/7
                    //omega different formula omega multiplies with
                    v[d] = omega * (
                            x.get(i).getV()[d] +
                                    phiP * Util.rnd.nextDouble() * (x.get(i).getP().getValue(d) - x.get(i).getValue(d)) +
                                    phiG * Util.rnd.nextDouble() * (g.getValue(d) - x.get(i).getValue(d)));
                    //if (v[d]>(taskProblem.getIntervalLength()[d])) v[d]=taskProblem.getIntervalLength()[d];
                    //if (v[d]<(taskProblem.getIntervalLength()[d])) v[d]=-taskProblem.getIntervalLength()[d];
                }
                x.set(i, x.get(i).update(task, v));
                if (task.isFirstBetter(x.get(i), g)) g = x.get(i);
                if (task.isStopCriterion()) break;
            }
            task.incrementNumberOfIterations();
        }
        return g;
    }

    private void initPopulation() throws StopCriterionException {
        x = new ArrayList<>();
        for (int i = 0; i < popSize; i++) {
            x.add(new MyPSOSolution(task));
            if (i == 0) g = x.get(0);
            else if (task.isFirstBetter(x.get(i), g)) g = x.get(i);
        }
    }

    @Override
    public void resetToDefaultsBeforeNewRun() {

    }
}