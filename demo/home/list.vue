<template>
    <div>
        <fb-flex gap="8px" direction-column class="tab-list">

            <fb-flex grid cols3 gap="8px">
                <div class="card-banner orange">
                    <div class="icon">
                        <fb-icon size="32" color="#fff" name="accident-information-fill"></fb-icon>
                    </div>
                    <div class="icon-right">
                        <fb-icon size="60" color="#FC9C3B26" name="accident-information-fill"></fb-icon>
                    </div>
                    <div class="icon-arrow"></div>
                    <div class="title">突发事件</div>
                    <div class="value">
                        <span v-if="accident.accidentNum > 0" class="num clickable" @click="clickAccident({})">
                           {{ accident.accidentNum }}
                        </span>
                        <span v-else class="num">
                           {{ accident.accidentNum }}
                        </span>
                        <span class="unit">起</span>
                    </div>
                </div>
                <div class="card-banner blue">
                    <div class="icon">
                        <fb-icon size="32" color="#fff" name="emergency-team-fill"></fb-icon>
                    </div>
                    <div class="icon-right">
                        <fb-icon size="60" color="#6773F426" name="emergency-team-fill"></fb-icon>
                    </div>
                    <div class="icon-arrow"></div>
                    <div class="title">应急队伍</div>
                    <div class="value">
                        <span v-if="rank.rankNum > 0" class="num clickable" @click="clickTeam({})">
                           {{ rank.rankNum }}
                        </span>
                        <span v-else class="num">
                           {{ rank.rankNum }}
                        </span>
                        <span class="unit">个</span>
                    </div>
                </div>
                <div class="card-banner purple">
                    <div class="icon">
                        <fb-icon size="32" color="#fff" name="risk-information-fill"></fb-icon>
                    </div>
                    <div class="icon-right">
                        <fb-icon size="60" color="#6773F426" name="risk-information-fill"></fb-icon>
                    </div>
                    <div class="icon-arrow"></div>
                    <div class="title">应急预案</div>
                    <div class="value">
                        <span v-if="reserve.reserveNum > 0" class="num clickable" @click="clickReserve({})">
                           {{ reserve.reserveNum }}
                        </span>
                        <span v-else class="num">
                           {{ reserve.reserveNum }}
                        </span>
                        <span class="unit">个</span>
                    </div>
                </div>
            </fb-flex>

            <fb-flex grid cols3 gap="8px">

                <div class="card-wrapper">
                    <div class="header">
                        <fb-icon size="19" color="#0284FE" name="accident-information-fill"></fb-icon>
                        <span class="title">突发事件分布-事件类型</span>
                    </div>
                    <div class="body">
                        <fb-flex grid cols4 gap="4px">
                            <fb-flex class="card-board orange">
                                <span class="title fs-14">生产安全事故</span>
                                <span v-if="accident.scaqNum > 0" class="value clickable"
                                      @click="clickAccident({'safetyAccidentType':'ACC0201'})">
                                   {{ accident.scaqNum }}
                                </span>
                                <span v-else class="value">{{ accident.scaqNum }}</span>
                            </fb-flex>
                            <fb-flex class="card-board orange">
                                <span class="title fs-14">自然灾害事件</span>
                                <span v-if="accident.zrzhNum > 0" class="value clickable"
                                      @click="clickAccident({'safetyAccidentType':'ACC0202'})">
                                   {{ accident.zrzhNum }}
                                </span>
                                <span v-else class="value">{{ accident.zrzhNum }}</span>
                            </fb-flex>
                            <fb-flex class="card-board orange">
                                <span class="title fs-14">公共卫生事件</span>
                                <span v-if="accident.ggwsNum > 0" class="value clickable"
                                      @click="clickAccident({'safetyAccidentType':'ACC0203'})">
                                   {{ accident.ggwsNum }}
                                </span>
                                <span v-else class="value">{{ accident.ggwsNum }}</span>
                            </fb-flex>
                            <fb-flex class="card-board orange">
                                <span class="title fs-14">社会安全事件</span>
                                <span v-if="accident.shaqNum > 0" class="value clickable"
                                      @click="clickAccident({'safetyAccidentType':'ACC0204'})">
                                   {{ accident.shaqNum }}
                                </span>
                                <span v-else class="value">{{ accident.shaqNum }}</span>
                            </fb-flex>
                        </fb-flex>
                    </div>
                </div>
                <div class="card-wrapper">
                    <div class="header">
                        <fb-icon size="19" color="#0284FE" name="emergency-team-fill"></fb-icon>
                        <span class="title">应急队伍分布</span>
                    </div>
                    <div class="body">
                        <fb-flex grid cols3 gap="4px">
                            <fb-flex class="card-board blue">
                                <span class="title">队伍成员总数</span>
                                <span class="value"> {{ rank.rankPsnNum }}</span>
                            </fb-flex>
                            <fb-flex class="card-board blue">
                                <span class="title">专职应急队伍</span>
                                <span v-if="rank.zrNum > 0" class="value clickable"
                                      @click="clickTeam({'rankType':'C0801'})">
                                 {{ rank.zrNum }}
                               </span>
                                <span v-else class="value">
                                {{ rank.zrNum }}
                               </span>
                            </fb-flex>
                            <fb-flex class="card-board blue">
                                <span class="title">兼职应急队伍</span>
                                <span v-if="rank.jzNum > 0" class="value clickable"
                                      @click="clickTeam({'rankType':'C0803'})">
                                 {{ rank.jzNum }}
                               </span>
                                <span v-else class="value">
                                {{ rank.jzNum }}
                               </span>
                            </fb-flex>
                        </fb-flex>
                    </div>
                </div>
                <div class="card-wrapper">
                    <div class="header">
                        <fb-icon size="19" color="#0284FE" name="risk-information-fill"></fb-icon>
                        <span class="title">应急预案分布</span>
                    </div>
                    <div class="body">
                        <fb-flex grid cols4 gap="4px">
                            <fb-flex class="card-board purple">
                                <span class="title fs-14">新增预案</span>
                                <span v-if="reserve.addNum > 0" class="value purple clickable"
                                      @click="clickReserve({'reviseStatus':'1'})">
                                 {{ reserve.addNum }}
                                </span>
                                <span v-else class="value purple">
                                 {{ reserve.addNum }}
                                </span>
                            </fb-flex>
                            <fb-flex class="card-board purple">
                                <span class="title fs-14">修订预案</span>
                                <span v-if="reserve.updateNum > 0" class="value purple clickable"
                                      @click="clickReserve({'reviseStatus':'2'})">
                                 {{ reserve.updateNum }}
                                </span>
                                <span v-else class="value purple">
                                 {{ reserve.updateNum }}
                                </span>
                            </fb-flex>
                            <fb-flex class="card-board purple">
                                <span class="title fs-14">预案即将到期</span>
                                <span v-if="reserve.jjdqNum > 0" class="value orange clickable"
                                      @click="clickReserve({'reserveStatus': 2})">
                                 {{ reserve.jjdqNum }}
                                </span>
                                <span v-else class="value orange">
                                 {{ reserve.jjdqNum }}
                                </span>
                            </fb-flex>
                            <fb-flex class="card-board purple">
                                <span class="title fs-14">预案已到期</span>
                                <span v-if="reserve.ydqNum > 0" class="value red clickable"
                                      @click="clickReserve({'reserveStatus': 3})">
                                 {{ reserve.ydqNum }}
                                </span>
                                <span v-else class="value red">
                                 {{ reserve.ydqNum }}
                                </span>
                            </fb-flex>
                        </fb-flex>
                    </div>
                </div>

            </fb-flex>

            <fb-flex grid cols3 gap="8px">

                <div class="card-wrapper">
                    <div class="header">
                        <fb-icon size="19" color="#0284FE" name="chart-multi-donut-fill"></fb-icon>
                        <span class="title">突发事件按事件等级的分布</span>
                    </div>
                    <div class="body">
                        <ring-legend-custom-linear theme="fbecLight" @click="(events)=>{ }" ref="ec_1_1"
                                                   dot-size="8" unit="家"
                                                   legend-placement="right"
                                                   :chart-style="{
                            left: '8px',
                    }"
                                                   :legend-style="{
                        flex: '1 1 auto'            ,
                        gap: '9px',
                         maxHeight: '200px',
                         overflowY: 'auto',
                         paddingRight: '8px',
                    }"
                        >

                            <template #legend="{props}">
                                <fb-flex height="26px" ai-center radius="4px"
                                         pr="12px" @click="clickAccident({'accidentType': props.legend.accidentType})"
                                >
                                    <fb-flex width="34px" align-center>
                                        <fb-badge dot dot-size="8" :dot-color="props.color"/>
                                    </fb-flex>
                                    <fb-flex flex="1" min-width="30px">
                                        <fb-text size="14" color="#313C47" ellipsis>{{ props.title }}</fb-text>
                                    </fb-flex>
                                    <fb-flex width="84px" jc-end ai-center>
                                        <fb-text size="18" color="#313C47" family="DINPro">{{ props.value }}</fb-text>
                                        <fb-text size="13" color="#313C47" ml="6px">{{ props.legend.unit }}</fb-text>
                                    </fb-flex>
                                </fb-flex>
                            </template>

                        </ring-legend-custom-linear>
                    </div>
                </div>
                <div class="card-wrapper">
                    <div class="header">
                        <fb-icon size="19" color="#0284FE" name="chart-multi-donut-fill"></fb-icon>
                        <span class="title">生产安全事故的分布</span>
                    </div>
                    <div class="body">
                        <ring-legend-custom-linear theme="fbecLight" @click="(events)=>{ }" ref="ec_1_2"
                                                   dot-size="8" unit="家"
                                                   legend-placement="right"
                                                   :chart-style="{
                            left: '8px',
                    }"
                                                   :legend-style="{
                        flex: '1 1 auto'            ,
                        gap: '9px',
                         maxHeight: '200px',
                         overflowY: 'auto',
                         paddingRight: '8px',
                    }"
                        >

                            <template #legend="{props}">
                                <fb-flex height="26px" ai-center radius="4px"
                                         pr="12px"
                                         @click="clickAccident({'safetyAccidentType': props.legend.safetyAccidentType})"
                                >
                                    <fb-flex width="34px" align-center>
                                        <fb-badge dot dot-size="8" :dot-color="props.color"/>
                                    </fb-flex>
                                    <fb-flex flex="1" min-width="30px">
                                        <fb-text size="14" color="#313C47" ellipsis>{{ props.title }}</fb-text>
                                    </fb-flex>
                                    <fb-flex width="84px" jc-end ai-center>
                                        <fb-text size="18" color="#313C47" family="DINPro">{{ props.value }}</fb-text>
                                        <fb-text size="13" color="#313C47" ml="6px">起</fb-text>
                                    </fb-flex>
                                </fb-flex>
                            </template>

                        </ring-legend-custom-linear>
                    </div>
                </div>
                <div class="card-wrapper">
                    <div class="header">
                        <fb-icon size="19" color="#0284FE" name="chart-multi-donut-fill"></fb-icon>
                        <span class="title">应急预案按预案类别的分布</span>
                    </div>
                    <div class="body">
                        <ring-legend-custom-linear theme="fbecLight" @click="(events)=>{ }" ref="ec_1_3"
                                                   dot-size="8" unit="家"
                                                   legend-placement="right"
                                                   :chart-style="{
                            left: '8px',
                    }"
                                                   :legend-style="{
                        flex: '1 1 auto'            ,
                        gap: '9px',
                    }"
                        >

                            <template #legend="{props}">
                                <fb-flex height="26px" ai-center radius="4px"
                                         pr="12px" @click="clickReserve({'reserveType': props.legend.reserveType})"

                                >
                                    <fb-flex width="34px" align-center>
                                        <fb-badge dot dot-size="8" :dot-color="props.color"/>
                                    </fb-flex>
                                    <fb-flex flex="1" min-width="30px">
                                        <fb-text size="14" color="#313C47" ellipsis>{{ props.title }}</fb-text>
                                    </fb-flex>
                                    <fb-flex width="84px" jc-end ai-center>
                                        <fb-text size="18" color="#313C47" family="DINPro">{{ props.value }}</fb-text>
                                        <fb-text size="13" color="#313C47" ml="6px">{{ props.legend.unit }}</fb-text>
                                    </fb-flex>
                                </fb-flex>
                            </template>

                        </ring-legend-custom-linear>
                    </div>
                </div>
            </fb-flex>

            <fb-flex grid cols3 gap="8px">
                <div class="card-banner orange">
                    <div class="icon">
                        <fb-icon size="32" color="#fff" name="emergency-evacuation-fill"></fb-icon>
                    </div>
                    <div class="icon-right">
                        <fb-icon size="60" color="#FC9C3B26" name="emergency-evacuation-fill"></fb-icon>
                    </div>
                    <div class="icon-arrow"></div>
                    <div class="title">应急演练</div>
                    <div class="value">
                        <span v-if="replay.replayNum > 0" class="num clickable"
                              @click="clickReplay({})">
                                 {{ replay.replayNum }}
                        </span>
                        <span v-else class="num">
                                 {{ replay.replayNum }}
                        </span>
                        <span class="unit">家</span>
                    </div>
                </div>
                <div class="card-banner purple">
                    <div class="icon">
                        <fb-icon size="32" color="#fff" name="alarm-light-fill"></fb-icon>
                    </div>
                    <div class="icon-right">
                        <fb-icon size="60" color="#6773F426" name="alarm-light-fill"></fb-icon>
                    </div>
                    <div class="icon-arrow"></div>
                    <div class="title">应急设备</div>
                    <div class="value">
                        <span v-if="material.materialNum > 0" class="num clickable"
                              @click="clickMaterial({})">
                                 {{ material.materialNum }}
                        </span>
                        <span v-else class="num">
                                 {{ material.materialNum }}
                        </span>
                        <span class="unit">家</span>
                    </div>
                </div>
                <div class="card-banner blue">
                    <div class="icon">
                        <fb-icon size="32" color="#fff" name="emergency-supplies-fill"></fb-icon>
                    </div>
                    <div class="icon-right">
                        <fb-icon size="60" color="#6773F426" name="emergency-supplies-fill"></fb-icon>
                    </div>
                    <div class="icon-arrow"></div>
                    <div class="title">应急物资检维保</div>
                    <div class="value">
                        <span v-if="escape.escapeNum > 0" class="num clickable"
                              @click="clickEscape({})">
                                 {{ escape.escapeNum }}
                        </span>
                        <span v-else class="num">
                                 {{ escape.escapeNum }}
                        </span>
                        <span class="unit">家</span>
                    </div>
                </div>
            </fb-flex>

            <fb-flex grid cols3 gap="8px">

                <div class="card-wrapper">
                    <div class="header">
                        <fb-icon size="19" color="#0284FE" name="emergency-evacuation-fill"></fb-icon>
                        <span class="title">应急演练分布</span>
                    </div>
                    <div class="body">
                        <fb-flex grid cols2 gap="4px 4px">
                            <fb-flex class="card-board h-46 orange" flex-direction="row" jc-space-between px="10px">
                                <span class="title fs-14">综合预案演练</span>
                                <span v-if="replay.zhya > 0" class="value clickable"
                                      @click="clickReplay({'planType':'0'})">
                                 {{ replay.zhya }}
                                </span>
                                <span v-else class="value">
                                 {{ replay.zhya }}
                                 </span>
                            </fb-flex>
                            <fb-flex class="card-board h-46 orange" flex-direction="row" jc-space-between px="10px">
                                <span class="title fs-14">专项预案演练</span>
                                <span v-if="replay.zxya > 0" class="value clickable"
                                      @click="clickReplay({'planType':'1'})">
                                 {{ replay.zxya }}
                                </span>
                                <span v-else class="value">
                                 {{ replay.zxya }}
                                 </span>
                            </fb-flex>
                            <fb-flex class="card-board h-46 orange" flex-direction="row" jc-space-between px="10px">
                                <span class="title fs-14">实战评估演练</span>
                                <span v-if="replay.szpg > 0" class="value clickable"
                                      @click="clickReplay({'replayType':1})">
                                 {{ replay.szpg }}
                                </span>
                                <span v-else class="value">
                                 {{ replay.szpg }}
                                 </span>
                            </fb-flex>
                            <fb-flex class="card-board h-46 orange" flex-direction="row" jc-space-between px="10px">
                                <span class="title fs-14">桌面评估演练</span>
                                <span v-if="replay.zmpg > 0" class="value clickable"
                                      @click="clickReplay({'replayType':0})">
                                 {{ replay.zmpg }}
                                </span>
                                <span v-else class="value">
                                 {{ replay.zmpg }}
                                 </span>
                            </fb-flex>
                        </fb-flex>
                    </div>
                </div>

                <div class="card-wrapper">
                    <div class="header">
                        <fb-icon size="19" color="#0284FE" name="alarm-light-fill"></fb-icon>
                        <span class="title">应急设备分布</span>
                    </div>
                    <div class="body">
                        <fb-flex grid cols5 gap="3px">
                            <fb-flex class="card-board h-96 purple">
                                <span class="title fs-14">物资</span>
                                <span v-if="material.wzNum > 0" class="value clickable"
                                      @click="clickMaterial({'materialType':'2'})">
                                 {{ material.wzNum }}
                                </span>
                                <span v-else class="value">
                                 {{ material.wzNum }}
                                 </span>
                            </fb-flex>
                            <fb-flex class="card-board h-96 purple">
                                <span class="title fs-14">设施</span>
                                <span v-if="material.ssNum > 0" class="value clickable"
                                      @click="clickMaterial({'materialType':'1'})">
                                 {{ material.ssNum }}
                                </span>
                                <span v-else class="value">
                                 {{ material.ssNum }}
                                 </span>
                            </fb-flex>
                            <fb-flex class="card-board h-96 purple">
                                <span class="title fs-14">装备</span>
                                <span v-if="material.zbNum > 0" class="value clickable"
                                      @click="clickMaterial({'materialType':'0'})">
                                 {{ material.zbNum }}
                                </span>
                                <span v-else class="value">
                                 {{ material.zbNum }}
                                 </span>
                            </fb-flex>
                            <fb-flex class="card-board h-96 purple">
                                <span class="title fs-14">即将到期</span>
                                <span v-if="material.jjdqNum > 0" class="value orange clickable"
                                      @click="clickMaterial({'status':'0'})">
                                 {{ material.jjdqNum }}
                                </span>
                                <span v-else class="value orange">
                                 {{ material.jjdqNum }}
                                 </span>
                            </fb-flex>
                            <fb-flex class="card-board h-96 purple">
                                <span class="title fs-14">已到期</span>
                                <span v-if="material.ydqNum > 0" class="value red clickable"
                                      @click="clickMaterial({'status':'1'})">
                                 {{ material.ydqNum }}
                                </span>
                                <span v-else class="value red">
                                 {{ material.ydqNum }}
                                 </span>
                            </fb-flex>
                        </fb-flex>
                    </div>
                </div>

                <div class="card-wrapper">
                    <div class="header">
                        <fb-icon size="19" color="#0284FE" name="emergency-supplies-fill"></fb-icon>
                        <span class="title">维保类型分布</span>
                    </div>
                    <div class="body">
                        <fb-flex grid cols3 gap="3px">
                            <fb-flex class="card-board h-96 blue">
                                <span class="title fs-14">维修</span>
                                <span v-if="escape.wxNum > 0" class="value clickable"
                                      @click="clickEscape({'type':2})">
                                 {{ escape.wxNum }}
                                </span>
                                <span v-else class="value">
                                 {{ escape.wxNum }}
                                 </span>
                            </fb-flex>
                            <fb-flex class="card-board h-96 blue">
                                <span class="title fs-14">保养</span>
                                <span v-if="escape.byNum > 0" class="value clickable"
                                      @click="clickEscape({'type':1})">
                                 {{ escape.byNum }}
                                </span>
                                <span v-else class="value">
                                 {{ escape.byNum }}
                                 </span>
                            </fb-flex>
                            <fb-flex class="card-board h-96 blue">
                                <span class="title fs-14">检查</span>
                                <span v-if="escape.jcNum > 0" class="value clickable"
                                      @click="clickEscape({'type':0})">
                                 {{ escape.jcNum }}
                                </span>
                                <span v-else class="value">
                                 {{ escape.jcNum }}
                                 </span>
                            </fb-flex>
                        </fb-flex>
                    </div>
                </div>

            </fb-flex>


            <fb-flex width="100%">
                <div class="card-wrapper">
                    <div class="header">
                        <fb-icon size="19" color="#0284FE" name="chart-bar2-fill"></fb-icon>
                        <span class="title">应急演练统计</span>
                        <fb-flex class="actions">
                            <fb-radio-group button :data="[
                                {label: '按本级', value: '1'},
                                {label: '按本级及下级', value: '2'},
                            ]"
                                            v-model="replayEntType"
                                            @change="clickReplayByEntType()">

                            </fb-radio-group>
                        </fb-flex>
                    </div>
                    <div class="body">
                        <FbecBarBase ref="ec_1_4" style="height: 240px; width: 100%;" @click="clickReplayByEnt"/>
                    </div>
                </div>
            </fb-flex>

        </fb-flex>
        <tp-dialog ref="TpDialog"></tp-dialog>
    </div>
