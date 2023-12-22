package com.paopao.demo;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.StopWatch;

import java.util.Arrays;

/**
 * 模块描述
 *
 * @Author paoPao
 * @Date 2023/12/12
 */
@SpringBootTest
public class Test01 {

    @Test
    public void test01() {
        Cache<Object, Object> cache = Caffeine.newBuilder().initialCapacity(100).build();

        Object val1 = cache.get("key", k -> true);
        System.out.println(val1);
        Object key = cache.getIfPresent("key");
        System.out.println(key);
        Object val2 = cache.get("key", k -> "111");
        System.out.println(val2);
    }

    @Test
    public void maopao() throws InterruptedException {
//        int[] nums = new int[]{0, 6, 3, 1, 5, 4, 8, 10, 7, 2, 9, 0};
        int[] nums = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11};
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        boolean flag = true;
        for (int i = 0; i < nums.length && flag; i++) {
            flag = false;
            for (int j = 0; j < nums.length - i - 1; j++) {
                int a1 = nums[j];
                int a2 = nums[j + 1];
                if (a1 > a2) {
                    flag = true;
                    nums[j] = a2;
                    nums[j + 1] = a1;
                }
            }
        }
//        Thread.sleep(1000);
        stopWatch.stop();
        System.out.println(Arrays.toString(nums));
        System.out.println(stopWatch.getLastTaskTimeNanos());
    }

    @Test
    public void quickSort() {
        int[] nums = new int[]{0, 6, 3, 1, 5, 4, 8, 10, 7, 2, 9, 0};
        getMidNum(nums, 0, nums.length - 1);
        System.out.println(Arrays.toString(nums));
    }

    private void getMidNum(int[] nums, int left, int right) {
        if (left >= right) return;
        int pre = nums[left];
        int i = left + 1;
        int j = right;
        while (true) {
            while (i <= j && nums[i] <= pre) i++;
            while (i <= j && nums[j] >= pre) j--;
            if (i >= j) break;
            int tmp = nums[i];
            nums[i] = nums[j];
            nums[j] = tmp;
        }
        nums[left] = nums[j];
        nums[j] = pre;
        getMidNum(nums, left, j - 1);
        getMidNum(nums, j + 1, right);
    }

    @Test
    public void test02() {
        int[] nums1 = new int[]{1, 2, 3, 0, 0, 0};
        int m = 3;
        int[] nums2 = new int[]{2, 5, 6};
        int n = 3;
        this.merge(nums1, m, nums2, n);
        System.out.println(Arrays.toString(nums1));
    }

    public void merge(int[] nums1, int m, int[] nums2, int n) {
        int len1 = nums1.length;
        int len2 = nums2.length;
        int j = n - 1;
        for (int i = 0; i < n && j > 0; i++) {
            int a1 = nums1[m - 1 - i];
            int a2 = nums2[j];
            if (a1 > a2) {
                nums1[len1 - 1 - i] = a1;
            } else {
                nums1[len1 - 1 - i] = a2;
                i--;
                j--;
            }
        }
        for (int i = 0; i <= j; j++) {
            nums1[i] = nums2[i];
        }
    }
}
