import request from '@/utils/request'

export interface LoginData {
  username: string;
  password: string;
  code?: string;
  uuid?: string;
}

export interface RegisterData {
  username: string;
  password: string;
  confirmPassword: string;
  code: string;
  uuid: string;
}

export interface CodeImgResponse {
  captchaEnabled: boolean;
  img: string;
  uuid: string;
}

// 登录方法
export function login(data: LoginData): Promise<any> {
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
export function register(data: RegisterData): Promise<any> {
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
export function getInfo(): Promise<any> {
  return request({
    url: '/getInfo',
    method: 'get'
  })
}

// 退出方法
export function logout(): Promise<any> {
  return request({
    url: '/logout',
    method: 'post'
  })
}

// 获取验证码
export function getCodeImg(): Promise<CodeImgResponse> {
  return request({
    url: '/captchaImage',
    headers: {
      isToken: false
    },
    method: 'get',
    timeout: 20000
  })
}
