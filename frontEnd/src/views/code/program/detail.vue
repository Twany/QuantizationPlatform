<template>
  <div class="app-container">

    <el-tabs type="border-card" class="demo-tabs" @tab-click="handleClick">
      <!-- 代码管理标签 -->
      <el-tab-pane label="代码管理" name="first">
        <el-button type="primary" plain icon="el-icon-plus" size="mini" @click="handleAddFile"
          v-hasPermi="['system:dept:add']">新增</el-button>


        <el-table v-loading="loading" :data="list.slice((pageNum-1)*pageSize,pageNum*pageSize)" style="width: 100%;">
          <el-table-column label="序号" type="index" align="center" width="50">
            <template slot-scope="scope">
              <span>{{(pageNum - 1) * pageSize + scope.$index + 1}}</span>
            </template>
          </el-table-column>


          <el-table-column label="文件类型" align="center" prop="fileType" width="100">
            <template slot-scope="scope">
              <i class="el-icon-folder" v-if="scope.row.fileType === 'folder'" />

              <span v-else>{{ scope.row.fileType }}</span>
            </template>
          </el-table-column>


          <el-table-column label="文件名" align="left" prop="fileName" :show-overflow-tooltip="true">
            <template slot-scope="scope">
              <span @dblclick="doubleClickFile(scope.row)">{{ scope.row.fileName }}</span>
            </template>
          </el-table-column>

          <el-table-column label="描述" align="center" prop="fileDesc" :show-overflow-tooltip="true" />
          <el-table-column label="上传人" align="center" prop="uploaderName" :show-overflow-tooltip="true" />
          <el-table-column label="上传时间" align="center" prop="editTime" width="180">
            <template slot-scope="scope" v-if="scope.row.fileType!='folder'">
              <span>{{ parseTime(scope.row.createTime).slice(0,10) }}</span>
            </template>
          </el-table-column>
          <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
            <template slot-scope="scope">
              <el-button v-if="scope.row.fileType!='folder'" size="mini" type="text" icon="el-icon-preview"
                @click="previewFile(scope.row)" v-hasPermi="['fileManage:futures:download']">预览</el-button>
              <el-button v-if="scope.row.fileType!='folder'" size="mini" type="text" icon="el-icon-download"
                @click="downFile(scope.row)" v-hasPermi="['fileManage:futures:download']">下载</el-button>
              <el-button v-if="scope.$index!=0" v-hasPermi="['fileManage:futures:delete']"
                @click="deleteFile(scope.row)" size="mini" type="text" icon="el-icon-delete">删除</el-button>
            </template>
          </el-table-column>
        </el-table>

        <!-- 上传文件弹窗 -->
        <el-dialog v-loading="uploadFileLoading" :title="title" :visible.sync="open" width="600px" append-to-body>
          <el-form ref="form" :model="form" label-width="80px">

            <el-row>
              <el-col :span="12">
                <el-form-item label="文件名称" prop="file_name">
                  <el-input v-model="form.fileName" placeholder="请输入文件名称" />
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="12">
                <el-form-item label="文件描述" prop="file_desc">
                  <el-input v-model="form.fileDesc" placeholder="请输入文件描述" />
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="12">
                <el-form-item label="选择文件" prop="file_desc">
                  <el-upload action="#" v-model="uploadFileInfo" :http-request="requestUpload" :show-file-list="false"
                    :before-upload="beforeUpload">
                    <el-button size="small">
                      选择
                      <i class="el-icon-upload el-icon--right"></i>
                    </el-button>
                  </el-upload>
                </el-form-item>

              </el-col>
            </el-row>


          </el-form>
          <div slot="footer" class="dialog-footer">
            <el-button type="primary" @click="submitForm">确 定</el-button>
            <el-button @click="cancel">取 消</el-button>
          </div>
        </el-dialog>

      </el-tab-pane>

      <!-- 运行结果标签 -->
      <el-tab-pane label="运行结果" name="second">
        <!-- 结果集数据 -->
        <h2>结果集</h2>
        <el-upload ref="upload" class="upload-demo"
          action="https://run.mocky.io/v3/9d059bf9-4660-45f2-925d-ce80ad6c4d15" :limit="1" :on-exceed="handleExceed"
          :auto-upload="false">
          <template #trigger>
            <el-button type="primary" size="small">select file</el-button>
          </template>
          <el-button class="ml-3" type="success" @click="submitUpload" size="small" style="margin-left: 16px;">
            上传
          </el-button>
          <template #tip>
            <div class="el-upload__tip text-red">
              限制 1 个文件，新文件将覆盖旧文件
            </div>
          </template>
        </el-upload>

        <el-divider />

        <!-- 相关性计算 -->
        <h2>操作</h2>
        <el-button>相关性计算</el-button>


        <el-divider />

        <!-- 结果分析 -->
        <h2>分析</h2>
        <el-col>
          <el-card class="update-log" style="width:fit-content;float: left;">
            <div slot="header" class="clearfix">
              <span>结果相关度</span>
            </div>
            <div class="body">
              <el-progress type="dashboard" :percentage="80">
                <template #default="{ percentage }">
                  <span class="percentage-value">{{ percentage }}%</span>
                  <span class="percentage-label">Progressing</span>
                </template>
              </el-progress>
              <!-- <span style="display: inline-block; height: 30px; line-height: 30px">结果相关度</span> -->
            </div>
          </el-card>

          <el-card class="update-log" style="width:fit-content;float: left;margin-left: 16px;">
            <div slot="header" class="clearfix">
              <span>结果准确度</span>
            </div>
            <div class="body">
              <el-progress type="dashboard" :percentage="60">
                <template #default="{ percentage }">
                  <span class="percentage-value">{{ percentage }}%</span>
                  <span class="percentage-label">Progressing</span>
                </template>
              </el-progress>
              <!-- <span style="display: inline-block; height: 30px; line-height: 30px">结果相关度</span> -->
            </div>
          </el-card>
        </el-col>


      </el-tab-pane>

      <!-- 模型运行标签 -->
      <el-tab-pane label="模型运行" name="third">


        <!-- 选择数据集 -->
        <el-form :model="runModalData" class="demo-form-inline" label-width="120px">
          <el-form-item label="数据源编号：">
            <el-input v-model="runModalData.runModalDataId" placeholder="" clearable style="width: 100px;" />
            <el-button type="primary" @click="searchDataClick()" style="margin-left: 16px;">查询数据源
            </el-button>
          </el-form-item>
          <el-form-item label="模型编号：">
            <el-input v-model="runModalData.runModalModalId" placeholder="" clearable style="width: 100px;" />
            <el-button type="primary" @click="" style="margin-left: 16px;">查询模型</el-button>
          </el-form-item>
        </el-form>


        <!-- 选择数据弹窗 -->
        <el-dialog v-model="selectDataDialogVisible" title="Tips" width="80%" :before-close="handleClose">
          <fileList />
