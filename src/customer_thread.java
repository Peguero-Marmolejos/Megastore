import java.util.*;
import java.util.concurrent.*;

public class customer_thread extends Thread{
	
	
	public static long time = System.currentTimeMillis();

	 public void msg(String m) {
	 System.out.println("["+(System.currentTimeMillis()-time)+"] "+getName()+": "+m);
	 }
	
	
	
	
	shared customerCount = new shared();
	customer_thread cafeteria [];
	public boolean receipt = false, cash, paid = false;
	public BlockingQueue <customer_thread> floorClerkNeeded;
	public BlockingQueue <customer_thread> cashierNeeded;
	public BlockingQueue <customer_thread> crediterNeeded;
	public static int cafeteriaPop;
	
	
	public customer_thread(int customer_name, BlockingQueue<customer_thread> fc, BlockingQueue<customer_thread> cash, BlockingQueue<customer_thread> credit) {
		customerCount.incrementcust();
		this.floorClerkNeeded = fc;
		this.cashierNeeded = cash;
		this.crediterNeeded = credit;
		this.setName("Customer " + customer_name);
	}
	
	@Override
	public void run() {
	
		
		//enters and looks around
		looking_around();
		//deciding whether to purchase or not
		decide_what_to_get();
		//goes to floor clerk
		get_receipt();
		//pays for item
		pay_for_item(this.receipt);
	
		//take a break in the cafeteria
		cafeteriaBreak(paid, cafeteria);
		
	}
	public void looking_around() {
		try{
			//System.out.println("");
			msg( this.getName() + " has entered the store.");
			Thread.sleep((long)(Math.random() * 1000));
			msg(this.getName() + " has found something they might want to buy!");
		}catch(InterruptedException e) {
			System.out.println("Interrupted Exception was caught.");
		}
	}
	public void decide_what_to_get() {
		msg(this.getName() + " is deciding whether they want to buy the item or not ");
		Thread.yield();
		Thread.yield();
	}
	public void get_receipt(){
		//goes to floorclerk if available and sets receipt to true, 
		msg(this.getName() + " is added to the floorclerk queue !");
		floorClerkNeeded.add(this);
		
		while(this.receipt == false) {
			//otherwise BW(sleep) FCFS order
		}
		msg(this.getName() + " is ready to pay!");
	}

	public void pay_for_item(boolean a) {
		if(a == true) {
			//System.out.println(this.getPriority() + " is old priority of " + this.getName());
			Random ran = new Random();
			int choser = ran.nextInt(100);
			if(choser%2 == 0) {
				//System.out.println(this.getName() + " is paying with cash.");
				this.cash = true;
				try {
					Thread.sleep(500);
					this.setPriority(MAX_PRIORITY);
					//System.out.println(this.getPriority() + " is new priority of " + this.getName());
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				cashierNeeded.add(this);
			}else {
				this.cash = false;
				try {
					Thread.sleep(500);
					this.setPriority(MAX_PRIORITY);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				crediterNeeded.add(this);
				while(this.paid == false) {}
			}
		}	
	}
	
	public void cafeteriaBreak(boolean a, customer_thread args[]) {
		args= new customer_thread[customerCount.getcust()+1]; 
		if(a == true) {
			
			msg(this.getName() + " has finished shopping and is in the cafeteria. ");
			cafeteriaPop++;// population in cafeteria
			String str = this.getName();
			String number = str.substring(str.length()-2, str.length());
			int integer = Integer.parseInt(number.trim());
			System.out.println(integer);
			
			args[integer] = this;
		}
		while(cafeteriaPop <customerCount.getcust()) {}
		if(cafeteriaPop == customerCount.getcust()) {
			exit(args);
		
		
		
	}
		
}
	
	public void exit(customer_thread args [] ) {
		for(int i = customerCount.getcust(); i>1; i--) {
			if(args[i-1].isAlive()) {
			try {
					msg("is joing " + args[i-1].getName());
					args[i].join();
				}
			catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	}
}
	
