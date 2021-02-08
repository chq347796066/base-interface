package com.spring.baseinfo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.spring.base.entity.baseinfo.UserEntity;
import com.spring.base.entity.baseinfo.UserRoleEntity;
import com.spring.base.vo.saas.BuyConfirmVo;
import com.spring.base.vo.saas.UpgradeVo;
import com.spring.baseinfo.dao.ICompanyDao;
import com.spring.baseinfo.dao.IUserDao;
import com.spring.baseinfo.dao.IUserRoleDao;
import com.spring.baseinfo.service.ISaasService;
import com.spring.common.constants.MessageCode;
import com.spring.common.request.RequestUtils;
import com.spring.common.response.ApiResponseResult;
import com.spring.common.util.id.UUIDFactory;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author 作者：ZhaoJinHua
 * @date : 创建时间：2020-03-31 19:02:26
 * @Desc类说明: saas业务接口实现类
 */
@Slf4j
@Service("saasService")
public class SaasServiceImpl implements ISaasService {
    @Autowired
    private ICompanyDao companyDao;

    @Autowired
    private IUserRoleDao userRoleDao;

    @Autowired
    private IUserDao userDao;

    /**
     * @description:购买正式应用
     * @return:
     * @author: 赵进华
     * @time: 2020/7/15 9:52
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ApiResponseResult buyApp(BuyConfirmVo vo) throws Exception {
        ApiResponseResult result = new ApiResponseResult();
        //saas购买正式应用更新租户小区数和购买时间
        companyDao.saasBuyApp(vo);
        //删除租户下用户角色列表，所有用户更新为新的应用，再批量插入
        List<UserRoleEntity> addUserRoleList = new ArrayList<>();
        QueryWrapper<UserRoleEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("tenant_id", vo.getTenantId());
        List<UserRoleEntity> userRoleList = userRoleDao.selectList(queryWrapper);
        if (CollectionUtils.isNotEmpty(userRoleList)) {
            userRoleList.forEach(item -> {
                UserRoleEntity addEntity = new UserRoleEntity();
                addEntity.setId(UUIDFactory.createId());
                //saas正式版角色id
                addEntity.setRoleId(vo.getRoleId());
                //设置租户id
                addEntity.setTenantId(vo.getTenantId());
                addEntity.setUserId(item.getUserId());
                addEntity.setStatus(1);
                addEntity.setDelFlag(0);
                addEntity.setCreateUser(RequestUtils.getUserId());
                addEntity.setCreateDate(new Date());
                addUserRoleList.add(addEntity);
            });
        }
        //根据租户删除用户角色列表
        userRoleDao.deleteTenantUser(vo.getTenantId());
        //批量保存用户角色列表
        if (CollectionUtils.isNotEmpty(addUserRoleList)) {
            userDao.addListUserRole(addUserRoleList);
        }
        //更新租户下用户角色id为新购买的应用角色id
        userDao.updateTenantRole(vo.getTenantId(), vo.getRoleId());
        result.setCode(MessageCode.SUCCESS);
        result.setMsg(MessageCode.SUCCESS_TEXT);
        return result;
    }

    /**
     * @description:应用时间续费或者扩增小区数
     * @return:
     * @author: 赵进华
     * @time: 2020/7/15 11:23
     */
    @Override
    public ApiResponseResult extendApp(BuyConfirmVo vo) throws Exception {
        ApiResponseResult result = new ApiResponseResult();
        //更新租户小区数和购买时间
        int no = companyDao.saasBuyApp(vo);
        if (no > 0) {
            result.setCode(MessageCode.SUCCESS);
            result.setMsg("成功");
        } else {
            result.setCode(MessageCode.FAIL);
            result.setMsg("更新失败");
        }
        return result;
    }

    /**
     * @description:升级新应用
     * @return:
     * @author: 赵进华
     * @time: 2020/7/15 11:34
     */
    @Override
    public ApiResponseResult upgradeApp(UpgradeVo vo) throws Exception {
        ApiResponseResult result = new ApiResponseResult();
        //租户的管理员新绑定升级新应用的角色
        userDao.upgradeUserRole(vo.getTenantId(),vo.getRoleId());
        //查询租户管理员id
        String tenantAdminId="";
        QueryWrapper<UserEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("tenant_id", vo.getTenantId());
        queryWrapper.eq("tenant_admin", 1);
        List<UserEntity> userList = userDao.selectList(queryWrapper);
        if (CollectionUtils.isNotEmpty(userList)) {
            UserEntity userEntity=userList.get(0);
            if(userEntity!=null)
            {
                tenantAdminId=userEntity.getId();
            }
        }

        //用户角色表新增租户管理员升级新应用的角色
        List<UserRoleEntity> addUserRoleList = new ArrayList<>();
        UserRoleEntity addEntity = new UserRoleEntity();
        addEntity.setId(UUIDFactory.createId());
        //saas正式版角色id
        addEntity.setRoleId(vo.getRoleId());
        //设置租户id
        addEntity.setTenantId(vo.getTenantId());
        addEntity.setUserId(tenantAdminId);
        addEntity.setStatus(1);
        addEntity.setDelFlag(0);
        addEntity.setCreateUser(RequestUtils.getUserId());
        addEntity.setCreateDate(new Date());
        addUserRoleList.add(addEntity);
        //批量保存用户角色列表
        if (CollectionUtils.isNotEmpty(addUserRoleList)) {
            userDao.addListUserRole(addUserRoleList);
        }
        result.setCode(MessageCode.SUCCESS);
        result.setMsg("成功");
        return result;
    }
}
