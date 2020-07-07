import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;


public class ClassRoomMain {
	private static int getRandomNumberInRange(int min, int max) {

		if (min >= max) {
			throw new IllegalArgumentException("max must be greater than min");
		}

		Random r = new Random();
		return r.nextInt((max - min) + 1) + min;
	}
	public static void main(String args[]) {
		System.out.println("=======================================================================================");
		System.out.println("Class  "+"         "+"Lecture"+"         "+"Session"+"    Students    "+"  Visitors  " );
		System.out.println("=======================================================================================");
		Thread t1 = new Thread();
		Thread t2 = new Thread();
		Thread t3 = new Thread();
		Thread t4 = new Thread();
		Thread t5 = new Thread();
		Thread t6 = new Thread();
		while(true) {
			ClassRoom room = new ClassRoom(getRandomNumberInRange(0,3));
			t1 = new students(room);
			t6 = new teacher(room);
			t4 = new students(room);
			t2 = new teacher(room);
			t5= new visitors(room);
			t3 = new monitor(room);
			//t5.start();
			t1.start();
			t3.start();
			t2.start();
			//t6.start();
			t4.start();
			t5.start();
			
			
			
			try {
				//t1.join();
				//t2.join();
				t3.join();
				//t4.join();
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		
	}
	
}
}