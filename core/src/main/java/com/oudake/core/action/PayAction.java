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
public class PayAction implements TxnAction {

    @Autowired
    private GatewayOrderService gatewayOrderService;

    @Override
    public TxnMessage execute(TxnMessage txnMessage) {
        Integer gatewayOrder = txnMessage.getTxnId();
        Integer userId = txnMessage.getUserId();
        String payPwd = txnMessage.getTxnRemark();
        ResultEntity result = gatewayOrderService.pay(gatewayOrder, userId, payPwd);
        txnMessage.setSuccess(result.isSuccess());
        txnMessage.setMsg(result.getMsg());
        return txnMessage;
    }
}
