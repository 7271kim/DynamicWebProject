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
    public List<KospiModel> getKospi(KospiModel kospi) {
        return sqlSession.selectList("BASE.getKospi",kospi);
    }

    @Override
    public void settingKospi(KospiModel kospi) {
        sqlSession.insert("BASE.settingKospi",kospi);
    }

    @Override
    public void kospiUpdate(KospiModel kospi) {
        sqlSession.update("BASE.kospiUpdate",kospi);
    }

    @Override
    public List<CompanyModel> getKospi200(CompanyModel company) {
        return sqlSession.selectList("BASE.getKospi200",company);
    }

    @Override
    public List<CompanyModel> getTodayCompany(CompanyModel company) {
        return sqlSession.selectList("BASE.getTodayCompany",company);
    }

    @Override
    public void settingKospi200(CompanyModel company) {
        sqlSession.insert("BASE.settingKospi200",company);
        
    }

    @Override
    public void settingTodayCompany(CompanyModel company) {
        sqlSession.insert("BASE.settingTodayCompany",company);
        
    }

    @Override
    public void updateTodayCompany(CompanyModel company) {
        sqlSession.update("BASE.updateTodayCompany",company);
    }

    @Override
    public void updateKospi200(CompanyModel company) {
        sqlSession.update("BASE.updateKospi200",company);
    }
  
}
