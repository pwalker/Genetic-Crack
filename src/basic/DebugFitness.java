package basic;

public class DebugFitness {

	/**
	 * @param args
	 */
	@SuppressWarnings("unused")
	public static void main(String[] args) {
		String plaintext = "holmes had been seated for some hours in silence with his long thinback curved over a chemical vessel in which he was brew ing aparticularly malodorous product his head was sunk upon his breast andhe looked from my point of view like a strange lankbird with dull grayplumage and a black top knot";
		String cipherText = "UGBCR MUNAE RRDMR NPRAI GKMGC RUGQK MSDMS BRDFR VSPUU SMBGD OPUSD ENFYF QKTRA GTRKN FURCS FNBTR MMRBS DVUSF UURVN MEKRV SDONH NKPSF QBNKB XCNBG AGKGQ MHKGA QFPUS MURNA VNMMQ DYQHG DUSME KRNMP NDAUR BGGYR AIKGC CXHGS DPGIT SRVBS YRNMP KNDOR BNDYE SKAVS PUAQB BOKNX HBQCN ORNDA NEBNF YPGHY DGP";

		MonoCipher cipher = new MonoCipher();
		String ptext = cipher.decrypt("nefariouslybcdghjkmpqtvwxz", cipherText);
		System.out.println(ptext);
		
		Fitness fitness = new MonoFitness("top1000.txt",cipher,cipherText);
		System.out.println(fitness.fitness(new MonoCandidate("nefariouslybcdghjkmpqtvwxz")));
	}

}
