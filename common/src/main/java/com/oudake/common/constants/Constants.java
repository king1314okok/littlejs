package com.oudake.common.constants;

/**
 * @Author wangyi
 * @Description 常量
 * @Date 2019/1/4 18:04
 * @Version 1.0
 */
public interface Constants {

    interface UserStatus {
        /**
         * name
          */
        String NAME = "USER_STATUS";
        /**
         * 正常
         */
        String NORMAL = "NORMAL";
        /**
         * 停用
         */
        String STOP = "STOP";
    }

    interface UserType {
        /**
         * name
         */
        String NAME = "USER_TYPE";
        /**
         * 普通用户
         */
        String NORMAL = "NORMAL";
        /**
         * VIP用户
         */
        String VIP = "VIP";
    }

    interface FormType {
        /**
         * 添加操作
         */
        String ADD = "add";
        /**
         * 编辑操作
         */
        String EDIT = "edit";
    }

    interface Currency {
        /**
         * name
         */
        String NAME = "CURRENCY";
        /**
         * 人民币
         */
        String CNY = "CNY";
    }

    interface TxnType {
        /**
         * name
         */
        String NAME = "TXN_TYPE";
        /**
         * 待付款
         */
        String WAIT_PAY = "WAIT_PAY";
        /**
         * 已付款
         */
        String PAYED = "PAYED";
        /**
         * 取消订单(退款)
         */
        String CANCEL = "CANCEL";
    }

    interface TxnStatus {
        /**
         * name
         */
        String NAME = "TXN_STATUS";
        /**
         * 已接单
         */
        String ACCEPT = "ACCEPT";
        /**
         * 已发货
         */
        String SEND = "SEND";
        /**
         * 拒绝发货
         */
        String REFUSE = "REFUSE";
        /**
         * 待处理
         */
        String WAITING = "WAITING";
    }

    interface TxnMessage {
        /**
         * name
         */
        String NAME = "TXN_MESSAGE";
        /**
         * 单独下一单
         */
        String PLACE_ONE_ORDER = "P001";
        /**
         * 一次下多单
         */
        String PLACE_ORDERS = "P002";
        /**
         * 支付
         */
        String PAY = "P003";
    }

    interface FlowerStatus {
        /**
         * name
         */
        String NAME = "FLOWER_STATUS";
        /**
         * 浏览次数
         */
        String VIEW_COUNT= "VIEW_COUNT";
        /**
         * 购买次数(销量)
         */
        String SALE_COUNT = "SALE_COUNT";

        /**
         * 启用
         */
        String NORMAL = "normal";

        /**
         * 停用
         */
        String STOP = "stop";
    }
}
