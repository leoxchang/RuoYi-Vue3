import useUserStore from '@/store/modules/user'

// 定义权限和角色的类型
type Permission = string;
type Role = string;

// 定义用户存储的接口
interface UserStore {
  permissions: Permission[];
  roles: Role[];
}

function authPermission(permission: Permission): boolean {
  const all_permission: Permission = "*:*:*";
  const permissions: Permission[] = useUserStore().permissions
  if (permission && permission.length > 0) {
    return permissions.some((v: Permission) => {
      return all_permission === v || v === permission
    })
  } else {
    return false
  }
}

function authRole(role: Role): boolean {
  const super_admin: Role = "admin";
  const roles: Role[] = useUserStore().roles
  if (role && role.length > 0) {
    return roles.some((v: Role) => {
      return super_admin === v || v === role
    })
  } else {
    return false
  }
}

// 定义导出对象的接口
interface AuthPlugin {
  hasPermi(permission: Permission): boolean;
  hasPermiOr(permissions: Permission[]): boolean;
  hasPermiAnd(permissions: Permission[]): boolean;
  hasRole(role: Role): boolean;
  hasRoleOr(roles: Role[]): boolean;
  hasRoleAnd(roles: Role[]): boolean;
}

const authPlugin: AuthPlugin = {
  // 验证用户是否具备某权限
  hasPermi(permission: Permission): boolean {
    return authPermission(permission);
  },
  // 验证用户是否含有指定权限，只需包含其中一个
  hasPermiOr(permissions: Permission[]): boolean {
    return permissions.some((item: Permission) => {
      return authPermission(item)
    })
  },
  // 验证用户是否含有指定权限，必须全部拥有
  hasPermiAnd(permissions: Permission[]): boolean {
    return permissions.every((item: Permission) => {
      return authPermission(item)
    })
  },
  // 验证用户是否具备某角色
  hasRole(role: Role): boolean {
    return authRole(role);
  },
  // 验证用户是否含有指定角色，只需包含其中一个
  hasRoleOr(roles: Role[]): boolean {
    return roles.some((item: Role) => {
      return authRole(item)
    })
  },
  // 验证用户是否含有指定角色，必须全部拥有
  hasRoleAnd(roles: Role[]): boolean {
    return roles.every((item: Role) => {
      return authRole(item)
    })
  }
}

export default authPlugin;