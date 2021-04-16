public class FindDeltaV {
	
	public static double solveEarthtoMars(Ephemerides ephemerides, Trajectory trajectory, List<Vector3D> velocities_pos1, List<Vector3D> velocities_neg1){
		
		double start_date = (double)trajectory.getStart_Date();
		double end_date = (double)trajectory.getEnd_Date();
		double start_position = trajectory.getStart_Position();
		double end_position = trajectory.getEnd_Position();
		double earth_velocity = ephemerides.getEarthEphemerides(start_date).get(1);
		double mars_velocity = ephemerides.getMarsEphemerides(end_date).get(1);
		
		double mu_earth = 3.986E14;
		double mu_mars = 4.282E13;
		
		double earth_orbit_radius = (6378*1000) + 400000;
		double mars_orbit_radius = (3397*1000) + 400000;
		
		if (Vector3D.magnitude(velocities_pos1.get(0)) == 0.0) {
			double v0 = velocities_neg1.get(0);
			double v = velocities_neg1.get(1);
		} else if (Vector3D.magnitude(velocities_neg1.get(0)) == 0.0) {
			double v0 = velocities_pos1.get(0);
			double v = velocities_pos1.get(1)
		} else if (Vector3D.magnitude(velocities_pos1.get(0)) < Vector3D.magnitude(velocities_neg1.get(0))){
			double v0 = velocities_pos1.get(0);
			double v = velocities_pos1.get(1)
		} else if (Vector3D.magnitude(velocities_neg1.get(0)) < Vector3D.magnitude(velocities_pos1.get(0)))) {
			double v0 = velocities_neg1.get(0);
			double v = velocities_neg1.get(1);
		} else {
			System.out.println('Error: No Lambert solution found.');
		}
		
		//Find initial transfer velocity
		double v_inf_depart = Vector3D.magnitude(Vector3D.vectorsubtract(v0, earth_velocity));
		
		//Find launching velocity
		double launch_velocity = Math.sqrt(2*mu_earth/earth_orbit_radius + v_inf_depart*v_inf_depart);
		
		//Find delta V for entering transfer
		double deltaV1 = launch_velocity - Math.sqrt(mu_earth/earth_orbit_radius);
		
		//Find end transfer velocity
		double v_inf_arrival = Vector3D.magnitude(Vector3D.vectorsubtract(v, mars_velocity));
		
		//Find arrival velocity
		double arrival_velocity = Math.sqrt(2*mu_mars/mars_orbit_radius + v_inf_arrival*v_inf_arrival);
		
		//Find delta V for leaving transfer
		double deltaV2 = arrival_velocity - Math.sqrt(mu_mars/mars_orbit_radius);
		
		return deltaV1 + deltaV2;
	}
		
		
		
		
		
}