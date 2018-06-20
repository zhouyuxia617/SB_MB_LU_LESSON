package cn.et.sml.service.impl;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import cn.et.sml.mapper.UserMapper;
import cn.et.sml.service.UserService;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private StringRedisTemplate template;
	
	/**
	 * 查询的方法（第四步）
	 */
	@Override
	public List<Map> selectAllUser(Integer page,Integer limit,Map map){
		Object userName = map.get("userName");
		//业务逻辑
		if(userName == null) {
			userName = "";
			map.put("userName", userName); //赋回去
		}
		
		int startIndex = (page-1)*limit;
		
		return userMapper.selectAllUser(startIndex, limit,map);
	}

	/**
	 * 查询用户总的个数
	 */
	@Override
	public int selectAllUserCount(Map map) {
		Object userName = map.get("userName");
		if(userName == null) {
			userName = "";
			map.put("userName", userName);
		}
		
		return userMapper.selectAllUserCount(map);
	}

	/**
	 * 删除的方法
	 */
	@Override
	public void deleteUser(String id) {
		userMapper.deleteUser(id);
	}

	/**
	 * 新增的方法
	 */
	@Override
	public void saveUser(String userName, String age, String gender) {
		userMapper.saveUser(userName, age, gender);
	}

	/**
	 * 根据id查询用户
	 */
	@Override
	public Map detailUser(String id) {
		//判断redis是否存在该id对应的用户数据
		String userId = "t_user:"+id;
		
		//判断是否存在
		Set<String> keys = template.keys(userId);
			
		//缓存存在数据
		if(keys.size() > 0) {
			//不查询数据库，直接返回
			return template.boundHashOps(userId).entries();
		//不存在数据
		}else {
			//查询数据库
			Map map = userMapper.selectUserById(id);
			
			//放入缓存
			template.boundHashOps(userId).putAll(map);
			
			return map;
		}
	}

}
