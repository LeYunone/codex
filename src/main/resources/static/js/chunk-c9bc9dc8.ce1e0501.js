(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-c9bc9dc8"],{"22ca":function(t,e,r){},"3fe3":function(t,e,r){"use strict";var a=r("688d");e["a"]={data:function(){var t=this;return{options2:{shortcuts:[{text:"近一周",value:function(){var t=new Date,e=new Date;return e.setTime(e.getTime()-6048e5),[e,t]}},{text:"近一个月",value:function(){var t=new Date,e=new Date;return e.setTime(e.getTime()-2592e6),[e,t]}},{text:"近一年",value:function(){var t=new Date,e=new Date;return e.setTime(e.getTime()-864e5*Object(a["b"])()),[e,t]}}],disabledDate:function(e){if(t.endMonth){var r=t.getDate(t.endMonth);return e&&e>r||e>new Date}return e&&e>new Date}}}}}},4890:function(t,e,r){"use strict";r("22ca")},"688d":function(t,e,r){"use strict";function a(t){var e=t.getFullYear(),r=t.getMonth()+1;r=r<10?"0"+r:r;var a=t.getDate();return a=a<10?"0"+a:a,e+"-"+r+"-"+a}function n(){var t=new Date,e=new Date(t.getFullYear(),0,1),r=t.getTime()-e.getTime(),a=Math.floor(r/864e5);return a}r.d(e,"a",(function(){return a})),r.d(e,"b",(function(){return n}))},c0a1:function(t,e,r){"use strict";r.r(e);var a=function(){var t=this,e=t.$createElement,r=t._self._c||e;return r("div",{staticClass:"page"},[t._m(0),r("div",{staticClass:"main-body"},[r("div",{staticClass:"main-title margin-bottom-20"},[r("Row",{attrs:{gutter:20}},[r("i-col",{attrs:{span:4}},[r("Input",{attrs:{placeholder:"请输入提交数量阈值",size:"large",type:"number",clearable:""},model:{value:t.limit,callback:function(e){t.limit=e},expression:"limit"}})],1),r("i-col",{staticClass:"text-left",attrs:{span:4}},[r("Select",{attrs:{size:"large",placeholder:"请选择部门",filterable:"",clearable:""},model:{value:t.query.groupId,callback:function(e){t.$set(t.query,"groupId",e)},expression:"query.groupId"}},t._l(t.deptList,(function(e){return r("Option",{key:e.groupId+e.groupName,attrs:{value:e.groupId}},[t._v(t._s(e.groupName))])})),1)],1),r("i-col",{staticClass:"text-left",attrs:{span:4}},[r("Select",{attrs:{size:"large",placeholder:"请选择人员",filterable:"",clearable:""},model:{value:t.query.realUserName,callback:function(e){t.$set(t.query,"realUserName",e)},expression:"query.realUserName"}},t._l(t.personList,(function(e){return r("Option",{key:e.realUserId+e.realUserName,attrs:{value:e.realUserName}},[t._v(t._s(e.realUserName))])})),1)],1),r("i-col",{staticClass:"text-left",attrs:{span:4}},[r("Select",{attrs:{size:"large",placeholder:"请选择BUG状态",filterable:"",clearable:""},model:{value:t.query.status,callback:function(e){t.$set(t.query,"status",e)},expression:"query.status"}},t._l(t.statusList,(function(e){return r("Option",{key:e.value,attrs:{value:e.value}},[t._v(t._s(e.name))])})),1)],1),r("i-col",{attrs:{span:5}},[r("div",{staticClass:"date-box"},[r("DatePicker",{staticStyle:{width:"200px"},attrs:{type:"daterange",options:t.options2,placement:"bottom-end",placeholder:"请选择日期",size:"large",value:t.selectTimeValue},on:{"on-change":t.changeTime}})],1)]),r("i-col",{attrs:{span:3}},[r("Button",{directives:[{name:"debounce",rawName:"v-debounce"}],attrs:{size:"large",type:"primary"},on:{click:t.searchList}},[r("span",{staticClass:"fa fa-search"})])],1)],1)],1),t.hasList?r("div",[r("div",{staticClass:"page-table margin-bottom-20 min-height-table"},[r("Table",{attrs:{loading:t.tableLoading,columns:t.newPersonColumns,stripe:"",data:t.dataList},on:{"on-sort-change":t.sortBugNumber},scopedSlots:t._u([{key:"bugNumber",fn:function(e){var a=e.row;return[r("Tooltip",{attrs:{content:t.getTooltipContent(a)}},[r("div",{staticClass:"link-text",on:{click:function(e){return t.gotoBugDetail(a,0)}}},[t._v(t._s(a.bugNumber)),r("i",{staticClass:"el-icon-info",staticStyle:{"margin-left":"10px"},attrs:{color:"#ccc"}})])])]}},{key:"reopenRate",fn:function(e){var a=e.row;return[r("span",{staticClass:"link-text",class:t.isGreaterThan5Percent(a)?"hight-rate":"",on:{click:function(e){return t.gotoBugDetail(a,1)}}},[t._v(" "+t._s(t.getReopenRate(a)))])]}},{key:"CMMINumber",fn:function(e){var a=e.row;return[r("span",[t._v(" "+t._s(t.getCIMM(a)))])]}},{key:"level",fn:function(e){var a=e.row;return[r("span",{class:t.getLevel(a)<4?"hight-rate":""},[t._v(" "+t._s(t.getLevel(a))+t._s("-"!==t.getLevel(a)?"级":""))])]}},{key:"deletions",fn:function(e){var a=e.row;return[r("div",{staticClass:"actionLink"},[r("span",{staticClass:"delete-txt"},[t._v(t._s(a.deletions||0))])])]}},{key:"additions",fn:function(e){var a=e.row;return[r("div",{staticClass:"actionLink"},[r("span",{staticClass:"add-txt"},[t._v(t._s(a.additions||0))])])]}}],null,!1,3056785289)})],1),r("div",{staticClass:"page-footer text-center"},[r("Page",{attrs:{total:t.itemTotal,current:t.pageIndex,"page-size":t.pageSize,"show-elevator":"","show-total":"","show-sizer":""},on:{"on-page-size-change":t.getPageSize,"on-change":t.getPageList}})],1)]):r("div",{staticClass:"main-none"},[r("noListData")],1)]),r("div",{staticClass:"content_win"})])},n=[function(){var t=this,e=t.$createElement,r=t._self._c||e;return r("div",{staticClass:"page-header margin-bottom-20"},[r("h3",[t._v("人员代码质量")])])}],s=r("3fe3"),i=r("688d"),o=r("2f62");function u(t){return u="function"==typeof Symbol&&"symbol"==typeof Symbol.iterator?function(t){return typeof t}:function(t){return t&&"function"==typeof Symbol&&t.constructor===Symbol&&t!==Symbol.prototype?"symbol":typeof t},u(t)}function c(t,e){var r=Object.keys(t);if(Object.getOwnPropertySymbols){var a=Object.getOwnPropertySymbols(t);e&&(a=a.filter((function(e){return Object.getOwnPropertyDescriptor(t,e).enumerable}))),r.push.apply(r,a)}return r}function l(t){for(var e=1;e<arguments.length;e++){var r=null!=arguments[e]?arguments[e]:{};e%2?c(Object(r),!0).forEach((function(e){d(t,e,r[e])})):Object.getOwnPropertyDescriptors?Object.defineProperties(t,Object.getOwnPropertyDescriptors(r)):c(Object(r)).forEach((function(e){Object.defineProperty(t,e,Object.getOwnPropertyDescriptor(r,e))}))}return t}function d(t,e,r){return e=p(e),e in t?Object.defineProperty(t,e,{value:r,enumerable:!0,configurable:!0,writable:!0}):t[e]=r,t}function p(t){var e=g(t,"string");return"symbol"==u(e)?e:e+""}function g(t,e){if("object"!=u(t)||!t)return t;var r=t[Symbol.toPrimitive];if(void 0!==r){var a=r.call(t,e||"default");if("object"!=u(a))return a;throw new TypeError("@@toPrimitive must return a primitive value.")}return("string"===e?String:Number)(t)}var m={name:"message",components:{},mixins:[s["a"]],data:function(){return{selectActive:1,hasList:!1,pageSize:10,pageIndex:1,sortField:1,sortMode:2,itemTotal:0,validForm:{},query:{realUserName:"",status:"",startDate:"",endDate:"",groupId:""},dataList:[],personList:[],selectTime:[],tableLoading:!1,limit:"1000",selectTimeValue:[],deptList:[]}},computed:l(l(l({},Object(o["c"])("bugDetails",["tableColumns","statusList"])),Object(o["c"])("codeQuality",["columns","personColumns","hasSortColumns"])),{},{newPersonColumns:function(){return this.personColumns.concat(this.hasSortColumns).concat(this.columns)},getCIMM:function(){return function(t){return 0==t.bugNumber||0==t.codeTotal?0:(Number(t.bugNumber)/Number(t.codeTotal)*1e3).toFixed(2)}},getLevel:function(){var t=this;return function(e){var r=t.getCIMM(e);switch(!0){case r>5.52:return 1;case r>2.39&&r<=5.52:return 2;case r>.92&&r<=2.39:return 3;case r>.32&&r<=.92:return 4;case r>=0&&r<=.32:return 5;default:return"-"}}},getReopenRate:function(){return function(t){if(0==t.reopenNumber||0==t.bugNumber)return 0;var e=Number(t.reopenNumber)/Number(t.bugNumber);return(100*e).toFixed(2)}},isGreaterThan5Percent:function(){var t=this;return function(e){var r=t.getReopenRate(e),a=parseFloat(r)/100;return a>.05}}}),watch:{},created:function(){var t=new Date,e=new Date;e.setTime(e.getTime()-864e5*Object(i["b"])()),this.query.startDate=Object(i["a"])(e,"yyyy-MM-dd"),this.query.endDate=Object(i["a"])(t,"yyyy-MM-dd"),this.selectTimeValue[0]=this.query.startDate,this.selectTimeValue[1]=this.query.endDate},mounted:function(){void 0!==this.$route.params.projectId&&this.$set(this.validForm,"projectId",this.$route.params.projectId),this.getList(),this.getPersonList(),this.getDeptList()},methods:{getPageList:function(t){this.pageIndex=t,this.getList()},getPageSize:function(t){this.pageIndex=1,this.pageSize=t,this.getList()},searchList:function(){this.pageIndex=1,this.getList()},updatePerson:function(){this.$refs.editPerson.dialogVisible=!0},getList:function(){var t=this;this.tableLoading=!0;var e={pageIndex:this.pageIndex,pageSize:this.pageSize,queryType:0,limit:this.limit,sortMode:this.sortMode,sortField:this.sortField};e=Object.assign(this.query,e),this.$store.dispatch("codeQuality/statistics",e).then((function(e){t.tableLoading=!1;var r=e.data;r.records&&0===r.records.length?t.hasList=!1:(t.hasList=!0,t.dataList=r.records,t.itemTotal=r.total)})).catch((function(){t.tableLoading=!1}))},getPersonList:function(){var t=this,e={type:0};this.$store.dispatch("commit/getPersonList",e).then((function(e){t.personList=e.data})).catch((function(){}))},changeTime:function(t){console.log(t),this.selectTime=t,this.query.startDate=t[0],this.query.endDate=t[1]},getTooltipContent:function(t){return"\n        未修复数(个)：".concat(t.doingNumber,"\n        已修复数(个)：").concat(t.fixNumber,"\n        Reopen数(个)：").concat(t.reopenNumber,"\n      ")},sortBugNumber:function(t){var e=t.column,r=t.order;this.sortMode="desc"===r?2:1,this.sortField="bugNumber"===e.slot?1:2,this.getList()},getDeptList:function(){var t=this;this.$store.dispatch("deptManager/getDeptList").then((function(e){t.deptList=e.data,t.groupId=t.deptList[0].groupId||"",t.getData(t.deptList[0].groupId)})).catch((function(){}))},gotoBugDetail:function(t,e){console.log("row",t),this.$router.push({path:"/bugModule/bugDetails",query:{realUserName:t.name,startDate:this.query.startDate,endDate:this.query.endDate,type:e}})}}},f=m,h=(r("4890"),r("2877")),b=Object(h["a"])(f,a,n,!1,null,"446fb9c4",null);e["default"]=b.exports}}]);