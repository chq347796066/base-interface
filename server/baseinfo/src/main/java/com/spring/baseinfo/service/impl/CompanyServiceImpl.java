package com.spring.baseinfo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.spring.base.dao.BaseDao;
import com.spring.base.entity.baseinfo.*;
import com.spring.base.service.impl.BaseServiceImpl;
import com.spring.base.vo.baseinfo.company.AddSaasVo;
import com.spring.base.vo.baseinfo.company.CompanyAddVo;
import com.spring.base.vo.baseinfo.company.CompanyUpdateVo;
import com.spring.baseinfo.dao.ICompanyDao;
import com.spring.baseinfo.dao.IPicDao;
import com.spring.baseinfo.dao.IRoleDao;
import com.spring.baseinfo.dao.IUserDao;
import com.spring.baseinfo.service.ICompanyService;
import com.spring.common.constants.Constants;
import com.spring.common.constants.MessageCode;
import com.spring.common.page.RequestPageVO;
import com.spring.common.request.RequestUtils;
import com.spring.common.response.ApiResponseResult;
import com.spring.common.util.date.DateUtil;
import com.spring.common.util.id.UUIDFactory;
import com.spring.common.util.md5.Md5Util;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author 作者：ZhaoJinHua
 * @date : 创建时间：2020-03-31 19:02:26
 * @Desc类说明: 公司业务接口实现类
 */
@Slf4j
@Service("companyService")
public class CompanyServiceImpl extends BaseServiceImpl<CompanyEntity, String> implements ICompanyService {

    @Autowired
    private IRoleDao roleDao;

    @Autowired
    private IUserDao userDao;

    @Autowired
    private ICompanyDao companyDao;

    @Autowired
    private IPicDao picDao;

    @Override
    public BaseDao getBaseMapper() {
        return companyDao;
    }

    @Override
    public CompanyEntity getCompanyInfo(String id) {
        return companyDao.selectById(id);
    }

