export interface QueryParams {
  pageNum: number;
  pageSize: number;
  tableName?: string;
  tableComment?: string;
  orderByColumn: string;
  isAsc: string;
}

export interface PreviewData {
  open: boolean;
  title: string;
  data: Record<string, string>;
  activeName: string;
}

export interface TableRow {
  tableName: string;
  genType: string;
  genPath: string;
  tableId: number;
}

export interface Column {
  columnId: number;
  columnName: string;
  columnComment: string;
  columnType: string;
  javaType: string;
  javaField: string;
  isInsert: string;
  isEdit: string;
  isList: string;
  isQuery: string;
  queryType: string;
  isRequired: string;
  htmlType: string;
  dictType: string;
  sort: number;
}

export interface TableInfo {
  treeCode?: string;
  treeName?: string;
  treeParentCode?: string;
  parentMenuId?: string;
  [key: string]: any;
}

export  interface GenTableAll{
  info: TableInfo;
  rows: Column[];
  tables: TableInfo[];
}
