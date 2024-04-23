package com.parsam.controller;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;
import com.parsam.comm.Action;
import com.parsam.comm.Forward;
import com.parsam.dto.ProductDTO;
import com.parsam.service.ProductService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ProductWriteResultAction implements Action {
    @Override
    public Forward execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int filesize=1024*1024*100;
        String uploadpath=request.getServletContext().getRealPath("productUpload");
        MultipartRequest multi = new MultipartRequest(request, uploadpath, filesize,"utf-8",new DefaultFileRenamePolicy());

        String pimg = multi.getFilesystemName("pimg");
        String pname = multi.getParameter("pname");
        int pprice = Integer.parseInt(multi.getParameter("pprice"));
        String pcate = multi.getParameter("pcate");
        int pcnt = Integer.parseInt(multi.getParameter("pcnt"));
        String pdesc = multi.getParameter("pdesc");
        String popenchat = multi.getParameter("popenchat");
        String pstate = multi.getParameter("pstate");
        String ptrade = multi.getParameter("ptrade");
        String pplace = multi.getParameter("pplace");
        long u_id = Long.parseLong(multi.getParameter("u_id"));

        ProductDTO pdto = new ProductDTO();
        pdto.setP_img(pimg);
        pdto.setP_name(pname);
        pdto.setP_price(pprice);
        pdto.setP_cate(pcate);
        pdto.setP_cnt(pcnt);
        pdto.setP_desc(pdesc);
        pdto.setP_openchat(popenchat);
        pdto.setP_state(pstate);
        pdto.setP_place(pplace);
        pdto.setP_trade(ptrade);
        pdto.setU_id(u_id);

        ProductService service = ProductService.getService();
        service.insertData(pdto);

        Forward forward = new Forward();
        forward.setForward(true);
        forward.setUrl("product_write_alert.do");
        return forward;
    }
}
