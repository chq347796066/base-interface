package com.spring.workflow.activiti.controller;


import com.spring.workflow.vo.DictionaryDataVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 作者：赵进华
 * @version 创建时间：2019年5月31日 下午3:23:03
 * @Desc类说明:工作流控制器
 */
@RestController
@Api(value = "", tags = "工作流控制器")
@RequestMapping("workflow")
public class WorkflowController {


    /**
     * 查询工作流类型
     *
     * @throws Exception
     * @author 作者：赵进华
     * @version 创建时间：2019年6月12日 下午6:23:33
     */
    @ApiOperation(value = "查询工作流类型", httpMethod = "GET", notes = "查询工作流类型")
    @RequestMapping(value = "/getWorkTypeList", method = RequestMethod.GET)
    public List<DictionaryDataVo> getWorkTypeList() throws Exception {
        List<DictionaryDataVo> list=new ArrayList<DictionaryDataVo>();
        DictionaryDataVo vo1=new DictionaryDataVo();
        vo1.setValue("project");
        vo1.setLabel("项目工作流");
        list.add(vo1);
        DictionaryDataVo vo2=new DictionaryDataVo();
        vo2.setValue("leave");
        vo2.setLabel("请假工作流");
        list.add(vo2);
        return list;
    }


}
