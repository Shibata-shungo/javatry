package org.docksidestage.bizfw.basic.buyticket;

/**
 * @author Shibata shungo 
 */
public class TicketQuantity {
    private int quantity;

    public TicketQuantity(int ticketQuantity) {
        this.quantity = ticketQuantity;
    }

    public int getQuantity() {
        return quantity;
    }

    public void decrementQuantity() {
        quantity--;
    }
}
