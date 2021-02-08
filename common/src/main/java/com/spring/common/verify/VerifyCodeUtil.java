package com.spring.common.verify;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.spring.base.vo.VerifyCodeVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;


/**
 * @author 作者：赵进华
 * @version 创建时间：2019年4月24日 上午10:57:21
 * @Desc类说明:验证码工具类
 */
@Component
public class VerifyCodeUtil {
	@Autowired
	private DefaultKaptcha producer;

	/**
	 * 生成Base64验证码
	 * 
	 * @return
	 * @throws IOException
	 * @author 作者：赵进华
	 * @version 创建时间：2019年4月24日 上午11:27:06
	 */
	public VerifyCodeVo createVerify() throws IOException {
		VerifyCodeVo code=new VerifyCodeVo();
		 // 生成文字验证码
        String text = producer.createText();
        // 生成图片验证码
        ByteArrayOutputStream outputStream = null;
        BufferedImage image = producer.createImage(text);
        outputStream = new ByteArrayOutputStream();
        ImageIO.write(image, "jpg", outputStream);
		String base64Code="data:img/jpg;base64," +Base64.getEncoder().encodeToString(outputStream.toByteArray());
		code.setVerifyCode(text);
		code.setBase64String(base64Code);
		return code;
	}

	/**
	 * 生成图片验证码
	 * @param httpServletRequest
	 * @param httpServletResponse
	 * @throws Exception
	 * @author 作者：赵进华 
	 * @version 创建时间：2019年4月24日 下午1:47:42
	 */
	public void createImageVerify(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
			throws Exception {
		byte[] captchaChallengeAsJpeg = null;
		ByteArrayOutputStream jpegOutputStream = new ByteArrayOutputStream();
		try {
			// 生产验证码字符串
			String createText = producer.createText();

			// 使用生产的验证码字符串返回一个BufferedImage对象并转为byte写入到byte数组中
			BufferedImage challenge = producer.createImage(createText);
			ImageIO.write(challenge, "jpg", jpegOutputStream);
		} catch (IllegalArgumentException e) {
			httpServletResponse.sendError(HttpServletResponse.SC_NOT_FOUND);
			return;
		}

		// 定义response输出类型为image/jpeg类型，使用response输出流输出图片的byte数组
		captchaChallengeAsJpeg = jpegOutputStream.toByteArray();
		httpServletResponse.setHeader("Cache-Control", "no-store");
		httpServletResponse.setHeader("Pragma", "no-cache");
		httpServletResponse.setDateHeader("Expires", 0);
		httpServletResponse.setContentType("image/jpeg");
		ServletOutputStream responseOutputStream = httpServletResponse.getOutputStream();
		responseOutputStream.write(captchaChallengeAsJpeg);
		responseOutputStream.flush();
		responseOutputStream.close();
	}

}
