import java.io.*;
import java.util.Scanner;

public class Particle3D {


    //Properties

    private double mass;
    private Vector3D position;
    private Vector3D velocity;
    private String name;

    //Getters and Setters

    public double getMass() { return mass; }
    public Vector3D getPosition() { return position; }
    public Vector3D getVelocity() {return velocity; }
    public String getName() { return name; }

    //sets position/velocity from input vectors
 
    public void setPosition(Vector3D pos) {
		position = pos;
    }
    public void setVelocity(Vector3D vel) {
		velocity = vel;
    }
    
    //Sets mass and label 

    public void setMass(double m) {
		this.mass = m; 
	}
    public void setName(String s) {
		this.name = s; 
	}

    //Constructors

    //Sets from input vectors
   public Particle3D(double m, Vector3D p, Vector3D v, String label) {
		this.setMass(m);
		this.setPosition(p);
		this.setVelocity(v);
		this.setName(label);
    }

        //Creates a basic particle at origin with mass of one and zero velocity.
    public Particle3D() {
		this.setMass(1);
		this.setPosition(new Vector3D());
		this.setVelocity(new Vector3D());
		this.setName("One");
    }
    //tostring method
    public String toString() {
		return "Label = " + this.getName() + ", Position = " + this.getPosition();
    }

    //Methods



    //computes kinetic energy
    public static double kinetic(Particle3D a) {
		double ke = 0.5*(a.getMass())*(Vector3D.magsq(a.getPosition()));
		return ke;
    }

    //computing separation vector of two particles
    public static Vector3D particlesepvector(Particle3D a, Particle3D b) {
	   Vector3D separationvector = Vector3D.vectorsub(b.getPosition(), a.getPosition());
		return separationvector;
    }

    //Integration: evolve velocity, vector
    public void leapVelocity(double dt, Vector3D force) {
		//v = u +f/m * dt
		double toverm = dt/mass;
		Vector3D term = Vector3D.scalmulti(force, toverm);
		velocity = Vector3D.vectoradd(velocity, term);
    }

    //evolve position, vector
    public void leapPosition(double dt) {
		position = Vector3D.vectoradd(position, Vector3D.scalmulti(velocity, dt));
    }

    //find force between two particles
    public static Vector3D force(Particle3D a, Particle3D b) {
		double sep = Vector3D.magnitude(Particle3D.particlesepvector(a, b));
		double m1m2overrsq = (6.67E-11)*a.getMass()*b.getMass()*1/(Math.pow(sep, 2));
		Vector3D unitvector = Vector3D.scaldiv(Particle3D.particlesepvector(a, b), sep);
		Vector3D force = Vector3D.scalmulti(unitvector, m1m2overrsq);
		return force;
    }

    //find energy of two particles
    public static double energy(Particle3D a, Particle3D b) {
		double sep = Vector3D.magnitude(Particle3D.particlesepvector(a, b));
		double gpe = (-1)*a.getMass()*b.getMass()*(1/Math.pow(sep, 2));
		double ke = Vector3D.magsq(a.getVelocity())* 0.5*a.getMass() + Vector3D.magsq(b.getVelocity()) * 0.5*b.getMass(); 
		double energy = ke + gpe;
		return energy;
    }

    //instance for counting number of particles

    public static int particlecount(Scanner scan) {
		int LineCount = 0;
		while (scan.hasNextLine()) {
			LineCount = LineCount + 1;
			scan.nextLine();
		}
		int particlecount = LineCount;
		return particlecount;
    }

    //instance method for changing initialized particle from filescan.
    public void particlescan(Scanner scan) {

		//sets position and velocity from scanner - multiply by 1000 to get km/s into m/s
		position.setx(scan.nextDouble()*1000.0);
		position.sety(scan.nextDouble()*1000.0);
		position.setz(scan.nextDouble()*1000.0);

		velocity.setx(scan.nextDouble()*1000.0);
		velocity.sety(scan.nextDouble()*1000.0);
		velocity.setz(scan.nextDouble()*1000.0);
		
		//sets mass and name to scanned input.
		setMass(scan.nextDouble());
		setName(scan.next());
    }
  
