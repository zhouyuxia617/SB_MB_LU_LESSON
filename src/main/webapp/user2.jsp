<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
 <link rel="stylesheet" href="${pageContext.request.contextPath}/js/css/layui.css">
 <script src="${pageContext.request.contextPath}/js/layui.js"></script>
 <script type="text/javascript" src="https://cdn.bootcss.com/jquery/1.10.2/jquery.min.js"></script>
 <script type="text/javascript">
 var userTable=null;
 var openAdd=null;
 function queryUser(){
	 var userName=$("input[name='userName']").val();
	 var gender=$("select[name='gender']").val();
	 userTable.reload({
		  where: { //设定异步数据接口的额外参数，任意设
			    userName:userName,
			    gender:gender
			  }
	  });
 }
 function deleteUser(id){
	 $.ajax({
		 url:'/user/deleteUser',
		 data:'id='+id,
		 success:function(e){
			 if(e==1){
				 queryUser();
			 }else{
				 layui.layer.msg("删除失败");
			 }
		 }
	 })
 }
 
 function showAddUser(){
	 openAdd=layui.layer.open({
		  title: '新增用户',
		  area: ['400px', '400px'],
		  content: '<iframe src="userAdd2.jsp" height="97%" frameborder="no" border="0" marginwidth="0" marginheight="0" scrolling="no" allowtransparency="yes"></iframe>'
		});     
		  
 }
 
 
 layui.use(['layer','table',"form"], function(){
	 //获取到table模块对象
	 var table = layui.table;
	//第一个实例
	 userTable=table.render({
	    elem: '#userList'
	    ,height: 315
	    ,url: '/user/userList' //数据接口
	    ,page: true //开启分页
	    ,cols: [[ //表头
	      {field: 'id', title: 'ID', width:250, sort: true, fixed: 'left'}
	      ,{field: 'n_name', title: '用户姓名', width:250}
	      ,{field: 'age', title: '用户年龄', width:250, sort: true}
	      ,{field: 'gender', title: '性别', width:250} 
	      ,{field: 'myControler', title: '操作', width:250,templet: function(d){
	          return '<a href=javascript:deleteUser(\"'+d.id+'\")><img src="images/delete.ico"></a>'
	      }} 
	    ]]
	  });
	});
 </script>
</head>
<body>
  
  <div class="layui-form-item">
   <div class="layui-inline">
    <label class="layui-form-label">用户姓名</label>
    <div class="layui-input-inline" style="width: 200px;">
      <input type="text" name="userName"  placeholder="请输入用户姓名" style="width:200px" class="layui-input">
      
    </div>
    <div class="layui-form-mid">-</div>
    
    <div class="layui-input-inline" style="width: 200px;">
    <div class="layui-form-item">
  <label class="layui-form-label">性别：</label>
  <div class="layui-input-block">
    <select name="gender" style="margin-top:10px;width:80px">
        <option value=""></option>
        <option value="MAN">男</option>
        <option value="WOMAN">女</option>
      </select>
  </div>
</div> 
    </div>
    
    <div class="layui-input-inline" style="width: 100px;">
      <button class="layui-btn" onclick="queryUser()">查询</button>
    </div>
    <div class="layui-input-inline" style="width: 100px;">
      <button class="layui-btn" onclick="showAddUser()">新增</button>
    </div>
  </div>
  </div>
  <table id="userList"></table>
</body>
</html>
