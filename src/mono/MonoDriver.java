package mono;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;

import basic.EvalBarrier;
import basic.Fitness;
import basic.GeneTool;
import basic.Overlord;
import basic.Population;

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
		
		//String cipherText = "PGDAH EINPE MACAT AFZEM XQHAM AUEPQ HAIGD JRQAM JMGTM EDQHE QZAQA MDOFA ZQHAD OFODR DEDGR FQGBQ ODAOQ QGGNQ GIGSA MQHAA FQOMA FAUXG MNIOQ XPRLU EXPXP QADEB QAMQH AJMGT MEDUE PUMOQ QAFEL RFIHG BDOQH EINAM PEIQR ECCXU AFQQG FAUXG MNEFZ QMOAZ OQGRQ NEQOA HEBFA MOFIX LAMJR FNPGD ATAAN PEMAW RPQTA ANOAM QHEFG QHAMP";
		//String cipherText = "UGBCR MUNAE RRDMR NPRAI GKMGC RUGQK MSDMS BRDFR VSPUU SMBGD OPUSD ENFYF QKTRA GTRKN FURCS FNBTR MMRBS DVUSF UURVN MEKRV SDONH NKPSF QBNKB XCNBG AGKGQ MHKGA QFPUS MURNA VNMMQ DYQHG DUSME KRNMP NDAUR BGGYR AIKGC CXHGS DPGIT SRVBS YRNMP KNDOR BNDYE SKAVS PUAQB BOKNX HBQCN ORNDA NEBNF YPGHY DGP";
		// this should be 208 with words.txt
		// 94 with top1000
		
		String cipherText = "";
		try {
			Scanner s = new Scanner(new File("cipher.txt"));
			while (s.hasNextLine()){
				cipherText += s.nextLine();
			}
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			System.exit(1);
		}
		
		Fitness fitness = new MonoFitness("words.txt",new MonoCipher(),cipherText);
		
		MonoEvaluator eval = new MonoEvaluator(fitness);
		
		// setup our threadpool barrier
		EvalBarrier b = new EvalBarrier();
		
		Population population = new Population(Config.POPULATION_SIZE, eval, b);
		
		// initialize the population
		for (int i=0;i<Config.POPULATION_SIZE;i++){
			population.add(new MonoCandidate());
		}
		
		//Random rand = new Random();
		
		// our evaluator was a good place for these almost static methods
		GeneTool geneTool = eval;
		
		Overlord overlord = new Overlord(population, eval, geneTool, b, 4);
		
		for (int i=0; i<Config.GENERATIONS; i++){
			//System.err.println("running a generation...");
			try {
				overlord.runGeneration();
				MonoCandidate bestCand = (MonoCandidate) overlord.getBest();
				// TODO lets save our progress every 50 generations
				//if (i % 50 == 0) {
				//	population.savePopulation("population"+i+".csv");
				//}
				
				System.err.printf("%s\t%s\t%s\t%s\n",i,overlord.populationFitness(),bestCand.getFitness(),bestCand.getGenes());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

}
