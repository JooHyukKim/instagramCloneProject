package broker.threetier.vo;

import java.io.Serializable;
import java.util.Vector;

public class CustomerRec implements Serializable {
	private String ssn;
	private String name;
	private String address;
	
	private Vector<ShresRec> portfolio;

	public CustomerRec() {}
	
	public CustomerRec(String ssn, String name, String address, Vector<ShresRec> portfolio) {
		super();
		this.name = name;
		this.ssn = ssn;
		this.address = address;
		this.portfolio = portfolio;
	}
	
	public CustomerRec(String ssn, String name, String address) {
		this(ssn, name, address, null);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSsn() {
		return ssn;
	}

	public void setSsn(String ssn) {
		this.ssn = ssn;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Vector<ShresRec> getPortfolio() {
		return portfolio;
	}

	public void setPortfolio(Vector<ShresRec> portfolio) {
		this.portfolio = portfolio;
	}

	@Override
	public String toString() {
		return "CustomerRec [name=" + name + ", ssn=" + ssn + ", address=" + address + ", portfolio=" + portfolio + "]";
	}
	
	
	
	
	

}
