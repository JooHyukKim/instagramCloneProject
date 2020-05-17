package broker.three.client;

import java.net.*;
import java.io.*;

class TickerReader {

    Socket tickerSocket;
    DataInputStream recvStream;
    DataOutputStream sendStream;
    String symbol[];
    float price[];
	String price_st[];

    String hostname;
    int port;

    String reStr = null;
	int header=0;
    
    public TickerReader (String hostname, int port) {
        // hostname과 port를 member에 setting시키는 것과
        // makeConnection을 호출하는 부분이 필요합니다.
		this.hostname=hostname;
		this.port=port;
    	makeConnection(hostname, port);
    }

    public void makeConnection (String hostname, int port) {
        // Socket을 만들고 DataInputStream과 DataOutputStream을 만드는
        // 과정이 필요합니다.
     	try {
    		tickerSocket = new Socket(hostname,port);
    		recvStream = new DataInputStream(tickerSocket.getInputStream());
    		sendStream =  new DataOutputStream(tickerSocket.getOutputStream());
    	} catch(IOException e) {e.printStackTrace();}
  
    
    }

    public String readData () {
        // StreamingData가 보낸 정보를 받기 위해
        // 첫번째로 하나의 문자열을 보낸후, int type으로 Symbol을 갯수를 
        // 받습니다. 그후에 symbol과 price의 배열을 갯수만큼 만들겠죠.
        // 다음에 서버에서 보내주는 정보를 읽어 들입니다.
        // 그것을 하나의 String 으로 만들어서 return.
        String reStr = "";

    	try {
    		sendStream.writeUTF ("자료요청합니다");
        	if (recvStream == null) {
        	
        	} else {     
            	header = recvStream.readInt();
            	symbol = new String[header];
            	price = new float[header];
				price_st=new String[header];
            	for (int i = 0; i < header; i++) {
                		symbol[i] = recvStream.readUTF();
                		price[i]  = recvStream.readFloat();
					//	price_st[i]=new Float(price[i]).toString();
                }
				
        	} 
        } catch (IOException e) {}

    	//받아온 자료를 스트링으로 묶는 과정
        for (int i = 0; i <symbol.length; i++) {
    		reStr += symbol[i].trim() + " "+new Float(price[i]).toString().trim()+"   ";     
         // reStr += MakeFraction.convertToFraction (price[i]) + "  ";
		 //   reStr += price_st[i] + " ";
	    }
        return reStr;
    }

    public void closePort () {
        try {
            tickerSocket.close();
            recvStream.close();
        } catch (IOException e) {
            System.err.println ("closePort failed");
        }
    }

}