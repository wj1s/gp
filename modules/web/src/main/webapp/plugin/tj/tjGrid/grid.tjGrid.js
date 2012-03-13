(function($) {
	/*以下是调用.form方法，为form统一创建验证*/
	$.fn.grid = function(options){
		if (this.length==1&&$(this).data('tjGrid') == undefined) {
			var tjGrid=new TjGrid(this,options);
			tjGrid.load();
			$(this).data('tjGrid',tjGrid);
		}
    };
    
    $.fn.tjSearch = function(url){
		if (this.length==1&&$(this).data('tjGrid') != undefined) {
			if ($(this).find('*[id="grid-table-'+ $(this).attr('id')+'"]').length > 0){
				$(this).find('*[id="grid-table-'+ $(this).attr('id')+'"]').jqGrid('setGridParam',{url:url,page:1}).trigger("reloadGrid");
			}
		}
    };
    
    $.fn.tjSearchPost = function(url, options){
    	if (this.length==1&&$(this).data('tjGrid') != undefined) {
    		if ($(this).find('*[id="grid-table-'+ $(this).attr('id')+'"]').length > 0){
    			$(this).find('*[id="grid-table-'+ $(this).attr('id')+'"]').jqGrid('setGridParam',{url:url,page:1,'postData':options}).trigger("reloadGrid");
    		}
    	}
    };
    
    //grid对象
	function TjGrid(target,options){
		this.id=$(target).attr('id');
		this.target=target;
		this.name=options.name;
		this.tjColModel=[];
		this.operation = $.extend({add:true,edit:true,del:true}, options.operation || {});
		this.viewDetail = $.extend({view:false}, options.viewDetail || {});
		this.loadUrl = $.extend({content:this.getLoadUrl()}, options.loadUrl || {}); //分页查询url
		this.saveUrl = $.extend({content:this.getSaveUrl()}, options.saveUrl || {}); //新增或编辑url
		this.multiEditUrl = $.extend({content:this.getMultiEditUrl()}, options.multiEditUrl || {}); //新增或编辑url
		this.delUrl = $.extend({content:this.getDelUrl()}, options.delUrl || {}); //删除url
		this.inputUrl = $.extend({content:'',width:'',type:'',buttonName:''}, options.inputUrl || {}); //新增编辑页面的url
		this.viewUrl = $.extend({content:'',width:'',type:'',buttonName:''}, options.viewUrl || {}); //查看细节页面的url
		this.relateArray = [];//用来保存级联的属性
		for ( var i in options.colModel) {
			this.tjColModel.push(new TjColModel(options.colModel[i]));
		}
		if (typeof(options.gridType) != 'undefined' && options.gridType == 'easy'){
			options.setting = {edit:false,search:false,multiselect:false,toolbar:[false,"both"]};
		}
		if (typeof(options.gridType) != 'undefined' && options.gridType == 'onlyCheck'){
			options.setting = {edit:false,search:false,multiselect:false,toolbar:[true,"both"]};
			this.viewDetail = {view:true};
		}
		if (typeof(options.gridType) != 'undefined' && options.gridType == 'noSearch'){
			options.setting = {search:false,multiselect:false,toolbar:[true,"both"]};
			this.viewDetail = {view:true};
		}
		this.tjGridView=new TjGridView(this,options.setting);
		//根据后台传过来的CRUD按钮是否可见，给operation的增删改属性赋值[add,edit,del]
		var crudType = options.crudType;
		if (crudType != undefined){
			var crudArray = crudType.split('-');
			if (crudArray[0] == 0){
				this.operation.add = false;
			}else {
				this.operation.add = true;
			}
			if (crudArray[1] == 0){
				this.operation.edit = false;
			}else {
				this.operation.edit = true;
			}
			if (crudArray[2] == 0){
				this.operation.del = false;
			}else {
				this.operation.del = true;
			}
		}
		return this;
    }
	

	
	$.extend(TjGrid.prototype, {
		load:function(){
			this.tjGridView.createView();
		},
		//name为被关联的下拉选的id如dept，value为关联的下拉选的id如group
		relateAjaxFun: function(obj) {
			var relatedVal = $('*[id="'+obj.relatedName+'"]').tjVal();
			$.ajax({url:obj.url + '?id=' + relatedVal + '&rd=' + Math.random(),
			    type:'post',
			    async:false,
			    dataType:'json',
				success:function(data){
					$('*[id="'+obj.relateName+'"]').tjEmpty();
					if (data.result == true){
						if (data.data != ''){
							var array = eval(data['data']);
							var html = '';
							if (obj.relateNotNull != 'notNull'){
								html += '<li val=-1>无</li>';
							}
							for(var i in array){
								html += '<li val="'+array[i].value+'">'+array[i].name+'</li>';
							}
							$('*[id="'+obj.relateName+'"] ul').append(html);
							$('*[id="'+obj.relateName+'"]').tjAssemble();
						}
					}else {
						if (obj.relateNotNull != 'notNull'){
							var html = '<li val=-1>无</li>';
							$('*[id="'+obj.relateName+'"] ul').append(html);
							$('*[id="'+obj.relateName+'"]').tjAssemble();
						}else {
							tjAlert('没有任何关联数据，请先添加数据！');
						}
					}
			    }
			});
		},
		getLoadUrl:function(){
			var thisUrl = window.location.href;
			var loadUrl = thisUrl.substring(0,thisUrl.lastIndexOf('!')) + '!load.action';
			return loadUrl;
		},
		getSaveUrl:function(){
			var thisUrl = window.location.href;
			var saveUrl = thisUrl.substring(0,thisUrl.lastIndexOf('!')) + '!save.action';
			return saveUrl;
		},
		getDelUrl:function(){
			var thisUrl = window.location.href;
			var delUrl = thisUrl.substring(0,thisUrl.lastIndexOf('!')) + '!delete.action';
			return delUrl;
		},
		getMultiEditUrl:function(){
			var thisUrl = window.location.href;
			var multiEditUrl = thisUrl.substring(0,thisUrl.lastIndexOf('!')) + '!multiEdit.action';
			return multiEditUrl;
		}
	});
	
	//colModel对象
    function TjColModel(options){
		this.options = $.extend({
			name:  '',//标示
			header:'',//标头
			sortable:true,
			editable:true,
			verify:null,
			width:0,
			hidden:false,
			width:0,
			//是否提交数据
			canSubmit:true,
			//输入框的默认值
			defaultVal:'',
			//下拉选使用
			inputType:'input',
			items:[],
			//这两个属性日期框架使用
			dateFmt:null,
			now:null,
			//这两个属性级联下拉选使用
			related:'',//被关联的属性比如dept
			relate:'',//关联的属性比如group
			relateUrl:''//关联的url
	    }, options || {});
		return this;
	}
	
	function TjGridView(tjGrid,setting) {
		this.tjGrid = tjGrid;
		var id=this.tjGrid.id;
		//this.dan='grid-dan-'+id;//添加按钮ID
		// this.duo='grid-duo-'+id;//添加按钮ID
		this.viewId='grid-view-'+id;//查看按钮ID
		this.addId='grid-add-'+id;//添加按钮ID
		this.editId='grid-edit-'+id;//编辑按钮ID
		this.delId='grid-del-'+id;//删除按钮ID
		this.gridTableId='grid-table-'+id;//grid表格ID
		this.pageBarId='grid-page-'+id;//分页栏ID
		this.dialogId='grid-input-'+id;//编辑窗口ID
		this.multiEditDialogId='grid-multi-dialog-'+id;//多选编辑窗口ID
		this.multiFormId='grid-multi-form-'+id;//多选编辑表单ID
		this.multiSaveBtnId='grid-multi-saveBtn-'+id;//多选编辑保存按钮ID
		this.multiCloseBtnId='grid-multi-closeBtn-'+id;//多选编辑关闭按钮ID
		this.detailId='grid-detail-'+id;//细节窗口ID
		this.detailCloseId='grid-detail-close-'+id;//细节窗口关闭ID
		this.detailTdPrefix='grid-detail-'+id+'-';//细节TD的ID
		this.formId='grid-form-'+id;//编辑页面FORMID
		this.closeId='grid-form-close-'+id;//编辑页面关闭按钮
		this.messageBoxId='grid-message-box';
		this.inputPrefix='grid-input-'+id+'-';
		this.jqgrid=null;
		this.setting = $.extend({
			edit:true,
			search:true,
			multiselect:true,
			toolbar:[true,"both"]
	    }, setting || {});
	}
	$.extend(TjGridView.prototype, {
		getColNamesForJqGrid:function(){
			var colNames=[];
			$.each(this.tjGrid.tjColModel,function(index, value){
				colNames.push(value.options.header);
			});
			return colNames;
		},
		getcolModelsForJqGrid:function(){
			var colModels=[];
			$.each(this.tjGrid.tjColModel,function(index, value){
				colModels.push({
					name:value.options.name,
					index:value.options.name,
					align:'center',
					sortable:value.options.sortable,
					editable:value.options.editable,
					hidden:value.options.hidden,
					width:value.options.width
				});
			});
			return colModels;
		},
		createView: function(){
			$(this.tjGrid.target).addClass('gridDiv');
			$(this.tjGrid.target).append(this.createForm());
			$(this.tjGrid.target).after(this.createInput());
			$('#'+this.dialogId).find('#continual-add-div input[name="checkbox"]').checkbox();//这里给连续添加单选按钮初始化
			$(this.tjGrid.target).after(this.createMultiEdit());
			$(this.tjGrid.target).after(this.createDetail());
			this.createMessageBox();
			
			var $this=this;
			var colNames=this.getColNamesForJqGrid();
			var colModels=this.getcolModelsForJqGrid();
			this.jqgrid=$('#'+this.gridTableId).jqGrid({ 
				url: this.tjGrid.loadUrl.content,
				colNames:colNames,
				colModel:colModels,  	  	   	   	  
				datatype: 'json',
				mtype:'post',
				rowNum:10,
			    viewrecords: true,  
			    jsonReader: {  
		            root:"data",
		            repeatitems : false
		        }, 
				rownumbers: true,
				gridview: true,
				height: '100%', 	
				pager: '#'+this.pageBarId,
				rowList:[10,20,30],
				sortorder: 'asc',
				caption: this.tjGrid.name,
				toolbar : this.setting.toolbar,
				multiselect: this.setting.multiselect
			});
			this.createToolbar();
			$.init_style();
			this.bindEvent();
			this.initForm();
			
			//下拉选级联事件
			var relateArray = [];
			for(var i in this.tjGrid.tjColModel){
				if (this.tjGrid.tjColModel[i].options.relate != ''){
					var relateObj = {};
					var relateName = this.tjGrid.tjColModel[i].options.name;
					relateObj.relatedName = this.tjGrid.tjGridView.dialogId+'-'+relateName;
					relateObj.url = this.tjGrid.tjColModel[i].options.relateUrl;
					for(var j in this.tjGrid.tjColModel){
						if (this.tjGrid.tjColModel[j].options.related == relateName){
							relateObj.relateName = this.tjGrid.tjGridView.dialogId+'-'+this.tjGrid.tjColModel[j].options.name;
							if (this.tjGrid.tjColModel[j].options.verify != undefined && this.tjGrid.tjColModel[j].options.verify.indexOf('NotNull') != -1){
								relateObj.relateNotNull = 'notNull';
							}
							break;
						}
					}
					relateArray.push(relateObj);
				}
			}
			this.tjGrid.relateArray = relateArray;
	    },
	    
	    bindRelateSelect:function(){
			var $this = this;
			$.each(this.tjGrid.relateArray, function(i, obj){
				$('*[id="'+obj.relatedName+'"]').tjChange(function(){
					$this.tjGrid.relateAjaxFun(obj);
				});
			});
	    },
	    
		bindEvent:function(){
	    	var $this=this;
	    	var dialog=function(){
				$('#'+$this.dialogId).dialog({
					resizable: false,
					autoOpen: false,
					height: 'auto',
					width: 325,
					modal: true,
					close: function(event, ui) {
						$('#'+$this.dialogId).dialog('destroy');
						$('#'+$this.dialogId).dialog('close'); 
						//关闭验证提示框
						$('#'+$this.formId).removeTip();
						//取消高亮显示
						$('#gbox_'+$this.gridTableId).find('tr[aria-selected="true"]').each(function(){
							$(this).trigger('click');
						});
						//关闭所有显示的下拉选
						$('*[id*="block"]').hide();
					}
				});
			};
			
			var multiEditdialog=function(){
				$('#'+$this.multiEditDialogId).dialog({
					resizable: false,
					autoOpen: false,
					height: 'auto',
					width: 400,
					modal: true,
					close: function(event, ui) {
						//关闭验证提示框
						$('#'+$this.formId).removeTip();
						//关闭下拉选框
						$('.selectTreeDIV').hide();
					}
				});
			};
	    	var detail=function(){
				$('#'+$this.detailId).dialog({
					resizable: false,
					autoOpen: false,
					height: 'auto',
					width: 325,
					modal: true,
					close: function(event, ui) {
						//取消高亮显示
						$('#gbox_'+$this.gridTableId).find('tr[aria-selected="true"]').each(function(){
							$(this).trigger('click');
						});
					}
				});	
			};
	    	$(window).bind('resize', function() {     
				$('#'+$this.gridTableId).setGridWidth($(window).width()-70); 
			}).trigger('resize');
	    	$('#'+this.gridTableId).jqGrid('navGrid','#'+this.pageBarId,{del:false,add:false,edit:false,search:false},{},{},{},{multipleSearch:true});
	    	if ($this.setting.search){
	    		$('#'+this.gridTableId).jqGrid('navButtonAdd','#'+this.pageBarId,{caption:"查询",title:"分类查询", buttonicon :'ui-icon-pin-s',
		    		onClickButton:function(){
		    			var jqgrid=$this.jqgrid;
		    			jqgrid[0].toggleToolbar();
		    			//把查找条件中的date或time类型换成my97date控件
		    			$('input[id^="gs"]').each(function(){
				    		var inputName = $(this).attr('id').substring(3);
				    		var tempInput = $('#grid-input-'+ $this.tjGrid.id + '-' + inputName);
				    		var attrStr = tempInput.attr('ztype');
				    		if (attrStr != undefined && (attrStr.indexOf('date') != -1 || attrStr.indexOf('time') != -1)){
				    			$(this).attr('ztype',tempInput.attr('ztype'));
				    		}
				    	});
		    			//重新初始化文本框
		    			$.init_style();
		    		} 
		    	});
	    	}
//	    	$('#'+this.gridTableId).jqGrid('navButtonAdd','#'+this.pageBarId,{caption:"Clear",title:"Clear Search",buttonicon :'ui-icon-refresh',
//	    		onClickButton:function(){
//	    			var jqgrid=$this.jqgrid;
//	    			jqgrid[0].clearToolbar()
//	    		} 
//	    	});
	    	$('#'+this.gridTableId).jqGrid('filterToolbar',{stringResult: true,searchOnEnter : true});
	    	(this.jqgrid)[0].toggleToolbar();

			$('#'+this.gridTableId).setGridWidth($(window).width()-68); 
	    	//查看细节按钮
	    	$('#'+this.viewId).click(function() {
	    		id=$('#'+$this.gridTableId).jqGrid('getGridParam','selrow');
				if(id==null){
					$this.message('请选择一条记录');
					return;
				}
				var val=$('#'+$this.gridTableId).getRowData(id);
				$.each($this.tjGrid.tjColModel,function(index, value){
					var name=$this.detailTdPrefix+value.options.name;
					var result;
					for(var p in val){
						if(p==value.options.name){
							result=val[p];
						}
					}
					$('*[id="'+name+'"]').html(result);
				});
				$('#'+$this.detailId).dialog('destroy');
				$('#'+$this.detailId).attr('title','查看'+$this.tjGrid.name);
				detail();
				$('#'+$this.detailId).dialog('open'); 
			});
			//新增按钮
			$('#'+this.addId).click(function() {
				$('#'+$this.formId).each(function(){
				       this.reset();
		        });
				if ($this.fillDefaultValues($this.tjGrid.tjColModel,$this) == 'success'){
					$('#'+$this.dialogId).dialog('destroy');
					$this.tjGrid.tjGridView.bindRelateSelect();
					$('#'+$this.dialogId).attr('title','新增'+$this.tjGrid.name);
					dialog();
					$('#'+$this.formId).valiForm();
					$('#'+$this.dialogId).find('#continual-add-div').show();
					$('#'+$this.dialogId).dialog('open'); 
				}
			});
			//编辑按钮
			$('#'+this.editId).click(function() {
				var id;
				//多选模式
				if($('#'+$this.gridTableId).jqGrid('getGridParam','multiselect')){
					var selIds=$('#'+$this.gridTableId).jqGrid('getGridParam','selarrrow');
					if(selIds.length==0){
						$this.message('请选择一条记录');
						return;
					}else if (selIds.length>1){
						tjAlert('多选编辑功能尚未开放!');
//						//多选编辑
//						$('#'+$this.multiEditDialogId).dialog('destroy');
//						$('#'+$this.multiEditDialogId).attr('title','是否批量编辑'+$this.tjGrid.name);
//						multiEditdialog();
//						$('#'+$this.formId).valiForm();
//						$('#'+$this.multiEditDialogId).dialog('open'); 
//						$('#'+$this.multiEditDialogId).find('input[name="checkbox"]').each(function(){
//							$(this).checkbox();
//						});
					}else{
						id=selIds[0];
						//单选编辑的赋值方法
						
						//先初始化
						if ($this.fillDefaultValues($this.tjGrid.tjColModel,$this) == 'success'){
							//初始化所有下拉选的级联
							$this.tjGrid.tjGridView.bindRelateSelect();
							
							//赋值
							var val=$('#'+$this.gridTableId).getRowData(id);
							$.each($this.tjGrid.tjColModel,function(index, value){
								var name=$this.inputPrefix+value.options.name;
								var editValue;
								for(var p in val){
									if(p==value.options.name){
										editValue=val[p];
									}
								}
								if (value.options.inputType == 'input'){
									$('*[id="'+name+'"]').val(editValue);
								} else if (value.options.inputType == 'select') {
				    				$('*[id="'+name+'"]').tjText(editValue);
				    				//这里是关键，因为班组是联动出来的，所以需要在编辑窗口开启之前，首先根据他的选项联动出关联下拉选的内容
				    				var relateArray = $this.tjGrid.relateArray;
				    				for(var i in relateArray){
				    					if (relateArray[i].relatedName == name){
				    						$('*[id="'+name+'"]').trigger('tjChange');
				    					}
				    				}
								} else if (value.options.inputType == 'checkbox') {
									var submitName = value.options.name;
									var index = submitName.indexOf('.');
					    			$('input[name="'+submitName.substring(0, index)+'_id"]').each(function(){
					    				$(this).attr('checked','');
					    				var array = editValue.split(',');
					    				for(var i in array){
					    					if (array[i] == $(this).next().text()){
					    						$(this).attr('checked', 'checked');
					    					}
					    				}
					    			});
								}
							});
						}
						$('#'+$this.dialogId).dialog('destroy');
						$('#'+$this.dialogId).attr('title', '编辑'+$this.tjGrid.name);
						dialog();
						$('#'+$this.formId).valiForm();
						$('#'+$this.dialogId).find('#continual-add-div').hide();
						$('#'+$this.dialogId).dialog('open'); 
					}
				//单选模式
				}else{
					id=$('#'+$this.gridTableId).jqGrid('getGridParam','selrow');
					if(id==null){
						$this.message('请选择一条记录');
						return;
					}
				}
			});
			
			//删除按钮
			$('#'+this.delId).click(function() {
				var selIds=[];
				//多选模式
				if($('#'+$this.gridTableId).jqGrid('getGridParam','multiselect')){
					selIds=$('#'+$this.gridTableId).jqGrid('getGridParam','selarrrow');
					if(selIds.length==0){
						$this.message('请选择要删除的数据');
						return;
					}
				//单选模式
				}else{
					id=$('#'+$this.gridTableId).jqGrid('getGridParam','selrow');
					if(id==null){
						$this.message('请选择一条记录');
						return;
					}
					selIds.push(id);
				}
				var delIds = [];
				var p = 0;
				for(var i in selIds){
					var eachId = {};
					eachId.id = selIds[i];
					delIds.push(eachId);
				}
				tjConfirm('确认要删除所选的'+selIds.length+'条数据?',function(result){
					if (result){
						$.ajax({
						  type: "POST",
						  url: $this.tjGrid.delUrl.content,
						  data:{delIds:$.toJSON(delIds)},
						  dataType: "json",
						  success:function(data){
								if (data.result == true){
									if (data.msg != undefined){
										$('#'+$this.gridTableId).jqGrid().trigger("reloadGrid");
										$('#tb_'+ $this.gridTableId).html('<font color="#ffb400">'+data.msg+'</font>');
									}else {
										$('#'+$this.gridTableId).jqGrid().trigger("reloadGrid");
										$('#tb_'+ $this.gridTableId).html('<font color="#ffb400">删除成功!</font>');
									}
								}else {
									if (data.msg != undefined){
										$('#tb_'+ $this.gridTableId).html('<font color="#ffb400">'+data.msg+'</font>');
									}else {
										$('#tb_'+ $this.gridTableId).html('<font color="#ffb400">删除失败!系统中有关联数据，无法删除!</font>');
									}
								}
						 	}
						});
					}
				});
			});
			
			//新增编辑窗口的关闭按钮
			$('#'+this.closeId).click(function() {
				$('#'+$this.dialogId).dialog('destroy');
				$('#'+$this.dialogId).dialog('close'); 
				//关闭验证提示框
				$('#'+$this.formId).removeTip();
				//取消高亮显示
				$('#gbox_'+$this.gridTableId).find('tr[aria-selected="true"]').each(function(){
					$(this).trigger('click');
				});
				//关闭所有显示的下拉选
				$('*[id*="block"]').hide();
			});
			
			//查看细节按钮的关闭
			$('#'+this.detailCloseId).click(function() {
				$('#'+$this.detailId).dialog('destroy');
				$('#'+$this.detailId).dialog('close'); 
			});
			
			//自定义新增编辑页面
			if ($this.tjGrid.inputUrl.content != ''){
				if ($this.tjGrid.inputUrl.type == ''){
					$('#'+this.addId).unbind('click');
					var aHtml = '<a ztype="popUp" title="新增'+$this.tjGrid.name+'"';
					if ($this.tjGrid.inputUrl.width != ''){
						aHtml += ' zwidth="'+$this.tjGrid.inputUrl.width+'" ';
					}
					aHtml += ' url="'+$this.tjGrid.inputUrl.content+'"></a>';
					$('#'+this.addId).before(aHtml);
					$('#'+this.addId).click(function(){
						$(this).prev().trigger('click');
					});
					var id='';
					$('#'+this.editId).unbind('click');
					var eHtml = '<a ztype="popUp" title="编辑'+$this.tjGrid.name+'" url="'+$this.tjGrid.inputUrl.content +'"';
					if ($this.tjGrid.inputUrl.width != ''){
						eHtml += ' zwidth="'+$this.tjGrid.inputUrl.width+'" ';
					}
					eHtml += '></a>';
					$('#'+this.editId).before(eHtml);
					var editBtnId = this.editId;
					$('#'+this.editId).click(function(){
						//多选模式
						if($('#'+$this.gridTableId).jqGrid('getGridParam','multiselect')){
							var selIds=$('#'+$this.gridTableId).jqGrid('getGridParam','selarrrow');
							if(selIds.length==0){
								$this.message('请选择一条记录');
								return;
							}else if (selIds.length>1){
								$this.message('不支持多条编辑，请选择一条记录');
								return;
							}else{
								id=selIds[0];
								var urlTemp = $('#'+editBtnId).prev().attr('url');
								if (urlTemp.indexOf('?') != -1){
									var url = urlTemp.substring(0, urlTemp.indexOf('?'));
								}else {
									var url = urlTemp;
								}
								$('#'+editBtnId).prev().attr('url', url + '?id=' +id);
							}
						//单选模式
						}else{
							id=$('#'+$this.gridTableId).jqGrid('getGridParam','selrow');
							if(id==null){
								$this.message('请选择一条记录');
								return;
							}else {
								var urlTemp = $('#'+editBtnId).prev().attr('url');
								if (urlTemp.indexOf('?') != -1){
									var url = urlTemp.substring(0, urlTemp.indexOf('?'));
								}else {
									var url = urlTemp;
								}
								$('#'+editBtnId).prev().attr('url', url + '?id=' + id);
							}
						}
						$(this).prev().trigger('click');
					});
					$('.gridButton').init_style();
				}else if ($this.tjGrid.inputUrl.type == 'redirect'){
					$('#'+this.addId).unbind('click');
					$('#'+this.addId).click(function(){
						window.location.href = $this.tjGrid.inputUrl.content;
					});

					$('#'+this.editId).unbind('click');
					$('#'+this.editId).click(function(){
						//多选模式
						if($('#'+$this.gridTableId).jqGrid('getGridParam','multiselect')){
							var selIds=$('#'+$this.gridTableId).jqGrid('getGridParam','selarrrow');
							if(selIds.length==0){
								$this.message('请选择一条记录');
								return;
							}else if (selIds.length>1){
								$this.message('不支持多条编辑，请选择一条记录');
								return;
							}else{
								id=selIds[0];
								window.location.href = $this.tjGrid.inputUrl.content + '?id=' +id;
							}
						//单选模式
						}else{
							id=$('#'+$this.gridTableId).jqGrid('getGridParam','selrow');
							if(id==null){
								$this.message('请选择一条记录');
								return;
							}else {
								window.location.href = $this.tjGrid.inputUrl.content + '?id=' +id;
							}
						}
					});
				}
			}
			
			//自定义查看详细信息页面
			if ($this.tjGrid.viewUrl.content != ''){
				if ($this.tjGrid.viewUrl.type == ''){
				$('#'+this.viewId).unbind('click');
				var aHtml = '<a id="toView" ztype="popUp" ';
				if ($this.tjGrid.viewUrl.title != undefined && $this.tjGrid.viewUrl.title != ''){
					aHtml += 'title="'+this.tjGrid.viewUrl.title+'"';
				}else {
					aHtml += 'title="查看'+$this.tjGrid.name+'" ';
				}
				if ($this.tjGrid.viewUrl.width != undefined && $this.tjGrid.viewUrl.width != ''){
					aHtml += ' zwidth="'+$this.tjGrid.viewUrl.width+'" ';
				}
				if ($this.tjGrid.viewUrl.height != undefined && $this.tjGrid.viewUrl.height != ''){
					aHtml += ' zheight="'+$this.tjGrid.viewUrl.height+'" ';
				}
				if ($this.tjGrid.viewUrl.mode != undefined && $this.tjGrid.viewUrl.mode != ''){
					aHtml += ' zmode="'+$this.tjGrid.viewUrl.mode+'" ';
				}
				aHtml += ' url="'+$this.tjGrid.viewUrl.content+'"></a>';
				$('#'+this.viewId).before(aHtml);
				
				$('#'+this.viewId).data('postData',function(){
					var funName = 'viewPostFun';
		        	if (funName != undefined){
		        		var a = eval(funName+'()');
		        		return a;
		        	}
				});
				
				var viewBtnId = this.viewId;
				//只存在单选模式
				$('#'+this.viewId).click(function(){
					var id=$('#'+$this.gridTableId).jqGrid('getGridParam','selrow');
					if(id==null){
						$this.message('请选择一条记录');
						return;
					}else {
						var urlTemp = $('#'+viewBtnId).prev().attr('url');
						if (urlTemp.indexOf('?') != -1){
							var url = urlTemp.substring(0, urlTemp.indexOf('?'));
						}else {
							var url = urlTemp;
						}
						$('#'+viewBtnId).prev().attr('url', url + '?id=' + id);
						$(this).prev().trigger('click');
					}
				});
				 $('.gridButton').init_style();
			   }else if ($this.tjGrid.viewUrl.type == 'redirect'){
				   $('#'+this.viewId).unbind('click');
					$('#'+this.viewId).click(function(){
						window.location.href = $this.tjGrid.viewUrl.content;
					});
					$('#'+this.viewId).unbind('click');
					$('#'+this.viewId).click(function(){
						//多选模式
						if($('#'+$this.gridTableId).jqGrid('getGridParam','multiselect')){
							var selIds=$('#'+$this.gridTableId).jqGrid('getGridParam','selarrrow');
							if(selIds.length==0){
								$this.message('请选择一条记录');
								return;
							}else if (selIds.length>1){
								$this.message('不支持多条编辑，请选择一条记录');
								return;
							}else{
								id=selIds[0];
								window.location.href = $this.tjGrid.viewUrl.content + '?id=' +id;
							}
						//单选模式
						}else{
							id=$('#'+$this.gridTableId).jqGrid('getGridParam','selrow');
							if(id==null){
								$this.message('请选择一条记录');
								return;
							}else {
								window.location.href = $this.tjGrid.viewUrl.content + '?id=' +id;
							}
						}
					});

				}
			}
	    },
		createForm:function(){
	    	var html ='';
	    	html+='<table id="'+this.gridTableId+'" >';
	    	html+='</table>';
	    	html+='<div id="'+this.pageBarId+'"></div>';
			return html;
	    },
	    createToolbar: function(){
	    	var toolbarHtml = '';
    		toolbarHtml+='<div class="gridButton">';
	    	if(this.tjGrid.viewDetail.view){
	    		toolbarHtml+='<button id="'+this.viewId+'" type="button" ztype="button" width="325"><span class="style_viewButton">&nbsp;&nbsp;&nbsp;&nbsp;</span>';
	    		if(this.tjGrid.viewUrl.buttonName!='')
	    			{
	    			toolbarHtml+=this.tjGrid.viewUrl.buttonName+'</button>';
	    			}else
	    			{
	    				toolbarHtml+='查看</button>';    				
	    			}
	    			}
	    	if(this.setting.edit){
	    		if(this.tjGrid.operation.add){
	    			toolbarHtml+='<button id="'+this.addId+'" type="button" ztype="button" width="325"><span class="style_addButton">&nbsp;&nbsp;&nbsp;&nbsp;</span>新增</button>';
	    		}
	    		if(this.tjGrid.operation.edit){
	    			toolbarHtml+='<button id="'+this.editId+'" type="button" ztype="button" width="325"><span class="style_editButton">&nbsp;&nbsp;&nbsp;&nbsp;</span>';
	    			   if(this.tjGrid.inputUrl.buttonName!='')
	    				{
	    				toolbarHtml+=this.tjGrid.inputUrl.buttonName+'</button>';
	    				}else
	    				{
	    				toolbarHtml+='编辑</button>';
	    				}
	    			  }
	    		if(this.tjGrid.operation.del){
	    			toolbarHtml+='<button id="'+this.delId+'" type="button" ztype="button"><span class="style_deleteButton">&nbsp;&nbsp;&nbsp;&nbsp;</span>删除</button>';
	    		}
	    	}
	    	toolbarHtml+='</div>';
    		$('#t_' + this.gridTableId).append(toolbarHtml);
	    },
	    createDetail: function(){
	    	var html ='<div id="'+this.detailId+'" style="display:none;">';
	    	html +='<div style="text-align:center;"><table class="work_info_table" width="100%" border="0" cellpadding="0" cellspacing="0">';
	    	var $this=this;
	    	id=$('#'+$this.gridTableId).jqGrid('getGridParam','selrow');
	    	var val=$('#'+$this.gridTableId).getRowData(id);
	    	$.each(this.tjGrid.tjColModel,function(index, value){
	    		html += '<tr><td>' + value.options.header +'</td>';
				var result;
				for(var p in val){
					if(p==value.options.name){
						result=val[p];
					}
				}
	    		html += '<td id="'+$this.detailTdPrefix+value.options.name+'">'+result+'</td></tr>';
	    	});
			html+='</table></div>';
	    	html+='<div class="form_submitDiv">';
			html+='<input id="'+this.detailCloseId+'" type="button" value="关闭" ztype="button" />';
			html+='</div></div>';
			return html;
	    },
	    
	    createMultiEdit: function(){
	    	var html ='<div id="'+this.multiEditDialogId+'" style="display:none;">';
	    	html += '<form id="'+this.multiFormId+'" name="'+this.multiFormId+'" action="'+this.tjGrid.getMultiEditUrl.content+'" ztype="form" style="border:0;margin:0;padding:0;">';
	    	html += '<table class="work_info_table" border="0" cellpadding="0" cellspacing="0" width="100%">';
	    	var $this=this;
	    	var modelName = this.tjGrid.id.substring(0,this.tjGrid.id.indexOf('-'));
	    	$.each(this.tjGrid.tjColModel,function(index, value){
	    		if (value.options.inputType == 'checkbox' || value.options.inputType == 'select'){
	    			html+='<tr>';
	    		}else {
	    			if(!value.options.hidden){
	    				html+='<tr>';
		    		}else{
		    			html+='<tr style="display:none;">';
		    		}
	    		}
		    	html+='<td align="right">'+value.options.header+':</td><td><input type="checkbox" name="checkbox" id="checkbox-multiEdit-'+value.options.name+'"/></td><td>';
		    	html+=$this.createEachElement(value,$this,'-multi');
	    		html+='</td></tr>';
	    	});
	    	html+='<tr><td colspan="3" align="right">';
	    	html+='<input type="submit" id="'+this.multiSaveBtnId+'" value="保存" ztype="button" />';
			html+='<input type="button" id="'+this.multiCloseBtnId+'" value="关闭" ztype="button" />';
			html+='</td></tr>';
	    	html+='</table>';
			html+='</form>';
			html+='</div>';
	    	return html;
	    },
		createInput: function(){
	    	var html ='<div id="'+this.dialogId+'" style="display:none;">';
	    	html +='<form id="'+this.formId+'" name="'+this.formId+'" action="'+this.tjGrid.saveUrl.content+'" ztype="gridForm" style="border:0;margin:0;padding:0;">';
	    	html +='<table class="work_info_table" width="100%" border="0" cellpadding="0" cellspacing="0">';
	    	var $this=this;
	    	var modelName = this.tjGrid.id.substring(0,this.tjGrid.id.indexOf('-'));
	    	$.each(this.tjGrid.tjColModel,function(index, value){
	    		if (value.options.inputType == 'checkbox' || value.options.inputType == 'select'){
	    			html+='<tr>';
	    		}else {
	    			if(value.options.hidden && !value.options.editable){
	    				html+='<tr style="display:none;">';
		    		}else{
		    			html+='<tr>';
		    		}
	    		}
		    	html+='<td width="60"><label for="'+value.options.name+'">'+value.options.header+'：</label></td><td>';
		    	html+=$this.createEachElement(value,$this);
	    		html+='</td></tr>';
	    	});
			html+='</table>';
	    	html+='<div class="form_submitDiv">';
	    	html+='<div id="continual-add-div" style="float:left;">是否连续添加:<input type="checkbox" name="checkbox"/></div>';
	    	html+='<input type="submit" value="保存" ztype="button" />';
			html+='<input type="button" id="'+this.closeId+'" value="关闭" ztype="button" />';
			html+='</div>';
			html+='</form>';
			html+='</div>';
			return html;
	    },
	    
	    createEachElement:function(value,$this,multi){
	    	//multi在每一个表单元素的ID后加标识，表示是否是多选编辑，
	    	if (multi == undefined){
	    		multi = '';
	    	}
	    	var html = '';
	    	if (value.options.editable){
	    		if (value.options.inputType == 'input'){
		    		//输入框形式
		    		html+='<input type="text" id="'+$this.inputPrefix+value.options.name+multi+'" ';
		    		if (value.options.canSubmit){
		    			html += 'name="'+value.options.name+'" ';
		    		}
		    		if (value.options.dateFmt != null){
		    			html += 'ztype="'+value.options.dateFmt+'" ';
		    		}else {
		    			html += ' onfocus=this.select() ztype="text"';
		    		}
		    		if (!value.options.now != null){
		    			html += 'znow="'+value.options.now+'" ';
		    		}
		    		if(value.options.verify!=null){
		    			html+='verify="'+value.options.header+'|'+value.options.verify+'"';
		    		}
		    		html+='/>';
		    	}
		    	else if (value.options.inputType == 'textArea'){
		    		//文本域形式
		    		html += '<textarea cols="27" rows="4" id="' + $this.inputPrefix+value.options.name+multi+'" ';
		    		html += 'ztype="textarea" ';
		    		if (value.options.canSubmit){
		    			html += 'name="'+value.options.name+'" ';
		    		}
		    		if(value.options.verify!=null){
		    			html+='verify="'+value.options.header+'|'+value.options.verify+'"';
		    		}
		    		html+=' ></textarea>';
		    	}
		    	else if (value.options.inputType == 'select'){
		    		//下拉选形式
		    		if (value.options.canSubmit){
			    		html += '<div id="'+$this.inputPrefix+value.options.name+multi+'" style="width:auto;" ';
		    			var submitName = value.options.name;
		    			var index = submitName.indexOf('.');
		    			var name = '';
		    			if (index == -1){
		    				//自身属性的情况
		    				name = submitName;
		    				html += 'name="'+name+'" ';
		    			}else {
		    				//关联属性的情况
		    				name = submitName.substring(0, index);
		    				html += 'name="'+name+'.id" ';
		    			}
		    			if (value.options.verify != null){
		    				html += ' verify="' + value.options.verify + '"';
		    			}
	    				html += '><ul>';
			    		html += '</ul></div>';
		    		}
		    	}
		    	else if (value.options.inputType == 'checkbox'){
		    		//多选框形式
		    		if (value.options.canSubmit){
		    			var items = value.options.items;
		    			var submitName = value.options.name;
		    			var index = submitName.indexOf('.');
		    			html += '<div style="width:200px;';
		    			html += '">';
	    				for(var i in items){
	    					html += '<input type="checkbox" id="'+$this.inputPrefix+value.options.name+multi+'-' + i +'" ';
			    			html += 'name="'+submitName.substring(0, index)+'_id" ';
			    			html += 'value="'+items[i].value+'" /><span>'+items[i].name+ '</span>';
			    			html += '<br/>';
	    				}
	    				html += '</div>';
		    		}
		    	}
	    	}else {
	    		html+='<input type="text" style="color:#999;" id="'+$this.inputPrefix+value.options.name+multi+'" ';
	    		html += 'ztype="text" ';
	    		if (value.options.canSubmit){
	    			html += 'name="'+value.options.name+'" ';
	    		}
	    		html+='readonly="readonly" />';
	    	}
	    	return html;
	    },
	    
	    fillDefaultValues:function(tjColModel,$this){
	    	var str = 'success';
	    	$.each(tjColModel,function(index, value){
				var name=$this.inputPrefix+value.options.name;
				var val=value.options.defaultVal;
				if (value.options.inputType == 'input' || value.options.inputType == 'textarea'){
					$('*[id="'+name+'"]').val(val);
				}else if (value.options.inputType == 'select'){
					var items = value.options.items;
					var html = '';
    				if (value.options.verify != 'NotNull'){
    					html += '<li val="-1" selected="selected">无</li>';
    				}else {
    					if (items.length == 0){
    						tjAlert('请先录入' + value.options.header + '数据!');
    						str = 'isNull';
    						return;
    					}
    				}
					for(var i in items){
    					if (items[i].value == undefined || items[i].value == ''){
    						html += '<li val="">'+items[i].name+'</li>';
    					}else {
    						html += '<li val="'+items[i].value+'">'+items[i].name+'</li>';
    					}
    				}
    				$('*[id="'+name+'"]').find('ul').empty();
    				$('*[id="'+name+'"]').find('ul').append(html);
    				if ($('*[id="'+name+'"]').data('tjSelect') == undefined){
    					$('*[id="'+name+'"]').tjSelect(136,"true","true");
    				}else {
    					$('*[id="'+name+'"]').tjAssemble();
    				}
				}else if (value.options.inputType == 'checkbox'){
					var submitName = value.options.name;
	    			var index = submitName.indexOf('.');
	    			var name = submitName.substring(0, index)+'_id';
					$('input[name="'+name+'"]').each(function(){
	    				$(this).attr('checked','');
					});
				}
			});
	    	return str;
	    },
	    
	    createMessageBox:function(){
	    	if($('#'+this.messageBoxId).length==0){
				$('body').append('<div id="'+this.messageBoxId+'" title="提示!"></div>');
			}
	    	var $this=this;
			$('#'+this.messageBoxId).dialog({
				modal: true,
				height: 'auto',
				width: 200,
				autoOpen:false,
				resizable: false,
				buttons: {
					"确定": function() {
						$('#'+$this.messageBoxId).dialog('close');
					}
				}
			});
	    },
	    
	    message:function(message){
	    	$('#'+this.messageBoxId).empty();
	    	$('#'+this.messageBoxId).append('<p><span class="ui-icon ui-icon-circle-check" style="float:left; margin:0 7px 50px 0;"></span>'+message+'</p>');
	    	$('#'+this.messageBoxId).dialog('open');
	    },
	    
	    initForm:function(){
	    	var $this = this;
	    	$('#'+this.dialogId).find('form[ztype="gridForm"]').not($('[zinit]')).each(function(index){
				$(this).form();
				var thisForm = $(this);
				$(this).ajaxForm({
					type: "post",
					dataType: 'json',
					beforeSubmit: $this.gridValidateForm,
					success: function(data){
						if (data['result'] == true){
							//拼提示信息
							var theName = thisForm.find('input[name="name"]').val();
							if (theName != undefined){
								var message = $this.tjGrid.name + "(" + theName + ")已经"; 
								if (thisForm.find('input[name="id"]').val() == ''){
									message += '保存成功';
								}else {
									message += '更新成功';
									$('#'+$this.closeId).trigger('click');
								}
							}else {
								if (thisForm.find('input[name="id"]').val() == ''){
									var message = '保存成功';
								}else {
									var message = '更新成功';
									$('#'+$this.closeId).trigger('click');
								}
							}
							//判断是否连续添加，如果不是连续添加，直接关闭对话框
					    	if($(thisForm).find('#continual-add-div input[name="checkbox"]:visible').attr('checked') == false){
								$('#'+$this.dialogId).dialog('destroy');
								$('#'+$this.dialogId).dialog('close'); 
								//取消高亮显示
								$('#gbox_'+$this.gridTableId).find('tr[aria-selected="true"]').each(function(){
									$(this).trigger('click');
								});
				    		}
		 					$('#'+$this.gridTableId).jqGrid().trigger("reloadGrid");
							$('#tb_'+ $this.gridTableId).html('<font color="#ffb400">'+message+'</font>');
						}else {
							tjAlert("操作失败！");
						}
					}
				});
				$(this).attr('zinit',true);
			});
	    },
	    
	    gridValidateForm:function(q,thisForm){
	    	if (thisForm.data('tjForm').checkSubmit()) {
	    		return true;
	    	}else{
	    		return false;					
	    	}
	    }
	    
	});
})(jQuery);