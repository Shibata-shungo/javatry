package org.docksidestage.bizfw.basic.buyticket;

/**
 * @author Shibata shungo 
 */
public class TicketQuantity {
    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    private int quantity;

    // ===================================================================================
    //                                                                         Constructor
    //                                                                         ===========
    public TicketQuantity(int ticketQuantity) {
        this.quantity = ticketQuantity;
    }

    // ===================================================================================
    //                                                                            Accessor
    //                                                                            ========
    public int getQuantity() {
        return quantity;
    }

    public void decrementQuantity() {
        quantity--;
    }
}
