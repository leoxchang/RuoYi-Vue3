package com.truntao.web.controller.monitor;

import com.truntao.common.core.domain.R;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.truntao.framework.web.domain.Server;

/**
 * 服务器监控
 *
 * @author truntao
 */
@RestController
@RequestMapping("/monitor/server")
public class ServerController {
    @PreAuthorize("@ss.hasPermission('monitor:server:list')")
    @GetMapping()
    public R<Server> getInfo() throws Exception {
        Server server = new Server();
        server.copyTo();
        return R.ok(server);
    }
}
