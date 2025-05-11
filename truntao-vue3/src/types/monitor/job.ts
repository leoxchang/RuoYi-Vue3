import {PageQuery} from "@/types/global";

export interface JobItem {
  jobId: number | string;
  jobName: string;
  jobGroup: string;
  invokeTarget: string;
  cronExpression: string;
  misfirePolicy: string;
  concurrent: string;
  status: string;
  createTime?: string;
  nextValidTime?: string | number;
  [key: string]: any;
}

export interface JobQuery extends PageQuery {
  /** 任务名称（不能为空，最多64字符） */
  jobName?: string;

  /** 任务组名 */
  jobGroup?: string;

  /** 调用目标字符串（不能为空，最多500字符） */
  invokeTarget?: string;

  /** cron 执行表达式（不能为空，最多255字符） */
  cronExpression?: string;

  /** 计划执行错误策略（1立即执行 2执行一次 3放弃执行） */
  misfirePolicy?: string;

  /** 是否并发执行（0允许 1禁止） */
  concurrent?: string;

  /** 状态（0正常 1暂停） */
  status?: string;

  /** 备注信息 */
  remark?: string;
}
