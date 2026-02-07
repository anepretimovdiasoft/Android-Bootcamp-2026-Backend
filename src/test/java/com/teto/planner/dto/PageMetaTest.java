package com.teto.planner.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class PageMetaTest {

    @Test
    void computesTotalPagesAndNavigationFlags() {
        PageMeta empty = new PageMeta(0, 50, 0);
        assertEquals(0, empty.totalPages());
        assertFalse(empty.hasNext());
        assertFalse(empty.hasPrev());

        PageMeta firstOfTwo = new PageMeta(0, 50, 51);
        assertEquals(2, firstOfTwo.totalPages());
        assertTrue(firstOfTwo.hasNext());
        assertFalse(firstOfTwo.hasPrev());

        PageMeta lastOfTwo = new PageMeta(1, 50, 51);
        assertEquals(2, lastOfTwo.totalPages());
        assertFalse(lastOfTwo.hasNext());
        assertTrue(lastOfTwo.hasPrev());
    }
}

