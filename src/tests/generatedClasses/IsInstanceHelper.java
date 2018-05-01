package tests.generatedClasses;

import java.lang.reflect.Type;

// THIS CLASS IS RESPONSIBLE FOR CHECKING IF A TYPE IS AN INSTANCE OF A GENERATED CLASS

public class IsInstanceHelper {
	
	public static boolean isInstance(Object value) {
				
		boolean isInstance = value instanceof Author || value instanceof Book || value instanceof Publisher || value instanceof House 
				|| value instanceof Person;
		
		return isInstance;
	}
	
	public static boolean isClass(Type attributeType) throws ClassNotFoundException {
		
		boolean isClass = attributeType == Author.class || attributeType == Book.class || attributeType == Publisher.class ||
				attributeType == House.class || attributeType == Person.class;
			
		return isClass;
	}
}
	