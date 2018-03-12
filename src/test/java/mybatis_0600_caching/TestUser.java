package mybatis_0600_caching;

import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;

import com.ymd.learn.mapper.UserMapper;
import com.ymd.learn.model.User;


public class TestUser {
	
	SqlSession sqlSession = null;
	
	@Before
	public void init() throws Exception {
		String resource = "mybatis-configuration.xml";
		InputStream inputStream = Resources.getResourceAsStream(resource);
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		sqlSession = sqlSessionFactory.openSession();
	}
	
	@Test
	public void testUser() {
		UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
		User user = userMapper.getUserById(1);
		System.out.println("user = " + user);
		user = userMapper.getUserById(1);
		System.out.println("user = " + user);
		
	}
	
	@Test 
	public void testUser2() {
		UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
		User user = userMapper.getUserById(1);
		System.out.println("user = " + user);
		
		//when execute any insert/update/delete operation . level one cache will be clear
		user.setName("michael11");
		userMapper.updateUserById(user);
		sqlSession.commit();
		
		user = userMapper.getUserById(1);
		System.out.println("user = " + user);
		
	}
	
	@Test 
	public void testUser3() {
		UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
		User user = userMapper.getUserById(1);
		System.out.println("user = " + user);
		
		//when do update then will clear cache
		User user2 = new User();
		user2.setName("sara");
		userMapper.insertUser(user2);
		sqlSession.commit();
		
		user = userMapper.getUserById(1);
		System.out.println("user = " + user);
		
	}
	
}
