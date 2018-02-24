package pl.seft.contactlist;

import org.junit.Assert;
import org.junit.Test;

import pl.self.contactlist.ContactList;

public class ContactListFeaturesTests {

	private static final String[] EXAMPLE_PHONES= {
		"111222333","564-422-733","602-664-521","734567222"
	};
	private static final String[] EXAMPLE_NAMES= {
		"Anna","Ramona","Wojciech","Janusz"
	};
	private static final String NOT_EXISTING_NAME="NOT_EXISTING";
	@Test
	public void shouldAddSameNameMultipleTimesContactAndRetreiveFirstPhoneNumber() {
		final ContactList contactList = new ContactList();
		final String firstPhoneNumber = EXAMPLE_PHONES[0];
		final String name = EXAMPLE_NAMES[0];
		for(String phoneNumber : EXAMPLE_PHONES) {
				contactList.addContact(name,phoneNumber);
				Assert.assertEquals(firstPhoneNumber, contactList.getContactByName(name));
			
		}
	}
	@Test
	public void shouldRetreiveCorrectPhoneNumberForEachName() {
		final ContactList contactList = new ContactList();
		final int length = EXAMPLE_NAMES.length;
		addAllExampleValues(contactList, length);
		for(int i= 0;i<length;i++) {
			Assert.assertEquals(EXAMPLE_PHONES[i],contactList.getContactByName(EXAMPLE_NAMES[i]));
		}
	}
	private void addAllExampleValues(final ContactList contactList,
			final int length) {
		for(int i = 0;i<length ;i++) {
			contactList.addContact(EXAMPLE_NAMES[i],EXAMPLE_PHONES[i]);
		}
	}
	@Test
	public void shouldReturnNullWhenNotFound() {
		final ContactList contactList = new ContactList();
		Assert.assertNull(contactList.getContactByName(NOT_EXISTING_NAME));
		addAllExampleValues(contactList, EXAMPLE_NAMES.length);
		Assert.assertNull(contactList.getContactByName(NOT_EXISTING_NAME));
	}



}
