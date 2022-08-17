package com.javafxdemo;

import java.io.IOException;

//this interface controls everything that's reusable in the code, and acts as a helper class
public interface ReusableInterface {

	default void exit() {
		System.exit(0);
	} //reusable exit method
	void backMethod() throws IOException; //reusable back method, although very simple
}
