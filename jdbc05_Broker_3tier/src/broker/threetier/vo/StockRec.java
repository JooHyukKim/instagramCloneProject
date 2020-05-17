package broker.threetier.vo;

import java.io.Serializable;

/*
 * Stock 테이블의 정보를 저장하는 클래스
 * 주식의 이름, 주식의 가격으로 구성된다. 
 */
public class StockRec implements Serializable {
	private String symbol;
	private float price;
	
	public StockRec() {
		this("", 0.0F);
	}
	
	public StockRec(String symbol, float f) {
		super();
		this.symbol = symbol;
		this.price = f;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	@Override
	public String toString() {
		return "StockRec [symbol=" + symbol + ", price=" + price + "]";
	}
	
	
	
	
	
}
