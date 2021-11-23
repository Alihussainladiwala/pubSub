package messagequeue;

import java.util.List;

public class Subscriber {

	private final int ID;
	private final String name;
	private int consume_time;
	
	public Subscriber(int ID, String name, int consume_time) {
		this.ID = ID;
		this.name = name;
		this.consume_time = consume_time;
	}
	
	public int get_ID()
	{
		return ID;
	}
	
	public String get_name()
	{
		return name;
	}
	
	public int get_consume_time()
	{
		return consume_time;
	}
	
	public void set_consume_time(int consume_time)		// if want to change time of consumption for testing
	{
		this.consume_time = consume_time;
	}
	
	
	public void consume(String topic_name, String message)
	{
		System.out.println("Subscriber: " + name + " receives message " + message + " from Topic: "+topic_name);
        try {
			
        	Thread.sleep(consume_time);
		
        } catch (InterruptedException e) {
			
			e.printStackTrace();
		}
        System.out.println("Subscriber: " + name + " finishes consuming message " + message + " from Topic: "+topic_name );
	}
	
	
	public void receive_poll(String topic_name, List<String> messages)
	{
		System.out.println("Poll Subscriber: " + name + " receives polled messages from Topic: "+topic_name);
		
		if(messages == null) 
		{
			System.out.println("No new messages");
			return;
		}
		for(int i = 0; i<messages.size(); i++)
		{
			System.out.println(messages.get(i));
		}
	}
	
	
}
