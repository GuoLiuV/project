package com.glv.music.system.modules.drools.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.glv.music.system.modules.drools.dao.DroolsRuleMapper;
import com.glv.music.system.modules.drools.dto.DroolsRuleDto;
import com.glv.music.system.modules.drools.dto.DroolsRuleExecDto;
import com.glv.music.system.modules.drools.entity.DroolsRuleEntity;
import com.glv.music.system.modules.drools.service.DroolsRuleService;
import com.glv.music.system.modules.exception.StriveException;
import com.glv.music.system.modules.mybatisplus.utils.MyBatisPlusUtils;
import com.glv.music.system.modules.request.dto.PageRequest;
import com.glv.music.system.modules.response.dto.PageData;
import com.glv.music.system.utils.ObjectUtils;
import com.glv.music.system.utils.RandomStringUtils;
import com.glv.music.system.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.kie.api.KieBase;
import org.kie.api.io.ResourceType;
import org.kie.api.runtime.KieSession;
import org.kie.internal.builder.KnowledgeBuilder;
import org.kie.internal.builder.KnowledgeBuilderFactory;
import org.kie.internal.io.ResourceFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author ZHOUXIANG
 */
@Service
@Slf4j
public class DroolsRuleServiceImpl
        extends ServiceImpl<DroolsRuleMapper, DroolsRuleEntity>
        implements DroolsRuleService {

    @Resource
    @Lazy
    private ConcurrentHashMap<String, KieBase> kieBaseMap;

    @Override
    public void afterPropertiesSet() {
        this.loadAllRules();
    }

    @Override
    public String resolveDrlFile(MultipartFile file) {
        try (
                InputStreamReader streamReader = new InputStreamReader(
                        file.getInputStream(), StandardCharsets.UTF_8);
                BufferedReader bufferedReader = new BufferedReader(streamReader)
        ) {
            StringBuilder builder = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                builder.append(line);
            }
            return builder.toString();
        } catch (Exception e) {
            throw new StriveException(e.getMessage());
        }
    }

    @Override
    public void save(@Valid DroolsRuleDto dto) {
        // 判断规则是否有错误
        this.ruleHasErrors(dto.getRuleContent());
        DroolsRuleEntity entity = new DroolsRuleEntity();
        if (ObjectUtils.notNull(dto.getId())) {
            entity = this.getById(dto.getId());
            // 更改版本号
            entity.setVersion(entity.getVersion() + 1);
            // 更改名称或规则内容
            entity.setRuleName(dto.getRuleName())
                    .setRuleContent(dto.getRuleContent());
        } else {
            // 插入时附上默认版本并自动生成规则编号
            entity.setRuleName(dto.getRuleName())
                    .setRuleContent(dto.getRuleContent())
                    .setVersion(1).setRuleCode(
                    RandomStringUtils.randomAlphabetic(10));
        }
        this.saveOrUpdate(entity);
    }

    @Override
    public PageData<DroolsRuleEntity> list(PageRequest<DroolsRuleDto> pageRequest) {
        QueryWrapper<DroolsRuleEntity> queryWrapper = new QueryWrapper<>();
        MyBatisPlusUtils.buildQuery(queryWrapper, pageRequest);
        IPage<DroolsRuleEntity> page = pageRequest.buildMybatisPlusPage();
        IPage<DroolsRuleEntity> iPage = this.page(page, queryWrapper);
        return new PageData<>(iPage);
    }

    @Override
    public void loadAllRules() {
        List<DroolsRuleEntity> rules = this.baseMapper.listAllRules();
        rules.forEach(this::loadRule);
    }

    @Override
    public void loadRule(DroolsRuleEntity rule) {
        if (StringUtils.isNotBlank(rule.getRuleContent())) {
            KnowledgeBuilder knowledgeBuilder =
                    KnowledgeBuilderFactory.newKnowledgeBuilder();
            knowledgeBuilder.add(
                    ResourceFactory.newByteArrayResource(
                            rule.getRuleContent().getBytes(StandardCharsets.UTF_8)),
                    ResourceType.DRL);
            kieBaseMap.put(rule.getRuleCode(), knowledgeBuilder.newKieBase());
        }
    }

    @Override
    public void applyRule(Long id) {
        DroolsRuleEntity entity = this.getById(id);
        this.loadRule(entity);
    }

    @Override
    public DroolsRuleExecDto executeRule(DroolsRuleExecDto dto) {
        KieBase kieBase = kieBaseMap.get(dto.getRuleCode());
        KieSession kieSession = kieBase.newKieSession();
        kieSession.insert(dto.getRuleData());
        kieSession.fireAllRules();
        kieSession.dispose();
        return dto;
    }

    /**
     * 规则是否有错误
     *
     * @param rule 规则字符串
     */
    private void ruleHasErrors(String rule) {
        KnowledgeBuilder knowledgeBuilder =
                KnowledgeBuilderFactory.newKnowledgeBuilder();
        knowledgeBuilder.add(
                ResourceFactory.newByteArrayResource(
                        rule.getBytes(StandardCharsets.UTF_8)),
                ResourceType.DRL);
        if (knowledgeBuilder.hasErrors()) {
            throw new StriveException("规则验证失败");
        }
    }
}
