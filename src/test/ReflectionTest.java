package test;
import java.util.*;
import java.lang.reflect.*;
public class ReflectionTest {
	
	public static void main(String args[]){
		String name;
		Scanner in = new Scanner(System.in);
		name=in.next();
		try{
			Class cl=Class.forName(name);
			Class supercl=cl.getSuperclass();
			String modifiers=Modifier.toString(cl.getModifiers());
			if(modifiers.length()>0) System.out.print(modifiers+" ");
			System.out.print("class "+name);
			if(supercl!=null&&supercl!=Object.class) System.out.print(" extends "+supercl.getName());
			System.out.print("\n{\n");
			
			printConstructors(cl);
			printMethods(cl);
			printParams(cl);
		}
		catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public static void printConstructors(Class c) {
		Constructor[] constructors=c.getDeclaredConstructors();
		for(Constructor co:constructors) {
			String name=co.getName();
			String modi=Modifier.toString(co.getModifiers());
			if(modi.length()>0) System.out.print(modi+" ");
			System.out.print(name+"(");
			
			Class[] params=co.getParameterTypes();
			for(Class pa:params) {
				System.out.print(pa.getName()+" ");
			}
			System.out.print(");\n");
		}
	}
	public static void printMethods(Class c) {
		Method[] methods=c.getDeclaredMethods();
		for(Method me:methods) {
			String modi=Modifier.toString(me.getModifiers());
			String name=me.getName();
			if(modi.length()>0) System.out.print(modi+" ");
			System.out.print(name+"(");
			Class[] params=me.getParameterTypes();
			for(Class cl:params) {
				System.out.print(cl.getName());
			}
			System.out.print(");\n");
		}
	}
	public static void printParams(Class c) {
		Field[] fields=c.getDeclaredFields();
		for(Field f:fields) {
			Class cl=f.getType();
			String name=f.getName();
			String modi=Modifier.toString(f.getModifiers());
			if(modi.length()>0) System.out.print(modi+" ");
			System.out.print(cl.getName()+" "+name+";");
			System.out.print("\n");
		}
	}
}


