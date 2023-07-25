package com.hei.project2p1.controller.mapper.utils;

import com.hei.project2p1.model.Employee;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class ConvertNullValueToView {
    public ConvertNullValueToView() {
    }

    public static String valueToView(Integer value){
        if (value==null){
            return null;
        }
        return String.valueOf(value);
    }

    public static String valueToView(LocalDate value){
        if (value==null){
            return null;
        }
        return String.valueOf(value);
    }
    public static String valueToView(Employee.SocioProfessionalCategory value){
        if (value==null){
            return null;
        }
        return String.valueOf(value);
    }

    public static String valueToView(Employee.Gender value){
        if (value==null){
            return null;
        }
        return String.valueOf(value);
    }
}
