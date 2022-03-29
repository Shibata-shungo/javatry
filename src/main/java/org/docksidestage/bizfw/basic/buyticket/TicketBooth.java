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
    private int quantity = MAX_QUANTITY;
    private int twoDayQuantity = MAX_QUANTITY;
    private int fourDayQuantity = MAX_QUANTITY;
    private int nightOnlyTwoDayQuantity = MAX_QUANTITY;
    private Integer salesProceeds; // null allowed: until first purchase
    //    private Integer twoDaySalesProceeds;

    // ===================================================================================
    //                                                                         Constructor
    //                                                                         ===========
    public TicketBooth() {
        //**********コンストラクタの定義って必須？**********
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
     * @return return bought Ticket **********なぜこれが無いとエラー？**********
     * @throws TicketSoldOutException When ticket in booth is sold out.
     * @throws TicketShortMoneyException When the specified money is short for purchase.
     */
    public Ticket buyOneDayPassport(Integer handedMoney) {
        buyPassport(handedMoney, ONE_DAY_PRICE);
        quantity--;

        return new Ticket(ONE_DAY_PRICE);
    }

    public TicketBuyResult buyTwoDayPassport(int handedMoney) {
        buyPassport(handedMoney, TWO_DAY_PRICE);
        twoDayQuantity--;

        return new TicketBuyResult(handedMoney - TWO_DAY_PRICE, TWO_DAY_PRICE);
    }

    public TicketBuyResult buyFourDaypassport(int handedMoney) {
        buyPassport(handedMoney, FOUR_DAY_PRICE);
        fourDayQuantity--;

        return new TicketBuyResult(handedMoney - FOUR_DAY_PRICE, FOUR_DAY_PRICE);
    }

    public TicketBuyResult buyNightOnlyTwoDayPassport(int handedMoney) {
        buyPassport(handedMoney, NIGHT_ONLY_TWO_DAY_PRICE);
        nightOnlyTwoDayQuantity--;

        return new TicketBuyResult(handedMoney - NIGHT_ONLY_TWO_DAY_PRICE, NIGHT_ONLY_TWO_DAY_PRICE);
    }

    private void buyPassport(int handedMoney, int passportPrice) {
        if (quantity <= 0) { //チケットが余っているか
            throw new TicketSoldOutException("Sold out");
        }
        if (handedMoney < passportPrice) { //金が足りているか
            throw new TicketShortMoneyException("Short money: " + handedMoney);
        }
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
        return quantity;
    }

    public int getTwoDayQuantity() {
        return twoDayQuantity;
    }

    public int getFourDayQuantity() {
        return fourDayQuantity;
    }

    public int getNightOnlyTwoDayQuantity() {
        return nightOnlyTwoDayQuantity;
    }

    public Integer getSalesProceeds() {
        return salesProceeds;
    }
}
