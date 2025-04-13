import { DefineComponent } from 'vue'

export interface CrontabProps {
  hideComponent?: Array<'second' | 'min' | 'hour' | 'day' | 'month' | 'week' | 'year'>
  expression?: string
}

declare const Crontab: DefineComponent<CrontabProps>

export default Crontab