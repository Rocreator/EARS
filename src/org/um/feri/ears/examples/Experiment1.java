package org.um.feri.ears.examples;

import org.um.feri.ears.algorithms.so.de.DEAlgorithm;
import org.um.feri.ears.algorithms.so.es.ES1p1sAlgorithm;
import org.um.feri.ears.algorithms.so.random.RandomWalkAMAlgorithm;
import org.um.feri.ears.algorithms.so.random.RandomWalkAlgorithm;
import org.um.feri.ears.algorithms.so.tlbo.TLBOAlgorithm;
import org.um.feri.ears.benchmark.RatingRPUOed2;
import org.um.feri.ears.rating.Rating;


public class Experiment1 {
    public static void main(String[] args) {
        RunMain m = new RunMain(false, false, new RatingRPUOed2());
        m.addAlgorithm(new RandomWalkAlgorithm(), new Rating(1500, 350, 0.06));
        m.addAlgorithm(new RandomWalkAMAlgorithm(), new Rating(1500, 350, 0.06));
        m.addAlgorithm(new ES1p1sAlgorithm(), new Rating(1500, 350, 0.06));
        //m.addAlgorithm(new SwarmAlgorithm(),new Rating(1500, 350, 0.06));
        //m.addAlgorithm(new BeeColonyAlgorithm(),new Rating(1500, 350, 0.06));
        m.addAlgorithm(new TLBOAlgorithm(), new Rating(1500, 350, 0.06));
        for (DEAlgorithm.Strategy strategy : DEAlgorithm.Strategy.values())
            m.addAlgorithm(new DEAlgorithm(strategy, 20), new Rating(1500, 350, 0.06));
        m.run(2);
        System.out.println(m);
    }
}
