package com.sumit.java8.practise.defaultImplAndStaticMethod;

public interface Defaulable {
	// Interfaces now allow default methods, the implementer may or
	// may not implement (override) them.
	default String notRequired() {

		return "Default implementation";

	}

}



