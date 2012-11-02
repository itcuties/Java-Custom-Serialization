package com.itcuties.java.serialization.custom;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamException;

/**
 * This class will demonstrate how custom serialization methods are invoked when serializing or deserializing
 * class that derives from other class.
 * 
 * It works much like constructors. First invoke the superclass write/read method and then the current class methods.
 * 
 * @author itcuties
 */
public class CustomInheritedSerializableClass extends CustomSerializableClass {
	/** Inherited class MUST define its own serialVersionUID. */
	private static final long serialVersionUID = -4474814700665637815L;
	
	private String additionalField;
	
	public CustomInheritedSerializableClass(int number, String string, String transientString, String additionalField) {
		super(number, string, transientString);
		this.additionalField = additionalField;
	}
	
	@Override
	public String toString() {
		return "CustomInheritedSerializableClass [additionalField="
				+ additionalField + ", toString()=" + super.toString() + "]";
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
		System.out.println(CustomInheritedSerializableClass.class.getName() + ".writeObject");
		oos.writeObject(additionalField);
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
        System.out.println(CustomInheritedSerializableClass.class.getName() + ".readObject");
        additionalField = (String)ois.readObject();
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
		System.out.println(CustomInheritedSerializableClass.class.getName() + ".readResolve");
		System.out.println("Feel free to do custom stuff here.");
		additionalField += " (object modified in 'readResolve' method)";
		return this;
	}
}
