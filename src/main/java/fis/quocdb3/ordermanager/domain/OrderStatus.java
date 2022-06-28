package fis.quocdb3.ordermanager.domain;

public enum OrderStatus {
    CREATED, /* Trang thai da khoi tao thanh cong*/
    WAITING_APPROVAL, /* Trang thai cho phe duyet*/
    APPROVED, /* Trang thai da duoc phe duyet */
    PAID, /* Trang thai da thanh toan thanh cong */
    CANCELLED /*Order da bi Huy*/
}
