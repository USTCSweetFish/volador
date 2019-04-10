package com.ayu.growing.entity;

import com.ayu.growing.annotation.SqlKey;
import lombok.Data;

@Data
public class PayOrderEntity {
    /**
     * 自增id
     */
    @SqlKey("id")
    private Long id;
    /**
     * 支付平台支付流水id
     */
    @SqlKey("tx_id")
    private Long txId;
    /**
     * 业务方id
     */
    @SqlKey("customer_id")
    private Integer customerId;
    /**
     * 业务方业务类型
     */
    @SqlKey("service_type")
    private Integer serviceType;
    /**
     * 业务方订单号
     */
    @SqlKey("order_id")
    private String orderId;
    /**
     * 用户uid
     */
    @SqlKey("uid")
    private Long uid;
    /**
     * 业务订单创建时间
     */
    @SqlKey("order_create_time")
    private String orderCreateTime;
    /**
     * 订单支付过期间隔
     */
    @SqlKey("order_expire")
    private Integer orderExpire;
    /**
     * 货币类型，默认CNY
     */
    @SqlKey("fee_type")
    private String feeType;
    /**
     * 订单实际需支付金额
     */
    @SqlKey("pay_amount")
    private Integer payAmount;
    /**
     * 订单原始金额，默认=payAmount
     */
    @SqlKey("original_amount")
    private Integer originalAmount;
    /**
     * 支付设备渠道类型， 1 pc 2 webapp 3 app 4 jsapi
     */
    @SqlKey("device_type")
    private Integer deviceType;
    /**
     * 支付成功异步回调地址
     */
    @SqlKey("notify_url")
    private String notifyUrl;
    /**
     * 支付成功跳转地址
     */
    @SqlKey("return_url")
    private String returnUrl;
    /**
     * 支付失败跳转地址
     */
    @SqlKey("fail_url")
    private String failUrl;
    /**
     * 商品id
     */
    @SqlKey("product_id")
    private String productId;
    /**
     * 订单展示标题
     */
    @SqlKey("show_title")
    private String showTitle;
    /**
     * 订单详细内容，默认=showTitle
     */
    @SqlKey("show_content")
    private String showContent;
    /**
     * 第三方支付渠道，1 alipay 2 wechat 3 paypal 4 google pay
     */
    @SqlKey("pay_channel")
    private Integer payChannel;
    /**
     * 支付渠道id
     */
    @SqlKey("pay_channel_id")
    private Integer payChannelId;
    /**
     * 支付渠道支付账号id
     */
    @SqlKey("pay_account_id")
    private String payAccountId;
    /**
     * 支付渠道支付账号
     */
    @SqlKey("pay_account")
    private String payAccount;
    /**
     * 支付状态，1 待支付 2 支付关闭 3 支付失败 4 支付成功 5 交易完成  6已退款
     */
    @SqlKey("pay_status")
    private Integer payStatus;
    /**
     * 支付时间
     */
    @SqlKey("pay_time")
    private String payTime;
    /**
     * 记录删除标记，1 正常 2 删除
     */
    @SqlKey("is_deleted")
    private Integer DeletedFlag;
    /**
     * 记录版本
     */
    @SqlKey("version")
    private Integer version;
    /**
     * 关闭时间
     */
    @SqlKey("close_time")
    private String closeTime;
    /**
     * 创建时间
     */
    @SqlKey("ctime")
    private String ctime;
    /**
     * 更新时间
     */
    @SqlKey("mtime")
    private String mtime;


    @SqlKey("final_pay_channel")
    private String finalPayChannel;

    @SqlKey("term")
    private Integer term;
    @SqlKey("charge_fee")
    private Integer chargeFee;

    /**
     * 通知状态
     */
    @SqlKey("notify_flag")
    private Integer notifyFlag;
    /**
     * 下次通知时间
     */
    @SqlKey("next_notify_time")
    private String nextNotifyTime;

    @SqlKey("compensate_times")
    private Integer compensateTimes;


}
