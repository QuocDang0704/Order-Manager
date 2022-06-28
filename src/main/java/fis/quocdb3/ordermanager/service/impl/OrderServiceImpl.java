package fis.quocdb3.ordermanager.service.impl;

import fis.quocdb3.ordermanager.domain.*;
import fis.quocdb3.ordermanager.dto.order.CreateOrderDTO;
import fis.quocdb3.ordermanager.dto.order.RemoveOrderItemDTO;
import fis.quocdb3.ordermanager.exception.*;
import fis.quocdb3.ordermanager.repository.OrderItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import fis.quocdb3.ordermanager.dto.order.CreateOrderItemDTO;
import fis.quocdb3.ordermanager.dto.order.OrderDTO;
import fis.quocdb3.ordermanager.repository.OrderRepository;
import fis.quocdb3.ordermanager.repository.ProductRepository;
import fis.quocdb3.ordermanager.service.OrderService;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    private final Boolean FLUS_PRODUCT = Boolean.TRUE;
    private final Boolean MINUS_PRODUCT = Boolean.FALSE;


    @Override
    public Page<OrderDTO> findAll(Pageable pageable) {
        return orderRepository.findAll(pageable).map(OrderDTO.Mapper::fromEntity);
    }

    @Override
    public OrderDTO findById(Long orderId) {
        return OrderDTO.Mapper.fromEntity(this.findOrderById(orderId));
    }

    @Override
    @Transactional
    public OrderDTO create(CreateOrderDTO createOrderDTO) {

        Order orderAdd = Order.builder()
                .customer(Customer.builder().id(createOrderDTO.getCustomerId()).build())
                .orderDateTime(LocalDateTime.now())
                .status(OrderStatus.CREATED)
                .totalAmount(0.0)
                .build();

        Order order = orderRepository.save(orderAdd);
        AtomicReference<Double> totalAmount = new AtomicReference<>(0.0);

        createOrderDTO.getOrderItems().forEach(i -> {
            Product product = this.findProductById(i.getProductId());
            //kiểm tra trong kho còn hay không
            if (product.getAvaiable() < i.getQuantity()) {
                throw new IllegalArgumentException(String.format("The quantity in stock is not enough to add: %s", i.getProductId()));
            }
            // Cập nhật lại số lượng còn lại trong product
            this.updateProductByAvaiable(product, i.getQuantity(), this.MINUS_PRODUCT);

            OrderItem orderItemAdd = OrderItem.builder()
                    .quantity(i.getQuantity())
                    .product(Product.builder().id(i.getProductId()).build())
                    .order(order)
                    .amount(i.getQuantity() * product.getPrice())
                    .build();
            totalAmount.updateAndGet(v -> v + orderItemAdd.getAmount());

            orderItemRepository.save(orderItemAdd);
        });
        // hoàn thành tính totalAmount
        order.setTotalAmount(totalAmount.get());

        return OrderDTO.Mapper.fromEntity(orderRepository.save(orderAdd));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Order order = this.findOrderById(id);
        this.checkStatusIsCANCELLED(order.getStatus());
        this.checkStatusIsCREATED(order.getStatus());

        // xóa orderItem
        order.getOrderItems().forEach(i -> {
            orderItemRepository.delete(i);
        });
        orderRepository.delete(order);
    }

    @Override
    @Transactional
    public OrderDTO addOrderItem(CreateOrderItemDTO createOrderItemDTO) {
        Order order = this.findOrderById(createOrderItemDTO.getOrderId());
        if (!"CREATED".equals(order.getStatus().toString())) {
            throw new IllegalArgumentException("status is not CREATED");
        }
        Product product = this.findProductById(createOrderItemDTO.getProductId());
        //kiểm tra trong kho còn hay không
        if (product.getAvaiable() < createOrderItemDTO.getQuantity()) {
            throw new ProductQuantityNotEngoughException(String.format("The quantity in stock is not enough to add: %s", createOrderItemDTO.getProductId()));
        }
        // Cập nhật lại số lượng còn lại trong product
        this.updateProductByAvaiable(product, createOrderItemDTO.getQuantity(), this.MINUS_PRODUCT);

        OrderItem orderItem = OrderItem.builder()
                .order(order)
                .quantity(createOrderItemDTO.getQuantity())
                .product(product)
                .amount(product.getPrice() * createOrderItemDTO.getQuantity())
                .build();

        order.getOrderItems().add(orderItem);
        Double totalAmount = order.getOrderItems().stream()
                .mapToDouble(OrderItem::getAmount)
                .reduce(0, Double::sum);
        order.setTotalAmount(totalAmount);

        orderItemRepository.save(orderItem);

        return OrderDTO.Mapper.fromEntity(orderRepository.save(order));
    }


    @Override
    @Transactional
    public OrderDTO removeOrderItem(RemoveOrderItemDTO removeOrderItemDTO) {
        Order order = this.findOrderById(removeOrderItemDTO.getOrderId());

        this.checkStatusIsCREATED(order.getStatus());

        order.getOrderItems().forEach(i -> {
            if (i.getId().equals(removeOrderItemDTO.getOrderItemId())) {
                Product product = this.findProductById(i.getProduct().getId());

                // Cập nhật lại số lượng còn lại trong product
                this.updateProductByAvaiable(product, i.getQuantity(), this.FLUS_PRODUCT);

                //cập nhật lại giá
                order.setTotalAmount(order.getTotalAmount() - i.getAmount());
                orderItemRepository.delete(i);
                return;
            }
        });

        return OrderDTO.Mapper.fromEntity(orderRepository.save(order));
    }

    @Override
    public OrderDTO updateStatus(Long id, OrderStatus status) {
        Order order = this.findOrderById(id);
        if (this.checkStatusIsCREATED(status)) {
            orderRepository.updateStatusById(status, id);
            order.setStatus(status);
            return OrderDTO.Mapper.fromEntity(order);
        }
        return null;
    }

    private Order findOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> {
                    throw new OrderNotFoundException("Can't not find Order by id:" +id);
                });
    }

    private Product findProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> {
                    throw new ProductNotFoundException("Can't not find Product by id:" +id);
                });
    }

    private Product updateProductByAvaiable(Product product, Integer quantityOrderItem, Boolean plusOrMinus) {
        if (plusOrMinus) {
            product.setAvaiable(product.getAvaiable() + quantityOrderItem);
        } else {
            product.setAvaiable(product.getAvaiable() - quantityOrderItem);
        }
        return productRepository.save(product);
    }
    private Boolean checkStatusIsCREATED(OrderStatus status) {
        if ("CREATED".equals(status.toString())) {
            return true;
        }
        throw new OrderCorrectStatus("status is not CREATED");
    }
    private Boolean checkStatusIsCANCELLED(OrderStatus status) {
        if ("CANCELLED".equals(status.toString())) {
            return true;
        }
        throw new OrderCorrectStatus("status is not CANCELLED");
    }


}
