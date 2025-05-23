// 按需引入 CryptoJS 需要的组件
import CryptoJS from 'crypto-js/core'
import Latin1 from 'crypto-js/enc-latin1'
import AES from 'crypto-js/aes'
import ZeroPadding from 'crypto-js/pad-zeropadding'
import Utf8 from 'crypto-js/enc-utf8'
import Base64 from 'crypto-js/enc-base64'

/*
 * 加密 解密
 */
const baseCryptoCode: string = "vjVlOh3Qr9MFBgXK"; // 这个私钥每个项目指定一个唯一。更换密钥，请确认16位

const getKeyHex = (cryptoCode?: string): CryptoJS.lib.WordArray => Latin1.parse(cryptoCode || baseCryptoCode);

const getIvHex = (): CryptoJS.lib.WordArray => Latin1.parse(baseCryptoCode);

/**
 * 加密
 * @param key - 要加密的数据
 * @param cryptoCode - 加密密钥
 * @returns 加密后的字符串
 */
export const getEncrypt = (key: any, cryptoCode?: string): string => {
  let keyHex: CryptoJS.lib.WordArray = getKeyHex(cryptoCode);
  let ivHex: CryptoJS.lib.WordArray = getIvHex();
  try {
    key = JSON.stringify(key);
  } catch (e) {
    console.warn(e);
  }
  return AES.encrypt(key, keyHex, {
    mode: CryptoJS.mode.CBC,
    padding: ZeroPadding,
    iv: ivHex
  }).toString();
}

/**
 * 加密后转base64
 * @param key - 要加密的数据
 * @param cryptoCode - 加密密钥
 * @returns base64编码的加密字符串
 */
export const getEncryptToBase64 = (key: any, cryptoCode?: string): string => {
  let encryptStr: string = getEncrypt(key, cryptoCode);
  let wordArray: CryptoJS.lib.WordArray = Utf8.parse(encryptStr);
  return Base64.stringify(wordArray);
}

/**
 * 解密
 * @param data - 要解密的数据
 * @returns 解密后的数据
 */
export const getDecrypt = (data: string): any => {
  let keyHex: CryptoJS.lib.WordArray = getKeyHex();
  let ivHex: CryptoJS.lib.WordArray = getIvHex();
  let decrypted: string = AES.decrypt({
    ciphertext: Base64.parse(data)
  }, keyHex, {
    mode: CryptoJS.mode.CBC,
    padding: ZeroPadding,
    iv: ivHex
  }).toString(Utf8);
  try {
    decrypted = JSON.parse(decrypted);
  } catch (e) {
    console.warn(e);
  }
  return decrypted
}

/**
 * 对base64数据解密  先解析base64，在做解密
 * @param data - base64编码的加密数据
 * @returns 解密后的数据
 */
export const getDecryptByBase64 = (data: string): any => {
  let parsedWordArray: CryptoJS.lib.WordArray = Base64.parse(data);
  let decryptStr: string = parsedWordArray.toString(Utf8);
  return getDecrypt(decryptStr);
}