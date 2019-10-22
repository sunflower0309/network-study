package test;

import java.lang.reflect.*;

public class MethodPointer{
	public static void main(String[] args) throws Exception{
		Method square=MethodPointer.class.getMethod("square", double.class);
		Method sqrt=Math.class.getMethod("sqrt", double.class);
		Class cl=Double.class;
		Class cll=double.class;
		Constructor[] cons=cl.getDeclaredConstructors();
		Constructor[] cons1=cll.getDeclaredConstructors();
		System.out.println(cl);
		System.out.println(cll);
		/*
		 * printnum((double)1,10,10,square); printnum((double)1,10,10,sqrt);
		 */
	}
	
	public static double square(double x) {
		return x*x;
	}
	
	public static void printnum(Double from, double to, double x, Method m) {
		double dx=(to-from)/(x-1);
		System.out.println(m);
		for(double i=from;i<=to;i+=dx) {
			try {
				double a=(double)m.invoke(null, i);
				System.out.println(i+"   "+a);
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}