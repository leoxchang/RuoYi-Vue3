interface Styles {
  [key: string]: string;
}

const styles: Styles = {
  'el-rate': '.el-rate{display: inline-block; vertical-align: text-top;}',
  'el-upload': '.el-upload__tip{line-height: 1.2;}'
}

interface Element {
  tag: string;
  children?: Element[];
}

interface Config {
  fields: Element[];
}

function addCss(cssList: string[], el: Element): void {
  const css = styles[el.tag]
  css && cssList.indexOf(css) === -1 && cssList.push(css)
  if (el.children) {
    el.children.forEach(el2 => addCss(cssList, el2))
  }
}

export function makeUpCss(conf: Config): string {
  const cssList: string[] = []
  conf.fields.forEach(el => addCss(cssList, el))
  return cssList.join('\n')
}