package com.zsx.design.pattern.structural.proxy;

import com.zsx.design.pattern.structural.proxy.dynamical.LogProxyFactory;
import com.zsx.design.pattern.structural.proxy.dynamical.TaoBaoPlatform;
import com.zsx.design.pattern.structural.proxy.statical.ISell;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DynamicalProxyTest {

    @Test
    void testSellTicket() {
        ISell sell = new TaoBaoPlatform();
        assertEquals("com.zsx.design.pattern.structural.proxy.dynamical.TaoBao", sell.getClass().getName());
        assertEquals("TaoBao", sell.sellTicket());

        ISell sell1 = (ISell)new LogProxyFactory().getProxyInstance(sell);
        assertEquals("com.sun.proxy.$Proxy9", sell1.getClass().getName());
        assertEquals("TaoBao", sell1.sellTicket());

    }
}
