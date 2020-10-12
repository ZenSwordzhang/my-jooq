package com.zsx.design.pattern.structural.proxy.statical;

public class ThirdParty implements ISell {

    @Override
    public String sellTicket() {
        Official official = new Official();
        return String.format("ThirdParty-%s", official.sellTicket());
    }
}
