
import java.util.concurrent.*;
import java.util.*;

class ViewingStand{
	ArrayBlockingQueue<Long> Gallery;
	
	public ViewingStand(ArrayBlockingQueue<Long> vt){
		Gallery = vt; 
	}
	
	
	public void findSeat() {
		int remainingCapacity = Gallery.remainingCapacity(); 
		
		if (remainingCapacity > 0)
		{
			try {
			Gallery.put(Thread.currentThread().getId());
			System.out.println("Thread-  "+Thread.currentThread().getId()+"   Got seat");
			}catch(Exception e) {}
		}
		else {
			System.out.println("Thread-  "+Thread.currentThread().getId()+"   didn't get seat since Gallery is Full");
			
		}
	}
	
	public void leaveSeat() {
		
		if(Gallery.isEmpty())
		{
			System.out.println("Gallery is Empty");
		}
		else
		{
		long leftid =	Gallery.remove();
			System.out.println("Thread  "+leftid+"  left from Gallery");
		}
	}
	
	
}




public class MainClassMusem {
 public static void main(String [] args)
 {
	 	ArrayBlockingQueue<Long> VStand = new ArrayBlockingQueue<Long>(8);
	 	ViewingStand vs = new ViewingStand(VStand);
	 	Thread t1;
	 	Thread t2;
	 	Thread t3;
	 	//try {
	 	while(true) {
	 	t1 = new Seatfinder(vs);
	 	t2 = new leaveSeat(vs);
	 	t3 = new Seatfinder(vs);
	 	t1.start();
	 	t2.start();
	 	t3.start();
	 	
	 	//t1.join();
	 	//t2.join();
	 	try {
	 	t3.join();
	 	}catch(Exception e) {}
	 	}
	 	//}catch(Exception e) {}
	 	
		
 }	
	
}

class Seatfinder extends Thread{
	ViewingStand ga;
	public Seatfinder(ViewingStand vs) {
		ga = vs;
		
	}
	
	public void run() {
		try {
			Thread.sleep(1000);
		}catch(Exception e) {}
		ga.findSeat();
		try {
			Thread.sleep(1000);
		}catch(Exception e) {}
	}
}

class leaveSeat extends Thread{
	ViewingStand ga;
	public leaveSeat(ViewingStand vs) {
		ga = vs;
		
	}
	
	public void run() {
		try {
			Thread.sleep(160);
		}catch(Exception e) {}
		ga.leaveSeat();
		try {
			Thread.sleep(100);
		}catch(Exception e) {}
	}
}

