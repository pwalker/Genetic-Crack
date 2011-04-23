package alphabet;

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
		
		Fitness fitness = new Fitness("/home/peterw/Projects/Eclipse/GeneticCrack/top1000.txt",new MonoCipher(),cipherText);
		
		Mono population = new Mono(5000,fitness);

		Random rand = new Random();
		
		MonoGeneTool geneTool = new MonoGeneTool(rand);
		
		MonoManipulator manipulator = new MonoManipulator(rand);
		
		Overlord overlord = new Overlord(population, geneTool, manipulator);
		
		//while(!overlord.isDone()){
		for (int i=0; i<1000; i++){
			//System.err.println("running a generation...");
			int ret = overlord.runGeneration();
			
			// TODO debugging
			if (ret >= 350){
				System.err.println();
			}
		}
		//}
		
	}

}
