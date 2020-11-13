package com.example.demo;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageConfig;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

/*
 * QR Generation open source API
 * 
 * Zxing will support only JPEG, JPG, PNG, GIF formats.
 * 
 * MatrixToSvgWriter will provide support of SVG file formats.
 */
public class QRCodeGenerator {

	private static final MatrixToImageConfig DEFAULT_CONFIG = new MatrixToImageConfig();

	public static void generateQRCodeImage(String text, int width, int height, String filePath, String extension)
			throws WriterException, IOException {
		QRCodeWriter qrCodeWriter = new QRCodeWriter();
		BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);

		Path path = FileSystems.getDefault().getPath(filePath);
		if (extension.toLowerCase() == QRFileExtensionType.SVG.getValue())
			MatrixToSvgWriter.writeToPath(bitMatrix, path, DEFAULT_CONFIG);
		else
			MatrixToImageWriter.writeToPath(bitMatrix, extension, path);

	}

	public static byte[] getQRCodeImage(String text, int width, int height, String extension)
			throws WriterException, IOException {
		QRCodeWriter qrCodeWriter = new QRCodeWriter();
		BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);

		ByteArrayOutputStream qrImageOutputStream = new ByteArrayOutputStream();
		if (extension.toLowerCase() == QRFileExtensionType.SVG.getValue())
			MatrixToSvgWriter.writeToStream(bitMatrix, qrImageOutputStream, DEFAULT_CONFIG);
		else
			MatrixToImageWriter.writeToStream(bitMatrix, extension, qrImageOutputStream);
		byte[] qrImageData = qrImageOutputStream.toByteArray();
		return qrImageData;
	}

}
