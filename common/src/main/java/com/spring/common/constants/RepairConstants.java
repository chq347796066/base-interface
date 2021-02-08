package com.spring.common.constants;

/**
 * @author 熊锋
 * @date 2021-01-05 19:07
 * @Describe:报事报修
 */
public interface RepairConstants {

    /**
     * 工单状态(1业主提交,2业主取消,3关闭工单,4派工,5维修接单,6待支付,7已完成,8已评价，9已转单)
     */
    final Integer COMMIT=1;

    /**
     * 工单状态(1业主提交,2业主取消,3关闭工单,4派工,5维修接单,6待支付,7已完成,8已评价，9已转单)
     */
    final Integer CANCEL=2;

    /**
     * 工单状态(1业主提交,2业主取消,3关闭工单,4派工,5维修接单,6待支付,7已完成,8已评价，9已转单)
     */
    final Integer CLOSE=3;

    /**
     * 工单状态(1业主提交,2业主取消,3关闭工单,4派工,5维修接单,6待支付,7已完成,8已评价，9已转单)
     */
    final Integer SEND=4;

    /**
     * 工单状态(1业主提交,2业主取消,3关闭工单,4派工,5维修接单,6待支付,7已完成,8已评价，9已转单)
     */
    final Integer WORK=5;

    /**
     * 工单状态(1业主提交,2业主取消,3关闭工单,4派工,5维修接单,6待支付,7已完成,8已评价，9已转单)
     */
    final Integer STAY_PAY=6;

    /**
     * 工单状态(1业主提交,2业主取消,3关闭工单,4派工,5维修接单,6待支付,7已完成,8已评价，9已转单)
     */
    final Integer COMPLETE=7;

    /**
     * 工单状态(1业主提交,2业主取消,3关闭工单,4派工,5维修接单,6待支付,7已完成,8已评价，9已转单)
     */
    final Integer COMMENTS=8;

    /**
     * 工单状态(1业主提交,2业主取消,3关闭工单,4派工,5维修接单,6待支付,7已完成,8已评价，9已转单)
     */
    final Integer TURN_SINGLE=9;

    /**
     * 图片类型(1报修图片,2维修完图片,3支付图片)
     */
    final Integer REPAIR_PIC=1;

    /**
     * 图片类型(1报修图片,2维修完图片,3支付图片)
     */
    final Integer WORK_PIC=2;

    /**
     * 图片类型(1报修图片,2维修完图片,3支付图片)
     */
    final Integer PAY_PIC=3;

    /**
     * 流程类型（1业主提交）
     */
    final Integer PROCESS_SUBMIT=1;

    /**
     * 流程类型（2业主取消）
     */
    final Integer PROCESS_CANCEL=2;

    /**
     * 流程类型（3关闭工单）
     */
    final Integer PROCESS_CLOSE=3;

    /**
     * 流程类型（4已派单）
     */
    final Integer PROCESS_WORK=4;

    /**
     * 流程类型（5已接单）
     */
    final Integer PROCESS_ACCEPT=5;

    /**
     * 流程类型（6待支付）
     */
    final Integer PROCESS_PAY=6;

    /**
     * 流程类型（7已完成）
     */
    final Integer PROCESS_FINISH=7;

    /**
     * 流程类型（8已评价）
     */
    final Integer PROCESS_COMMENT=8;

    /**
     * 流程类型（9已转单）
     */
    final Integer PROCESS_CHANGE=9;

    /**
     * 待处理工单
     */
    final Integer PENDING=1;

    /**
     * 处理中工单
     */
    final Integer BEGIN_PROCESS=2;

    /**
     * 已完成工单
     */
    final Integer COMPLETED=3;


}
