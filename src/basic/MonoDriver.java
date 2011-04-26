package basic;

import java.util.Random;
import java.util.Scanner;

public class MonoDriver {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// Read in plaintext from stdin
		/*Scanner s = new Scanner(System.in);
		String plainText = "";
		while(s.hasNextLine()){
			plainText += s.nextLine();
		}*/
		
		String cipherText = "PGDAH EINPE MACAT AFZEM XQHAM AUEPQ HAIGD JRQAM JMGTM EDQHE QZAQA MDOFA ZQHAD OFODR DEDGR FQGBQ ODAOQ QGGNQ GIGSA MQHAA FQOMA FAUXG MNIOQ XPRLU EXPXP QADEB QAMQH AJMGT MEDUE PUMOQ QAFEL RFIHG BDOQH EINAM PEIQR ECCXU AFQQG FAUXG MNEFZ QMOAZ OQGRQ NEQOA HEBFA MOFIX LAMJR FNPGD ATAAN PEMAW RPQTA ANOAM QHEFG QHAMP";
		
		Fitness fitness = new MonoFitness("/home/peterw/Projects/Eclipse/GeneticCrack/top1000.txt",new MonoCipher(),cipherText);
		
		MonoEvaluator eval = new MonoEvaluator(fitness);
		
		Population population = new Population(4000, eval);

		Random rand = new Random();
		
		// our evaluator was a good place for these almost static methods
		GeneTool geneTool = eval;
		
		Overlord overlord = new Overlord(population, eval, geneTool, 4);
		
		for (int i=0; i<20000; i++){
			//System.err.println("running a generation...");
			int ret;
			try {
				ret = overlord.runGeneration();
				// TODO lets save our progress every 50 generations
				if (i % 200 == 0) {
					population.savePopulation("population"+i+".txt");
				}
				
				System.err.println(i+" "+ret);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

}
