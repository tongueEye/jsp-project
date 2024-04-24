package com.parsam.controller.user;

import com.parsam.comm.Action;
import com.parsam.comm.Forward;
import com.parsam.dto.ProductDTO;
import com.parsam.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class UserSoldListAction implements Action {
    @Override
    public Forward execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // 파라미터
        Long u_id = Long.valueOf(request.getParameter("u_id"));

        // 사용자 거래완료 내역 가져오기
        UserService service = UserService.getService();
        List<ProductDTO> list = service.getUserSoldList(u_id);

        request.setAttribute("u_id", u_id);
        request.setAttribute("list", list);

        // 판매완료 목록으로 이동
        Forward forward = new Forward();
        forward.setForward(true);
        forward.setUrl("WEB-INF/index.jsp?page=user/userSoldList.jsp");

        return forward;
    }
}
