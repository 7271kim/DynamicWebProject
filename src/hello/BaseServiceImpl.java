package hello;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BaseServiceImpl implements BaseService {
    @Autowired
    BaseDao baseDao;
    
    @Override
    @Transactional(readOnly = true)
    public List<BaseModel> getMemList(BaseModel baseModel) {
        return baseDao.getMemList(baseModel);
    }
}
