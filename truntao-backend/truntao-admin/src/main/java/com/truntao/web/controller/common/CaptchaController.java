package com.truntao.web.controller.common;

import cn.hutool.core.codec.Base64;
import com.google.code.kaptcha.Producer;
import com.truntao.common.config.TruntaoConfig;
import com.truntao.common.constant.CacheConstants;
import com.truntao.common.constant.Constants;
import com.truntao.common.core.domain.R;
import com.truntao.common.core.redis.RedisCache;
import com.truntao.common.utils.uuid.IdUtils;
import com.truntao.system.domain.dto.CaptchaDTO;
import com.truntao.system.service.ISysConfigService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.util.FastByteArrayOutputStream;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * 验证码操作处理
 *
 * @author truntao
 */
@RestController
public class CaptchaController {
    @Resource(name = "captchaProducer")
    private Producer captchaProducer;

    @Resource(name = "captchaProducerMath")
    private Producer captchaProducerMath;

    @Resource
    private RedisCache redisCache;

    @Resource
    private ISysConfigService configService;

    /**
     * 生成验证码
     */
    @GetMapping("/captchaImage")
    public R<CaptchaDTO> getCode(HttpServletResponse response) {
        CaptchaDTO captchaDTO = new CaptchaDTO();
        boolean captchaEnabled = configService.selectCaptchaEnabled();
        captchaDTO.setCaptchaEnabled(captchaEnabled);
        if (!captchaEnabled) {
            return R.ok(captchaDTO);
        }

        // 保存验证码信息
        String uuid = IdUtils.simpleUUID();
        String verifyKey = CacheConstants.CAPTCHA_CODE_KEY + uuid;

        String capStr;
        String code = null;
        BufferedImage image = null;

        // 生成验证码
        String captchaType = TruntaoConfig.getCaptchaType();
        if ("math".equals(captchaType)) {
            String capText = captchaProducerMath.createText();
            capStr = capText.substring(0, capText.lastIndexOf("@"));
            code = capText.substring(capText.lastIndexOf("@") + 1);
            image = captchaProducerMath.createImage(capStr);
        } else if ("char".equals(captchaType)) {
            capStr = code = captchaProducer.createText();
            image = captchaProducer.createImage(capStr);
        }

        redisCache.setCacheObject(verifyKey, code, Constants.CAPTCHA_EXPIRATION, TimeUnit.MINUTES);
        // 转换流信息写出
        FastByteArrayOutputStream os = new FastByteArrayOutputStream();
        try {
            assert image != null;
            ImageIO.write(image, "jpg", os);
        } catch (IOException e) {
            return R.fail(e.getMessage());
        }

        captchaDTO.setUuid(uuid);
        captchaDTO.setImg(Base64.encode(os.toByteArray()));
        return R.ok(captchaDTO);
    }
}
