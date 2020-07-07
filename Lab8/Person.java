import java.io.*;
final public class Person {
	private String fName;
	private String sName;
	private int age;
  public Person(String fn,String sn, int a){
    fName = fn; sName = sn; age = a;
  }
  public Person(){
  	fName = null; sName = null; age = 0;
  }
  public String fName(){return fName;}
  public String sName(){return sName;}
  public int age(){return age;}
  public String toString(){
  	return sName+" "+fName+" "+age;
  }
  public boolean equals(Object ob){ //equality based on surname only
  	if(!(ob instanceof Person)) return false;
  	Person p = (Person)ob;
  	return sName.equals(p.sName);
  }
  //======================================================
  //Methods used to read and write to streams over sockets
  public void writeOutputStream(DataOutputStream out){
  	try{
  	 out.writeUTF(fName);
  	 out.writeUTF(sName);
  	 out.writeInt(age);
  	}catch(IOException e){e.printStackTrace();}
  }
  public void readInputStream(DataInputStream in){
  	try{
      fName = in.readUTF();
      sName = in.readUTF();
      age = in.readInt();
  	}
  	catch(IOException e){e.printStackTrace();}
  }
}