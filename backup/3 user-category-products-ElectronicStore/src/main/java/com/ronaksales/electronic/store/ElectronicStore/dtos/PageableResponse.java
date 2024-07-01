package com.ronaksales.electronic.store.ElectronicStore.dtos;

import com.ronaksales.electronic.store.ElectronicStore.entities.User;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PageableResponse<T> {

    private List<T> content;
    private int pageNumber;
    private int pageSize;
    private long totalPages;
    private long totalElements;
    private boolean lastPage;
}
