package dao;

import java.util.List;

import model.CompanyModel;
import model.KospiModel;
import model.LottoModel;

public interface BaseDao {
    public List<KospiModel> getKospi(KospiModel kospi);
    public void settingKospi(KospiModel kospi);
    public void kospiUpdate(KospiModel kospi);
    
    public List<CompanyModel> getKospi200(CompanyModel company);
    public List<CompanyModel> getTodayCompany(CompanyModel company);
    public void settingKospi200(CompanyModel company);
    public void settingTodayCompany(CompanyModel company);
    public void updateTodayCompany(CompanyModel company);
    public void updateKospi200(CompanyModel company);
}
