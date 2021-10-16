package com.aseproject.util;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;

/**
 * This class provides methods for cutting image into small pieces and converting image to base64 format.
 *
 * @author Yihao Yang
 */
public class MapTool
{
    private final static int widthPerBlock = 30;
    private final static int heightPerBlock = 30;

    /**
     * This method splits the image into small blocks.
     * @param image The image waiting for cutting
     * @return A two-dimensional array of the split image. Every element in the array is a small part of the whole image
     */
    public static BufferedImage[][] cutImage(BufferedImage image)
    {
        int imageWidth = image.getWidth();
        int imageHeight = image.getHeight();
        int totalRow = imageHeight / heightPerBlock;
        int totalCol = imageWidth / widthPerBlock;
        BufferedImage[][] images = new BufferedImage[totalRow][totalCol];

        for (int row = 0; row < imageHeight / heightPerBlock; row++)
        {
            for (int col = 0; col < imageWidth / widthPerBlock; col++)
            {
                images[row][col] = image.getSubimage(col * widthPerBlock, row * heightPerBlock, widthPerBlock,
                        heightPerBlock);
            }
        }
        return images;
    }

    public static String imageToBase64(BufferedImage image, ByteArrayOutputStream baos) throws IOException
    {
        ImageIO.write(image, "jpg", baos);
        return Base64.getEncoder().encodeToString(baos.toByteArray());
    }
}
