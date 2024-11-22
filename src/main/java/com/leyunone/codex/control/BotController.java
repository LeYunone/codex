package com.leyunone.codex.control;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.leyunone.codex.model.DataResponse;
import com.leyunone.codex.model.dto.AlarmBotDTO;
import com.leyunone.codex.model.query.BotQuery;
import com.leyunone.codex.model.vo.AlarmBotVO;
import com.leyunone.codex.service.BotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * :)
 *
 * @Author LeYunone
 * @Date 2024/4/10 10:53
 */
@RestController
@RequestMapping("/bot")
public class BotController {

    @Autowired
    private BotService botService;

    @GetMapping("/getAlarm")
    public DataResponse<Page<AlarmBotVO>> getBotPage(BotQuery query) {
        Page<AlarmBotVO> alarmBots = botService.botPage(query);
        return DataResponse.of(alarmBots);
    }

    @PostMapping("/saveBot")
    public DataResponse<?> saveBot(@RequestBody AlarmBotDTO alarmBotDTO) {
        botService.saveBot(alarmBotDTO);
        return DataResponse.of();
    }

    @PostMapping("/chanceStatus")
    public DataResponse<?> chanceStatus(@RequestBody AlarmBotDTO alarmBotDTO) {
        botService.chanceStatus(alarmBotDTO.getId(), alarmBotDTO.isStatus());
        return DataResponse.of();
    }

    @PostMapping("/delete")
    public DataResponse<?> deleteBot(@RequestBody AlarmBotDTO alarmBotDTO) {
        botService.deleteBot(alarmBotDTO.getId());
        return DataResponse.of();
    }
}
