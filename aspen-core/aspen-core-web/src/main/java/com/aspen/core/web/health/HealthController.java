package com.aspen.core.web.health;

import com.aspen.core.foundation.utils.AspenResponse;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 健康检测接口
 * @author jingchuan
 */
@RestController
@RequestMapping("/health")
public class HealthController {

    @RequestMapping("")
    public AspenResponse<String> health(){
        return AspenResponse.success("success");
    }

}