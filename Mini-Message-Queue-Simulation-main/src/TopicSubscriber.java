package messagequeue;

import java.util.concurrent.atomic.AtomicInteger;

// One topic subscriber is created for every subscriber per topic
// represents the subscriber and its "state" i.e how much it has read wrt a topic
public class TopicSubscriber {

	private Subscriber subscriber;
	private AtomicInteger index;		// message index till which subscriber has received messages
	
	
	TopicSubscriber(Subscriber subscriber)
	{
		this.subscriber = subscriber;
		this.index = new AtomicInteger(0);
	}
	
	public Subscriber get_subscriber()
	{
		return this.subscriber;
	}
	
	public AtomicInteger get_index()
	{
		return index;
	}
	
	public void set_index(int new_index)
	{
		index.set(new_index);
	}
}
