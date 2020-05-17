package broker.three.shares;
// 도시락통임

import java.io.Serializable;

public class Command implements Serializable {
	public static final int BUYSHARES = 10;
	public static final int SELLSHARES = 20;
	public static final int GETALLSTOCKS = 30;
	public static final int GETSTOCKPRICE = 40;
	public static final int GETALLCUSTOMERS = 50;
	public static final int GETCUSTOMER = 60;
	public static final int ADDCUSTOMER = 70;
	public static final int DELETECUSTOMER = 80;
	public static final int UPDATECUSTOMER = 90;
	
	private int commandValue;
	private String[ ] args;
	private Result results;
	
	public Command(int commandValue) {
		super();
		this.commandValue = commandValue;
		results = new Result();
	}

	public int getCommandValue() {
		return commandValue;
	}

	public void setCommandValue(int commandValue) {
		this.commandValue = commandValue;
	}

	public String[] getArgs() {
		return args;
	}

	public void setArgs(String[] args) {
		this.args = args;
	}

	public Result getResults() {
		return results;
	}

	public void setResults(Result results) {
		this.results = results;
	}
	
	
	
	
	
	

}
