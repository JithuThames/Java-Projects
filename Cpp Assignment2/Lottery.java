
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;
final public class Lottery {
	private String TicketNo;
	private String CreateName;
	private String ServerName;
	
  public Lottery(String fn,String sn, String a){
	  TicketNo = fn; CreateName = sn; ServerName = a;
  }
  public Lottery(){
	  TicketNo = null; CreateName = null; ServerName = null;
  }
  public String TicketNo(){return TicketNo;}
  public String CreateName(){return CreateName;}
  public String ServerName(){return ServerName;}
  public String toString(){
  	return TicketNo+" "+CreateName+" "+ServerName;
  }
  
  //======================================================
  //Methods used to read and write to streams over sockets
  public void writeOutputStream(DataOutputStream out){
  	try{
  	 out.writeUTF(TicketNo);
  	 out.writeUTF(CreateName);
  	 out.writeUTF(ServerName);
  	 
  	}catch(IOException e){e.printStackTrace();}
  }
  public void readInputStream(DataInputStream in){
  	try{
  	  TicketNo = in.readUTF();
  	CreateName = in.readUTF();
  	ServerName = in.readUTF();
  	}
  	catch(IOException e){e.printStackTrace();}
  }
}
class Ticketcount {
	AtomicInteger  Ticketcount = new AtomicInteger(0);
}

