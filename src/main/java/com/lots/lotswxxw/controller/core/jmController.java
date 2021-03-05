package com.lots.lotswxxw.controller.core;

import com.lots.lotswxxw.controller.auth.BaseAction;
import com.lots.lotswxxw.domain.vo.JsonResult;
import com.lots.lotswxxw.util.creak.EncryptManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @name: jmController
 * @author: lots
 * @date: 2021/3/5 15:22
 */

@RestController
@RequestMapping("jm")
public class jmController  extends BaseAction {
    private static String p1 = "7205a6c3883caf95b52db5b534e12ec3";
    private static String p2 = "81d7beac44a86f4337f534ec93328370";
    @GetMapping("enc")
    public String enc(String data) {
        EncryptManager em = new EncryptManager();
        em.init(p1,p2);
        return em.encrypt(data);
    }

    @GetMapping("dec")
    public String dec(String data) {
        EncryptManager em = new EncryptManager();
        em.init(p1,p2);
        return em.decrypt(data);
    }
}
