import java.util.ArrayList;
import java.util.Arrays;


public class Test {
	public static void main(String[] args) {
		TopicPublisher p1=new TopicPublisher("Food","C:\\275_Pro\\Publish-Subscribe-Simulation-master\\src\\Food.txt");
		TopicPublisher p2=new TopicPublisher("Health","C:\\275_Pro\\Publish-Subscribe-Simulation-master\\src\\Health.txt");
		Subscriber s1=new Subscriber("ABCD",SubscriberType.PULL, new ArrayList<TopicPublisher>(Arrays.asList(p1,p2)));
		p1.start();
		Subscriber s2=new Subscriber("EFGH",SubscriberType.PUSH,new ArrayList<TopicPublisher>(Arrays.asList(p2)));
		p2.start();
		s1.start();
		s2.start();
		TopicPublisher p3=new TopicPublisher("Books","C:\\275_Pro\\Publish-Subscribe-Simulation-master\\src\\Books.txt");
		s2.subscribePublisher(p3);
		//s1.unsubscribePublisher(p1);
		p3.start();

		while(true) {
			try {
				p2.publish(args[0]);
			}
			catch(Exception e){
				System.out.println("No data");
			}
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}


	 }
}
