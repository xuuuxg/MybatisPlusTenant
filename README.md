## MybatisPlus多租户

> [MybatisPlus 官方API]( https://mp.baomidou.com/guide/tenant.html) 

### 多租户种类

1. 独立数据库
2. 共享数据库，独立Scama
3. 共享数据库，共享Scama（MybatisPlus实现）

### 多租户实现

> 我的思路是让多租户的拦截器对系统是没有侵入的
>
> 所以采用了<u>监听器</u>的的方式来获取<u>前端传来的租户ID</u>
>
> 当然也可以采用将租户ID存在session内或者cookie内

拦截器代码

```
@WebListener
public class CustomListener implements ServletRequestListener {

    @Autowired
    private CustomContext customContext;

    public void requestDestroyed(ServletRequestEvent sre) {

    }

	// 获取前端传来的 租户ID集合，我这里用的是post请求
	// 请求结构： {id: "1", tenantId: [1,2,3]}  当然租户ID可以为字符串也可以为数字
    public void requestInitialized(ServletRequestEvent sre) {
        StringBuffer data = new StringBuffer();
        String line = null;
        BufferedReader reader = null;
        try {

            reader = sre.getServletRequest().getReader();
            while (null != (line = reader.readLine())){
             data.append(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 将传来的参数转为Json
        JSONObject jsonObject = JSONObject.parseObject(String.valueOf(data));
        
        Map<String, List<String>> map = new HashMap<>();
        List<String> tenantIds = new ArrayList<>();
        
        // 将jsonObject转为list
		List<String> list = JSONArray.parseArray(jsonObject.get("tenantId").toString())
        for(Object tenantId : list){
            tenantIds.add(tenantId.toString());
        }
        map.put("tenantIds",tenantIds);
        customContext.setTenantIdMap(map);
    }

}


```

CustomContext为自定义的存储数据的上下文类

```
@Component
public class CustomContext {

    private Map<String, List<String>> tenantIdMap = new HashMap<>();

    public Map<String, List<String>> getTenantIdMap() {
        return tenantIdMap;
    }

    public void setTenantIdMap(Map<String, List<String>> tenantIdMap) {
        this.tenantIdMap = tenantIdMap;
    }
}
```

定义MybatisPlus拦截器

```
@Component
public class MybatisInterceptor {

    @Autowired
    CustomContext context;

    @Bean
    public PaginationInterceptor paginationInterceptor() {
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
        List<ISqlParser> sqlParsers = new ArrayList<>();
        TenantSqlParser tenantSqlParser = new TenantSqlParser();
        tenantSqlParser.setTenantHandler(new TenantHandler() {

            @Override
            public Expression getTenantId(boolean where) {
                /**
                 * where 参数 在官网介绍为   3.2.0版本后添加
                 * 用于区分多租户ID
                 */
                final boolean multipleTenantIds = false;   // 用于判断单个租户还是多个租户
                if(where && multipleTenantIds) {
                    return multipleTenantIdCondition();
                }
                // 单个租户返回的租户ID
                return new StringValue(context.getTenantIdMap().get("tenantIds").get(0));
            }

            @Override
            public String getTenantIdColumn() {
            	// 告诉MybatisPlus数据库内哪个字段为租户ID
                return "tenant_id";
            }

            @Override
            public boolean doTableFilter(String tableName) {
            	// 是否做表过滤，因为业务逻辑不同，所以不是所有表都需要添加为租户ID
                return false;
            }

            // 单租户的id返回
            private Expression singleTenantIdCondition() {
                return  new StringValue(context.getTenantIdMap().get("tenantId").get(0));
            }

            // 多租户的id返回
            private Expression multipleTenantIdCondition() {
                final InExpression inExpression = new InExpression();
                inExpression.setLeftExpression(new Column(getTenantIdColumn()));
                final ExpressionList itemsList = new ExpressionList();
                // 定义一个List
                final List<Expression> inValues = new ArrayList<>();
                // 将租户ID放入List
                for(String tenantId : context.getTenantIdMap().get("tenantId")) {
                    inValues.add(new StringValue(tenantId));
                }
                // 将List放入ExpressionList对象
                itemsList.setExpressions(inValues);
                //  将ExpressionList放入inExpression对象内
                inExpression.setRightItemsList(itemsList);
                return inExpression;
            }
        });
        
        // 将上述TenantSqlParser内的拦截器放入sqlParser内
        sqlParsers.add(tenantSqlParser);
        
        // 将sqlParser放入paginationInterceptor拦截器内
        paginationInterceptor.setSqlParserList(sqlParsers);
        
        return paginationInterceptor;
    }
}
```

<u>忽略Mapper与Service</u>

Controller

```
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserService userService;

    @RequestMapping("/user")
    // 在这个方法内可以看出来，只需要传你所需的查询信息即可
    public ResponseEntity getUser(Integer id){
        return ResponseEntity.ok().body(userService.findUsersById(id));
    }
}
```

请求

![请求](https://xuuuxgblog.oss-cn-hangzhou.aliyuncs.com/多租户3.png)

SQL返回

![请求](https://xuuuxgblog.oss-cn-hangzhou.aliyuncs.com/多租户1.png)

