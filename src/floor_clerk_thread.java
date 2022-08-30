
import java.util.concurrent.*;

class floor_clerk_thread extends Thread{
	
	
	public static long time = System.currentTimeMillis();

	 public void msg(String m) {
	 System.out.println("["+(System.currentTimeMillis()-time)+"] "+getName()+": "+m);
	 }	
	shared floorclerks = new shared();
	String floorClerkName = "";
	static int customersHelped = 0;
	public BlockingQueue <customer_thread> q = new LinkedBlockingQueue<customer_thread>();

	public floor_clerk_thread(int a, BlockingQueue <customer_thread> q) {
		floorclerks.incrementfc();
		setName("FC : " + a);
		this.q = q;
	}

	
	@Override
	public void run() {
			help_customers();
	}
	public void giveReceipt(customer_thread c){
		customersHelped ++;
		msg(c.getName() + " gets receipt from " + this.getName());
		c.receipt = true;
		floorclerks.incrementfc();
	}
	public void help_customers()  {
		while(customersHelped < floorclerks.getcust()) {
			while(q.size() == 0){
			}
			if(floorclerks.getfc() > 0) {
					this.floorclerks.decrementfc();
					try {
						this.giveReceipt(q.take());
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			}
			try {Thread.sleep((long)5);}catch(InterruptedException e) {}//this is to remind threads to check statement
		}
		if(customersHelped == floorclerks.getcust()) {
			msg(this.getName() + " is waiting for closing.");
			try {Thread.sleep(120000);}catch(InterruptedException e) {}
		}
	}
}