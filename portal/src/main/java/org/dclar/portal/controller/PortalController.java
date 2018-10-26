package org.dclar.portal.controller;

import org.dclar.portal.toolkit.SessionHolder;
import org.dclar.portal.vo.HomeVo;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * Description:
 *
 * @author dclar
 */
@RestController
public class PortalController {


    @GetMapping("/home")
    public ResponseEntity<HomeVo> home() {

        return ResponseEntity.ok(new HomeVo());
    }

    @GetMapping("/home/session/put/{key}/{value}")
    public ResponseEntity<String> put(
            @PathVariable("key") String key,
            @PathVariable("value") String value) {
        SessionHolder.put(key, value);
        return ResponseEntity.ok("success");
    }

    @GetMapping("/home/session/{key}")
    public ResponseEntity<String> get(
            @PathVariable("key") String key) {
        String value = String.valueOf(SessionHolder.get(key));
        return ResponseEntity.ok(value);
    }


}
