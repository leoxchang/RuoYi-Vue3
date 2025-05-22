import {titleCase} from '@/utils'
import {trigger} from './config'

// 文件大小单位设置
interface Units {
  [key: string]: string;
}

const units: Units = {
  KB: '1024',
  MB: '1024 / 1024',
  GB: '1024 / 1024 / 1024',
}

interface FormConfig {
  formRef: string;
  formModel: string;
  formRules: string;
  fields: FormField[];

  [key: string]: any;
}

interface FormField {
  vModel?: string;
  tag?: string;
  defaultValue?: any;
  required?: boolean;
  placeholder?: string;
  label?: string;
  regList?: RegItem[];
  options?: OptionItem[];
  dataType?: string;
  props: {
    props: {
      [key: string]: string;
    };
  };
  valueKey: string;
  labelKey: string;
  childrenKey: string;
  action?: string;
  'auto-upload'?: boolean;
  fileSize?: number;
  sizeUnit?: string;
  accept?: string;
  children: FormField[];

  [key: string]: any;
}

interface RegItem {
  pattern: string;
  message: string;
}

interface OptionItem {
  label: string;
  value: any;

  [key: string]: any;
}

/**
 * @name: 生成js需要的数据
 * @description: 生成js需要的数据
 * @param {FormConfig} conf
 * @param {string} type 弹窗或表单
 * @return {string}
 */
export function makeUpJs(conf: FormConfig, type: string): string {
  conf = JSON.parse(JSON.stringify(conf))
  const dataList: string[] = []
  const ruleList: string[] = []
  const optionsList: string[] = []
  const propsList: string[] = []
  const methodList: string[] = []
  const uploadVarList: string[] = []

  conf.fields.forEach((el) => {
    buildAttributes(
        el,
        dataList,
        ruleList,
        optionsList,
        methodList,
        propsList,
        uploadVarList
    )
  })

  const script = buildexport(
      conf,
      type,
      dataList.join('\n'),
      ruleList.join('\n'),
      optionsList.join('\n'),
      uploadVarList.join('\n'),
      propsList.join('\n'),
      methodList.join('\n')
  )

  return script
}

/**
 * @name: 生成参数
 * @description: 生成参数，包括表单数据表单验证数据，多选选项数据，上传数据等
 * @return {void}
 */
function buildAttributes(
    el: FormField,
    dataList: string[],
    ruleList: string[],
    optionsList: string[],
    methodList: string[],
    propsList: string[],
    uploadVarList: string[]
): void {
  buildData(el, dataList)
  buildRules(el, ruleList)

  if (el.options && el.options.length) {
    buildOptions(el, optionsList)
    if (el.dataType === 'dynamic') {
      const model = `${el.vModel}Options`
      const options = titleCase(model)
      buildOptionMethod(`get${options}`, model, methodList)
    }
  }

  if (el.props && el.props.props) {
    buildProps(el, propsList)
  }

  if (el.action && el.tag === 'el-upload') {
    uploadVarList.push(
        `
      // 上传请求路径
      const ${el.vModel}Action = ref('${el.action}')
      // 上传文件列表
      const ${el.vModel}fileList =  ref([])`
    )
    methodList.push(buildBeforeUpload(el))
    if (!el['auto-upload']) {
      methodList.push(buildSubmitUpload(el))
    }
  }

  if (el.children) {
    el.children.forEach((el2) => {
      buildAttributes(
          el2,
          dataList,
          ruleList,
          optionsList,
          methodList,
          propsList,
          uploadVarList
      )
    })
  }
}

/**
 * @name: 生成表单数据formData
 * @description: 生成表单数据formData
 * @param {FormField} conf
 * @param {string[]} dataList 数据列表
 * @return {void}
 */
function buildData(conf: FormField, dataList: string[]): void {
  if (conf.vModel === undefined) return
  let defaultValue: string
  if (typeof conf.defaultValue === 'string' && !conf.multiple) {
    defaultValue = `'${conf.defaultValue}'`
  } else {
    defaultValue = `${JSON.stringify(conf.defaultValue)}`
  }
  dataList.push(`${conf.vModel}: ${defaultValue},`)
}

/**
 * @name: 生成表单验证数据rule
 * @description: 生成表单验证数据rule
 * @param {FormField} conf
 * @param {string[]} ruleList 验证数据列表
 * @return {void}
 */
function buildRules(conf: FormField, ruleList: string[]): void {
  if (conf.vModel === undefined) return
  const rules: string[] = []
  if (conf.tag && trigger[conf.tag]) {
    if (conf.required) {
      const type = Array.isArray(conf.defaultValue) ? "type: 'array'," : ''
      let message = Array.isArray(conf.defaultValue)
          ? `请至少选择一个${conf.vModel}`
          : conf.placeholder
      message ??= `${conf.label}不能为空`;
      rules.push(
          `{ required: true, ${type} message: '${message}', trigger: '${
              trigger[conf.tag]
          }' }`
      )
    }
    if (conf.regList && Array.isArray(conf.regList)) {
      conf.regList.forEach((item) => {
        if (item.pattern && conf.tag) {
          rules.push(
              `{ pattern: new RegExp(${item.pattern}), message: '${
                  item.message
              }', trigger: '${trigger[conf.tag]}' }`
          )
        }
      })
    }
    ruleList.push(`${conf.vModel}: [${rules.join(',')}],`)
  }
}

/**
 * @name: 生成选项数据
 * @description: 生成选项数据，单选多选下拉等
 * @param {FormField} conf
 * @param {string[]} optionsList 选项数据列表
 * @return {void}
 */
