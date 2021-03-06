package pl.self.contactlist;

class PhoneHelper {

	private static final PhoneHelper INSTANCE = new PhoneHelper();
	private static final String SEPARATOR="-",EMPTY="";
	private static final String[] ACCEPTABLE_PHONE_REGEXPS= {
		"^[0-9]{3}-[0-9]{3}-[0-9]{3}$",
		"^[0-9]{9}$"
	};
	
	private PhoneHelper() {}
	
	static PhoneHelper get() {
		return INSTANCE;
	}
	boolean isNotValid(String phone) {
		for(String phoneRegexp : ACCEPTABLE_PHONE_REGEXPS) {
			if(phone.matches(phoneRegexp)) {
				return false;
			}
		}
		return true;
	}
	boolean areEqual(String phone1,String phone2) {
		return getInFormatAgnostic(phone1).equals(getInFormatAgnostic(phone2));
	}
	private String getInFormatAgnostic(String phone) {
		return phone.replace(SEPARATOR, EMPTY);
	}

}
