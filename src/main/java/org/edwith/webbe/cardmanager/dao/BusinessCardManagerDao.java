package org.edwith.webbe.cardmanager.dao;

import org.edwith.webbe.cardmanager.dto.BusinessCard;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BusinessCardManagerDao {

    private static String dbUrl = "jdbc:mysql://localhost:3306/project_a?characterEncoding=UTF-8&serverTimezone=UTC";
    private static String dbUser = "projectuser-a";
    private static String dbPassword = "00000000";

    public List<BusinessCard> searchBusinessCard(String keyword){
        List<BusinessCard> list = new ArrayList<>();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // 1. 드라이버 로딩하기
        } catch (Exception e) {
            e.printStackTrace();
        }

        String sql = "select name, phone, company_name, create_date from business_card where name like ?"; // 쿼리 만들기


        try (Connection conn = DriverManager.getConnection(dbUrl, dbUser, dbPassword); // 2. connection 객체 얻기
             PreparedStatement ps = conn.prepareStatement(sql)) { // 3. statement 객체 얻기
            ps.setString(1,"%"+keyword+"%");

            try (ResultSet rs = ps.executeQuery()) {// 4. statement 객체 실행

                while (rs.next()) {
                    String name = rs.getString("name");
                    String phone = rs.getString("phone");
                    String company_name = rs.getString("company_name");
                    Date create_date = rs.getTimestamp("create_date");

                    BusinessCard businessCard = new BusinessCard(name, phone, company_name, create_date);

                    list.add(businessCard);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return list;
    }


    public BusinessCard addBusinessCard(BusinessCard businessCard){

        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // 1. 드라이버 로딩하기
        } catch (Exception e) {
            e.printStackTrace();
        }

        String sql = "insert into business_card (name, phone, company_name, create_date) values (?,?,?,?)"; // 쿼리 만들기

        try (Connection conn = DriverManager.getConnection(dbUrl, dbUser, dbPassword); // 2. connection 객체 얻기
             PreparedStatement ps = conn.prepareStatement(sql)) { // 3. statement 객체 얻기
            ps.setString(1, businessCard.getName());
            ps.setString(2, businessCard.getPhone());
            ps.setString(3, businessCard.getCompanyName());
            ps.setTimestamp(4, new java.sql.Timestamp(businessCard.getCreateDate().getTime()));

            ps.executeUpdate();// 4. statement 객체 실행

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return businessCard;
    }
}
