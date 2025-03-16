<template>
   <!-- 授权用户 -->
   <el-dialog title="选择用户" v-model="visible" width="800px" top="5vh" :modal-append-to-body="false">
      <el-form :model="queryParams" ref="queryRef" :inline="true">
         <el-form-item label="用户名称" prop="userName">
            <el-input
               v-model="queryParams.userName"
               placeholder="请输入用户名称"
               clearable
               style="width: 200px"
               @keyup.enter="handleQuery"
            />
         </el-form-item>
         <el-form-item label="手机号码" prop="phoneNumber">
            <el-input
               v-model="queryParams.phoneNumber"
               placeholder="请输入手机号码"
               clearable
               style="width: 200px"
               @keyup.enter="handleQuery"
            />
         </el-form-item>
         <el-form-item>
            <el-button type="primary" icon="Search" @click="handleQuery">搜索</el-button>
            <el-button icon="Refresh" @click="resetQuery">重置</el-button>
         </el-form-item>
      </el-form>
      <el-row>
         <el-table @row-click="clickRow" ref="refTable" :data="userList" @selection-change="handleSelectionChange" height="260px">
            <el-table-column type="selection" width="55"></el-table-column>
            <el-table-column label="用户名称" prop="userName" :show-overflow-tooltip="true" />
            <el-table-column label="用户昵称" prop="nickName" :show-overflow-tooltip="true" />
            <el-table-column label="邮箱" prop="email" :show-overflow-tooltip="true" />
            <el-table-column label="手机" prop="phoneNumber" :show-overflow-tooltip="true" />
            <el-table-column label="状态" align="center" prop="status">
               <template #default="scope">
                  <dict-tag :options="sys_normal_disable" :value="scope.row.status" />
               </template>
            </el-table-column>
            <el-table-column label="创建时间" align="center" prop="createTime" width="180">
               <template #default="scope">
                  <span>{{ parseTime(scope.row.createTime) }}</span>
               </template>
            </el-table-column>
         </el-table>
         <pagination
            v-show="total > 0"
            :total="total"
            v-model:page="queryParams.pageNum"
            v-model:limit="queryParams.pageSize"
            @pagination="getList"
         />
      </el-row>
      <template #footer>
         <div class="dialog-footer">
            <el-button type="primary" @click="handleSelectUser">确 定</el-button>
            <el-button @click="visible = false">取 消</el-button>
         </div>
      </template>
   </el-dialog>
</template>

<script setup lang="ts">
import { ref, reactive, toRefs, getCurrentInstance } from 'vue';
import { unallocatedUserList, authUserSelectAll } from "@/api/system/role";
import type { FormInstance } from 'element-plus';
import type { User, UnallocatedUserQueryParams } from '@/types/system/role';

const props = defineProps({
  roleId: {
    type: [Number, String]
  }
});

const { proxy } = getCurrentInstance()!;
const { sys_normal_disable } = proxy!.useDict("sys_normal_disable");

const userList = ref<User[]>([]);
const visible = ref(false);
const total = ref<number>(0);
const userIds = ref<Array<string | number>>([]);
const loading = ref<boolean>(true);
const queryRef = ref<FormInstance>();

const data = reactive({
  queryParams: {
    pageNum: 1,
    pageSize: 10,
    roleId: undefined,
    userName: undefined,
    phoneNumber: undefined
  } as UnallocatedUserQueryParams
});

const { queryParams } = toRefs(data);

// 显示弹框
function show() {
  queryParams.value.roleId = props.roleId;
  getList();
  visible.value = true;
}

/**选择行 */
function clickRow(row) {
  proxy!.$refs["refTable"].toggleRowSelection(row);
}

// 多选框选中数据
function handleSelectionChange(selection) {
  userIds.value = selection.map(item => item.userId);
}

/** 查询未授权用户列表 */
function getList() {
  loading.value = true;
  unallocatedUserList(queryParams.value).then(response => {
    userList.value = response.data.data.rows;
    total.value = response.data.data.total;
    loading.value = false;
  });
}

/** 搜索按钮操作 */
function handleQuery() {
  queryParams.value.pageNum = 1;
  getList();
}

/** 重置按钮操作 */
function resetQuery() {
  proxy!.resetForm("queryRef");
  handleQuery();
}

const emit = defineEmits(["ok"]);
/** 选择授权用户操作 */
function handleSelectUser() {
  const roleId = queryParams.value.roleId;
  if (!roleId) {
    proxy!.$modal.msgError("角色ID不能为空");
    return;
  }
  if (userIds.value.length === 0) {
    proxy!.$modal.msgError("请选择要分配的用户");
    return;
  }
  authUserSelectAll({ roleId: roleId as string | number, userIds: userIds.value }).then(res => {
    proxy!.$modal.msgSuccess(res.data.msg);
    if (res.data.code === 200) {
      visible.value = false;
      emit("ok");
    }
  });
}

defineExpose({
  show,
});
</script>
