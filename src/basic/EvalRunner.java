package basic;

import java.util.concurrent.LinkedBlockingQueue;

public class EvalRunner extends Thread {

	private Evaluator eval;
	private LinkedBlockingQueue<Candidate> work;
	private EvalBarrier barrier;
	
	EvalRunner (Evaluator eval, LinkedBlockingQueue<Candidate> work, EvalBarrier barrier) {
		this.work = work;
		// TODO do I need to make a copy of the fittor?
		this.eval = eval;
		this.barrier = barrier;
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
				
				// increment our barrier
				this.barrier.inc();
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
