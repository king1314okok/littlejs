package com.oudake.core.mq;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oudake.common.msg.MailMsg;
import com.oudake.common.msg.TxnMessage;
import com.oudake.core.action.TxnAction;
import com.oudake.core.init.TxnType;
import com.oudake.core.service.MailService;
import com.oudake.core.util.ConvertUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author wangyi
 */
@Component
public class MessageProcess {

    private final static Logger LOGGER = LoggerFactory.getLogger(MessageProcess.class);

    private ObjectMapper objectMapper = new ObjectMapper();

    @Value("${saltKey}")
    private String saltKey;

    @Autowired
    private TxnType txnType;

    @Autowired
    private MailService mailService;

    /**
     * 处理并返回应答
     * @param message message
     * @return mqMsg
     */
    @RabbitListener(bindings ={@QueueBinding(value = @Queue(value = "core-queue",durable = "true"),
            exchange =@Exchange(value = "message-exchange"),key = "core-queue")})
    @RabbitHandler
    public Map process(Message message) {
        String body = new String(message.getBody());
        LOGGER.info(">>core已处理消息: " + body);
        TxnMessage txnMessage = null;
        TxnMessage response = new TxnMessage();
        response.setKey(saltKey);
        try {
            txnMessage = objectMapper.readValue(body, TxnMessage.class);

            TxnAction action = txnType.get(txnMessage.getTxnType());
            if (action == null) {
                response.setSuccess(false);
                response.setMsg("交易失败: 交易类型为空");
                return ConvertUtil.objectToMap(response);
            }
            txnMessage = action.execute(txnMessage);

            LOGGER.info(">>core组装应答: 交易类型: " + txnMessage.getTxnType());
        } catch (Exception e) {
            LOGGER.error(">>messageProcess, 核心处理异常", e);
            response.setSuccess(false);
            response.setMsg("交易失败: 核心处理异常");
            return ConvertUtil.objectToMap(response);
        }
        return ConvertUtil.objectToMap(txnMessage);
    }

    @RabbitListener(bindings ={@QueueBinding(value = @Queue(value = "mail-queue",durable = "true"),
            exchange =@Exchange(value = "message-exchange"),key = "mail-queue")})
    @RabbitHandler
    public void process(MailMsg mailMsg) {
        mailService.sendMail(mailMsg.getToAddr(), mailMsg.getTitle(), mailMsg.getContext(), mailMsg.isHtml());
        LOGGER.info(">>Mail: " + mailMsg.getTitle() + "  已发送");
    }
}
