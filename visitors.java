class visitors extends Thread
	{

		ClassRoom cl;
		public visitors (ClassRoom room)
		{
			cl = room;
		}
		public void run()
		{
			
			cl.visitorsGetIn();
			
			
			
			cl.visitorsSitDown();
			
		
			
			cl.visitorsLeave();
			
			
		}
		
	}
