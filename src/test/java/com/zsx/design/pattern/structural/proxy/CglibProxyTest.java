package com.zsx.design.pattern.structural.proxy;

import com.zsx.design.pattern.structural.proxy.cglib.TaoBaoPlatform;
import com.zsx.design.pattern.structural.proxy.cglib.LogProxyFactory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CglibProxyTest {

    @Test
    void testSellTicket() {
        TaoBaoPlatform taoBao = new TaoBaoPlatform();
        assertEquals("com.zsx.design.pattern.structural.proxy.cglib.TaoBaoPlatform", taoBao.getClass().getName());
        assertEquals("TaoBao", taoBao.sellTicket());

        TaoBaoPlatform taoBao1 = (TaoBaoPlatform)new LogProxyFactory(new TaoBaoPlatform()).getProxyInstance();
        assertEquals("com.zsx.design.pattern.structural.proxy.cglib.TaoBaoPlatform$$EnhancerByCGLIB$$76b0bf53", taoBao1.getClass().getName());
        assertEquals("TaoBao", taoBao1.sellTicket());
    }
}
