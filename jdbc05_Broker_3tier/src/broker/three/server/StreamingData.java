package broker.three.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import broker.threetier.vo.StockRec;

public class StreamingData implements Runnable {

    int listenPort = 5500;					
    int alternatePort = listenPort;			 
    ServerSocket myServer = null;
    SendThread	sendData;
    Socket	 answerSocket;
    Database	db;
   
   
	String stockName []= null;
   	float stockPrice [] =  null;


	public StreamingData() throws Exception {
		
		db = new Database("127.0.0.1");

	}
	public void listen() { 
	
        try { 

            myServer = new ServerSocket(listenPort);

        } catch (IOException e) {

            System.err.println(e.toString());
            System.err.println("Unable to create Server Socket," + "trying alternate port " + alternatePort);
        
		} 

        System.out.println("Notify all clients that the current Server Port"  + " is " + listenPort );

    } 



    public void run() {

         while (true) {
    
			try {
            
				answerSocket = myServer.accept();
            
			} catch (IOException e) { 
            
				return ; 
            }

            sendData = new SendThread(answerSocket,  this);

            Thread.yield();

        }
    }

   /**
	*1)DB stock���̺� �ִ� ��� �ֽ������� ������ �´�.
	*2)symbol�� price�� ���� String type�� �迭�� ��´�.
  */
    public void allStocks() throws Exception{
           
		ArrayList<StockRec> sr=null;
		sr = db.getAllStocks();
		System.out.println("DB���� ������ ������....");
		stockName = new String[sr.size()];
		stockPrice = new float[sr.size()];

		for(int i=0; i<sr.size(); i++) {
		
			stockName [i] = sr.get(i).getSymbol();
			stockPrice [i] = sr.get(i).getPrice();
		
		}
		
	}


    public static void main(String args[]) throws Exception {
  
		StreamingData myStreamer = new StreamingData();
		myStreamer.allStocks();
		
        myStreamer.listen();

        Thread myThread = new Thread (myStreamer);

        myThread.start();


    } 

} 
