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

    public TicketBuyResult(int change, int ticketPrice, int days) {
        this.change = change;
        ticket = new Ticket(ticketPrice, days);
    }

    public TicketBuyResult(int change, int ticketPrice, boolean nightOnly) {
        this.change = change;
        ticket = new Ticket(ticketPrice, nightOnly);
    }

    public TicketBuyResult(int change, int ticketPrice, int days, boolean nightOnly) {
        this.change = change;
        ticket = new Ticket(ticketPrice, days, nightOnly);
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