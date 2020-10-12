package com.zsx.design.pattern.structural.proxy.dynamical;

import com.zsx.design.pattern.structural.proxy.statical.ISell;

public class TaoBaoPlatform implements ISell {

    @Override
    public String sellTicket() {
        return "TaoBao";
    }
}
