package com.glv.music.system.modules.drools.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.glv.music.system.modules.drools.dto.DroolsRuleDto;
import com.glv.music.system.modules.drools.dto.DroolsRuleExecDto;
import com.glv.music.system.modules.drools.entity.DroolsRuleEntity;
import com.glv.music.system.modules.request.dto.PageRequest;
import com.glv.music.system.modules.response.dto.PageData;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

/**
 * @author ZHOUXIANG
 */
@Validated
public interface DroolsRuleService extends IService<DroolsRuleEntity>, InitializingBean {

    /**
     * 上传的规则文件
     *
     * @param file 文件
     * @return 规则内容
     */
    String resolveDrlFile(MultipartFile file);

    /**
     * 保存规则定义
     *
     * @param dto 规则定义
     */
    void save(@Valid DroolsRuleDto dto);

    /**
     * 分页请求规则
     *
     * @param pageRequest 条件
     * @return 结果
     */
    PageData<DroolsRuleEntity> list(PageRequest<DroolsRuleDto> pageRequest);

    /**
     * 获取所有规则，忽略租户
     */
    void loadAllRules();

    /**
     * 将数据库规则定义加载中内存
     *
     * @param rule 规则定义
     */
    void loadRule(DroolsRuleEntity rule);

    /**
     * 应用规则到内存，
     * 否则要等到下一次系统启动时载入
     *
     * @param id 规则ID
     */
    void applyRule(Long id);

    /**
     * 执行规则
     *
     * @param dto 规则数据
     * @return fact数据
     */
    DroolsRuleExecDto executeRule(DroolsRuleExecDto dto);
}
