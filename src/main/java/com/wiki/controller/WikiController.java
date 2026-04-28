package com.wiki.controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import com.wiki.service.StrategyService;
import com.wiki.entity.StrategyGuide;
import com.wiki.service.CharacterService;
import com.wiki.dao.EchoDao;
import com.wiki.dao.UserDao;
import com.wiki.dao.WeaponDao;
import com.wiki.entity.CharacterInfo;
import com.wiki.entity.EchoInfo;
import com.wiki.entity.User;
import com.wiki.entity.WeaponInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.RequestParam;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 实验三核心任务：使用 Spring MVC 替代传统的 Servlet
 * RestController 会自动将返回的 Java 对象转换成 JSON 并发送给前端浏览器
 */
@RestController
@RequestMapping("/api") // 统一接口前缀
public class WikiController {

    // 💡 这里就是修复报错的关键点：需要声明这个变量
    @Autowired
    private CharacterService characterService;

    // 👇 实验七新增：注入 StrategyService
    @Autowired
    private StrategyService strategyService;

    // ==========================================
    // 实验七验收测试接口：一对一级联查询
    // ==========================================
    @GetMapping("/test/cascade")
    public List<StrategyGuide> testCascade() {
        return strategyService.testCascade();
    }

    // ==========================================
    // 实验八验收测试接口：使用 @ModelAttribute 接收请求参数
    // ==========================================
    @PostMapping("/test/modelAttribute")
    public Object testModelAttribute(@ModelAttribute StrategyGuide guide) {
        // 当你向这个接口发送 POST 请求并携带 title 和 content 时，Spring MVC 会自动把它们装进 guide 对象里
        System.out.println("【实验八测试】成功通过 @ModelAttribute 接收到攻略标题：" + guide.getTitle());

        Map<String, Object> result = new HashMap<>();
        result.put("code", 200);
        result.put("message", "实验八测试成功！接收到的标题是: " + guide.getTitle());
        return result;
    }

    // ==========================================
    // 1. 图鉴数据查询接口 (替代原来的 CharacterServlet, WeaponListServlet, EchoListServlet)
    // ==========================================

    @GetMapping("/characters")
    public List<CharacterInfo> getCharacters() {
        return characterService.getAllCharacters();
    }

    // ==========================================
    // 实验十验收测试接口：分页显示数据
    // ==========================================
    @GetMapping("/test/page")
    public PageInfo<CharacterInfo> testPage(
            @RequestParam(defaultValue = "1") int pageNum,
            @RequestParam(defaultValue = "3") int pageSize) {

        // 默认查询第 1 页，每页只显示 3 条角色数据
        return characterService.getCharactersByPage(pageNum, pageSize);
    }

    @GetMapping("/weapon/list")
    public List<WeaponInfo> getWeapons() {
        WeaponDao dao = new WeaponDao();
        return dao.getAllWeapons();
    }

    @GetMapping("/echo/list")
    public List<EchoInfo> getEchoes() {
        EchoDao dao = new EchoDao();
        return dao.getAllEchoes();
    }

    // ==========================================
    // 2. 用户登录状态与权限接口 (替代原来的 GetUserInfoServlet, LoginServlet, LogoutServlet)
    // ==========================================

    @GetMapping("/user/info")
    public Object getUserInfo(HttpSession session) {
        User user = (User) session.getAttribute("currentUser");
        if (user != null) {
            Map<String, Object> result = new HashMap<>();
            result.put("code", 200);
            result.put("data", user);
            return result;
        }
        // 如果没登录，返回字符串 "null"，兼容前端的判断逻辑
        return "null";
    }

    @PostMapping("/user/login")
    public String login(String username, String password, HttpSession session) {
        UserDao dao = new UserDao();
        User loginUser = dao.login(username, password);

        if (loginUser != null) {
            session.setAttribute("currentUser", loginUser);
            return "success";
        }
        return "fail";
    }

    @GetMapping("/user/logout")
    public String logout(HttpSession session) {
        session.invalidate(); // 清除 Session
        return "success";
    }
    // ==========================================
    // 实验十一验收测试接口：数据验证
    // ==========================================
    @PostMapping("/test/validation")
    public String testValidation(@Validated @RequestBody StrategyGuide guide) {
        // @Validated 注解会触发实体类里的 @NotBlank 规则。
        // 如果前端传来的 guide 没有 title，程序会直接抛出异常被 Spring 拦截，根本进不到这一行。
        return "数据验证通过！收到的攻略标题是：" + guide.getTitle();
    }
}