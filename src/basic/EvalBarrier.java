package basic;

import java.io.Serializable;

public class EvalBarrier implements Serializable {

	private static final long serialVersionUID = 1L;
	int current;
	int max;
	
	public EvalBarrier() {
		this.current = 0;
		this.max = 0;
	}

	public synchronized void inc(){
		current++;
		//System.err.println("incremented");
		//System.err.println(this.current + "/" + this.max);
		if (this.current >= this.max) {
			//System.err.println("wake up!");
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
				//System.err.println("waiting");
				this.wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
