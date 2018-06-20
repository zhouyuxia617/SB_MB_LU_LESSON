package cn.et.sml.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.jdbc.SQL;

@Mapper
public interface UserMapper {
	/**  1
	@Select("select * from t_user limit #{0},#{1}")
	public List<Map> selectAllUser(Integer page,Integer limit);
	*/

	/**  2
	@Select("select * from t_user limit #{p},#{l}")
	public List<Map> selectAllUser(@Param("p")Integer page,@Param("l")Integer limit);
	*/
	
	
	/*
	 * 提交动态sql的内部类
	 */
	static class SqlProvider{
		
		/**
		 * 为user提供的动态内部类方法
		 */
		public String getSelectAllUserSql(Map map) {
			
			Map m = (Map)map.get("user");
			Object userName = m.get("userName");
			Object gender = m.get("gender");
			
			//分页数据
			Integer startIndex = (Integer)map.get("startIndex");
			Integer limit = (Integer)map.get("limit");
			
			//注解中的动态sql
			SQL sql = new SQL();
			
			sql = sql.SELECT("*").FROM("t_user");
			
			if(userName != null && !"".equals(userName)) {
				sql.WHERE("n_name like '%"+userName+"%'");
			}
			
			if(gender != null && !"".equals(gender)) {
				sql.AND();
				sql.WHERE("gender = #{user.gender}");
			}
			
			String sqlStr = sql.toString()+" limit "+startIndex+","+limit;
			System.out.println(sqlStr);
			return sqlStr;
		}
		
		
		/**
		 * 为count提供的动态内部类方法
		 */
		public String getSelectAllUserCountSql(Map map) {
			
			Map m = (Map)map.get("user");
			Object userName = m.get("userName");
			Object gender = m.get("gender");
			
			SQL sql = new SQL();
			sql = sql.SELECT("count(*)").FROM("t_user");
			
			if(userName != null && !"".equals(userName)) {
				sql.WHERE("n_name like '%"+userName+"%'");
			}
			
			if(gender != null && !"".equals(gender)) {
				sql.AND();
				sql.WHERE("gender = #{user.gender}");
			}
			
			System.out.println(sql.toString());
			return sql.toString();
		}
		
	}
	
	
	//3
//	@Select("select * from t_user where n_name like '%${param3}%' limit #{param1},#{param2}")
//	@Select("select * from t_user where n_name like '%${user.userName}%' limit #{startIndex},#{limit}")
	/**
	 * 查询用户（第二步）
	 */
	@SelectProvider(type=SqlProvider.class,method="getSelectAllUserSql")
	public List<Map> selectAllUser(@Param("startIndex")Integer startIndex,@Param("limit")Integer limit,@Param("user")Map map);
	
	/**查询总共的条数,建议用@Param("")取别名,上面那个方法也一样建议使用这种方法*/
//	@Select("select count(*) from t_user where n_name like '%${user.userName}%'")
	/**
	 * 查询用户总个数
	 */
	@SelectProvider(type=SqlProvider.class,method="getSelectAllUserCountSql")
	public int selectAllUserCount(@Param("user")Map map);
	
	/**
	 * 删除用户
	 */
	@Delete("delete from t_user where id=#{id}")
	public void deleteUser(@Param("id")String id);
	
	/**
	 * 新增用户
	 */
	@Insert("insert into t_user values(UUID(),#{userName},#{age},#{gender})")
	public void saveUser(@Param("userName")String userName,@Param("age")String age,@Param("gender")String gender);
	
	/**
	 * 根据id查用户
	 */
	@Select("select * from t_user where id=#{id}")
	public Map selectUserById(@Param("id")String id);
	
}
