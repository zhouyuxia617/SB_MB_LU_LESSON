<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>CRUD页面</title>
 
<link rel="stylesheet" href="${pageContext.request.contextPath}/js/css/layui.css">
<script src="${pageContext.request.contextPath}/js/layui.js"></script>
<script type="text/javascript" src="https://cdn.bootcss.com/jquery/1.10.2/jquery.min.js"></script>
 
<script type="text/javascript">

	//定义表格的全局变量
	var userTable = null;
//	alert($);

	var openAdd = null;
	
	/**
	         查询用户的方法（第一步）
	*/
	function queryUser(){
		
		//先用标签选择器再用属性选择器，再取文本框的值val()
		var userName = $("input[name='userName']").val();
		var gender = $("select[name='gender']").val();
		
		userTable.reload({
			where:{ //设定异步数据接口的额外参数，任意设
				userName:userName,
				gender:gender
			}/* ,page:{
				curr:1  //重新从第一页开始
			} */
		});
	}
	
	/**
		根据id删除用户的方法
	*/
	function deleteUser(id){
		//alert(id);
		
		$.ajax({
			url:'/user/deleteUser',
			dataType:'json',
			data:'id='+id,
			success:function(e){
				if(e == 1){
					queryUser(); //重新查询，刷新
				}else{
					//弹出一会儿就会消失
					layui.layer.msg("删除失败！");
				}
			}
		});
	}
	
	/**
		新增用户的方法
	*/
	function showAddUser(){
		/* layui.layer.open({
			title:'弹出框',
			content:'可以填写任何的layer代码：常立祥ai周玉霞'
		}); */
		
		openAll = layui.layer.open({
			title:'新增用户',
			area:['400px','400px'],
			//iframe标签，把另外一个页面引进来
			content:'<iframe src="userAdd.jsp" height="97%" frameborder="no" border="0" marginwidth="0",marginheight="0",scrolling="no" allowtransparency="yes"></iframe>'
		});
	}
	
	/**
		根据id查询用户详细信息
	*/
	function detailUser(id){
		$.ajax({
			url:'/user/detailUser',
			data:'id='+id,
			success:function(e){
				layui.layer.open({
					title:'新增用户',
					area:['400px','400px'],
					content:'<div>用户姓名:'+e.n_name+'</div><div>用户身高:'+e.length+'</di>'
				});
			}
		});
	}

	
	//layui方法加载完可以自动回调这个方法
	layui.use(['layer','table','form'],function(){
		//获取到table模块对象
		var table = layui.table;
		
		//第一个实例
		userTable = table.render({
			elem:'#userList',
			height:315,
			url:'/user/userList', //数据接口
			page:true, //开启分页
			cols:[[ //表头
				{field:'id',title:'ID',width:250,sort:true,fixed:'left'},
				{field:'n_name',title:'用户姓名',width:250},
				{field:'age',title:'用户年龄',width:250,sort:true},
				{field:'gender',title:'性别',width:250},
				{field:'myController',title:'操作',width:250,templet:function(d){
				//	return '<a href="javascript:alert('+d.id+')"><img src="images/delete.ico"></a>'
					//转译\"'++'\"
					return '<a href=javascript:deleteUser(\"'+d.id+'\")><img src="images/delete.ico"></a>'
						+'&nbsp;&nbsp;<a href=javascript:detailUser(\"'+d.id+'\")><img src="images/detail.ico"></a>';
				}}
			]]
		});
	}); 
</script>
</head>
<body>
	<!--1 <form class="layui-form" action=""> -->
		<div class="layui-form-item">
			<div class="layui-inline">
				<label class="layui-form-label">用户姓名</label>
				<div class="layui-input-inline" style="width:200px;">
					<input type="text" name="userName" placeholder="请输入用户姓名" style="width:200px" class="layui-input">
				</div>
				
				<div class="layui-form-mid"><font color="pink"><h1>-</h1></font></div>
				
				<div class="layui-input-inline" style="width:200px;">
					<div class="layui-form-item">
					<label class="layui-form-label">性别：</label>
					<div class="layui-input-block">
						<select name="gender" style="margin-top:10px;width:40px;">
							<option value=""></option>
							<option value="MAN">男</option>
							<option value="WOMAN">女</option>
						</select>
					</div>
				</div>
			</div>
				
			<div class="layui-input-inline" style="width:100px;">
				<!--1 <button class="layui-btn" onclike="return false">查询</button> -->
				<button class="layui-btn" onclick="queryUser()">查询</button>
			</div>
			<div class="layui-input-inline" style="width:100px;">
				<button class="layui-btn" onclick="showAddUser()">新增</button>
			</div>
		</div>
	
			<!-- 这也是一个按钮 
			<label class="layui-form-label">用户姓名</label>
			<div class="layui-input-block">
				<input type="text" name="userName" placeholder="请输入用户姓名" style="width:200px" class="layui-input">
				<button class="layui-btn">查询</button>
			</div> -->
	</div>
	<!-- </form> -->
	
	<table id="userList"></table>
	
</body>
</html>
