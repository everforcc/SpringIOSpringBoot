package cn.cc.usb.camera;

import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.Java2DFrameConverter;
import org.bytedeco.javacv.OpenCVFrameGrabber;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.OutputStream;

@RestController
public class CameraBackController {

    @GetMapping(value = "/cameraBack01/stream", produces = "multipart/x-mixed-replace;boundary=frame")
    public void stream(HttpServletResponse response) {
        try (OpenCVFrameGrabber grabber = new OpenCVFrameGrabber(0)) {
            grabber.start();
            response.setContentType("multipart/x-mixed-replace;boundary=frame");
            OutputStream out = response.getOutputStream();
            while (true) {
                Frame frame = grabber.grab();
                if (frame == null) {
                    continue;
                }
                
                // 将帧数据转换为BufferedImage格式
                Java2DFrameConverter converter = new Java2DFrameConverter();
                BufferedImage img = converter.convert(frame);
                if (img == null) {
                    continue;
                }
                out.write(("--frame\r\nContent-Type: image/jpeg\r\n\r\n").getBytes());
                ImageIO.write(img, "jpg", out);
                out.write("\r\n".getBytes());
                out.flush();
                Thread.sleep(50); // 控制帧率
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}