package dao;

import java.util.List;

import model.CompanyModel;
import model.KospiModel;
import model.LottoModel;

public interface LottoDao {
    public List<LottoModel> getLotto(LottoModel lottoModel);
    public List<LottoModel> getLottoNumber(LottoModel lottoModel);
    public List<LottoModel> getLottoDetail(LottoModel lottoModel);
    
    public void updateLotto(LottoModel lottoModel);
    public void updateLottoNumber(LottoModel lottoModel);
    public void updateLottoDetail(LottoModel lottoModel);
    
    public void insertLotto(LottoModel lottoModel);
    public void inserLottoNumber(LottoModel lottoModel);
    public void insertLottoDetail(LottoModel lottoModel);
}
