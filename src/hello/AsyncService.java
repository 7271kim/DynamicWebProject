package hello;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.scheduling.annotation.Async;

public class AsyncService {
    @Async
    public BaseModel testtest(BaseModel baseModel) {
        try {
            if ( baseModel.getComPanyData() == null) return baseModel;
            for(String code : baseModel.getComPanyData().keySet()) {
                String url = "https://finance.naver.com/item/frgn.nhn?code="+code;
                Document doc = Jsoup.connect(url).get();
                Element table           = doc.select("tlline2").next().first();
                System.out.println("aaaaaaaaaaaaaaa");
                System.out.println(table);
            }
            
        } catch (Exception e) {
            baseModel.setError(e.toString());
        }
        
        return baseModel;
    }
    
}
