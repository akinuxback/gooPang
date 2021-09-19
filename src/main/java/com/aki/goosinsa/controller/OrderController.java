package com.aki.goosinsa.controller;

import com.aki.goosinsa.asecurity.auth.PrincipalDetails;
import com.aki.goosinsa.domain.entity.order.Order;
import com.aki.goosinsa.domain.entity.order.OrderSearch;
import com.aki.goosinsa.domain.entity.orderItem.OrderItem;
import com.aki.goosinsa.domain.entity.user.User;
import com.aki.goosinsa.exception.NotFoundItemException;
import com.aki.goosinsa.repository.item.ItemRepository;
import com.aki.goosinsa.repository.order.OrderRepository;
import com.aki.goosinsa.repository.order.QDOrderRepository;
import com.aki.goosinsa.repository.orderItem.QDOrderItemRepository;
import com.aki.goosinsa.repository.user.UserRepository;
import com.aki.goosinsa.service.order.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/admin/order")
public class OrderController {

    private final OrderRepository orderRepository;
    private final OrderService orderService;
    private final QDOrderRepository qdOrderRepository;

    private final QDOrderItemRepository qdOrderItemRepository;

    private final UserRepository userRepository;
    private final ItemRepository itemRepository;

    @PostMapping("/orderPost")
    public String orderPost(Model model, RedirectAttributes rttr){
        User loginUser = getLoginUser();

        try {
            // order 생성
            Long orderId = orderService.order(loginUser.getId());
        } catch (NotFoundItemException e) {
            e.getMessage();
            rttr.addFlashAttribute("error1", "장바구니에 상품이 없습니다.");
            return "redirect:/admin/orderItem/getOrderItemList";
        }
        return "redirect:/admin/order/getOrderList";
    }

    @GetMapping("/getOrderList")
    public String orderList(Model model){
        User loginUser = getLoginUser();
        List<Order> orderList = qdOrderRepository.findByAllJoin(loginUser.getId());
//        List<OrderItem> orderItemList = qdOrderItemRepository.findListByIdAndStatusOrderJoinItemJoinUserJoinOrder(loginUser.getId());
        model.addAttribute("orderList", orderList);
        model.addAttribute("orderItemList", orderList);

        return "/admin/order/orderList";
    }

    // 로그인한 유저의 정보 가져오기
    private User getLoginUser() {
        PrincipalDetails user = (PrincipalDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        log.info(user.getId());
        log.info(user.getUsername());
        User userCompany = userRepository.findById(user.getId()).get();
        return userCompany;
    }

}
