package basic;

import java.util.concurrent.LinkedBlockingQueue;

public class EvalRunner implements Runnable {

	private Evaluator eval;
	private LinkedBlockingQueue<Candidate> work;
	
	EvalRunner (Evaluator eval, LinkedBlockingQueue<Candidate> work) {
		this.work = work;
		// TODO do I need to make a copy of the fittor?
		this.eval = eval;
	}
	
	@Override
	public void run() {
		while (true) {
			// get some work, this should block
			Candidate next;
			try {
				next = work.take();
				
				// do the work
				this.eval.evaluate(next);
				
				// let people know
				work.notifyAll();
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
