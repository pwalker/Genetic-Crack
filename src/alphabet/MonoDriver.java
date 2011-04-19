package alphabet;

import java.util.Random;
import java.util.Scanner;

public class MonoDriver {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// Read in plaintext from stdin
		Scanner s = new Scanner(System.in);
		String plainText = "";
		while(s.hasNextLine()){
			plainText += s.nextLine();
		}
		
		Fitness fitness = new Fitness(new MonoCipher(),plainText);
		
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
			if (ret >= 73){
				break;
			}
		}
		//}
		
	}

}
