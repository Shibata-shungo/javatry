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

/**
 * @author jflute
 * @author Shibata shungo
 */
public class Ticket {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    private final int displayPrice; // written on ticket, park guest can watch this
    private int enterCount = 0; // true means this ticket is unavailable
    private int days = 1;
    private boolean nightOnly;

    // ===================================================================================
    //                                                                         Constructor
    //                                                                         ===========
    public Ticket(int displayPrice) {
        this.displayPrice = displayPrice;
    }

    public Ticket(int displayPrice, int days) {
        this.displayPrice = displayPrice;
        this.days = days;
    }

    public Ticket(int displayPrice, boolean nightOnly) {
        this.displayPrice = displayPrice;
        this.nightOnly = nightOnly;
    }

    public Ticket(int displayPrice, int days, boolean nightOnly) {
        this.displayPrice = displayPrice;
        this.days = days;
        this.nightOnly = nightOnly;
    }

    // ===================================================================================
    //                                                                             In Park
    //                                                                             =======
    public void doInPark() {
        if (enterCount == days) {
            throw new IllegalStateException("Already in park by this ticket: displayedPrice=" + displayPrice);
        }

        //        LocalTime time = LocalTime.of(15, 36);
        LocalTime time = LocalTime.now();
        int hour = time.getHour();

        if ((hour >= 8 || hour < 1) && !nightOnly) {
            enterCount++;
        } else if ((hour >= 18 || hour < 1) && nightOnly) {
            enterCount++;
        } else if (hour >= 1 && hour < 8) {
            throw new IllegalStateException("This amusement park is open from 8:00 to 1:00!");
        } else {
            throw new IllegalStateException("You can use this ticket from 18:00 to 1:00!");
        }
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

    public boolean isPieceOfPaper() {
        return enterCount == days;
    }
}
