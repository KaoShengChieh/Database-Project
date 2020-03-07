public class ManagerException extends Exception {
	private static final long serialVersionUID = 1L;
	
	ManagerException(){
		
	}
	ManagerException(ManagerException e){
		super(e);
	}
	ManagerException(String msg){
		super(msg);
	}
}
