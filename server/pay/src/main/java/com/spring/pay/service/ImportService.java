package com.spring.pay.service;

import com.spring.common.exception.ServiceException;

import java.io.InputStream;
import java.util.Map;

public interface ImportService {

    Map<String, Object>  readToBeanList(InputStream inputStream) throws ServiceException;

}
