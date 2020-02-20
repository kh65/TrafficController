import java.util.concurrent.locks.* ;
public class TrafficControllerFair implements TrafficController {

private TrafficRegistrar registrar;
private boolean bridge_free=true;
Lock lock = new ReentrantLock(true);
Condition placeEmpty = lock.newCondition();
	public TrafficControllerFair(TrafficRegistrar r) {
		this.registrar = r;
	}
	
	public void enterRight(Vehicle v)   { 
		lock.lock();
		while (!bridge_free) {
		try
		{
			 placeEmpty.await();
		}
		catch(InterruptedException ex)
		{
		   
		}
		}
		bridge_free=false;
		registrar.registerRight(v);       
	
	}
	
	public  void enterLeft(Vehicle v) {
		lock.lock();
		while (!bridge_free) {
		try
		{
			  placeEmpty.await();
		}
		catch(InterruptedException ex)
		{
		   
		}
		}
		bridge_free=false;
		registrar.registerLeft(v);   
	}
	
	public  void leaveLeft(Vehicle v) {
		placeEmpty.signalAll(); 
		   lock.unlock();
		bridge_free=true;
		registrar.deregisterLeft(v);      
	}
	
	public  void leaveRight(Vehicle v) { 
		placeEmpty.signalAll();
		   lock.unlock();

		bridge_free=true;
		registrar.deregisterRight(v); 
	}
}