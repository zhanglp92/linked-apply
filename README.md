## linked-apply
---

插拔式框架, 基于SpringBoot实现

## 使用方式

### maven依赖

```text
        <dependency>
            <groupId>com.github.zhanglp92</groupId>
            <artifactId>linked-apply</artifactId>
            <version>1.0-SNAPSHOT</version>
        </dependency>
```

### 代码依赖方式

1. 自定义框架配置;(参考: com.github.zhanglp92.launcher.DefaultApplyConfig)

```java
@Component
@Log4j2
public class DefaultApplyConfig extends ApplyConfig implements ApplyMonitorConfigurable {

    @Override
    public void setApplyHandlerMonitor(HandlerMonitorAspectConfigurable configurable) {
        configurable.setApplyHandlerMonitor(new LogHandlerMonitor());
    }

    @Override
    public @NotNull String[] getDefaultLinkedHandlerNameList() {
        return new String[]{"sampleHandler"};
    }
}
```

2. 单次运行;(参考: com.github.zhanglp92.controllers.SampleController)
```java
@Controller
@Log4j2
public class SampleController {

    @Resource(name = "defaultApplyConfig")
    private ApplyExecute applyExecute;

    @ResponseBody
    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public String hello(@RequestParam String name) throws Throwable {
        applyExecute.execute(new Context(), new Compose());
        return "hello " + name;
    }
}
```

3. 自定义出入参(Context, Compose)