package com.example.repin_course.Model;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import javafx.concurrent.Task;

import javax.imageio.ImageIO;
import java.io.FileInputStream;
import java.util.Map;

public class QRcodeReadTaskFile extends Task<String> {
    private String path;
    private String charset;
    private Map hashMap;

    public QRcodeReadTaskFile(String path, String charset, Map hashMap) {
        this.path = path;
        this.charset = charset;
        this.hashMap = hashMap;
    }

    @Override
    protected String call() throws Exception {
        BinaryBitmap binaryBitmap =
                new BinaryBitmap(new HybridBinarizer(
                        new BufferedImageLuminanceSource(ImageIO.read(
                                new FileInputStream(path)))));
        Result result = new MultiFormatReader().decode(binaryBitmap);
        return result.getText();
    }

}
