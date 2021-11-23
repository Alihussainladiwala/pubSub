package messagequeue;

public class Simulation {

    public static void main(String[] args) throws InterruptedException {
    	
    
        MessageQueue queue = new MessageQueue();
        
        Topic topic1 = queue.create_topic("topic1");
        Topic topic2 = queue.create_topic("topic2");
        
        Subscriber sub1 = new Subscriber(1,"subscriber1", 9000);
        Subscriber sub2 = new Subscriber(2,"subscriber2", 9000);
        
        queue.add_poll_subscriber(sub1, topic1);
        queue.add_poll_subscriber(sub2, topic2);

        /*Subscriber sub3 = new Subscriber(3,"sub3", 5000);
        queue.add_subscriber(sub3, topic1); */

        queue.publish("m1",topic1);
        queue.publish("m2",topic1);
        
        queue.poll(sub1, topic1);

        /*queue.publish("m3",topic2);
		
        Thread.sleep(10000);
        queue.publish("m4",topic2);*/
        
        //Thread.sleep(10000);
        queue.publish("m5",topic1);
        queue.publish("m6",topic1);
        queue.publish("m7",topic1);
        queue.publish("m8",topic1);


        //queue.disconnect(sub1,topic1, 0);
        
        queue.poll(sub1, topic1);
        //queue.poll(sub2, topic1);
        
        
        
    }
    
}
