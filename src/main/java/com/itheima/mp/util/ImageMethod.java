package com.itheima.mp.util;

import lombok.SneakyThrows;

import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import javax.sql.rowset.serial.SerialBlob;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.sql.Blob;
import java.util.Base64;

public class ImageMethod {


    @SneakyThrows
    public static void changePathTest() {
        String imagePath = "D://photo//xiaoma.png";
        BufferedImage bufferedImage = ImageIO.read(new FileInputStream(imagePath));

        System.out.println(bufferedImage.getData());
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, "png", output);
        byte[] imageByte = output.toByteArray();
        System.out.println(imageByte.length / 1024);
        // System.out.println(Arrays.toString(imageByte));
    }

    @SneakyThrows
    public static String getBlobByByteArray(byte[] bytes) {
        String blobString = null;
        Blob blob = new SerialBlob(bytes);
        byte[] blobBytes = blob.getBytes(1, (int) blob.length());
        blobString = Base64.getEncoder().encodeToString(blobBytes);
        return blobString;
    }

    @SneakyThrows
    public static byte[] getByteArrayByBlob(String string) {
        byte[] blobBytes = null;

        byte[] decodedBytes = Base64.getDecoder().decode(string);
        Blob blob = new SerialBlob(decodedBytes);
        blobBytes = blob.getBytes(1, (int) blob.length());

        return blobBytes;
    }

    @SneakyThrows
    public static BufferedImage reduceImageQuality(BufferedImage originalImage, float quality) {
        // 创建一个新的 BufferedImage，根据原始图片的宽度和高度
        BufferedImage newImage = new BufferedImage(originalImage.getWidth(), originalImage.getHeight(), BufferedImage.TYPE_INT_RGB);

        // 在新的 BufferedImage 上绘制原始图片
        Graphics2D graphics = newImage.createGraphics();
        graphics.drawImage(originalImage, 0, 0, null);
        graphics.dispose();

        // 获取 ImageWriter 并设置压缩质量
        ImageWriter writer = ImageIO.getImageWritersByFormatName("jpg").next();
        ImageWriteParam param = writer.getDefaultWriteParam();
        param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
        param.setCompressionQuality(quality);

        // 将图像写入 BufferedImage
        BufferedImage compressedImage = null;
        try {
            ImageOutputStream outputStream = ImageIO.createImageOutputStream(new File("low_quality_image.jpg"));
            writer.setOutput(outputStream);
            writer.write(null, new javax.imageio.IIOImage(newImage, null, null), param);
            outputStream.close();
            compressedImage = ImageIO.read(new File("low_quality_image.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return compressedImage;
    }

    @SneakyThrows
    public static BufferedImage getBufferedFromByteArray(byte[] bytes) {
        return ImageIO.read(new ByteArrayInputStream(bytes));
    }

    @SneakyThrows
    public static byte[] getByteArrayFromBufferedImage(BufferedImage bufferedImage) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, "png", output);
        byte[] imageByte = output.toByteArray();
        return imageByte;
    }

    @SneakyThrows
    public static BufferedImage reduceImageQuality(byte[] bytes, float quality) {
        return reduceImageQuality(getBufferedFromByteArray(bytes), quality);
    }

    @SneakyThrows
    public static int getImageKB(BufferedImage bufferedImage) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, "png", output);
        byte[] imageByte = output.toByteArray();
        return imageByte.length / 1024;
    }
}
