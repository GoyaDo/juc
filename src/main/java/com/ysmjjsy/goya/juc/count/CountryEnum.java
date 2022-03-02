package com.ysmjjsy.goya.juc.count;

/**
 * @author goya
 * @create 2022-01-08 14:06
 */
public enum CountryEnum {
    ONE(1, "齐"), TWO(2, "楚"), THREE(3, "燕"), FOUR(4, "赵"), FIVE(5, "魏"), SIX(6, "韩");

    private Integer reCode;
    private String retMessage;

    CountryEnum(Integer reCode, String retMessage) {
        this.reCode = reCode;
        this.retMessage = retMessage;
    }

    public static CountryEnum forEach_CountryEnum(int index) {
        CountryEnum[] countryEnums = CountryEnum.values();
        for (CountryEnum element :
                countryEnums) {
            if (index == element.getReCode()) return element;
        }
        return null;
    }

    public Integer getReCode() {
        return reCode;
    }

    public String getRetMessage() {
        return retMessage;
    }
}
