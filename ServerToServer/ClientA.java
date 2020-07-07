import java.io.*;
import java.net.*;
import java.util.*;
public class ClientA {
	final static int portA = 1235;
	final static int portB = 1234;
  public static void main(String[] args) {
   int i =0;
   while(i<10)
   {
	   int x = (int)(Math.random()*((100-0)+1))+0;
   
	   write(x);
	   i++;
   }
   
   
 }
 static void write(int p){
 	try{
    Socket socketA, socketB;
    socketA = new Socket(InetAddress.getLocalHost(),portA);
    DataOutputStream outA = new DataOutputStream(socketA.getOutputStream());
    
    socketB= new Socket(InetAddress.getLocalHost(),portB);
    DataOutputStream outB = new DataOutputStream(socketB.getOutputStream());	
   
    System.out.println("Writing Element to Buffer: "+p);
    
    outA.writeInt(p); //send upload option
    socketA.close();
    
    outB.writeInt(p);
    socketB.close();
    
    
   } catch (IOException e) {System.out.println(e);}
 }
}