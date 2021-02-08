package com.spring.common.util.excel;

import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import com.alibaba.excel.metadata.Sheet;
import com.spring.common.response.ResponseData;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.groups.Default;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class EasyImportUtil<T> {

    //验证实例化
    private  Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    //校验表头
    public <T> String validateHeader(Object obj,Class<? extends BaseRowModel> clazz) throws IllegalAccessException{
        StringBuilder result = new StringBuilder();

        BeanUtils.copyProperties(obj,clazz);

        Field[] declaredFields = obj.getClass().getDeclaredFields();
        String excelHead ;
        String excelVO ;
        ExcelProperty annotation ;
        for (int i = 0; i < declaredFields.length; i++) {
            declaredFields[i].setAccessible(true);
            annotation = declaredFields[i].getAnnotation(ExcelProperty.class);
            excelHead=(String)declaredFields[i].get(obj);
            excelVO=annotation.value()[0];
            if(!excelHead.equals(excelVO)){
                result.append("导入内容格式不正确.");
                break;
            }
        }
        return result.toString();
    }

    //校验数据格式
    public <T> String validateEntity(T obj) throws NoSuchFieldException {
        StringBuilder result = new StringBuilder();
        Set<ConstraintViolation<T>> set = validator.validate(obj, Default.class);
        ExcelProperty annotation;
        if (set != null && !set.isEmpty()) {
            for (ConstraintViolation<T> cv : set) {
                Field declaredField = obj.getClass().getDeclaredField(cv.getPropertyPath().toString());
                annotation = declaredField.getAnnotation(ExcelProperty.class);
                //拼接错误信息，包含当前出错数据的标题名字+错误信息
                result.append(annotation.value()[0]+cv.getMessage()).append(";");
            }
        }
        return result.toString();
    }

    /**
     * 获取Excel对象
     * @param inputStream
     * @param clazz
     * @return
     * @throws ClassNotFoundException
     */
    public ResponseData importExcel(MultipartFile multipartFile, Class<? extends BaseRowModel> clazz) throws NoSuchFieldException, IllegalAccessException, IOException {
        ResponseData responseData = new ResponseData(200);
        //获得文件名
        String fileName = multipartFile.getOriginalFilename();
        //判断文件是否是excel文件
        if(!fileName.endsWith("xls") && !fileName.endsWith("xlsx")){
            responseData.setCode("400");
            responseData.setMsg("Excel 格式必须是 xls 或 xlsx");
            return responseData;
        }
        //2.同步到导购表
        InputStream inputStream = multipartFile.getInputStream();
        List<Object> objects = EasyExcelFactory.read(inputStream, new Sheet(1, 0, clazz));
        if (objects == null || objects.size() == 0) {
            responseData.setCode("400");
            responseData.setMsg("没有导入数据");
            return responseData;
        }
        String headError = validateHeader(objects.get(0),clazz);
        if(StringUtils.isNotEmpty(headError)){
            responseData.setCode("400");
            responseData.setMsg(headError);
            return responseData;
        }
        // 存 GuideExcelModel 实体的 集合
        List<T> listAll = new ArrayList<>();
        for (Object obj : objects) {
            listAll.add((T) obj);
        }

        List<T> guideImportInfoList = new ArrayList<>();
        boolean falseBoolean = true;
        int num = 0;
        for (T guideImport : listAll) {
            num++;
            if (falseBoolean) {
                falseBoolean = false;
                continue;
            }
            String errMsg = validateEntity(guideImport);
            if (!StringUtils.isEmpty(errMsg)) {
                responseData.setMsg("第" + num + "行:" + errMsg);
                break;
            }
            guideImportInfoList.add(guideImport);
            //去重操作
            responseData.setData(guideImportInfoList.stream().distinct().collect(Collectors.toList()));
        }
        return responseData;
    }
}
