package com.oudake.core.action;

import com.oudake.common.ResultEntity;
import com.oudake.common.msg.TxnMessage;
import com.oudake.core.service.GatewayOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author wangyi
 */
@Component
public class PlaceOneOrderAction implements TxnAction {

    @Autowired
    private GatewayOrderService gatewayOrderService;

    @Override
    public TxnMessage execute(TxnMessage txnMessage) {
        Integer flowerId = txnMessage.getTxnId();
        Integer amount = txnMessage.getAmount();
        Integer userId = txnMessage.getUserId();
        ResultEntity result = gatewayOrderService.addOrder(flowerId, amount, userId);
        txnMessage.setSuccess(result.isSuccess());
        txnMessage.setMsg(result.getMsg());
        return txnMessage;
    }
}
