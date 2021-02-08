package com.spring.baseinfo.dao;

import com.spring.base.entity.baseinfo.CompanyEntity;
import com.spring.base.dao.BaseDao;
import com.spring.base.entity.baseinfo.DictionaryEntity;
import com.spring.base.vo.baseinfo.company.CompanySearchVo;
import com.spring.base.vo.baseinfo.company.CompanyUpdateVo;
import com.spring.base.vo.baseinfo.dictionary.DictionaryDataVo;
import com.spring.base.vo.baseinfo.dictionary.DictionaryNodeVo;
import com.spring.base.vo.baseinfo.dictionary.DictionaryTreeVo;
import com.spring.base.vo.saas.BuyConfirmVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author 作者：ZhaoJinHua
 * @date : 创建时间：2020-03-31 19:02:26
 * @Desc类说明:  公司Dao
 */
@Mapper
public interface ICompanyDao extends BaseDao<CompanyEntity> {

 /**
  * 新增
  * @param companyEntity
  * @return
  */
  int insertCompany(CompanyEntity companyEntity);

 /**
  * 修改
  */
  int updateCompany(CompanyEntity companyEntity);

 /**
  * 查询对象
  * @return
  * @param companyEntity
  */
  CompanyEntity queryCompanyEntity(CompanyUpdateVo companyEntity);

 /**
  * @Desc:  查询公司实体类信息
  * @param companyEntity
  * @Author:邓磊
  * @UpdateDate:2020/4/26 16:44
  * @return: 返回
  */
 CompanyEntity queryCompanyEntityInfo(CompanyEntity companyEntity);


 /**
  * @Desc:校验公司名称唯一性
  * @param companyEntity
  * @Author:邓磊
  * @UpdateDate:2020/6/4 10:19
  * @return: 返回
  */
 List<CompanyEntity> queryCompanyName(CompanyEntity companyEntity);

 /**
  * @description:分页查询公司
  * @return:
  * @author: 赵进华
  * @time: 2020/6/4 17:01
  */
 List<CompanyEntity> queryListPage(CompanyEntity entity);

 /**
  * 根据子项获取父项
  * @param id
  * @return
  */
 CompanyEntity queryParentListById(@Param("companyId") String id);

 /**
  * 根据父项获取子项
  * @param id
  * @return
  */
 CompanyEntity queryChildrenListByParent(@Param("companyId") String id);

 /**
  * @description:查询第一级公司树形结构
  * @return:
  * @author: 赵进华
  * @time: 2020/6/4 17:29
  */
 List<DictionaryDataVo> queryCompanyTree(CompanySearchVo vo);

 /**
  * @description:查询第二级公司树形结构
  * @return:
  * @author: 赵进华
  * @time: 2020/6/12 15:06
  */
 List<DictionaryDataVo> queryTreeByParentId(CompanySearchVo vo);

/**
 * @description:saas购买应用更新小区数和购买时间
 * @return:
 * @author: 赵进华
 * @time: 2020/7/15 9:58
 */
int saasBuyApp(BuyConfirmVo vo);

 List<CompanyEntity> queryListTree(String id);
}
