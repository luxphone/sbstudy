<template>
  <div>
    <el-card>
      <el-row :gutter="20">
        <el-col :span="4">
          <el-input placeholder="请输图书类型" clearable v-model="keyword">
            <el-button slot="append" icon="el-icon-search" @click="initBooktypeList"></el-button>
          </el-input>
        </el-col>
        <el-col :span="4">
          <el-button type="primary" @click="showAddForm">添加类型</el-button>
        </el-col>
      </el-row>
      <el-table :data="booktypeList" border stripe>
        <el-table-column label="#" type="index"></el-table-column>
        <el-table-column label="图书类型" prop="name" width="200"></el-table-column>
        <el-table-column label="操作" width="200">
          <template v-slot="scope">
            <el-button type="warning" size="mini" @click="showUpdateForm(scope.row)">修改</el-button>
            <el-button type="danger" size="mini" @click="deleteBooktype(scope.row.id)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
      <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="pageData.pageNumber"
          :page-sizes="[5, 10, 15, 20]"
          :page-size="pageData.pageSize"
          layout="total, sizes, prev, pager, next, jumper"
          :total="pageData.totalRow">
      </el-pagination>

      <el-dialog title="添加类型" :visible.sync="addFormVisible">
        <el-form ref="addForm" :rules="addBooktypeRules" :model="addBooktype" hide-required-asterisk label-width="150px"
                 size="mini">
          <el-form-item label="图书类型" prop="name">
            <el-input v-model="addBooktype.name"></el-input>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="addFormVisible=false">取 消</el-button>
          <el-button type="primary" @click="submitAddForm('addForm')">确 定</el-button>
        </div>
      </el-dialog>


      <el-dialog title="修改类型" :visible.sync="updateFormVisible">
        <el-form ref="updateForm" :rules="updateBooktypeRules" :model="updateBooktype" hide-required-asterisk
                 label-width="200px" size="mini">
          <el-form-item label="图书类型" prop="name">
            <el-input v-model="updateBooktype.name"></el-input>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="updateFormVisible=false">取 消</el-button>
          <el-button type="primary" @click="submitUpdateForm('updateForm')">确 定</el-button>
        </div>
      </el-dialog>

    </el-card>
  </div>

</template>

<script>
export default {
  name: "BooktypeList",
  data() {
    return {
      booktypeList: [],
      keyword: "",
      pageData: {
        pageNumber: 1,
        pageSize: 5,
        totalPage: 0,
        totalRow: 0
      },
      addBooktype: {},
      addFormVisible: false,
      addBooktypeRules: {
        name: [
          {required: true, message: "类型不能为空", trigger: "blur"}
        ]
      },
      updateBooktype: {},
      updateFormVisible: false,
      updateBooktypeRules: {
        name: [
          {required: true, message: "类型不能为空", trigger: "blur"}
        ]
      },
    }
  },
  methods: {
    initBooktypeList() {
      let params = {
        pageNumber: this.pageData.pageNumber,
        pageSize: this.pageData.pageSize,
        keyword: this.keyword
      }
      this.$axios.postForm("/booktype/query", params)
          .then(response => {
            if (response.data.code === 200) {
              this.pageData = response.data.data
              this.booktypeList = response.data.data.records;
            } else {
              this.$message.error(response.data.message)
            }
          })
    },
    handleSizeChange(size) {
      this.pageData.pageNumber = 1;
      this.pageData.pageSize = size;
      this.initBooktypeList();
    },
    handleCurrentChange(current) {
      this.pageData.pageNumber = current;
      this.initBooktypeList();
    },
    resetForm(formName) {
      this.$refs[formName].resetFields();
    },
    showAddForm() {
      this.addFormVisible = true
    },
    submitAddForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.$axios.postForm("/booktype/add", this.addBooktype)
              .then(response => {
                if (response.data.code === 200) {
                  this.$message.success("添加成功")
                  this.addFormVisible = false
                  this.initBooktypeList();
                }
              })
        }
      })
    },
    showUpdateForm(booktype) {
      this.updateBooktype = booktype
      this.updateFormVisible = true
    },
    submitUpdateForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.$axios.postForm("/booktype/update", this.updateBooktype)
              .then(response => {
                if (response.data.code === 200) {
                  this.$message.success("修改成功")
                  this.updateFormVisible = false
                  this.initBooktypeList();
                }else {
                  this.$message.error(response.data.message)
                }
              })
        }
      })
    },
    deleteBooktype(id) {
      this.$confirm('此操作将永久删除该类型,是否继续?', '警告', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.$axios.delete("/booktype/delete/" + id)
            .then(response => {
              if (response.data.code == 200) {
                this.$message.success("删除成功")
                this.initBooktypeList();
              } else {
                this.$message.error(response.data.message)
              }
            })
      }).catch(() => {
        this.$message.info("已取消删除")
      })
    }
  },
  created() {
    this.initBooktypeList();
  }

}
</script>

<style scoped>

</style>