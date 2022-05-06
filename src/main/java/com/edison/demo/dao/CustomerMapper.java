package com.edison.demo.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.edison.demo.model.Customer;
import com.edison.demo.model.CustomerDto;

@Repository
public interface CustomerMapper {

    /**
     * 搜尋客戶
     * @return
     */
//	@Select({
//         "<script>",
//             "SELECT * FROM UserInfo",
//             "WHERE CUSTOMER_ID in",
//                 "<foreach collection='ids' item='id' open='(' separator=',' close=')'>",
//                 "#{id}",
//                 "</foreach>",
//         "</script>"
//	})
//	@Results({
//        @Result(property = "id",  column = "CUSTOMER_ID"),
//        @Result(property = "name", column = "CUSTOMER_NAME"),
//        @Result(property = "gender",  column = "CUSTOMER_GENDER"),
//        @Result(property = "city", column = "CUSTOMER_CITY"),
//        @Result(property = "signTime",  column = "CUSTOMER_SIGN_TIME")
//    })
    public List<Customer> findByCustomerIds(@Param("ids") List<String> ids);
	
//    @Insert("insert into UserInfo(CUSTOMER_ID, CUSTOMER_NAME, CUSTOMER_GENDER, CUSTOMER_CITY, CUSTOMER_SIGN_TIME) values (#{user.id},#{user.name},#{user.gender},#{user.city},#{user.signTime});")
    public int insertCustomer(@Param(value = "user") CustomerDto customer);
}
