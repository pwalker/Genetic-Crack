package basic;

public class EvalBarrier {

	int current;
	int max;
	
	public EvalBarrier() {
		this.current = 0;
		this.max = 0;
	}

	public synchronized void inc(){
		current++;
		if (this.current >= this.max) {
			notifyAll();
		}
	}
	
	public synchronized void newMax(int max){
		this.max = max;
	}
	
	public synchronized void reset(int max){
		this.current = 0;
		this.newMax(max);
	}

	public synchronized void pause() {
		while (current < max) {
			try {
				this.wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
