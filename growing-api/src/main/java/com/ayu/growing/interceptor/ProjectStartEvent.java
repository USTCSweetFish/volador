package com.bilibili.growing.interceptor;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
@Component
public class ProjectStartEvent implements ApplicationListener<ContextRefreshedEvent> {
    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        if (contextRefreshedEvent.getApplicationContext().getParent().getParent() == null) {//判断容器加载完成后
            /*
            一个最简单的方式就是，让我们的bean实现ApplicationListener接口，这样当发布事件时，
            spring的ioc容器就会以容器的实例对象作为事件源类，并从中找到事件的监听者，
            此时ApplicationListener接口实例中的onApplicationEvent(E event)方法就会被调用，
            我们的逻辑代码就会写在此处。这样我们的目的就达到了。但这也带来一个思考，
            有人可能会想，这样的代码我们也可以通过实现spring的InitializingBean接口来实现啊，
            也会被spring容器去自动调用，但是大家应该想到，如果我们现在想做的事，
            是必须要等到所有的bean都被处理完成之后再进行，
            此时InitializingBean接口的实现就不合适了，
            所以需要深刻理解事件机制的应用场合。
             */
            //执行springboot启动后就执行的任务和事件
        }
    }
}
