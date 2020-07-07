import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.locks.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;
public class Server1{
	final static int portA = 1235;
	
	public static void main(String[] args){
		System.out.println(" Server 1 running...");
		Data data = new Data();
		
  
    	
      Thread t1 ;	
      Socket clientB = null ;
      try {
      ServerSocket servesockA = new ServerSocket(portA);
      
     
      while(data.IntCount.get()<10) {
    	 
        
    	 Socket serverA = servesockA.accept();
       
    	 t1 = new IntgerReader(serverA,data);
    	
    	 t1.start();
    	 
    	 t1.join();
         
    	 
      }
      }catch(Exception e) {}
      System.out.println();
      System.out.println("Server1 Buffer Details");
      
      
     
      data.retrieveall();
      
      
   }
  }


class IntgerReader extends Thread{
	  Socket socket;
	  Data data;
	  IntgerReader(Socket s, Data d){socket = s; data = d;}
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
	 
	class Data {
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