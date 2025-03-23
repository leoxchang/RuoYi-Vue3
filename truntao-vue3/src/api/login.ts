import request from '@/utils/request'
import { AxiosPromise } from 'axios'

interface LoginData {
  username: string;
  password: string;
  code: string;
  uuid: string;
}

interface RegisterData {
  username: string;
  password: string;
  confirmPassword: string;
  code: string;
  uuid: string;
}

interface CodeImgResponse {
  captchaEnabled: boolean;
  img: string;
  uuid: string;
}

// 登录方法
export function login(data: LoginData): AxiosPromise {
  return request({
    url: '/login',
    headers: {
      isToken: false,
      repeatSubmit: false
    },
    method: 'post',
    data: data
  })
}

// 注册方法
export function register(data: RegisterData): AxiosPromise {
  return request({
    url: '/register',
    headers: {
      isToken: false
    },
    method: 'post',
    data: data
  })
}

// 获取用户详细信息
export function getInfo(): AxiosPromise {
  return request({
    url: '/getInfo',
    method: 'get'
  })
}

// 退出方法
export function logout(): AxiosPromise {
  return request({
    url: '/logout',
    method: 'post'
  })
}

// 获取验证码
export function getCodeImg(): AxiosPromise<CodeImgResponse> {
  return request({
    url: '/captchaImage',
    headers: {
      isToken: false
    },
    method: 'get',
    timeout: 20000
  })
} 