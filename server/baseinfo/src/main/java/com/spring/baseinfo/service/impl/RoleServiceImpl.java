package com.spring.baseinfo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.spring.base.dao.BaseDao;
import com.spring.base.entity.baseinfo.*;
import com.spring.base.service.impl.BaseServiceImpl;
import com.spring.base.vo.baseinfo.dictionary.DictionaryDataVo;
import com.spring.base.vo.baseinfo.role.*;
import com.spring.base.vo.baseinfo.user.UserEntityResponseVO;
import com.spring.base.vo.baseinfo.user.UserUpdateLogo;
import com.spring.baseinfo.dao.*;
import com.spring.baseinfo.service.IRoleService;
import com.spring.common.constants.Constants;
import com.spring.common.constants.MessageCode;
import com.spring.common.page.RequestPageVO;
import com.spring.common.request.RequestUtils;
import com.spring.common.response.ApiResponseResult;
import com.spring.common.util.id.UUIDFactory;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author 作者：ZhaoJinHua
 * @date : 创建时间：2020-03-18 15:37:54
 * @Desc类说明: 角色业务接口实现类
 */
@Slf4j
@Service("roleService")
public class RoleServiceImpl extends BaseServiceImpl<RoleEntity, String> implements IRoleService {

    @Autowired
    private IRoleDao roleDao;

    @Autowired
    private IPicDao picDao;

    @Autowired
    private IRoleMenuDao roleMenuDao;

    @Autowired
    private IMenuDao menuDao;

    @Autowired
    private IUserDao userDao;

    @Autowired
    private ICompanyDao companyDao;

    @Override
    public BaseDao getBaseMapper() {
        return roleDao;
    }
    /**
     * 新增角色
     *
     * @param vo
     * @return
     * @throws Exception
     * @author 作者：ZhaoJinHua
     * @version 创建时间：2020-03-18 15:37:54
     */
    @Override
    public ApiResponseResult addRole(RoleAddVo vo) throws Exception {
        // 返回的对象
        ApiResponseResult result = new ApiResponseResult();
        RoleEntity entity = new RoleEntity();
        BeanUtils.copyProperties(vo, entity);
        entity.setId(UUIDFactory.createId());
        entity.setCreateUser(RequestUtils.getUserName());
        entity.setTenantId(RequestUtils.getTenantId());
        entity.setCreateDate(new Date());
        // 新增
        int no = roleDao.insert(entity);
        if (no > 0) {
            result.setCode(MessageCode.SUCCESS);
            result.setMsg("成功");
        } else {
            result.setCode(MessageCode.FAIL);
            result.setMsg("新增失败");
        }
        return result;
    }

    /**
     * 更新角色
     *
     * @param vo
     * @return
     * @throws Exception
     * @author 作者：ZhaoJinHua
     * @version 创建时间：2020-03-18 15:37:54
     */
    @Override
    public ApiResponseResult updateRole(RoleUpdateVo vo) throws Exception {
        RoleEntity entity = new RoleEntity();
        BeanUtils.copyProperties(vo, entity);
        entity.setModifyUser(RequestUtils.getUserName());
        entity.setModifyDate(new Date());
        // 更新
        int no = roleDao.updateById(entity);
        if (no > 0) {
            return createSuccessResult(null);
        }
        return createFailResult();
    }

    /**
     * @description:获取角色列表
     * @return:
     * @author: 赵进华
     * @time: 2020/3/19 11:10
     */
    @Override
    public ApiResponseResult getRoleList() throws Exception {
        List<DictionaryDataVo> list=new ArrayList<>();
        //saas用户试用阶段只能看到试用版本的角色，购买服务后只能看到购买的服务角色
        if(RequestUtils.getIsSaas())
        {
            //获取租户信息，得到租户是试用版，还是正式购买服务
            CompanyEntity companyEntity=companyDao.selectById(RequestUtils.getTenantId());
            if(companyEntity!=null)
            {
                if(companyEntity.getSaasStatus()==0)
                {
                    //试用版
                    list=roleDao.getTryRoleList();
                }else if(companyEntity.getSaasStatus()==1)
                {
                    //正式版
                    list=roleDao.getSaasRoleList(RequestUtils.getTenantId());
                }
            }
        }else
        {
            list=roleDao.getRoleList();
        }
        return createSuccessResult(list);
    }

