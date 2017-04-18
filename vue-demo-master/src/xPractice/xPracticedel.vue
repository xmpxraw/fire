<template>
    <div class="page-xPracticedel">
        <header-nav title="章节练习" :backshow="backshow"></header-nav>
        <div class="contain" v-show="allCon">
            <div class="contain-top">
                <p class="wrongTitle"><span v-show="false">{{proIndex+1}}</span>{{questionAllData.chapterName}}</p>
                <p class="hardStar"><span>难度</span>
                    <rater :value.sync="questionAllData.level" slot="value" active-color="#e15147" disabled></rater>
                </p>
            </div>

            <div class="contain-middle">
                <p>{{proIndex+1}}.【{{questionAllData.questionType}}】{{questionAllData.caption}}</p>
              <img src={{questionAllData.imgUrl}} alt="" v-show="imgUrl">

                <ul class="box type0" v-show="showPro0">
                    <li>
                      <label for="t0Checkbox1">
                        A. {{questionAllData.optionA}}
                        </label>
                        <input type="radio" class="checkbox" id="t0Checkbox1" value="optionA" v-model="showPro0Arr" :class="answerChoose0">
                    </li>
                    <li>
                      <label for="t0Checkbox2">
                        B. {{questionAllData.optionB}}
                      </label>
                        <input type="radio" class="checkbox" value="optionB" id="t0Checkbox2" v-model="showPro0Arr" :class="answerChoose1">
                    </li>
                </ul>

                <ul class="box type1" v-show="showPro1">
                    <li>
                      <label for="t1Checkbox1">
                        A. {{questionAllData.optionA}}
                      </label>
                        <input type="radio" class="checkbox" id="t1Checkbox1" value="optionA" v-model="showPro1Arr" :class="answerChoose0">
                    </li>
                    <li>
                      <label for="t1Checkbox2">
                        B. {{questionAllData.optionB}}
                      </label>
                        <input type="radio" class="checkbox" value="optionB" id="t1Checkbox2" v-model="showPro1Arr" :class="answerChoose1">
                    </li>
                    <li>
                      <label for="t1Checkbox3">
                        C. {{questionAllData.optionC}}
                      </label>
                        <input type="radio" class="checkbox" value="optionC" id="t1Checkbox3" v-model="showPro1Arr" :class="answerChoose2">
                    </li>
                    <li>
                      <label for="t1Checkbox4">
                        D. {{questionAllData.optionD}}
                      </label>
                        <input type="radio" class="checkbox" value="optionD" id="t1Checkbox4" v-model="showPro1Arr" :class="answerChoose3">
                    </li>
                </ul>

                <ul class="box type2" v-show="showPro2">
                    <li>
                      <label for="t2Checkbox1">
                        A. {{questionAllData.optionA}}
                      </label>
                        <input type="checkbox" class="checkbox" id="t2Checkbox1" value="optionA" v-model="showPro2Arr" :class="answerChoose0">
                    </li>
                    <li>
                      <label for="t2Checkbox2">
                        B. {{questionAllData.optionB}}
                      </label>
                      <input type="checkbox" class="checkbox" value="optionB" id="t2Checkbox2" v-model="showPro2Arr" :class="answerChoose1">
                    </li>
                    <li>
                        <label for="t2Checkbox3">
                           C. {{questionAllData.optionC}}
                        </label>
                        <input type="checkbox" class="checkbox" value="optionC" id="t2Checkbox3" v-model="showPro2Arr" :class="answerChoose2">
                    </li>
                    <li>
                        <label for="t2Checkbox4">
                          D. {{questionAllData.optionD}}
                        </label>
                        <input type="checkbox" class="checkbox" value="optionD" id="t2Checkbox4" v-model="showPro2Arr" :class="answerChoose3">
                    </li>
                </ul>

            </div>

            <div class="contain-foot" v-show="analysis">
                <p>正确答案: {{questionAllData.desAnswer}}</p>
                <p>答案解析</p>
                <p>{{questionAllData.analysis}}</p>

            </div>

            <div class="submit" v-show="submitBtn" data-id={{questionAllData.id}} @click="submitPro">提交</div>
            <footer>
                <p class="conRight"><span>{{rightCount}}对/{{wrongCount}}错</span><br><span>{{unAnswerCount}}未答</span></p>

                <span class="menu" @click="showTemp"></span>

                <span class="goPage">
					  		<img src="../assets/images/btn_left.png" alt="" @click="preItem">
					  		<span>{{proIndex+1}}/{{questionList.length}}</span>
					  		<img src="../assets/images/btn_right.png" alt="" @click="nextItem">
					  	</span>

							<span class="reset" @click="resetPro" v-show="false">重做</span>

            </footer>
        </div>
        <no-message tip="暂无数据" v-show="showNoMessage"></no-message>
        <div class="mask" v-show="showMenu">
            <div class="problemDetail">
                <div class="mask_top">
                    <p>
                        <span>答对{{rightCount}}</span> <span>答错{{wrongCount}}</span> <span>未答{{unAnswerCount}}</span> <span @click="showMenu=false">取消</span>
                    </p>
                </div>
                <div class="mask_middle">
                    <ul class="mask_middle_ul">
                        <li v-for="item in tempData" @click="tempJumpItem($index)" class={{item.alreadyAns.classType}}>{{$index + 1}}</li>
                    </ul>
                </div>
                <div class="mask_foot">
                    <span>跳转到</span> <input type="text" id="jumpItem"> <span class="sure" @click="jumpItem">确定</span>
                </div>
            </div>
        </div>

        <loading :show="loading"></loading>
        <toast :show.sync="show2" type="text" :time="1000">{{warntext}}</toast>
        <confirm :show.sync="showConfirm" title="提示" @on-cancel="onCancel" cancel-text="取消" confirm-text="确定" @on-confirm="onConfirm">
        <p style="text-align:center;">{{confirmText}}</p>
      </confirm>
    </div>
