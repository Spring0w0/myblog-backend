package com.spring0w0.myblog.controller.user;

import com.spring0w0.myblog.common.domain.Message;
import com.spring0w0.myblog.pojo.vo.UserWebConfigVO;
import com.spring0w0.myblog.service.IWebConfigService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Spring0w0
 */
@RestController
@RequestMapping("/setting")
@RequiredArgsConstructor
public class UserWebConfigController {

    private final IWebConfigService webConfigService;

    @GetMapping
    public Message<List<UserWebConfigVO>> getAllWebConfig() {
        List<UserWebConfigVO> webConfigVOList = webConfigService.getAllUserWebConfig();
        return Message.success(webConfigVOList);
    }


}
