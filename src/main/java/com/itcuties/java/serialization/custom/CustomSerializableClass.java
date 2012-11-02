package com.itcuties.java.serialization.custom;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamException;
import java.io.Serializable;

/**
 * This class will deomnstrate how to use custom serialization mechanism.
 * Implementing the following methods:
 * <li> private void writeObject(ObjectOutputStream oos) throws IOException;
 * <li> private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException;
 * will cause the default serialization mechanism to be overwridden.
 * 
 * <b>IMPORTANT</b>
 * It is important to make sure that the order of writing and reading the data is the same.
 * Otherwise errors might occur od data might not be exactly the same as before serialization.
 * 
 * @author itcuties
 */
public class CustomSerializableClass implements Serializable {
	private static final long serialVersionUID = 5627510126395562166L;

	private int number;
	private String string;
	/** Transient fields may or may be not serialized. This is your choice. */
	private transient String transientString;
	
	public CustomSerializableClass(int number, String string, String transientString) {
		this.number = number;
		this.string = string;
		this.transientString = transientString;
	}

	@Override
	public String toString() {
		return "CustomSerializableClass [number=" + number + ", string="
				+ string + ", transientString=" + transientString + "]";
	}

	/**
	 * This method is private but it is called using reflection by java
	 * serialization mechanism. It overwrites the default object serialization.
	 * 
	 * <br/><br/><b>IMPORTANT</b>
	 * The access modifier for this method MUST be set to <b>private</b> otherwise {@link java.io.StreamCorruptedException}
	 * will be thrown.
	 * 
	 * @param oos
	 *            the stream the data is stored into
	 * @throws IOException
	 *             an exception that might occur during data storing
	 */
	private void writeObject(ObjectOutputStream oos) throws IOException {
		System.out.println(CustomSerializableClass.class.getName() + ".writeObject");
		oos.writeObject(string);			//write any obejct here
		oos.writeInt(number);				//write all kinds of known primitives
		
		oos.writeFloat(1.234f);				//feel free to add data that is not in the class' fields
		oos.writeObject(transientString);	//transient keyword is not significant here
	}

	/**
	 * This method is private but it is called using reflection by java
	 * serialization mechanism. It overwrites the default object serialization.
	 * 
	 * <br/><br/><b>IMPORTANT</b>
	 * The access modifier for this method MUST be set to <b>private</b> otherwise {@link java.io.StreamCorruptedException}
	 * will be thrown.
	 * 
	 * @param oos
	 *            the stream the data is read from
	 * @throws IOException
	 *             an exception that might occur during data reading
	 * @throws ClassNotFoundException
	 *             this exception will be raised when a class is read that is
	 *             not known to the current ClassLoader
	 */
	private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        System.out.println(CustomSerializableClass.class.getName() + ".readObject");
        
        string = (String) ois.readObject();
        number = ois.readInt();
        
        float customFloat = ois.readFloat();
        System.out.println("Read custom data that is not in the fields: " + customFloat);
        
        transientString = (String) ois.readObject();
	}
	
	/**
	 * This method is called after the deserialization has been done.
	 * Here you can make some final touches and do custom stuff with your deserialized instance.
	 * The access modifier can be any one you like.
	 * 
	 * @return a reference to <b>this</b> object
	 * @throws ObjectStreamException throw this exception if you figure that something is wrong with your object
	 */
	private /*or public od protected or default*/ Object readResolve() throws ObjectStreamException {
		System.out.println(CustomSerializableClass.class.getName() + ".readResolve");
		System.out.println("Feel free to do custom stuff here.");
		string += " (object modified in 'readResolve' method)";
		return this;
	}
}
