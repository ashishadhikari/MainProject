
package application;

import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;
import javax.swing.JTable;

import application.gui.CustomTableModel;
import application.gui.EbazaarMainFrame;
import application.gui.SelectOrderWindow;
import application.gui.ViewOrderDetailsWindow;
import business.SessionContext;
import business.externalinterfaces.CustomerConstants;
import business.externalinterfaces.ICustomerSubsystem;

/**
 * @author pcorazza
 */
public enum ViewOrdersController implements CleanupControl  {
	//This makes ManageProductsController a Singleton. Since controllers
	//monitor the states of the windows under their control, they
	//need to be singletons. This style for implementing the Singleton
	//pattern is explained in Effective Java, 2nd ed.
	INSTANCE;
	

    /////////// EVENT HANDLERS -- new code goes here ////////////  
 
    //// control SelectOrderWindow
	class ViewOrderDetailsListener implements ActionListener {
	 	final String ERROR_MESSAGE = "Please select a row.";
		final String ERROR = "Error"; 	    
        public void actionPerformed(ActionEvent evt) {
            JTable table = selectOrderWindow.getTable();
            CustomTableModel model = selectOrderWindow.getModel();
        	int selectedRow = table.getSelectedRow();
        	if(selectedRow >= 0) {
                //start by reading order id from screen
         		selectOrderWindow.setVisible(false);
                @SuppressWarnings("unused")
				String selOrderId = (String)model.getValueAt(selectedRow,0);
                //now get customer from SessionContext, getOrderHistory
                //and then read the appropriate order from the history, using order id
                
                //default implementation
                selectOrderWindow.setVisible(false);
                viewOrderDetailsWindow  = new ViewOrderDetailsWindow();
                viewOrderDetailsWindow.setVisible(true);
       		
        	}
        	else {
       			JOptionPane.showMessageDialog(selectOrderWindow,         									          
        									  ERROR_MESSAGE,
        									  ERROR, 
        									  JOptionPane.ERROR_MESSAGE);
        		
        	}        	
        	        	

        }
	}
	class CancelViewOrdersListener implements ActionListener {
        public void actionPerformed(ActionEvent evt) {
        	selectOrderWindow.setVisible(false);
        	BrowseAndSelectController.INSTANCE.makeMainFrameVisible();

        }
	}
	
	///// control of ViewOrderDetailsWindow
	class OrderDetailsOkListener implements ActionListener {
        public void actionPerformed(ActionEvent evt) {
			selectOrderWindow.setVisible(true);
			
			viewOrderDetailsWindow.dispose();
        }
	}	
    
	class SelectOrderActionListener implements ActionListener,Controller {
        /* this method is called when LoginControl needs this class to load order 
         * history data for newly logged in customer
         */
		public void doUpdate() {
			if(selectOrderWindow != null){
                //implement by reading order history from customer
                //customer should be available in SessionContext
			}
		}	    
		public void actionPerformed(ActionEvent e) {
            SessionContext ctx = SessionContext.INSTANCE;
            Boolean loggedIn = (Boolean)ctx.get(CustomerConstants.LOGGED_IN);
            ICustomerSubsystem ss = (ICustomerSubsystem)ctx.get(CustomerConstants.CUSTOMER);
            if(!loggedIn.booleanValue()) {
                selectOrderWindow = new SelectOrderWindow();
                LoginControl loginControl = 
                    new LoginControl(selectOrderWindow,
                                     mainFrame);
                loginControl.startLogin();
            } 
            else {
            
                //default implementation, runs when user has logged in
                selectOrderWindow = new  SelectOrderWindow();
                selectOrderWindow.setVisible(true);
                mainFrame.setVisible(false);
            }
		}
	}       
 
    ///////// PUBLIC INTERFACE -- for getting instances of listeners ///
	public ActionListener getViewOrderDetailsListener(SelectOrderWindow w){
	    return (new ViewOrderDetailsListener());
	}    
	public ActionListener getCancelViewOrdersListener(SelectOrderWindow w){
	    return (new CancelViewOrdersListener());
	}  
	
	public ActionListener getOrderDetailsOkListener(ViewOrderDetailsWindow w){
	    return (new OrderDetailsOkListener());
	}  	
    public ActionListener getSelectOrderActionListener(EbazaarMainFrame f){
        return (new SelectOrderActionListener());
    }   
    
    ////////  PUBLIC ACCESSORS to register screens controlled by this class////
	public void setSelectOrderWindow(SelectOrderWindow w){
	    selectOrderWindow = w;
	}      
	public void setViewOrderDetailsWindow(ViewOrderDetailsWindow w){
	    viewOrderDetailsWindow = w;
	}     
	public void setMainFrame(EbazaarMainFrame w){
	    mainFrame = w;
	}    
    /////// screens -- private references
    private SelectOrderWindow selectOrderWindow;
    private ViewOrderDetailsWindow viewOrderDetailsWindow;
    private EbazaarMainFrame mainFrame;
	private Window[] allWindows = {
			selectOrderWindow,
			viewOrderDetailsWindow,
			mainFrame
	};
	public void cleanUp(){
		for(Window w : allWindows){
			if(w != null){
				System.out.println("Disposing of window "+w.getClass().getName());
				w.dispose();
			}
		}
	}          
}
