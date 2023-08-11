<template>
  <div class="app-container">
    <!-- 对话框(添加 / 修改) -->
    <el-form ref="form" :model="form" :rules="rules" label-width="110px" label-position="left">

      <el-form-item label="项目名称：" prop="projectName">
        <el-col :span="40">
          <el-input type="text" :rows="1" v-model="form.projectName" placeholder="请输入项目名称" maxlength="128" />
        </el-col>
      </el-form-item>

      <el-form-item label="预采购产品：" prop="projectProduct">
        <el-col :span="40">
          <el-input type="text" :rows="1" v-model="form.projectProduct" placeholder="请输入预采购产品" maxlength="128" />
        </el-col>
      </el-form-item>

      <el-form-item label="金额（万）：" prop="projectMoney">
        <el-col :span="40">
          <el-input type="text" :rows="1" v-model="form.projectMoney" placeholder="请输入金额" maxlength="128" />
        </el-col>
      </el-form-item>

      <el-form-item label="招标日期" prop="projectDate">
          <el-date-picker clearable size="small" v-model="form.projectDate" type="date" value-format="timestamp" placeholder="选择招标日期" />
      </el-form-item>

      <el-form-item label="客户名称：" prop="customerName">
        <el-col :span="40">
          <el-input type="text" :rows="1" v-model="form.customerName" placeholder="请输入客户名称" maxlength="128" />
        </el-col>
      </el-form-item>
      <el-form-item label="联系人：" prop="contactName">
        <el-col :span="40">
          <el-input type="text" :rows="1" v-model="form.contactName" placeholder="请输入联系人" maxlength="20" />
        </el-col>
      </el-form-item>
      <el-form-item label="手机号：" prop="contactPhone">
        <el-col :span="40">
          <el-input type="text" :rows="1" v-model="form.contactPhone" placeholder="请输入手机号" maxlength="20" />
        </el-col>
      </el-form-item>
      <el-form-item label="职务：" prop="contactJob">
        <el-col :span="40">
          <el-input type="text" :rows="1" v-model="form.contactJob" placeholder="请输入职务" maxlength="20" />
        </el-col>
      </el-form-item>

      <el-form-item>
        <el-button type="primary" @click="submitForm">提 交</el-button>
      </el-form-item>
    </el-form>
    <el-card class="box-card">
      <div slot="header" class="clearfix">
        <span class="el-icon-picture-outline">审批流程</span>
      </div>
      <el-col>
        <div class="block">
          <el-timeline>
            <el-timeline-item icon='el-icon-time' type='primary'>
              <p style="font-weight: 700">任务：一级代理审批</p>
              <el-card :body-style="{ padding: '10px' }">
                <label style="font-weight: normal; margin-right: 30px;">
                  审批人：{{ firstName }}
                </label>
              </el-card>
            </el-timeline-item>
            <el-timeline-item icon='el-icon-time' type='primary'>
              <p style="font-weight: 700">任务：区域厂家负责人审批</p>
              <el-card :body-style="{ padding: '10px' }">
                <label style="font-weight: normal; margin-right: 30px;">
                  审批人：{{ secondName }}
                </label>
              </el-card>
            </el-timeline-item>
          </el-timeline>
        </div>
      </el-col>
    </el-card>
    <el-card class="box-card">
      <div slot="header" class="clearfix">
        <span class="el-icon-picture-outline">流程图</span>
      </div>
      <my-process-viewer key="designer" v-model="bpmnXML" v-bind="bpmnControlForm" />
    </el-card>
  </div>
</template>

<script>
import { createTender, getApprovalUser }  from "@/api/bpm/tender"
import {getModel} from "@/api/bpm/model";
import store from "@/store";

export default {
  name: "BpmOALeaveCreate",
  components: {
  },
  data() {
    var validatePhone = (rule, value, callback) => {
      let re = /((\d{11})|^((\d{7,8})|(\d{4}|\d{3})-(\d{7,8})|(\d{4}|\d{3})-(\d{7,8})-(\d{4}|\d{3}|\d{2}|\d{1})|(\d{7,8})-(\d{4}|\d{3}|\d{2}|\d{1}))$)/
      if (!re.test(value)) {
        callback(new Error('请输入11位手机号或者电话号码，如0596-3107777-1234'));
      } else {
        callback();
      }
    };
    return {
      // BPMN 数据
      bpmnXML: null,
      bpmnControlForm: {
        prefix: "flowable"
      },
      firstName: '一级',
      secondName: '省级',
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
      // 表单校验
      rules: {
        projectName: [{ required: true, message: "项目名称不能为空", trigger: "change" }],
        projectProduct: [{ required: true, message: "产品不能为空", trigger: "change" }],
        projectMoney: [{ required: true, message: "金额不能为空", trigger: "change" }],
        projectDate: [{ required: true, message: "招标日期不能为空", trigger: "blur" }],
        customerName: [{ required: true, message: "客户名称不能为空", trigger: "change" }],
        contactName: [{ required: true, message: "联系人不能为空", trigger: "change" }],
        contactPhone: [
          { required: true, message: "手机号不能为空", trigger: "change" },
          { validator: validatePhone, trigger: 'change' }
        ],
      },

    };
  },
  created() {
    getApprovalUser(store.getters.userId).then(response => {
      this.firstName = response.data.firstName;
      this.secondName = response.data.secondName;
    });
    getModel('f8ead454-368a-11ee-a664-00d8612d271b').then(response => {
      this.bpmnXML = response.data.bpmnXml
    });
  },
  methods: {
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (!valid) {
          return;
        }

        // 添加的提交
        createTender(this.form).then(response => {
          this.$modal.msgSuccess("发起成功");
          this.$tab.closeOpenPage({ path: "/bpm/oa/tender" });
        });
      });
    }
  }
};
</script>
<style lang="scss">
.my-process-designer {
  height: calc(100vh - 200px);
}

.box-card {
  width: 100%;
  margin-bottom: 20px;
}
</style>
