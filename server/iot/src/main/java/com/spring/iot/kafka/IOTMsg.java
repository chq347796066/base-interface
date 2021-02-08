package com.spring.iot.kafka;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * @description:
 * @author: 赵进华
 * @time: 2020/7/22 18:18
 */
@Data
@ToString(callSuper = true)
public class IOTMsg implements Serializable {
    private static final long serialVersionUID = 1L;

    private String msg;

    private Date createDate;
}
