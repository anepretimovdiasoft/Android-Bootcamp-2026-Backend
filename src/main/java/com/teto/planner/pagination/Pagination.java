package com.teto.planner.pagination;

import com.teto.planner.exception.BadRequestException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

public final class Pagination {
    // Keep defaults aligned with controller @RequestParam defaults.
    public static final int DEFAULT_PAGE = 0;
    public static final int DEFAULT_SIZE = 50;
    public static final int MAX_SIZE = 200;

    private Pagination() {
    }

    public static PageRequest pageRequest(int page, int size) {
        return pageRequest(page, size, null);
    }

    public static PageRequest pageRequest(int page, int size, Sort sort) {
        if (page < 0) {
            throw new BadRequestException("INVALID_PAGINATION", "page must be >= 0");
        }
        if (size < 1) {
            throw new BadRequestException("INVALID_PAGINATION", "size must be >= 1");
        }
        if (size > MAX_SIZE) {
            throw new BadRequestException("INVALID_PAGINATION", "size must be <= " + MAX_SIZE);
        }
        return sort == null ? PageRequest.of(page, size) : PageRequest.of(page, size, sort);
    }
}

