package cn.cc.usb.camera;

import org.bytedeco.javacv.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.MemoryCacheImageOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicReference;

@RestController
public class CameraController {

    // 全局帧缓存，所有用户共享
    private final AtomicReference<BufferedImage> latestFrame = new AtomicReference<>();
    private volatile boolean running = true;
    private Thread grabberThread;

    /**
     * 初始化摄像头采集线程
     */
    @PostConstruct
    public void startCamera() {
        grabberThread = new Thread(() -> {
            try (OpenCVFrameGrabber grabber = new OpenCVFrameGrabber(0)) {
                // 设置分辨率
                grabber.setImageWidth(1280);
                grabber.setImageHeight(720);
                grabber.start();
                Java2DFrameConverter converter = new Java2DFrameConverter();
                while (running) {
                    Frame frame = grabber.grab();
                    if (frame != null) {
                        BufferedImage img = converter.convert(frame);
                        if (img != null) {
                            latestFrame.set(img);
                        }
                    }
                    // ms fps
                    // 10 100
                    // 33 30
                    // 40 25
                    Thread.sleep(33); // 约30fps
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        grabberThread.setDaemon(true);
        grabberThread.start();
    }

    /**
     * 关闭摄像头采集线程
     */
    @PreDestroy
    public void stopCamera() {
        running = false;
        if (grabberThread != null) {
            try {
                grabberThread.join();
            } catch (InterruptedException ignored) {}
        }
    }

    /**
     * MJPEG流接口，支持多用户同时观看
     */
    @GetMapping(value = "/camera/stream", produces = "multipart/x-mixed-replace;boundary=frame")
    public void stream(HttpServletResponse response) {
        response.setContentType("multipart/x-mixed-replace;boundary=frame");
        try {
            OutputStream out = response.getOutputStream();
            while (running) {
                BufferedImage img = latestFrame.get();
                if (img != null) {
                    // 写入MJPEG帧，设置高质量JPEG
                    out.write(("--frame\r\nContent-Type: image/jpeg\r\n\r\n").getBytes());
                    writeJpegWithQuality(img, out, 0.9f); // 0.9为高质量
                    out.write("\r\n".getBytes());
                    out.flush();
                }
                Thread.sleep(33); // 控制帧率，约30fps
            }
        } catch (Exception e) {
            // 客户端断开连接时，通常会抛出IOException，可以忽略
            if (!(e instanceof java.io.IOException)) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 写入高质量JPEG图片
     * @param image   BufferedImage对象
     * @param out     输出流
     * @param quality JPEG质量（0.0-1.0）
     */
    private void writeJpegWithQuality(BufferedImage image, OutputStream out, float quality) throws Exception {
        Iterator<ImageWriter> writers = ImageIO.getImageWritersByFormatName("jpg");
        if (!writers.hasNext()) throw new IllegalStateException("No JPEG writers found");
        ImageWriter writer = writers.next();
        ImageWriteParam param = writer.getDefaultWriteParam();
        if (param.canWriteCompressed()) {
            param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
            param.setCompressionQuality(quality);
        }
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
             MemoryCacheImageOutputStream mcios = new MemoryCacheImageOutputStream(baos)) {
            writer.setOutput(mcios);
            writer.write(null, new IIOImage(image, null, null), param);
            writer.dispose();
            out.write(baos.toByteArray());
        }
    }
}