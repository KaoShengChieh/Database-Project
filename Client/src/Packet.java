public class Packet
{
    private Query query;
    private Response response;
    private boolean queryMutex = true;
    private boolean responseMutex = true; 
  
	public synchronized void setQuery(Query query) {
		while (!queryMutex) {
			try { 
				wait();
			} catch (InterruptedException e)  {
				Thread.currentThread().interrupt(); 
			}
		}
		queryMutex = false;

		this.query = query.clone();
		notifyAll();
	}
	
	public synchronized Query getQuery() {
		while (queryMutex) {
			try {
				wait();
			} catch (InterruptedException e)  {
				Thread.currentThread().interrupt(); 
			}
		}
		queryMutex = true;

		notifyAll();
		return query;
	}
	
	public synchronized void setResponse(Response response) {
		while (!responseMutex) {
			try { 
				wait();
			} catch (InterruptedException e)  {
				Thread.currentThread().interrupt(); 
			}
		}
		responseMutex = false;

		this.response = response;
		notifyAll();
	}
	
	public synchronized Response getResponse() {
		while (responseMutex) {
			try {
				wait();
			} catch (InterruptedException e)  {
				Thread.currentThread().interrupt(); 
			}
		}
		responseMutex = true;

		notifyAll();
		return response;
	}
}
