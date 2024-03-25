package com.cc.liteflow.excutor;

import com.yomahub.liteflow.core.FlowExecutor;
import com.yomahub.liteflow.flow.LiteflowResponse;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @program: elevencc-labs
 * @description:
 * @author: yic
 * @create: 2024-03-25 09:47
 */
@Component
public class FlowTest {
    @Resource
    private FlowExecutor flowExecutor;

    public void testConfig() {
        LiteflowResponse response = flowExecutor.execute2Resp("chain1", "arg");
    }
}
