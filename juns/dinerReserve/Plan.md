1. 회원기능
   - 회원가입
   - 로그인
   - 로그아웃
   - 내 정보 조회
   - 내 예약 목록 조회
   - 회원 정보 수정
     비밀번호 변경
     회원 탈퇴

CREATE TABLE users (
id BIGINT AUTO_INCREMENT PRIMARY KEY,
email VARCHAR(255) NOT NULL UNIQUE,
password VARCHAR(255) NOT NULL,
name VARCHAR(100) NOT NULL,
phone_number VARCHAR(20) NOT NULL,
role VARCHAR(20) NOT NULL DEFAULT 'USER',
status VARCHAR(20) NOT NULL DEFAULT 'ACTIVE',
created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    CONSTRAINT chk_user_role
        CHECK (role IN ('USER', 'ADMIN')),

    CONSTRAINT chk_user_status
        CHECK (status IN ('ACTIVE', 'INACTIVE', 'BANNED', 'DELETED'))
);

2. 식당 정보 관리
   - 영업 시간 설정
     예약 가능 시간대 설정
     테이블 관리
     휴무일 관리

CREATE TABLE restaurants (
id BIGINT AUTO_INCREMENT PRIMARY KEY,
name VARCHAR(100) NOT NULL,
address VARCHAR(255) NOT NULL,
phone_number VARCHAR(20) NOT NULL,
description TEXT,
opening_time TIME,
closing_time TIME,
active BOOLEAN NOT NULL DEFAULT TRUE,
created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);


3. 테이블 관리
    -테이블 등록
   테이블 목록 조회
   테이블 수정
   테이블 삭제 또는 비활성화


4. 예약 기능
    - 예약 가능 날짜 선택
      예약 가능 시간 선택
      예약 인원 입력
      예약 요청
      내 예약 확인
      내 예약 취소
    - 예약자
      예약 날짜
      예약 시간
      예약 인원
      배정 테이블
      예약 상태
      요청사항
      - REQUESTED  예약 요청
        CONFIRMED  예약 확정
        CANCELLED  예약 취소
        COMPLETED  방문 완료
        NO_SHOW    노쇼

CREATE TABLE restaurant_tables (
id BIGINT AUTO_INCREMENT PRIMARY KEY,
restaurant_id BIGINT NOT NULL,
table_name VARCHAR(50) NOT NULL,
capacity INT NOT NULL,
active BOOLEAN NOT NULL DEFAULT TRUE,
created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    CONSTRAINT fk_restaurant_tables_restaurant
        FOREIGN KEY (restaurant_id) REFERENCES restaurants(id),

    CONSTRAINT chk_restaurant_table_capacity
        CHECK (capacity > 0),

    CONSTRAINT uq_restaurant_table_name
        UNIQUE (restaurant_id, table_name)
);


CREATE TABLE reservations (
id BIGINT AUTO_INCREMENT PRIMARY KEY,
restaurant_id BIGINT NOT NULL,
user_id BIGINT NOT NULL,
table_id BIGINT NOT NULL,
slot_id BIGINT NOT NULL,
reservation_date DATE NOT NULL,
party_size INT NOT NULL,
status VARCHAR(20) NOT NULL DEFAULT 'CONFIRMED',
request_message TEXT,
created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
cancelled_at DATETIME,

    CONSTRAINT fk_reservations_restaurant
        FOREIGN KEY (restaurant_id) REFERENCES restaurants(id),

    CONSTRAINT fk_reservations_user
        FOREIGN KEY (user_id) REFERENCES users(id),

    CONSTRAINT fk_reservations_table
        FOREIGN KEY (table_id) REFERENCES restaurant_tables(id),

    CONSTRAINT fk_reservations_slot
        FOREIGN KEY (slot_id) REFERENCES reservation_slots(id),

    CONSTRAINT chk_reservation_party_size
        CHECK (party_size > 0),

    CONSTRAINT chk_reservation_status
        CHECK (status IN ('REQUESTED', 'CONFIRMED', 'CANCELLED', 'COMPLETED', 'NO_SHOW'))
);


5. 관리자 예약 관리
    - 전체 예약 목록 조회
    날짜별 예약 조회
    예약 상세 조회
    예약 상태 변경
    예약 취소 처리
    방문 완료 처리
    노쇼 처리
    - 오늘 예약
    날짜별 예약
    예약 상태별 조회
    고객 이름 검색
    전화번호 검색



6. 예약 중복 체크
    - 같은 날짜
      같은 시간
      같은 테이블
      예약 상태가 CONFIRMED 또는 REQUESTED



7. 예약 가능 시간대
    - 11:00
      11:30
      12:00
      12:30
      13:00
      17:00
      17:30
      18:00
      18:30
      19:00

CREATE TABLE reservation_slots (
id BIGINT AUTO_INCREMENT PRIMARY KEY,
restaurant_id BIGINT NOT NULL,
slot_time TIME NOT NULL,
active BOOLEAN NOT NULL DEFAULT TRUE,
created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    CONSTRAINT fk_reservation_slots_restaurant
        FOREIGN KEY (restaurant_id) REFERENCES restaurants(id),

    CONSTRAINT uq_reservation_slot_time
        UNIQUE (restaurant_id, slot_time)
);

8. 리뷰 기능
   CREATE TABLE reviews (
   id BIGINT AUTO_INCREMENT PRIMARY KEY,
   restaurant_id BIGINT NOT NULL,
   reservation_id BIGINT NOT NULL,
   user_id BIGINT NOT NULL,
   rating DECIMAL(2,1) NOT NULL,
   content TEXT NOT NULL,
   visible BOOLEAN NOT NULL DEFAULT TRUE,
   created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
   updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

   CONSTRAINT fk_reviews_restaurant
   FOREIGN KEY (restaurant_id) REFERENCES restaurants(id),
   CONSTRAINT fk_reviews_reservation
   FOREIGN KEY (reservation_id) REFERENCES reservations(id),
   CONSTRAINT fk_reviews_user
   FOREIGN KEY (user_id) REFERENCES users(id),
   CONSTRAINT uq_reviews_reservation UNIQUE (reservation_id),
   CONSTRAINT chk_review_rating CHECK (rating >= 0.0 AND rating <= 5.0)
   );