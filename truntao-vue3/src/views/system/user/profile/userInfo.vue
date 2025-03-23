<template>
   <el-form ref="userRef" :model="form" :rules="rules" label-width="80px">
      <el-form-item label="用户昵称" prop="nickName">
         <el-input v-model="form.nickName" maxlength="30" />
      </el-form-item>
      <el-form-item label="手机号码" prop="phoneNumber">
         <el-input v-model="form.phoneNumber" maxlength="11" />
      </el-form-item>
      <el-form-item label="邮箱" prop="email">
         <el-input v-model="form.email" maxlength="50" />
      </el-form-item>
      <el-form-item label="性别">
         <el-radio-group v-model="form.sex">
            <el-radio value="0">男</el-radio>
            <el-radio value="1">女</el-radio>
         </el-radio-group>
      </el-form-item>
      <el-form-item>
      <el-button type="primary" @click="submit">保存</el-button>
      <el-button type="danger" @click="close">关闭</el-button>
      </el-form-item>
   </el-form>
</template>

<script setup lang="ts">
import { ref, watch, getCurrentInstance } from 'vue';
import type { FormInstance } from 'element-plus';
import { updateUserProfile } from "@/api/system/user";

interface UserFormData {
  nickName?: string;
  phoneNumber?: string;
  email?: string;
  sex?: string;
}

interface UserProps {
  phoneNumber?: string;
  email?: string;
  nickName?: string;
  sex?: string;
}

const props = defineProps({
  user: {
    type: Object as () => UserProps,
    required: true
  }
});

const { proxy } = getCurrentInstance()!;
const userRef = ref<FormInstance>();

const form = ref<UserFormData>({});
const rules = ref({
  nickName: [{ required: true, message: "用户昵称不能为空", trigger: "blur" }],
  email: [{ required: true, message: "邮箱地址不能为空", trigger: "blur" }, { type: "email", message: "请输入正确的邮箱地址", trigger: ["blur", "change"] }],
  phoneNumber: [{ required: true, message: "手机号码不能为空", trigger: "blur" }, { pattern: /^1[3|4|5|6|7|8|9][0-9]\d{8}$/, message: "请输入正确的手机号码", trigger: "blur" }],
});

/** 提交按钮 */
function submit() {
  userRef.value?.validate(valid => {
    if (valid) {
      updateUserProfile(form.value).then(() => {
        proxy!.$modal.msgSuccess("修改成功");
        if (props.user && form.value.phoneNumber) {
          props.user.phoneNumber = form.value.phoneNumber;
        }
        if (props.user && form.value.email) {
          props.user.email = form.value.email;
        }
      });
    }
  });
}

/** 关闭按钮 */
function close() {
  proxy!.$tab.closeOpenPage({ path: '/system/user/profile' });
}

// 回显当前登录用户信息
watch(() => props.user, (user) => {
  if (user) {
    form.value = { 
      nickName: user.nickName, 
      phoneNumber: user.phoneNumber, 
      email: user.email, 
      sex: user.sex 
    };
  }
},{ immediate: true });
</script>
