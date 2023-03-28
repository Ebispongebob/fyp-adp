package com.fyp.adp.common.watermark;

import org.apache.flink.api.common.eventtime.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 在一定时间内(alignmentIntervalTimeMs)没有事件产生，则将水位时间对齐为max(事件时间,当前时间-2*水位延迟时间)
 * 注意:如果需要将水位设置为空闲，空闲间隔时间一定要大于对齐时间
 */
public abstract class AbstractWatermarkAlignStrategy<T> implements WatermarkStrategy<T> {

    private Logger logger = LoggerFactory.getLogger(AbstractWatermarkAlignStrategy.class);

    /**
     * 水位延迟时间
     */
    int maxWatermarkDelayMs;
    /**
     * 对齐间隔时间
     */
    int alignmentIntervalTimeMs;

    public AbstractWatermarkAlignStrategy(int maxWatermarkDelayMs, int alignmentIntervalTimeMs) {
        this.maxWatermarkDelayMs = maxWatermarkDelayMs;
        this.alignmentIntervalTimeMs = alignmentIntervalTimeMs;
        if (maxWatermarkDelayMs > alignmentIntervalTimeMs) {
            throw new RuntimeException("max watermark delay ms must <= max event time wait ms");
        }
    }

    @Override
    public WatermarkGenerator<T> createWatermarkGenerator(WatermarkGeneratorSupplier.Context context) {
        return new WatermarkGenerator<T>() {
            Long lastEventTime = 0L;
            Long lastProcessTime = 0L;
            Long currentWatermarkTime = 0L;

            @Override
            public void onEvent(T t, long eventTimestamp, WatermarkOutput output) {
                long eventTime = getEventTime(t);
                // TODO: 脏数据导致事件时间超出当前时间，暂时不更新水位
                if (eventTime > System.currentTimeMillis()) {
                    return;
                }

                lastEventTime = Math.max(lastEventTime, getEventTime(t));
                lastProcessTime = System.currentTimeMillis();
                currentWatermarkTime = lastEventTime - maxWatermarkDelayMs - 1;
            }

            @Override
            public void onPeriodicEmit(WatermarkOutput output) {
                if (allowAlign()) {
                    currentWatermarkTime = Math.max(lastEventTime, System.currentTimeMillis() - 2 * maxWatermarkDelayMs);
                }
                output.emitWatermark(new Watermark(currentWatermarkTime));
            }

            public boolean allowAlign() {
                if (0 == lastProcessTime) {
                    return false;
                }
                return System.currentTimeMillis() - lastProcessTime > alignmentIntervalTimeMs;
            }
        };
    }

    @Override
    public TimestampAssigner<T> createTimestampAssigner(TimestampAssignerSupplier.Context context) {
        return (element, recordTimestamp) -> getEventTime(element);
    }

    /**
     * 获取事件时间
     * @param t 元素
     * @return 事件时间
     */
    protected abstract long getEventTime(T t);

}
