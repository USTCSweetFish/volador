package com.ayu.growing.web;

import com.ayu.growing.annotation.RequestLimit;
import com.ayu.growing.entity.JdEntity;
import com.ayu.growing.rss.HelloService;
import com.ayu.growing.utils.HttpUtils;
import com.ayu.growing.vo.req.HelloReqVO;
import io.swagger.annotations.ApiOperation;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.stream.Collectors;


@RestController
@RequestMapping(value = "growing")
public class HelloController {
    @Autowired
    private HelloService helloService;

    private static final Logger logger = LoggerFactory.getLogger(HelloController.class);


    @RequestLimit(count = 2, time = 2)
    @ApiOperation(value = "请求XXX", notes = "请求XXX", produces = "application/json")
    @RequestMapping(value = "/hello", method = {RequestMethod.POST})
    public String hello(@RequestBody @Validated HelloReqVO req, BindingResult result) {
        helloService.query(req.getName());
        return "SUCCESS";
    }


    public static void main(String[] args) {
        //初始化一个httpclient
        HttpClient client = new DefaultHttpClient();
        //我们要爬取的一个地址，这里可以从数据库中抽取数据，然后利用循环，可以爬取一个URL队列
        String url = "https://s.taobao.com/search?q=python&imgfile=&js=1&stats_click=search_radio_all%3A1&initiative_id=staobaoz_20180430&ie=utf8";
        //抓取的数据
        List<JdEntity> bookdatas = URLParser(client, url);
        //循环输出抓取的数据
        for (JdEntity jd : bookdatas) {
            logger.info("bookID:" + jd.getBookID() + "\t" + "bookPrice:" + jd.getBookPrice() + "\t" + "bookName:" + jd.getBookName());
        }
        //将抓取的数据插入数据库
//            parseService.insertBatch(bookdatas);
    }


    public static List<JdEntity> getData(String html) {
        //获取的数据，存放在集合中
        List<JdEntity> data = new ArrayList<JdEntity>();
        //采用Jsoup解析
        Document doc = Jsoup.parse(html);
        //获取html标签中的内容
        Elements elements = doc.select("ul[class=gl-warp clearfix]").select("li[class=gl-item]");
        for (Element ele : elements) {
            String bookID = ele.attr("data-sku");
            String bookPrice = ele.select("div[class=p-price]").select("strong").select("i").text();
            String bookName = ele.select("div[class=p-name]").select("em").text();
            //创建一个对象，这里可以看出，使用Model的优势，直接进行封装
            JdEntity jdModel = new JdEntity();
            //对象的值

            jdModel.setBookID(bookID);
            jdModel.setBookName(bookName);
            jdModel.setBookPrice(bookPrice);
            //将每一个对象的值，保存到List集合中
            data.add(jdModel);
        }
        //返回数据
        return data;
    }

    public static List<JdEntity> URLParser(HttpClient client, String url) {
        List<JdEntity> JingdongData = new ArrayList<JdEntity>();
        try {
            //用来接收解析的数据

            //获取网站响应的html，这里调用了HTTPUtils类
            HttpResponse response = HttpUtils.getRawHtml(client, url);
            //获取响应状态码
            int StatusCode = response.getStatusLine().getStatusCode();
            //如果状态响应码为200，则获取html实体内容或者json文件
            if (StatusCode == 200) {
                String entity = EntityUtils.toString(response.getEntity(), "utf-8");
                JingdongData = getData(entity);
                EntityUtils.consume(response.getEntity());
            } else {
                //否则，消耗掉实体
                EntityUtils.consume(response.getEntity());
            }
        } catch (Exception e) {

        }
        return JingdongData;
    }


}
