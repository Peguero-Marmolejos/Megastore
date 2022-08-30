
import java.util.concurrent.*;

class shared{
		volatile static int fc = 0, customers = 0;
		public synchronized int  incrementfc() {
			return fc++;
		}
		public synchronized int decrementfc() {
			return fc--;
		}
		public synchronized int incrementcust() {
			return customers++;
		}
		public synchronized int decrementcust() {
			return customers--;
		}
		public synchronized int getfc() {
			return fc;
		}
		public synchronized int getcust() {
			return customers;
		}
}

public class Megastore {
	public static customer_thread[] customers = new customer_thread[12];
	public static floor_clerk_thread[] floor_clerks = new floor_clerk_thread[2];
	public static cashier_thread[] cashiers = new cashier_thread[2];

	public static  void main(String [] args) {
		BlockingQueue<customer_thread> f = new LinkedBlockingQueue<customer_thread>();
		BlockingQueue <customer_thread> c = new LinkedBlockingQueue<customer_thread>();
		BlockingQueue <customer_thread> cc = new LinkedBlockingQueue<customer_thread>();
	    
	    for (int i = 0; i < 12; i++) {
		      customers[i] = new customer_thread(i+1, f, c, cc);
		}
	    for (int i = 0; i < 2; i++) {
		      floor_clerks[i] = new floor_clerk_thread(i+1, f);
		      cashiers[i] = new cashier_thread(i+1, c, cc);
		}
	    for (int i = 0; i < 12; i++) {
		      customers[i].start();
		}
	    for (int i = 0; i < 2; i++) {
		      floor_clerks[i].start();
		      cashiers[i].start();
		 }
}}
