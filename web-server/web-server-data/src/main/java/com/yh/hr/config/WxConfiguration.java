package com.yh.hr.config;

import com.yh.hr.wechar.handler.SubscribeHandler;
import com.yh.hr.wechar.handler.UnSubscribeHandler;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;
import me.chanjar.weixin.mp.api.WxMpInRedisConfigStorage;
import me.chanjar.weixin.mp.api.WxMpMessageRouter;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Configuration
public class WxConfiguration {

    @Value("${wx.appId.ajd}")
    private String wxAppId;

    @Value("${wx.appSecret.ajd}")
    private String wxAppSecret;


    @Value("${wx.token}")
    private String wxToken;


    @Value("${spring.redis.host}")
    private String redisHost;

    @Autowired
    private UnSubscribeHandler unsubscribeHandler;
    @Autowired
    private SubscribeHandler subscribeHandler;

    @Bean
    public WxMpService wxMpService() {
        WxMpInMemoryConfigStorage config = new WxMpInRedisConfigStorage(jedisPool());
        config.setAppId(wxAppId); // 设置微信公众号的app_id
        config.setSecret(wxAppSecret); // 设置微信公众号的app corpSecret
        config.setToken(wxToken); // 设置微信公众号的token
        config.setAesKey(""); // 设置微信公众号的EncodingAESKey
        WxMpService wxService = new WxMpServiceImpl();
        wxService.setWxMpConfigStorage(config);
        return wxService;
    }

    @Bean
    public WxMpMessageRouter router(WxMpService wxMpService) {
        final WxMpMessageRouter router = new WxMpMessageRouter(wxMpService);


        // 关注事件
        router.rule().async(false).msgType(WxConsts.XmlMsgType.EVENT).event(WxConsts.EventType.SUBSCRIBE)
                .handler(subscribeHandler).end();

        // 取消关注事件
        router.rule().async(false).msgType(WxConsts.XmlMsgType.EVENT).event(WxConsts.EventType.UNSUBSCRIBE)
                .handler(unsubscribeHandler).end();

        return router;
    }


    @Bean
    public JedisPool jedisPool() {
        return new JedisPool(new JedisPoolConfig(), redisHost);
    }
}
