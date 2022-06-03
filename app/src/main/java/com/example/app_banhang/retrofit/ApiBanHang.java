package com.example.app_banhang.retrofit;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

import com.example.app_banhang.model.DonHangModel;
import com.example.app_banhang.model.LoaiSpModel;
import com.example.app_banhang.model.SanPhamMoiModel;
import com.example.app_banhang.model.UserModel;

public interface ApiBanHang {
    // Lấy dữ liệu trên SQL về
    @GET("getloaisp.php")
    Observable<LoaiSpModel> getLoaiSp();

    @GET("getspmoi.php")
    Observable<SanPhamMoiModel> getSpMoi();
    //Đưa dữ liệu từ app lên SQL
    @POST("chitiet.php")
    @FormUrlEncoded
    Observable<SanPhamMoiModel> getSanPham(
            @Field("page") int page,
            @Field("loai") int loai
    );
    @POST("dangky.php")
    @FormUrlEncoded
    Observable<UserModel> dangky(
            @Field("tendangnhap") String tendangnhap,
            @Field("password") String password,
            @Field("username") String username,
            @Field("sdt") String sdt
    );
    @POST("dangnhap.php")
    @FormUrlEncoded
    Observable<UserModel> dangNhap(
            @Field("tendangnhap") String tendangnhap,
            @Field("password") String password
    );
    @POST("resetpass.php")
    @FormUrlEncoded
    Observable<UserModel> resetpass(
            @Field("tendangnhap") String tendangnhap
    );
    @POST("donhang.php")
    @FormUrlEncoded
    Observable<UserModel> createOder(
            @Field("email") String email,
            @Field("sdt") String sdt,
            @Field("tongtien") String tongtien,
            @Field("iduser") int id,
            @Field("diachi") String diachi,
            @Field("soluong") int soluong,
            @Field("chitiet") String chitiet
    );
    @POST("xemdonhang.php")
    @FormUrlEncoded
    Observable<DonHangModel> xemDonHang(
            @Field("iduser") int id
    );
}
