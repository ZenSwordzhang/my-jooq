package com.zsx.service.impl;

import com.zsx.exception.NonExistingUserException;
import com.zsx.service.ExceptionService;
import org.springframework.stereotype.Service;

@Service
public class ExceptionServiceImpl implements ExceptionService {

    @Override
    public void generateRuntimeException() {
        throw new RuntimeException("ExceptionServiceImpl.generateRuntimeException()");
    }

    @Override
    public void generateNonExistingUserException() {
        throw new NonExistingUserException("ExceptionServiceImpl.generateNonExistingUserException()");
    }

}
