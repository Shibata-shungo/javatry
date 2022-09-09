package org.docksidestage.bizfw.basic.objanimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Human extends Animal {
    // ===================================================================================
    //                                                                          Definition
    //                                                                          ==========
    private static final Logger logger = LoggerFactory.getLogger(Cat.class);

    // ===================================================================================
    //                                                                         Constructor
    //                                                                         ===========
    public Human() {
    }

    public void repairHitPoint() {
        logger.debug("Sleeping...");
        hitPoint += 3;
        logger.debug("Wake up!");
    }

    @Override
    protected String getBarkWord() {
        // TODO Auto-generated method stub
        return "Hello World!";
    }

}
