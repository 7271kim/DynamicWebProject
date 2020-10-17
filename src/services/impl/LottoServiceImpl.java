package services.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.seokjin.kim.library.JsoupCustom;
import com.seokjin.kim.library.MathAll;

import dao.LottoDao;
import hello.MainController;
import model.LottoModel;
import services.LottoService;

@Service
public class LottoServiceImpl implements LottoService {
    
    @Autowired
    LottoDao lottoDao;
    
    static int count = 0;
    static final int TOTAL_PAGE = 93;
    static final int LOTTO_RANGE = 45;
    static final int GET_TOTAL = 7;
    static final Set<String> notPassible = new TreeSet<>();
    static final int RANGE1 = 1;
    static final int RANGE10 = 0;
    static final int RANGE20 = 0;
    static final int RANGE30 = 0;
    static final int RANGE40 = 0;
    // https://www.dhlottery.co.kr/gameResult.do?method=noViewNumber 15주간 안나온 번호
    static final int[] choiceNum = {3,15,17,29,36};
    static final int[] choiceNotNum = {37,40,45};
    static final int[] choiceNotNumArray = new int[46];
    static final int[] isCheckWhenTime = {29,30,36}; // 몇회째에 해당 번호가 나왔는지 찾기
    
    Logger LOGGER = LogManager.getLogger(LottoServiceImpl.class);
    
