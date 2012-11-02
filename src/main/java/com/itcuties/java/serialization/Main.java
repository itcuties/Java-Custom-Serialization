package com.itcuties.java.serialization;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import com.itcuties.java.serialization.custom.CustomInheritedSerializableClass;
import com.itcuties.java.serialization.custom.CustomSerializableClass;

/**
 * The class to present serialization issues.
 * 
 * @author itcuties
 */
public class Main {
	
	public static void main(String[] args) {
		
		//Main.presentCustomSerialization();
		//System.out.println("==========================================");
		Main.presentCustomSerializationInInheritedClasses();
		
	}
	
	/**
	 * This method presents how to serialize and deserialize using custom defined methods.
	 */
	private static void presentCustomSerialization() {
		System.out.println("Running custom serialization.");
		
		//first create an instance of a class and fill it with data
		CustomSerializableClass customSerializableClass = new CustomSerializableClass(1234, "1234", "Transient:1234");
		
		Main.serializeAndDeserialize(customSerializableClass, "custom-serialization.dat");
	}
	
	/**
	 * This method presents how to serialize and deserialize using custom defined methods.
	 */
	private static void presentCustomSerializationInInheritedClasses() {
		System.out.println("Running custom serialization in inherited classes.");
		
		//first create an instance of a class and fill it with data
		CustomInheritedSerializableClass customSerializableClass = new CustomInheritedSerializableClass(1234, 
																										"1234", 
																										"Transient:1234", 
																										"additional field");
		
		Main.serializeAndDeserialize(customSerializableClass, "custom-inherited-serialization.dat");
	}
	
	/**
	 * An utility method to serialize - deserialize the given instance.
	 * 
	 * @param objectToSerialize
	 *            instance to be serialized-deserialized
	 * @param serializationFileName
	 *            serialization file name
	 * @return deserialized object
	 */
	private static Serializable serializeAndDeserialize(Serializable objectToSerialize, String serializationFileName) {
		System.out.println("Data that will be serialized: " + objectToSerialize);
		
		//open the stream where the data will be put
		ObjectOutputStream oos = null;
		try {
			FileOutputStream fos = new FileOutputStream(serializationFileName);
			
			//ObjectOutputStream is the stream used to serialize the object
			oos = new ObjectOutputStream(fos);
			
			System.out.println("Serializing the created instance.");
			oos.writeObject(objectToSerialize);
			oos.flush();
			System.out.println("Object is succesfully serialized.");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(oos != null) {
				try {
					oos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		System.out.println("Now the object will be deserialized.");
		Serializable deserializedInstance = null;
		ObjectInputStream ois = null;
		try {
			FileInputStream fis = new FileInputStream(serializationFileName);
			
			//ObjectInputStream is the stream used to deserialize the object
			ois = new ObjectInputStream(fis);
			
			deserializedInstance = (Serializable) ois.readObject();
			System.out.println("Deserialized data: " + deserializedInstance);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			if(ois != null) {
				try {
					ois.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return deserializedInstance;
	}
}
