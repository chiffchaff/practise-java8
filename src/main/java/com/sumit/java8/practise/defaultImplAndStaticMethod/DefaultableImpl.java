package com.sumit.java8.practise.defaultImplAndStaticMethod;

class DefaultableImpl implements Defaulable {
	public static void main( String[] args ) {

	    Defaulable defaulable = DefaulableFactory.create( DefaultableImpl::new );

	    System.out.println( defaulable.notRequired() );

	         

	    defaulable = DefaulableFactory.create( OverridableImpl::new );

	    System.out.println( defaulable.notRequired() );

	}
}

