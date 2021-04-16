import java.lang.Math;

public class Vector3D {

		    //define private variables for vector components. Unchangeable from outside files.
		    private double x;
		    private double y;
		    private double z;
		    
		    //This constructor creates a zero vector, using set methods defined below.
		    public Vector3D() {
			this.setx(0);
			this.sety(0);
			this.setz(0);
		    }

		    //This constructor creates a vector with variable components, which can be set using the setter methods from the tester program.
		    public Vector3D(double xx, double yy, double zz) {
			this.setx(xx);
			this.sety(yy);
			this.setz(zz);
		    }
		    
		    //Defining setter methods. These change the values of the private variables above to a user defined input value
		    public void setx(double xx) {this.x = xx;}
		    public void sety(double yy) {this.y = yy;}
		    public void setz(double zz) {this.z = zz;}
		    
		    // Defining the getter methods. Simply returns the values of the private variables.
		    public double getx() {return this.x;}
		    public double gety() {return this.y;}
		    public double getz() {return this.z;}
		    
		    //This method defines how to write a vector3d object as a string. Will get called whenever a string is to be written.
		    public String toString() {
			double xx = this.getx();
			double yy = this.gety();
			double zz = this.getz();
			return xx + "x + " + yy + "y + " + zz + "z";
		    }
		    //These static methods provide the standard vector operations. These get called to provide vector algebra. Most of them create new vector3ds, with only magnitude, mag squared and dot product returning doubles.
		    
		    //Magnitude
		    public static double magnitude(Vector3D a) {
			return Math.sqrt((a.getx()*a.getx()) + (a.gety()*a.gety()) + (a.getz()*a.getz()));
		    }
		    //Magnitude squared
		    public static double magsq(Vector3D a) {
			return ((a.getx()*a.getx()) + (a.gety()*a.gety()) + (a.getz()*a.getz()));
		    }
		    //Scalar division
		    public static Vector3D scaldiv(Vector3D a , double b) {
			return new Vector3D(a.getx()/b, a.gety()/b, a.getz()/b);
		    }
		    //Scalar multiplication
		    public static Vector3D scalmulti(Vector3D a, double b) {
			return new Vector3D(a.getx()*b, a.gety()*b, a.getz()*b);
		    }
		    //Vector addition
		    public static Vector3D vectoradd(Vector3D a, Vector3D b) {
			return new Vector3D(a.getx() + b.getx(), a.gety() + b.gety(), a.getz() + b.getz());
		    }
		    //Vector subtraction
		    public static Vector3D vectorsub(Vector3D a, Vector3D b) {
			return new Vector3D(a.getx() - b.getx(), a.gety() - b.gety(), a.getz() - b.getz());
		    }
		    //Cross product
		    public static Vector3D vectorcross(Vector3D a, Vector3D b) {
			return new Vector3D(a.gety()*b.getz() - a.getz()*b.gety(), a.getz()*b.getx() - a.getx()*b.getz(), a.getx()*b.gety() - b.getx()*a.gety());
		    }
		    //Dot Product
		    public static double vectordot(Vector3D a, Vector3D b) {
			return (a.getx()*b.getx() + b.gety()*a.gety() + a.getz()*b.getz());
		    }
		    //This method creates a boolean variable. It subtracts the corresponding x,y,z components from both vectors, each component individually. 
			//If each component subtraction is zero, it returns true. Basically checks if two vectors are equal.
		    public static Boolean vectorcheck(Vector3D a, Vector3D b) {

			double xval = (a.getx()-b.getx());
			double yval = (a.gety()-b.gety());
			double zval = (a.getz()-b.getz());

			boolean xtest = xval <= 0.000001;
			boolean ytest = yval <= 0.000001;
			boolean ztest = zval <= 0.000001;

			Boolean	vectorcheck = xtest = ytest = ztest = true;
			return vectorcheck;
		    }
}

