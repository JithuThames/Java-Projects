class students extends Thread
	{
		ClassRoom room;
		public students(ClassRoom cl)
		{
			room = cl;
		}
		
		public void run()
		{
			
				room.studentsGetIn();
				room.studentsSitDown();
				
				
				
				room.studentsLeave();
				//System.out.println("Students leaving :"+room.count);
			
		}
		
	}
