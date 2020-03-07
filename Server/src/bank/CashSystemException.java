public class CashSystemException extends Exception {
	private static final long serialVersionUID = 1L;
	
	CashSystemException(){
		
	}
	CashSystemException(CashSystemException e){
		super(e);
	}
	CashSystemException(String msg){
		super(msg);
	}
}
