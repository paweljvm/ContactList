package pl.self.contactlist;

import java.util.Collection;
import java.util.Set;

import pl.self.contactlist.exception.NameMustBeProvidedException;
import pl.self.contactlist.exception.PhoneNumberMustBeInValidFormatException;
import pl.self.contactlist.exception.PhoneNumberMustBeProvidedException;
import pl.self.contactlist.exception.PhoneNumberMustBeUniqueException;

class ParametersChecker {
	
	private final PhoneHelper phoneHelper;
	ParametersChecker(PhoneHelper phoneHelper) {
		this.phoneHelper = phoneHelper;
	}
	
	void checkName(String name) {
		Util.requireNotNullAndNotEmpty(name, NameMustBeProvidedException::new);
	}
	void checkPhoneNumber(String phoneNumber,Collection<Set<String>> currentPhones) {
		Util.requireNotNullAndNotEmpty(phoneNumber,
				PhoneNumberMustBeProvidedException::new);
		Util.require(phoneHelper::isNotValid, phoneNumber,
				PhoneNumberMustBeInValidFormatException::new);
		if(Util.isNotNullAndNotEmpty(currentPhones)) {
			checkIfPhoneNumberAlreadyExists(phoneNumber, currentPhones);
		}

	}

	private void checkIfPhoneNumberAlreadyExists(String phoneNumber,
			Collection<Set<String>> currentPhones) {
		Util.require(
				phone -> currentPhones
						.stream()
						.flatMap(value -> value.stream())
						.anyMatch(
								existingPhone -> phoneHelper.areEqual(
										existingPhone, phone)),
				phoneNumber, PhoneNumberMustBeUniqueException::new);
	}
}
