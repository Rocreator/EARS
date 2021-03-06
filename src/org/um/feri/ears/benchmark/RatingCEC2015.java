package org.um.feri.ears.benchmark;

import org.um.feri.ears.problems.EnumStopCriterion;
import org.um.feri.ears.problems.Problem;
import org.um.feri.ears.problems.Task;
import org.um.feri.ears.problems.unconstrained.cec2015.*;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;


public class RatingCEC2015 extends RatingBenchmark {
    protected boolean calculateTime = false;
    protected int warmupIterations = 10000;
    private double optimumEpsilon = 0.000001;

    public RatingCEC2015() {
        this(1e-7);
    }

    public RatingCEC2015(double drawLimit) {
        super();
        name = "Benchmark CEC 2015";
        this.drawLimit = drawLimit;
        maxEvaluations = 300000;
        dimension = 30;
        timeLimit = 2500;
        maxIterations = 2500;
        stopCriterion = EnumStopCriterion.EVALUATIONS;
        /*addParameter(EnumBenchmarkInfoParameters.STOPPING_CRITERIA,""+stopCriterion);
        addParameter(EnumBenchmarkInfoParameters.DIMENSION,""+dimension);
        addParameter(EnumBenchmarkInfoParameters.EVAL,String.valueOf(maxEvaluations));
        addParameter(EnumBenchmarkInfoParameters.DRAW_PARAM,"abs(evaluation_diff) < "+drawLimit);*/
    }

    @Override
    protected void registerTask(Problem p, EnumStopCriterion sc, int eval, long time, int maxIterations, double epsilon) {
        tasks.add(new Task(sc, eval, time, maxIterations, epsilon, p));
    }

    @Override
    public int getNumberOfRuns() {
        //number of runs set to 10 to reduce server execution time
        return 10;
    }

    @Override
    protected void initFullProblemList() {

        ArrayList<Problem> problems = new ArrayList<Problem>();

        problems.add(new F1(dimension));
        problems.add(new F2(dimension));
        problems.add(new F3(dimension));
        problems.add(new F4(dimension));
        problems.add(new F5(dimension));
        problems.add(new F6(dimension));
        problems.add(new F7(dimension));
        problems.add(new F8(dimension));
        problems.add(new F9(dimension));
        problems.add(new F10(dimension));
        problems.add(new F11(dimension));
        problems.add(new F12(dimension));
        problems.add(new F13(dimension));
        problems.add(new F14(dimension));
        problems.add(new F15(dimension));

        for (Problem p : problems) {
    		/*if(stopCriterion == EnumStopCriterion.CPU_TIME && calculateTime)
    		{
    			System.out.println("Calculating time for problem: "+p.getName());
    			timeLimit = calculateTime(p);
    		}*/

            if (stopCriterion == EnumStopCriterion.CPU_TIME) {
                for (int i = 0; i < warmupIterations; i++) {
                    p.getRandomEvaluatedSolution();
                }
            }

            registerTask(p, stopCriterion, maxEvaluations, timeLimit, maxIterations, optimumEpsilon);
        }
    }

    private long calculateTime(Problem p) {

        long start = System.nanoTime();
        long duration;
        for (int i = 0; i < maxEvaluations; i++) {
            p.getRandomEvaluatedSolution();
        }
        duration = System.nanoTime() - start;
        // add algorithm runtime
        duration += (int) (duration * (10.0f / 100.0f));

        return TimeUnit.NANOSECONDS.toMillis(duration);
    }
}
