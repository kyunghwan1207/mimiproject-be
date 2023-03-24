INSERT INTO `emart`.`users` (`id`, `email`, `name`, `password`) VALUES ('1', 'test@gmail.com', '최인규', '12345678');

INSERT INTO `emart`.`products` (`id`, `brand`, `category`, `description`, `discount`, `name`, `price`, `rating`, `thumbnail`) VALUES ('1', '롯데제과', '크래커', '초콜릿가공품, 대륙식품(주) 덕계공장 경남 양산시 그린공단3길 108 (한국), 원산지: 상세설명참조', '0', '롯데 빈츠 2입 리미티드기획 408g', '5370', '4.8', 'https://sitem.ssgcdn.com/59/56/18/item/1000530185659_i1_1100.jpg');
INSERT INTO `emart`.`products` (`id`, `brand`, `category`, `description`, `discount`, `name`, `price`, `rating`, `thumbnail`) VALUES ('2', '네슬레코리아 (제조국 : 아랍에미리트)', '초콜릿', '킷캣 오리지널 18입, 네슬레코리아 (제조국 : 아랍에미리트)', '0', '킷캣 오리지널 18입', '9980', '4.9', 'https://sitem.ssgcdn.com/21/16/61/item/1000529611621_i1_1100.jpg');
INSERT INTO `emart`.`products` (`id`, `brand`, `category`, `description`, `discount`, `name`, `price`, `rating`, `thumbnail`) VALUES ('3', 'HERSHEY CANADA INC (제조국 : 캐나다)', '초콜릿', '허쉬 다크초콜릿 아사이&블루베리210g, 초콜릿가공품', '0', '허쉬 다크초콜릿 아사이&블루베리210g', '9080', '4.8', 'https://sitem.ssgcdn.com/90/11/54/item/1000270541190_i1_1100.jpg');
INSERT INTO `emart`.`products` (`id`, `brand`, `category`, `description`, `discount`, `name`, `price`, `rating`, `thumbnail`) VALUES ('4', '(주)농심', '초콜릿', 'LINDT&SPRUNGLI SAS / (주)농심 / 서울특별시 동작구 여의대방로112(신대방동) (제조국 : 상세설명참조)', '0', '[린트]엑설런스 다크 99% 50g', '6860', '4.8', 'https://sitem.ssgcdn.com/50/75/43/item/1000029437550_i1_1100.jpg');

INSERT INTO `emart`.`carts` (`id`, `qty`, `product_id`, `user_id`) VALUES ('1', '3', '2', '1');
INSERT INTO `emart`.`carts` (`id`, `qty`, `product_id`, `user_id`) VALUES ('2', '3', '3', '1');

INSERT INTO `emart`.`events` (`id`, `name`) VALUES ('1', '발렌타인데이 행사');

INSERT INTO `emart`.`event_products` (`event_id`, `product_id`) VALUES ('1', '2');
INSERT INTO `emart`.`event_products` (`event_id`, `product_id`) VALUES ('1', '3');
INSERT INTO `emart`.`event_products` (`event_id`, `product_id`) VALUES ('1', '4');

