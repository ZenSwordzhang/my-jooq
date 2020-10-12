package com.zsx.design.pattern.structural.proxy;

import com.zsx.design.pattern.structural.proxy.statical.ISell;
import com.zsx.design.pattern.structural.proxy.statical.ThirdParty;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class StaticalProxyTest {

    @Test
    void testSellTicket() {
        ISell sell = new ThirdParty();
        Assertions.assertEquals("ThirdParty-Official", sell.sellTicket());
    }
}
