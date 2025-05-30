import { login, logout, getInfo } from '@/api/login'
import { getToken, setToken, removeToken } from '@/utils/auth'
import { isHttp, isEmpty } from '@/utils/validate'
import defAva from '@/assets/images/profile.jpg'
import { defineStore } from 'pinia'

interface UserState {
  token: string
  id: string
  name: string
  nickName: string
  avatar: string
  roles: string[]
  permissions: string[]
}

interface LoginInfo {
  username: string
  password: string
  code?: string
  uuid?: string
}

const useUserStore = defineStore('user', {
  state: (): UserState => ({
    token: getToken() || '',
    id: '',
    name: '',
    nickName: '',
    avatar: '',
    roles: [],
    permissions: []
  }),
  actions: {
    // 登录
    login(userInfo: LoginInfo) {
      const username = userInfo.username.trim()
      const password = userInfo.password
      const code = userInfo.code
      const uuid = userInfo.uuid
      let loginData = {
        username: username,
        password: password,
        code: code,
        uuid: uuid
      }
      return new Promise<void>((resolve, reject) => {
        login(loginData).then((res: any) => {
          setToken(res.data.token)
          this.token = res.data.token
          resolve()
        }).catch((error: any) => {
          reject(error)
        })
      })
    },
    // 获取用户信息
    getInfo() {
      return new Promise<any>((resolve, reject) => {
        getInfo().then((res: any) => {
          const user = res.data.user
          let avatar = user.avatar || ''
          if (!isHttp(avatar)) {
            avatar = (isEmpty(avatar)) ? defAva : avatar
          }
          if (res.data.roles && res.data.roles.length > 0) {
            this.roles = res.data.roles
            this.permissions = res.data.permissions
          } else {
            this.roles = ['ROLE_DEFAULT']
          }
          this.id = user.userId
          this.name = user.userName
          this.nickName = user.nickName
          this.avatar = avatar
          resolve(res)
        }).catch((error: any) => {
          reject(error)
        })
      })
    },
    // 退出系统
    logOut() {
      return new Promise<void>((resolve, reject) => {
        logout().then(() => {
          this.token = ''
          this.roles = []
          this.permissions = []
          removeToken()
          resolve()
        }).catch((error: any) => {
          reject(error)
        })
      })
    }
  }
})

export default useUserStore
