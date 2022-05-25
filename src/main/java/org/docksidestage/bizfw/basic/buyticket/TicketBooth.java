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
    private static final int ONE_DAY_PRICE = 7400; // when 2019/06/15
    private static final int TWO_DAY_PRICE = 13200;
    private static final int FOUR_DAY_PRICE = 22400;
    private static final int NIGHT_ONLY_TWO_DAY_PRICE = 7400;

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
        int ticketPrice = ONE_DAY_PRICE;

        doBuyPassport(handedMoney, ticketPrice, quantity);

        return new Ticket(ticketPrice);
    }

    /**
     * Buy two-day passport, method for park guest.
     * @param handedMoney The money (amount) handed over from park guest. (NotNull, NotMinus)
     * @return bought twoDayTicket (NotNull)
     * @throws TicketSoldOutException When ticket in booth is sold out.
     * @throws TicketShortMoneyException When the specified money is short for purchase.
     */
    public TicketBuyResult buyTwoDayPassport(int handedMoney) {
        int ticketPrice = TWO_DAY_PRICE;

        doBuyPassport(handedMoney, ticketPrice, twoDayQuantity);

        return new TicketBuyResult(handedMoney - ticketPrice, ticketPrice, 2);
    }

    /**
     * Buy four-day passport, method for park guest.
     * @param handedMoney The money (amount) handed over from park guest. (NotNull, NotMinus)
     * @return bought fourDayTicket (NotNull)
     * @throws TicketSoldOutException When ticket in booth is sold out.
     * @throws TicketShortMoneyException When the specified money is short for purchase.
     */
    public TicketBuyResult buyFourDaypassport(int handedMoney) {
        int ticketPrice = FOUR_DAY_PRICE;

        doBuyPassport(handedMoney, ticketPrice, fourDayQuantity);

        return new TicketBuyResult(handedMoney - ticketPrice, ticketPrice, 4);
    }

    /**
     * Buy night-only-two-day passport, method for park guest.
     * @param handedMoney The money (amount) handed over from park guest. (NotNull, NotMinus)
     * @return bought nightOnlyTwoDayTicket (NotNull)
     * @throws TicketSoldOutException When ticket in booth is sold out.
     * @throws TicketShortMoneyException When the specified money is short for purchase.
     */
    public TicketBuyResult buyNightOnlyTwoDayPassport(int handedMoney) {
        int ticketPrice = NIGHT_ONLY_TWO_DAY_PRICE;

        doBuyPassport(handedMoney, ticketPrice, nightOnlyTwoDayQuantity);

        return new TicketBuyResult(handedMoney - ticketPrice, ticketPrice, 2, true);
    }

    private void doBuyPassport(int handedMoney, int passportPrice, TicketQuantity quantity) {
        if (quantity.getQuantity() <= 0) { //チケットが余っているか
            throw new TicketSoldOutException("Sold out");
        }
        if (handedMoney < passportPrice) { //金が足りているか
            throw new TicketShortMoneyException("Short money: " + handedMoney);
        }

        quantity.decrementQuantity();

        if (salesProceeds != null) { // second or more purchase
            salesProceeds += passportPrice;
        } else { // first purchase
            salesProceeds = passportPrice;
        }
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
