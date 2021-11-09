import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Scanner;


public class TopicPublisher extends Thread {
	private String topicName;
	private String blogFile;

	private ArrayList<String> dataList;
	private ArrayList<Subscriber> pullReqSubscribers;
	private ArrayList<Subscriber> pushReqSubscribers;
	/**
	 *   Subscriber()--- Constructor for the class taking details like topic name and its different mode subscribers 
	 */
	TopicPublisher(String n,String blgFile){
		  topicName=n;
		  blogFile=blgFile;
		  dataList=new ArrayList<String>();
		  pullReqSubscribers=new ArrayList<Subscriber>();
		  pushReqSubscribers=new ArrayList<Subscriber>();
	}
	
	/**
	 * getTopicName() --- method to return the topic name  
	 */
	public String getTopicName(){
		return topicName;
	}
	/**
	 * subscribe() --- method to add subscribers to the list based on their mode of subscription.
	 */
	public void subscribe(Subscriber s){
		if(s.type==SubscriberType.PULL){
			pullReqSubscribers.add(s);
			s.timeStamp.add(dataList.size());
		}else{
			pushReqSubscribers.add(s);
		}
	}
	/**
	 * publish() --- method to publish data as soon its entered for PUSH mode subscribers and store data in list for PULL mode subscribers.  
	 */
	public void publish(String data){
		for(Subscriber s:pushReqSubscribers){
			s.getPublication(data);
		}
		dataList.add(data);
	}
	/**
	 * pullData() --- method to return recent data for PULL mode subscribers. 
	 */
	public ArrayList<String> pullData(Subscriber s,int idx){
		int lastIndex=dataList.size();	
		if(lastIndex==0||s.timeStamp.get(idx)==lastIndex)
			return null;
		return  new ArrayList<String>(dataList.subList(s.timeStamp.get(idx), lastIndex));
	}
	/**
	 * getInput() --- method to get input from command line.  
	 */
	public static String getInput () {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String response = "";
		try {
			response = br.readLine();
			if (response.length()==0) {
				return null;
				}
			} 
		catch (IOException ioe) {
			System.out.println("IO error reading from terminal\n");
			}
		return response;
		}
	/**
	 * startPublish() --- method to start publish data i.e. take input form command line. 
	 */
		public void run () {
			try{
				String fileName=blogFile;
				Scanner scanner = new Scanner(new File(fileName));				
				while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				Scanner lineScanner = new Scanner(line);
				lineScanner.useDelimiter("\n");
				while (lineScanner.hasNext()) {
				String part = lineScanner.nextLine();
				publish(part);
				Thread.sleep(1000);
				}
				lineScanner.close();
				
			//System.out.println("Input for "+topicName);
			//System.out.print("> ");
			//String s = getInput();
			
				}
				scanner.close();
		}
		catch (Exception e) {
			e.printStackTrace();
			}
		}

}