    /**
     * @description:根据角色ID获取已授权的菜单
     * @return:
     * @author: 赵进华
     * @time: 2020/3/30 16:59
     */
    @Override
    public ApiResponseResult getMenuByRoleId(String roleId,String systemCode) throws Exception {
        List<String> result = new ArrayList<>();
        //系统code为空，查询全部
        if(StringUtils.isEmpty(systemCode))
        {
            QueryWrapper<RoleMenuEntity> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("role_id", roleId);
            List<RoleMenuEntity> list = roleMenuDao.selectList(queryWrapper);
            if (list != null && list.size() > 0) {
                for (RoleMenuEntity entity : list) {
                    result.add(entity.getMenuId());
                }
            }
        }
        else {
            QueryWrapper<RoleMenuEntity> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("role_id", roleId).eq("system_code", systemCode);
            List<RoleMenuEntity> list = roleMenuDao.selectList(queryWrapper);
            if (list != null && list.size() > 0) {
                for (RoleMenuEntity entity : list) {
                    result.add(entity.getMenuId());
                }
            }
        }
        return createSuccessResult(result);
    }

    /**
     * @description:更新菜单权限
     * @return:
     * @author: 赵进华
     * @time: 2020/3/30 17:30
     */
    @Override
    public ApiResponseResult updateMenuRole(UpdateMenuRoleVo vo) throws Exception {
        //先移除之前的权限
        roleMenuDao.deleteByRoleId(vo.getRoleId());
        //保存的菜单角色列表
        List<RoleMenuEntity> roleMenuList = new ArrayList<>();
        if (vo.getMenuIdList() != null && vo.getMenuIdList().length > 0) {
            for (String menuId : vo.getMenuIdList()) {
                MenuEntity menuEntity = menuDao.selectById(menuId);
                if (menuEntity != null) {
                    RoleMenuEntity roleMenuEntity = new RoleMenuEntity();
                    roleMenuEntity.setId(UUIDFactory.createId());
                    roleMenuEntity.setMenuId(menuId);
                    roleMenuEntity.setMenuParentId(menuEntity.getParent());
                    roleMenuEntity.setRoleId(vo.getRoleId());
                    roleMenuEntity.setSystemCode(menuEntity.getSystemCode());
                    roleMenuEntity.setStatus(1);
                    roleMenuEntity.setCreateUser(RequestUtils.getUserName());
                    roleMenuEntity.setCreateDate(new Date());
                    roleMenuList.add(roleMenuEntity);
                }
            }
            //批量保存菜单角色
            if (roleMenuList.size() > 0) {
                roleMenuDao.addList(roleMenuList);
            }
        }
        return createSuccessResult(null);
    }


