<template>
  <div class="app-container">
    <!-- 搜索 -->
    <!-- <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" label-width="68px">
        <el-form-item label="登录地址" prop="ipaddr">
          <el-input v-model="queryParams.ipaddr" placeholder="请输入登录地址" clearable @keyup.enter.native="handleQuery" />
        </el-form-item>
        <el-form-item label="用户名称" prop="userName">
          <el-input v-model="queryParams.userName" placeholder="请输入用户名称" clearable @keyup.enter.native="handleQuery" />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
          <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
        </el-form-item>
  
      </el-form> -->

    <!-- 选择数据源类别 -->
    <el-select v-model="selectDataType" class="m-2" placeholder="选择数据源种类">
      <el-option v-for="item in dataTypeList" :key="item.id" :label="item.dataTypeName" :value="item.id"
        v-hasPermi="[item.dataPermission]" />
    </el-select>
    &nbsp; &nbsp; &nbsp;

    <!-- 查询按钮 -->
    <el-button type="primary" plain icon="el-icon-search" size="" @click="searchFileByDataType"
      style="margin-left: 16px;">查询</el-button>


    <span style="margin:0 16px;color: #ccc;"> | </span>

    <!-- 新增文件 -->
    <el-button type="primary" plain icon="el-icon-plus" size="" @click="handleAddFile" style="margin-right: 16px;"
      v-hasPermi="['system:dept:add']">新增文件</el-button>

    <!-- 表单 -->
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
          <el-button v-if="scope.$index!=0" v-hasPermi="['fileManage:futures:delete']" @click="deleteFile(scope.row)"
            size="mini" type="text" icon="el-icon-delete">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 上传文件弹窗 -->
    <el-dialog v-loading="uploadFileLoading" :title="title" :visible.sync="open" width="600px" append-to-body>
      <el-form ref="form" :model="form" label-width="80px" required>

        <el-row>
          <el-col :span="12">
            <el-form-item label="文件类型" prop="fileType" required>
              <el-select v-model="form.fileType" placeholder="请选择文件类型">
                <el-option label="文件夹" value="folder" />
                <el-option label="文件" value="file" />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="文件名称" prop="fileName" required>
              <el-input v-model="form.fileName" placeholder="请输入文件名称" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="文件描述" prop="fileDesc" required>
              <el-input v-model="form.fileDesc" placeholder="请输入文件描述" required />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row v-if="form.fileType=='file'">
          <el-col :span="12">
            <el-form-item label="选择文件" prop="uploadedFileInfo" required>
              <el-upload action="#" v-model="form.uploadedFileInfo" :http-request="requestUpload"
                :show-file-list="false" :before-upload="beforeUpload">
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
    getType,
    listType
  } from "@/api/fileManage/fileType";
  import {
    getUserProfile
  } from "@/api/system/user";

  import {
    getConfigKey,
    listConfig
  } from "@/api/system/config";



  export default {
    name: "Online",
    data() {
      return {
        dataTypeList: [], //数据源种类
        selectDataType: 0, //选择的数据源
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
          fileDesc: "",
          uploadedFileInfo: {}
        },

        // 要保持的全量DTO
        platformFileDetailItem: {},

        //   系统配置
        serverAddr: '',
        serverkkViewAddr: ''
      };
    },
    created() {
      this.getListType();
      this.getList();
      this.getUser();
      this.getConfig();
    },
    methods: {
      getConfig() {
        getConfigKey('server.addr').then(response => {

          this.serverAddr = response.msg;
        });
        getConfigKey('server.kkview.addr').then(response => {

          this.serverkkViewAddr = response.msg;
        });

      },

      // 查询文件通过数据源
      searchFileByDataType() {
        // 将当前文件id置为选择的
        this.fileTypeId = this.selectDataType;
        this.getList();
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

        list(this.curFileParentId, this.fileTypeId).then(response => {
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
          console.log("数据源种类：");

          console.log(response);
          this.dataTypeList = response.rows;
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
        const Base64 = require('js-base64').Base64;

        var originUrl = this.serverAddr + 'npm'; //要预览文件的访问地址
        var previewUrl = originUrl + row.fileUrl + '&fullfilename=file.' + row.fileType;

        window.open(this.serverkkViewAddr + '/onlinePreview?url=' + encodeURIComponent(previewUrl));

      },

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


        addFile(formData).then(response => {
          // this.uploadedFileInfo = response;

          this.form.uploadedFileInfo = response;
          this.form.uploadedFileInfo.name = formData.fileName;
          this.uploadFileLoading = false;

          this.$modal.msgSuccess("上传成功");
        });
      },
      /** 保存提交按钮 */
      submitForm: function () {

        this.$refs["form"].validate(valid => {
          if (valid) {
            this.platformFileDetailItem.fileId = this.form.uploadedFileInfo.id;
            this.platformFileDetailItem.fileName = this.form.fileName;
            this.platformFileDetailItem.fileDesc = this.form.fileDesc;
            if (this.form.fileType == 'folder') {
              this.platformFileDetailItem.fileType = 'folder';
              this.platformFileDetailItem.fileUrl = '0';
              this.platformFileDetailItem.fileSize = '0';
            } else {
              this.platformFileDetailItem.fileType = this.form.uploadedFileInfo.fileType;
              this.platformFileDetailItem.fileUrl = this.form.uploadedFileInfo.filePath;
              this.platformFileDetailItem.fileSize = this.form.uploadedFileInfo.fileSize;
            }


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
          }
        });

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
