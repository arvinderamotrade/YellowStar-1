package com.yellowstar.Url;

import com.yellowstar.model.District;
import com.yellowstar.model.Login;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface APIInterface
{
    @FormUrlEncoded
    @POST("/api.php")
    Call<Login> LoginUser(@Field("type") String type,@Field("uname") String uname,@Field("pass") String pass);

    @FormUrlEncoded
    @POST("/api.php")
    Call<District> GetDistrict(@Field("type") String type, @Field("uname") String uname, @Field("branch") String branch, @Field("SID") String SID, @Field("PID") String PID);

    @FormUrlEncoded
    @POST("/api.php")
    Call<Login> SendServer(@Field("type") String type, @Field("uname") String uname, @Field("SID") String SID, @Field("file") String file,
                           @Field("district_id") String district_id, @Field("block_id") String block_id, @Field("panchayat_id") String panchayat_id,
                           @Field("ward_id") String ward_id, @Field("pole_id") String pole_id, @Field("luminary_qr") String luminary_qr,
                           @Field("battery_qr") String battery_qr, @Field("panel_qr") String panel_qr);

}