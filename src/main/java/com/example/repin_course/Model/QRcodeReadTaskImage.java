package com.example.repin_course.Model;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import javafx.concurrent.Task;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

import java.awt.image.BufferedImage;
import java.util.Map;

public class QRcodeReadTaskImage extends Task<String>{
    private Image image;
    private String charset;
    private Map hashMap;

    public QRcodeReadTaskImage(Image image, String charset, Map hashMap) {
        this.image = image;
        this.charset = charset;
        this.hashMap = hashMap;
    }

    @Override
    protected String call() throws Exception {
        BinaryBitmap binaryBitmap =
                new BinaryBitmap(new HybridBinarizer(
                        new BufferedImageLuminanceSource(SwingFXUtils.fromFXImage(image,null))));
        Result result = new MultiFormatReader().decode(binaryBitmap);
        return result.getText();
    }
}
