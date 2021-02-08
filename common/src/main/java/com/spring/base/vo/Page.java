package com.spring.base.vo;

import java.util.List;

/**
* @author 作者：赵进华 
* @version 创建时间：2019年4月19日 下午3:39:59
* @Desc类说明:分页实体类
*/
public class Page<T> {

    //总记录数
    private Long total;

    //结果集
    private List<T> list;

	public Long getTotal() {
		return total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}

	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}

	@Override
	public String toString() {
		return "Page [total=" + total + ", list=" + list + "]";
	}

	
    
    
}
