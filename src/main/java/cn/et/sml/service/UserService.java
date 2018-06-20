package cn.et.sml.service;

import java.util.List;
import java.util.Map;

public interface UserService {
	
	/**
	 * 查询用户（第三步）
	 */
	public List<Map> selectAllUser(Integer page,Integer limit,Map map);
	
	/**
	 * 统计所有用户数
	 */
	public int selectAllUserCount(Map map);
	
	/**
	 * 删除用户
	 */
	public void deleteUser(String id);
	
	/**
	 * 新增用户
	 */
	public void saveUser(String userName,String age,String gender);
	
	/**
	 * 根据id查询用户
	 */
	public Map detailUser(String id);
	
}
