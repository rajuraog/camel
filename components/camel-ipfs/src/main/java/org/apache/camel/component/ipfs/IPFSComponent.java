/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.camel.component.ipfs;

import java.net.URI;
import java.util.Map;

import org.apache.camel.Endpoint;
import org.apache.camel.spi.annotations.Component;
import org.apache.camel.support.DefaultComponent;

@Component("ipfs")
public class IPFSComponent extends DefaultComponent {

    @Override
    protected Endpoint createEndpoint(String urispec, String remaining, Map<String, Object> parameters) throws Exception {

        IPFSConfiguration config = new IPFSConfiguration();
        IPFSEndpoint endpoint = new IPFSEndpoint(urispec, this, config);
        setProperties(endpoint, parameters);

        // Derive host:port and cmd from the give uri
        URI uri = new URI(urispec);
        String host = uri.getHost();
        int port = uri.getPort();
        String cmd = remaining;
        if (!cmd.equals(host)) {
            if (host != null) {
                config.setIpfsHost(host);
            }
            if (port > 0) {
                config.setIpfsPort(port);
            }
            int idx = cmd.indexOf('/');
            cmd = cmd.substring(idx + 1);
        }
        config.setIpfsCmd(cmd);

        return endpoint;
    }
}