function buildOptions(conf: FormField, optionsList: string[]): void {
  if (conf.vModel === undefined) return
  if (conf.dataType === 'dynamic') {
    conf.options = []
  }
  const str = `const ${conf.vModel}Options = ref(${JSON.stringify(conf.options)})`
  optionsList.push(str)
}

/**
 * @name: 生成方法
 * @description: 生成方法
 * @param {string} methodName 方法名
 * @param {string} model
 * @param {string[]} methodList 方法列表
 * @return {void}
 */
function buildOptionMethod(methodName: string, model: string, methodList: string[]): void {
  const str = `function ${methodName}() {
    // TODO 发起请求获取数据
    ${model}.value
  }`
  methodList.push(str)
}

/**
 * @name: 生成表单组件需要的props设置
 * @description: 生成表单组件需要的props设置，如；级联组件
 * @param {FormField} conf
 * @param {string[]} propsList
 * @return {void}
 */
function buildProps(conf: FormField, propsList: string[]): void {
  if (conf.dataType === 'dynamic') {
    conf.valueKey !== 'value' && (conf.props.props.value = conf.valueKey)
    conf.labelKey !== 'label' && (conf.props.props.label = conf.labelKey)
    conf.childrenKey !== 'children' &&
    (conf.props.props.children = conf.childrenKey)
  }
  const str = `
  // props设置
  const ${conf.vModel}Props = ref(${JSON.stringify(conf.props?.props)})`
  propsList.push(str)
}

/**
 * @name: 生成上传组件的相关内容
 * @description: 生成上传组件的相关内容
 * @param {FormField} conf
 * @return {string}
 */
function buildBeforeUpload(conf: FormField): string {
  const unitNum = units[conf.sizeUnit || 'MB']
  let rightSizeCode = ''
  let acceptCode = ''
  const returnList: string[] = []
  if (conf.fileSize) {
    rightSizeCode = `let isRightSize = file.size / ${unitNum} < ${conf.fileSize}
    if(!isRightSize){
      proxy.$modal.msgError('文件大小超过 ${conf.fileSize}${conf.sizeUnit}')
    }`
    returnList.push('isRightSize')
  }
  if (conf.accept) {
    acceptCode = `let isAccept = new RegExp('${conf.accept}').test(file.type)
    if(!isAccept){
      proxy.$modal.msgError('应该选择${conf.accept}类型的文件')
    }`
    returnList.push('isAccept')
  }
  const str = `
  /**
   * @name: 上传之前的文件判断
   * @description: 上传之前的文件判断，判断文件大小文件类型等
   * @param {File} file
   * @return {boolean}
   */  
  function ${conf.vModel}BeforeUpload(file: File): boolean {
    ${rightSizeCode}
    ${acceptCode}
    return ${returnList.join('&&')}
  }`
  return returnList.length ? str : ''
}

/**
 * @name: 生成提交表单方法
 * @description: 生成提交表单方法
 * @param {FormField} conf vModel 表单ref
 * @return {string}
 */
function buildSubmitUpload(conf: FormField): string {
  const str = `function submitUpload(): void {
    this.$refs['${conf.vModel}'].submit()
  }`
  return str
}

/**
 * @name: 组装js代码
 * @description: 组装js代码方法
 * @return {string}
 */
function buildexport(
    conf: FormConfig,
    type: string,
    data: string,
    rules: string,
    selectOptions: string,
    uploadVar: string,
    props: string,
    methods: string
): string {
  let str = `
    const { proxy } = getCurrentInstance()
    const ${conf.formRef} = ref()
    const data = reactive({
      ${conf.formModel}: {
        ${data}
      },
      ${conf.formRules}: {
        ${rules}
      }
    })

    const {${conf.formModel}, ${conf.formRules}} = toRefs(data)

    ${selectOptions}

    ${uploadVar}

    ${props}

    ${methods}
  `

  if (type === 'dialog') {
    str += `
      // 弹窗设置
      const dialogVisible = defineModel()
      // 弹窗确认回调
      const emit = defineEmits(['confirm'])
      /**
       * @name: 弹窗打开后执行
       * @description: 弹窗打开后执行方法
       * @return {void}
       */
      function onOpen(): void {

      }
      /**
       * @name: 弹窗关闭时执行
       * @description: 弹窗关闭方法，重置表单
       * @return {void}
       */
      function onClose(): void {
        ${conf.formRef}.value.resetFields()
      }
      /**
       * @name: 弹窗取消
       * @description: 弹窗取消方法
       * @return {void}
       */
      function close(): void {
        dialogVisible.value = false
      }
      /**
       * @name: 弹窗表单提交
       * @description: 弹窗表单提交方法
       * @return {void}
       */
      function handelConfirm(): void {
        ${conf.formRef}.value.validate((valid: boolean) => {
          if (!valid) return
          // TODO 提交表单

          close()
          // 回调父级组件
          emit('confirm')
        })
      }
    `
  } else {
    str += `
    /**
     * @name: 表单提交
     * @description: 表单提交方法
     * @return {void}
     */
    function submitForm(): void {
      ${conf.formRef}.value.validate((valid: boolean) => {
        if (!valid) return
        // TODO 提交表单
      })
    }
    /**
     * @name: 表单重置
     * @description: 表单重置方法
     * @return {void}
     */
    function resetForm(): void {
      ${conf.formRef}.value.resetFields()
    }
    `
  }
  return str
}
