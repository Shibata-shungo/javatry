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

import java.util.EnumSet;

/**
 * 朝8時開園、夜1時閉園
 * 夕方6時からはNIGHT TIME！
 * @author jflute
 * @author Shibata shungo
 */
public class TicketBooth {

    // ===================================================================================
    //                                                                          Definition
    //                                                                          ==========
    private static final int MAX_QUANTITY = 10;

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    private TicketQuantity quantity = new TicketQuantity(MAX_QUANTITY);
    private TicketQuantity twoDayQuantity = new TicketQuantity(MAX_QUANTITY);
    private TicketQuantity fourDayQuantity = new TicketQuantity(MAX_QUANTITY);
    private TicketQuantity nightOnlyTwoDayQuantity = new TicketQuantity(MAX_QUANTITY);
    private Integer salesProceeds; // null allowed: until first purchase

    // ===================================================================================
    //                                                                         Constructor
    //                                                                         ===========
    public TicketBooth() {
        //**********コンストラクタの定義って必須？**********
        //中身がないなら無くても良い
    }

    // ===================================================================================
    //                                                                         OpeningTime
    //                                                                          ==========
    public enum OpeningTime {
        Standard(8, 1), NightOnly(18, 1);

        private int openTime;
        private int closeTime;

        private OpeningTime(int openTime, int closeTime) {
            this.openTime = openTime;
            this.closeTime = closeTime;
        }

        public int getOpenTime() {
            return openTime;
        }

        public int getCloseTime() {
            return closeTime;
        }
    }

    // ===================================================================================
    //                                                                          TicketType
    //                                                                          ==========
    public enum TicketType { //もっと活用
        OneDay(1, 7400, OpeningTime.Standard), TwoDay(2, 13200, OpeningTime.Standard), FourDay(4, 22400,
                OpeningTime.Standard), NightOnlyTwoDay(2, 7400, OpeningTime.NightOnly);

        private int maxDays;
        private int ticketPrice;
        private int openTime;
        private int closeTime;

        private TicketType(int maxDays, int ticketPrice, OpeningTime openingTime) {
            this.maxDays = maxDays;
            this.ticketPrice = ticketPrice;
            this.openTime = openingTime.getOpenTime();
            this.closeTime = openingTime.getCloseTime();
        }

        private static final EnumSet<TicketType> nightOnlyTickets = EnumSet.of(NightOnlyTwoDay);

        public int getMaxDays() {
            return maxDays;
        }

        public int getTicketPrice() {
            return ticketPrice;
        }

        public boolean isNightOnly() {
            return nightOnlyTickets.contains(this);
        }

        public int getOpenTime() {
            return openTime;
        }

        public int getCloseTime() {
            return closeTime;
        }
    }

    // ===================================================================================
    //                                                                          Buy Ticket
    //                                                                          ==========
    // you can rewrite comments for your own language by jflute
    // e.g. Japanese
    // /**
    // * 1Dayパスポートを買う、パークゲスト用のメソッド。
    // * @param handedMoney パークゲストから手渡しされたお金(金額) (NotNull, NotMinus)
    // * @throws TicketSoldOutException ブース内のチケットが売り切れだったら
    // * @throws TicketShortMoneyException 買うのに金額が足りなかったら
    // */
    /**
     * Buy one-day passport, method for park guest.
     * @param handedMoney The money (amount) handed over from park guest. (NotNull, NotMinus)
     * @return bought oneDayTicket (NotNull)
     * @throws TicketSoldOutException When ticket in booth is sold out.
     * @throws TicketShortMoneyException When the specified money is short for purchase.
     */
    public Ticket buyOneDayPassport(Integer handedMoney) {
        return doBuyPassport(handedMoney, TicketType.OneDay, quantity).getTicket();
    }

    /**
     * Buy two-day passport, method for park guest.
     * @param handedMoney The money (amount) handed over from park guest. (NotNull, NotMinus)
     * @return bought twoDayTicket (NotNull)
     * @throws TicketSoldOutException When ticket in booth is sold out.
     * @throws TicketShortMoneyException When the specified money is short for purchase.
     */
    public TicketBuyResult buyTwoDayPassport(int handedMoney) {
        return doBuyPassport(handedMoney, TicketType.TwoDay, twoDayQuantity);
    }

    /**
     * Buy four-day passport, method for park guest.
     * @param handedMoney The money (amount) handed over from park guest. (NotNull, NotMinus)
     * @return bought fourDayTicket (NotNull)
     * @throws TicketSoldOutException When ticket in booth is sold out.
     * @throws TicketShortMoneyException When the specified money is short for purchase.
     */
    public TicketBuyResult buyFourDaypassport(int handedMoney) {
        return doBuyPassport(handedMoney, TicketType.FourDay, fourDayQuantity);
    }

    /**
     * Buy night-only-two-day passport, method for park guest.
     * @param handedMoney The money (amount) handed over from park guest. (NotNull, NotMinus)
     * @return bought nightOnlyTwoDayTicket (NotNull)
     * @throws TicketSoldOutException When ticket in booth is sold out.
     * @throws TicketShortMoneyException When the specified money is short for purchase.
     */
    public TicketBuyResult buyNightOnlyTwoDayPassport(int handedMoney) {
        return doBuyPassport(handedMoney, TicketType.NightOnlyTwoDay, nightOnlyTwoDayQuantity);
    }

    private TicketBuyResult doBuyPassport(int handedMoney, TicketType ticketType, TicketQuantity quantity) {
        int ticketPrice = ticketType.getTicketPrice();

        if (quantity.getQuantity() <= 0) { //チケットが余っているか
            throw new TicketSoldOutException("Sold out");
        }
        if (handedMoney < ticketPrice) { //金が足りているか
            throw new TicketShortMoneyException("Short money: " + handedMoney);
        }

        quantity.decrementQuantity();

        if (salesProceeds != null) { // second or more purchase
            salesProceeds += ticketPrice;
        } else { // first purchase
            salesProceeds = ticketPrice;
        }

        return new TicketBuyResult(ticketType, handedMoney - ticketPrice);
    }

    public static class TicketSoldOutException extends RuntimeException {

        private static final long serialVersionUID = 1L;

        public TicketSoldOutException(String msg) {
            super(msg);
        }
    }

    public static class TicketShortMoneyException extends RuntimeException {

        private static final long serialVersionUID = 1L;

        public TicketShortMoneyException(String msg) {
            super(msg);
        }
    }

    // ===================================================================================
    //                                                                            Accessor
    //                                                                            ========
    public int getQuantity() {
        return quantity.getQuantity();
    }

    public int getTwoDayQuantity() {
        return twoDayQuantity.getQuantity();
    }

    public int getFourDayQuantity() {
        return fourDayQuantity.getQuantity();
    }

    public int getNightOnlyTwoDayQuantity() {
        return nightOnlyTwoDayQuantity.getQuantity();
    }

    public Integer getSalesProceeds() {
        return salesProceeds;
    }
}
