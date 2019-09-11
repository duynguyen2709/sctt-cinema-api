package com.sctt.cinema.api.common.enums;

import java.util.HashMap;

public enum ProvinceEnum {

    DEFAULT(0, "NULL"),
    AN_GIANG(1, "An Giang"),
    BA_RIA_VUNG_TAU(2, "Bà Rịa - Vũng Tàu"),
    BAC_GIANG(3, "Bắc Giang"),
    BAC_KAN(4, "Bắc Kạn"),
    BAC_LIEU(5, "Bạc Liêu"),
    BAC_NINH(6, "Bắc Ninh"),
    BEN_TRE(7, "Bến Tre"),
    BINH_DINH(8, "Bình Định"),
    BINH_DUONG(9, "Bình Dương"),
    BINH_PHUOC(10, "Bình Phước"),
    BINH_THUAN(11, "Bình Thuận"),
    CA_MAU(12, "Cà Mau"),
    CAO_BANG(13, "Cao Bằng"),
    DAK_LAK(14, "Đắk Lắk"),
    DAK_NONG(15, "Đắk Nông"),
    DIEN_BIEN(16, "Điện Biên"),
    DONG_NAI(17, "Đồng Nai"),
    DONG_THAP(18, "Đồng Tháp"),
    GIA_LAI(19, "Gia Lai"),
    HA_GIANG(20, "Hà Giang"),
    HA_NAM(21, "Hà Nam"),
    HA_TINH(22, "Hà Tĩnh"),
    HAI_DUONG(23, "Hải Dương"),
    HAU_GIANG(24, "Hậu Giang"),
    HOA_BINH(25, "Hòa Bình"),
    HUNG_YEN(26, "Hưng Yên"),
    KHANH_HOA(27, "Khánh Hòa"),
    KIEN_GIANG(28, "Kiên Giang"),
    KON_TUM(29, "Kon Tum"),
    LAI_CHAU(30, "Lai Châu"),
    LAM_DONG(31, "Lâm Đồng"),
    LANG_SON(32, "Lạng Sơn"),
    LAO_CAI(33, "Lào Cai"),
    LONG_AN(34, "Long An"),
    NAM_DINH(35, "Nam Định"),
    NGHE_AN(36, "Nghệ An"),
    NINH_BINH(37, "Ninh Bình"),
    NINH_THUAN(38, "Ninh Thuận"),
    PHU_THO(39, "Phú Thọ"),
    QUANG_BINH(40, "Quảng Bình"),
    QUANG_NAME(41, "Quảng Nam"),
    QUANG_NGAI(42, "Quảng Ngãi"),
    QUANG_NINH(43, "Quảng Ninh"),
    QUANG_TRI(44, "Quảng Trị"),
    SOC_TRANG(45, "Sóc Trăng"),
    SON_LA(46, "Sơn La"),
    TAY_NINH(47, "Tây Ninh"),
    THAI_BINH(48, "Thái Bình"),
    THAI_NGUYEN(49, "Thái Nguyên"),
    THANH_HOA(50, "Thanh Hóa"),
    THUA_THIEN_HUE(51, "Thừa Thiên Huế"),
    TIEN_GIANG(52, "Tiền Giang"),
    TRA_VINH(53, "Trà Vinh"),
    TUYEN_QUANG(54, "Tuyên Quang"),
    VINH_LONG(55, "Vĩnh Long"),
    VINH_PHUC(56, "Vĩnh Phúc"),
    YEN_BAI(57, "Yên Bái"),
    PHU_YEN(58, "Phú Yên"),
    CAN_THO(59, "Cần Thơ"),
    DA_NANG(60, "Đà Nẵng"),
    HAI_PHONG(61, "Hải Phòng"),
    HA_NOI(62, "Hà Nội"),
    TP_HCM(63, "TP HCM");


    private final int code;
    private final String provinceName;

    private static final HashMap<Integer, ProvinceEnum> returnMap = new HashMap();

    ProvinceEnum(int value, String name) {
        this.code = value;
        this.provinceName = name;
    }

    public int getValue() {
        return this.code;
    }

    public static ProvinceEnum fromInt(int iValue) {
        return returnMap.get(iValue);
    }

    public String toString() {
        return this.provinceName;
    }

    static {
        ProvinceEnum[] var0 = values();

        int var1 = var0.length;

        for (int var2 = 0; var2 < var1; ++var2) {
            ProvinceEnum province = var0[var2];
            returnMap.put(province.code, province);
        }

    }
}