    /**
     * @Desc:   获取角色下所有的用户信息
     * @param vo
     * @Author:邓磊
     * @UpdateDate:2020/4/22 20:45
     * @return: 返回
     */
    @Override
    public ApiResponseResult queryRoleUserList(RoleVo vo) throws Exception {
        ApiResponseResult result = new ApiResponseResult();
        List<UserUpdateLogo> userRoleEntities = new ArrayList<UserUpdateLogo>();
        //角色ID查询获取角色用户中间表的所有角色下的用户
        List<UserRoleEntity> userRoleEntityList = roleDao.queryRoleUser(vo);
        if(CollectionUtils.isNotEmpty(userRoleEntityList)){
            userRoleEntityList.stream().forEach(userRoleEntity -> {
                UserEntity userEntity = new UserEntity();
                userEntity.setId(userRoleEntity.getUserId());
                UserEntityResponseVO userEntityResponseVO = userDao.queryUserVoInfo(userEntity);
                if(null != userEntityResponseVO && StringUtils.isNotEmpty(userEntityResponseVO.getPlatformAuthority())){
                    String[] split = userEntityResponseVO.getPlatformAuthority().split(",");
                    for(int i=0;i<split.length;i++){
                        if("1".equals(split[i])){
                            UserUpdateLogo userLogo = new UserUpdateLogo();
                            userLogo.setId(userRoleEntity.getUserId());
                            UserUpdateLogo updateLogo = userDao.queryRoleInfoUserList(userLogo);
                            if(null !=updateLogo && StringUtils.isNotEmpty(updateLogo.getUserLogoId())){//用户图片Logo
                                PicEntity picVo = new PicEntity();
                                picVo.setTableId(updateLogo.getUserLogoId());
                                List<PicEntity> picEntities = picDao.queryPicEntityList(picVo);
                                if(CollectionUtils.isNotEmpty(picEntities)){
                                    updateLogo.setPicEntityList(picEntities);
                                }
                            }
                            userRoleEntities.add(updateLogo);
                        }
                    }
                }
            });
        }
        result.setData(userRoleEntities);
        result.setCode(MessageCode.SUCCESS);
        result.setMsg(MessageCode.SUCCESS_TEXT);
        return result;
    }

    /**
     * @description:saas角色新增
     * @return:
     * @author: 赵进华
     * @time: 2020/7/2 10:33
     */
    @Override
    public ApiResponseResult addSaasRole(SaasRoleVo vo) throws Exception {
        // 返回的对象
        ApiResponseResult result = new ApiResponseResult();
        RoleEntity entity = new RoleEntity();
        String roleId=vo.getRoleId();
        entity.setId(roleId);
        entity.setRoleCode(Constants.SAAS);
        entity.setRoleName(vo.getRoleName());
        entity.setRoleDesc(vo.getRoleDesc());
        entity.setRoleType(2);
        entity.setAppType(vo.getAppType());
        entity.setCreateUser(RequestUtils.getUserName());
        entity.setTenantId(Constants.SAAS);
        entity.setCreateDate(new Date());
        // 新增角色
        int no = roleDao.insert(entity);
        if (no > 0) {
            //新增角色的权限
            //先移除之前的权限
            roleMenuDao.deleteByRoleId(roleId);
            //保存的菜单角色列表
            List<RoleMenuEntity> roleMenuList = new ArrayList<>();
            if (vo.getMenuIdList() != null && vo.getMenuIdList().length > 0) {
                for (String menuId : vo.getMenuIdList()) {
                    MenuEntity menuEntity = menuDao.selectById(menuId);
                    if (menuEntity != null) {
                        RoleMenuEntity roleMenuEntity = new RoleMenuEntity();
                        roleMenuEntity.setId(UUIDFactory.createId());
                        roleMenuEntity.setMenuId(menuId);
                        roleMenuEntity.setMenuParentId(menuEntity.getParent());
                        roleMenuEntity.setRoleId(roleId);
                        roleMenuEntity.setSystemCode(menuEntity.getSystemCode());
                        roleMenuEntity.setStatus(1);
                        roleMenuEntity.setCreateUser(RequestUtils.getUserName());
                        roleMenuEntity.setCreateDate(new Date());
                        roleMenuList.add(roleMenuEntity);
                    }
                }
                //批量保存菜单角色
                if (roleMenuList.size() > 0) {
                    roleMenuDao.addList(roleMenuList);
                }
            }
            result.setCode(MessageCode.SUCCESS);
            result.setMsg("成功");
        } else {
            result.setCode(MessageCode.FAIL);
            result.setMsg("新增失败");
        }
        return result;
    }

