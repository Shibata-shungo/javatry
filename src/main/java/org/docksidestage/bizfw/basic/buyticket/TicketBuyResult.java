package org.docksidestage.bizfw.basic.buyticket;

/**
 * @author shibata shungo
 */
public class TicketBuyResult {
    // Ticket change
    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    private final int change;
    private final Ticket ticket;

    // ===================================================================================
    //                                                                         Constructor
    //                                                                         ===========
    public TicketBuyResult(int change, int ticketPrice) {
        this.change = change;
        ticket = new Ticket(ticketPrice);
    }

    // ===================================================================================
    //                                                                            Accessor
    //                                                                            ========
    public int getChange() {
        return change;
    }

    public Ticket getTicket() {
        return ticket;
    }
}