</template>

<script>
    import headerNav from '../common/header.vue'
    import loading from 'vux-components/loading'
    import apiService from '../apiservice.js'
    import toast from 'vux-components/toast'
    import rater from 'vux-components/rater'
    import confirm from 'vux-components/confirm'
    import noMessage from '../common/noMessage.vue'
    export default {
        name: 'xPracticedel',
        components: {
            headerNav,
            noMessage,
            loading,
            rater,
            toast,
            confirm,
        },
        ready(){
            this.renderData();
        },
        data () {
            return {
//                studentNo: "170405002",
//                sysCode: "94a1533f-85a2-4c6a-8850-74a39b2b5ba9",
                studentNo: localStorage.getItem("studentNo"),
                sysCode: localStorage.getItem("sysCode"),
                backshow: true,
                loading: false,//控制是否出现loading
                show2: false,//控制toast的出现
                allCon: true, //主体内容
                warntext: "",      //警告文字
                showMenu: false,   //菜单按钮显示选项
                showPro0: false,   //判断题选项
                showPro1: false,  //单选题选项
                showPro2: false,  //多选题选项
                answerList: [],   //储存答案的数组
                proIndex: 0,     //当前位置，第几道题
                submitBtn: true, //提交按钮
                analysis: false,  //题目解析
                questionAllData: [],//所有数据，用来渲染模版的data
                showPro0Arr: [],  //获取showPro0的选中项
                showPro1Arr: [],  //获取showPro1的选中项
                showPro2Arr: [],  //获取showPro2的选中项
                showNoMessage: false,  // 暂无信息提示语
                questionList: [], //自定义储存所有数据的数组
                trueAnswer: true,
                answerChoose0: [],
                answerChoose1: [],
                answerChoose2: [],
                answerChoose3: [],
                rightCount:"", //正确答案数量
                wrongCount:"", //错误答案数量
                unAnswerCount:"", //未做答案数量
                tempData:[], //模版数据
                resetBtn: false, //重做按钮
                confirmText: "",
                showConfirm: false,
                imgUrl: false, //判断题目描述的img是否显示
            }
        },

        methods: {
            renderData: function () {
                this.loading = true;
                var perparams = {
                  chapterId: this.$route.params.chapterId,
                  questionType: "",
                  specialty: "",
                  studentNo: this.studentNo,
                  sysCode: this.sysCode
                };

                apiService.getAllQuestion(perparams).then((data) => {
                    this.loading = false;
                    if (data.data.data.length !== 0) {
                        //console.log(data.data.code);
                        this.questionList = data.data.data;
                        //console.log("每题数据");
                        //console.log(this.logJSON(data));
                        // this.logJSON(this.questionList);

                        // 设置自己定义的xtype, 0 ==> 判断 1 ==> 单选 2 ==> 多选
                        //console.log("momoList1111111111111111111")
                        this.momoList={};
                        //console.log(this.momoList);
                        for (var i = 0; i < this.questionList.length; i++) {
                        	this.questionList[i].alreadyAns={};
                        	this.momoList[this.questionList[i].id]=i;
                            if (this.questionList[i].questionType == "判断") {
                                this.questionList[i].xtype = 0;
                            } else if (this.questionList[i].questionType == "单选") {
                                this.questionList[i].xtype = 1;
                            } else {
                                this.questionList[i].xtype = 2;
                            }
                            ;
                        }

                        this.getAnswerList();

                    } else {
                        this.show2 = true;
                        this.submitBtn = false;
                        this.allCon = false;
                        this.showNoMessage = true;
                        this.warntext = "暂无数据";
                    }

                    //console.log("ssssss");
                    //console.log(this.momoList);

                }, (err) => {
                    this.loading = false;
                    this.show2 = true;
                    this.warntext = "网络不给力,请稍后再试...";
                    //console.log(err);
                })
            },

            // 答题记录
            getAnswerList: function () {
                this.loading = true;

                apiService.getAnswerList({
                    chapterId: this.$route.params.chapterId,
                    studentNo: this.studentNo,
                    sysCode: this.sysCode,
                    type: "practice"

                }).then((data) => {
                    this.loading = false;
                    this.answerList = data.data.data.recored;
                    //console.log("answerList=======");
                    //console.log(data);
                    //console.log(data.data);
                    //console.log(this.logJSON(this.answerList));

                    this.rightCount = data.data.data.count.rightCount;
                    this.unAnswerCount = data.data.data.count.unAnswerCount;
                    this.wrongCount = data.data.data.count.wrongCount;

                    //console.log(this.rightCount,this.unAnswerCount,this.wrongCount);

	                  // 把答题记录存在数组里
										//console.log("momoList222222222222222");
                    this.logJSON(this.questionList);

                    //console.log("answerList============");
                    this.logJSON(this.answerList);

                    //console.log("this.momoList============");
                    this.logJSON(this.momoList);

		                for(var i=0,length=this.answerList.length;i<length;i++){
	                    	var answerId = this.answerList[i].questionId;
                      //console.log(this.momoList);
	                    	var index = this.momoList[answerId];
                        if(index !== undefined){
                          this.questionList[index].alreadyAns=this.answerList[i];
                        }
	                    }

                    	//console.log("所有数据============");
                    	 this.logJSON(this.answerList);
                    	// //console.log(this.momoList);
                    	this.logJSON(this.questionList);

                    	this.setItem();

                }, (err) => {
                    this.loading = false;
                    this.show2 = true;
                    this.warntext = "网络不给力,请稍后再试...";
                    //console.log(err);
                })


            },

            // 提交答案
            submitPro: function (event) {
            		this.loading = true;
            		//console.log("this.questionAllData.xtype======",this.questionAllData.xtype);
                if (this['showPro' + this.questionAllData.xtype + 'Arr'].length == 0) {
                    this.show2 = true;
                    this.loading = false;
                    this.warntext = "请选择选项";
                } else {
                    var params = {
                        questionId: event.target.attributes["data-id"].nodeValue,
                        studentNo: this.studentNo,
                        sysCode: this.sysCode,
                        choseAnswer: this['showPro' + this.questionAllData.xtype + 'Arr'].toString(),
                        type:"practice",
                    };

                    apiService.submitAnswer(params).then((data) => {
                    		//console.log("这是提交返回数据=====》");
                    		this.logJSON(data);
                        this.loading = false;
                        //错误处理
                        if(data.data.code == 500){
                          this.show2 = true;
                          this.warntext = "提交失败,请稍后再试...";
                          return;
                        }
                        //显示分析
                        this.submitBtn = false;
                        this.analysis = true;
                        this.resetBtn = true;
                        // questionAllData===》是否已答
                        this.questionAllData.xanswerType = true;

                        // checkbox 不能选择
                       	this.cancelCheckbox();

                        // xanswer====》答案数组
                        this.questionAllData.xanswerArr = this['showPro' + this.questionAllData.xtype + 'Arr'];

                        // 设置答案
                        this.setAns();

                        //console.log("选中的答案" + this.questionAllData.xanswerArr);
                        //console.log("正确的答案" + this.questionAllData.rightAnswer);


                        // 本地设置alreadyAns对象里面需要的字段
                        var needObj = {
                        	choseAnswer: this.questionAllData.xanswerArr,
                        	rightAnswer: this.questionAllData.rightAnswer,
                        	questionId: this.questionAllData.id,
                        	id: data.data.data
                        };
                        //console.log("needobj",needObj);
                        this.questionAllData.alreadyAns = needObj;


                        // 设置答题者是否选中正确答案
                        if(this.questionAllData.xanswerArr.toString()==this.questionAllData.rightAnswer.toString()){
                        	//console.log("isrightanswer");
                        	// 选中了正确答案
                        	this.questionAllData.alreadyAns.isRight = true;

                        	this.rightCount += 1;
                        	// if(this.unAnswerCount>=1){
                        		this.unAnswerCount -=1;
                        	// }else{
                        		// this.unAnswerCount = 0;
                        	// }

                        }else{
                        	//console.log("iswronganswer");
                        	// 选中了错误答案
                        	this.questionAllData.alreadyAns.isRight = false;
                        	this.wrongCount += 1;
                        	// if(this.unAnswerCount>=1){
                        		this.unAnswerCount -=1;
                        	// }else{
                        		// this.unAnswerCount = 0;
                        	// }
                        };

                    }, (err) => {
                        this.loading = false;
                        this.show2 = true;
                        this.warntext = "网络不给力,请稍后再试...";
                        //console.log(err);
                    })
                    //console.log("this.questionAllDataww======>");
                    //console.log(this.logJSON(this.questionAllData));
                }

            },

            setItem: function () {
                //console.log("setItem====>" + this.proIndex);
                // 这里设置所有选择项不显示，下面再设置某一项显示
                for(var i=0;i<3;i++){
                	this['showPro' + i] = false;
                }

                //显示题目信息
                this.questionAllData = this.questionList[this.proIndex];

                //设置正确答案描述
                this.questionAllData.desAnswer = this.questionAllData.rightAnswer.replace(/option/g, "").replace(/,/g, " ");
                //console.log("描述答案============>"+this.questionAllData.desAnswer);


                //拼接图片url
                if(this.questionAllData.image !== ""){
                  this.imgUrl = true;
                  this.questionAllData.imgUrl = window.location.origin + this.questionAllData.image;
                }else{
                  this.imgUrl = false;
                };

                //console.log("this.questionAllData========>");
                this.logJSON(this.questionAllData);

                var xtype = this.questionAllData.xtype;
                // 上面设置所有选择项不显示，这里再设置某一项显示
                this['showPro' + xtype] = true;

                // 删除已答class
                this.delectAnsClass();

                //判断是否已经答过该题目
                if (this.questionAllData.alreadyAns.isRight !== undefined) {
                		//已经答题
                		//console.log(this.questionAllData.alreadyAns.isRight);
                		//console.log("已经答题");
                   	this.submitBtn = false;
                   	this.analysis = true;
                   	this.resetBtn = true;
                   	// 关闭checkbox
                   	this.cancelCheckbox();
                   	// 设置已答
               	    this.setAlAns();
                }else{
                		//console.log("没有答题");
                   	this.submitBtn = true;
                   	this.analysis = false;
                   	this.resetBtn = false;
                   	// checkbox能选
                   	this.openCheckbox();
                }
            },

            // 判断对象是否为空
            judgeObj: function (obj){

					　　for(var i in obj){//如果不为空，则会执行到这一步，返回true
					　　　　return true;
					 　　};
					　　 return false;
					 	},

					 	// 设置已答（this.questionAllData里面拿数据）
					 	setAns: function(){
					 		  var temp = ["A", "B", "C", "D"];
                var itemData = this.questionAllData;
                var selectList = itemData.xanswerArr;
                var rightList = itemData.rightAnswer;
                // //console.log("selectList&&rightList=========");
                // //console.log(selectList);
                // //console.log(rightList);
                //遍历A-D这4个答案
                for (var i = 0; i < 4; i++) {
                    var optionName = "option" + temp[i];
                    if (itemData[optionName] != "") {//判断该题目中是否有这个选项
                    // //console.log(selectList.indexOf(optionName));
                    ////console.log(rightList.indexOf(optionName));
                        if (selectList.indexOf(optionName) != -1) {
                        		//判断是否选中了这个选项
                            if (rightList.indexOf(optionName) != -1) {
                              //判断是否是正确答案
                              this["answerChoose" + i] = ["trueAns"];
                            }else{
                              this["answerChoose" + i] = ["wrongAns"]
                            }
                        }
                        else {
                          if (rightList.indexOf(optionName) != -1) {
                            if(this.questionAllData.xtype == 2){
                              this["answerChoose" + i] = ["trueAns2"];
                            }

                          }
                        }
                    }
                }
					 	},


					 	// 设置已答（alreadyAns里面拿数据）
					 	setAlAns: function(){
					 		var temp = ["A", "B", "C", "D"];
              var itemData = this.questionAllData;
              var selectList = itemData.alreadyAns.choseAnswer;
              var rightList = itemData.alreadyAns.rightAnswer;
              //遍历A-D这4个答案
              for (var i = 0; i < 4; i++) {
                  var optionName = "option" + temp[i];
                  if (itemData[optionName] != "") {//判断该题目中是否有这个选项
                      // //console.log(selectList.indexOf(optionName));
                      // //console.log(rightList.indexOf(optionName));
                      if (selectList.indexOf(optionName) != -1) {
                      		//判断是否选中了这个选项
                          if (rightList.indexOf(optionName) != -1) {
                            //判断是否是正确答案
                            this["answerChoose" + i] = ["trueAns"];
                          }else {
                            this["answerChoose" + i] = ["wrongAns"];
                          }
                      }  else {
                        if (rightList.indexOf(optionName) != -1) {
                          if(this.questionAllData.xtype == 2){
                            this["answerChoose" + i] = ["trueAns2"];
                          }

                        }
                      }

                  }
              }
					 	},


            popState: function () {
                var state = {
                    title: "title",
                    url: "#"
                };
                window.history.pushState(state, "title", "#");
            },

             //上一题
            preItem: function () {
            		if(this.proIndex > 0 && this.proIndex<this.questionList.length){
                	this.proIndex--;
              	  this.setItem();
              	}
            },

            //下一题
            nextItem: function () {
            	if(this.proIndex >= 0 && this.proIndex<this.questionList.length-1){
            			this.proIndex++;
                this.setItem();
            	};

            },

            // 跳题
            jumpItem: function(){
            	var jumpIndex = document.querySelector("#jumpItem").value;

            	if(jumpIndex!==""&&jumpIndex >= 0 && jumpIndex<=this.questionList.length){
            			this.proIndex = jumpIndex - 1;
            			this.showMenu = false;
                	this.setItem();
            	}else{
            		this.show2 = true;
            		this.warntext = "请输入正确题号";
            		return;
            	};
            },

            //  模版跳题
            tempJumpItem: function(index){
              //console.log(index);
              var jumpIndex = index + 1;

              if(jumpIndex!==""&&jumpIndex >= 0 && jumpIndex<=this.questionList.length){
                this.proIndex = jumpIndex - 1;
                this.showMenu = false;
                this.setItem();
              }else{
                this.show2 = true;
                this.warntext = "请输入正确题号";
                return;
              };
            },

            // checkbox 不能选择
            cancelCheckbox: function(){
              var checkbox = document.querySelector(".type" + this.questionAllData.xtype);
              var allcheckbox = checkbox.querySelectorAll(".checkbox");
              for (var i = 0; i < allcheckbox.length; i++) {
                  allcheckbox[i].disabled = true;
              }
            },

            // checkbox 可以选择
            openCheckbox: function(){
              var checkbox = document.querySelector(".type" + this.questionAllData.xtype);
              var allcheckbox = checkbox.querySelectorAll(".checkbox");
              for (var i = 0; i < allcheckbox.length; i++) {
                  allcheckbox[i].disabled = false;
              }
            },

            // 删除已答class样式
            delectAnsClass: function(){
              	var checkbox = document.querySelector(".type" + this.questionAllData.xtype);
                var allcheckbox = checkbox.querySelectorAll(".checkbox");

                for (var i = 0; i < allcheckbox.length; i++) {
                    allcheckbox[i].className = allcheckbox[i].className.replace(new RegExp( "(\\s|^)" + "trueAns" + "(\\s|$)" )," " ); // replace 方法是替换
                    allcheckbox[i].className = allcheckbox[i].className.replace(new RegExp( "(\\s|^)" + "wrongAns" + "(\\s|$)" )," " );
                    allcheckbox[i].className = allcheckbox[i].className.replace(new RegExp( "(\\s|^)" + "trueAns2" + "(\\s|$)" )," " );

                    allcheckbox[i].checked = false;
                };
            },

            // 输出专用
            logJSON: function (data) {
                function innerLog(data) {
                    var temp = {};
                    for (var p in data) {
                        ////console.log(p + "    " + data[p] + "    " + typeof data[p]);
                        if (typeof data[p] == "object") temp[p] = innerLog(data[p]);
                        else temp[p] = data[p];
                    }
                    return temp;
                }

                //console.log(innerLog(data));
            },

            // 显示菜单栏
            showTemp: function(){
            	this.showMenu = true;
            	this.tempData = this.questionList;

            	for(var i=0,length=this.tempData.length;i<length;i++){
            		var item = this.tempData[i];
            		if(item.alreadyAns.isRight==null){

									item.alreadyAns.classType="";
									var temp = {};
                        Object.assign(temp, this.tempData[i]);
                        this.tempData.$set(i, temp);

            		}else if(item.alreadyAns.isRight){

									// var  temp = item;
									// temp.alreadyAns.classType="passActive";
									item.alreadyAns.classType="passActive";
									var temp = {};
                        Object.assign(temp, this.tempData[i]);
                        this.tempData.$set(i, temp);



            			// item.alreadyAns.classType="passActive";
            			// this.tempData.$set(item.alreadyAns.classType, i, "passActive")

            		}else {

            				// var  temp = item;
									// temp.alreadyAns.classType="notpassActive";
									// this.tempData.$set(i,temp);
										item.alreadyAns.classType="notpassActive";
									var temp = {};
                        Object.assign(temp, this.tempData[i]);
                        this.tempData.$set(i, temp);



            			// this.tempData.$set(item.alreadyAns.classType, i, "notpassActive");
            			// item.alreadyAns.classType="notpassActive";

            		}
            	};

            	//console.log("=============tempData===========")

            	this.logJSON(this.tempData);
            },

            onCancel: function(){
              this.showConfirm = false;
            },

            // 重置当前题目
            resetPro: function(){
                this.showConfirm = true;
                this.confirmText = "确定重置此条记录吗？";
            },

            //关闭重置
            onCancel: function(){
              this.showConfirm = false;
           },

         //确定重置
          onConfirm: function(){
            //console.log("onconfirm");
            this.logJSON(this.questionAllData);
            this.loading = true;
            apiService.deleteRecord({
              recordId: this.questionAllData.alreadyAns.id
            }).then((data) => {
              this.logJSON(data);
            this.loading = false;
            if(data.data.code == 200){
              this.show2 = true;
              this.warntext = "重置记录成功";

              // 答案数组设置为0
              this['showPro' + this.questionAllData.xtype + 'Arr'] = [];

              // 如果之前回答了正确答案
              if(this.questionAllData.alreadyAns.isRight == true){
                this.rightCount -= 1;
                this.unAnswerCount +=1;
              }else if(this.questionAllData.alreadyAns.isRight == false){
                this.wrongCount -= 1;
                this.unAnswerCount +=1;
              }else{
                //console.log("====3333333333=========")
              };


              this.questionAllData.alreadyAns = {};
              //console.log("this.questionAllData========");
              this.logJSON(this.questionAllData);
              this.delectAnsClass();
              this.openCheckbox();
              this.analysis = false;
              this.submitBtn = true;
              this.resetBtn = false;
            }else{
              this.show2 = true;
              this.warntext = "重置记录失败，请稍后重试";
              this.resetBtn = true;
            }

          }, (err) => {
              this.loading = false;
              this.show2 = true;
              this.warntext = "网络不给力,请稍后再试...";
              //console.log(err);
            })

          }

        },


    }
