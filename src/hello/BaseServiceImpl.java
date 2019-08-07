package hello;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BaseServiceImpl implements BaseService {
    @Autowired
    BaseDao baseDao;
    
    @Override
    public List<KospiModel> getKospi(KospiModel kospiModel) {
        return baseDao.getKospi(kospiModel);
    }

    @Override
    public void settingKospi(KospiModel kospi) {
        baseDao.settingKospi(kospi);
    }

    @Override
    public void kospiUpdate(KospiModel kospi) {
        baseDao.kospiUpdate(kospi);
    }

    @Override
    public List<CompanyModel> getKospi200(CompanyModel company) {
        return baseDao.getKospi200(company);
    }

    @Override
    public List<CompanyModel> getTodayCompany(CompanyModel company) {
        return baseDao.getTodayCompany(company);
    }

    @Override
    public void settingKospi200(CompanyModel company) {
        baseDao.settingKospi200(company);
    }

    @Override
    public void settingTodayCompany(CompanyModel company) {
        baseDao.settingTodayCompany(company);
    }

    @Override
    public void updateTodayCompany(CompanyModel company) {
        baseDao.updateTodayCompany(company);
    }

    @Override
    public void updateKospi200(CompanyModel company) {
        baseDao.updateKospi200(company);
    }
}
