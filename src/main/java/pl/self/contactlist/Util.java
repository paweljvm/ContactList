package pl.self.contactlist;

import java.util.Collection;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class Util {

	static void requireNotNullAndNotEmpty(String value,Supplier<RuntimeException> exceptionSupplier) {
		require(Util::isNullOrEmpty,value,exceptionSupplier);
	}	
	static void require(Predicate<String> condition,String value,Supplier<? extends RuntimeException> exceptionSupplier) {
		if(condition.test(value)) {
			throw exceptionSupplier.get();
		}
	}
	static boolean isNotNullAndNotEmpty(Collection<?> collection) {
		return collection != null && !collection.isEmpty();
	}
	
	static boolean isNullOrEmpty(String value) {
		return value == null || value.isEmpty();
	}
}
