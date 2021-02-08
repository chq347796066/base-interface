package com.spring.base.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.spring.base.dao.BaseDao;
import com.spring.base.service.IBaseService;
import com.spring.base.vo.Vo;
import com.spring.common.constants.MessageCode;
import com.spring.common.exception.ServiceException;
import com.spring.common.page.RequestPageVO;
import com.spring.common.response.ApiResponseResult;
import com.spring.common.util.id.IdWorker;
import com.spring.common.util.id.SnowflakeIdWorker;
import com.spring.common.util.id.UUIDFactory;
import com.spring.common.util.reflect.ReflectUtil;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

/**
 * @author 作者：赵进华
 * @version 创建时间：2019年3月21日 上午9:36:54
 * @Desc类说明:ServiceImpl基础父类
 */
public abstract class BaseServiceImpl<T extends Vo<PK>, PK extends Serializable> implements IBaseService<T, PK> {

    public abstract BaseDao getBaseMapper();

    /**
     * 获得自定义返回
     *
     * @param code
     * @param msg
     * @param data
     * @return
     */
    public ApiResponseResult createResult(String code, String msg, Object data) {
        ApiResponseResult ro = new ApiResponseResult();
        ro.setCode(code);
        ro.setMsg(msg);
        ro.setData(data);
        return ro;
    }

    /**
     * 获得失败返回
     *
     * @return
     */
    public ApiResponseResult createFailResult() {
        return createResult(MessageCode.FAIL, MessageCode.FAIL_TEXT, null);
    }

    /**
     * 获得失败返回
     *
     * @return
     */
    public ApiResponseResult createFailResult(String message) {
        return createResult(MessageCode.FAIL, message, null);
    }

    /**
     * 获得成功返回
     *
     * @return
     */
    public ApiResponseResult createSuccessResult(Object data) {
        return createResult(MessageCode.SUCCESS, MessageCode.SUCCESS_TEXT, data);
    }

    /**
     * 获得异常返回
     *
     * @return
     */
    public ApiResponseResult createExceptionResult() {
        return createResult(MessageCode.EXCEPTION, MessageCode.EXCEPTION_TEXT, null);
    }

    /**
     * 新增
     *
     * @param entity
     * @return
     */
    @Override
    public ApiResponseResult add(T entity) throws Exception {
        ApiResponseResult result = new ApiResponseResult();
        if (null == entity.getId() || "".equals(entity.getId())) {
            Class<?> c = ReflectUtil.getClassGenricType(this.getClass(), 1);
            if (null == c) {
                throw new ServiceException("主键类型为空，主键=" + c);
            } else if (c.toString().endsWith(String.class.toString())) {
                entity.setId((PK) UUIDFactory.createId());
            } else if (c.toString().endsWith(Long.class.toString())) {
                entity.setId((PK) new Long(new SnowflakeIdWorker(0, 0).nextId()));
            } else if (c.toString().endsWith(Integer.class.toString())) {
                entity.setId((PK) (new IdWorker(0, 0).nextId() + ""));
            } else {
                throw new ServiceException("未知的主键类型，类型=" + c.toString());
            }
        }
        Integer no = getBaseMapper().insert(entity);
        if (no > 0) {
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
     * 批量新增
     */
    @Override
    public ApiResponseResult addList(List<T> list) throws Exception {
        ApiResponseResult result = new ApiResponseResult();
        if (null != list && list.size() > 0) {
            for (T vo : list) {
                if (null == vo.getId() || "".equals(vo.getId())) {

                    Class<?> c = ReflectUtil.getClassGenricType(this.getClass(), 1);
                    if (null == c) {
                        throw new ServiceException("主键类型为空，主键=" + c);
                    } else if (c.toString().endsWith(String.class.toString())) {
                        vo.setId((PK) UUIDFactory.createId());
                    } else if (c.toString().endsWith(Long.class.toString())) {
                        vo.setId((PK) new Long(new IdWorker(0, 0).nextId() + ""));
                    } else if (c.toString().endsWith(Integer.class.toString())) {
                        vo.setId((PK) new Integer(new IdWorker(0, 0).nextId() + ""));
                    } else {
                        throw new ServiceException("未知的主键类型，类型=" + c.toString());
                    }
                }
            }
            Integer no = getBaseMapper().addList(list);
            if (no > 0) {
                result.setCode(MessageCode.SUCCESS);
                result.setMsg(MessageCode.SUCCESS_TEXT);
                result.setData(no);
            } else {
                result.setCode(MessageCode.FAIL);
                result.setMsg(MessageCode.FAIL_TEXT);
                result.setData(null);
            }
        }
        return result;
    }

    /**
     * 更新
     *
     * @param entity
     * @return
     */
    @Override
    public ApiResponseResult update(T entity) throws Exception {
        ApiResponseResult result = new ApiResponseResult();
        Integer no = getBaseMapper().updateById(entity);
        if (no > 0) {
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
     * 删除
     *
     * @param id
     * @return
     */
    @Override
    public ApiResponseResult delete(String id) throws Exception {
        ApiResponseResult result = new ApiResponseResult();
        //判断
        Integer no = getBaseMapper().deleteById(id);
        if (no > 0) {
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
     * 批量删除
     *
     * @param
     * @return
     */
    @Override
    public ApiResponseResult batchDelete(String[] idArray) throws Exception {
        ApiResponseResult result = new ApiResponseResult();
        Integer no = getBaseMapper().deleteBatchIds(Arrays.asList(idArray));
        if (no > 0) {
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
     * 根据主键id查询对象
     *
     * @param id
     * @return
     */
    @Override
    public ApiResponseResult queryObject(String id) throws Exception {
        QueryWrapper<T> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("id",id).eq("del_flag",0);
        Object object=getBaseMapper().selectOne(queryWrapper);
        return createSuccessResult(object);
    }

    /**
     * 根据条件查询列表
     *
     * @param
     * @return
     */
    @Override
    public ApiResponseResult queryList(T entity) throws Exception {
        return createSuccessResult((List<T>) getBaseMapper().queryList(entity));
    }

    /**
     * 根据条件分页查询
     *
     * @param requestPageVO
     * @return
     * @throws Exception
     */
    @Override
    public ApiResponseResult queryPage(RequestPageVO<T> requestPageVO) throws Exception {
        PageHelper.startPage(requestPageVO.getCurrentPage(), requestPageVO.getPageSize(), true);
        List<T> list = getBaseMapper().queryList(requestPageVO.getEntity());
        PageInfo<T> pageInfo = new PageInfo<T>(list);
        return createSuccessResult(pageInfo);
    }


}
