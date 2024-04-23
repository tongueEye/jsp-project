package com.parsam.dao;

import com.parsam.dto.ProductDTO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;


public class ProductDAO {
    private static ProductDAO dao = new ProductDAO();
    public static ProductDAO getDao() {return dao;}
    private ProductDAO(){}


    public void insertData(Connection conn, ProductDTO pdto) throws SQLException {
        StringBuilder sql = new StringBuilder();
        sql.append(" insert into product  (                               ");
        sql.append("                         p_name                       ");
        sql.append("                         , p_desc                     ");
        sql.append("                         , p_price                    ");
        sql.append("                         , p_img                      ");
        sql.append("                         , openchat                   ");
        sql.append("                         , p_date                     ");
        sql.append("                         , p_sold                     ");
        sql.append("                         , p_state                    ");
        sql.append("                         , p_cate                     ");
        sql.append("                         , p_readno                   ");
        sql.append("                         , p_fav                      ");
        sql.append("                         , p_stock                    ");
        sql.append("                         , p_place                    ");
        sql.append("                         , p_trade                    ");
        sql.append("                         , u_id    )                  ");
        sql.append(" values(?,?,?,?,?,CURDATE(),false,?,?,0,0,0,?,?,?)    ");
        try (
                PreparedStatement pstmt = conn.prepareStatement(sql.toString());
                ){
            pstmt.setString(1, pdto.getP_name());
            pstmt.setString(2, pdto.getP_desc());
            pstmt.setInt(3, pdto.getP_price());
            pstmt.setString(4, pdto.getP_img());
            pstmt.setString(5, pdto.getP_openchat());
            pstmt.setString(6,pdto.getP_state());
            pstmt.setString(7,pdto.getP_cate());
            pstmt.setString(8, pdto.getP_place());
            pstmt.setString(9, pdto.getP_trade());
            pstmt.setLong(10, pdto.getU_id());
            pstmt.executeUpdate();
        }
    }
  
  public List<ProductDTO> getList(Connection conn) throws SQLException {
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT   p_id                   ");
        sql.append("        , p_img                  ");
        sql.append("        , p_cate                 ");
        sql.append("        , p_name                 ");
        sql.append("        , p_price                ");
        sql.append("        , p_state                ");
        sql.append("        , p_fav                  ");
        sql.append(" FROM product                    ");

        ResultSet rs = null;
        List<ProductDTO> arr = new ArrayList<>();

        try (PreparedStatement pstmt = conn.prepareStatement(sql.toString())){
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ProductDTO dto = new ProductDTO();
                dto.setP_id(rs.getLong("p_id"));
                dto.setP_img(rs.getString("p_img"));
                dto.setP_cate(rs.getString("p_cate"));
                dto.setP_name(rs.getString("p_name"));
                dto.setP_price(rs.getInt("p_price"));
                dto.setP_state(rs.getString("p_state"));
                dto.setP_fav(rs.getInt("p_fav"));
                arr.add(dto);
            }
        }
        return arr;
    }

    public int addReadno(Connection conn, long pid) throws SQLException{

        StringBuilder sql = new StringBuilder();
        sql.append(" update  product  set           ");
        sql.append(" p_readno=p_readno+1            ");
        sql.append(" where p_id=?                   ");
        int result = 0;
        try (
                PreparedStatement pstmt = conn.prepareStatement(sql.toString());
                ){
            pstmt.setLong(1, pid);
            result = pstmt.executeUpdate();
        }
        return result;
    }

    public ProductDTO getDetail(Connection conn, long pid) throws SQLException{
        StringBuilder sql = new StringBuilder();
        sql.append(" select    p_id                                       ");
        sql.append("           ,p_name                                    ");
        sql.append("           , p_desc                                   ");
        sql.append("           , p_price                                  ");
        sql.append("           , p_img                                    ");
        sql.append("           , openchat                                 ");
        sql.append("           , p_date                                   ");
        sql.append("           , p_sold                                   ");
        sql.append("           , p_state                                  ");
        sql.append("           , p_cate                                   ");
        sql.append("           , p_readno                                 ");
        sql.append("           , p_fav                                    ");
        sql.append("           , p_stock                                  ");
        sql.append("           , SUBSTRING_INDEX(p_place,' ',2) AS p_place");
        sql.append("           , p_trade                                  ");
        sql.append("           , u_id                                     ");
        sql.append(" from product                                         ");
        sql.append(" where p_id=?                                         ");

        ProductDTO dto = new ProductDTO();
        ResultSet rs = null;

        try(
                PreparedStatement pstmt = conn.prepareStatement(sql.toString())
                ) {
            pstmt.setLong(1, pid);
            rs= pstmt.executeQuery();

            while (rs.next()){
                dto.setP_id(rs.getLong("p_id"));
                dto.setP_name(rs.getString("p_name"));
                dto.setP_desc(rs.getString("p_desc"));
                dto.setP_price(rs.getInt("p_price"));
                dto.setP_img(rs.getString("p_img"));
                dto.setP_openchat(rs.getString("openchat"));
                dto.setP_date(rs.getDate("p_date").toLocalDate());
                dto.setP_sold(rs.getBoolean("p_sold"));
                dto.setP_state(rs.getString("p_state"));
                dto.setP_cate(rs.getString("p_cate"));
                dto.setReadno(rs.getInt("p_readno"));
                dto.setP_fav(rs.getInt("p_fav"));
                dto.setP_stock(rs.getInt("p_stock"));
                dto.setP_place(rs.getString("p_place"));
                dto.setP_trade(rs.getString("p_trade"));
                dto.setU_id(rs.getLong("u_id"));
            }
        } finally {
            disconn(rs);
        }
        return dto;
    }

    public void disconn(ResultSet rs){
        if (rs!=null){
            try {
                rs.close();
            } catch (Exception e){
                System.out.println(e);
            }
        }
    }

}

