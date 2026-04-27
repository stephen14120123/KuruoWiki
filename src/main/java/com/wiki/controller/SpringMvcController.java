package com.wiki.controller;

import com.wiki.dao.CharacterDao;
import com.wiki.entity.CharacterInfo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 这是我们引入 Spring Boot 和 Spring MVC 之后写的第一个 Controller。
 * 相比于以前繁琐的 Servlet（继承 HttpServlet、重写 doGet/doPost、捕获异常、用 PrintWriter 刷出 JSON），
 * Spring MVC 简直是神作！
 */
@RestController // 这个注解的作用 = @Controller + @ResponseBody (方法直接返回 JSON 数据给前端)
@RequestMapping("/api/spring") // 给这个类里的所有接口前面加上统一的路径前缀
public class SpringMvcController {

    /**
     * 这是一个典型的 Spring MVC 风格的接口写法。
     * 你只要返回一个 Java 对象（比如 List 集合或者单个实体类），
     * Spring Boot 底层内置的 Jackson 就会自动把它变成完美的 JSON 格式发送给前端。
     */
    @GetMapping("/characters")
    public List<CharacterInfo> testSpringMvc() {
        // 由于我们目前还没有改造 Mybatis 和 Service 层，所以先用以前的 Dao 模拟一下数据
        CharacterDao dao = new CharacterDao();
        // 取出前三条角色数据作为测试
        List<CharacterInfo> allList = dao.getAllCharacters();
        return allList.size() > 3 ? allList.subList(0, 3) : allList;
    }

    /**
     * 简单字符串测试接口
     */
    @GetMapping("/hello")
    public String helloSpring() {
        return "Hello, 欢迎来到 Spring Boot 和 Spring MVC 的世界！";
    }
}
