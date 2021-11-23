package messagequeue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Topic {

	private String ID;
	private String name;
	private List<String> messages;
	private List<TopicSubscriber> subscribers;
	private Map<String, TopicSubscriber> poll_subscribers;
	
	public Topic(String ID, String name)
	{
		this.ID = ID;
		this.name = name;
		this.messages = new ArrayList<>();
		this.subscribers = new ArrayList<>();
		this.poll_subscribers = new HashMap<>();
	}
	
	
	public List<String> get_all_messages()
	{
		List<String> messages_copy = new ArrayList<>(messages);
		
		return messages_copy;
	}
	
	
	public List<TopicSubscriber> get_all_subscribers()
	{
		List<TopicSubscriber> subscribers_copy= new ArrayList<>(subscribers);
		
		return subscribers_copy;
		
		//return subscribers;
	}
	
	public Map<String, TopicSubscriber> get_all_poll_subscribers()
	{
		return poll_subscribers;
	}
	
	public void add_message(String message)
	{
		this.messages.add(message);
	}
	
	
	public void add_subscriber(TopicSubscriber subscriber)
	{
		this.subscribers.add(subscriber);
	}
	
	public void add_poll_subscriber(String name, TopicSubscriber subscriber)
	{
		this.poll_subscribers.put(name, subscriber);
	}
	
	
	public String get_ID()
	{
		return ID;
	}
	
	public String get_name()
	{
		return name;
	}
		
}
