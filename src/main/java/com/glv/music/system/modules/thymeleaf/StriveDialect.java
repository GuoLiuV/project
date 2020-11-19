package com.glv.music.system.modules.thymeleaf;

import com.glv.music.system.modules.thymeleaf.processor.StriveElementTagCommonProcessor;
import com.glv.music.system.modules.thymeleaf.processor.StriveElementTagProcessor;
import org.thymeleaf.dialect.AbstractProcessorDialect;
import org.thymeleaf.processor.IProcessor;
import org.thymeleaf.standard.StandardDialect;

import java.util.HashSet;
import java.util.Set;


/**
 * @author ZHOUXIANG
 */
public class StriveDialect extends AbstractProcessorDialect {

    private static final String PREFIX = "strive";

    public StriveDialect() {
        super("Strive Dialect", PREFIX, StandardDialect.PROCESSOR_PRECEDENCE);
    }

    @Override
    public Set<IProcessor> getProcessors(String dialectPrefix) {
        final Set<IProcessor> processors = new HashSet<>();
        processors.add(new StriveElementTagProcessor(dialectPrefix));
        processors.add(new StriveElementTagCommonProcessor(dialectPrefix));
        return processors;
    }

}
