<template>
  <div class="user-info-head" @click="editCropper()">
    <img :src="options.img" title="点击上传头像" class="img-circle img-lg" />
    <el-dialog :title="title" v-model="open" :append-to="appendTo" width="800px" @open="dialogAppendTo"  @opened="modalOpened" @close="closeDialog">
      <el-row>
        <el-col :xs="24" :md="12" :style="{ height: '350px' }">
          <vue-cropper
            ref="cropper"
            :img="options.img"
            :info="true"
            :autoCrop="options.autoCrop"
            :autoCropWidth="options.autoCropWidth"
            :autoCropHeight="options.autoCropHeight"
            :fixedBox="options.fixedBox"
            :outputType="options.outputType"
            @realTime="realTime"
            v-if="visible"
          />
        </el-col>
        <el-col :xs="24" :md="12" :style="{ height: '350px' }">
          <div class="avatar-upload-preview">
            <img v-if="options.previews.url" :src="options.previews.url" :style="options.previews.img || {}"  alt=""/>
          </div>
        </el-col>
      </el-row>
      <br />
      <el-row>
        <el-col :lg="2" :md="2">
          <el-upload
            action="#"
            :http-request="requestUpload"
            :show-file-list="false"
            :before-upload="beforeUpload"
          >
            <el-button>
              选择
              <el-icon class="el-icon--right"><Upload /></el-icon>
            </el-button>
          </el-upload>
        </el-col>
        <el-col :lg="{ span: 1, offset: 2 }" :md="2">
          <el-button icon="Plus" @click="changeScale(1)"></el-button>
        </el-col>
        <el-col :lg="{ span: 1, offset: 1 }" :md="2">
          <el-button icon="Minus" @click="changeScale(-1)"></el-button>
        </el-col>
        <el-col :lg="{ span: 1, offset: 1 }" :md="2">
          <el-button icon="RefreshLeft" @click="rotateLeft()"></el-button>
        </el-col>
        <el-col :lg="{ span: 1, offset: 1 }" :md="2">
          <el-button icon="RefreshRight" @click="rotateRight()"></el-button>
        </el-col>
        <el-col :lg="{ span: 2, offset: 6 }" :md="2">
          <el-button type="primary" @click="uploadImg()">提 交</el-button>
        </el-col>
      </el-row>
    </el-dialog>
  </div>
</template>

<script setup lang="ts">
import "vue-cropper/dist/index.css";
import { ref, reactive, getCurrentInstance } from 'vue';
import { VueCropper } from "vue-cropper";
import { uploadAvatar } from "@/api/system/user";
import useUserStore from "@/store/modules/user";

interface CropperOptions {
  img: string;
  autoCrop: boolean;
  autoCropWidth: number;
  autoCropHeight: number;
  fixedBox: boolean;
  outputType: string;
  filename: string;
  previews: {
    url?: string;
    img?: Record<string, string>;
  };
}

interface DialogContainer {
  value?: HTMLElement;
}

const userStore = useUserStore();
const { proxy } = getCurrentInstance()!;
const appendTo = ref('body');
const cropper = ref();

// 接收来自父组件的 append-to 属性
const props = defineProps({
  dialogContainer: {
    type: Object as () => DialogContainer,
    required: true
  }
});

const open = ref(false);
const visible = ref(false);
const title = ref("修改头像");

// 图片裁剪数据
const options = reactive<CropperOptions>({
  img: userStore.avatar,     // 裁剪图片的地址
  autoCrop: true,            // 是否默认生成截图框
  autoCropWidth: 200,        // 默认生成截图框宽度
  autoCropHeight: 200,       // 默认生成截图框高度
  fixedBox: true,            // 固定截图框大小 不允许改变
  outputType: "png",         // 默认生成截图为PNG格式
  filename: 'avatar',        // 文件名称
  previews: {}               // 预览数据
});

/** 编辑头像 */
function editCropper() {
  open.value = true;
}

function dialogAppendTo() {
  appendTo.value = props.dialogContainer as unknown as string;
}

/** 打开弹出层结束时的回调 */
function modalOpened() {
  visible.value = true;
}

/** 覆盖默认上传行为 */
function requestUpload() {}

/** 向左旋转 */
function rotateLeft() {
  (proxy!.$refs.cropper as any).rotateLeft();
}

/** 向右旋转 */
function rotateRight() {
  (proxy!.$refs.cropper as any).rotateRight();
}

/** 图片缩放 */
function changeScale(num: number) {
  num = num || 1;
  (proxy!.$refs.cropper as any).changeScale(num);
}

/** 上传预处理 */
function beforeUpload(file: File) {
  if (file.type.indexOf("image/") == -1) {
    proxy!.$modal.msgError("文件格式错误，请上传图片类型,如：JPG，PNG后缀的文件。");
  } else {
    const reader = new FileReader();
    reader.readAsDataURL(file);
    reader.onload = () => {
      options.img = reader.result as string;
      options.filename = file.name;
    };
  }
}

/** 上传图片 */
function uploadImg() {
  (proxy!.$refs.cropper as any).getCropBlob((data: Blob) => {
    let formData = new FormData();
    formData.append("avatarFile", data, options.filename);
    uploadAvatar(formData).then(response => {
      open.value = false;
      options.img = response.data.imgUrl;
      userStore.avatar = options.img;
      proxy!.$modal.msgSuccess("修改成功");
      visible.value = false;
    });
  });
}

/** 实时预览 */
function realTime(data: any) {
  options.previews = data;
}

/** 关闭窗口 */
function closeDialog() {
  options.img = userStore.avatar;
  visible.value = false;
}
</script>

<style lang='scss' scoped>
.user-info-head {
  position: relative;
  display: inline-block;
  height: 120px;
}

.user-info-head:hover:after {
  content: "+";
  position: absolute;
  left: 0;
  right: 0;
  top: 0;
  bottom: 0;
  color: #eee;
  background: rgba(0, 0, 0, 0.5);
  font-size: 24px;
  font-style: normal;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  cursor: pointer;
  line-height: 110px;
  border-radius: 50%;
}
</style>
