package com.fyp.adp.rule.engine.info;

import lombok.Data;

@Data
public class SourceInfo {
    /**
     * 水位，固定延迟策略，单位：ms，默认10秒
     */
    private long watermark;
}
