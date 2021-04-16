import java.util.Random;

public class Trajectory {
	
	//Member Variables
	
	private int start_date;
	private int end_date;
	private Vector3D start_velocity;
	private Vector3D end_velocity;
	private Vector3D start_position;
	private Vector3D end_position;
	private double deltaV;
	
	//Creates Trajectory object with randomised start date, random duration
	public Trajectory(double period_end) {
		
		Random rand = new Random();
		
		this.start_date = 
			rand.nextInt((period_end - 400) + 1);
		
		this.end_date = 
			rand.nextInt((400 - 150) + 1) + start_date;
			
	}
	
	
	//Getters
	
	public int getStart_Date(){
		return start_date;
	}
	
	public int getEnd_Date(){
		return end_date;
	}
	
	public int getDuration(){
		return end_date - start_date;
	}
	
	public Vector3D getStart_Velocity(){
		return start_velocity;
	}
	
	public Vector3D getEnd_Velocity(){
		return end_velocity;
	}
	
	public Vector3D getStart_Position(){
		return start_position;
	}
	
	public Vector3D getEnd_Position(){
		return end_position;
	}
	
	public double getDeltaV(){
		return deltaV;
	}
	
	
	//Setters
	
	public void setStart_date(int new_start_date){
		start_date = new_start_date;
	}
	
	public void setEnd_date(int new_end_date){
		end_date = new_end_date;
	}
	
	public void setStart_Velocity(Vector3D new_start_velocity){
		start_velocity = new_start_velocity;
	}
	
	public void setEnd_Velocity(Vector3D new_end_velocity){
		end_velocity = new_end_velocity;
	}
	
	public void setStart_Position(Vector3D new_start_position){
		start_position = new_start_position;
	}
	
	public void setEnd_Position(Vector3D new_end_position){
		end_position = new_end_position;
	}
	
	public void setDeltaV(double new_delta_v){
		deltaV = new_delta_v;
	}
}	