package com.atguigu.crowd.mvc.config;

import com.atguigu.crowd.util.CrowdUtil;
import com.atguigu.crowd.util.ResultEntity;
import com.google.gson.Gson;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @ControllerAdvice: 表示当前类是一个基于注解的异常处理器类
 */
@ControllerAdvice
public class CrowdExceptionResolver {

    @ExceptionHandler(value = ArithmeticException.class)
    public ModelAndView resolveNullPointerException(
            ArithmeticException exception,
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        String viewName = "system-error";
        return commonExceptionResolver(viewName, exception, request, response);
    }

    /**
     * @ExceptionHandler: 将一个具体的异常类型和一个方法关联起来
     *
     * @param exception
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    @ExceptionHandler(value = NullPointerException.class)
    public ModelAndView resolveNullPointerException(
            NullPointerException exception,
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        String viewName = "system-error";
        return commonExceptionResolver(viewName, exception, request, response);
    }

    public static ModelAndView commonExceptionResolver(
            // 跳转的视图
            String viewName,
            // 实例捕获到的异常类型
            Exception exception,
            // 当前请求对象
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        // 1. 判断请求类型
        boolean judgeRequestType = CrowdUtil.judgeRequestType(request);

        // 2. 如果是 Ajax 请求
        if (judgeRequestType) {
            // 3. 创建 ResultEntity 对象
            ResultEntity<Object> resultEntity = ResultEntity.failed(exception.getMessage());

            // 4. 创建 Gson 对象
            Gson gson = new Gson();

            // 5. 将 ResultEntity 对象转换为 JSON 对象
            String json = gson.toJson(resultEntity);

            // 6. 将 JSON 字符串作为响应体返回
            response.getWriter().write(json);

            // 7. 由于上面已经通过原生的 response 对象返回了响应, 所以不提供 ModelAndView 对象
            return null;
        }

        // 8. 如果不是 Ajax 请求则创建 ModelAndView 对象
        ModelAndView modelAndView = new ModelAndView();

        // 9. 将 Exception 对象存入模型
        modelAndView.addObject("exception", exception);

        // 10. 设置对应的视图名称
        modelAndView.setViewName(viewName);

        // 11. 返回 ModelAndView 对象
        return modelAndView;
    }

}
