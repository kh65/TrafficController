
public class TrafficControllerSimple implements TrafficController{
private TrafficRegistrar registrar;
private int bridge_free_left=0;
private int bridge_free_right=0;

	
	public TrafficControllerSimple(TrafficRegistrar r) {
		this.registrar = r;
	}
	
	public synchronized void enterRight(Vehicle v) { 
		while (bridge_free_right>=2 || bridge_free_left>=1 ) {
			try { wait(); }
			catch(InterruptedException e) {}
			}
		bridge_free_right++;

		registrar.registerRight(v);       
	}
	
	public synchronized void enterLeft(Vehicle v) {
		while (bridge_free_left >=2 || bridge_free_right>=1 ) {
			try { wait(); }
			catch(InterruptedException e) {}
			}
		bridge_free_left++;
		System.out.println(bridge_free_right + " " + bridge_free_left);

		registrar.registerLeft(v);   
	}
	
	public synchronized void leaveLeft(Vehicle v) { 
		bridge_free_right--;
		notifyAll();
		registrar.deregisterLeft(v);      
	}
	
	public synchronized void leaveRight(Vehicle v) { 
		bridge_free_left--;
		notifyAll();
		registrar.deregisterRight(v); 
	}
}
