import {ref, toRefs} from 'vue'
import useDictStore from '@/store/modules/dict'
import {getDicts} from '@/api/system/dict/data'
import DictData from "@/components/DictData";

export interface DictData {
  label: string | undefined;
  value: string | undefined;
  elTagType: string | undefined;
  elTagClass: string | undefined;
}


/**
 * 获取字典数据
 */
export function useDict(...args: string[]) {
  const res = ref<Map<string,DictData>>(new Map());
  return (() => {
    args.forEach((dictType) => {
      res.value[dictType] = [];
      const dicts = useDictStore().getDict(dictType);
      if (dicts) {
        res.value[dictType] = dicts;
      } else {
        getDicts(dictType).then(resp => {
          res.value[dictType] = resp.data.map((p) => {
            const item: DictData = {
              label: p.dictLabel,
              value: p.dictValue,
              elTagType: p.listClass,
              elTagClass: p.cssClass
            }
            return item;
          });
          useDictStore().setDict(dictType, res.value[dictType]);
        })
      }
    })
    return toRefs(res.value);
  })()
}
