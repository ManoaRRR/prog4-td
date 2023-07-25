package com.hei.project2p1.utils;

import com.hei.project2p1.exception.BadRequestException;
import org.springframework.stereotype.Component;

@Component
public class PaginationUtils {
    public final static int PAGE_SIZE_MAX = 200;
    private PaginationUtils(){
    }

    public static void paginationValidator(int page, int pageSize){
        if(page<1){
            throw new BadRequestException("page must be >=1");
        }
        if(pageSize>PAGE_SIZE_MAX){
            throw new BadRequestException("page size too large, must be less than "+PAGE_SIZE_MAX);
        }
    }


    public static long getTotalPages(double totalCount, int pageSize) {
        if (totalCount==0) return 1;
        double totalPages = Math.ceil(totalCount / pageSize);
        return (long) totalPages;
    }
}
