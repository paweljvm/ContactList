package pl.self.contactlist;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

public class ContactList {

	private final Map<String, Set<String>> dataContainer;
	private final ParametersChecker checker;
	public ContactList() {
		this.dataContainer = new ConcurrentHashMap<>();
		this.checker = new ParametersChecker(PhoneHelper.get());
	}
	/**
	 * 
	 * @param name -> must be provided, cannot be null or empty
	 * @param phoneNumber -> must be provided in format DDD-DDD-DDD or DDDDDDDDD where D is a digit 
	 */
	public void addContact(String name, String phoneNumber)	 {
		performParametersValidation(name, phoneNumber);
		performAdd(name, phoneNumber);
	}

	private void performAdd(String name, String phoneNumber) {
		Set<String> phones = dataContainer.get(name);
		if (phones == null) {
			phones = new CopyOnWriteArraySet<String>();
		}
		phones.add(phoneNumber);
		dataContainer.put(name, phones);
	}
	
	/**
	 * 
	 * @param name -> must be provided, cannot be null or empty
	 * @return phone number of contact or null if not present
	 */
	public String getContactByName(String name) {
		checker.checkName(name);
		final Set<String> phones = dataContainer.get(name);
		if (Util.isNotNullAndNotEmpty(phones)) {
			return phones.iterator().next();
		}
		return null;
	}

	private void performParametersValidation(String name, String phoneNumber) {
		checker.checkName(name);
		checker.checkPhoneNumber(phoneNumber, dataContainer.values());
	}

}
