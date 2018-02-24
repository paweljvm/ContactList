package pl.seft.contactlist;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

import org.junit.Assert;
import org.junit.Test;

import pl.self.contactlist.ContactList;
import pl.self.contactlist.exception.PhoneNumberMustBeUniqueException;

public class ContactListThreadSafetyTests {

	private static final String EXAMPLE_NAME = "THREAD_EXAMPLE",
			EXAMPLE_PHONE = "123456789";

	private static final int THREADS_NUMBER = 5,
			OPERATIONS=100;

	@Test
	public void shouldBeAbleToReadFromMultipleThreads()
			throws InterruptedException {
		final ContactList contactList = new ContactList();
		performMultiThreadOperation(OPERATIONS, (operations,threadNumber) -> {
			try {
				contactList.addContact(EXAMPLE_NAME, EXAMPLE_PHONE);
			} catch (PhoneNumberMustBeUniqueException e) {

			}
			Assert.assertEquals(EXAMPLE_PHONE,
					contactList.getContactByName(EXAMPLE_NAME));
		});
	}

	@Test
	public void shouldBeThreadSafeDuringAdds() throws InterruptedException {
		final ContactList contactList=  new ContactList();
		performMultiThreadOperation(OPERATIONS,(operations,threadNumber) -> performThreadWork(operations, contactList, threadNumber));
		for(int threadNumber = 0;threadNumber<THREADS_NUMBER;threadNumber++) {
			for(int i = 0;i<OPERATIONS;i++) {
				Assert.assertEquals(createThreadPhoneNumber(threadNumber, i),contactList.getContactByName(createThreadName(threadNumber, i)));
			}
		}
	}

	private void performThreadWork(final int numbers,
			final ContactList contactList, final int threadNumber) {
		for (int index = 0; index < numbers; index++) {
			contactList.addContact(createThreadName(threadNumber, index),
					createThreadPhoneNumber(threadNumber, index));
		}
		for (int j = 1_000_00_000 + (threadNumber + 1) * 1000; j < 1_000_00_999 + (threadNumber + 1) * 1000; j++) {
			contactList.addContact(EXAMPLE_NAME, String.format("%d", j));
		}
		Assert.assertNotNull(contactList.getContactByName(EXAMPLE_NAME));

	}

	
	private void performMultiThreadOperation(
			int operations,
			BiConsumer<Integer, Integer> handler) throws InterruptedException {
		final List<Thread> threadList = new ArrayList<>();
		for (int i = 0; i < THREADS_NUMBER; i++) {
			final int threadNumber = i;
			final Thread thread = new Thread(() -> {
				handler.accept(operations, threadNumber);
			});
			thread.start();
			threadList.add(thread);
		}
		for (final Thread thread : threadList) {
			thread.join();
		}
	}


	private String createThreadPhoneNumber(final int threadNumber, int index) {
		return String.format("%d%08d", threadNumber, index);
	}

	private String createThreadName(final int threadNumber, int index) {
		return String.format("%d-%d", index, threadNumber);
	}
}
