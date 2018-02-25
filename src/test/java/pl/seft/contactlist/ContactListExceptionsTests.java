package pl.seft.contactlist;

import org.junit.*;

import pl.self.contactlist.ContactList;
import pl.self.contactlist.exception.NameMustBeProvidedException;
import pl.self.contactlist.exception.PhoneNumberMustBeInValidFormatException;
import pl.self.contactlist.exception.PhoneNumberMustBeProvidedException;
import pl.self.contactlist.exception.PhoneNumberMustBeUniqueException;

public class ContactListExceptionsTests {
	
	private static final String EMPTY = "";
	private static final String EXAMPLE_PHONE="555-666-777",
			EXAMPLE_PHONE_IN_OTHER_FORMAT="555666777",
			EXAMPLE_NAME="Pawel",
			ANOTHER_EXAMPLE_NAME="Anna";
	private static final String[] WRONG_PHONE_NUMBERS={
		"-555-666","AAA-BBB-AAA","55-11-AAA","333-222","222","000-111-CCC",
		"000-CCC-111","CCC-111-222","22-11-22",
		"111111","111111111111111","11122233A","A22255566","111+222+333"
	};

	@Test(expected=NameMustBeProvidedException.class)
	public void shouldThrowOnNullNameWhenAdd() {
		invokeAddContact(null,EXAMPLE_PHONE);
	}
	@Test(expected=NameMustBeProvidedException.class)
	public void shouldThrowOnEmptyNameWhenAdd() {
		invokeAddContact(EMPTY,EXAMPLE_PHONE);
	}
	@Test(expected=PhoneNumberMustBeProvidedException.class)
	public void shouldThrowOnNullPhoneWhenAdd() {
		invokeAddContact(EXAMPLE_NAME, null);
	}
	@Test(expected=PhoneNumberMustBeProvidedException.class)
	public void shouldThrowOnEmptyPhoneWhenAdd() {
		invokeAddContact(EXAMPLE_NAME, EMPTY);
	}
	@Test(expected=NameMustBeProvidedException.class)
	public void shouldThrowOnNullNameWhenGet() {
		invokeGetContactByName(null);
	}
	@Test(expected=NameMustBeProvidedException.class)
	public void shouldThrowOnEmptyNameWhenGet() {
		invokeGetContactByName(EMPTY);
	}
	@Test
	public void shouldThrowOnWrongPhoneNumber() {
		int illegalCounter = 0;
		for(String wrongPhone : WRONG_PHONE_NUMBERS) {
			try {
				invokeAddContact(EXAMPLE_NAME, wrongPhone);
			} catch(PhoneNumberMustBeInValidFormatException e) {
				illegalCounter++;
			}
		}
		Assert.assertEquals(WRONG_PHONE_NUMBERS.length, illegalCounter);
		
	}
	@Test(expected=PhoneNumberMustBeUniqueException.class)
	public void shouldNotBeAbleToAddSamePhoneNumberMoreThanOnceForDistictNames() {
		shouldNotBeAbleToAddSamePhoneNumberMoreThanOnce(EXAMPLE_NAME, ANOTHER_EXAMPLE_NAME);
	}
	@Test(expected=PhoneNumberMustBeUniqueException.class)
	public void shouldNotBeAbleToAddSamePhoneNumberMoreThanOnceForSameName() {
		shouldNotBeAbleToAddSamePhoneNumberMoreThanOnce(EXAMPLE_NAME, EXAMPLE_NAME);
	}
	@Test(expected=PhoneNumberMustBeUniqueException.class)
	public void shouldNotBeAbleToAddSamePhoneNumberInDifferentFormat() {
		shouldNotBeAbleToAddSamePhoneNumberMoreThanOnce(EXAMPLE_NAME,EXAMPLE_NAME,EXAMPLE_PHONE,
				EXAMPLE_PHONE_IN_OTHER_FORMAT);
	}
	private void shouldNotBeAbleToAddSamePhoneNumberMoreThanOnce(String firstName,String secondName) {
		shouldNotBeAbleToAddSamePhoneNumberMoreThanOnce(firstName, secondName,EXAMPLE_PHONE);
	}
	private void shouldNotBeAbleToAddSamePhoneNumberMoreThanOnce(String firstName,String secondName,String firstPhone) {
		shouldNotBeAbleToAddSamePhoneNumberMoreThanOnce(firstName, secondName, firstPhone,firstPhone);
	}
	private void shouldNotBeAbleToAddSamePhoneNumberMoreThanOnce(String firstName,String secondName,
			String firstPhone,String secondPhone) {
		final ContactList contactList = new ContactList();
		contactList.addContact(firstName, firstPhone);
		contactList.addContact(secondName, secondPhone);
	}

	
	
	private void invokeGetContactByName(String name) {
		final ContactList contactList = new ContactList();
		contactList.getContactByName(name);
	}
	
	private void invokeAddContact(String name,String phone) {
		final ContactList contactList = new ContactList();
		contactList.addContact(name,phone);
	}
	
}
