package com.ctrip.framework.apollo.core.enums;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class EnvUtilsTest {

  @Test
  public void testTransformEnv() throws Exception {
    assertEquals(Env.DEV, EnvUtils.transformEnv(Env.DEV.name()));
    assertEquals(Env.FAT, EnvUtils.transformEnv(Env.FAT.name().toLowerCase()));
    assertEquals(Env.UAT, EnvUtils.transformEnv(" " + Env.UAT.name().toUpperCase() + ""));
    assertEquals(Env.UNKNOWN, EnvUtils.transformEnv("someInvalidEnv"));
    assertEquals(Env.ALIYUN_PRO, EnvUtils.transformEnv("ALIYUN_PRO"));
    assertEquals(Env.SZY_PRO, EnvUtils.transformEnv("SZY_PRO"));
    assertEquals(Env.HWY_PRO, EnvUtils.transformEnv("HWY_PRO"));
  }

  @Test
  public void testFromString() throws Exception {
    assertEquals(Env.DEV, Env.fromString(Env.DEV.name()));
    assertEquals(Env.FAT, Env.fromString(Env.FAT.name().toLowerCase()));
    assertEquals(Env.UAT, Env.fromString(" " + Env.UAT.name().toUpperCase() + ""));
    assertEquals(Env.ALIYUN_PRO, Env.fromString(" " + Env.ALIYUN_PRO.name().toUpperCase() + ""));
    assertEquals(Env.SZY_PRO, Env.fromString(" " + Env.SZY_PRO.name().toUpperCase() + ""));
    assertEquals(Env.HWY_PRO, Env.fromString(" " + Env.HWY_PRO.name().toUpperCase() + ""));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testFromInvalidString() throws Exception {
    Env.fromString("someInvalidEnv");
  }
}
