package com.ayu.growing.job;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHander;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@JobHander(value = "xxljobhandler")
@Component
public class JobHandler extends IJobHandler {
   @Autowired
   private RestTemplate restTemplate;

   private static final String JOBURL="";

    @Override
    public ReturnT<String> execute(String... strings) throws Exception {
        try {
            String result = restTemplate.getForObject(JOBURL, String.class);
//            BiliLog.info("autopsRevokeJobHandler.res:{}", result);
            JSONObject json = JSON.parseObject(result);
            if (json.getLong("errno") == 0) {
                return ReturnT.SUCCESS;
            } else {
                return ReturnT.FAIL;
            }
        } catch (RestClientException e) {
//            BiliLog.info(e.getMessage());
            return ReturnT.FAIL;
        } finally {
//            BiliLog.info("end autopsRevokeJobHandler");
        }
    }
}

