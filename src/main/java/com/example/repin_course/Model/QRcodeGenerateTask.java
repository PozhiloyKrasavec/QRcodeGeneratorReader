package com.example.repin_course.Model;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import javafx.concurrent.Task;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;

import java.awt.image.BufferedImage;

import java.nio.charset.StandardCharsets;
import java.util.Map;

public class QRcodeGenerateTask extends Task<Image> {
    private String data;
    private String charset;
    private Map hashMap;
    private int height;
    private int width;

    public QRcodeGenerateTask(String data, String charset, Map hashMap, int height, int width) {
        this.data = data;
        this.charset = charset;
        this.hashMap = hashMap;
        this.height = height;
        this.width = width;
    }

    @Override
    protected Image call() throws Exception {
        BitMatrix matrix = new MultiFormatWriter().encode(
                new String(data.getBytes(charset),charset), BarcodeFormat.QR_CODE,width,height);
        BufferedImage image = MatrixToImageWriter.toBufferedImage(matrix);
        return convertToFxImage(image);
    }
    public Image convertToFxImage(BufferedImage image){
        WritableImage wr = null;
        if (image != null){
            wr = new WritableImage(image.getWidth(), image.getHeight());
            PixelWriter pw = wr.getPixelWriter();
            int count = image.getHeight();
            for (int x=0;x<image.getWidth();x++){
                for (int y=0;y< image.getHeight();y++){
                    pw.setArgb(x,y, image.getRGB(x,y));
                    this.updateProgress(y,count);
                }
            }
        }
        return new ImageView(wr).getImage();
    }
}
