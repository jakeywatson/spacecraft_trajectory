public class LambertSolver{
	
	public static List<double> FindC2C3(double psi){
		double c2;
		double c3;
		List<double> c2c3 = new List<double>();
		
		if (psi > 1E-6){
			c2 = (1.0 - Math.cos(Math.sqrt(psi)))/psi;
			c3 = (Math.sqrt(psi) - Math.sin(Math.sqrt(psi)))/Math.sqrt(psi*psi*psi);
		} else if (psi < 1E-6){
			c2 = (1.0 - Math.cosh(Math.sqrt(-psi)))/psi;
			c3 = (Math.sinh(Math.sqrt(-psi)) - Math.sqrt(-psi))/Math.sqrt((-psi)*(-psi)*(-psi);
		} else {
			c2 = 1.0/2.0;
			c3 = 1.0/6.0;
		}
		c2c3.add(c2);
		c2c3.add(c3);
		return c2c3;
	}

	public static List<Vector3D> solve(Trajectory trajectory, int dir) {
		
		List<Vector3D> velocities = new List<double>();
		
		Vector3D initial_position = trajectory.getStart_Position();
		Vector3D end_position = trajectory.getEnd_Position();
		double duration = trajectory.getDuration()*86400; //Convert duration from days to seconds
		
		double initial_position_mag = initial_position.magnitude();
		double end_position_mag = end_position.magnitude();
		double mu_sun = 1.327E20;
		
		double cos_nu = Vector3D.vectordot(end_position, initial_position)/(initial_position_mag*end_position_mag);
		
		double A = dir * Math.sqrt(initial_position_mag*end_position_mag*(1 + cos_nu));
	
		double psi_n = 0.0;
		double c2 = 1.0/2.0;
		double c3 = 1.0/6.0;
		double psi_up = 4*Math.PI*Math.PI;
		double psi_low = -4*Math.PI;
		
		boolean converge = false;
		int count = 0;
		
		while (converge == false) {
			
			double y_n = initial_position_mag + end_position_mag + A*(psi_n*c3 - 1)/Math.sqrt(c2);
			
			double chi_n = Math.sqrt(y_n/c2);
			
			double delta_tn = (chi_n*chi_n*chi_n*c3 + A*Math.sqrt(y_n))/Math.sqrt(mu_sun);
			
			if (delta_tn <= duration){
				psi_low = psi_n;
			} else {
				psi_up = psi_n;
			}
			
			double psi_np1 = (psi_up + psi_low)/2.0;
			
			List<double> c2c3 = FindC2C3(psi_np1);
			
			psi_n = psi_np1;
			
			count = count + 1;
			
			if (Math.abs(delta_tn - duration) < 1E-3 || count > 10000){
				converge = true;
			}
			
		}
	
		if (count > 10000) {
			velocities.add(new Vector3D);
			velocities.add(new Vector3D);
			return velocities;
		}
		
		double f = 1 - y_n/initial_position_mag;
		double gdot = 1 - y_n/end_position_mag;
		
		double g = A*Math.sqrt(y_n/mu_sun);
		
		Vector3D v = Vector3D.scaldiv(Vector3D.vectorsubtract(end_position, Vector3D.scalmulti(initial_position, f)), g);
		Vector3D v0 = Vector3D.scaldiv(Vector3D.vectorsubtract(Vector3D.scalmulti(end_position, gdot), initial_position), g);
		
		velocities.add(v0);
		velocities.add(v);
		return velocities;
	}
}