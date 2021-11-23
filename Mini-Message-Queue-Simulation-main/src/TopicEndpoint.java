package messagequeue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/* handles all operations wrt a topic 
		*fanning out/ publishing messages to subscribers
*/ 

public class TopicEndpoint {
	
	private Topic topic;
	private HashMap<Integer, SubscriberSpawn> subscriber_spawns;
	
	
	
	
	public TopicEndpoint(Topic topic)
	{
		this.topic = topic;
		this.subscriber_spawns = new HashMap<>();
	}
	
	
	
	
	public void publish_fan_out()
	{
		for(TopicSubscriber t: topic.get_all_subscribers())
		{
			start_subscriber_spawn(t);
		}
	}
	
	public void start_subscriber_spawn(TopicSubscriber topic_subscriber)
	{
		Integer subscriber_ID = topic_subscriber.get_subscriber().get_ID();
		
		
		if( !subscriber_spawns.containsKey(subscriber_ID))
		{
			
			 SubscriberSpawn spawn = new SubscriberSpawn(topic, topic_subscriber);
			 
			 subscriber_spawns.put(subscriber_ID, spawn);
				
			 Thread t = new Thread(spawn);
			 t.start();
			 
			 //new Thread(spawn).start();
		}
		
		SubscriberSpawn spawn = subscriber_spawns.get(subscriber_ID);
		
		spawn.initiate_sending();
		
	}
	
	public void respond_to_poll(String subscriber_name)
	{
		
		TopicSubscriber topic_subscriber = topic.get_all_poll_subscribers().get(subscriber_name);
		
		synchronized (topic_subscriber) 
		{
			List<String> polled_messages = null;
			
			int curr_index = topic_subscriber.get_index().get();
			
			int n = topic.get_all_messages().size();
			
			if( curr_index < n)		// since new messages may not have been published since the previous poll done by subscriber
			{
				polled_messages =  new ArrayList<>(topic.get_all_messages().subList(curr_index, n));
				
				topic_subscriber.set_index(n);	// n is the index from which messages will be sent in the next poll
			}
			
			
			topic_subscriber.get_subscriber().receive_poll(topic.get_name(), polled_messages);
			
		}
		
	}
}
