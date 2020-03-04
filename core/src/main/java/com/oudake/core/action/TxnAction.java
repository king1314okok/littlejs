package com.oudake.core.action;

import com.oudake.common.msg.TxnMessage;

/**
 * @author wangyi
 */
public interface TxnAction {

    TxnMessage execute(TxnMessage txnMessage);
}
