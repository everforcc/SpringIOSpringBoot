package cn.cc.face.controller;

import cn.cc.face.dao.FaceImage;
import cn.cc.face.service.FaceImageService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping("/face-image")
public class FaceImageController {
    @Resource
    private FaceImageService faceImageService;

    @GetMapping("/list")
    public String list(Model model) {
        List<FaceImage> images = faceImageService.listAll();
        model.addAttribute("images", images);
        return "face_image_list";
    }

    @PostMapping("/update")
    @ResponseBody
    public String updatePersonName(@RequestParam Integer id, @RequestParam String personName) {
        faceImageService.updatePersonName(id, personName);
        return "success";
    }
} 