package com.teto.planner.dto;

public record PageMeta(
        int page,
        int size,
        long total,
        int totalPages,
        boolean hasNext,
        boolean hasPrev
) {
    public PageMeta(int page, int size, long total) {
        this(page, size, total, calcTotalPages(total, size), calcHasNext(page, total, size), page > 0);
    }

    private static int calcTotalPages(long total, int size) {
        if (size <= 0) {
            return 0;
        }
        if (total <= 0) {
            return 0;
        }
        long pages = (total + size - 1) / size;
        return pages > Integer.MAX_VALUE ? Integer.MAX_VALUE : (int) pages;
    }

    private static boolean calcHasNext(int page, long total, int size) {
        int totalPages = calcTotalPages(total, size);
        return totalPages > 0 && (page + 1) < totalPages;
    }
}
