package hello;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class BaseDaoImpl implements BaseDao {

    
    private static Logger logger = LoggerFactory.getLogger(BaseDaoImpl.class);
    
    @Autowired
    private SqlSession sqlSession;

    @Override
    public List<BaseModel> getMemList(BaseModel baseModel) {
        return sqlSession.selectList("BASE.getMemList",baseModel);
    }
  
}
