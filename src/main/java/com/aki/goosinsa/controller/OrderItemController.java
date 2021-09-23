package com.aki.goosinsa.controller;

import com.aki.goosinsa.asecurity.auth.PrincipalDetails;
import com.aki.goosinsa.domain.entity.orderItem.OrderItem;
import com.aki.goosinsa.domain.entity.user.User;
import com.aki.goosinsa.repository.orderItem.OrderItemRepository;
import com.aki.goosinsa.repository.orderItem.QDOrderItemRepository;
import com.aki.goosinsa.repository.user.UserRepository;
import com.aki.goosinsa.service.OrderItem.OrderItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/admin/orderItem/")
public class OrderItemController {

    private final UserRepository userRepository;

    private final QDOrderItemRepository qdOrderItemRepository;
    private final OrderItemService orderItemService;


    @PostMapping("/orderItemPost")
    public String orderItemPost(@RequestParam Long itemId, @RequestParam int count, RedirectAttributes rttr){

        User loginUser = getLoginUser();
        orderItemService.createOrderItem(loginUser.getId(), itemId, count);
        rttr.addFlashAttribute("message","장바구니로 상품을 담았습니다.");
        return "redirect:/home/food/getFoodItem/"+ itemId;
    }

    @GetMapping("/getOrderItemList")
    public String getOrderItemList(Model model){
        Long loginId = getLoginUser().getId();
        List<OrderItem> orderItemList = qdOrderItemRepository.findListByIdAndStatusReadyJoinItemJoinUser(loginId);
        int total = orderItemList.stream().mapToInt(OrderItem::getTotalPrice).sum();

        model.addAttribute("orderItemList", orderItemList);
        model.addAttribute("total", total);

        return "admin/orderItem/orderItemList";
    }

    @PostMapping("/deleteOrderItem")
    public String deleteOrderItem(Long deleteItemId){
        orderItemService.deleteOrderItem(deleteItemId);
        return "redirect:/admin/orderItem/getOrderItemList";
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
