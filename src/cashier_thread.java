
import java.util.concurrent.*;

	class cashier_thread extends Thread{
		
		public static long time = System.currentTimeMillis();

		public void msg(String m) {
			 System.out.println("["+(System.currentTimeMillis()-time)+"] "+getName()+": "+m);
		}
		private boolean cash;
		shared cashiers = new shared();
		static int customersPaid = 0;
		public BlockingQueue <customer_thread> cashLine;
		public BlockingQueue <customer_thread> creditLine;
		public cashier_thread(int a, BlockingQueue <customer_thread> c, BlockingQueue <customer_thread> cc) {
			this.cashLine = c;
			this.creditLine = cc;
			this.setName("Cashier : " + a);
			if(a%2 == 1){
				this.cash = false;
			}else {
				this.cash = true;
			}
		}
	@Override
		public void run() {
			assistCustomers();
		}
		
		
		public void help_customers(customer_thread c) {
			customersPaid++;
			c.setPriority(NORM_PRIORITY);
			c.paid = true;
		}
		
		public void assistCustomers() {
			msg("waiting for customers");
			while(customersPaid < cashiers.getcust()) {
				if(this.cash == true && cashLine.size()>0) {
					try {
						msg(cashLine.peek().getName() + " is paying with cash on " + this.getName());
						help_customers(cashLine.take());
					
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				else if(this.cash == false && creditLine.size() > 0)
					try {
						msg(creditLine.peek().getName() + " is paying with credit, on " + this.getName());
						help_customers(creditLine.take());
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			}
			if(customersPaid == cashiers.getcust()) {
				try {
					msg(this.getName() + " is waiting for closing.");
					Thread.sleep(120000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	
			}
		}
	}