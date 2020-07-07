
import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;
class ClassRoom {

	int classindex;
	String [] classrooms = {"W201","W202","W101","JS101"};
	int [] classsize = {60,60,20,30};
	String [] lecturename = {"Osama","Barry","Faheem","Alex","Aqeel","Waseem"};
	String currentclass="";
	String currentlecture="          ";
	
	Semaphore [] classlock = new Semaphore[]{
		new  Semaphore(1,true),
		new Semaphore(1,true), new Semaphore(1,true), new Semaphore(1,true)
	};
	Semaphore [] lecturelock = new Semaphore[]{new  Semaphore(1,true),
			new Semaphore(1,true), new Semaphore(1,true), new Semaphore(1,true),new Semaphore(1,true),new Semaphore(1,true)};
	AtomicInteger  studentcount = new AtomicInteger();
	AtomicInteger visitorcount = new AtomicInteger();
	AtomicInteger  studentsitcount = new AtomicInteger();
	AtomicInteger  visitorsitcount = new AtomicInteger();
	
	
	
	int  currentlectureindex   ;
	
	AtomicInteger  lecturesinclass = new AtomicInteger();
	
	boolean countmatch ;
	boolean session;
	int currentclasssize ;
	public ClassRoom(int index)
	{	session = false;
		classindex = index;
		currentclass = classrooms[index];
		currentclasssize = classsize[index];
		countmatch = false;
	}
	
	
	public void studentsGetIn()
	{
		//System.out.println(studentcount[classindex].get());
		try {
		classlock[classindex].acquire();
		} catch (InterruptedException e) {}
		
		if(currentclasssize > studentcount.get() )
		{
			int incrementcount = getRandomNumberInRange(0 ,currentclasssize-studentcount.get());
			studentcount.getAndAdd(incrementcount);
		}
		classlock[classindex].release();
	}
	
	public void studentsSitDown()
	{
		
		try {
			classlock[classindex].acquire();
			} catch (InterruptedException e) {}
			if(studentcount.get() > studentsitcount.get() )
			{
				//int test = studentcount.get();
				//studentsitcount.set(test);
				//System.out.println(studentcount.get()  + "  " + studentsitcount.get() );
				int incrementcount = getRandomNumberInRange((studentcount.get()-studentsitcount.get())-1,studentcount.get()-studentsitcount.get());
				studentsitcount.getAndAdd(incrementcount);
			}
			classlock[classindex].release();
			//System.out.println(studentcount.get()  + "  " + studentsitcount.get() );
	}
	
	public void studentsLeave()
	{
		try {
			classlock[classindex].acquire();
			} catch (InterruptedException e) {}
			if(studentcount.get() > 0 && studentsitcount.get() > 0 )
			{
				int decrementcount = getRandomNumberInRange(0,studentsitcount.get());
				//System.out.println("HistudentsSitDown "+studentcount.get());
				studentcount.getAndAdd(-decrementcount);
				studentsitcount.getAndAdd(-decrementcount);
			}
			classlock[classindex].release();
		
	}
	//Visitors
	public void visitorsGetIn()
	{
		
		try {
		classlock[classindex].acquire();
		} catch (InterruptedException e) {}
		if(5 > visitorcount.get() )
		{
			int incrementcount = getRandomNumberInRange(0, 5 - visitorcount.get());
			visitorcount.getAndAdd(incrementcount);
			
		}
		classlock[classindex].release();
	}
	
	public void visitorsSitDown()
	{
		try {
			classlock[classindex].acquire();
			} catch (InterruptedException e) {}
			if(visitorcount.get() > visitorsitcount.get() )
			{
				
				int incrementcount = getRandomNumberInRange((5 - visitorcount.get()), (5 - visitorcount.get())+1);
				visitorsitcount.getAndAdd(incrementcount);
				//System.out.println("HistudentsSitDown "+visitorsitcount.get());
			}
			classlock[classindex].release();
		
	}
	
	public void visitorsLeave()
	{
		try {
			classlock[classindex].acquire();
			} catch (InterruptedException e) {}
			if(visitorcount.get() > 0 && visitorsitcount.get() > 0 )
			{
				int deccount = getRandomNumberInRange(0,  visitorcount.get()+1);
				visitorcount.getAndAdd(-deccount);
				visitorsitcount.getAndAdd(-deccount);
			}
			classlock[classindex].release();
		
	}
	//lecture 
	public void lectureGetIn() {
		//System.out.println("lectureGetIn  classlock "+classlock[classindex].availablePermits());
		//System.out.println("lectureGetIn lecturelock  "+lecturelock[currentlectureindex].availablePermits());
		do {
			currentlectureindex = getRandomNumberInRange(0,5);
			lecturesinclass.set(currentlectureindex);
			currentlecture = lecturename[currentlectureindex];
			
			
			//System.out.println(currentlectureindex+ "" + lecturelock[currentlectureindex].availablePermits());
		}while(lecturelock[currentlectureindex].availablePermits() == 0 );
		
		try {
			if(lecturelock[currentlectureindex].availablePermits()==1)
			lecturelock[currentlectureindex].acquire();
			
			
		}catch(InterruptedException e) {
			
		}
		
	}
	
	public void lectureSessionBegin() {
		//System.out.println("lectureSessionBegin  classlock "+classlock[classindex].availablePermits());
		//System.out.println("lectureSessionBegin lecturelock  "+lecturelock[currentlectureindex].availablePermits());
		//System.out.println(lecturelock[currentlectureindex].availablePermits());
		if(lecturelock[currentlectureindex].availablePermits()==0 
				&& visitorcount.get() == visitorcount.get()
				&& studentcount.get()== studentsitcount.get()
				)
		{
			
			try {
			if (classlock[classindex].availablePermits()==1)
			classlock[classindex].acquire();
			
			session = true;
			
			System.out.println();
			}catch(InterruptedException e) {}
			
			
		}
		
		
		
	}
	
	public void lectureGetOut()
	{
		//System.out.println("lectureGetOut  classlock "+classlock[classindex].availablePermits());
		//System.out.println("lectureGetOut lecturelock  "+lecturelock[currentlectureindex].availablePermits());
		if(lecturelock[currentlectureindex].availablePermits()==0 && classlock[classindex].availablePermits()==0 )
		{
			classlock[classindex].release();
			lecturelock[currentlectureindex].release();
			session= false;
			currentlecture="          ";
		}
		
		
	}
	
	public void monitor() {
		
		
		//System.out.println("monitor  classlock "+classlock[classindex].availablePermits());
		//System.out.println("monitor lecturelock  "+lecturelock[currentlectureindex].availablePermits());
		
		try {classlock[classindex].acquire();
			
		}catch(InterruptedException e) {}
			System.out.println("");
			String sessionstr =  currentlecture=="          "?"      ":String.valueOf(session); 
			//if(session == true)
			System.out.println(classrooms[classindex] +"           "+currentlecture+"           "+sessionstr+"           "+studentsitcount.get()+"           "+Math.abs(visitorsitcount.get()));
			//if (session == false)
			//	System.out.println(classrooms[classindex] +"    "+lecturename[currentlectureindex]+"    "+session+"    "+studentsitcount.get()+"    "+Math.abs(visitorsitcount.get()));
			classlock[classindex].release();
		
		
	}
	
	
	private static int getRandomNumberInRange(int min, int max) {

		if (min >= max) {
			throw new IllegalArgumentException("max must be greater than min");
		}

		Random r = new Random();
		return r.nextInt((max - min) + 1) + min;
	}
	
}

	 
	 
	
