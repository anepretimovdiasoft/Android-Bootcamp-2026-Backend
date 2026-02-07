package com.teto.planner.pagination;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.teto.planner.exception.BadRequestException;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Sort;

class PaginationTest {

    @Test
    void rejectsNegativePage() {
        assertThrows(BadRequestException.class, () -> Pagination.pageRequest(-1, 10));
    }

    @Test
    void rejectsNonPositiveSize() {
        assertThrows(BadRequestException.class, () -> Pagination.pageRequest(0, 0));
        assertThrows(BadRequestException.class, () -> Pagination.pageRequest(0, -1));
    }

    @Test
    void rejectsTooLargeSize() {
        assertThrows(BadRequestException.class, () -> Pagination.pageRequest(0, Pagination.MAX_SIZE + 1));
    }

    @Test
    void buildsPageRequestWithSort() {
        var pr = Pagination.pageRequest(2, 25, Sort.by(Sort.Order.asc("name")));
        assertEquals(2, pr.getPageNumber());
        assertEquals(25, pr.getPageSize());
        assertEquals(Sort.by(Sort.Order.asc("name")), pr.getSort());
    }
}

