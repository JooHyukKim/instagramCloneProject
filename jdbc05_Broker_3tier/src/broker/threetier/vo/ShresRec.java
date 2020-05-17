package broker.threetier.vo;

import java.io.Serializable;

/*
 * 누가 
 * 어떤 주식을 
 * 얼마만큼 보유하고 있는지의 정보를 담고 있는 클래스 
 * Shares 테이블의 정보
 * 
 */
public class ShresRec implements Serializable {
	private String ssn;
	private String symbol;
	private int qunatity;
	
	public ShresRec() {}
	
	public ShresRec(String ssn, String symbol, int qunatity) {
		super();
		this.ssn = ssn;
		this.symbol = symbol;
		this.qunatity = qunatity;
	}

	public String getSsn() {
		return ssn;
	}

	public void setSsn(String ssn) {
		this.ssn = ssn;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public int getQunatity() {
		return qunatity;
	}

	public void setQunatity(int qunatity) {
		this.qunatity = qunatity;
	}

	@Override
	public String toString() {
		return "ShresRec [ssn=" + ssn + ", symbol=" + symbol + ", qunatity=" + qunatity + "]";
	}
	
	
}
