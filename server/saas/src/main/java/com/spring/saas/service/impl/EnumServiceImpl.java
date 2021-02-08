package com.spring.saas.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.spring.base.dao.BaseDao;
import com.spring.base.entity.saas.EnumEntity;
import com.spring.base.entity.saas.HomeBannerEntity;
import com.spring.base.service.impl.BaseServiceImpl;
import com.spring.base.vo.saas.EnumKeyValueVo;
import com.spring.common.util.ConvertUtils;
import com.spring.common.exception.ServiceException;
import com.spring.common.response.ApiResponseResult;
import com.spring.saas.dao.IEnumDao;
import com.spring.saas.service.IEnumService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 枚举服务
 *
 * @author WuJiaQuan
 */
@Slf4j
@Service("enumService")
public class EnumServiceImpl extends BaseServiceImpl<HomeBannerEntity, String> implements IEnumService {

    @Autowired
    private IEnumDao enumDao;

    @Override
    public BaseDao getBaseMapper() {
        return enumDao;
    }


    /**
     * 按枚举类型名称查询枚举列表
     *
     * @param enumType
     * @return
     * @throws Exception
     * @author WuJiaQuan
     * @date 2020/7/14 10:53
     */
    @Override
    public ApiResponseResult queryEnumListByEnumType(String enumType) throws Exception {
        if (StringUtils.isBlank(enumType)) {
            throw new ServiceException("枚举类型不能为空");
        }
        QueryWrapper<EnumEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(EnumEntity::getEnumType, enumType);
        List<EnumEntity> enumList = enumDao.selectList(queryWrapper);
        List<EnumKeyValueVo> enumKeyValueVoList = ConvertUtils.copyPropertiesList(enumList, EnumKeyValueVo.class);
        return createSuccessResult(enumKeyValueVoList);
    }
}
