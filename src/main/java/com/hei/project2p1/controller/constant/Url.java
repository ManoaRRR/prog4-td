package com.hei.project2p1.controller.constant;

import org.springframework.stereotype.Component;

@Component
public class Url {
    public static final String EMPLOYEES_LIST= "/employees";
    public static final String EMPLOYEES_DETAILS= "/employees/{id}/details";
    public static final String EMPLOYEES_UPDATE= "/employees/{id}/update";
    public static final String EMPLOYEES_ADD= "/employees/add";

}
