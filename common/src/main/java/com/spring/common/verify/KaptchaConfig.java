package com.spring.common.verify;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

/**
* @author 作者：赵进华 
* @version 创建时间：2019年4月24日 上午11:06:05
* @Desc类说明:Kaptcha配置类
*/

@Configuration
public class KaptchaConfig {
	@Bean
    public DefaultKaptcha producer() {
        Properties properties = new Properties();
        properties.put("kaptcha.border", "no");
        properties.put("kaptcha.textproducer.font.color", "black");
        properties.put("kaptcha.textproducer.char.space", "9");
        properties.put("kaptcha.textproducer.char.length","4");
        properties.put("kaptcha.image.width","130");
        properties.put("kaptcha.image.height","50");
        properties.put("kaptcha.textproducer.font.size","30");
 
        properties.put("kaptcha.noise.impl","com.google.code.kaptcha.impl.NoNoise");
        Config config = new Config(properties);
        DefaultKaptcha defaultKaptcha = new DefaultKaptcha();
        defaultKaptcha.setConfig(config);
        return defaultKaptcha;
    }

}
