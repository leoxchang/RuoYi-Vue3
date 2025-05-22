import {defineComponent, h, resolveComponent, PropType} from 'vue'
import {makeMap} from '@/utils'

const isAttr = makeMap(
    'accept,accept-charset,accesskey,action,align,alt,async,autocomplete,' +
    'autofocus,autoplay,autosave,bgcolor,border,buffered,challenge,charset,' +
    'checked,cite,class,code,codebase,color,cols,colspan,content,http-equiv,' +
    'name,contenteditable,contextmenu,controls,coords,data,datetime,default,' +
    'defer,dir,dirname,disabled,download,draggable,dropzone,enctype,method,for,' +
    'form,formaction,headers,height,hidden,high,href,hreflang,http-equiv,' +
    'icon,id,ismap,itemprop,keytype,kind,label,lang,language,list,loop,low,' +
    'manifest,max,maxlength,media,method,GET,POST,min,multiple,email,file,' +
    'muted,name,novalidate,open,optimum,pattern,ping,placeholder,poster,' +
    'preload,radiogroup,readonly,rel,required,reversed,rows,rowspan,sandbox,' +
    'scope,scoped,seamless,selected,shape,size,type,text,password,sizes,span,' +
    'spellcheck,src,srcdoc,srclang,srcset,start,step,style,summary,tabindex,' +
    'target,title,type,usemap,value,width,wrap' + 'prefix-icon'
)
const isNotProps = makeMap(
    'layout,prepend,regList,tag,document,changeTag,defaultValue'
)

interface ComponentOption {
  label: string;
  value: any;
  disabled?: boolean;
}

interface ComponentConf {
  tag: string;
  options?: ComponentOption[];
  optionType?: string;
  border?: boolean;
  showTip?: boolean;
  fileSize?: number;
  sizeUnit?: string;
  accept?: string;
  buttonText?: string;
  'list-type'?: string;

  [key: string]: any;
}

type ChildFunc = (h: any, conf: ComponentConf, key: string) => any;

interface ComponentChildMap {
  [key: string]: {
    [key: string]: ChildFunc;
  };
}

interface ComponentSlotMap {
  [key: string]: {
    [key: string]: (h: any, conf: ComponentConf, key: string) => (() => any) | undefined;
  };
}

interface DataObject {
  attrs: Record<string, any>;
  props: Record<string, any>;
  on: Record<string, any>;
  style: Record<string, any>;
}
function useVModel(props: any, emit: any): Record<string, any> {
  return {
    modelValue: props.defaultValue,
    'onUpdate:modelValue': (val: any) => emit('update:modelValue', val),
  }
}

const componentChild: ComponentChildMap = {
  'el-button': {
    default(h, conf, key) {
      return conf[key]
    },
  },
  'el-select': {
    options(h, conf) {
      return conf.options?.map(item => h(resolveComponent('el-option'), {
        label: item.label,
        value: item.value,
      }))
    }
  },
  'el-radio-group': {
    options(h, conf) {
      return conf.optionType === 'button' ? conf.options?.map(item => h(resolveComponent('el-checkbox-button'), {
        label: item.value,
      }, () => item.label)) : conf.options?.map(item => h(resolveComponent('el-radio'), {
        label: item.value,
        border: conf.border,
      }, () => item.label))
    }
  },
  'el-checkbox-group': {
    options(h, conf) {
      return conf.optionType === 'button' ? conf.options?.map(item => h(resolveComponent('el-checkbox-button'), {
        label: item.value,
      }, () => item.label)) : conf.options?.map(item => h(resolveComponent('el-checkbox'), {
        label: item.value,
        border: conf.border,
      }, () => item.label))
    }
  },
  'el-upload': {
    'list-type': (h, conf) => {
      const option: Record<string, any> = {}
      if (conf['list-type'] === 'picture-card') {
        return h(resolveComponent('el-icon'), option, () => h(resolveComponent('Plus')))
      } else {
        // option.size = "small"
        option.type = "primary"
        option.icon = "Upload"
        return h(resolveComponent('el-button'), option, () => conf.buttonText)
      }
    },
  }
}

const componentSlot: ComponentSlotMap = {
  'el-upload': {
    'tip': (h, conf) => {
      if (conf.showTip) {
        return () => h('div', {
          class: "el-upload__tip"
        }, '只能上传不超过' + conf.fileSize + conf.sizeUnit + '的' + conf.accept + '文件')
      }
      return undefined;
    },
  }
}

export default defineComponent({
  // 使用 render 函数
  render() {
    const dataObject: DataObject = {
      attrs: {},
      props: {},
      on: {},
      style: {}
    }
    const confClone: ComponentConf = JSON.parse(JSON.stringify(this.conf))
    const children: any[] = []
    const slot: Record<string, any> = {}
    const childObjs = componentChild[confClone.tag]

    if (childObjs) {
      Object.keys(childObjs).forEach(key => {
        const childFunc = childObjs[key]
        if (confClone[key]) {
          children.push(childFunc(h, confClone, key))
        }
      })
    }

    const slotObjs = componentSlot[confClone.tag]
    if (slotObjs) {
      Object.keys(slotObjs).forEach(key => {
        const childFunc = slotObjs[key]
        if (confClone[key]) {
          slot[key] = childFunc(h, confClone, key)
        }
      })
    }

    Object.keys(confClone).forEach(key => {
      const val = confClone[key]
      if (dataObject[key as keyof DataObject]) {
        dataObject[key as keyof DataObject] = val
      } else if (isAttr(key)) {
        dataObject.attrs[key] = val
      } else if (!isNotProps(key)) {
        dataObject.props[key] = val
      }
    })

    if (children.length > 0) {
      slot.default = () => children
    }

    return h(resolveComponent(this.conf.tag),
        {
          modelValue: this.$attrs.modelValue,
          ...dataObject.props,
          ...dataObject.attrs,
          style: {
            ...dataObject.style
          },
        },
        slot ?? null)
  },
  props: {
    conf: {
      type: Object as PropType<ComponentConf>,
      required: true,
    },
  }
})
