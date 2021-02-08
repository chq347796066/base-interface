package com.spring.base.vo;

import java.io.Serializable;


/**
* @author 作者：赵进华 
* @version 创建时间：2019年3月21日 上午9:36:54
* @Desc类说明:vo基础父类
*/
public interface Vo<PK extends Serializable> extends Serializable{

	PK getId();

	void setId(PK id);
}