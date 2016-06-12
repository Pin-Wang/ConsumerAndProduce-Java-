package demo;

import java.util.Vector;

public class Main {

	public static void main(String[] args) {
		Vector<Integer> pool=new Vector<Integer>();
		Producer producer=new Producer(pool, 4);
		Consumer consumer=new Consumer(pool);
		new Thread(producer).start();
		new Thread(consumer).start();
	}

}

//生产者
class Producer implements Runnable{
	private Vector pool;
	private Integer size;
	
	public Producer(Vector pool, Integer size) {
		this.pool = pool;
		this.size = size;
	}
	@Override
	public void run() {
		for(int i=0;i<7;i++){
			try {
				System.out.println("produce "+i);
				produce(i);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	private void produce(int i) throws InterruptedException{
		while(pool.size()==size){
			synchronized (pool) {
				System.out.println("pool is full Producer is waiting,size is "+pool.size());
				pool.wait();
			}
		}
		synchronized (pool) {
			pool.add(i);
			pool.notifyAll();
		}
	}
}


//消费者
class Consumer implements Runnable{
	private Vector pool;
	public Consumer(Vector pool) {
		this.pool = pool;
	}
	
	@Override
	public void run() {
		for(int i=0;i<7;i++){
			try {
				System.out.println("consume "+i);
				consume();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	private void consume() throws InterruptedException{
		while(pool.isEmpty()){
			synchronized (pool) {
				System.out.println("pool is empty Consumer is waiting,size is "+pool.size());
				pool.wait();
			}
		}
		synchronized (pool) {
			pool.notifyAll();
			pool.remove(0);
			
		}
	}
}
