package com.movisens.movisensgattlib;

import java.util.HashMap;
import java.util.UUID;

public class MovisensService {
    public static final UUID MOVISENS_SERVICE = UUID.fromString("f89edeb6-e4e8-928b-4cfa-ebc07fce1768");
    public static final UUID HRV_SERVICE = UUID.fromString("0bd51666-e7cb-469b-8e4d-2742f1ba77cd");
    public static final UUID ACC_SERVICE = UUID.fromString("0302c2b2-ce64-4542-b819-666d20d415bd");
    public static final UUID EDA_SERVICE = UUID.fromString("10847e7a-d43f-4b9e-b2f2-3e8546215c3c");

    private static HashMap<UUID, String> attributes = new HashMap<UUID, String>();

    static {
        attributes.put(MOVISENS_SERVICE, "Movisens Service");
        attributes.put(HRV_SERVICE, "HRV Service");
        attributes.put(ACC_SERVICE, "Acceleration Service");
        attributes.put(EDA_SERVICE, "EDA Service");
    }

    public static String lookup(UUID uuid, String defaultName) {
        String name = attributes.get(uuid);
        return name == null ? defaultName : name;
    }
}