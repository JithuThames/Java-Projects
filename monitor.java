class monitor extends Thread
	{
	ClassRoom room;	
	public monitor(ClassRoom cl)
	{
	room = cl;	
	}
	public void run() {
		try {
			Thread.sleep(2000);
		}catch(InterruptedException e){}
	room.monitor();
	
	}

	}