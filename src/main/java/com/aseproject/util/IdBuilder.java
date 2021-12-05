package com.aseproject.util;

import java.util.UUID;

/**
 * An util class for generating UUID.
 * @author Yihao Yang
 */
public class IdBuilder
{
    /**
     * Generating UUID in a specific format.
     * @return 24 bits UUID
     */
    public static String generateId()
    {
        String s = UUID.randomUUID().toString();
        return s.substring(0,8)+s.substring(9,13)+s.substring(14,18)+s.substring(19,23)+s.substring(24);
    }
}
