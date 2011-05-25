package mono;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.concurrent.LinkedBlockingQueue;

import basic.Candidate;
import basic.CandidateFeed;
import basic.Driver;
import basic.EvalBarrier;
import basic.Overlord;
import basic.Population;

public class MonoDriver extends Driver {

	public MonoDriver() {
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
				cipherText += s.nextLine().replaceAll(" ", "");
			}
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			System.exit(1);
		}
		
		this.fitness = new MonoFitness("top1000.txt",new MonoCipher(),cipherText);
		
		MonoEvaluator meval = new MonoEvaluator(fitness);
		this.eval = meval;
		this.geneTool = meval;

		this.b = new EvalBarrier();
		
		this.pop = new Population(500, eval, b, null);
		
		// initialize the population
		for (int i=0;i<Config.POPULATION_SIZE;i++){
			pop.add(new MonoCandidate());
		}
				
		this.overlord = new Overlord(pop, eval, geneTool, b, 4);
		
//		for (int i=0; i<Config.GENERATIONS; i++){
//			//System.err.println("running a generation...");
//			try {
//				overlord.runGeneration();
//				MonoCandidate bestCand = (MonoCandidate) overlord.getBest();
//				// TODO lets save our progress every 50 generations
//				//if (i % 50 == 0) {
//				//	population.savePopulation("population"+i+".csv");
//				//}
//				
//				System.err.printf("%s\t%s\t%s\t%s\n",i,overlord.populationFitness(),bestCand.getFitness(),bestCand.getGenes());
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
		
	}
	
	public MonoDriver(CandidateFeed feed, LinkedBlockingQueue<Candidate> outgoing) {
		this();
		this.pop.setFeed(feed);
		this.output = outgoing;
	}
	
	// just so you can run this by itself
	public static void main(String args[]){
		MonoDriver driver = new MonoDriver();
		driver.start();
	}

}
