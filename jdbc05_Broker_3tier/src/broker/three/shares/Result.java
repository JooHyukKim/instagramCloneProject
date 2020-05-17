package broker.three.shares;

import java.util.ArrayList;
/*
 * Result는 1칸 짜리 ArrayList임 
 * 성공/실패 여부를 확인할 수 있는 status 값을 가지고 있다. 
 */
public class Result extends ArrayList{
	private int status = -1;
	
	public Result() {
		super(1); // ArrayList는 기본적으로 10칸으로 만듬 따라서 1칸만 만들어야하기에 저기에 1 넣어둠
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
	
}
