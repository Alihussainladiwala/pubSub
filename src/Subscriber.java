import java.util.ArrayList;

public class Subscriber extends Thread {
	public String name="";
	public SubscriberType type;
	public ArrayList<Integer> timeStamp;
	private ArrayList<TopicPublisher> subscribedPublishers=new ArrayList<TopicPublisher>();
	/**
	 *   Subscriber()--- Constructor for the class taking details like name and topics subscribed
	 */
	Subscriber(String s,SubscriberType t,ArrayList<TopicPublisher> p){	
		name=s;
		type=t;	
		subscribedPublishers=p;
		timeStamp=new ArrayList<Integer>();
		System.out.println();
		System.out.println("Creating subscriber "+name);
		System.out.print(name+" subscribing topic :");
		for (TopicPublisher x: subscribedPublishers){
			System.out.print(x.getTopicName()+" ");
			x.subscribe(this);
	}
		System.out.println();
		
	}
	
	/**
	 * getPublication() --- method to print the data every time its published. Used only for PUSH mode subscribers 
	 */
	public void getPublication (String data) {
		System.out.println("Push request data for "+name+":" + data);
		};
		
		/**
		 * pullPublication() --- method to pull recent data for subscribed topics. used only for PULL mode subscribers..
		 */
	public void run(){
		try{
			if(type==SubscriberType.PUSH)
				return;
		//int j=10;
		while(true){
		System.out.println();
		System.out.println("User "+name+" pulled data");
		for (int i=0;i<subscribedPublishers.size();i++){
			ArrayList<String> resultData=subscribedPublishers.get(i).pullData(this,i);
			if(resultData==null){
				System.out.println("No new data published for "+subscribedPublishers.get(i).getTopicName());
			}else{			
			System.out.print("Data published for "+subscribedPublishers.get(i).getTopicName()+":");
			for(String s:resultData)
				System.out.print(s+" ");
			timeStamp.set(i,timeStamp.get(i)+resultData.size());
			System.out.println();
			
		}
			Thread.sleep(3000);
		//	j++;

		}
		}
		}
			catch(InterruptedException e)
		     {
		        System.out.println("my thread interrupted");
		     }
		
	}
	/**
	 * subscribePublisher() --- method to subscribe a particular topic publisher.Adds publisher to subscribedPublishers list and their time stamp.
	 */
	public void subscribePublisher(TopicPublisher p){
		System.out.println("User "+name+" subscribed topic "+p.getTopicName());
		subscribedPublishers.add(p);
		p.subscribe(this);
	}
	/**
	 * unsubscribePublisher() --- method to unsubscribe a particular topic publisher.Removes publisher to subscribedPublishers list and their time stamp.
	 */
	public void unsubscribePublisher(TopicPublisher p){
		System.out.println("User "+name+" unsubscribed topic "+p.getTopicName());
		for(int i=0;i<subscribedPublishers.size();i++){
			if(subscribedPublishers.get(i).equals(p)){
				subscribedPublishers.remove(p);
				timeStamp.remove(i);
			}
		}
		
	}
}
