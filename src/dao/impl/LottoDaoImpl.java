package dao.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import dao.LottoDao;
import model.LottoModel;

@Repository
public class LottoDaoImpl implements LottoDao {

    
    private static Logger LOGGER = LogManager.getLogger(LottoDaoImpl.class);
    
    @Autowired
    private SqlSession sqlSession;

    @Override
    public List<LottoModel> getLotto(LottoModel lottoModel) {
        return sqlSession.selectList("LOTTO.getLotto",lottoModel);
    }
    
    @Override
    public void updateLotto(LottoModel lottoModel) {
        sqlSession.update("LOTTO.updateLotto",lottoModel);
    }
    
    @Override
    public List<LottoModel> getLottoNumber(LottoModel lottoModel) {
        return sqlSession.selectList("LOTTO.getLottoNumber",lottoModel);
    }
    
    @Override
    public void updateLottoNumber(LottoModel lottoModel) {
        sqlSession.update("LOTTO.updateLottoNumber",lottoModel);
    }

    

    @Override
    public List<LottoModel> getLottoDetail(LottoModel lottoModel) {
        return sqlSession.selectList("LOTTO.getLottoDetail",lottoModel);
    }

    @Override
    public void updateLottoDetail(LottoModel lottoModel) {
        sqlSession.update("LOTTO.updateLottoDetail",lottoModel);
    }

    @Override
    public void insertLotto(LottoModel lottoModel) {
        sqlSession.insert("LOTTO.insertLotto",lottoModel);
    }

    @Override
    public void inserLottoNumber(LottoModel lottoModel) {
        sqlSession.insert("LOTTO.inserLottoNumber",lottoModel);
    }

    @Override
    public void insertLottoDetail(LottoModel lottoModel) {
        sqlSession.insert("LOTTO.insertLottoDetail",lottoModel);
    }
}
