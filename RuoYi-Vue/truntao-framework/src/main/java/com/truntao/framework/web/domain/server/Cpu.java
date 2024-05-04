package com.truntao.framework.web.domain.server;

import com.truntao.common.utils.Arithmetic;
import lombok.Getter;
import lombok.Setter;

/**
 * CPU相关信息
 *
 * @author truntao
 */
@Setter
public class Cpu {
    /**
     * 核心数
     */
    @Getter
    private int cpuNum;

    /**
     * CPU总的使用率
     */
    private double total;

    /**
     * CPU系统使用率
     */
    private double sys;

    /**
     * CPU用户使用率
     */
    private double used;

    /**
     * CPU当前等待率
     */
    private double wait;

    /**
     * CPU当前空闲率
     */
    private double free;

    public double getTotal() {
        return Arithmetic.round(Arithmetic.mul(total, 100), 2);
    }

    public double getSys() {
        return Arithmetic.round(Arithmetic.mul(sys / total, 100), 2);
    }

    public double getUsed() {
        return Arithmetic.round(Arithmetic.mul(used / total, 100), 2);
    }

    public double getWait() {
        return Arithmetic.round(Arithmetic.mul(wait / total, 100), 2);
    }

    public double getFree() {
        return Arithmetic.round(Arithmetic.mul(free / total, 100), 2);
    }

}
