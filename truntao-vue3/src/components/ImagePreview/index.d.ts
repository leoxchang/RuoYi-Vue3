import type { PropType } from 'vue';

export interface ImagePreviewProps {
  src: string;
  width: number | string;
  height: number | string;
}

export const imagePreviewProps = {
  src: {
    type: String,
    required: true
  },
  width: {
    type: [Number, String] as PropType<number | string>,
    default: '100%'
  },
  height: {
    type: [Number, String] as PropType<number | string>,
    default: '100%'
  }
};