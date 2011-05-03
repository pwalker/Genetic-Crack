package nqueen;

import java.util.Random;

import basic.EvalBarrier;
import basic.Fitness;
import basic.GeneTool;
import basic.Overlord;
import basic.Population;

public class NQDriver {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		Fitness fitness = new NQFitness();
		
		NQEvaluator eval = new NQEvaluator(fitness);
		
		// setup our threadpool barrier
		EvalBarrier b = new EvalBarrier();
		
		Population population = new Population(Config.POPULATION_SIZE, eval, b);
		
		// initialize the population
		for (int i=0;i<Config.POPULATION_SIZE;i++){
			population.add(new NQCandidate());
		}
		
		Random rand = new Random();
		
		// our evaluator was a good place for these almost static methods
		GeneTool geneTool = eval;
		
		Overlord overlord = new Overlord(population, eval, geneTool, b, 12);
		
		for (int i=0; i<Config.GENERATIONS; i++){
			//System.err.println("running a generation...");
			int ret;
			try {
				ret = overlord.runGeneration();
				// TODO lets save our progress every 50 generations
				if (i % 50 == 0) {
					//population.savePopulation("population"+i+".csv");
					System.err.println();
				}
				
				System.err.println(i+" "+ret);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

}
