(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-ba8b4070"],{"31a2":function(e,t,a){"use strict";a("79c4")},7646:function(e,t,a){"use strict";a.r(t);var i=function(){var e=this,t=e.$createElement,a=e._self._c||t;return a("div",{staticClass:"page"},[e._m(0),a("div",{staticClass:"main-body"},[e.hasList?a("div",[a("div",{staticClass:"page-table margin-bottom-20"},[a("Table",{attrs:{columns:e.columns1,stripe:"",data:e.dataList}})],1),a("div",{staticClass:"page-footer text-center"},[a("Page",{attrs:{total:e.itemTotal,"page-size-opts":[10,20,40],current:e.pageIndex,"page-size":e.pageSize,"show-elevator":"","show-total":"","show-sizer":""},on:{"on-page-size-change":e.getPageSize,"on-change":e.getPageList}})],1)]):a("div",{staticClass:"main-none"},[a("noListData")],1)]),a("div",{staticClass:"content_win"},[a("editPerson",{ref:"editPerson",on:{getList:e.getList}})],1)])},s=[function(){var e=this,t=e.$createElement,a=e._self._c||t;return a("div",{staticClass:"page-header margin-bottom-20"},[a("h3",[e._v("人员管理")])])}],r=function(){var e=this,t=e.$createElement,a=e._self._c||t;return a("div",{staticClass:"editRoleModal page"},[a("Modal",{staticClass:"page-modal",attrs:{width:"35%",title:"修改",scrollable:!1,"footer-hide":!0,"class-name":"vertical-center-modal",styles:{top:"-30px"}},model:{value:e.dialogVisible,callback:function(t){e.dialogVisible=t},expression:"dialogVisible"}},[e.loadingShow?a("Spin",{attrs:{fix:""}},[a("Icon",{staticClass:"demo-spin-icon-load",attrs:{type:"ios-loading",size:"18"}}),a("div",[e._v("Loading")])],1):e._e(),a("div",{staticClass:"modalContainer margin-bottom-100"},[a("Form",{ref:"addForm",staticClass:"form-box",attrs:{"label-width":130,model:e.addForm,"label-colon":"",rules:e.ruleValidate}},[a("FormItem",{attrs:{label:"git账号"}},[a("Input",{attrs:{size:"large",maxlength:10,placeholder:"请输入git账号",clearable:"",disabled:""},model:{value:e.addForm.userName,callback:function(t){e.$set(e.addForm,"userName",t)},expression:"addForm.userName"}})],1),a("FormItem",{attrs:{label:"邮箱"}},[a("Input",{attrs:{size:"large",placeholder:"请输入邮箱",clearable:"",disabled:""},model:{value:e.addForm.userEmail,callback:function(t){e.$set(e.addForm,"userEmail",t)},expression:"addForm.userEmail"}})],1),a("FormItem",{attrs:{label:"真实姓名"}},[a("Input",{attrs:{size:"large",maxlength:10,placeholder:"请输入真实姓名",clearable:""},model:{value:e.addForm.userRealName,callback:function(t){e.$set(e.addForm,"userRealName",t)},expression:"addForm.userRealName"}})],1),a("FormItem",{attrs:{label:"部门"}},[a("Select",{attrs:{filterable:"",multiple:""},model:{value:e.addForm.groups,callback:function(t){e.$set(e.addForm,"groups",t)},expression:"addForm.groups"}},e._l(e.deptTypes,(function(t){return a("Option",{key:t.groupId,attrs:{value:t.groupId}},[e._v(e._s(t.groupName))])})),1)],1)],1)],1),a("div",{staticClass:"detailBackBtn margin-bottom-40"},[a("Button",{attrs:{size:"large"},on:{click:function(t){return e.closeModal()}}},[e._v(e._s(e.$t("common.cancel")))]),a("Button",{attrs:{type:"primary",size:"large"},on:{click:function(t){return e.handleSubmit()}}},[e._v(e._s(e.$t("common.ensure")))])],1)],1)],1)},o=[],n={name:"addModal",data:function(){var e=this,t=function(t,a,i){var s=/^\+?[1-9]\d*$/;s.test(a)||i(new Error(e.$t("addOtherTip.tip8"))),(a<0||a>4294967294)&&i(new Error(e.$t("addOtherTip.tip8"))),i()},a=function(t,a,i){var s=/^[^\#\$\￥\%\^\&\*\……\~\`\|\【\】\》\《\'\!\！\{\}\‘\“\”\’\/\、\?\？\——\=\+\@]*$/;s.test(a)||i(new Error(e.$t("addOtherTip.tip7"))),i()};return{loadingShow:!1,cardtype:0,dialogVisible:!1,resultType:!0,addForm:{userName:"",userRealName:"",dept:"",userEmail:""},ruleValidate:{cardnumber:[{required:!0,message:this.$t("card.tip1"),trigger:"blur"},{validator:t,trigger:"blur"}],cardtype:[{required:!0,message:this.$t("card.tip2"),trigger:"blur"}],remarks:[{validator:a,trigger:"blur"}]},randomModal:!1,loginTime:"",deptTypes:[],isEdit:!1,row:{},disabled:!1,timer:null,ipAddress:""}},computed:{},watch:{dialogVisible:function(e){e?(this.addForm=this.row,this.getDeptList()):(this.handleReset(),this.$emit("getList"),clearInterval(this.timer))},isEdit:function(e){this.disabled=!!e}},mounted:function(){},methods:{handleReset:function(){this.$refs.addForm.resetFields(),this.loginTime="",this.addForm=this.$options.data.call(this).addForm},closeModal:function(){this.dialogVisible=!1,this.handleReset(),clearInterval(this.timer)},handleSubmit:function(){console.log("点击确定按钮",this.addForm);var e=this;this.$refs.addForm.validate((function(t){t&&e.updatePerson()}))},getDeptList:function(){var e=this;this.$store.dispatch("deptManager/getDeptList").then((function(t){console.log(t),e.deptTypes=t.data})).catch((function(){}))},updatePerson:function(){var e=this;this.$store.dispatch("personManager/editPerson",this.addForm).then((function(t){console.log(t),e.$message({type:"success",message:"修改成功"}),e.$emit("getList"),e.dialogVisible=!1})).catch((function(){}))}},beforeDestroy:function(){clearInterval(this.timer)}},l=n,d=(a("31a2"),a("2877")),c=Object(d["a"])(l,r,o,!1,null,"0b7b5229",null),u=c.exports,m={name:"message",components:{editPerson:u},data:function(){return{selectActive:1,columns1:[{title:"姓名",align:"center",key:"realUserName"},{title:"邮箱",align:"center",key:"userEmail"},{title:"git账号",align:"center",key:"userName"}],hasList:!1,pageSize:10,pageIndex:1,itemTotal:0,validForm:{},query:{},dataList:[]}},computed:{},watch:{},created:function(){},mounted:function(){this.getList()},methods:{getPageList:function(e){this.pageIndex=e,this.getList()},getPageSize:function(e){this.pageIndex=1,this.pageSize=e,this.getList()},searchComm:function(){for(var e in this.validForm={},this.query)this.query[e]&&(this.validForm[e]=this.query[e]);this.pageIndex=1,this.getCaList()},updatePerson:function(e){this.$refs.editPerson.dialogVisible=!0;var t=[];e.groupList.map((function(e){t.push(e.groupId)}));var a={userName:e.userName,userRealName:e.userRealName,userEmail:e.userEmail,groups:t,userId:e.userId};this.$refs.editPerson.row=a},getList:function(){var e=this,t={pageIndex:this.pageIndex,pageSize:this.pageSize};this.$store.dispatch("personManager/getPersonList",t).then((function(t){var a=t.data;a.records&&0===a.records.length?e.hasList=!1:(e.hasList=!0,e.dataList=a.records,e.itemTotal=a.total),console.log(t)})).catch((function(){}))}}},g=m,p=(a("ea67"),Object(d["a"])(g,i,s,!1,null,"53b597cc",null));t["default"]=p.exports},"79c4":function(e,t,a){},"90ba":function(e,t,a){},ea67:function(e,t,a){"use strict";a("90ba")}}]);