package com.aki.goosinsa.service.OrderItem;

import com.aki.goosinsa.domain.entity.item.Item;
import com.aki.goosinsa.domain.entity.orderItem.OrderItem;
import com.aki.goosinsa.domain.entity.user.User;
import com.aki.goosinsa.exception.NotFoundItemException;
import com.aki.goosinsa.repository.item.ItemRepository;
import com.aki.goosinsa.repository.order.OrderRepository;
import com.aki.goosinsa.repository.orderItem.OrderItemRepository;
import com.aki.goosinsa.repository.user.UserRepository;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderItemServiceImpl implements OrderItemService {

    private final UserRepository userRepository;
    private final OrderItemRepository orderItemRepository;
    private final ItemRepository itemRepository;

    @SneakyThrows
    @Override
    public Long createOrderItem(Long userId, Long itemId, int count) {

        User findUser = userRepository.findById(userId).orElseThrow();
        Item findItem = (Item) itemRepository.findById(itemId).orElseThrow();

        OrderItem orderItem = OrderItem.createOrderItem(findUser, findItem, findItem.getPrice(), count);

        OrderItem saveOrderItem = orderItemRepository.save(orderItem);

        return saveOrderItem.getId();
    }

    @Override
    public void deleteOrderItem(Long orderItemId) {
        OrderItem orderItem = orderItemRepository.findById(orderItemId).orElseThrow();
        orderItem.deleteOrderItem();
        orderItemRepository.deleteById(orderItemId);
    }
}