</template>

<script>

import FbecBarBase from '@/components/echarts/bar-base/FbecBarBase.vue'
import FbecPieRing from '@/components/echarts/pie-ring/FbecPieRing.vue'
import RingLegendCustomLinear from '@/components/echarts/pie-ring/RingLegendCustomLinear.vue'

export default {
    name: 'tab-emergency-list',
    components: {
        RingLegendCustomLinear,
        FbecBarBase,
        FbecPieRing,
    },
    data() {
        return {
            formData: {},
            accident: {
                accidentNum: 0,
                scaqNum: 0,
                zrzhNum: 0,
                ggwsNum: 0,
                shaqNum: 0
            },
            rank: {
                rankNum: 0,
                rankPsnNum: 0,
                zrNum: 0,
                jzNum: 0,
            },
            reserve: {
                reserveNum: 0,
                jjdqNum: 0,
                ydqNum: 0,
                zhyaNum: 0,
                zxyaNum: 0,
                xcczyaNum: 0,
                addNum: 0,
                updateNum: 0,
            },
            replay: {
                replayNum: 0,
                zhya: 0,
                zxya: 0,
                zmpg: 0,
                szpg: 0,
            },
            material:{
                materialNum: 0,
                zbNum: 0,
                ssNum: 0,
                wzNum: 0,
                ydqNum: 0,
                jjdqNum: 0
            },
            escape: {
                escapeNum: 0,
                jcNum: 0,
                byNum: 0,
                wxNum: 0
            },
            replayEntType:'1',
            ec_ring_pie_label_config: {
                label: {
                    show: false,
                    padding: [-40, 0, 0, 0],
                    fontSize: '14',
                    formatter: [
                        //'{label|{b}}',
                        '{value|{c}}',
                        '{split|--------------}',
                        '{title|{b}}',
                    ].join('\n'),
                    rich: {
                        label: {},
                        value: {
                            fontFamily: 'DINPro-Bold',
                            fontSize: '32',
                            fontWeight: 500,
                            color: '#313C47FF',
                            backgroundColor: 'transparent',
                        },
                        split: {
                            fontSize: '12',
                            lineHeight: 0,
                            color: '#979797FF',
                            backgroundColor: 'transparent',
                        },
                        title: {
                            fontSize: '13',
                            color: '#313C47FF',
                            backgroundColor: 'transparent',
                        },
                    },

                },
                emphasis: {
                    scaleSize: 10,
                    scale: true,
                    label: {
                        show: true,
                        padding: [-40, 0, 0, 0],
                        fontSize: '14',
                        formatter: [
                            //'{label|{b}}',
                            '{value|{c}}',
                            '{split|--------------}',
                            '{title|{b}}',
                        ].join('\n'),
                        rich: {
                            label: {},
                            value: {
                                fontFamily: 'DINPro-Bold',
                                fontSize: '32',
                                fontWeight: 500,
                                color: '#313C47FF',
                                backgroundColor: 'transparent',
                            },
                            split: {
                                fontSize: '12',
                                lineHeight: 0,
                                color: '#979797FF',
                                backgroundColor: 'transparent',
                            },
                            title: {
                                fontSize: '13',
                                color: '#313C47FF',
                                backgroundColor: 'transparent',
                            },
                        },

                    },
                },
            },
        }
    },
    mounted() {
    },
    methods: {
        init() {
            this.$nextTick(() => {
                this.getAccidentNum();
                this.getAcctdentTypeNum();
                this.getRankNum();
                this.getReserveNum();
                this.getReplayNum();
                this.getMaterialNum();
                this.getEscapeNum();
                this.getReplayByEnt("1");
            })
        },
        notify(formData) {
            Object.assign(this.formData, formData)
            this.init()
        },
        getAccidentNum() {
            app.$svc.service.get('/homePage/emergencyCount/getAcctdentNum', this.formData)
                .then((result) => {
                    if (result.code == 1) {
                        this.accident = result.data
                        this.updateEc_1_1()
                    } else {
                        // 服务器返回失败
                        this.$message.error('查询失败: ' + result.message)
                    }
                });
        },
        getAcctdentTypeNum() {
            app.$svc.service.get('/homePage/emergencyCount/getAcctdentTypeNum', this.formData)
                .then((result) => {
                    if (result.code == 1) {
                        const obj = result.data.map(item => ({
                            value: item.accidentTypeNum,
                            showValue: item.accidentTypeNum,
                            name: item.accidentTypeName,
                            safetyAccidentType: item.accidentType,
                        }));
                        this.updateEc_1_2(obj)
                    } else {
                        // 服务器返回失败
                        this.$message.error('查询失败: ' + result.message)
                    }
                });
        },
        getRankNum() {
            app.$svc.service.get('/homePage/emergencyCount/getRankNum', this.formData)
                .then((result) => {
                    if (result.code == 1) {
                        this.rank = result.data
                    } else {
                        // 服务器返回失败
                        this.$message.error('查询失败: ' + result.message)
                    }
                });
        },
        getReserveNum() {
            app.$svc.service.get('/homePage/emergencyCount/getReserveNum', this.formData)
                .then((result) => {
                    if (result.code == 1) {
                        this.reserve = result.data
                        this.updateEc_1_3()
                    } else {
                        // 服务器返回失败
                        this.$message.error('查询失败: ' + result.message)
                    }
                });
        },
        getReplayNum() {
            app.$svc.service.get('/homePage/emergencyCount/getReplayNum', this.formData)
                .then((result) => {
                    if (result.code == 1) {
                        this.replay = result.data
                    } else {
                        // 服务器返回失败
                        this.$message.error('查询失败: ' + result.message)
                    }
                });
        },
        getMaterialNum() {
            app.$svc.service.get('/homePage/emergencyCount/getMaterialNum', this.formData)
                .then((result) => {
                    if (result.code == 1) {
                        this.material = result.data
                    } else {
                        // 服务器返回失败
                        this.$message.error('查询失败: ' + result.message)
                    }
                });
        },
        getEscapeNum() {
            app.$svc.service.get('/homePage/emergencyCount/getEscapeNum', this.formData)
                .then((result) => {
                    if (result.code == 1) {
                        this.escape = result.data
                    } else {
                        // 服务器返回失败
                        this.$message.error('查询失败: ' + result.message)
                    }
                });
        },
        getReplayByEnt(type){
            let url;
            if(type == '1'){
                url = '/homePage/emergencyCount/getReplayByEntSelf'
            }
            if(type == '2'){
                url = '/homePage/emergencyCount/getReplayByEnt'
            }
            app.$svc.service.get(url, this.formData)
                .then((result) => {
                    if (result.code == 1) {
                        this.updateEc_1_4(result.data);
                    } else {
                        this.$message.error('查询失败: ' + result.message)
                    }
                });
        },

        /**
         * 突发事件按事件等级的分布
         */
        updateEc_1_1() {
            /**
             * value 为图形补偿展示
             * showValue 为图形数值展示
             */
            let val = {
                series: [
                    {
                        data: [
                            {
                                value: this.accident.qwssg,
                                showValue: this.accident.qwssg,
                                accidentType: 'E9008',
                                name: '轻微伤事故',
                                unit: '起',
                            },
                            {
                                value: this.accident.xwsg,
                                showValue: this.accident.xwsg,
                                accidentType: 'E9007',
                                name: '小微事故',
                                unit: '起',
                            },
                            {
                                value: this.accident.sjsg,
                                showValue: this.accident.sjsg,
                                accidentType: 'E9006',
                                name: '三级事故',
                                unit: '起',
                            },
                            {
                                value: this.accident.ejsg,
                                showValue: this.accident.ejsg,
                                accidentType: 'E9005',
                                name: '二级事故',
                                unit: '起',
                            },
                            {
                                value: this.accident.yjsg,
                                showValue: this.accident.yjsg,
                                accidentType: 'E9004',
                                name: '一级事故',
                                unit: '起',
                            },
                            {
                                value: this.accident.jdsg,
                                showValue: this.accident.jdsg,
                                accidentType: 'E9003',
                                name: '较大事故',
                                unit: '起',
                            },
                            {
                                value: this.accident.zdsg,
                                showValue: this.accident.zdsg,
                                accidentType: 'E9002',
                                name: '重大事故',
                                unit: '起',
                            },
                            {
                                value: this.accident.ztdsg,
                                showValue: this.accident.ztdsg,
                                accidentType: 'E9001',
                                name: '重特大事故',
                                unit: '起',
                            },
                        ],

                        ...this.ec_ring_pie_label_config,
                    },
                ],
            }
            this.$refs['ec_1_1'].updateOptions(val, {
                loop: true,
                itemColors: [
                    '#5D7092D9',
                    '#5AD8A6D9',
                    '#6DC8ECD9',
                    '#6773F4FF',
                    '#A371EAFF',
                    '#F6BD16D9',
                    '#FFE75EFF',
                    '#FB544EFF',
                ],
            })
        },
        /**
         * 生产安全事故的分布
         */
        updateEc_1_2(obj) {
            /**
             * value 为图形补偿展示
             * showValue 为图形数值展示
             */
            let val = {
                series: [
                    {
                        data: obj,

                        ...this.ec_ring_pie_label_config,
                    },
                ],
            }
            this.$refs['ec_1_2'].updateOptions(val, {
                loop: true,
                itemColors: [
                    'rgba(151, 190, 255, 0.8)',
                    'rgba(172, 255, 200, 0.8)',
                    'rgba(214, 214, 214, 0.8)',
                    'rgba(150, 242, 255, 0.8)',
                    'rgba(255, 202, 148, 0.8)',
                    '#5D7092D9',
                    '#5AD8A6D9',
                    '#6DC8ECD9',
                    '#6773F4FF',
                    '#A371EAFF',
                    '#F6BD16D9',
                    '#FFE75EFF',
                    '#2FA1FEFF',
                    '#FC9C3BFF',
                    '#FB544EFF',
                ],
            })
        },
        /**
         * 应急预案按预案类别的分布
         */
        updateEc_1_3() {
            /**
             * value 为图形补偿展示
             * showValue 为图形数值展示
             */
            let val = {
                series: [
                    {
                        data: [
                            {
                                value: this.reserve.zhyaNum,
                                showValue: this.reserve.zhyaNum,
                                name: '综合预案',
                                reserveType: 'C0301'
                            },
                            {
                                value: this.reserve.xcczyaNum,
                                showValue: this.reserve.xcczyaNum,
                                name: '现场处置预案',
                                reserveType: 'C0302'
                            },
                            {
                                value: this.reserve.zxyaNum,
                                showValue: this.reserve.zxyaNum,
                                name: '专项预案',
                                reserveType: 'C0303'
                            },
                        ],

                        ...this.ec_ring_pie_label_config,
                    },
                ],
            }
            this.$refs['ec_1_3'].updateOptions(val, {
                loop: true,
                itemColors: [
                    '#6773F4FF',
                    '#FDCA30FF',
                    '#A371EAFF',
                ],
            })
        },

        /**
         * 应急演练统计
         */
        updateEc_1_4(result) {
            let val = {
                legend: {
                    show: true,
                },
                xAxis: {
                    data: result.map(obj => obj.entName),
                    radius: {
                        fontSize: 13,
                        formatter: function (value) {
                            // 每4个字符换行
                            return value.match(/.{1,4}/g).join('\n')
                        },
                    },
                },

                series: [
                    {
                        name: '实战评估',
                        data: result.map(obj => {
                            return {
                                value: obj.szpgNum,
                                entId: obj.entId,
                                replayType:1
                            }
                        }),
                        barWidth: 12,
                    },
                    {
                        name: '桌面评估',
                        data: result.map(obj => {
                            return {
                                value: obj.zmpgNum,
                                entId: obj.entId,
                                replayType:0
                            }
                        }),
                        barWidth: 12,
                    },

                ],
            }
            this.$refs['ec_1_4'].updateOptions(val)
        },
        clickAccident(type) {
            let param = {
                startAccidentTime: this.formData.startDate,
                endAccidentTime: this.formData.endDate,
            };
            Object.assign(param, type);
            this.$refs.TpDialog.show(() => import('./accidentList.vue'), param, "突发事件查询");
        },
        clickTeam(type) {
            let param = {
                startAccidentTime: this.formData.startDate,
                endAccidentTime: this.formData.endDate,
            };
            Object.assign(param, type);
            this.$refs.TpDialog.show(() => import('./teamList.vue'), param, "应急队伍查询");
        },
        clickReserve(type) {
            let param;
            if (type.reviseStatus) {
                param = {
                    reviseTimeStart: this.formData.startDate,
                    reviseTimeEnd: this.formData.endDate,
                };
            } else if (type.reserveStatus) {
                param = {};
            } else {
                param = {
                    startReserveDate: this.formData.startDate,
                    endReserveDate: this.formData.endDate,
                };
            }
            Object.assign(param, type);
            this.$refs.TpDialog.show(() => import('./reserveList.vue'), param, "应急预案查询");
        },
        clickReplay(type) {
            let param = {
                replayStartTime: this.formData.startDate,
                replayEndTime: this.formData.endDate,
            };
            Object.assign(param, type);
            this.$refs.TpDialog.show(() => import('./replayList.vue'), param, "应急演练查询");
        },
        clickMaterial(type) {
            let param = {};
            if(type.status == '0'){//即将到期
                param = {
                    startValidTime: app.$dayjs().format('YYYYMMDD'),
                    endValidTime: app.$dayjs().add(3, 'month').format('YYYYMMDD'),
                };
            }
            if(type.status == '1'){ //已到期
                param = {
                    endValidTime: app.$dayjs().add(-1, 'day').format('YYYYMMDD')
                };
            }
            Object.assign(param, type);
            this.$refs.TpDialog.show(() => import('./materialList.vue'), param, "应急设备查询");
        },
        clickEscape(type) {
            let param = {
                sDate: this.formData.startDate,
                eDate: this.formData.endDate,
            };
            Object.assign(param, type);
            this.$refs.TpDialog.show(() => import('./escapeList.vue'), param, "应急物资检维保查询");
        },
        clickReplayByEntType(){
            this.getReplayByEnt(this.replayEntType);
        },
        clickReplayByEnt(val) {
            let param = {
                replayStartTime: this.formData.startDate,
                replayEndTime: this.formData.endDate,
                replayType: val.data.replayType,
                entId: val.data.entId,
            };
            this.$refs.TpDialog.show(() => import('./replayList.vue'), param, "应急演练查询");
        },
    },
}
</script>

<style lang="less" scoped>
@import "./index";
</style>
