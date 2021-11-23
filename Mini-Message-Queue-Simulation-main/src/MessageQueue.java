package messagequeue;

import java.util.HashMap;
import java.util.List;


/* 
 * Message Queue
 * create topic
 * Publish Message to appropriate topic
 * Add a subscriber to a topic - polling subscriber or fan-out subscriber
 * Simulate disconnect of a subscriber to a topic
 */
import java.util.UUID;



public class MessageQueue {
	
	
	private HashMap<String, TopicEndpoint> topic_endpoints;
	
	public MessageQueue() {
		
		this.topic_endpoints = new HashMap<>();
	}
	
	
	// 1. Create topic
	public Topic create_topic(String topic_name)
	{
		String topic_ID = UUID.randomUUID().toString();
		
		Topic topic = new Topic(topic_ID, topic_name);
		TopicEndpoint topic_endpoint = new TopicEndpoint(topic);
		
		topic_endpoints.put(topic_ID, topic_endpoint);
		
		System.out.println("Topic "+topic_name+ " has been created");
		
		return topic;
	}
	
	// 2. Add subscriber to specific topic
	public void add_subscriber(Subscriber subscriber, Topic topic)
	{
		TopicSubscriber new_subcriber = new TopicSubscriber(subscriber);
		
		topic.add_subscriber(new_subcriber);
		
		System.out.println("Subscriber "+subscriber.get_name() + " added to topic "+topic.get_name());
	}
	
	// 3. publish message to appropriate topic
	public void publish(String message, Topic topic)
	{
		topic.add_message(message);
		
		String topic_id = topic.get_ID();
				
		// create new thread to publish message 
		TopicEndpoint endpoint = topic_endpoints.get(topic_id);
		
		
		System.out.println("Message "+message+" published to topic: "+ topic.get_name());
		
		Runnable task = () -> { endpoint.publish_fan_out();};
		new Thread(task).start();
		
		
	}
	
	
	// 4. simulate a disconnect of a subscriber from a topic
	public void disconnect(Subscriber subscriber, Topic topic, int new_index)
	{
		
		 List<TopicSubscriber> all_subscribers = topic.get_all_subscribers();
		 
		 for(TopicSubscriber s : all_subscribers)
		 {
			 //System.out.println(s.get_subscriber().get_name() + "     " + subscriber.get_name());
			 
			 if((s.get_subscriber().get_name()).compareTo(subscriber.get_name()) == 0)
			 {
				 System.out.println("Subscriber "+subscriber.get_name()+" disconnected! Index re-set to: "+new_index);
				 
				 s.set_index(new_index);
				 
				 // create thread to start the subscriber's consumption of messages from new index 
				 TopicEndpoint endpoint = topic_endpoints.get(topic.get_ID());
				 
				 Runnable task = () -> endpoint.start_subscriber_spawn(s);
				 
				 new Thread(task).start();
				 
				 break;
			 }
		 }
	}
	
	// 5. add a polling subscriber
	public void add_poll_subscriber(Subscriber subscriber, Topic topic)
	{
		TopicSubscriber new_subcriber = new TopicSubscriber(subscriber);
		
		topic.add_poll_subscriber(subscriber.get_name(), new_subcriber);
		
		System.out.println("Poll Subscriber "+subscriber.get_name() + " added to topic "+topic.get_name());
	}
	
	// 6. poll the messages in specific topic to a specific subscriber
	public void poll(Subscriber subscriber, Topic topic)
	{
		TopicEndpoint endpoint = topic_endpoints.get(topic.get_ID());
		
		Runnable task = () -> { endpoint.respond_to_poll(subscriber.get_name());};
		new Thread(task).start();
		
	}
	
	public List<String> get_all_topics()
	{
		List<String> topics_names = new ArrayList<>();
		
		for(TopicEndpoint t : topic_endpoints)
		{
			topics_names.add(t.getTopic().getName());
		}
		
		return topics_names;
	} 
	
}
	
	
	
	
	
	
	
	

