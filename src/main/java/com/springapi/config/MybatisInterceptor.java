package com.springapi.config;

import com.baomidou.mybatisplus.core.parser.ISqlParser;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.tenant.TenantHandler;
import com.baomidou.mybatisplus.extension.plugins.tenant.TenantSqlParser;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.StringValue;
import net.sf.jsqlparser.expression.operators.relational.ExpressionList;
import net.sf.jsqlparser.expression.operators.relational.InExpression;
import net.sf.jsqlparser.schema.Column;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

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

                boolean multipleTenantIds = false;   // 用于判断单个租户还是多个租户
                /**
                 * where 参数 在官网介绍为   3.2.0版本后添加
                 * 用于区分多租户ID
                 */
                if(context.getTenantIdMap().get("tenantIds").size() > 1) {
                    multipleTenantIds = true;
                }

                if(multipleTenantIds) {
                    return multipleTenantIdCondition();
                }
                return new StringValue(context.getTenantIdMap().get("tenantIds").get(0));
            }

            @Override
            public String getTenantIdColumn() {
                return "tenant_id";
            }

            @Override
            public boolean doTableFilter(String tableName) {
                return false;
            }

            // 单租户的id返回
            private Expression singleTenantIdCondition() {
                return  new StringValue(context.getTenantIdMap().get("tenantIds").get(0));
            }

            // 多租户的id返回
            private Expression multipleTenantIdCondition() {
                final InExpression inExpression = new InExpression();
                inExpression.setLeftExpression(new Column(getTenantIdColumn()));
                final ExpressionList itemsList = new ExpressionList();
                // 定义一个List
                final List<Expression> inValues = new ArrayList<>();
                // 将租户ID放入List
                for(String tenantId : context.getTenantIdMap().get("tenantIds")) {
                    inValues.add(new StringValue(tenantId));
                }
                // 将List放入ExpressionList对象
                itemsList.setExpressions(inValues);
                //  将ExpressionList放入inExpression对象内
                inExpression.setRightItemsList(itemsList);
                return inExpression;
            }
        });
        sqlParsers.add(tenantSqlParser);
        paginationInterceptor.setSqlParserList(sqlParsers);
        return paginationInterceptor;
    }
}
