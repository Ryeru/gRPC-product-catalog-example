package server.mapper;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

public class MapperFactory {

    private static final SqlSession session;
    private static final String MYBATIS_CONFIG_LOCATION = "mybatis-config.xml";

    // Build a Mybatis session
    static {
        InputStream inputStream = null;
        try {
            inputStream = Resources.getResourceAsStream(MYBATIS_CONFIG_LOCATION);
        } catch (IOException e) {
            e.printStackTrace();
        }
        session = new SqlSessionFactoryBuilder().build(inputStream).openSession();
    }


    public static CatalogMapper getCatalogMapper() {
        return session.getMapper(CatalogMapper.class);
    }
}
