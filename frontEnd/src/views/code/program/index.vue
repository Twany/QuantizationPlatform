<template>
  <div class="app-container">
    <el-button type="primary" plain icon="el-icon-plus" size="mini" @click="handleAddProgram"
      v-hasPermi="['code:program:add']">新增项目</el-button>


    <el-row :gutter="20">
      <el-col :span="8"  v-for="a in 4"   :key="a"  style="margin: 16px 0;" >
        <el-card class="box-card" :span="8">
          <template #header>
            <div class="card-header" style="line-height: 36px;">
              <span   size="large" type="primary" style="font: 1.2em sans-serif;padding: 12px 0;">测试项目1</span>

              <!-- <span   type="info" style="float: right;color: darkgray;border: 1px solid;border-radius: 2px;padding: 2px;">进入项目</span> -->
              <el-button  style="float: right;">编辑项目</el-button>
            </div>
          </template>
          <div v-for="o in 4" :key="o" class="text item">{{ 'List item ' + o }}</div>
      
          <div style="margin-top: 16px">
            <el-button  style="width: 100%;"  type="primary" plain @click="handleEnterProject">进入项目</el-button>
          </div>
        </el-card>
      </el-col>
      
    </el-row>



    <!-- 上传文件弹窗 -->
    <el-dialog :title="title" :visible.sync="addProgramDialogOpen" width="600px" append-to-body>
      <el-form ref="form" :model="form" label-width="80px">

        <el-row>
          <el-col :span="12">
            <el-form-item label="项目名称" prop="file_name">
              <el-input v-model="form.fileName" placeholder="请输入文件名称" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="项目描述" prop="file_desc">
              <el-input v-model="form.fileDesc" placeholder="请输入文件描述" />
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
  export default {
    name: "Online",
    data() {
      return {

        addProgramDialogOpen: false,
        title: '新增项目',
        form: {
          fileName: '',
          fileDesc: ''
        },
      };
    },


    created() {},
    methods: {
      handleAddProgram() {
        this.addProgramDialogOpen = true;
        this.title = '新增项目';
        this.form = {
          fileName: '',
          fileDesc: ''
            };
        
        },
        handleEnterProject() {
            this.$router.push({
                path: '/code/detail',
                query: {
                    id: 1
                }
            });
        },

        cancel() {
          this.addProgramDialogOpen = false;
        },
        submitForm() {
          this.$refs.form.validate(valid => {
            if (valid) {
              this.addProgramDialogOpen = false;
            }
          });
        }



    }
  };

</script>
