package com.aki.goosinsa.util;

import org.springframework.stereotype.Component;
import org.thymeleaf.context.IExpressionContext;
import org.thymeleaf.dialect.AbstractDialect;
import org.thymeleaf.dialect.IExpressionObjectDialect;
import org.thymeleaf.expression.IExpressionObjectFactory;

import java.util.Collections;
import java.util.Set;


@Component
public class MyFormatDialect extends AbstractDialect implements IExpressionObjectDialect {

    protected MyFormatDialect() {
        super("myFormat");
    }

    @Override
    public IExpressionObjectFactory getExpressionObjectFactory() {
        return new IExpressionObjectFactory() {

            @Override
            public Set<String> getAllExpressionObjectNames() {
                return Collections.singleton("myFormat");
            }

            @Override
            public Object buildObject(IExpressionContext context, String expressionObjectName) {
                return new MyFormat();
            }

            @Override
            public boolean isCacheable(String expressionObjectName) {
                return true;
            }

        };

    }

}