package com.atguigu.crowd.mvc.config;

import com.atguigu.crowd.constant.CrowdConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
// 启用全局方法权限控制功能
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;
//    @Bean
//    public PasswordEncoder getBCryptPasswordEncoder() {
//        return new BCryptPasswordEncoder();
//    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/admin/get/page.html").hasRole("经理")
                // 无条件访问
                .antMatchers("/bootstrap/**").permitAll()
                .antMatchers("/crowd/**").permitAll()
                .antMatchers("/css/**").permitAll()
                .antMatchers("/fonts/**").permitAll()
                .antMatchers("/img/**").permitAll()
                .antMatchers("/jquery/**").permitAll()
                .antMatchers("/layer/**").permitAll()
                .antMatchers("/script/**").permitAll()
                .antMatchers("/WEB-INF/**").permitAll()
                .antMatchers("/ztree/**").permitAll()
                // 其他请求需要认证
                .anyRequest()
                .authenticated()
                .and()
                .exceptionHandling()
                .accessDeniedHandler((request, response, accessDeniedException) -> {
                    request.setAttribute("exception", new Exception(CrowdConstant.MESSAGE_ACCESS_DENIED));
                    request.getRequestDispatcher("/WEB-INF/system-error.jsp").forward(request, response);
                })
                .and()
                .formLogin()
                // 指定登录页面
                .loginPage("/admin/to/login/page.html")
                .permitAll()
                // 指定登录成功后跳转的地址
                .defaultSuccessUrl("/admin/to/main/page.html")
                // 指定表单登录请求
                .loginProcessingUrl("/security/do/login.html")
                // 账号的请求参数名称
                .usernameParameter("loginAcct")
                .passwordParameter("userPswd")
                .and()
                // 开启退出登录功能
                .logout()
                .logoutUrl("/security/do/logout.html")
                .logoutSuccessUrl("/admin/to/login/page.html")
                .and().csrf().disable()
        ;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // 临时使用内存版登录的模式测试
//        auth.inMemoryAuthentication().withUser("tom").password("123123").roles("ADMIN");

        // 正式功能中使用基于数据库的认证, 自动使用
//        auth.userDetailsService(userDetailsService).passwordEncoder(getBCryptPasswordEncoder());
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }

}
