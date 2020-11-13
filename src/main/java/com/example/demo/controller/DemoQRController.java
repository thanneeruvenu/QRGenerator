/**
 *
 */
package com.example.demo.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.QRCodeGenerator;
import com.example.demo.QRFileExtensionType;

import java.util.Map;

/**
 * @author venut
 *
 */
@RestController
public class DemoQRController {

    private static final String QR_CODE_IMAGE_PATH = "./src/main/resources/QRCode";

	/**
	 * Example : http://localhost:8080/genrateAndDownloadQRCode/Hello/350/350
	 * Output : it will download QR file with content, extension will be depends on accept header
	 * @param codeText
	 * @param width
	 * @param height
	 * @param headers
	 * @throws Exception
	 */
    @GetMapping(value = "/genrateAndDownloadQRCode/{codeText}/{width}/{height}")
    public void download(@PathVariable("codeText") String codeText, @PathVariable("width") Integer width,
                         @PathVariable("height") Integer height, @RequestHeader HttpHeaders headers) throws Exception {
        QRFileExtensionType type = QRFileExtensionType.fetchByKey(headers.getAccept().get(0).toString());
        QRCodeGenerator.generateQRCodeImage(codeText, width, height, QR_CODE_IMAGE_PATH + '.' + type.getValue(), type.getValue());

    }

	/**
	 * Example : http://localhost:8080/genrateQRCode/Hello World/350/350
	 * Output : it will Display QR file with content, extension will be depends on accept header
	 *
	 * @param codeText
	 * @param width
	 * @param height
	 * @param headers
	 * @return
	 * @throws Exception
	 */
    @GetMapping(value = "/genrateQRCode/{codeText}/{width}/{height}", produces = {MediaType.IMAGE_JPEG_VALUE,
            MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_GIF_VALUE, "image/svg+xml"})
    public ResponseEntity<byte[]> generateQRCodeByGet(@PathVariable("codeText") String codeText,
                                                      @PathVariable("width") Integer width, @PathVariable("height") Integer height,
                                                      @RequestHeader HttpHeaders headers) throws Exception {

        return ResponseEntity.status(HttpStatus.OK)
                .body(QRCodeGenerator.getQRCodeImage(codeText, width, height, QRFileExtensionType.fetchByKey(headers.getAccept().get(0).toString()).getValue()));
    }

	/**
	 * Exapmple :  http://localhost:8080/genrateQRCode/
	 * Request body :
	 * {
	 *     "codeText" : "Hello World test !!",
	 *     "width" : 500,
	 *     "height" : 500
	 * }
	 *
	 * @param content
	 * @param headers
	 * @return
	 * @throws Exception
	 */
    @PostMapping(value = "/genrateQRCode", produces = {MediaType.IMAGE_JPEG_VALUE,
            MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_GIF_VALUE, "image/svg+xml"})
    public ResponseEntity<byte[]> generateQRCodeByPost(@RequestBody Map content,
                                                       @RequestHeader HttpHeaders headers) throws Exception {

        return ResponseEntity.status(HttpStatus.OK)
                .body(QRCodeGenerator.getQRCodeImage(
                        String.valueOf(content.get("codeText")),
                        Integer.valueOf(content.get("width").toString()),
                        Integer.valueOf(content.get("height").toString()),
                        QRFileExtensionType.fetchByKey(headers.getAccept().get(0).toString()).getValue()));
    }

}
