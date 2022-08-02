/*
 * Copyright 2019-2021 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package org.docksidestage.bizfw.basic.buyticket;

import java.time.LocalTime;

import org.docksidestage.bizfw.basic.buyticket.TicketBooth.TicketType;

/**
 * @author jflute
 * @author Shibata shungo
 */
public class Ticket {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    private final int displayPrice; // written on ticket, park guest can watch this
    private final int maxDays;
    private final TicketType ticketType;
    private final boolean nightOnly;

    private final int openTime = 8; // ========================
    private final int closeTime = 1; // Ticketクラスで持つべきではない？
    private final int nightTime = 18; // ========================

    private int enterCount = 0;

    // ===================================================================================
    //                                                                         Constructor
    //                                                                         ===========
    public Ticket(TicketType ticketType) {
        this.ticketType = ticketType;
        this.displayPrice = ticketType.getTicketPrice();
        this.maxDays = ticketType.getMaxDays();
        this.nightOnly = ticketType.isNightOnly();
    }

    //    public Ticket(TicketType ticketType, int displayPrice, int maxDays, boolean nightOnly) {
    //        this.ticketType = ticketType;
    //        this.displayPrice = displayPrice;
    //        this.maxDays = maxDays;
    //        this.nightOnly = nightOnly;
    //    }

    // ===================================================================================
    //                                                                             In Park
    //                                                                             =======
    public void doInPark() {
        if (enterCount == maxDays) {
            throw new IllegalStateException("Already in park by this ticket: displayedPrice=" + displayPrice);
        }

        //        LocalTime time = LocalTime.of(15, 36);
        LocalTime time = LocalTime.now();
        int hour = time.getHour();

        if (nightOnly) {
            if (hour >= nightTime || hour < closeTime) {
                enterCount++;
            } else {
                throw new IllegalStateException("You can use night only ticket from 18:00 to 1:00!");
            }
        } else {
            if (hour >= openTime || hour < closeTime) {
                enterCount++;
            } else {
                throw new IllegalStateException("This amusement park is open from 8:00 to 1:00!");
            }
        }

        //        if ((hour >= 8 || hour < 1) && !nightOnly) {
        //            // 通常チケットでの入場
        //            enterCount++;
        //        } else if ((hour >= 18 || hour < 1) && nightOnly) {
        //            // night onlyチケットでの入場
        //            enterCount++;
        //        } else if (hour >= 1 && hour < 8) {
        //            throw new IllegalStateException("This amusement park is open from 8:00 to 1:00!");
        //        } else {
        //            throw new IllegalStateException("You can use night only ticket from 18:00 to 1:00!");
        //        }
    }

    // ===================================================================================
    //                                                                            Accessor
    //                                                                            ========
    public int getDisplayPrice() {
        return displayPrice;
    }

    public int getEnterCount() {
        return enterCount;
    }

    public int getDays() {
        return maxDays;
    }

    public boolean isNightOnly() {
        return nightOnly;
    }

    public TicketType getTicketType() {
        return ticketType;
    }

    public boolean isPieceOfPaper() {
        return enterCount == maxDays;
    }
}
