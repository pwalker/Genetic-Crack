package nqueen;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
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
		
		Overlord overlord = new Overlord(population, eval, geneTool, b, 4);
		
		PrintStream output = System.err;
		
//		FileOutputStream f;
//		try {
//			f = new FileOutputStream("output.txt");
//			output = new PrintStream(f);
//		} catch (FileNotFoundException e1) {
//			
//		}
		
		int max = 0;
		int i;
		for (i=0; !overlord.isDone(fitness.maxFitness()); i++){
			try {
				overlord.runGeneration();
				
				NQCandidate bestCand = (NQCandidate) overlord.getBest();
				
				if (bestCand.getFitness()>=max){
					output.printf("%s\t%s\t%s\t%s\n",i,overlord.populationFitness(),bestCand.getFitness(),bestCand.getGenes());
					max = bestCand.getFitness();
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		NQCandidate bestCand = (NQCandidate) overlord.getBest();
		output.printf("%s\t%s\t%s\t%s\n",i,overlord.populationFitness(),bestCand.getFitness(),bestCand.getGenes());
		
		//System.err.println("tidying up!");
		overlord.joinThreads();
	}

}
