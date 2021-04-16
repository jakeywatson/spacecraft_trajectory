import java.io.*;


public class TrajectoryOptimiser {

	public static void main(String [] args) throws IOException {
		
		Console console = System.console();
		int  end_year = console.readLine("Enter the end year of the period to be considered: \n");
		end_year = DateHandler.daysBetween(end_year);
		
		//Instantiate trajectories with randomised values
		List<Trajectory> trajectories = new List<Trajectory>();
		for (int i = 0; i < 100; i++){
			trajectories.add(new Trajectory(end_year));
		}
		
		//Generate Ephemerides
		Ephemerides ephemerides = new Ephemerides(end_year, 1);
		
		//Set vectors of start position and end position for each trajectory from ephemerides
		for (int i = 0; i < 100; i++){
			double start_date = trajectories.get(i).getStart_Date();
			Vector3D start_position = ephemerides.getEarthEphemerides(start_date);
			trajectories.get(i).setStart_Position(start_position);
			
			double end_date = trajectories.get(i).getEnd_Date();
			Vector3D end_position = ephemerides.getMarsEphemerides(end_date);
			trajectories.get(i).setEnd_Position(end_position);
		}
		
		//Use Lambert Solver to find start/end velocities
		for (int i = 0; i < 100; i++){
			List<Vector3D> v_pos = LambertSolver.solve(trajectories.get(i), 1);
			List<Vector3D> v_neg = LambertSolver.solve(trajectories.get(i), -1);
			
			if (vpos.get(0) = 0 && vneg.get(0) = 0){
			} else {
			double deltaV = FindDeltaV.solve(ephemerides, trajectories.get(i), v_pos, v_neg);
			trajectories.get(i).setDeltaV(deltaV);
			}
		}
		
		//Output to file
		Filewriter fileWriter = new Filewriter("Trajectories.txt");
		PrintWriter printWriter = new PrintWriter(fileWriter);
		for (int i = 0; i < 100; i++){
			printWriter.print("%s     %d     %.6f\n", DateHandler.outputDate(trajectories.get(i).getStart_Date()), trajectories(i).getDuration(), trajectories(i).getDeltaV);
		}
		
	}
}
	
}