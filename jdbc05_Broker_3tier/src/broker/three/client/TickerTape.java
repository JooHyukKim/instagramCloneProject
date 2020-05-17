package broker.three.client;
import java.awt.*;
import java.io.*;
import java.net.*;

public class TickerTape extends Canvas	implements Runnable {
	
	char currChar;
	int strIndex = 0;
	String tape;
	

	
	private static final int TEXTMARGIN  = 3;
	private static final int RECTMARGIN  = 1;
	private static final int SCROLLPAUSE = 15;
	
	private static final int TICKERHOSTPORT = 5500;
	
	
	private Thread scrollThread;          
	private Image  offscreenImage = null; 

	private Font   textFont;             
	private int    tapeIndex;             
	private int    totalTapeSteps;       
	
	private int    textWidth;             
	private int    textHeight;           
	private int    textDescent;           
	
	private int    previousWidth = -1;   

	
	FontMetrics metrics;
	
	TickerReader tickerHost;
	Color bg = new Color(9,41,143);

	public TickerTape (String hostname, int width) {
		
		setBackground(bg);
		setForeground(Color.green);
		
		tickerHost = new TickerReader (hostname, TICKERHOSTPORT);
		
		
		tape = getNextString();
		
		
		textFont        = new Font("Dialog", Font.PLAIN, 12);
	
		
		metrics = getFontMetrics(textFont); 
		
		textHeight        = metrics.getHeight();
		textDescent        = metrics.getDescent();
		
		
		textWidth        = metrics.stringWidth(tape);
		
	
		setSize (width, 30);
		
		
		scrollThread = new Thread(this);
		scrollThread.start();
	} 
	
	public String getNextString () {
		String tickerStr;
		
		tickerStr = tickerHost.readData();
		
		
		if (tickerStr == null) {
			tickerStr = "Data connection to ticker tape host is down....Attempting reconnection....";
			System.err.println ("Ticker Reader readData failed.");
		}
		return (tickerStr);
	}
	
	public void paint(Graphics g) {
		
			
		setupTape();
		
				
		Graphics offg = offscreenImage.getGraphics();
		
		offg.setColor(getBackground());
		offg.fillRect(0, 0, getSize().width - (RECTMARGIN * 2), textHeight);
		offg.setColor(getForeground());
		offg.setFont(textFont);
		
				
		offg.drawString(tape, previousWidth - tapeIndex, 
			textHeight - textDescent);
		
		
				
		tapeIndex = (tapeIndex + 1) % totalTapeSteps;
		
		
		
		if (tapeIndex == 0) {
			tape = getNextString ();
			return;
		}
		
			
		g.draw3DRect(RECTMARGIN, RECTMARGIN,
			getSize().width - RECTMARGIN, 
			getSize().height - RECTMARGIN, 
			false);
		
	
		g.drawImage(offscreenImage, RECTMARGIN + 1, 
			RECTMARGIN + TEXTMARGIN + 1,
			this);
	} 
	
	
	public void close() {
		tickerHost.closePort();
	}
	
	

	
	public void stop() {
		
	}
	
	
	public void update(Graphics g) {
		paint(g);
	}
	
		
	public void run() {
		while (true) {
			try {
				scrollThread.sleep(SCROLLPAUSE);
				
			} catch (InterruptedException e) {
			}
			
			repaint();
		}
	}
	
	
	
	private void setupTape() {
		Dimension dim = getSize();
		
		
		setSize(dim.width, textHeight + (TEXTMARGIN * 2) + (RECTMARGIN * 2));
		
		
		if (dim.width == previousWidth) {
			return;
		}
		
		previousWidth  = dim.width;
		
		
		tapeIndex      = 0;
		
	
		totalTapeSteps = dim.width + textWidth;
		
		
		if (offscreenImage != null) {
			offscreenImage.flush();
		}
		
		offscreenImage = createImage(dim.width - (RECTMARGIN * 2), textHeight);
	} 
	
} 
