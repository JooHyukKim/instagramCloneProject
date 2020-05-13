package instagram.share;

import java.util.ArrayList;

public class Result extends ArrayList{

	private int status;

	public Result(int status) {
		super();
		this.status = status;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
	
}
