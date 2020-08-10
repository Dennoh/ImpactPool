package tcds.or.tcdsapp.Retrofit;


import java.util.List;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import tcds.or.tcdsapp.Model.Banner;
import tcds.or.tcdsapp.Model.Book;
import tcds.or.tcdsapp.Model.CheckUserResponse;
import tcds.or.tcdsapp.Model.Order;
import tcds.or.tcdsapp.Model.User;


public interface TcdsAPI {
   // @FormUrlEncoded
//    @POST("checkuser.php")//checkuser.php
//    Call<CheckUserResponse>checkuserexists(@Field("phone") String phone);

    @FormUrlEncoded
    @POST("checkuser.php")//checkuser.php
    Call<CheckUserResponse>checkuserexists(@Field("phone") String phone);

    @FormUrlEncoded
    @POST("getuser.php")//getuser.php
    Call<User> getUserInformation(
            @Field("phonenumber") String phone);

    @FormUrlEncoded
    @POST("getorder.php")
    Observable <List<Order>> getOrder(@Field("userPhone") String userPhone,
                                      @Field("status") String status);

    @FormUrlEncoded
    @POST("register.php")
    Call<User>registerNewUser(
            @Field("name") String name,
            @Field("phonenumber") String phone,
            @Field("location") String location);


    @FormUrlEncoded
    @POST("reportVendor.php")
    Call<String>reportVendor(
            @Field("problem") String problem,
            @Field("userphone") String userphone,
            @Field("vendorname") String vendorname,
            @Field("vendorphone") String vendorphone,
            @Field("vendorID") String vendorId
    );



    @FormUrlEncoded
    @POST("insertnew_purchaseOrder.php")
    Observable<String>insertPurchaseOrder(@Field("username") String username,
                                          @Field("userlocation") String userlocation,
                                          @Field("userphone") String userphone,
                                          @Field("details") String details,
                                          @Field("orderdate") String orderdate
    );

    @FormUrlEncoded
    @POST("server/order/update_confirmOrderReceived.php")
    Observable<String>ConfirmOrderReceived(
            @Field("order_id") long orderId,
            @Field("confirmed") String confirmed
    );

    @FormUrlEncoded
    @POST("server/order/update_feedbackandRating.php")
    Observable<String>sendFeedbcakAndRating(
            @Field("vendor_phone") String vendor_phone,
            @Field("rate") String rate,
            @Field("feedback") String feedback
    );

    @GET("getbanner.php")
    Observable<List<Banner>> getbanners();

//   @FormUrlEncoded
//    @POST("getPopularProducts.php")
//    Observable <List<AllServices>> getPopularProducts(
//           @Field("type") String type
//   );
//http://mbinitiative.com/broadcastgateway_mhf/pokeadatasmstuma.php
    @FormUrlEncoded
    @POST("SendNextByeSMSApi.php")
    Observable<String> SendSMSBULK(
            @Field("phone_number") String phone,
            @Field("message_body") String message_body
    );

    @FormUrlEncoded
    @POST("getAllUserOrderNotification.php")
    Call<String> getTotalCount(
            @Field("UserPhone") String UserPhone
    );

    @FormUrlEncoded
    @POST("getLatestProducts.php")
    Observable <List<Book>> getFlashDeals(
            @Field("type") String type
    );




    @FormUrlEncoded
    @POST("getBoostedProductsByCity.php")
    Observable <List<Book>> getBoostedProductsByCity(
            @Field("city") String status
    );


    @FormUrlEncoded
    @POST("getProductsByCity.php")
    Observable <List<Book>> getLatestProductsByCity(
            @Field("city") String status
    );


    @POST("getAllProducts.php")
    Observable <List<Book>> getAllProducts(
    );







//    //@FormUrlEncoded
//    @GET("getAllServices.php")
//    Observable <List<AllServices>> getAllServices(
//
//    );

    @FormUrlEncoded
    @POST("insertnew_serviceRequest.php")
    Observable<String>insertServiceRequest(

            @Field("servicename") String servicename,
            @Field("service_link") String service_link,
            @Field("servicedetails") String servicedetails,
            @Field("service_date") String serviceDate,
            @Field("username") String username,
            @Field("userphone") String userphone,
            @Field("userlocation") String userlocation,
            @Field("userMsg") String userMsg


    );

    @FormUrlEncoded
    @POST("getProductsByCity.php")
    Observable<List<Book>>getproductsByCity(@Field("city") String city);


    @FormUrlEncoded
    @POST("getProductsByCategory.php")
    Observable<List<Book>>getproductsByCategorProd(@Field("category") String category);




    @FormUrlEncoded
    @POST("submitorder.php")
    Call<String>submitOrder(
            @Field("OrderDate") String OrderDate,
            @Field("price") float orderPrice,
            @Field("orderDetail") String orderDetail,
            @Field("comment") String comment,
            @Field("email") String softcopyEmail,
            @Field("address") String address,
            @Field("phone") String phone,
            @Field("paymentMethod") String paymentMethod,
            @Field("restaurantphone") String vendorphone,
            @Field("restaurantname") String vendorname


    );

    @FormUrlEncoded
    @POST("updateCustomerToken.php")
    Call<String> updateCustomerToken(
            @Field("phone") String phone,
            @Field("token") String token
    );




    @FormUrlEncoded
    @POST("getVendorFoodsByRestId.php") //getVendorFoodsByRestId.php
    Observable<List<Book>>getVendorFoodsByRestId(@Field("RestId") String RestId);

}
