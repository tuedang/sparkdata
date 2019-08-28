package com.tue.company;

import com.tue.company.model.Company;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

public class PageableCompany extends PageImpl<Company> {
    public PageableCompany(List<Company> content, Pageable pageable, long total) {
        super(content, pageable, total);
    }

    private String fetchRange() {
        int from = getPageable().getPageNumber() * getPageable().getPageSize();
        int to = from + getNumberOfElements();
        return String.format("%s-%s", from, to);
    }

    public String toString() {
        return String.format("pageNumber=%s/%s. fetched=[%s] (pagesize=%s, total=%s)", getPageable().getPageNumber(), getTotalPages(),
                fetchRange(),
                getNumberOfElements(), getTotalElements());
    }
}
