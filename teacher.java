class teacher extends Thread
	{
		ClassRoom room;
		public teacher(ClassRoom cl)
		{
			room = cl;
		}
		
		public void run()
		{
			
			room.lectureGetIn();
			
			
			
			
			room.lectureSessionBegin();
			try {
				Thread.sleep(1000);
			}catch(InterruptedException e){}
			
			room.lectureGetOut();
			
			
		}

	}
