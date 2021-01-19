import java.util.ArrayList;
import java.util.List;

import com.ibm.broker.plugin.MbElement;
import com.ibm.broker.plugin.MbException;
import com.ibm.broker.plugin.MbJSON;
import com.ibm.broker.plugin.MbMessage;

public abstract class CustomerDatabase {

	private static List<Customer> customers = new ArrayList<>();

	static {
		customers.add(new Customer("Denis", "Darner", "1 The Street, The Town"));
		customers.add(new Customer("Elana", "Ericson", "2 The Street, The Town"));
		customers.add(new Customer("Lyle", "Longino", "3 The Street, The Town"));
		customers.add(new Customer("Gerri", "Gaertner", "4 The Street, The Town"));
		customers.add(new Customer("Willis", "Wicks", "5 The Street, The Town"));
		customers.add(new Customer("Jessika", "Jeon", "6 The Street, The Town"));
		customers.add(new Customer("Neil", "Newton", "7 The Street, The Town"));
		customers.add(new Customer("Ferne", "Foye", "8 The Street, The Town"));
	}

	public static void getAllCustomers(Long max, MbElement[] output) {
		try {
			MbElement root = output[0];
			long counter = 0;
			for (Customer customer : customers) {
				if (max > -1 && counter >= max) {
					break;
				}
				MbElement item = root.createElementAsLastChild(MbJSON.OBJECT, MbJSON.ARRAY_ITEM_NAME, null);
				item.createElementAsLastChild(MbElement.TYPE_NAME_VALUE, "id", customer.getID());
				item.createElementAsLastChild(MbElement.TYPE_NAME_VALUE, "firstname", customer.getFirstName());
				item.createElementAsLastChild(MbElement.TYPE_NAME_VALUE, "lastname", customer.getLastName());
				item.createElementAsLastChild(MbElement.TYPE_NAME_VALUE, "address", customer.getAddress());
				counter++;
			}
		} catch (MbException e) {
			throw new RuntimeException(e);
		}
	}

	public static MbElement getCustomer(int id) throws MbException {
		for (Customer customer : customers) {
			if (customer.getID() == id) {
				MbMessage message = new MbMessage();
				MbElement result = message.getRootElement().createElementAsLastChild(MbJSON.OBJECT, MbJSON.DATA_ELEMENT_NAME, null);
				result.createElementAsLastChild(MbElement.TYPE_NAME_VALUE, "id", customer.getID());
				result.createElementAsLastChild(MbElement.TYPE_NAME_VALUE, "firstname", customer.getFirstName());
				result.createElementAsLastChild(MbElement.TYPE_NAME_VALUE, "lastname", customer.getLastName());
				result.createElementAsLastChild(MbElement.TYPE_NAME_VALUE, "address", customer.getAddress());
				return result;
			}
		}
		return null;
	}

	public static boolean deleteCustomer(int id) {
		for (Customer customer : customers) {
			if (customer.getID() == id) {
				customers.remove(customer);
				return true;
			}
		}
		return false;
	}

	public static boolean customerExists(int id) {
		for (Customer customer : customers) {
			if (customer.getID() == id) {
				return true;
			}
		}
		return false;
	}

	public static MbElement addCustomer(String firstname, String lastname, String address) throws MbException {
		Customer customer = new Customer(firstname, lastname, address);
		customers.add(customer);
		MbMessage message = new MbMessage();
		MbElement result = message.getRootElement().createElementAsLastChild(MbJSON.OBJECT, MbJSON.DATA_ELEMENT_NAME, null);
		result.createElementAsLastChild(MbElement.TYPE_NAME_VALUE, "message", "A new customer with ID '" + customer.getID() + "' was successfully added to the database.");
		return result;
	}

	public static MbElement updateCustomer(int id, String firstname, String lastname, String address) throws MbException {
		for (Customer customer : customers) {
			if (customer.getID() == id) {
				customer.setFirstName(firstname);
				customer.setLastName(lastname);
				customer.setAddress(address);
				MbMessage message = new MbMessage();
				MbElement result = message.getRootElement().createElementAsLastChild(MbJSON.OBJECT, MbJSON.DATA_ELEMENT_NAME, null);
				result.createElementAsLastChild(MbElement.TYPE_NAME_VALUE, "message", "An existing customer with ID '" + customer.getID() + "' was successfully updated in the database.");
				return result;
			}
		}
		return null;
	}

}
