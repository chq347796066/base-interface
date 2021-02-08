package com.spring.baseinfo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.spring.base.dao.BaseDao;
import com.spring.base.entity.baseinfo.CompanyEntity;
import com.spring.base.entity.baseinfo.MenuEntity;
import com.spring.base.entity.baseinfo.RoleEntity;
import com.spring.base.entity.baseinfo.UserEntity;
import com.spring.base.service.impl.BaseServiceImpl;
import com.spring.base.vo.baseinfo.menu.MenuAddVo;
import com.spring.base.vo.baseinfo.menu.MenuOneVo;
import com.spring.base.vo.baseinfo.menu.MenuTwoVo;
import com.spring.base.vo.baseinfo.menu.MenuUpdateVo;
import com.spring.baseinfo.dao.ICompanyDao;
import com.spring.baseinfo.dao.IUserDao;
import com.spring.common.constants.MessageCode;
import com.spring.common.request.RequestUtils;
import com.spring.common.response.ApiResponseResult;
import com.spring.common.util.id.UUIDFactory;
import com.spring.baseinfo.dao.IMenuDao;
import com.spring.baseinfo.service.IMenuService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author 作者：ZhaoJinHua
 * @date : 创建时间：2020-03-13 11:18:07
 * @Desc类说明: 菜单业务接口实现类
 */
@Slf4j
@Service("menuService")
public class MenuServiceImpl extends BaseServiceImpl<MenuEntity, String> implements IMenuService {

    @Autowired
    private IMenuDao menuDao;

    @Autowired
    private ICompanyDao companyDao;

    @Autowired
    private IUserDao userDao;

    @Override
    public BaseDao getBaseMapper() {
        return menuDao;
    }