    /**
     * 新增 公司
     *
     * @param vo
     * @return
     * @throws Exception
     * @author 作者：ZhaoJinHua
     * @version 创建时间：2020-03-31 19:02:26
     */
    @Override
    public ApiResponseResult addCompany(CompanyAddVo vo) throws Exception {
        // 返回的对象
        ApiResponseResult result = new ApiResponseResult();
        CompanyEntity entity = new CompanyEntity();
        BeanUtils.copyProperties(vo, entity);
        final String id = UUIDFactory.createId();
        entity.setId(id);
        //第一级公司，设置租户id，隔离数据
        if ("0".equals(vo.getParent())) {
            entity.setTenantId(id);
        }
        entity.setStatus(Constants.Status.ENABLE);
        entity.setCompanyCode("PC" + id);
        entity.setCreateUser(RequestUtils.getUserId());
        //校验公司名称唯一性
        CompanyEntity entityVo = new CompanyEntity();
        entityVo.setCompanyName(entity.getCompanyName());
        List<CompanyEntity> companyEntities = companyDao.queryCompanyName(entityVo);
        if (companyEntities.size() > 0) {
            result.setCode(MessageCode.FAIL);
            result.setMsg("你填写的公司名称已存在，请核实后请重新输入！");
            return result;
        }
        // 新增
        int no = companyDao.insert(entity);
        List<PicEntity> picEntityList = entity.getPicEntityList();
        List<PicEntity> list = new ArrayList<PicEntity>();
        if (CollectionUtils.isNotEmpty(picEntityList)) {
            picEntityList.stream().forEach(picEntity -> {
                PicEntity picVo = new PicEntity();
                picVo.setId(UUIDFactory.createId());
                picVo.setTableId(id);
                picVo.setDataId(picEntity.getDataId());
                picVo.setName(picEntity.getName());
                picVo.setUrl(picEntity.getUrl());
                picVo.setStatus(picEntity.getStatus());//图片类型（1公司LOG 2营业执照）
                picVo.setTenantId(RequestUtils.getUserId());
                picVo.setDelFlag(0);//删除标志（0 未删除 1 已删除）")
                picVo.setCreateUser(RequestUtils.getUserId());
                picVo.setCreateDate(new Date());
                list.add(picVo);
            });
        }
        if (CollectionUtils.isNotEmpty(list)) {
            picDao.addList(list);
        }
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
     * 更新 公司
     *
     * @param vo
     * @return
     * @throws Exception
     * @author 作者：ZhaoJinHua
     * @version 创建时间：2020-03-31 19:02:26
     */
    @Override
    public ApiResponseResult updateCompany(CompanyUpdateVo vo) throws Exception {
        ApiResponseResult result = new ApiResponseResult();
        CompanyEntity entity = new CompanyEntity();
        BeanUtils.copyProperties(vo, entity);
        // 更新
        //校验公司名称唯一性
        CompanyEntity entityVo = new CompanyEntity();
        entityVo.setCompanyName(entity.getCompanyName());
        entityVo.setId(entity.getId());
        List<CompanyEntity> companyEntities = companyDao.queryCompanyName(entityVo);
        if (companyEntities.size() > 0) {
            result.setCode(MessageCode.FAIL);
            result.setMsg("你填写的公司名称已存在，请核实后请重新输入！");
            return result;
        }
        int no = companyDao.updateCompany(entity);
        List<PicEntity> picEntityList = vo.getPicEntityList();
        if (CollectionUtils.isNotEmpty(picEntityList)) {
            PicEntity picEntity = new PicEntity();
            picEntity.setTableId(vo.getId());
            picEntity.setDelFlag(1);
            picDao.updatePicDelFlag(picEntity);
        }
        List<PicEntity> list = new ArrayList<PicEntity>();
        if (CollectionUtils.isNotEmpty(picEntityList)) {
            picEntityList.stream().forEach(picEntity -> {
                PicEntity picVo = new PicEntity();
                picVo.setId(UUIDFactory.createId());
                picVo.setTableId(vo.getId());
                picVo.setDataId(picEntity.getDataId());
                picVo.setName(picEntity.getName());
                picVo.setUrl(picEntity.getUrl());
                //图片类型（1公司LOG 2营业执照）
                picVo.setStatus(picEntity.getStatus());
                picVo.setTenantId(RequestUtils.getUserId());
                list.add(picVo);
            });
        }
        if (CollectionUtils.isNotEmpty(list)) {
            picDao.addList(list);
        }
        if (no > 0) {
            return createSuccessResult(null);
        }
        return createFailResult();
    }

    /**
     * 查看对象数据
     *
     * @param vo
     * @return
     * @throws Exception
     */
    @Override
    public ApiResponseResult queryCompanyEntity(CompanyUpdateVo vo) {
        ApiResponseResult result = new ApiResponseResult();
        CompanyEntity companyEntities = companyDao.queryCompanyEntity(vo);
        if (null != companyEntities) {
            if (StringUtils.isNotEmpty(companyEntities.getId())) {
                PicEntity picEntity = new PicEntity();
                picEntity.setTableId(companyEntities.getId());
                picEntity.setDelFlag(0);
                List<PicEntity> picEntities = picDao.queryPicEntityList(picEntity);
                if (CollectionUtils.isNotEmpty(picEntities)) {
                    companyEntities.setPicEntityList(picEntities);
                }
            }
            result.setData(companyEntities);
        }
        result.setCode(MessageCode.SUCCESS);
        result.setMsg("成功");
        return result;
    }

    /**
     * @param requestPageVO
     * @Desc: 公司导出信息
     * @Author:邓磊
     * @UpdateDate:2020/5/14 11:17
     * @return: 返回
     */
    @Override
    public void exportCompanyEntityInfo(CompanyEntity requestPageVO) throws Exception {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletResponse response1 = servletRequestAttributes.getResponse();
        if (requestPageVO != null) {
            List<CompanyEntity> list = companyDao.queryList(requestPageVO);
            excelDownload(response1, list);
        }
    }

    public void excelDownload(HttpServletResponse response, List<CompanyEntity> list) throws Exception {
        //表头数据
        String[] header = {"公司名称", "公司电话", "公司地址", "法人姓名", "法人电话"};
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("公司信息");
        sheet.setDefaultColumnWidth(15);
        HSSFCellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setFillForegroundColor(IndexedColors.YELLOW.index);
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        HSSFRow headrow = sheet.createRow(0);
        for (int i = 0; i < header.length; i++) {
            HSSFCell cell = headrow.createCell(i);
            HSSFRichTextString text = new HSSFRichTextString(header[i]);
            cell.setCellValue(text);
            cell.setCellStyle(headerStyle);
        }
        for (int i = 0; i < list.size(); i++) {
            HSSFRow row1 = sheet.createRow(i + 1);
            row1.createCell(0).setCellValue(new HSSFRichTextString(list.get(i).getCompanyName()));
            row1.createCell(1).setCellValue(new HSSFRichTextString(list.get(i).getMobile()));
            row1.createCell(2).setCellValue(new HSSFRichTextString(list.get(i).getAddress()));
            row1.createCell(3).setCellValue(new HSSFRichTextString(list.get(i).getLegalName()));
            row1.createCell(4).setCellValue(new HSSFRichTextString(list.get(i).getLegalMobile()));
        }
        response.setHeader("content-Type", "application/ms-excel");
        String name = "公司信息";
        String fileName = URLEncoder.encode(name, "utf-8").replaceAll("\\+", "%20");
        response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xls");
        response.flushBuffer();
        workbook.write(response.getOutputStream());
    }


    /**
     * 根据父项 获取子项
     * @param CompanyId
     * @return
     */

    @Override
    public List<String> queryCompanyChidrenList(String companyId){
        CompanyEntity companyEntities = companyDao.queryChildrenListByParent(companyId);
        List<String> childrenNameByParent = getChildrenNameByParent(companyEntities, new ArrayList<>());
        return childrenNameByParent;
    }


    /**
     * 获取父项名称
     * @param companyEntities
     * @param parenStr
     * @return
     */
    private List<String> getChildrenNameByParent(CompanyEntity companyEntities, List<String> parenStr) {
        if(companyEntities!=null){
            List<CompanyEntity> childrenList = companyEntities.getChildren();
            if(CollectionUtils.isNotEmpty(childrenList)){
                childrenList.forEach(item->{
                    getChildrenNameByParent(item,parenStr);
                });
            }
            parenStr.add(companyEntities.getId());
        }

        return parenStr;
    }
    /**
     * 根据条件分页查询公司
     *
     * @param requestPageVO
     * @return
     * @throws Exception
     */
    @Override
    public ApiResponseResult queryPageCompany(RequestPageVO<CompanyEntity> requestPageVO) throws Exception {
        CompanyEntity entity = requestPageVO.getEntity();
        PageHelper.startPage(requestPageVO.getCurrentPage(), requestPageVO.getPageSize(), true);
        String companyId=RequestUtils.getCompanyId();
        if (StringUtils.isNotBlank(companyId)){
            entity.setId(companyId);
        }
        List<CompanyEntity> list = companyDao.queryListPage(entity);
        PageInfo<CompanyEntity> pageInfo = new PageInfo<>(list);
        return createSuccessResult(pageInfo);
    }

    /**
     * 根据条件分页查询公司
     *
     * @return
     * @throws Exception
     */
    @Override
    public ApiResponseResult queryCompanyTree() throws Exception {
        String companyId=RequestUtils.getCompanyId();
        List<CompanyEntity> list = companyDao.queryListTree(companyId);
        return createSuccessResult(list);
    }

    /**
     * @description:查询公司树形结构
     * @return:
     * @author: 赵进华
     * @time: 2020/6/4 17:26
     */
    /*@Override
    public ApiResponseResult queryCompanyTree() throws Exception {
        String tenantId = "";
        CompanySearchVo one = new CompanySearchVo();
        //超级管理员有所有权限
        if (!RequestUtils.getUserId().equals(Constants.SYSADMIN)) {
            if (!StringUtils.isEmpty(RequestUtils.getTenantId())) {
                tenantId = RequestUtils.getTenantId();
                one.setTenantId(tenantId);
            }
        }
        //根据权限查询第一级公司
        List<DictionaryDataVo> list = companyDao.queryCompanyTree(one);
        if (list != null) {
            list.forEach(item -> {
                String companyId = "";
                CompanySearchVo two = new CompanySearchVo();
                two.setTenantId(item.getValue());
                if (!StringUtils.isEmpty(RequestUtils.getCompanyId())) {
                    companyId = RequestUtils.getCompanyId();
                    two.setCompanyId(companyId);
                }
                //根据权限查询第二级公司
                List<DictionaryDataVo> listCompany = companyDao.queryTreeByParentId(two);
                item.setChildren(listCompany);
            });
        }
        return createSuccessResult(list);
    }*/

    @Override
    public ApiResponseResult queryCompanyPage(RequestPageVO<CompanyEntity> requestPageVO) throws Exception {
        CompanyEntity entity = requestPageVO.getEntity();
        if (entity != null) {
            entity.setParent("0");
            entity.setTenantId(RequestUtils.getTenantId());
        }
        PageHelper.startPage(requestPageVO.getCurrentPage(), requestPageVO.getPageSize(), true);
        List<CompanyEntity> list = companyDao.queryListPage(entity);
        PageInfo<CompanyEntity> pageInfo = new PageInfo<>(list);
        return createSuccessResult(pageInfo);
    }

    /**
     * @description:新增saas租户
     * @return:
     * @author: 赵进华
     * @time: 2020/7/3 18:28
     */
    @Override
    public ApiResponseResult addTenant(AddSaasVo vo) throws Exception {
        // 返回的对象
        ApiResponseResult result = new ApiResponseResult();
        CompanyEntity entity = new CompanyEntity();
        BeanUtils.copyProperties(vo, entity);
        entity.setParent("0");
        //第一级公司，设置租户id，隔离数据
        entity.setTenantId(vo.getId());
        entity.setStatus(Constants.Status.ENABLE);
        entity.setCompanyCode("COM" + vo.getId());
        //saas试用期时间
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        Date newDate = DateUtil.stepMonth(date, Constants.SAAS_TRY_DATE);
        entity.setTryDate(newDate);
        //saas试用小区数量
        entity.setCommunityNum(Constants.SAAS_COMMUNITY_NO);
        //saas试用状态
        entity.setSaasStatus(0);
        entity.setCreateUser(RequestUtils.getUserId());
        entity.setCreateDate(new Date());
        // 新增公司
        int no =companyDao.insert(entity);
        if (no > 0) {
            //查询saas试用角色id
            QueryWrapper<RoleEntity> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("app_type", 1);
            // 根据用户id和密码查询是否正确
            List<RoleEntity> roleList = roleDao.selectList(queryWrapper);
            if (CollectionUtils.isNotEmpty(roleList)) {
                RoleEntity roleEntity = roleList.get(0);
                if (roleEntity != null) {
                    //创建saas租户的管理员，绑定角色
                    UserEntity user = new UserEntity();
                    String userId = UUIDFactory.createId();
                    user.setId(userId);
                    user.setUserCode(vo.getMobile());
                    user.setUserName(vo.getContactPeople());
                    user.setRoleId(roleEntity.getId());
                    //电话号码后６为数
                    String password = vo.getMobile().substring(vo.getMobile().length() - 6);
                    //密码为电话号码后6位数
                    user.setPassword(Md5Util.md5Encode(password));
                    //用户状态(1:启用或2:禁用)
                    user.setEnableStatusFlag(1);
                    //是saas用户
                    user.setIsSaas(1);
                    //用户类型(1平台管理员,2物业公司管理员
                    user.setUserType("2");
                    user.setTenantId(vo.getId());
                    //设置用户为租户管理员
                    user.setTenantAdmin(1);
                    user.setCreateUser(RequestUtils.getUserId());
                    user.setCreateDate(new Date());
                    userDao.insert(user);

                    //创建用户角色关系
                    List<UserRoleEntity> userRoleList = new ArrayList<>();
                    UserRoleEntity userRole = new UserRoleEntity();
                    userRole.setId(UUIDFactory.createId());
                    //saas试用版角色id
                    userRole.setRoleId(roleEntity.getId());
                    //设置租户id
                    userRole.setTenantId(vo.getId());
                    userRole.setUserId(userId);
                    userRole.setStatus(1);
                    userRole.setDelFlag(0);
                    userRole.setCreateUser(RequestUtils.getUserId());
                    userRole.setCreateDate(new Date());
                    userRoleList.add(userRole);
                    userDao.addListUserRole(userRoleList);
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
}
