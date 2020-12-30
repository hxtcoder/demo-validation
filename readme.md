validation最佳实践和实现原理

最佳实践
见本项目代码 或者 原作者 github代码

实现原理
org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor.resolveArgument
org.springframework.web.servlet.mvc.method.annotation.AbstractMessageConverterMethodArgumentResolver.validateIfApplicable
org.springframework.validation.DataBinder.validate(java.lang.Object...)
最终调  Hibernate Validator


ref ↓
公众号文章:
https://mp.weixin.qq.com/s/uboXPGgLtHBAtgEr1aXQAQ

github:
https://github.com/chentianming11/spring-validation