package com.spring.booking.dto.pageRequest;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@Getter
@Setter
public class PageRequestDto {

    private String searchText = "";
    
    @Min(value = 1, message = "INVALID_PAGE_NUMBER")
    private int page = 1;
    
    @Min(value = 1, message = "Filter size must be at least one!")
    @Max(value = 20, message = "Filter size must be at most 20!")
    private int size = 16;

    private Sort.Direction sort = Sort.Direction.DESC;
    
    private String sortBy = "id";  // Default sort field

    @JsonIgnore
    public Pageable getPageable() {
        return PageRequest.of(
                this.page - 1,
                this.size,
                Sort.by(sort, sortBy)
        );
    }
}