import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.locks.*;
public class UpDownPersonServer{
	final static int portPerson = 1235; // any number > 1024
	public static void main(String[] args){
		System.out.println("Server running...");
		Data data = new Data();
    try{
      ServerSocket servesock = new ServerSocket(portPerson);
      while (true) {
         // wait for a service request on port portSqrt
         Socket socket = servesock.accept();
         // start thread to service request
         new PersonUpDown(socket,data).start();
      }
   }catch (IOException e) {e.printStackTrace();}
  }
}
class PersonUpDown extends Thread{
  Socket socket;
  Data data;
  PersonUpDown(Socket s, Data d){socket = s; data = d;}
  public void run() {
  	try{
  		DataInputStream in = new DataInputStream(socket.getInputStream());
      DataOutputStream out = new DataOutputStream(socket.getOutputStream());
      int opt = in.readInt();
      if(opt == 0){ //upload
         Person p = new Person();
         p.readInputStream(in);
         data.add(p);
         out.writeBoolean(true);
         socket.close();
      }
      else if (opt == 1){ //download
      	String sname = in.readUTF();
      	ArrayList<Person> lst = data.retrieve(sname);
      	out.writeInt(lst.size());
      	for(int j = 0; j < lst.size();j++){
      		Person p = lst.get(j);
      		p.writeOutputStream(out);
      	}
      	socket.close();
      }
      else if(opt == 2)
      {
    	  ArrayList<Person> lst = data.retrieveall();
    	 // System.out.println(lst.size());
        	out.writeInt(lst.size());
        	for(int j = 0; j < lst.size();j++){
        		Person p = lst.get(j);
        		p.writeOutputStream(out);
        	}
        	socket.close();
      }
      else if(opt == 3)
      {
    	  ArrayList<Person> lst = data.retrieveoldest();
        	out.writeInt(lst.size());
        	for(int j = 0; j < lst.size();j++){
        		Person p = lst.get(j);
        		p.writeOutputStream(out);
        	}
        	socket.close();
      }
      else if(opt == 4)
      {
    	  int [] agebracket = new int[in.readInt()];
    	  
    	  for (int i = 0; i < agebracket.length; ++i) agebracket[i] = in.readInt();
    	  ArrayList<Person> lst = data.retrieveagebracket(agebracket);
        	out.writeInt(lst.size());
        	for(int j = 0; j < lst.size();j++){
        		Person p = lst.get(j);
        		p.writeOutputStream(out);
        	}
        	socket.close();
      }
    }
    catch (IOException e){}
 }
}
class Data{
	private ArrayList<Person> data = new ArrayList<Person>();
	private Lock lock = new ReentrantLock();
	void add(Person p){
		lock.lock();
		try{
			data.add(p);
		}finally{lock.unlock();}
	}
	boolean search(Person p){
		lock.lock();
		try{
			return data.contains(p);
		}finally{lock.unlock();}
	}
	ArrayList<Person> retrieve(String sname){
		lock.lock();
		try{
			ArrayList<Person> dt = new ArrayList<Person>();
			Person p = new Person("",sname,0); //use for search
			for(int j = 0; j < data.size();j++){
	      Person p1 = data.get(j);
				if(p1.equals(p)) dt.add(p1);
			}
			return dt;
		}finally{lock.unlock();}
	}
	ArrayList<Person> retrieveall(){
		lock.lock();
		try{
			ArrayList<Person> dt = new ArrayList<Person>();
			System.out.println(data.size());
			for(int j = 0; j < data.size();j++){
	      Person p1 = data.get(j);
				 dt.add(p1);
			}
			return dt;
		}finally{lock.unlock();}
	}
	
	ArrayList<Person> retrieveoldest(){
		lock.lock();
		try{
			int old = 0;
			int oldindex = 0;
			ArrayList<Person> dt = new ArrayList<Person>();
			Person p = new Person("","",0); //use for search
			
			for(int j = 0; j < data.size();j++){
	      Person p1 = data.get(j);
				if(p1.age() > old ) {
					old = p1.age();
					oldindex = j ;
				}
				
				
				}
			System.out.println(oldindex);
			Person pold = data.get(oldindex);
			dt.add(pold);
			
			
			return dt;
		}finally{lock.unlock();}
	}
	ArrayList<Person> retrieveagebracket(int [] age){
		lock.lock();
		try{
			ArrayList<Person> dt = new ArrayList<Person>();
			
			
			//use for search
			for(int j = 0; j < data.size();j++){
	      Person p1 = data.get(j);
	      for(int i =0 ; i< age.length;i++)
	      {
				if(p1.age()== age[i]) dt.add(p1);
		  }
		}
			return dt;
		}finally{lock.unlock();}
	}
	
}