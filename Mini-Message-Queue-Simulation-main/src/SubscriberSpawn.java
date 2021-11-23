package messagequeue;

// for every subscriber to a topic - a subscriber thread is spawned so that the messages can be sent parallely across multiple subscribers
public class SubscriberSpawn implements Runnable{
	
	private TopicSubscriber topic_subscriber;
	private Topic topic;
	
	public SubscriberSpawn(Topic topic, TopicSubscriber topic_subscriber)
	{
		this.topic = topic;
		this.topic_subscriber = topic_subscriber;
	}
	
	
	public void run()
	{
		synchronized (topic_subscriber) {
			
			do {
				
				
				int curr_index = topic_subscriber.get_index().get(); // index of messages till which subscriber has read in this topic
				
				while( curr_index >= topic.get_all_messages().size())
				{
					try {
						topic_subscriber.wait();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} 	// go to sleep since there is nothing to read
				}
				
				// there is something to read
				
				String message = topic.get_all_messages().get(curr_index);
				
				topic_subscriber.get_subscriber().consume(topic.get_name(), message);
				
				// update index to which subscriber has read ONLY IF there has been no disconnect i.e curr_index has changed 
				topic_subscriber.get_index().compareAndSet(curr_index, curr_index+1);
				
			} while (true);
		
			
		}
		
	}
	
	
	public void initiate_sending()		
	{
		synchronized (topic_subscriber) {
			
			topic_subscriber.notify();
		}
	}
		
	
	
	
}
