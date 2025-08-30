<template>
	<fb-page-header
		title="企业承诺上报"
		sub-title=""
	>


		<fb-card header="企业承诺上报">


			<fb-container>

				<fb-text :color="colors.grey_9" size="xl">浙江汇金商贸有限公司</fb-text>

				<fb-container mt="16px" mb="24px" padding="16px" :background="colors.blue_1" radius="4px">
					<fb-property colon :split="1">
						<fb-property-item label="行政区划">XX镇XX村</fb-property-item>
						<fb-property-item label="行政区划">XX镇XX村</fb-property-item>
						<fb-property-item label="行政区划">XX镇XX村</fb-property-item>
					</fb-property>
				</fb-container>

				<fb-form label-position="top" label-width="500"
						 :content-style="{marginLeft: '24px'}">

					<fb-form-item label="1、前一工作日是否发生工伤事故？" prop="xxx" :rule="{required: true}">
						<fb-radio-group :data="[{value:'1', label:'是'}, {value:'0', label:'否'}]"></fb-radio-group>
					</fb-form-item>

					<fb-form-item label="1、前一工作日是否发生工伤事故？" prop="xxx" :rule="{required: true}">
						<fb-radio-group vertical
										:data="[{value:'1', label:'是'}, {value:'0', label:'否'}]"></fb-radio-group>
					</fb-form-item>

					<fb-form-item label="3、当天是否开展安全生产隐患排查？" prop="xxx" :rule="{required: true}">
						<fb-radio-group vertical
										:data="[{value:'1', label:'是'}, {value:'0', label:'否'}]"></fb-radio-group>
					</fb-form-item>


					<fb-container pl="24px">
						<fb-divider margin-size="xs"></fb-divider>
						<fb-form-item label="隐患排查部位" prop="xxx" :rule="{required: true}"
									  :content-style="{marginLeft: '0'}">
							<template slot="label-extra">
								<fb-tag label="多选" type="warning" effect="light"></fb-tag>
							</template>
							<fb-checkbox-group vertical
											   :data="[{value:'1', label:'是'}, {value:'0', label:'否'}]"></fb-checkbox-group>
						</fb-form-item>
						<fb-form-item prop="xxx" :rule="{required: true}" :content-style="{marginLeft: '0'}">
							<fb-input placeholder="请输入"></fb-input>
						</fb-form-item>
					</fb-container>

					<fb-form-item label="4、值班人员在岗情况？" prop="xxx" :rule="{required: true, message: '请输入值班人数'}">
						<fb-input placeholder="请输入值班人数">
							<template slot="append">人</template>
						</fb-input>
					</fb-form-item>

					<fb-form-item prop="xxx" :rule="{required: true, message: '请输入值班人数'}">
						<fb-input placeholder="请输入值班人员姓名，多个名字中间用“逗号”隔开"></fb-input>
					</fb-form-item>

					<fb-form-item prop="xxx_12" :rule="{required: true, message: '请输入值班人数'}">
						<fb-input placeholder="请输入值班人员姓名，多个名字中间用“逗号”隔开"></fb-input>
					</fb-form-item>

					<fb-form-item label="12、当天是否有特殊作业？" prop="xxx_12" :rule="{required: true, message: '请选择当天是否有特殊作业'}">
						<fb-radio-group vertical
										:data="[{value:'1', label:'是'}, {value:'0', label:'否'}]"></fb-radio-group>
					</fb-form-item>

					<fb-container pl="24px" mb="24px">
						<fb-divider margin-size="xs"></fb-divider>
						<fb-form-item label="作业列表" prop="xxx_12_list"
									  :rule="{required: true, message: '请选择当天是否有特殊作业'}"
									  :content-style="{marginLeft: '0'}"
						>
							<template slot="label-extra">
								<fb-tag label="多选" type="warning" effect="light"></fb-tag>
							</template>
							<fb-checkbox>动火作业</fb-checkbox>
						</fb-form-item>

						<fb-container padding="12px 0 0 24px" :background="colors.grey_2">
							<fb-form-item label="是否审批" prop="xxx_12_list"
										  :rule="{required: true, message: '请选择当天是否有特殊作业'}"
										  :content-style="{marginLeft: '0'}"
							>
								<fb-radio-group vertical
												:data="[{value:'1', label:'是'}, {value:'0', label:'否'}]"></fb-radio-group>
							</fb-form-item>
						</fb-container>

						<fb-form-item prop="xxx_12_list"
									  :rule="{required: true, message: '请选择当天是否有特殊作业'}"
									  :content-style="{marginLeft: '0', paddingBottom: 0, marginTop: '12px'}"
						>
							<fb-checkbox>临时用电作业</fb-checkbox>
						</fb-form-item>
						<fb-container padding="12px 0 0 24px" :background="colors.grey_2">
							<fb-form-item label="是否审批" prop="xxx_12_list"
										  :rule="{required: true, message: '请选择当天是否有特殊作业'}"
										  :content-style="{marginLeft: '0'}"
							>
								<fb-radio-group vertical
												:data="[{value:'1', label:'是'}, {value:'0', label:'否'}]"></fb-radio-group>
							</fb-form-item>
						</fb-container>

					</fb-container>


					<fb-form-item label="14、当天化工生产设施是否运行？" prop="xxx_14"
								  :rule="{required: true, message: '当天化工生产设施是否运行'}">
						<fb-radio-group vertical
										:data="[{value:'1', label:'是'}, {value:'0', label:'否'}, {value:'2', label:'不涉及'}]"></fb-radio-group>
					</fb-form-item>

					<fb-container pl="24px" mb="24px">
						<fb-divider margin-size="xs"></fb-divider>
						<fb-form-item label="运行套数" prop="xxx_14" :rule="{required: true, message: '请输入运行套数'}"
									  :content-style="{marginLeft: '0'}">
							<fb-input placeholder="请输入运行套数">
								<template slot="append">套</template>
							</fb-input>
						</fb-form-item>
					</fb-container>


				</fb-form>


			</fb-container>


		</fb-card>
	</fb-page-header>

</template>

<script>

export default {
	components: {},
	data () {
		return {}

	},

	methods: {},
	mounted () {

	},

}
</script>

<style scoped lang="less">

</style>