  //creating array, scans in particles from given file
 
    public static Particle3D[] ParticleArray(String Filename)throws FileNotFoundException {

		BufferedReader file = new BufferedReader(new FileReader(Filename));
		Scanner scan = new Scanner(file);

		int LineCount = 0;
		while (scan.hasNextLine()) {
			LineCount = LineCount + 1;
			scan.nextLine();
		}

		BufferedReader file1 = new BufferedReader(new FileReader(Filename));
		Scanner scan1 = new Scanner(file1);
		int particlecount = LineCount;

		Particle3D[] Particles = new Particle3D[particlecount];
		
		for (int i=0; i < particlecount; i++) {
			Particle3D A = new Particle3D();
			A.particlescan(scan1);
			Particles[i] = A;
		}
		return Particles;
    }
	
    //method for finding total linear momentum of system, changing velocity of sun to compensate
    public static Particle3D[] CorrectedParticlesArray(int particlecount, Particle3D[] Particles){

		Vector3D TotalMomentum = new Vector3D();

		for (int i=0; i<particlecount;i++) {
			//Finding total planetary momentum  
			Vector3D Momentum = new Vector3D();
			Momentum = Vector3D.scalmulti(Particles[i].getVelocity(), Particles[i].getMass());
			TotalMomentum = Vector3D.vectoradd(TotalMomentum, Momentum);
		}
		
		Vector3D sunvelocity = Vector3D.scalmulti(TotalMomentum, -1/(Particles[0].getMass()));
		Particles[0].setVelocity(sunvelocity);
		return Particles;
    }


    //updates force on particles in array, returns array of forces
    public static Vector3D[] ForceArray(int particlecount, Particle3D[] Particles) { 

		Vector3D[] forces = new Vector3D[particlecount];

		for (int i = 0; i < particlecount; i++) {
			forces[i] = new Vector3D();
		}
		for (int i = 0; i < particlecount; i++) {	
			for (int j = 0; j < particlecount; j++)
			if (i != j) {
				forces[i] = Vector3D.vectoradd(forces[i], Particle3D.force(Particles[i], Particles[j]));
			}else{
				forces[i] = forces[i];
			}
		}
		return forces;
    }
	
    //updates velocities for particle array
    public static void leapVelocityArray(int particlecount, double dt, Particle3D[] Particles, Vector3D[] forces) {
		for (int k = 0; k < particlecount; k++) {
			Particles[k].leapVelocity(dt, forces[k]);   
		}
    }
	
    //updates positions for particle array
    public static void leapPositionArray(int particlecount, double dt, Particle3D[] Particles) {
		for (int k = 0; k < particlecount; k++) {
			Particles[k].leapPosition(dt);
		}
    }
	

    //method to write the positions of particles to file from the array. vmd format.
    public static void output(Particle3D[] Particles, int count, double t, PrintWriter output)throws IOException{
		output.printf("%s\n", count);
		output.printf("Point = %10.5f\n", t);
		for (int i=0;i<count; i++){
			output.printf("%s %10.5f %10.5f %10.5f\n", Particles[i].getName(), (Particles[i].getPosition().getx()),(Particles[i].getPosition().gety()), (Particles[i].getPosition().getz()) );

		}
    }
	
	public static List<Vector3D> to_ephemeris_earth (Particle3D[] Particles){
		List<Vector3D> state_vector = new List<Vector3D>();
		state_vector.add(Particles[4].getPosition());
		state_vector.add(Particles[4].getVelocity());
	}
	
	public static List<Vector3D> to_ephemeris_mars (Particle3D[] Particles){
		List<Vector3D> state_vector = new List<Vector3D>();
		state_vector.add(Particles[5].getPosition());
		state_vector.add(Particles[5].getVelocity());
	}
}
