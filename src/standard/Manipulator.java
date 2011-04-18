package standard;

import java.util.Collection;

import alphabet.MonoCandidate;

public interface Manipulator {

	public Collection<MonoCandidate> select(Population population, double percentage);
	
	public Collection<MonoCandidate> select(Population population, int n);
	
	public void fillPopulation(Population population);

	public void crossOver(Population population, GeneTool crossover);

	public void mutate(Population population, GeneTool geneTool);
	
}
