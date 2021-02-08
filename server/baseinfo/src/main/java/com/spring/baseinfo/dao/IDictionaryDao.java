package com.spring.baseinfo.dao;

import com.spring.base.dao.BaseDao;
import com.spring.base.entity.baseinfo.DictionaryEntity;
import com.spring.base.vo.baseinfo.dictionary.DictionaryDataVo;
import com.spring.base.vo.baseinfo.dictionary.DictionaryNodeVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author 作者：ZhaoJinHua
 * @date : 创建时间：2020-03-18 17:32:18
 * @Desc类说明: 字典Dao
 */
@Mapper
public interface IDictionaryDao extends BaseDao<DictionaryEntity> {

    /**
     * @description:分页查询字典
     * @return:
     * @author: 赵进华
     * @time: 2020/3/23 14:01
     */
    List<DictionaryNodeVo> queryListPage(DictionaryEntity entity);

    /**
     * @description:根据字典类型查询字典数据
     * @return:
     * @author: 赵进华
     * @time: 2020/3/23 14:47
     */
    List<DictionaryDataVo> getListByParentCode(String parentCode) throws Exception;

    /**
     * @description:判断字典类型是否重复
     * @return:
     * @author: 赵进华
     * @time: 2020/4/22 16:52
     */
    int dictTypeExist(String dictType) throws Exception;

}
