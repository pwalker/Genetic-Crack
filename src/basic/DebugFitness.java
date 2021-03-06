package basic;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import mono.MonoCandidate;
import mono.MonoCipher;
import mono.MonoFitness;

public class DebugFitness {

	/**
	 * @param args
	 */
	@SuppressWarnings("unused")
	public static void main(String[] args) {
		//String plaintext = "holmes had been seated for some hours in silence with his long thinback curved over a chemical vessel in which he was brew ing aparticularly malodorous product his head was sunk upon his breast andhe looked from my point of view like a strange lankbird with dull grayplumage and a black top knot";
		//String cipherText = "UGBCR MUNAE RRDMR NPRAI GKMGC RUGQK MSDMS BRDFR VSPUU SMBGD OPUSD ENFYF QKTRA GTRKN FURCS FNBTR MMRBS DVUSF UURVN MEKRV SDONH NKPSF QBNKB XCNBG AGKGQ MHKGA QFPUS MURNA VNMMQ DYQHG DUSME KRNMP NDAUR BGGYR AIKGC CXHGS DPGIT SRVBS YRNMP KNDOR BNDYE SKAVS PUAQB BOKNX HBQCN ORNDA NEBNF YPGHY DGP";

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
		String ptext = cipher.decrypt("nefariousbcdghjklmpqtvwxyz", cipherText);
		System.out.println(ptext);
		
		Fitness fitness = new MonoFitness("words.txt",cipher,cipherText);
		System.out.println(fitness.fitness(new MonoCandidate("nefariousbcdghjklmpqtvwxyz")));
	}

}
