package com.mk.space.cargoship.spring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.interceptor.BeanFactoryTransactionAttributeSourceAdvisor;
import org.springframework.transaction.interceptor.NameMatchTransactionAttributeSource;
import org.springframework.transaction.interceptor.RuleBasedTransactionAttribute;
import org.springframework.transaction.interceptor.TransactionInterceptor;

import com.mk.space.cargoship.core.annotations.Behavior;

@Configuration
@EnableJpaRepositories(basePackages = "com.mk.space.cargoship.adapters.sec.jpa",
        considerNestedRepositories = true)
public class JpaConfiguration {

  @Bean
  BeanFactoryTransactionAttributeSourceAdvisor transactionAdvisor(
          TransactionManager transactionManager) {

    RuleBasedTransactionAttribute readOnlyAttribute = new RuleBasedTransactionAttribute();
    readOnlyAttribute.setReadOnly(true);

    NameMatchTransactionAttributeSource source = new NameMatchTransactionAttributeSource();
    source.addTransactionalMethod("get*", readOnlyAttribute);
    source.addTransactionalMethod("find*", readOnlyAttribute);
    source.addTransactionalMethod("is*", readOnlyAttribute);
    source.addTransactionalMethod("load*", readOnlyAttribute);
    source.addTransactionalMethod("*", new RuleBasedTransactionAttribute());

    BeanFactoryTransactionAttributeSourceAdvisor advisor =
            new BeanFactoryTransactionAttributeSourceAdvisor();
    advisor.setTransactionAttributeSource(source);
    advisor.setAdvice(new TransactionInterceptor(transactionManager, source));
    advisor.setClassFilter(clazz -> AnnotationUtils.findAnnotation(clazz, Behavior.class) != null);
    return advisor;
  }

}
