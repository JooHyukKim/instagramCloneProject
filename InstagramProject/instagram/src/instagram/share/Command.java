package instagram.share;

import java.io.Serializable;
import java.io.ObjectInputStream.GetField;

import instagram.share.Result;
public class Command implements Serializable{
	
	// Command
	//USER CRUD 10단위로
	public static final int ADDUSER = 10;
	public static final int GETUSER = 11;
	public static final int UPDATEUSER = 12;
	public static final int DELETEUSER = 13;
	public static final int AUTHENTICATEUSER = 14;
	public static final int GETUSERSBYPERSONTAG = 15;
	public static final int GETUSERBYPERSONTAG = 16;
	public static final int GETFOLLOWERUSERS = 17;
	public static final int GETFOLLOWINGUSERS = 18;
	
	//POST CRUD 20단위로
	public static final int ADDPOST = 20;
	public static final int GETPOST = 21;
	public static final int UPDATEPOST = 22;
	public static final int DELETEPOST = 23;
	public static final int GETALLPOSTSOFPERSON = 24;
	public static final int GETSOMEPOSTSOFOTHERPERSON = 25;
	public static final int GETPOSTSBYHASHTAG = 26;

	
	//COMMENT CRUD 30
	public static final int ADDCOMMENT = 30;
	public static final int UPDATECOMMENT = 31;
	public static final int DELETECOMMENT = 32;
	public static final int GETCOMMENTSONPOST = 33;
	public static final int GETALLCOMMENTS = 34;
	
	//FOLLOW CRUD 40
	
	//HASHTAG CRUD 50
	public static final int GETHASHTAGSONPOST = 50;
	
	//PERSONTAG CRUD 60
	public static final int GETPERSONTAGSONPOST = 60;
	

	
	
	
	
	private int commandValue;
	private String[ ] args;
	private Result results;	
	
	public Command(int commandValue) {
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
