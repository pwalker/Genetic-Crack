package nqueen;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class NQFitnessTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testFitnessNQCandidate() {
		NQCandidate c1 = new NQCandidate("0 1 2 4 4 4 5 7 8 7");
		NQFitness nqf = new NQFitness();
		
		int fit = nqf.fitness(c1);
		assertTrue(fit == 24);
	}

}
