import { getEncryptToBase64 as encrypt, getDecryptByBase64 as decrypt } from "./encryption";

const encodeReserveRE = /[!'()*]/g;
const encodeReserveReplacer = (c: string): string => '%' + c.charCodeAt(0).toString(16);
const commaRE = /%2C/g;

const encode = (str: string): string => encodeURIComponent(str)
    .replace(encodeReserveRE, encodeReserveReplacer)
    .replace(commaRE, ',');

const decode = decodeURIComponent;

/**
 * 判断字符串是否是base64
 * @param str - 待检测的字符串
 * @returns 是否为base64格式
 */
function isBase64(str: string): boolean {
  if (str === '' || str.trim() === '') {
    return false;
  }
  try {
    return btoa(atob(str)) === str;
  } catch (err) {
    return false;
  }
}

/**
 * 序列化对象值类型
 */
type QueryValue = string | number | boolean | null | undefined;

/**
 * 查询对象类型
 */
interface QueryObject {
  [key: string]: QueryValue | QueryValue[];
}

/**
 * 序列化对象并加密
 * @param obj - 待序列化的对象
 * @returns 加密后的查询字符串
 */
export const stringifyQuery = (obj: QueryObject | null | undefined): string => {
  const res = obj ? Object.keys(obj).map(key => {
    const val = obj[key];

    if (val === undefined) {
      return '';
    }

    if (val === null) {
      return encode(key);
    }

    if (Array.isArray(val)) {
      const result: string[] = [];
      val.forEach(val2 => {
        if (val2 === undefined) {
          return;
        }
        if (val2 === null) {
          result.push(encode(key));
        } else {
          result.push(encode(key) + '=' + encode(String(val2)));
        }
      });
      return result.join('&');
    }

    return encode(key) + '=' + encode(String(val));
  }).filter(x => x.length > 0).join('&') : null;

  return res ? `?${encrypt(res)}` : '';
};

/**
 * 解析查询结果类型
 */
interface ParsedQuery {
  [key: string]: string | string[] | null;
}

/**
 * 解密反序列化字符串参数
 * @param query - 查询字符串
 * @returns 解析后的对象
 */
export const parseQuery = (query: string): ParsedQuery => {
  const res: ParsedQuery = {};

  query = query.trim().replace(/^(\?|#|&)/, '');

  if (!query) {
    return res;
  }

  // 解密
  query = isBase64(query) ? decrypt(query) : query;

  query.split('&').forEach(param => {
    const parts = param.replace(/\+/g, ' ').split('=');
    const key = decode(parts.shift() || '');
    const val = parts.length > 0
        ? decode(parts.join('='))
        : null;

    if (res[key] === undefined) {
      res[key] = val;
    } else if (Array.isArray(res[key])) {
      (res[key] as string[]).push(val as string);
    } else {
      res[key] = [res[key] as string, val as string];
    }
  });

  return res;
};
