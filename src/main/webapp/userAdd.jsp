<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset=UTF-8">
<title>新增用户弹出框</title>

<!-- 把layui引进来 -->
<link rel="stylesheet" href="${pageContext.request.contextPath}/js/css/layui.css">
<script src="${pageContext.request.contextPath}/js/layui.js"></script>
<script type="text/javascript" src="https://cdn.bootcss.com/jquery/1.10.2/jquery.min.js"></script>

</head>
<body style="height:100%">
	
	<form class="layui-form" action="">
		<div class="layui-form-item">
			<label class="layui-form-label">用户姓名：</label>
			<div class="layui-input-block">
				<input type="text" name="userName" required lay-verify="required" placeholder="请输入用户名" autocomplete="off" class="layui-input">
			</div>
		</div>
		
		<div class="layui-form-item">
			<label class="layui-form-label">年龄：</label>
			<div class="layui-input-inline">
				<input type="text" name="age" required lay-verify="required" placeholder="请输入年龄" autocomplete="off" class="layui-input">
			</div>
		</div>
		
		<div class="layui-form-item">
			<label class="layui-form-label">性别：</label>
			<div class="layui-input-block">
				<select name="gender" lay-verify="required">
					<option value="MAN">男</option>
					<option value="WOMAN">女</option>
				</select>
			</div>
		</div>
		
		<div class="layui-form-item">
			<div class="layui-input-block">
				<button class="layui-btn" lay-submit lay-filter="formDemo">立即提交</button>
				<button type="reset" class="layui-btn layui-btn-primary">重置</button>
			</div>
		</div>
	</form>

	<script>
		//Demo
		layui.use('form',function(){
			var form = layui.form;
			
			//监听提交
			form.on('submit(formDemo)',function(data){
				layui.msg(JSON.stringify(data.field));
				
				$.ajax({
					url:'/user/addUser',
					data:data.field,
					success:function(e){
						if(e == 1){
							parent.layui.layer.close(parent.openAdd);
							parent.queryUser(); //重新查询，刷新页面
						}else{
							layui.layer.msg("新增失败!");
						}
					}
				});
				
				return false;
			});
		});
	</script>
</body>
</html>