    @Override
    public synchronized void  totalLottoDataUpdate() {
        
        ExecutorService executorServiceWithCached = Executors.newFixedThreadPool(40);
        // 총 10페이지 업데이트
        for(int index = 1; index <= TOTAL_PAGE; index++) {
            TaskLotto<Integer> task = new TaskLotto<Integer>(index, lottoDao );
            Future<Boolean> returnBoolean = executorServiceWithCached.submit(task);
            executorServiceWithCached.execute(()->{
                try {
                    if( returnBoolean.get() ) {
                        count++;
                    }
                } catch ( Exception e ) {
                    LOGGER.error(e.getMessage());
                    LOGGER.error(e.getStackTrace());
                }
            });
            
        }
        boolean isLoop = true;
        while( isLoop ) {
            if( count == TOTAL_PAGE ) {
                isLoop = false;
                List<LottoModel> result = lottoDao.getLotto(new LottoModel());
                Map<String, Integer> totlaNumber = new HashMap<String, Integer>(); // 1~45 개의 번호가 몇번 나왔는지 업데이트
                Map<String, Integer> totlaTwoNumber = new HashMap<String, Integer>(); // 2개의 쌍이 몇번 나왔는지 업데이트
                Set<String> lottoCombi = MathAll.getCombination(45, 2);
                for( int index = 1; index <= 45; index++ ) {
                    totlaNumber.put(String.valueOf(index), 0);
                }
                for( String numberTwos : lottoCombi ) {
                    totlaTwoNumber.put(numberTwos, 0);
                }
                
                for( LottoModel item : result ) {
                    String[] numbers = item.getNumber().split(" ");
                    Set<String> numbersCombi = MathAll.getCombination(numbers, 2);
                    for( String number : numbersCombi) {
                        int temp = totlaTwoNumber.get(number);
                        totlaTwoNumber.put(number, temp+1);
                    }
                    for( String number : numbers) {
                        int temp = totlaNumber.get(number);
                        totlaNumber.put(number, temp+1);
                    }
                }
                count = 0;
                for( int index = 1; index <= 45; index++ ) {
                    String number = String.valueOf( index );
                    LottoModel tempModel = new LottoModel();
                    tempModel.setNumber(number);
                    tempModel.setValue(String.valueOf(totlaNumber.get(number)));
                    HashMap<String, String> where = new HashMap<>();
                    where.put("number", number);
                    tempModel.setWhere(where);
                    
                    TaskLotto2 task2 = new TaskLotto2( tempModel, lottoDao );
                    Future<Boolean> returnBoolean = executorServiceWithCached.submit(task2);
                    executorServiceWithCached.execute(()->{
                        try {
                            if( returnBoolean.get() ) {
                                count++;
                            }
                        } catch ( Exception e ) {
                        }
                    });
                }
                
                for( String numberset : totlaTwoNumber.keySet() ) {
                    LottoModel tempModel = new LottoModel();
                    String[] temp = numberset.split(" ");
                    String firstNum = temp[0];
                    String seconNum = temp[1];
                    tempModel.setValue( String.valueOf( totlaTwoNumber.get(numberset)));
                    tempModel.setNumber(firstNum);
                    tempModel.setNumber_two(seconNum);
                    
                    HashMap<String, String> where = new HashMap<String, String>();
                    where.put("NUMBER", firstNum);
                    where.put("NUMBER_TWO", seconNum);
                    tempModel.setWhere(where);
                    TaskLotto3 task3 = new TaskLotto3( tempModel, lottoDao );
                    executorServiceWithCached.submit(task3);
                }
            }
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        
        isLoop = true;
        while( isLoop ) {
            if( count == LOTTO_RANGE ) {
                isLoop = false;
                System.out.println("모든 작업이 끝났습니다.");
            }
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        executorServiceWithCached.shutdown();
    }
    
    private static class TaskLotto <T> implements Callable<Boolean> {
        private T input;
        private LottoDao lottoDao;

        public TaskLotto(T input, LottoDao lottoDao ) {
            this.input = input;
            this.lottoDao = lottoDao;
        }


        @Override
        public Boolean call() throws Exception {
            String parsingUrl = "https://www.dhlottery.co.kr/gameResult.do?method=statByColor&currentPage="+input;
            Document doc= JsoupCustom.getGetDocumentFromURL(parsingUrl);
            if(doc.select("table.tbl_data.tbl_data_col tbody")!= null){
                Element table  = doc.select("table.tbl_data.tbl_data_col tbody").get(0);
                Elements tr   = table.select("tr");
                for ( Element item : tr ) {
                    LottoModel tempModel = new LottoModel();
                    Elements td = item.select("td");
                    String date = td.get(0).text();
                    String number = td.get(2).text();
                    
                    
                    tempModel.setDate(date);
                    tempModel.setNumber(number);
                    HashMap<String, String> where = new HashMap<>();
                    where.put("DATE", date);
                    where.put("NUMBER", number);
                    
                    
                    tempModel.setWhere(where);
                    List<LottoModel> list = lottoDao.getLotto(tempModel);
                    
                    if(list.size() == 0 ) {
                        lottoDao.insertLotto(tempModel);
                    };
                }
            }
            
            return true;
        }
    }

    private static class TaskLotto2 implements Callable<Boolean> {
        private LottoModel lottoModel;
        private LottoDao lottoDao;

        public TaskLotto2( LottoModel lottoModel, LottoDao lottoDao ) {
            this.lottoModel = lottoModel;
            this.lottoDao = lottoDao;
        }


        @Override
        public Boolean call() throws Exception {
            List<LottoModel> list = lottoDao.getLottoNumber(lottoModel);
            
            if( list.size() == 0 ) {
                lottoDao.inserLottoNumber(lottoModel);
            } else {
                lottoDao.updateLottoNumber(lottoModel);
            }
            return true;
        }
    }

    private static class TaskLotto3 implements Callable<Boolean> {
        private LottoModel lottoModel;
        private LottoDao lottoDao;

        public TaskLotto3(LottoModel tempModel, LottoDao lottoDao) {
            this.lottoModel = tempModel;
            this.lottoDao = lottoDao;
        }


        @Override
        public Boolean call() throws Exception {
            List<LottoModel> list = lottoDao.getLottoDetail(lottoModel);
            
            if( list.size() == 0 ) {
                lottoDao.insertLottoDetail(lottoModel);
            } else {
                lottoDao.updateLottoDetail(lottoModel);
            }
            return true;
        }
    }
}