    /**
     * @description:saas角色更新
     * @return:
     * @author: 赵进华
     * @time: 2020/7/2 10:33
     */
    @Override
    public ApiResponseResult updateSaasRole(SaasRoleVo vo) throws Exception {
        // 返回的对象
        ApiResponseResult result = new ApiResponseResult();
        RoleEntity entity = new RoleEntity();
        String roleId=vo.getRoleId();
        entity.setId(roleId);
        entity.setRoleName(vo.getRoleName());
        entity.setRoleDesc(vo.getRoleDesc());
        entity.setModifyUser(RequestUtils.getUserName());
        entity.setModifyDate(new Date());

        // 新增角色
        int no = roleDao.updateById(entity);
        if (no > 0) {
            //新增角色的权限
            //先移除之前的权限
            roleMenuDao.deleteByRoleId(roleId);
            //保存的菜单角色列表
            List<RoleMenuEntity> roleMenuList = new ArrayList<>();
            if (vo.getMenuIdList() != null && vo.getMenuIdList().length > 0) {
                for (String menuId : vo.getMenuIdList()) {
                    MenuEntity menuEntity = menuDao.selectById(menuId);
                    if (menuEntity != null) {
                        RoleMenuEntity roleMenuEntity = new RoleMenuEntity();
                        roleMenuEntity.setId(UUIDFactory.createId());
                        roleMenuEntity.setMenuId(menuId);
                        roleMenuEntity.setMenuParentId(menuEntity.getParent());
                        roleMenuEntity.setRoleId(roleId);
                        roleMenuEntity.setSystemCode(menuEntity.getSystemCode());
                        roleMenuEntity.setStatus(1);
                        roleMenuEntity.setCreateUser(RequestUtils.getUserName());
                        roleMenuEntity.setCreateDate(new Date());
                        roleMenuList.add(roleMenuEntity);
                    }
                }
                //批量保存菜单角色
                if (roleMenuList.size() > 0) {
                    roleMenuDao.addList(roleMenuList);
                }
            }
            result.setCode(MessageCode.SUCCESS);
            result.setMsg("成功");
        } else {
            result.setCode(MessageCode.FAIL);
            result.setMsg("新增失败");
        }
        return result;
    }

    /**
     * @description:删除角色
     * @return:
     * @author: 赵进华
     * @time: 2020/7/2 14:52
     */
    @Override
    public ApiResponseResult deleteRole(String id) throws Exception {
        ApiResponseResult result = new ApiResponseResult();
        //删除角色
        Integer no = roleDao.deleteById(id);
        if (no > 0) {
            //删除角色下的菜单权限
            roleMenuDao.deleteByRoleId(id);
            result.setCode(MessageCode.SUCCESS);
            result.setMsg(MessageCode.SUCCESS_TEXT);
            result.setData(no);
        } else {
            result.setCode(MessageCode.FAIL);
            result.setMsg(MessageCode.FAIL_TEXT);
            result.setData(null);
        }
        return result;
    }

    /**
     * 物业根据条件分页查询
     * @param requestPageVO
     * @return
     * @throws Exception
     */
    @Override
    public ApiResponseResult queryBasePage(RequestPageVO<RoleEntity> requestPageVO) throws Exception {
        PageHelper.startPage(requestPageVO.getCurrentPage(), requestPageVO.getPageSize(), true);
        List<RoleEntity> list = getBaseMapper().queryList(requestPageVO.getEntity());
        PageInfo<RoleEntity> pageInfo = new PageInfo<RoleEntity>(list);
        return createSuccessResult(pageInfo);
    }

    /**
     * saas根据条件分页查询
     * @param requestPageVO
     * @return
     * @throws Exception
     */
    @Override
    public ApiResponseResult querySaasPage(RequestPageVO<RoleEntity> requestPageVO) throws Exception {
        PageHelper.startPage(requestPageVO.getCurrentPage(), requestPageVO.getPageSize(), true);
        requestPageVO.getEntity().setCreateUser(RequestUtils.getUserId());
        log.info("querySaasRolePage entity:"+requestPageVO.getEntity().toString());
        List<RoleEntity> list = roleDao.querySaasList(requestPageVO.getEntity());
        PageInfo<RoleEntity> pageInfo = new PageInfo<RoleEntity>(list);
        return createSuccessResult(pageInfo);
    }
}