    /**
     * 新增菜单
     *
     * @param vo
     * @return
     * @throws Exception
     * @author 作者：ZhaoJinHua
     * @version 创建时间：2020-03-13 11:18:07
     */
    @Override
    public ApiResponseResult addMenu(MenuAddVo vo) throws Exception {
        // 返回的对象
        ApiResponseResult result = new ApiResponseResult();
        MenuEntity entity = new MenuEntity();
        BeanUtils.copyProperties(vo, entity);
        entity.setId(UUIDFactory.createId());
        entity.setCreateUser(RequestUtils.getUserId());
        entity.setCreateDate(new Date());
        entity.setStatus("1");
        // 新增
        int no = menuDao.insert(entity);
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
     * 更新菜单
     *
     * @param vo
     * @return
     * @throws Exception
     * @author 作者：ZhaoJinHua
     * @version 创建时间：2020-03-13 11:18:07
     */
    @Override
    public ApiResponseResult updateMenu(MenuUpdateVo vo) throws Exception {
        MenuEntity entity = new MenuEntity();
        BeanUtils.copyProperties(vo, entity);
        entity.setModifyUser(RequestUtils.getUserId());
        entity.setModifyDate(new Date());
        // 更新
        int no = menuDao.updateById(entity);
        if (no > 0) {
            return createSuccessResult(null);
        }
        return createFailResult();
    }

    /**
     * 查询管理员菜单
     *
     * @return
     * @throws Exception
     * @author 作者：赵进华
     * @version 创建时间：2019年6月26日 上午11:39:32
     */
    @Override
    public List<MenuOneVo> queryAdminMenu(String systemCode) throws Exception {
        //查询第一级菜单
        List<MenuOneVo> oneList = menuDao.queryMenuOne(systemCode);
        if (oneList != null) {
            for (int i = 0; i < oneList.size(); i++) {
                MenuOneVo one = oneList.get(i);
                if (one != null) {
                    //查询第二级菜单
                    List<MenuTwoVo> twoList = menuDao.queryMenuTwo(one.getId(), systemCode);
                    if (CollectionUtils.isNotEmpty(twoList)) {
                        for (int ii = 0; ii < twoList.size(); ii++) {
                            MenuTwoVo two = twoList.get(ii);
                            if (two != null) {
                                //判断下级是否是页面
                                int no = menuDao.checkPage(two.getId());
                                if (no > 0) {
                                    //查询第三级级菜单
                                    List<MenuTwoVo> threeList = menuDao.queryMenuTwo(two.getId(), systemCode);
                                    if (CollectionUtils.isNotEmpty(threeList)) {
                                        for (int iii = 0; iii < threeList.size(); iii++) {
                                            MenuTwoVo three = threeList.get(iii);
                                            if (three != null) {
                                                //查询按钮列表
                                                List<MenuTwoVo> buttonList = menuDao.queryButton(three.getId(), systemCode);
                                                List<String> buttonArray = new ArrayList<>();
                                                if (buttonList != null) {
                                                    buttonList.forEach(item -> {
                                                        String but = item.getMenuCode();
                                                        buttonArray.add(but);
                                                    });
                                                }
                                                //设置按钮列表,用户权限按钮列表
                                                three.setButtonList(buttonArray);
                                            }
                                        }
                                        //设置三级菜单
                                        two.setChildren(threeList);
                                    }
                                } else {
                                    //查询按钮列表
                                    List<MenuTwoVo> buttonList = menuDao.queryButton(two.getId(), systemCode);
                                    List<String> buttonArray = new ArrayList<>();
                                    if (CollectionUtils.isNotEmpty(buttonList)) {
                                        buttonList.forEach(item -> {
                                            String but = item.getMenuCode();
                                            buttonArray.add(but);
                                        });
                                    }
                                    //设置按钮列表,用户权限按钮列表
                                    two.setButtonList(buttonArray);
                                }
                            }
                        }
                        //设置第二级菜单
                        one.setChildren(twoList);
                    }

                    //查询按钮列表
                    List<MenuTwoVo> twoButtonList = menuDao.queryButton(one.getId(), systemCode);
                    if (CollectionUtils.isNotEmpty(twoButtonList)) {
                        List<String> buttonArray = new ArrayList<>();
                        if (CollectionUtils.isNotEmpty(twoButtonList)) {
                            twoButtonList.forEach(item -> {
                                buttonArray.add(item.getMenuCode());
                            });
                        }
                        //设置第二级按钮
                        one.setButtonList(buttonArray);
                    }

                }
            }
        }
        return oneList;
    }

    /**
     * 根据角色查询菜单按钮权限
     *
     * @return
     * @throws Exception
     * @author 作者：赵进华
     * @version 创建时间：2019年6月26日 上午11:39:32
     */
    @Override
    public List<MenuOneVo> queryRoleMenu(String roleId, String parentId, String systemCode) throws Exception {
        //角色id转列表
        List<String> roleIdList = Arrays.asList(roleId.split("\\,"));
        //查询第一级菜单
        List<MenuOneVo> oneList = menuDao.queryRoleMenuOne(roleIdList, systemCode);
        if (oneList != null) {
            for (int i = 0; i < oneList.size(); i++) {
                MenuOneVo one = oneList.get(i);
                if (one != null) {
                    //角色id转列表
                    List<String> roleIdTwoList = Arrays.asList(roleId.split("\\,"));
                    //查询第二级菜单
                    List<MenuTwoVo> twoList = menuDao.queryRoleMenuTwo(roleIdTwoList, one.getId(), systemCode);
                    if (twoList != null) {
                        for (int ii = 0; ii < twoList.size(); ii++) {
                            MenuTwoVo two = twoList.get(ii);
                            if (two != null) {
                                //角色id转列表
                                List<String> roleIdButtonList = Arrays.asList(roleId.split("\\,"));
                                //查询按钮列表
                                List<MenuTwoVo> buttonList = menuDao.queryRoleButton(roleIdButtonList, two.getId(), systemCode);
                                List<String> buttonArray = new ArrayList<>();
                                if (buttonList != null) {
                                    buttonList.forEach(item -> {
                                        String but = item.getMenuCode();
                                        buttonArray.add(but);
                                    });
                                }
                                //设置按钮列表,用户权限按钮列表
                                two.setButtonList(buttonArray);
                                //查询页面需要控制的按钮列表
                                List<MenuTwoVo> buttonListAll = menuDao.queryButton(two.getId(), systemCode);
                                List<String> buttonArrayAll = new ArrayList<>();
                                if (buttonList != null) {
                                    buttonListAll.forEach(item -> {
                                        String but = item.getMenuCode();
                                        buttonArrayAll.add(but);
                                    });
                                }
                            }
                        }
                        //设置第二级菜单
                        one.setChildren(twoList);
                    }


                    //查询按钮列表
                    List<MenuTwoVo> twoButtonList = menuDao.queryButton(one.getId(), systemCode);
                    if (CollectionUtils.isNotEmpty(twoButtonList)) {
                        List<String> buttonArray = new ArrayList<>();
                        if (CollectionUtils.isNotEmpty(twoButtonList)) {
                            twoButtonList.forEach(item -> {
                                buttonArray.add(item.getMenuCode());
                            });
                        }
                        //设置第二级按钮
                        one.setButtonList(buttonArray);
                    }
                }
            }
        }
        return oneList;

    }

    /**
     * 查询管理系统树形菜单
     *
     * @return
     * @throws Exception
     * @author 作者：赵进华
     * @version 创建时间：2019年5月24日 下午3:19:25
     */
    @Override
    public ApiResponseResult queryManageTreeMenu() throws Exception {
        List<MenuOneVo> list = new ArrayList<>();
        //判断是否是saas用户
       if (RequestUtils.getIsSaas()) {
            //获取租户管理员购买的角色权限
            String roleId = getTenantRoleId(RequestUtils.getTenantId());
            //saas用户只能查询租户购买的菜单权限
            list = queryRoleMenu(roleId, "0","manage");
        } else {
            //非saas用户查询所有菜单
            list = menuDao.queryManageMenu("0");
        }
        return this.createSuccessResult(list);
    }

    /**
     * 查询收费系统树形菜单
     *
     * @return
     * @throws Exception
     * @author 作者：赵进华
     * @version 创建时间：2019年5月24日 下午3:19:25
     */
    @Override
    public ApiResponseResult queryPaymentTreeMenu() throws Exception {
        List<MenuOneVo> list = new ArrayList<>();
        //判断是否是saas用户
        if (RequestUtils.getIsSaas()) {
            //获取租户管理员购买的角色权限
            String roleId = getTenantRoleId(RequestUtils.getTenantId());
            //saas用户只能查询租户购买的菜单权限
            list = queryRoleMenu(roleId, "0","payment");
        } else {
            //非saas用户查询所有菜单
            list = menuDao.queryPaymentMenu("0");
        }
        return this.createSuccessResult(list);
    }

    /**
     * 查询APP树形菜单
     *
     * @return
     * @throws Exception
     * @author 作者：赵进华
     * @version 创建时间：2019年5月24日 下午3:19:25
     */
    @Override
    public ApiResponseResult queryAppTreeMenu() throws Exception {
        List<MenuOneVo> list = new ArrayList<>();
        //判断是否是saas用户
        if (RequestUtils.getIsSaas()) {
            log.info("queryAppTreeMenu is saas:"+RequestUtils.getIsSaas());
            //获取租户管理员购买的角色权限
            String roleId = getTenantRoleId(RequestUtils.getTenantId());
            //saas用户只能查询租户购买的菜单权限
            list = queryRoleMenu(roleId, "0","app");
        } else {
            //非saas用户查询所有菜单
            list = menuDao.queryAppMenu("0");
        }
        return this.createSuccessResult(list);
    }

    /**
     * @description:查询租户购买的角色菜单权限
     * @return:
     * @author: 赵进华
     * @time: 2020/7/9 15:52
     */
    String getTenantRoleId(String tenantId) {
        String roleId = "";
        //根据租户id获取租户对象
        CompanyEntity companyEntity = companyDao.selectById(tenantId);
        if (companyEntity != null) {
            //获取saas租房注册的管理员
            String userId = companyEntity.getMobile();
            QueryWrapper<UserEntity> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("user_code", userId);
            //查询saas租房注册的管理员
            List<UserEntity> userList = userDao.selectList(queryWrapper);
            if (CollectionUtils.isNotEmpty(userList)) {
                UserEntity userEntity = userList.get(0);
                if (userEntity != null) {
                    //获取购买的权限
                    roleId = userEntity.getRoleId();
                }
            }
        }
        return roleId;
    }


    /**
     * 删除菜单
     *
     * @param id
     * @return
     */
    @Override
    public ApiResponseResult deleteMenu(String id) throws Exception {
        ApiResponseResult result = new ApiResponseResult();
        //判断菜单下是否有下级菜单，如果有，不允许删除
        QueryWrapper<MenuEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("parent", id);
        // 根据用户id和密码查询是否正确
        List<MenuEntity> menuList = menuDao.selectList(queryWrapper);
        if (menuList.size() > 0) {
            result.setCode(MessageCode.FAIL);
            result.setMsg("该菜单下有子菜单，请先删除子菜单!");
            return result;
        }
        //删除
        Integer no = menuDao.deleteById(id);
        if (no > 0) {
            result.setCode(MessageCode.SUCCESS);
            result.setMsg(MessageCode.SUCCESS_TEXT);
        } else {
            result.setCode(MessageCode.FAIL);
            result.setMsg(MessageCode.FAIL_TEXT);
        }
        return result;
    }
}
