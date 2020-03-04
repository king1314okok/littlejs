package com.oudake.core.init;

import com.oudake.common.constants.Constants;
import com.oudake.core.action.PayAction;
import com.oudake.core.action.PlaceOneOrderAction;
import com.oudake.core.action.PlaceOrdersAction;
import com.oudake.core.action.TxnAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.HashMap;

/**
 * @author wangyi
 */
@Component
public class TxnType extends HashMap<String, TxnAction> {

    @Autowired
    private PlaceOneOrderAction placeOneOrderAction;
    @Autowired
    private PlaceOrdersAction placeOrdersAction;
    @Autowired
    private PayAction payAction;

    @PostConstruct
    public void init() {
        this.put(Constants.TxnMessage.PLACE_ONE_ORDER, placeOneOrderAction);
        this.put(Constants.TxnMessage.PLACE_ORDERS, placeOrdersAction);
        this.put(Constants.TxnMessage.PAY, payAction);
    }
}
