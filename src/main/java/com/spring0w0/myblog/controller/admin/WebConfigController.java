package com.spring0w0.myblog.controller.admin;

import com.spring0w0.myblog.common.domain.Message;
import com.spring0w0.myblog.pojo.dto.WebConfigDTO;
import com.spring0w0.myblog.pojo.vo.WebConfigVO;
import com.spring0w0.myblog.service.IWebConfigService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Spring0w0
 */
@RestController
@RequestMapping("admin/setting")
@RequiredArgsConstructor
public class WebConfigController {

    private final IWebConfigService webConfigService;

    @GetMapping("/all")
    public Message<List<WebConfigVO>> getAllWebConfig() {
        List<WebConfigVO> webConfigVOList = webConfigService.getAllWebConfig();
        return Message.success(webConfigVOList);
    }

    @PutMapping("/batch")
    public Message<Object> updateBatch(
            @RequestBody List<WebConfigDTO> dtoList
    ) {
        boolean result = webConfigService.updateBatch(dtoList);
        return result ? Message.success("更新成功") : Message.error("更新失败"  );
    }

}
