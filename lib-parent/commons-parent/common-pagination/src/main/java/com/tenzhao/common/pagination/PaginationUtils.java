package com.tenzhao.common.pagination;

import java.util.List;
import java.util.Map;

public final class PaginationUtils {
    public static <T> Pagination<T> newPagination(int pageSize, int pageNumber, int totalNumberOfElements, List<T> elements) {
        return new DefaultPagination<T>(pageSize, pageNumber, totalNumberOfElements, elements);
    }

    public static <T> Pagination<T> newPagination(int pageSize, int pageNumber, int totalNumberOfElements, List<T> elements, Map<String, Object> model) {
        return new DefaultPagination<T>(pageSize, pageNumber, totalNumberOfElements, elements, model);
    }

    private PaginationUtils() {
    }
}
