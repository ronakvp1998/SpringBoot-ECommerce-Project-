package com.ronaksales.electronic.store.ElectronicStore.helper;

import com.ronaksales.electronic.store.ElectronicStore.dtos.PageableResponse;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

public class Helper {

    public static <U,V> PageableResponse<V> getPageableResponse(Page<U> page, Class<V> type){
        // U -> entity V-> DTO
        List<U> entity = page.getContent();

        // mapping
        List<V> dtoList = entity.stream()
                .map(object -> new ModelMapper().map(object,type)).collect(Collectors.toList());

        // create custom pageable response
        PageableResponse<V> response = new PageableResponse<>();
        response.setContent(dtoList);
        response.setPageNumber(page.getNumber());
        response.setPageSize(page.getSize());
        response.setTotalElements(page.getTotalElements());
        response.setTotalPages(page.getTotalPages());
        response.setLastPage(page.isLast());

        return response;
    }
}