ssss
          <template #footer>
            <span class="dialog-footer">
              <el-button @click="dialogVisible = false">Cancel</el-button>
              <el-button type="primary" @click="dialogVisible = false">
                Confirm
              </el-button>
            </span>
          </template>
        </el-dialog>

        <el-divider />

        <span class="modelRun">模型运行：</span>
        <el-switch v-model="runModelLoading" loading />


      </el-tab-pane>



    </el-tabs>



  </div>
</template>

<script>
  import {
    list,
    forceLogout,
    addFile,
    addPlatformFileDetail,
    downloadFile,
    delFile,
    getDownloadUrl
  } from "@/api/fileManage/fileList";
  import {
    listType
  } from "@/api/fileManage/fileType";
  import {
    getUserProfile
  } from "@/api/system/user";
  import fileList from "@/views/fileManage/fileList";


  export default {
    name: "Online",
    data() {
      return {
        selectDataDialogVisible: true,
        runModalData: {},
        runModelLoading: false,
        programIdFromLastPage: 0,
        fileTypeList: [],
        uploadFileInfo: {},
        // 遮罩层
        loading: true,
        uploadFileLoading: false,
        lastFileParentId: 0, // 上次parentId，默认进入就是0
        curFileParentId: 0,
        // 表格数据
        list: [],
        userInfo: {},
        // 弹出层标题
        title: "",
        // 是否显示弹出层
        open: false,
        pageNum: 1,
        pageSize: 10000,
        // 查询参数
        queryParams: {
          //   ipaddr: undefined,
          //   userName: undefined
        },
        // 数据源种类，期权为0
        fileTypeId: 0,
        // 表单参数
        form: {
          fileName: "",
          fileDesc: ""
        },
        // 已上传后的文件信息
        uploadedFileInfo: {},

        // 要保持的全量DTO
        platformFileDetailItem: {}
      };
    },
    created() {
      var id = this.$route.query.id;
      console.log(id);
      this.getListType();
      this.getList();
      this.getUser();
    },
    methods: {
      searchDataClick() {
        this.selectDataDialogVisible = true;
        console.log("点击查询");

      },
      handleClose() {},
      // 当数据集超过1个后
      handleExceed() {

      },
      // 提交数据集
      submitUpload() {

      },
      handleClick(row) {
        console.log(row);
      },
      /** 新增按钮操作 */
      handleAddFile(row) {
        // this.reset();
        if (row != undefined) {
          this.form.parentId = row.deptId;
        }
        this.open = true;
        this.title = "添加文件";
      },
      /** 查询文件列表 */
      getList() {
        this.loading = true;

        list(this.curFileParentId, 0).then(response => {
          this.list = response.rows;
          this.list.unshift({
            id: this.lastFileParentId,
            fileName: '../',
            fileType: 'folder',
            fileDesc: '',
            createTime: '',
            parentFileId: this.lastFileParentId
          });

          this.loading = false;
        });
      },
      getListType() {
        listType().then(response => {
          console.log(response);
          this.fileTypeList = response.rows;
        });
      },
      //   双击文件，文件夹进入下一层，非文件夹进行预览
      doubleClickFile(row) {
        // 如果是文件类型，那么进入对应的文件夹，去库中查询以此id为parentId的文件
        if (row.fileType === 'folder') {
          console.log("进入子文件夹");

          this.curFileParentId = row.id;

          this.getList();
        } else {
          // 预览文件
          console.log(row);
        }

      },
      // 下载文件
      downFile(row) {
        var path = row.fileUrl;
        const regex = /[^/]+$/; // 匹配最后一个斜杠后面的内容
        const match = path.match(regex);
        if (match) {
          const filename = match[0];

          this.$modal.confirm('是否确认下载？').then(function () {
            downloadFile(row.fileUrl).then(response => {
              console.log(response);
              const blob = new Blob([response], {
                type: 'application/octet-stream'
              });
              let url = URL.createObjectURL(blob);

              let a = document.createElement('a');
              a.href = url;
              a.download = filename; // 设置下载的文件名
              a.click();

              // 释放URL对象
              URL.revokeObjectURL(url);
            });
          }).then(() => {
            this.$modal.msgSuccess("下载成功");
          }).catch(() => {});

        } else {
          console.log('未匹配到文件名');
        }


      },
      // 预览文件
      previewFile(row) {
        console.log(row);
        const Base64 = require('js-base64').Base64;

        var originUrl = 'http://localhost/dev-api/resources/' + row.fileUrl; //要预览文件的访问地址
        window.open('http://127.0.0.1:8012/onlinePreview?url=' + originUrl);
      },
      // http://localhost/dev-api/resources/20231220_18/158f2bf86a95498d94c0401974e3b003.png

      /* 下载文件的公共方法，参数就传blob文件流*/
      handleExport(data) {
        // 动态创建iframe下载文件
        let fileName = this.selectedTabelRow[0].dirName;
        if (!data) {
          return;
        }
        let blob = new Blob([data], {
          type: "application/octet-stream"
        });
        if ("download" in document.createElement("a")) {
          // 不是IE浏览器
          let url = window.URL.createObjectURL(blob);
          let link = document.createElement("a");
          link.style.display = "none";
          link.href = url;
          link.setAttribute("download", fileName);
          document.body.appendChild(link);
          link.click();
          document.body.removeChild(link); // 下载完成移除元素
          window.URL.revokeObjectURL(url); // 释放掉blob对象
        } else {
          // IE 10+
          window.navigator.msSaveBlob(blob, fileName);
        }
      },
      // 覆盖默认的上传行为
      requestUpload(item) {
        //上传文件的需要formdata类型;所以要转
        let formData = new FormData();
        formData.append('file', item.file);
        formData.append('fileName', this.form.fileName);

        this.uploadFileLoading = true;

        addFile(formData).then(response => {
          this.uploadedFileInfo = response;
          this.uploadFileLoading = false;
        });
      },
      /** 保存提交按钮 */
      submitForm: function () {
        this.platformFileDetailItem.fileId = this.uploadedFileInfo.id;
        this.platformFileDetailItem.fileName = this.form.fileName;
        this.platformFileDetailItem.fileDesc = this.form.fileDesc;
        this.platformFileDetailItem.fileType = this.uploadedFileInfo.fileType;
        this.platformFileDetailItem.fileUrl = this.uploadedFileInfo.filePath;
        this.platformFileDetailItem.fileSize = this.uploadedFileInfo.fileSize;
        this.platformFileDetailItem.uploaderName = this.userInfo.nickName;
        this.platformFileDetailItem.uploaderId = this.userInfo.userId;
        this.platformFileDetailItem.parentFileId = this.curFileParentId;
        this.platformFileDetailItem.fileTypeId = this.fileTypeId;


        addPlatformFileDetail(this.platformFileDetailItem).then(response => {
          console.log(response);
          this.$modal.msgSuccess("新增成功");
          this.open = false;
          this.form = {}
          this.getList();
        });



        // this.$refs["form"].validate(valid => {
        //   if (valid) {
        //     if (this.form.deptId != undefined) {
        //       updateDept(this.form).then(response => {
        //         this.$modal.msgSuccess("修改成功");
        //         this.open = false;
        //         this.getList();
        //       });
        //     } else {
        //       addDept(this.form).then(response => {
        //         this.$modal.msgSuccess("新增成功");
        //         this.open = false;
        //         this.getList();
        //       });
        //     }
        //   }
        // });

      },
      // 删除文件
      deleteFile(row) {

        this.$modal.confirm('是否确认删除？').then(function () {
          delFile(row.id).then(response => {
            this.getList();
          });
        }).then(() => {
          this.$modal.msgSuccess("删除成功");
        }).catch(() => {});


      },
      getUser() {
        getUserProfile().then(response => {
          this.userInfo = response.data;
        });
      },
      // 取消按钮
      cancel() {
        this.open = false;
      },
      /** 搜索按钮操作 */
      handleQuery() {
        this.pageNum = 1;
        this.getList();
      },
      /** 重置按钮操作 */
      resetQuery() {
        this.resetForm("queryForm");
        this.handleQuery();
      },

      // 上传预处理
      beforeUpload(file) {
        // if (file.type.indexOf("image/") == -1) {
        //   this.$modal.msgError("文件格式错误，请上传图片类型,如：JPG，PNG后缀的文件。");
        // } else {
        //   const reader = new FileReader();
        //   reader.readAsDataURL(file);
        //   reader.onload = () => {
        //     this.options.img = reader.result;
        //   };
        // }
      }


    }
  };

</script>


<style scoped>
  .modelRun {
    color: rgb(96, 98, 102) !important;
    font-weight: 500 !important;
  }

  h2 {
    color: rgb(96, 98, 102) !important;
    font-weight: 600 !important;
  }

  .percentage-value {
    display: block;
    margin-top: 10px;
    font-size: 28px;
  }

  .percentage-label {
    display: block;
    margin-top: 10px;
    font-size: 12px;
  }

</style>
