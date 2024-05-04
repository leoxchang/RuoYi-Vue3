package com.truntao.framework.web.domain.server;

import java.lang.management.ManagementFactory;

import com.truntao.common.utils.Arithmetic;
import com.truntao.common.utils.DateUtils;
import lombok.Setter;

/**
 * JVM相关信息
 *
 * @author truntao
 */
@Setter
public class Jvm {
    /**
     * 当前JVM占用的内存总数(M)
     */
    private double total;

    /**
     * JVM最大可用内存总数(M)
     */
    private double max;

    /**
     * JVM空闲内存(M)
     */
    private double free;

    /**
     * JDK版本
     */
    private String version;

    /**
     * JDK路径
     */
    private String home;

    public double getTotal() {
        return Arithmetic.div(total, (1024 * 1024), 2);
    }

    public double getMax() {
        return Arithmetic.div(max, (1024 * 1024), 2);
    }

    public double getFree() {
        return Arithmetic.div(free, (1024 * 1024), 2);
    }

    public double getUsed() {
        return Arithmetic.div(total - free, (1024 * 1024), 2);
    }

    public double getUsage() {
        return Arithmetic.mul(Arithmetic.div(total - free, total, 4), 100);
    }

    /**
     * 获取JDK名称
     */
    public String getName() {
        return ManagementFactory.getRuntimeMXBean().getVmName();
    }

    public String getVersion() {
        return version;
    }

    public String getHome() {
        return home;
    }

    /**
     * JDK启动时间
     */
    public String getStartTime() {
        return DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD_HH_MM_SS, DateUtils.getServerStartDate());
    }

    /**
     * JDK运行时间
     */
    public String getRunTime() {
        return DateUtils.timeDistance(DateUtils.getNowDate(), DateUtils.getServerStartDate());
    }

    /**
     * 运行参数
     */
    public String getInputArgs() {
        return ManagementFactory.getRuntimeMXBean().getInputArguments().toString();
    }
}
