package com.yance.config.exception;

import com.yance.config.annos.ConditionalOnExceptionNotice;
import com.yance.config.notice.NoticeTextResolverCustomer;
import com.yance.properties.notice.PrometheusNoticeProperties;
import com.yance.text.NoticeTextResolverProvider;
import com.yance.text.markdown.ExceptionNoticeMarkdownMessageResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

/**
 * @author yance
 * @date 2020/11/04
 * @description 钉钉异常信息配置
 */
@Configuration
@ConditionalOnExceptionNotice
public class ExceptionNoticeDingdingSendingConfig implements NoticeTextResolverCustomer {

    @Autowired
    private PrometheusNoticeProperties prometheusNoticeProperties;

//	private final Log logger = LogFactory.getLog(ExceptionNoticeDingdingSendingConfig.class);

    @Override
    public void custom(NoticeTextResolverProvider resolverProvider) {
        switch (prometheusNoticeProperties.getDingdingTextType()) {
            case MARKDOWN:
                resolverProvider.add(new ExceptionNoticeMarkdownMessageResolver());
                break;
            default:
                break;
        }
    }

    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE;
    }

}
