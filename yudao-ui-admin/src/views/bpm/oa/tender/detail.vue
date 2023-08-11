<template>
  <div class="app-container">
    <!-- 对话框(添加 / 修改) -->
      <el-form ref="form" :model="form" label-width="100px" label-position="left">
        <el-form-item label="项目名称：" prop="projectName">
          {{ form.projectName }}
        </el-form-item>

        <el-form-item label="预采购产品：" prop="projectProduct">
          {{ form.projectProduct }}
        </el-form-item>

        <el-form-item label="金额（万）：" prop="projectMoney">
          {{ form.projectMoney }}
        </el-form-item>

        <el-form-item label="招标日期" prop="projectDate">
          {{parseTime(form.projectDate, '{y}-{m}-{d}')}}
        </el-form-item>

        <el-form-item label="客户名称：" prop="customerName">
          {{ form.customerName }}
        </el-form-item>
        <el-form-item label="联系人：" prop="contactName">
          {{ form.contactName }}
        </el-form-item>
        <el-form-item label="手机号：" prop="contactPhone">
          {{ form.contactPhone }}
        </el-form-item>
        <el-form-item label="职务：" prop="contactJob">
          {{ form.contactJob }}
        </el-form-item>
        <el-form-item label="发起人：" prop="nickname">
          {{ form.nickname }}
        </el-form-item>
      </el-form>
  </div>
</template>

<script>
import { getTender}  from "@/api/bpm/tender"
export default {
  name: "BpmOATenderDetail",
  components: {
  },
  props: {
    id: {
      type: [String, Number],
      default: undefined
    },
  },
  data() {
    return {
      tenderId: undefined, // 项目报备编号
      // 表单参数
      form: {
        projectName: undefined,
        projectProduct: undefined,
        projectMoney: undefined,
        projectDate: undefined,
        customerName: undefined,
        contactName: undefined,
        contactPhone: undefined,
        contactJob: undefined,
      },

    };
  },
  created() {
    this.tenderId = this.id || this.$route.query.id;
    if (!this.tenderId) {
      this.$message.error('未传递 id 参数，无法查看 OA 项目报备信息');
      return;
    }
    this.getDetail();
  },
  methods: {
    /** 获得项目报备信息 */
    getDetail() {
      getTender(this.tenderId).then(response => {
        this.form = response.data;
      });
    },
  }
};
</script>
