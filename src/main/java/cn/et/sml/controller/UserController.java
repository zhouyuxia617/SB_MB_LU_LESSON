package cn.et.sml.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.et.sml.entity.ResponseEnt;
import cn.et.sml.service.UserService;

@RestController
public class UserController {
	
	@Autowired
	DataSource dataSource;

	@Autowired
	private UserService userService;
	
	/**
	 * 查询的方法（第五步）
	 */
	@GetMapping("/user/userList")
	public ResponseEnt userList(Integer page,Integer limit,String userName,String gender){
		System.out.println(page+"----"+limit);
		
		Map map = new HashMap();
		map.put("userName", userName);
		map.put("gender", gender);
		
		ResponseEnt re = new ResponseEnt();
		
		List<Map> selectAllUser = userService.selectAllUser(page,limit,map);
		re.setData(selectAllUser);
	//	re.setCode(selectAllUser.size());
		re.setCount(userService.selectAllUserCount(map));
		
		return re;
	}
	
	
	/**
	 * 删除的方法
	 */
	@GetMapping("/user/deleteUser")
	public String deleteUser(String id) {
		try {
			//模拟一个异常
		/**	String str = null;
			str.toString();
			*/
			
			userService.deleteUser(id);
			
			return "1"; //删除成功
		}catch(Exception e) {
			return "0"; //删除失败
		}
	}
	
	
	/**
	 * 新增的方法
	 */
	@GetMapping("/user/addUser")
	public String saveUser(String userName,String age,String gender) {
		try {
			userService.saveUser(userName, age, gender);
			return "1";
		}catch(Exception e) {
			return "0";
		}
	}
	
	/**
	 * 根据id查询用户
	 */
	@GetMapping("/user/detailUser")
	public Map detailUser(String id) {
		
		return userService.detailUser(id);
	}
	
}
