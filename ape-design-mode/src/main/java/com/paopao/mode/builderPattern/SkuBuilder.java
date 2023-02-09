package com.paopao.mode.builderPattern;

/**
 * @Author paoPao
 * @Date 2023/2/9
 * @Description 构造者模式
 */
public class SkuBuilder<T extends SkuVO> {
    private SkuDO skuDO;

    private Boolean needCoupon;

    private SkuVOFunction<T> skuVOFunction;

    private SkuVOExtFunction<T> skuVOExtFunction;

    public static <T extends SkuVO> SkuBuilder<T> create() {
        return new SkuBuilder<T>();
    }

    public SkuBuilder<T> skuDo(SkuDO skuDO) {
        this.skuDO = skuDO;
        return this;
    }

    public SkuBuilder<T> skuDo(Boolean needCoupon) {
        this.needCoupon = needCoupon;
        return this;
    }

    public SkuBuilder<T> skuDo(SkuVOFunction<T> skuVOFunction) {
        this.skuVOFunction = skuVOFunction;
        return this;
    }

    public SkuBuilder<T> skuDo(SkuVOExtFunction<T> skuVOExtFunction) {
        this.skuVOExtFunction = skuVOExtFunction;
        return this;
    }

    public T build() {
        T skuVO = this.skuVOFunction.newInstance();
        skuVO.setSkuId(skuDO.getSkuId());
        skuVO.setSkuName(skuDO.getSkuName());
        if (needCoupon) {
            //转换优惠券
            skuVO.setCouponText("");
        }
        if (this.skuVOExtFunction != null) {
            this.skuVOExtFunction.buildExtInfo(skuVO, skuDO);
        }
        return skuVO;
    }
}
