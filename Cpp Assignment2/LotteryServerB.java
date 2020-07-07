
import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.locks.*;
public class LotteryServerB{
	final static int portLotteryB = 1234;
	
	public static void main(String[] args){
		System.out.println("Lottery Server B running...");
		Data1 data = new Data1();
    try{
    	 
    	
      
      Socket clientA = null ;
      ServerSocket servesockB = new ServerSocket(portLotteryB);
      Ticketcount l = new Ticketcount();
      Thread t1;
      Thread t2;
      while (l.Ticketcount.get()< 40) {
    	  
         
         
       // if (socketA.isConnected())
        
        try {
         t1 = new TicketRecieverB(clientA,data);
         t1.start();
         t1.join();
         
         t2 = new TicketGeneratorB(servesockB,data,l);
         t2.start();
         t2.join();
       
         
         
         //socket = new Socket(InetAddress.getLocalHost(),portLotteryB);
    	 //DataInputStream in = new DataInputStream(socket.getInputStream());
         //DataOutputStream out = new DataOutputStream(socket.getOutputStream());
        }
        catch(Exception e) {}
         
         
      }
      System.out.println();
      System.out.println("ServerB Buffer Details");
      data.retrieveall();
      
      
   }catch (IOException e) {e.printStackTrace();}
  }
}
class TicketGeneratorB extends Thread{
  ServerSocket serversocketB ;Socket clientsocketA;
  Ticketcount l ;
  Data1 data;
  TicketGeneratorB(ServerSocket s,  Data1 d,Ticketcount i){serversocketB = s; data = d;l =i;}
  public void run() {
  	try{
//  		clients
  		LotteryCreator();
  		
  		
  	  	
  	  	

    }
    catch (Exception e){}
  	
 }
  void LotteryCreator() {
	  try {
		
		serversocketB.setSoTimeout(10000);
		ArrayList<Lottery> lst = data.generatelottery(l);
	  	Lottery p = lst.get(0);
	  	
	  	System.out.println("Creating New Lottery in Server B");
	  	Socket serverB = serversocketB.accept();
	  	data.add(p);
	    l.Ticketcount.addAndGet(1);
	    DataOutputStream out = new DataOutputStream(serverB.getOutputStream());
	    System.out.println("Sending New Lottery to Server A");
	  	p.writeOutputStream(out);
	  	
	  }catch(Exception e) {}
	}
  

}


class TicketRecieverB extends Thread{
	 Socket clientsocketA;
	  Data1 data;
	  TicketRecieverB(Socket s,  Data1 d){clientsocketA = s ;data = d;}
	  public void run() {
	  	try{
//	  		
	  		
	  		LotteryReceiver();
	  	  	
	  	  	

	    }
	    catch (Exception e){}
	  	
	 }
	  
	  void LotteryReceiver() {
		  try {
			  
		  
		  clientsocketA =  new Socket(InetAddress.getLocalHost(),1235);
		  System.out.println("Receiving new Lottery from Server A");
		  DataInputStream in = new DataInputStream(clientsocketA.getInputStream());
		  Lottery L = new Lottery();
		  
		  L.readInputStream(in);
		  
		 
		  data.add(L);
		  
		  }
		  catch(Exception e) {}
	  }
	}



class Data1{
	private ArrayList<Lottery> data = new ArrayList<Lottery>();
	private Lock lock = new ReentrantLock();
	void add(Lottery p){
		lock.lock();
		try{
			data.add(p);
		}finally{lock.unlock();}
	}
	
	
	
	ArrayList<Lottery> generatelottery(Ticketcount l){
		lock.lock();
		try{
			
			ArrayList<Lottery> dt = new ArrayList<Lottery>();
			String result = "";
			for(int i =0 ; i<= 5; i++)
			{
				 result = result.concat(Integer.toString( ThreadLocalRandom.current().nextInt(1,40)));
			
				 result = result.concat(" ");	 
			}
			Lottery p1 = new Lottery(result,"SerB"+l.Ticketcount.get(),"ServerB");
			
			dt.add(p1);
			
			
			return dt;
		}finally{lock.unlock();}
	}
	void retrieveall(){
		lock.lock();
		try{
			
			System.out.println();
			for(int j = 0; j < data.size();j++){
				Lottery p1 = data.get(j);
				System.out.println(p1);
			}
			
		}finally{lock.unlock();}
	}
	
	
}