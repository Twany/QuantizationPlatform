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
        <el-table-column label="创建时间" align="center" prop="editTime" width="180">
          <template slot-scope="scope">
            <span>{{ parseTime(scope.row.createTime) }}</span>
          </template>
        </el-table-column>
        <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
          <template slot-scope="scope">
            <el-button size="mini" type="text" icon="el-icon-delete" @click="handleForceLogout(scope.row)"
              v-hasPermi="['monitor:online:forceLogout']">强退</el-button>
          </template>
        </el-table-column>
      </el-table>
  
    </div>
  </template>
  
  <script>
    import {
      list,
      forceLogout
  } from "@/api/fileManage/fileList";
  import { listType} from "@/api/fileManage/fileType";
  
  
    export default {
      name: "Online",
      data() {
        return {
          fileTypeList: [],
          // 遮罩层
          loading: true,
          lastFileParentId: 0, // 上次parentId，默认进入就是0
          curFileParentId: 0,
          // 表格数据
          list: [],
          pageNum: 1,
          pageSize: 10,
          // 查询参数
          queryParams: {
            //   ipaddr: undefined,
            //   userName: undefined
          }
        };
      },
      created() {
        this.getListType();
        this.getList();
      },
      methods: {
        /** 查询文件列表 */
        getList() {
          this.loading = true;
  
          list(this.curFileParentId,3).then(response => {
            console.log(response);
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
          console.log(row);
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
        /** 强退按钮操作 */
        handleForceLogout(row) {
          this.$modal.confirm('是否确认强退名称为"' + row.userName + '"的用户？').then(function () {
            return forceLogout(row.tokenId);
          }).then(() => {
            this.getList();
            this.$modal.msgSuccess("强退成功");
          }).catch(() => {});
        }
      }
    };
  
  </script>
  