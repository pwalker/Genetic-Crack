package mono;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class DebugMonoFitness {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
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
		
		MonoCipher cipher = new MonoCipher();
		
		MonoFitness f = new MonoFitness("plain.txt",cipher,cipherText);
		
		MonoCandidate c = new MonoCandidate("nefariouslybcdghjkmpqtvwxz");
		
		System.out.println(cipher.decrypt(c.getGenes(), cipherText));
		
		System.out.println(f.fitness(c));

	}

}
