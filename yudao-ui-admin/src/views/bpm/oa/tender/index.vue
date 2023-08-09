<template>
  <div class="app-container">
    <doc-alert title="工作流" url="https://doc.iocoder.cn/bpm" />

    <!-- 搜索工作栏 -->
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="申请时间" prop="createTime">
        <el-date-picker v-model="queryParams.createTime" style="width: 240px" value-format="yyyy-MM-dd HH:mm:ss" type="daterange"
                        range-separator="-" start-placeholder="开始日期" end-placeholder="结束日期" :default-time="['00:00:00', '23:59:59']" />
      </el-form-item>
      <el-form-item label="结果" prop="result">
        <el-select v-model="queryParams.result" placeholder="请选择流结果" clearable>
          <el-option v-for="dict in this.getDictDatas(DICT_TYPE.BPM_PROCESS_INSTANCE_RESULT)"
                     :key="dict.value" :label="dict.label" :value="dict.value"/>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <!-- 操作工具栏 -->
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" plain icon="el-icon-plus" size="mini"
                   v-hasPermi="['bpm:oa-tender:create']" @click="handleAdd">发起投标</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <!-- 列表 -->
    <el-table v-loading="loading" :data="list">
      <el-table-column label="申请编号" align="center" prop="id" />
      <el-table-column label="状态" align="center" prop="result">
        <template v-slot="scope">
          <dict-tag :type="DICT_TYPE.BPM_PROCESS_INSTANCE_RESULT" :value="scope.row.result"/>
        </template>
      </el-table-column>
      <el-table-column label="发起人" align="center" prop="nickname" width="120" />
      <el-table-column label="项目名称" align="center" prop="projectName" />
      <el-table-column label="预采购产品" align="center" prop="projectProduct" />
      <el-table-column label="金额（万）" align="center" prop="projectMoney" />
      <el-table-column label="招标日期" align="center" prop="projectDate" width="180">
        <template v-slot="scope">
          <span>{{ parseTime(scope.row.projectDate) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="客户名称" align="center" prop="customerName" />
      <el-table-column label="联系人" align="center" prop="contactName" />
      <el-table-column label="手机号" align="center" prop="contactPhone" />
      <el-table-column label="职务" align="center" prop="contactJob" />

      <el-table-column label="申请时间" align="center" prop="applyTime" width="180">
        <template v-slot="scope">
          <span>{{ parseTime(scope.row.createTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width" width="200">
        <template v-slot="scope">
          <el-button size="mini" type="text" icon="el-icon-delete" @click="handleCancel(scope.row)"
                     v-hasPermi="['bpm:oa-tender:create']" v-if="scope.row.result === 1">取消投标</el-button>
          <el-button size="mini" type="text" icon="el-icon-view" @click="handleDetail(scope.row)"
                     v-hasPermi="['bpm:oa-tender:query']">详情</el-button>
          <el-button size="mini" type="text" icon="el-icon-edit" @click="handleProcessDetail(scope.row)">审批进度</el-button>
        </template>
      </el-table-column>
    </el-table>
    <!-- 分页组件 -->
    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNo" :limit.sync="queryParams.pageSize"
                @pagination="getList"/>

  </div>
</template>

<script>
import { getTenderPage } from "@/api/bpm/tender"
import { getDictDatas, DICT_TYPE } from '@/utils/dict'
import {cancelProcessInstance} from "@/api/bpm/processInstance";

export default {
  name: "BpmOATender",
  components: {
  },
  data() {
    return {
      // 遮罩层
      loading: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 投标申请列表
      list: [],
      // 查询参数
      queryParams: {
        pageNo: 1,
        pageSize: 10,
        result: null,
        createTime: []
      },

      leaveResultData: getDictDatas(DICT_TYPE.BPM_PROCESS_INSTANCE_RESULT),
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询列表 */
    getList() {
      this.loading = true;
      // 执行查询
      getTenderPage(this.queryParams).then(response => {
        this.list = response.data.list;
        this.total = response.data.total;
        this.loading = false;
      });
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNo = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.$router.push({ name: "BpmOATenderCreate"});
    },
    /** 详情按钮操作 */
    handleDetail(row) {
      this.$router.push({ name: "BpmOATenderDetail", query: { id: row.id}});
    },
    /** 查看审批进度的操作 */
    handleProcessDetail(row) {
      this.$router.push({ name: "BpmProcessInstanceDetail", query: { id: row.processInstanceId}});
    },
    /** 取消投标 */
    handleCancel(row) {
      const id = row.processInstanceId;
      this.$prompt('请输入取消原因？', "取消流程", {
        type: 'warning',
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        inputPattern: /^[\s\S]*.*\S[\s\S]*$/, // 判断非空，且非空格
        inputErrorMessage: "取消原因不能为空",
      }).then(({ value }) => {
        return cancelProcessInstance(id, value);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("取消成功");
      })
    }
  }
};
</script>
