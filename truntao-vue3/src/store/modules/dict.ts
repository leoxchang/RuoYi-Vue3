import { defineStore } from 'pinia'

// 定义字典项的接口
interface DictItem {
  key: string;
  value: any;
}

// 定义字典状态的接口
interface DictState {
  dict: DictItem[];
}

const useDictStore = defineStore(
  'dict',
  {
    state: (): DictState => ({
      dict: []
    }),
    actions: {
      // 获取字典
      getDict(_key: string | null): any | null {
        if (_key == null || _key === "") {
          return null;
        }
        try {
          for (let i = 0; i < this.dict.length; i++) {
            if (this.dict[i].key === _key) {
              return this.dict[i].value;
            }
          }
        } catch (e) {
          return null;
        }
        return null; // 如果没有找到匹配项，返回 null
      },
      // 设置字典
      setDict(_key: string | null, value: any): void {
        if (_key !== null && _key !== "") {
          this.dict.push({
            key: _key,
            value: value
          });
        }
      },
      // 删除字典
      removeDict(_key: string): boolean {
        let bln = false;
        try {
          for (let i = 0; i < this.dict.length; i++) {
            if (this.dict[i].key === _key) {
              this.dict.splice(i, 1);
              return true;
            }
          }
        } catch (e) {
          bln = false;
        }
        return bln;
      },
      // 清空字典
      cleanDict(): void {
        this.dict = [];
      },
      // 初始字典
      initDict(): void {
        // 初始化逻辑可以在这里添加
      }
    }
  })

export default useDictStore