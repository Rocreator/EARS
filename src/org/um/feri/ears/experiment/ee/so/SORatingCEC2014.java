package org.um.feri.ears.experiment.ee.so;

import org.um.feri.ears.problems.EnumStopCriteria;
import org.um.feri.ears.problems.Problem;
import org.um.feri.ears.problems.StopCriteriaException;
import org.um.feri.ears.problems.Task;
import org.um.feri.ears.problems.unconstrained.cec2014.*;
import org.um.feri.ears.util.Util;

public class SORatingCEC2014 {
	
    public static void main(String[] args) {
        Util.rnd.setSeed(System.currentTimeMillis());
        
        Problem[] problems = new Problem[16];
        int dimm;
        try
        {
        	dimm = Integer.parseInt(args[0]);
        }catch(Exception e)
        {
        	dimm = 2;
        }
        
        RandomWalkAlgorithmLogging randomLog = new RandomWalkAlgorithmLogging();
        HillClimbingLogging hillClimb = new HillClimbingLogging(0.001);
        JADELogging jadeLogging = new JADELogging();
        DEAlgorithmLogging deLogging = new DEAlgorithmLogging(DEAlgorithmLogging.DE_best_1_bin);
        jDElscopLogging jDElscopLog = new jDElscopLogging();
        TLBOAlgorithmLogging TLBOLog = new TLBOAlgorithmLogging();
        
        System.out.println(randomLog.getID()+ " "+ hillClimb.getID()+" "+jadeLogging.getID()+" "+
        		          deLogging.getID()+" "+ jDElscopLog.getID() +" "+TLBOLog.getID());
        
        for(int run = 0; run < 10; ++run)
        {
			problems[0] = new F1(dimm);
			problems[1] = new F2(dimm);
			problems[2] = new F3(dimm);
			problems[3] = new F4(dimm);
			problems[4] = new F5(dimm);
			problems[5] = new F6(dimm);
			problems[6] = new F7(dimm);
			problems[7] = new F8(dimm);
			problems[8] = new F9(dimm);
			problems[9] = new F10(dimm);
			problems[10] = new F11(dimm);
			problems[11] = new F12(dimm);
			problems[12] = new F13(dimm);
			problems[13] = new F14(dimm);
			problems[14] = new F15(dimm);
			problems[15] = new F16(dimm);
        	
        	for(int pr = 0; pr < problems.length; ++pr)
        	{
        	/*	Problem p = problems[pr];
        		try {
                	Task t = new Task(EnumStopCriteria.EVALUATIONS, 1000*dimm, 0, 0, 0.001, p);
                	t.enableAncestorLogging();
                	randomLog.execute(t);
        			t.saveAncestorLogging(randomLog.getID()+"_"+p.getName()+"D"+dimm+"R"+run);
        		} catch (StopCriteriaException e) {
        			e.printStackTrace();
        		}
        		
                Task.resetLoggingID();
                try {
                	Task t = new Task(EnumStopCriteria.EVALUATIONS, 1000*dimm, 0, 0, 0.001, p);
                	t.enableAncestorLogging();
                	hillClimb.execute(t);
        			t.saveAncestorLogging(hillClimb.getID()+"_"+p.getName()+"D"+dimm+"R"+run);
        		} catch (StopCriteriaException e) {
        			e.printStackTrace();
        		}
        		
              /*  Task.resetLoggingID();
                try {
                	Task t = new Task(EnumStopCriteria.EVALUATIONS, 1000*dimm, 0, 0, 0.001, p);
                	t.enableAncestorLogging();
                	jadeLogging.execute(t);
        			t.saveAncestorLogging(jadeLogging.getID()+"_"+p.getName()+"D"+dimm+"R"+run);
        		} catch (StopCriteriaException e) {
        			e.printStackTrace();
        		}
                
                Task.resetLoggingID();
                try{
                	Task t = new Task(EnumStopCriteria.EVALUATIONS, 1000*dimm, 0, 0, 0.001, p);
                	t.enableAncestorLogging();
                	deLogging.execute(t);
                	t.saveAncestorLogging(deLogging.getID().replaceAll("/", "-")+"_"+p.getName()+"D"+dimm+"R"+run);
                } catch (StopCriteriaException e){
                	e.printStackTrace();
                }
                Task.resetLoggingID();

                try{
                	Task t = new Task(EnumStopCriteria.EVALUATIONS, 1000*dimm, 0, 0, 0.001, p);
                	t.enableAncestorLogging();
                	jDElscopLog.execute(t);
                	t.saveAncestorLogging(jDElscopLog.getID().replaceAll("/", "-")+"_"+p.getName()+"D"+dimm+"R"+run);
                } catch (StopCriteriaException e){
                	e.printStackTrace();
                }
                Task.resetLoggingID();

                try{
                	Task t = new Task(EnumStopCriteria.EVALUATIONS, 1000*dimm, 0, 0, 0.001, p);
                	t.enableAncestorLogging();
                	TLBOLog.execute(t);
                	t.saveAncestorLogging(TLBOLog.getID().replaceAll("/", "-")+"_"+p.getName()+"D"+dimm+"R"+run);
                } catch (StopCriteriaException e){
                	e.printStackTrace();
                }*/
                Task.resetLoggingID();

        	}
        }
          
    }
}
