package com.ruoyi.fangyuanapi.utils;


import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class demo {


    public static void main(String[] args) {

        String arr = "1\n" + "115,64\n" + "118,67\n" + "119,68\n" + "120,69\n" + "121,70\n" + "122,71\n" + "2\n" + "3\n" + "4\n" + "5\n" + "6\n" + "64\n" +
                "64,115\n" + "65\n" + "65,116\n" + "66\n" + "66,117\n" + "67\n" + "67,118\n" + "68\n" + "68,119\n" + "69\n" + "69,120\n" + "70\n" + "70,121\n" + "71\n" + "71,122\n" + "72";
        String[] split = arr.replace("\n", ",").split(",");
        java.util.stream.Stream<String> split1 = java.util.stream.Stream.of(split);
        List<String> strings = Arrays.asList(split);
//
//        返回一个无穷序列无序流，其中每个元素是由提供Supplier生成即参数中的方法生成。这是适用于产生恒定的流，随机元素的流等。
        Stream<Integer> stream3 = Stream.generate(new Random()::nextInt).limit(10);
        System.out.println(stream3.collect(Collectors.toList()));
        /* 返回由迭代产生的无限顺序的参数
         * param 流元素类型 +起始元素
         *  param   产生元素的方法  将起始元素传参进方法
         * */
        System.out.println(Stream.iterate(0, n -> n + 1).limit(10).collect(Collectors.toList()));


        /*
         * Optional<T> max(Comparator<? super T> comparator);
         * 传入参数是一个 比较器 comparator
         *返回值是comparator中的T类型
         * */
//        比较器
        Comparator<String> comparator = Comparator.comparing(Integer::valueOf);
//      最大值
        System.out.println(strings.stream().max(comparator).get());



        /*
         * Stream<T> distinct();
         * 无参
         *返回值是comparator中的T类型
         * */
//        去重
        StringBuilder stringBuilder = new StringBuilder();
        Arrays.stream(arr.replace("\n", ",").split(",")).distinct().forEach(ite -> {
            stringBuilder.append(ite + ",");
        });
        System.out.println(stringBuilder.toString());


        /*
         * Stream<T> filter(Predicate<? super T> predicate);
         * @Author: Zheng
         * @Description: 过滤
         * @param predicate:
         * @return: Stream<T>
         **/
        System.out.println(Arrays.stream(arr.replace("\n", ",").split(",")).filter(a -> !a.equals("71")).collect(Collectors.toList()));

        /*
          <R, A> R collect(Collector<? super T, A, R> collector);
         * @Description: collect()
         * @param Collector:收集器
         * @return: 收集器返回的类型
         **/
        List<String> collect = Arrays.stream(arr.replace("\n", ",").split(",")).collect(Collectors.toList());
//        分组
        System.out.println(Arrays.stream(arr.replace("\n", ",").split(",")).collect(Collectors.groupingBy(Object::toString)));


//      映射
        Stream.of("1", "2", "3", "4", "5").map(Integer::parseInt).forEach(ite-> System.out.println(ite));

//        limit() 分页
        System.out.println(Stream.of("1", "2", "3", "4", "5").limit(3).collect(Collectors.toList()));

//     forEach  逐一处理
        Stream.of("1", "2", "3", "4", "5").forEach(c-> System.out.println(c));

//     concat组合流
        Stream.concat(Stream.of("1", "2", "3", "4", "5"),Stream.of("11", "10", "9", "8", "7")).forEach(c-> System.out.println(c));

//        skip  跳过几个元素
        System.out.println(Stream.of("1", "2", "3", "4", "5").skip(1).collect(Collectors.toList()));
    }
}
