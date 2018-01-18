package com.lark.middleware.web.framework.exception;

import com.alibaba.fastjson.JSONObject;

import com.lark.middleware.model.BusinessException;
import com.lark.middleware.model.dto.Response;
import com.lark.middleware.model.enums.CommonAttachmentEnum;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.UnauthenticatedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * 公共异常处理,目前只做日志记录,后续通过队列等形式将异常发送出去
 */
public class CommonExceptionResolver implements HandlerExceptionResolver {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    protected String errorPage;


    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        logger.error("处理异常path=" + request.getRequestURI() + ", parms=" + request.getParameterMap(), ex);

        if (null != ((HandlerMethod) handler).getMethod().getDeclaredAnnotation(ResponseBody.class)) {
            Response rs = new Response();
            if (ex instanceof BusinessException) {
                rs.attachment(((BusinessException) ex).getAttachment());
                if (StringUtils.isNotBlank(((BusinessException) ex).getDesc())) {
                    rs.setDesc(((BusinessException) ex).getDesc());
                }

            } else if(ex instanceof DuplicateKeyException){  // 数据重复
                rs.attachment(CommonAttachmentEnum.DATA_DUMPLICATE.getAttachment());
            } else if(ex instanceof UnauthenticatedException){ // 未登陆
                rs.attachment(CommonAttachmentEnum.PERMISSION_DENIED.getAttachment());
            }
            else {
                rs.attachment(CommonAttachmentEnum.SYSETM_ERROR.getAttachment());
            }

            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");
            try (PrintWriter writer = response.getWriter()) {
                writer.write(JSONObject.toJSONString(rs));
            } catch (Exception e) {
                logger.error("返回流异常", e);
            }
            return new ModelAndView();
        }

        return new ModelAndView(errorPage);
    }

    public String getErrorPage() {
        return errorPage;
    }

    public void setErrorPage(String errorPage) {
        this.errorPage = errorPage;
    }
}
