package com.ctrip.framework.apollo.core.enums;

import com.ctrip.framework.apollo.core.utils.StringUtils;

public final class EnvUtils {

    public static Env transformEnv(String envName) {
        if (StringUtils.isBlank(envName)) {
            return Env.UNKNOWN;
        }
        switch (envName.trim().toUpperCase()) {
            case "LPT":
                return Env.LPT;
            case "FAT":
            case "FWS":
                return Env.FAT;
            case "UAT":
                return Env.UAT;
            case "PRO":
            case "PROD": //just in case
                return Env.PRO;
            case "DEV":
                return Env.DEV;
            case "LOCAL":
                return Env.LOCAL;
            case "TOOLS":
                return Env.TOOLS;
            case "ALIYUN_PRO":
                return Env.ALIYUN_PRO;
            case "SZY_PRO":
                return Env.SZY_PRO;
            case "HWY_PRO":
                return Env.HWY_PRO;
            default:
                return Env.UNKNOWN;
        }
    }
}
