import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.locks.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;
public class Server2{
	final static int portA = 1234;
	
	public static void main(String[] args){
		System.out.println("Server 2 running...");
		Data1 data = new Data1();
		
  
    	
      Thread t1;
      Socket clientB = null ;
      try {
      ServerSocket servesockA = new ServerSocket(portA);
      
      while (data.IntCount.get()<10) {
    	  
        
    	 Socket serverA = servesockA.accept();
    	 t1 = new IntgerReaderB(serverA,data);
     	
    	 t1.start();
    	 
    	 t1.join();
       
    	 
         
         
      }
      }catch(Exception e) {}
      System.out.println();
      System.out.println("Server2 Buffer Details");
      
      
     
      data.retrieveall();
      
      
   }
  }


class IntgerReaderB extends Thread{
	  Socket socket;
	  Data1 data;
	  IntgerReaderB(Socket s, Data1 d){socket = s; data = d;}
	  public void run() {
	  	try{
	  		 DataInputStream in = new DataInputStream(socket.getInputStream());
	      
	         int opt = in.readInt();
	         System.out.println("Adding item to Buffer: "+opt);
	      	 data.lst.add(opt);
	      	
	      	 data.IntCount.addAndGet(1);
	         socket.close();
	      }

	
	  	catch(Exception e) {}
	  }
	  }
	 
	class Data1 {
		ArrayList<Integer> lst = new ArrayList<Integer>();
		AtomicInteger  IntCount = new AtomicInteger(0);
		private Lock lock = new ReentrantLock();
		
		void retrieveall(){
			lock.lock();
			try{
				
				
				for(int j = 0; j < lst.size();j++){
		        System.out.println (lst.get(j));
					 
				}
				
			}finally{lock.unlock();}
		}
	}