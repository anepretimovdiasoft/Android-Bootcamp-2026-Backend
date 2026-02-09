package ru.sicampus.bootcamp2026.util;

import java.util.List;
import java.util.Random;

public final class PastelColorGenerator {

    private static final List<String> PASTEL_COLORS = List.of(
            "#E9D5FF", "#DDD6FE", "#C4B5FD", "#F5D0FE", "#F3E8FF",
            "#DBEAFE", "#BFDBFE", "#BAE6FD", "#A5F3FC", "#E0F2FE",
            "#D1FAE5", "#A7F3D0", "#BBF7D0", "#DCFCE7", "#D4F4DD",
            "#FEF3C7", "#FEF9C3", "#FFFBEB", "#FDF2B3", "#FEF08A",
            "#FEE2E2", "#FECACA", "#FED7AA", "#FFE4E6", "#FAE8FF"
    );

    private static final Random random = new Random();

    private PastelColorGenerator() {}

    public static String randomHex() {
        return PASTEL_COLORS.get(random.nextInt(PASTEL_COLORS.size()));
    }
}