import java.util.concurrent.atomic.AtomicInteger;

public class Customer {

	private static AtomicInteger currentID = new AtomicInteger(1);

	private int id;
	private String firstname;
	private String lastname;
	private String address;

	public Customer(String firstname, String lastname, String address) {
		id = currentID.getAndIncrement();
		this.firstname = firstname;
		this.lastname = lastname;
		this.address = address;
	}

	public int getID() {
		return id;
	}

	public String getFirstName() {
		return firstname;
	}

	public void setFirstName(String firstname) {
		this.firstname = firstname;
	}

	public String getLastName() {
		return lastname;
	}

	public void setLastName(String lastname) {
		this.lastname = lastname;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

}
