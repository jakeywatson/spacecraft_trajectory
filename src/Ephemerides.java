import java.util.Scanner;
import java.util.*;

public class Ephemerides {
	
	//Using 2 maps to store the position and velocity mapped to the date for Mars and Earth
	private Map<double, List<Vector3D>> ephemeris_earth = new HashMap<double, List<Vector3D>>();
	private Map<double, List<Vector3D>> ephemeris_mars = new HashMap<double, List<Vector3D>>();
	
	public Ephemerides(double end_date, double ts){
		
		//reading input ephemeris - 01/01/2020
		String Filename = "solarinput-01012020.txt";

		//Creating and filling array
		Particle3D[] Particlesinitial = Particle3D.ParticleArray(Filename);

		//Counting Particles
		BufferedReader file = new BufferedReader(new FileReader(Filename));
		Scanner scan = new Scanner(file);
		int count = Particle3D.particlecount(scan);
		
		//Finds momentum of sun, sets velocity accordingly
		Particle3D[] Particles = Particle3D.CorrectedParticlesArray(count, Particlesinitial);

		//Initial Conditions
		double t = 0;
		double timestep = ts; //timestep is in seconds - 1 day in seconds?
		
		//Writing Initial Conditions to ephemeris
		ephemeris_earth.put(t, Particle3D.to_ephemeris_earth(Particles));
		ephemeris_mars.put(t, Particle3D.to_ephemeris_mars(Particles));
		
		//Euler Velocity Update
		Vector3D[] forceinitial = Particle3D.ForceArray(count, Particles);
		Particle3D.leapVelocityArray(count, dt*0.5, Particles, forceinitial);

		//define number of loops - number of timesteps between start and end dates
		int numstep = end_date/timestep) + 10;

		//main simulation loop
		for (int i = 0; i < numstep; i++) {
			
			//update position
			Particle3D.leapPositionArray(count,dt,Particles);
			
			//update time
			t = t + dt;
			
			//Write results to ephemeris
			ephemeris_earth.put(t, Particle3D.to_ephemeris_earth(Particles));
			ephemeris_mars.put(t, Particle3D.to_ephemeris_mars(Particles));

			//update force
			Vector3D[] force = Particle3D.ForceArray(count, Particles);

			//update velocity
			Particle3D.leapVelocityArray(count, dt, Particles, force);
		}
	}
	
	//Returns state vector of Earth/Mars for given date
	public List<Vector3D> getEarthEphemerides(double t){
		return ephemeris_earth.get(t);
	}
	
	public List<Vector3D> getMarsEphemerides(double t){
		return ephemeris_mars.get(t);
	}
}
