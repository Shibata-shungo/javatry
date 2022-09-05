package org.docksidestage.bizfw.basic.buyticket;

import org.docksidestage.bizfw.basic.buyticket.TicketBooth.TicketType;

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
    public TicketBuyResult(TicketType ticketType, int change) {
        this.ticket = new Ticket(ticketType);
        this.change = change;
    }

    //    public TicketBuyResult(TicketType ticketType, int change, int ticketPrice, int maxDays, boolean nightOnly) {
    //        this.change = change;
    //        ticket = new Ticket(ticketType, ticketPrice, maxDays, nightOnly);
    //    }

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