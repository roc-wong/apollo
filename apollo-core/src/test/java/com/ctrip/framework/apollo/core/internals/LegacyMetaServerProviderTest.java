package com.ctrip.framework.apollo.core.internals;

import com.ctrip.framework.apollo.core.enums.Env;
import org.junit.After;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class LegacyMetaServerProviderTest {

  @After
  public void tearDown() throws Exception {
    System.clearProperty("dev_meta");
    System.clearProperty("fat_meta");
    System.clearProperty("aliyun_pro_meta");
    System.clearProperty("szy_pro_meta");
    System.clearProperty("hwy_pro_meta");
  }

  @Test
  public void testFromPropertyFile() {
    LegacyMetaServerProvider legacyMetaServerProvider = new LegacyMetaServerProvider();
    assertEquals("http://localhost:8080", legacyMetaServerProvider.getMetaServerAddress(Env.LOCAL));
    assertEquals("http://dev:8080", legacyMetaServerProvider.getMetaServerAddress(Env.DEV));
    assertEquals(null, legacyMetaServerProvider.getMetaServerAddress(Env.PRO));
    assertEquals("http://aliyun_pro:8080", legacyMetaServerProvider.getMetaServerAddress(Env.ALIYUN_PRO));
    assertEquals("http://szy_pro:8080", legacyMetaServerProvider.getMetaServerAddress(Env.SZY_PRO));
    assertEquals("http://hwy_pro:8080", legacyMetaServerProvider.getMetaServerAddress(Env.HWY_PRO));
  }

  @Test
  public void testWithSystemProperty() throws Exception {
    String someDevMetaAddress = "someMetaAddress";
    String someFatMetaAddress = "someFatMetaAddress";
    String someAliYunMetaAddress = "someAliYunMetaAddress";
    String someSzyMetaAddress = "someSzyMetaAddress";
    String someHwyMetaAddress = "someHwyMetaAddress";
    System.setProperty("dev_meta", someDevMetaAddress);
    System.setProperty("fat_meta", someFatMetaAddress);
    System.setProperty("aliyun_pro_meta", someAliYunMetaAddress);
    System.setProperty("szy_pro_meta", someSzyMetaAddress);
    System.setProperty("hwy_pro_meta", someHwyMetaAddress);

    LegacyMetaServerProvider legacyMetaServerProvider = new LegacyMetaServerProvider();

    assertEquals(someDevMetaAddress, legacyMetaServerProvider.getMetaServerAddress(Env.DEV));
    assertEquals(someFatMetaAddress, legacyMetaServerProvider.getMetaServerAddress(Env.FAT));
    assertEquals(someAliYunMetaAddress, legacyMetaServerProvider.getMetaServerAddress(Env.ALIYUN_PRO));
    assertEquals(someSzyMetaAddress, legacyMetaServerProvider.getMetaServerAddress(Env.SZY_PRO));
    assertEquals(someHwyMetaAddress, legacyMetaServerProvider.getMetaServerAddress(Env.HWY_PRO));
  }
}
