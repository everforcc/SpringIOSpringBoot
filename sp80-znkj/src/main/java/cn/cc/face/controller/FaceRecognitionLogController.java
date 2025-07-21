package cn.cc.face.controller;

import cn.cc.face.service.FaceRecognitionLogService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

@Controller
@RequestMapping("/face-log")
public class FaceRecognitionLogController {
    @Resource
    private FaceRecognitionLogService logService;

    @GetMapping("/list")
    public String list(Model model) {
        model.addAttribute("logs", logService.listAll());
        return "face_recognition_log_list";
    }
} 