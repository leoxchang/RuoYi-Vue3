<template>
  <div class="app-container">
    <el-row :gutter="10">
      <el-col :span="8">
        <el-card style="height: calc(100vh - 125px)">
          <template #header>
            <Collection style="width: 1em; height: 1em; vertical-align: middle;"/>
            <span style="vertical-align: middle;">缓存列表</span>
            <el-button
                style="float: right; padding: 3px 0"
                link
                type="primary"
                icon="Refresh"
                @click="refreshCacheNames()"
            ></el-button>
          </template>
          <el-table
              v-loading="loading"
              :data="cacheNames"
              :height="tableHeight"
              highlight-current-row
              @row-click="getCacheKeys"
              style="width: 100%"
          >
            <el-table-column
                label="序号"
                width="60"
                type="index"
            ></el-table-column>

            <el-table-column
                label="缓存名称"
                align="center"
                prop="cacheName"
                :show-overflow-tooltip="true"
                :formatter="nameFormatter"
            ></el-table-column>

            <el-table-column
                label="备注"
                align="center"
                prop="remark"
                :show-overflow-tooltip="true"
            />
            <el-table-column
                label="操作"
                width="60"
                align="center"
                class-name="small-padding fixed-width"
            >
              <template #default="scope">
                <el-button
                    link
                    type="primary"
                    icon="Delete"
                    @click="handleClearCacheName(scope.row)"
                ></el-button>
              </template>
            </el-table-column>
          </el-table>
        </el-card>
      </el-col>

      <el-col :span="8">
        <el-card style="height: calc(100vh - 125px)">
          <template #header>
            <Key style="width: 1em; height: 1em; vertical-align: middle;"/>
            <span style="vertical-align: middle;">键名列表</span>
            <el-button
                style="float: right; padding: 3px 0"
                link
                type="primary"
                icon="Refresh"
                @click="refreshCacheKeys()"
            ></el-button>
          </template>
          <el-table
              v-loading="subLoading"
              :data="cacheKeys"
              :height="tableHeight"
              highlight-current-row
              @row-click="handleCacheValue"
              style="width: 100%"
          >
            <el-table-column
                label="序号"
                width="60"
                type="index"
            ></el-table-column>
            <el-table-column
                label="缓存键名"
                align="center"
                :show-overflow-tooltip="true"
                :formatter="keyFormatter"
            >
            </el-table-column>
            <el-table-column
                label="操作"
                width="60"
                align="center"
                class-name="small-padding fixed-width"
            >
              <template #default="scope">
                <el-button
                    link
                    type="primary"
                    icon="Delete"
                    @click="handleClearCacheKey(scope.row)"
                ></el-button>
              </template>
            </el-table-column>
          </el-table>
        </el-card>
      </el-col>

      <el-col :span="8">
        <el-card :bordered="false" style="height: calc(100vh - 125px)">
          <template #header>
            <Document style="width: 1em; height: 1em; vertical-align: middle;"/>
            <span style="vertical-align: middle;">缓存内容</span>
            <el-button
                style="float: right; padding: 3px 0"
                link
                type="primary"
                icon="Refresh"
                @click="handleClearCacheAll()"
            >清理全部
            </el-button
            >
          </template>
          <el-form :model="cacheForm">
            <el-row :gutter="32">
              <el-col :offset="1" :span="22">
                <el-form-item label="缓存名称:" prop="cacheName">
                  <el-input v-model="cacheForm.cacheName" :readOnly="true"/>
                </el-form-item>
              </el-col>
              <el-col :offset="1" :span="22">
                <el-form-item label="缓存键名:" prop="cacheKey">
                  <el-input v-model="cacheForm.cacheKey" :readOnly="true"/>
                </el-form-item>
              </el-col>
              <el-col :offset="1" :span="22">
                <el-form-item label="缓存内容:" prop="cacheValue">
                  <el-input
                      v-model="cacheForm.cacheValue"
                      type="textarea"
                      :rows="8"
                      :readOnly="true"
                  />
                </el-form-item>
              </el-col>
            </el-row>
          </el-form>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script setup lang="ts">
import {ref, getCurrentInstance, onMounted} from 'vue';
import {
  listCacheName,
  listCacheKey,
  getCacheValue,
  clearCacheName,
  clearCacheKey,
  clearCacheAll
} from "@/api/monitor/cache.js";

interface CacheNameItem {
  cacheName: string;
  remark: string;

  [key: string]: any;
}

interface CacheKeyItem {
  cacheKey: string;

  [key: string]: any;
}

interface CacheFormData {
  cacheName?: string;
  cacheKey?: string;
  cacheValue?: string;

  [key: string]: any;
}

const {proxy} = getCurrentInstance()!;

const cacheNames = ref<CacheNameItem[]>([]);
const cacheKeys = ref<CacheKeyItem[]>([]);
const cacheForm = ref<CacheFormData>({});
const loading = ref<boolean>(true);
const subLoading = ref<boolean>(false);
const nowCacheName = ref<string>("");
const tableHeight = ref<number>(window.innerHeight - 200);

/** 查询缓存名称列表 */
function getCacheNames(): void {
  loading.value = true;
  listCacheName().then(response => {
    cacheNames.value = response.data;
    loading.value = false;
  });
}

/** 刷新缓存名称列表 */
function refreshCacheNames(): void {
  getCacheNames();
  proxy!.$modal.msgSuccess("刷新缓存列表成功");
}

/** 清理指定名称缓存 */
function handleClearCacheName(row: CacheNameItem): void {
  clearCacheName(row.cacheName).then(() => {
    proxy!.$modal.msgSuccess("清理缓存名称[" + row.cacheName + "]成功");
    getCacheKeys();
  });
}

/** 查询缓存键名列表 */
function getCacheKeys(row?: CacheNameItem): void {
  const cacheName = row !== undefined ? row.cacheName : nowCacheName.value;
  if (cacheName === "") {
    return;
  }
  subLoading.value = true;
  listCacheKey(cacheName).then(response => {
    cacheKeys.value = response.data;
    subLoading.value = false;
    nowCacheName.value = cacheName;
    cacheForm.value = {
      cacheName: cacheName
    };
  });
}

/** 刷新缓存键名列表 */
function refreshCacheKeys(): void {
  getCacheKeys();
  proxy!.$modal.msgSuccess("刷新键名列表成功");
}

/** 清理指定键名缓存 */
function handleClearCacheKey(row: CacheKeyItem): void {
  clearCacheKey(row.cacheKey).then(() => {
    proxy!.$modal.msgSuccess("清理缓存键名[" + row.cacheKey + "]成功");
    getCacheKeys();
  });
}

/** 获取缓存内容 */
function handleCacheValue(row: CacheKeyItem): void {
  getCacheValue(cacheForm.value.cacheName || '', row.cacheKey).then(response => {
    cacheForm.value.cacheKey = row.cacheKey;
    cacheForm.value.cacheValue = response.data;
  });
}

/** 清理全部缓存 */
function handleClearCacheAll(): void {
  proxy!.$modal.confirm('是否确认清理全部缓存数据项?').then(function () {
    return clearCacheAll();
  }).then(() => {
    proxy!.$modal.msgSuccess("清理全部缓存数据项成功");
  }).catch(() => {
  });
}

/** 格式化缓存名称 */
function nameFormatter(row: CacheNameItem): string {
  return row.cacheName === null ? "" : row.cacheName;
}

/** 格式化缓存键名 */
function keyFormatter(row: CacheKeyItem): string {
  return row.cacheKey === null ? "" : row.cacheKey;
}

onMounted(() => {
  getCacheNames();
});
</script>
