package com.nimo.rpc.v2.layer.protocol;

import com.nimo.rpc.v2.URL;
import com.nimo.rpc.v2.layer.config.Provider;
import com.nimo.rpc.v2.export.Exporter;

/**
 * @auther zgp
 * @desc
 * @date 2022/8/29
 */
public class Protocol {

    public <T> Exporter export(Provider<T> provider, URL serviceUrl) {
        Exporter exporter = new Exporter(provider, serviceUrl);
        exporter.init();
        return exporter;
    }
}
