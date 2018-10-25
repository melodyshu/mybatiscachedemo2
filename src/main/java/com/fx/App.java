package com.fx;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws IOException {
        InputStream in= Resources.getResourceAsStream("mybatis-config.xml");
        SqlSessionFactory sqlSessionFactory= new SqlSessionFactoryBuilder().build(in);
        SqlSession sqlSession= sqlSessionFactory.openSession();
        CityMapper cityMapper= sqlSession.getMapper(CityMapper.class);
        City city= cityMapper.selectById(1);
        System.out.println(city);
        //sqlSession.clearCache();//清除一级缓存
        City city2= cityMapper.selectById(1);
        System.out.println(city2);

        //一级缓存,不同的sqlsession还是会去查询数据库,由于开启了二级缓存,不会查询数据库了
        //需要commit才会生效
        sqlSession.commit();;
        SqlSession sqlSession2= sqlSessionFactory.openSession();
        System.out.println(sqlSession==sqlSession2);
        CityMapper cityMapper2= sqlSession2.getMapper(CityMapper.class);
        City city3= cityMapper2.selectById(1);
        System.out.println(city3);

        sqlSession.close();
        sqlSession2.close();;
    }
}
