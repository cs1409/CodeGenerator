package application.generatedClasses;

import java.lang.reflect.Type;

// WHEN NEW CLASSES ARE CREATED, ADD THEM TO BOTH METHODS 

public class IsInstanceHelper {
	
	public static boolean isInstance(Object value) {
				
		boolean isInstance =  value instanceof Vehicle ||  value instanceof Car ||  value instanceof Person ||  value instanceof House;
		
		return isInstance;
	}
	
	public static boolean isClass(Type attributeType) throws ClassNotFoundException {
		
		boolean isClass =  attributeType == Vehicle.class  ||  attributeType == Car.class  ||  attributeType == Person.class  ||  attributeType == House.class ;
				
		return isClass;
	}
	
}
	