</script>

<style rel="stylesheet/scss" lang="scss" type="text/css">
    .page-xPracticedel {
        .contain {
            padding-bottom: 1rem;
            .contain-top {
                width: 100%;
                background: #ffffff;
                font-size: .8rem;
                padding: .5rem 0 0 .5rem;

                .wrongTitle {
                    overflow: hidden;
                    text-overflow: ellipsis;
                    display: -webkit-box;
                    /*-webkit-line-clamp: 1;*/
                    -webkit-box-orient: vertical;
                    word-break: break-word;
                    padding: .5rem 0 .5rem 0;

                    span {
                        width: 1rem;
                        height: 1rem;
                        line-height: 1rem;
                        border-radius: 50%;
                        background: #4489ca;
                        display: inline-block;
                        text-align: center;
                        color: #ffffff;
                        margin-right: .25rem;
                    }
                }

                .hardStar {
                    font-size: .6rem;
                    color: #b8b8b8;
                    padding-bottom: .5rem;
                    border-bottom: 1px solid #cccccc;

                    span:first-child {
                        margin-right: .3rem;
                    }

                    .vux-rater .vux-rater-box {
                        font-size: .8rem !important;
                        width: .8rem !important;
                        height: .8rem !important;
                    }
                }
            }

            .contain-middle {
                width: 100%;
                background: #ffffff;
                font-size: .8rem;
                padding: .5rem;

                img{
                  width: 80%;
                  margin-left: 10%;
                  margin-top: 10px;
                }

                .box {
                    border: 1px solid #cccccc;
                    margin-top: .5rem;

                    li {
                        list-style: none;
                        padding: .5rem;
                        padding-right: 1.2rem;
                        border-bottom: 1px solid #cccccc;
                        position:relative;

                        label {

                                word-break: break-all;
                                display: inline-block;
                                width: 96%;


                        }

                        .checkbox {
                            display: inline-block;
                            float: right;
                            width: .8rem;
                            height: .8rem;
                            -webkit-appearance: none;
                            border: 1px solid #cccccc;
                            position: absolute;
                            right: 2%;
    												top: 28%;
                        }
                        .checkbox:checked {
                            background: url(../assets/images/icon_answer_choose.png) center center no-repeat;
                            background-size: 100% 100%;
                        }

                    }

                    li:last-child {
                        border-bottom: none;
                    }
                }

                .type0 {
                    li {
                        .checkbox {
                          border-radius: 50%;
                        }
                        .checkbox:checked {
                            background: url(../assets/images/icon_answer_choose.png) center center no-repeat;
                            background-size: 100% 100%;

                        }
                    }
                }

                .type1 {
                    li {
                        .checkbox {
                          border-radius: 50%;
                        }
                        .checkbox:checked {
                            background: url(../assets/images/icon_answer_choose.png) center center no-repeat;
                            background-size: 100% 100%;
                        }
                    }
                }

                .type2 {
                    li {
                        .checkbox {
                        }
                        .checkbox:checked {
                            background: url(../assets/images/icon_answer_choose.png) center center no-repeat;
                            background-size: 100% 100%;
                        }
                    }
                }

                .trueAns.trueAns.trueAns.trueAns.trueAns.trueAns.trueAns.trueAns.trueAns {
                    background: url(../assets/images/icon_answer_right.png) center center no-repeat;
                    background-size: 100% 100%;
                }


                .trueAns2.trueAns2.trueAns2.trueAns2.trueAns2.trueAns2.trueAns2 {
                    background: url(../assets/images/icon_answer_choose.png) center center no-repeat;
                    background-size: 100% 100%;
                }

                .wrongAns.wrongAns.wrongAns.wrongAns.wrongAns.wrongAns.wrongAns.wrongAns {
                    background: url(../assets/images/icon_answer_wrong.png) center center no-repeat;
                    background-size: 100% 100%;
                }

            }

            .contain-foot {
                width: 100%;
                background: #ffffff;
                font-size: .8rem;
                padding: .5rem .5rem 10rem .5rem;

                p:first-child {
                    border-top: 1px solid #cccccc;
                    padding: .5rem 0;
                }
                p:nth-child(2) {
                    word-break: break-all;
                    color: #999999;
                }
            }

            .submit {
                width: 94%;
                height: 2.5rem;
                line-height: 2.5rem;
                text-align: center;
                font-size: 1rem;
                background: #f39801;
                color: #ffffff;
                margin: 1rem auto 6rem auto;
                border-radius: .2rem;
            }

            footer {
                width: 100%;
                height: 2.75rem;
                line-height: 2.75rem;
                background: #4489ca;
                color: #ffffff;
                position: fixed;
                left: 0;
                bottom: 0;
                padding: 0 0.5rem 0 .5rem;

                .goPage {
                    height: 100%;
                    position: absolute;
                    left: 30%;
                    display: inline-block;
                    padding: 0 .5rem;

                    span {
                        margin: .2rem .6rem 0 .6rem;
                        font-size: .8rem;
                    }

                    img {
                        width: 1rem;
                        vertical-align: middle;
                    }
                }

                .reset{
                	    position: absolute;
									    left: 69%;
									    font-size: .8rem;
									    height: 2.8rem;
                }

                .menu {
                    width: 3rem;
                    height: 100%;
                    display: inline-block;
                    background: url(../assets/images/btn_menu.png) no-repeat center center;
                    background-size: 1.6rem 1.6rem;
                    position: absolute;
                    right: 0;
                }
                .conRight {
                    float: left;
                    width: 4rem;
                    letter-spacing: -2px;
                    font-size: .7rem;
                    height: 2.75rem;
                    line-height: .75rem;
                    padding-top: .8rem;
                    span{
                    	letter-spacing: 1px;
                    }
                }
            }
        }

        .mask {
            position: fixed;
            left: 0;
            top: 0;
            width: 100%;
            height: 100%;
            background: rgba(0, 0, 0, .5);
            font-size: .8rem;
            .problemDetail {
                padding: .5rem;
                width: 100%;
                background: #ffffff;
                height: 20rem;
                position: absolute;
                left: 0;
                bottom: 0;

                .mask_top {
                    width: 100%;
                    height: 2rem;
                    line-height: 2rem;

                    span {
                        margin-left: .8rem;
                    }

                    span:last-child {
                        float: right;
                        color: #4388c9;
                        margin: 0;
                    }
                }

                .mask_middle {
                    .mask_middle_ul {
                        overflow: auto;
                        height: 14.5rem;
                        border-top: 1px #dddddd solid;
                        border-bottom: 1px #dddddd solid;
                        li {
                            list-style: none;
                            float: left;
                            width: 2rem;
                            height: 2rem;
                            line-height: 2rem;
                            text-align: center;
                            color: #ffffff;
                            border-radius: .3rem;
                            background: #cccccc;
                            margin-left: .8rem;
                            margin-top: .75rem;
                        }
                        li.passActive {
                            background: #80c26a;
                        }
                        li.notpassActive {
                            background: #e15148;
                        }
                    }
                }

                .mask_foot {
                    height: 3rem;
                    line-height: 3rem;
                    font-size: .8rem;

                    span: first-child {
                    }

                    input {
                        width: 10rem;
                        height: 2rem;
                        border: 1px solid #dddddd;
                        border-radius: .3rem;
                        font-size: .8rem;
                        padding-left: .5rem;
                    }
                ;

                    .sure {
                        width: 3.75rem;
                        height: 2rem;
                        line-height: 2rem;
                        text-align: center;
                        border: 1px solid #dddddd;
                        display: inline-block;
                        border-radius: .3rem;
                        margin-left: .5rem;
                    }

                }

            }
        }

    }
</style>




























