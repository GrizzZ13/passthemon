package com.backend.passthemon.repositoryimpl;

import com.backend.passthemon.constant.GoodsConstant;
import com.backend.passthemon.constant.OrderConstant;
 import com.backend.passthemon.dao.GoodsDao;
 import com.backend.passthemon.dao.OrderDao;
import com.backend.passthemon.dao.UserDao;
import com.backend.passthemon.dao.WantsDao;
import com.backend.passthemon.entity.Goods;
import com.backend.passthemon.entity.Order;
import com.backend.passthemon.entity.User;
import com.backend.passthemon.entity.Wants;
import com.backend.passthemon.repositoryimpl.OrderRepositoryImpl;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.*;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;

public class OrderRepositoryImplUnitTest {
    @Mock
    OrderDao orderDao;
    @Mock
    GoodsDao goodsDao;
    @Mock
    WantsDao wantsDao;
    @Mock
    UserDao userDao;
    @InjectMocks
    private OrderRepositoryImpl orderRepository;

    private Timestamp timestamp = new Timestamp(System.currentTimeMillis());
    private BigDecimal price = new BigDecimal(19.99);
    private String name = "笔记本电脑", description = "戴尔牌子", comment = "东东好帅";
    private Integer userId = 1, goodsNum = 1, orderId = 1,
            inventory = goodsNum + 1, rating = 5, fetchPage = 0, pageItemNumber = 6,
            state = 1, category = 0, attrition = 0, status = 1, wantsId = 1;
    Pageable pageable = PageRequest.of(fetchPage, pageItemNumber, Sort.Direction.DESC, "timestamp");
    private User buyer = new User(), seller = new User();
    private Goods goods = new Goods(price, name, inventory, description, timestamp, seller, state, category, attrition);
    private Order order = new Order(goodsNum, null, null,
            price.multiply(BigDecimal.valueOf(goodsNum)), null, buyer, seller, goods);
    private List<Order> buyerOrderList = new ArrayList<>(), sellerOrderList = new ArrayList<>();
    private Page<Order> buyerOrderPageList = new Page<Order>() {
        @Override
        public int getTotalPages() {
            return 0;
        }

        @Override
        public long getTotalElements() {
            return 0;
        }

        @Override
        public <U> Page<U> map(Function<? super Order, ? extends U> converter) {
            return null;
        }

        @Override
        public int getNumber() {
            return 0;
        }

        @Override
        public int getSize() {
            return 0;
        }

        @Override
        public int getNumberOfElements() {
            return 0;
        }

        @Override
        public List<Order> getContent() {
            return buyerOrderList;
        }

        @Override
        public boolean hasContent() {
            return false;
        }

        @Override
        public Sort getSort() {
            return null;
        }

        @Override
        public boolean isFirst() {
            return false;
        }

        @Override
        public boolean isLast() {
            return false;
        }

        @Override
        public boolean hasNext() {
            return false;
        }

        @Override
        public boolean hasPrevious() {
            return false;
        }

        @Override
        public Pageable nextPageable() {
            return null;
        }

        @Override
        public Pageable previousPageable() {
            return null;
        }

        @Override
        public Iterator<Order> iterator() {
            return null;
        }
    };
    private Page<Order> sellerOrderPageList = new Page<Order>() {
        @Override
        public int getTotalPages() {
            return 0;
        }

        @Override
        public long getTotalElements() {
            return 0;
        }

        @Override
        public <U> Page<U> map(Function<? super Order, ? extends U> converter) {
            return null;
        }

        @Override
        public int getNumber() {
            return 0;
        }

        @Override
        public int getSize() {
            return 0;
        }

        @Override
        public int getNumberOfElements() {
            return 0;
        }

        @Override
        public List<Order> getContent() {
            return sellerOrderList;
        }

        @Override
        public boolean hasContent() {
            return false;
        }

        @Override
        public Sort getSort() {
            return null;
        }

        @Override
        public boolean isFirst() {
            return false;
        }

        @Override
        public boolean isLast() {
            return false;
        }

        @Override
        public boolean hasNext() {
            return false;
        }

        @Override
        public boolean hasPrevious() {
            return false;
        }

        @Override
        public Pageable nextPageable() {
            return null;
        }

        @Override
        public Pageable previousPageable() {
            return null;
        }

        @Override
        public Iterator<Order> iterator() {
            return null;
        }
    };
    private Wants wants = new Wants();
    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        Mockito.when(orderDao.findById(orderId)).thenReturn(order);
        Mockito.when(orderDao.findAllByBuyer(buyer, pageable)).thenReturn(buyerOrderPageList);
        Mockito.when(orderDao.findAllBySeller(buyer, pageable)).thenReturn(sellerOrderPageList);
        Mockito.when(orderDao.findAllBySeller(seller, pageable)).thenReturn(sellerOrderPageList);
    };

    @Test
    public void changeOrderStatus() {
        Mockito.when(orderDao.findById(orderId)).thenReturn(null);
        Assert.assertEquals(orderRepository.changeOrderStatus(orderId, status).toString(), String.valueOf(OrderConstant.ORDER_NOT_FOUND));
        Mockito.verify(orderDao).findById(orderId);

        Mockito.when(orderDao.findById(orderId)).thenReturn(order);
        order.setStatus(status);
        Assert.assertEquals(orderRepository.changeOrderStatus(orderId, status).toString(), String.valueOf(OrderConstant.SUCCESS));
        Mockito.verify(orderDao).save(order);
    }
    @Test
    public void makeOrder() {
        Assert.assertEquals(orderRepository.makeOrder(buyer, goods, goodsNum).toString(),
                String.valueOf(OrderConstant.SUCCESS));
        Mockito.verify(orderDao).save(order);
        Mockito.verify(goodsDao).save(goods);
        Integer originState = goods.getState();
        goods.setState(GoodsConstant.SOLD_OUT);
        Assert.assertEquals(orderRepository.makeOrder(buyer, goods, goodsNum).toString(),
                String.valueOf(GoodsConstant.SOLD_OUT));
        goods.setState(originState);

        User originUser = goods.getUser();
        goods.setUser(buyer);
        Assert.assertEquals(orderRepository.makeOrder(buyer, goods, goodsNum).toString(),
                String.valueOf(OrderConstant.BUYER_SELLER_SAME));
        goods.setUser(originUser);

        Integer originInventory = goods.getInventory();
        goods.setInventory(goodsNum);
        Assert.assertEquals(orderRepository.makeOrder(buyer, goods, goodsNum).toString(),
                String.valueOf(OrderConstant.SUCCESS));
        goods.setInventory(originInventory);

        originInventory = goods.getInventory();
        goods.setInventory(0);
        Assert.assertEquals(orderRepository.makeOrder(buyer, goods, goodsNum).toString(),
                String.valueOf(GoodsConstant.SOLD_OUT));
        goods.setInventory(originInventory);
    }

    @Test
    public void affirmWants() {
        Mockito.when(wantsDao.findById(wantsId)).thenReturn(null);
        Assert.assertEquals(orderRepository.affirmWants(wantsId).toString(), String.valueOf(OrderConstant.WANTS_NOT_FOUNT));
        Mockito.verify(wantsDao).findById(wantsId);

        Mockito.when(wantsDao.findById(wantsId)).thenReturn(wants);
        wants.setGoods(goods);
        wants.setBuyer(buyer);
        wants.setGoods(goods);
        wants.setNum(1);
        Assert.assertEquals(orderRepository.affirmWants(wantsId).toString(), String.valueOf(OrderConstant.SUCCESS));
        Mockito.verify(wantsDao).deleteAllWantsByGoods(goods);
    }
    @Test
    public void getOrdersByUser() {
        List<Order> orderList = buyerOrderList; orderList.addAll(sellerOrderList);
        Assert.assertEquals(orderRepository.getOrdersByUser(buyer, null, null, fetchPage).toString(),
                orderList.toString());
        Mockito.verify(orderDao).findAllByBuyer(buyer, pageable);
        Mockito.verify(orderDao).findAllBySeller(buyer, pageable);
    }

    @Test
    public void getOrdersBySeller() {
        Mockito.when(orderDao.findAllBySeller(seller, pageable)).thenReturn(sellerOrderPageList);
        Assert.assertEquals(orderRepository.getOrdersBySeller(seller, null, null, fetchPage).toString(),
                sellerOrderList.toString());
        Mockito.verify(orderDao).findAllBySeller(seller, pageable);

        Mockito.when(orderDao.findAllBySellerAndTimestampBetween(seller, timestamp, timestamp, pageable)).thenReturn(sellerOrderPageList);
        Assert.assertEquals(orderRepository.getOrdersBySeller(seller, timestamp, timestamp, fetchPage).toString(),
                sellerOrderList.toString());
        Mockito.verify(orderDao).findAllBySellerAndTimestampBetween(seller, timestamp, timestamp, pageable);

        Mockito.when(orderDao.findAllBySellerAndTimestampAfter(seller, timestamp, pageable)).thenReturn(sellerOrderPageList);
        Assert.assertEquals(orderRepository.getOrdersBySeller(seller, timestamp, null, fetchPage).toString(),
                sellerOrderList.toString());
        Mockito.verify(orderDao).findAllBySellerAndTimestampAfter(seller, timestamp, pageable);

        Mockito.when(orderDao.findAllBySellerAndTimestampBefore(seller, timestamp, pageable)).thenReturn(sellerOrderPageList);
        Assert.assertEquals(orderRepository.getOrdersBySeller(seller, null, timestamp, fetchPage).toString(),
                sellerOrderList.toString());
        Mockito.verify(orderDao).findAllBySellerAndTimestampBefore(seller, timestamp, pageable);
    }

    @Test
    public void getOrdersByBuyer() {
        Mockito.when(orderDao.findAllByBuyer(buyer, pageable)).thenReturn(buyerOrderPageList);
        Assert.assertEquals(orderRepository.getOrdersByBuyer(buyer, null, null, fetchPage).toString(),
                buyerOrderList.toString());
        Mockito.verify(orderDao).findAllByBuyer(buyer, pageable);

        Mockito.when(orderDao.findAllByBuyerAndTimestampBetween(buyer, timestamp, timestamp, pageable)).thenReturn(buyerOrderPageList);
        Assert.assertEquals(orderRepository.getOrdersByBuyer(buyer, timestamp, timestamp, fetchPage).toString(),
                buyerOrderList.toString());
        Mockito.verify(orderDao).findAllByBuyerAndTimestampBetween(buyer, timestamp, timestamp, pageable);

        Mockito.when(orderDao.findAllByBuyerAndTimestampAfter(buyer, timestamp, pageable)).thenReturn(buyerOrderPageList);
        Assert.assertEquals(orderRepository.getOrdersByBuyer(buyer, timestamp, null, fetchPage).toString(),
                buyerOrderList.toString());
        Mockito.verify(orderDao).findAllByBuyerAndTimestampAfter(buyer, timestamp, pageable);

        Mockito.when(orderDao.findAllByBuyerAndTimestampBefore(buyer, timestamp, pageable)).thenReturn(buyerOrderPageList);
        Assert.assertEquals(orderRepository.getOrdersByBuyer(buyer, null, timestamp, fetchPage).toString(),
                buyerOrderList.toString());
        Mockito.verify(orderDao).findAllByBuyerAndTimestampBefore(buyer, timestamp, pageable);
    }

    @Test
    public void commentAndRateOnOrder() {
        Mockito.when(orderDao.findById(orderId)).thenReturn(null);
        Assert.assertEquals(orderRepository.commentAndRateOnOrder(orderId, comment, rating).toString(), String.valueOf(OrderConstant.ORDER_NOT_FOUND));
        Mockito.verify(orderDao).findById(orderId);

        Mockito.when(orderDao.findById(orderId)).thenReturn(order);
        order.setComment(comment);
        order.setRating(rating);
        order.setSeller(seller);
        sellerOrderList = new ArrayList<>();
        sellerOrderList.add(order);
        seller.setSellerList(sellerOrderList);
        seller.setCredit(new Double(rating));
        Assert.assertEquals(orderRepository.commentAndRateOnOrder(orderId, comment, rating).toString(),
                String.valueOf(OrderConstant.SUCCESS));
        Mockito.verify(orderDao).saveAndFlush(order);
        Mockito.verify(userDao).saveAndFlush(seller);

    }
}
