package model;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;

/**
 * @author Matthew Subido
 * @version November 13 2016
 */
public final class Serializer {
	private Serializer(){}
	
	public static void writeFile(final Serializable theObject, final String theFileName) {
		try (
			OutputStream file = new FileOutputStream(theFileName);
			OutputStream buffer = new BufferedOutputStream(file);
			ObjectOutput output = new ObjectOutputStream(buffer);
		){
			output.writeObject(Data.getInstance());
		}  
		catch(IOException ex) {
			ex.printStackTrace();
		}
	}
	
	public static Object readFile(final String theFileName) {
		Object obj = null;
		try(
			InputStream file = new FileInputStream(theFileName);
			InputStream buffer = new BufferedInputStream(file);
			ObjectInput input = new ObjectInputStream (buffer);
		){
			obj = input.readObject();
		}
	    catch(ClassNotFoundException ex){
	    	ex.printStackTrace();
	    }
		catch(IOException ex){
			ex.printStackTrace();
		}
		return obj;
	}
}
