package com.oudake.web.mq;

import com.oudake.common.msg.MailMsg;
import com.oudake.common.msg.TxnMessage;
import com.oudake.common.utils.ConvertUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;

/**
 * @author wangyi
 */
@Component
public class CoreSender {

    private final static Logger LOGGER = LoggerFactory.getLogger(CoreSender.class);

    @Value("${core-queue-name}")
    private String coreQueueName;
    @Value("${mail-queue-name}")
    private String mailQueueName;
    @Value("${exchange-name}")
    private String exchangeName;
    @Value("${saltKey}")
    private String saltKey;

    @Autowired
    private AmqpTemplate amqpTemplate;

    /**
     * @Author wangyi
     * @Description // 发送消息TxnMessage到Core，不返回应答
     * @Date 2019/1/3
     * @Param [mqMsg]
     * @return void
    **/
    public void send(TxnMessage txnMessage) {
        LOGGER.info(">>组装消息: 交易类型: " + txnMessage.getTxnType());
        try {
            this.amqpTemplate.convertAndSend(exchangeName, coreQueueName, ConvertUtil.objectToMap(txnMessage));
        } catch (Exception e) {
            LOGGER.error(">>消息发送失败, 对象转换异常", e);
        }
    }

    /**
     * @Author wangyi
     * @Description // 发送消息TxnMessage到Core,返回应答
     * @Date 2019/1/3
     * @Param [mqMsg]
     * @return com.oudake.web.model.common.MqMsg
    **/
    public TxnMessage sendAndReceive(TxnMessage txnMessage) {
        HashMap map = null;
        try {
            map = (HashMap) this.amqpTemplate.convertSendAndReceive(exchangeName, coreQueueName, ConvertUtil.objectToMap(txnMessage));
        } catch (Exception e) {
            LOGGER.error(">>消息发送失败, 对象转换异常", e);
        }
        LOGGER.info(">>组装消息: 交易类型: " + txnMessage.getTxnType());
        TxnMessage response = new TxnMessage();
        try {
            response.setSuccess((boolean) map.get("success"));
            response.setTxnId((Integer) map.get("txnId"));
            response.setUserId((Integer) map.get("userId"));
            response.setTxnType((String) map.get("txnType"));
            response.setAmount((Integer) map.get("amount"));
            response.setTxnAmt((Long) map.get("txnAmt"));
            response.setTxnRemark((String) map.get("txnRemark"));
            response.setKey((String) map.get("key"));
            response.setMsg((String) map.get("msg"));
        } catch (Exception e) {
            LOGGER.error(">>core无应答", e);
            response.setSuccess(false);
            response.setMsg("系统无应答");
        }
        return response;
    }

    /**
     * @Author wangyi
     * @Description // 发送邮件到Core,不返回应答
     * @Date 2019/1/3
     * @Param [mailMsg]
     * @return void
    **/
    public void sendMail(MailMsg mailMsg) {
        try {
            this.amqpTemplate.convertAndSend(exchangeName, mailQueueName, mailMsg);
        } catch (Exception e) {
            LOGGER.error(">>邮件发送失败", e);
        }
        LOGGER.info(">>Mail: " + mailMsg.getTitle() + " 已发送");
    }
}
