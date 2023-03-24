package com.example.emart;

import com.example.emart.entity.Advertisement;
import com.example.emart.entity.Event;
import com.example.emart.entity.Product;
import com.example.emart.service.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class EmartApplicationTests {
  @Autowired
  AdvertisementService advertisementService;
  @Autowired
  private ProductService productService;

  @Autowired
  private EventService eventService;

  @Test
  @Rollback(value = false)
  void adInit() {
    Advertisement ad1 = new Advertisement(
            "이마트24 2월 카드혜택",
            "https://emart24.co.kr/image/NDg3MQ==",
            "https://www.emart24.co.kr/event/43"
    );

    Advertisement ad2 = new Advertisement(
            "발렌타인데이 와인픽업 최대 1만원 페이백",
            "https://emart24.co.kr/image/NTAxNw==",
            "https://www.emart24.co.kr/event/61"
    );

    Advertisement ad3 = new Advertisement(
            "SKYPASS 적립하고 파리가자!",
            "https://emart24.co.kr/image/Mjk0MQ==",
            "https://www.emart24.co.kr/event/26"
    );
    Advertisement ad4 = new Advertisement(
            "밀키트 예약픽업 10% 할인 + 페이백",
            "https://emart24.co.kr/image/NDg3OQ==",
            "https://www.emart24.co.kr/event/42"
    );

    List<Advertisement> ads = new ArrayList<>();
    ads.add(advertisementService.addAd(ad1));
    ads.add(advertisementService.addAd(ad2));
    ads.add(advertisementService.addAd(ad3));
    ads.add(advertisementService.addAd(ad4));

    assertThat(ads.size()).isEqualTo(4);
  }

  @Test
  @Rollback(value = false) // DB에 영구저장
  void productInit() {
    Product product1 = new Product(
            "롯데 빈츠 2입 리미티드기획 408g",
            "https://sitem.ssgcdn.com/59/56/18/item/1000530185659_i1_1100.jpg",
            5370,
            15.0,
            "초콜릿가공품, 대륙식품(주) 덕계공장 경남 양산시 그린공단3길 108 (한국), 원산지: 상세설명참조",
            "롯데제과",
            4.8,
            10
    );
    Product product2 = new Product(
            "킷캣 오리지널 18입",
            "https://sitem.ssgcdn.com/21/16/61/item/1000529611621_i1_1100.jpg",
            9980,
            20.0,
            "킷캣 오리지널 18입, 네슬레코리아 (제조국 : 아랍에미리트)",
            "네슬레코리아 (제조국 : 아랍에미리트)",
            4.9,
            10
    );
    Product product3 = new Product(
            "허쉬 다크초콜릿 아사이&블루베리210g",
            "https://sitem.ssgcdn.com/90/11/54/item/1000270541190_i1_1100.jpg",
            9080,
            10.0,
            "허쉬 다크초콜릿 아사이&블루베리210g, 초콜릿가공품",
            "HERSHEY CANADA INC (제조국 : 캐나다)",
            4.8,
            10
    );

    Product product4 = new Product(
            "[린트]엑설런스 다크 99% 50g",
            "https://sitem.ssgcdn.com/50/75/43/item/1000029437550_i1_1100.jpg",
            6860,
            15.0,
            "LINDT&SPRUNGLI SAS",
            "(주)농심",
            4.8,
            10
    );
    Product product5 = new Product(
            "농심 감튀 레드칠리맛 60g",
            "https://sitem.ssgcdn.com/42/65/20/item/1000059206542_i1_1100.jpg",
            1360,
            10.0,
            "매콤한 감자튀김",
            "(주)농심",
            4.8,
            10
    );
    Product product6 = new Product(
            "게이샤 헤이즐넛 필링 밀크 초콜릿 150g",
            "https://sitem.ssgcdn.com/17/24/06/item/1000262062417_i1_550.jpg",
            6380,
            10.0,
            "파제르 헤이즐넛필링 밀크초콜릿 150g",
            "FAZER(핀란드)/(주)영남코프레이션 (제조국 : 핀란드)",
            4.8,
            10
    );
    Product product7 = new Product(
            "농심 조청유과 96g",
            "https://sitem.ssgcdn.com/44/20/83/item/1000020832044_i1_1100.jpg",
            1360,
            20.0,
            "벌꿀이 함유된 달콤한 유과",
            "(주)농심",
            4.9,
            10
    );
    Product product8 = new Product(
            "[쇼게튼]다크초콜릿 위드 코코아 헤이즐넛 100g",
            "https://sitem.ssgcdn.com/60/71/07/item/2097001077160_i1_1100.jpg",
            3200,
            10.0,
            "설탕,코코아페이스트30.69%,식물성유지,가당유청분말,전지분유,코코아버터2.57%,헤이즐넛1.7%,코코아닙1.7%,크림분말,버터유지,대두레시틴(유화제),저지방코코아분말0.3%,천연바닐라향,바닐라추출물",
            "(주)가온누리코퍼레이션",
            4.8,
            10
    );
    Product product9 = new Product(
            "허쉬 후퍼스340g",
            "https://sitem.ssgcdn.com/50/60/73/item/1000293736050_i1_1100.jpg",
            6780,
            20.0,
            "THE HERSHEY COMPANY / 미국 (제조국 : 미국)",
            "미성패밀리(주)",
            4.6,
            10
    );
    Product product10 = new Product(
            "미에슈코_에스프레소_프랄린 153g",
            "https://sitem.ssgcdn.com/60/27/77/item/1000529772760_i1_1100.jpg",
            8980,
            10.0,
            "미에슈코_에스프레소_프랄린 153g, 폴란드 (제조국 : 폴란드)",
            "(주)천하코퍼레이션",
            5.0,
            10
    );
    List<Product> products = new ArrayList<>();
    products.add(productService.addProduct(product1));
    products.add(productService.addProduct(product2));
    products.add(productService.addProduct(product3));
    products.add(productService.addProduct(product4));
    products.add(productService.addProduct(product5));
    products.add(productService.addProduct(product6));
    products.add(productService.addProduct(product7));
    products.add(productService.addProduct(product8));
    products.add(productService.addProduct(product9));
    products.add(productService.addProduct(product10));

    assertThat(products.size()).isEqualTo(10);
  }

  @Test
  @Rollback(value = false)
  void eventInit() {
    Event event1 = new Event("신상품");
    Event event2 = new Event("1+1");
    Event event3 = new Event("2+1");

    List<Event> events = new ArrayList<>();
    events.add(eventService.addEvent(event1));
    events.add(eventService.addEvent(event2));
    events.add(eventService.addEvent(event3));

    assertThat(events.size()).isEqualTo(3);
  }
}
