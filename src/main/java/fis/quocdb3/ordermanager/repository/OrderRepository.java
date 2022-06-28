package fis.quocdb3.ordermanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import fis.quocdb3.ordermanager.domain.Order;
import fis.quocdb3.ordermanager.domain.OrderStatus;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    @Modifying
    @Transactional
    @Query("update Order o set o.status=:status where o.id=:id")
    void updateStatusById(
            @Param("status") OrderStatus status,
            @Param("id") Long id
            );
}
