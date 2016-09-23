package com.sumit.java8.practise.MethodReferences;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

public class Car {
	public static Car create(final Supplier<Car> supplier) {
		return supplier.get();
	}

	public static void collide(final Car car) {
		System.out.println("Collided " + car.toString());
	}

	public void follow(final Car another) {
		System.out.println("Following the " + another.toString());
	}

	public void repair() {
		System.out.println("Repaired " + this.toString());
	}
	
	public static void main(String[] args) {
		final Car car = Car.create(Car::new);
		final List<Car> cars = Arrays.asList(car);
		/**
		 * The second type is reference to static method with the syntax Class::static_method. 
		 * Please notice that the method accepts exactly one parameter of type Car.
		 */
		cars.forEach(Car::collide);
		
		cars.forEach(Car::repair);
		
		final Car police = Car.create( Car::new );
		cars.forEach( police::follow );
	}
}