package com.paopao.utils;

import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * @Author: paoPao
 * @Date: 2023/2/11
 * @Description:
 * 通用工具类：tree转list，list转tree，该工具类中的方法都是原数据集上的浅拷贝  <p>
 * 因为系统里关于tree转list，list转tree的代码都不通用且效率不高，所以就自己写了这个工具类。  <p>
 * 设计思路主要是借助 泛型+函数式编程+方法切面来实现  <p>
 * 除了支持常见的 id 和 pid 的list结构，使用其他类型属性做 id 和 pid 也是可以，id 和 pid只是一个父子关系的代号  <p>
 * 平时业务开发天天加班，虽然业务中的基础场景都基本没问题，但因为写和测的时间很少，所以复杂场景还没怎么实验，有兴趣的同事欢迎找我讨论  <p>
 * 多棵树组成的成为森林forest，树中结点统称为node，树的根结点统称root，叶子结点统称为leaf   <p>
 *
 */
public class TreeUtils {
    /**
     * nodeList中父属性值等于pid的node作为root，自顶向下递归构建tree  <p>
     *
     * @param nodeList   源数据
     * @param pid        根结点属性值（一般是父id）
     * @param idFn       获取id的方法
     * @param pidFn      获取父id的方法
     * @param setChildFn 设置子节点的方法
     * @return {@link List <T>}
     */
    public static <T, U> List<T> transferToTree(List<T> nodeList, U pid, Function<T, U> idFn, Function<T, U> pidFn, BiConsumer<T, List<T>> setChildFn) {
        return baseTransferToTree(nodeList, idFn, pidFn, setChildFn, getRootPredicate(pid, pidFn));
    }

    /**
     * 以父属性值满足rootPredicate的结点作root，自顶向下递归构建tree  <p>
     * rootPredicate例子：  <p>
     * node -> node.getParentField().equals(?) 以父属性值为?的结点作root，自顶向下递归构建tree  <p>
     *
     * @param nodeList      源数据
     * @param idFn          获取id的方法
     * @param pidFn         获取父id的方法
     * @param setChildFn    设置子节点的方法
     * @param rootPredicate 获取根节点的方法
     * @return {@link List <T>}
     */
    public static <T, U> List<T> transferToTree(List<T> nodeList, Function<T, U> idFn, Function<T, U> pidFn, BiConsumer<T, List<T>> setChildFn, Predicate<T> rootPredicate) {
        return baseTransferToTree(nodeList, idFn, pidFn, setChildFn, rootPredicate);
    }

    /**
     * 思路：将结点按照父id聚合成map，然后递归设置每个根结点的子结点  <p>
     * 聚合成map是空间换时间，其实也可以用stream的 groupBy，因为简单，所以自己手撕了  <p>
     *
     * @param nodeList      源数据
     * @param idFn          获取id的方法
     * @param pidFn         获取父id的方法
     * @param setChildFn    设置子节点的方法
     * @param rootPredicate 获取根节点的方法
     * @return {@link List <T>}
     */
    private static <T, U> List<T> baseTransferToTree(List<T> nodeList, Function<T, U> idFn, Function<T, U> pidFn, BiConsumer<T, List<T>> setChildFn, Predicate<T> rootPredicate) {
        List<T> rootList = new ArrayList<>();
        Map<U, List<T>> groupByPidMap = new HashMap<>(nodeList.size());
        nodeList.forEach(node -> groupByPid(node, rootList, groupByPidMap, pidFn, rootPredicate));
        rootList.forEach(node -> recurseBuildTree(node, groupByPidMap, idFn, setChildFn, 0));
        return rootList;
    }

    /**
     * 根据父id来处理数据，根结点加入到根结点数组中，非根结点聚集成 Map<idKey,childList>  <p>
     *
     * @param node          当前的树结点
     * @param rootList      根结点集合
     * @param groupByPidMap 非根结点Map
     * @param pidFn         获取父id的方法
     * @param rootPredicate 获取根节点的方法
     */
    private static <T, U> void groupByPid(T node, List<T> rootList, Map<U, List<T>> groupByPidMap, Function<T, U> pidFn, Predicate<T> rootPredicate) {
        if (rootPredicate.test(node)) {
            rootList.add(node);
        } else {
            groupByPidWithOutRoot(node, groupByPidMap, pidFn);
        }
    }

    /**
     * 聚合非根结点的方法，将非根结点聚集成Map  <p>
     *
     * @param node          当前的树结点
     * @param groupByPidMap 非根结点Map
     * @param pidFn         获取父id的方法
     */
    private static <T, U> void groupByPidWithOutRoot(T node, Map<U, List<T>> groupByPidMap, Function<T, U> pidFn) {
        List<T> tempList = groupByPidMap.getOrDefault(pidFn.apply(node), new ArrayList<>());
        tempList.add(node);
        groupByPidMap.put(pidFn.apply(node), tempList);
    }

    /**
     * 递归构建树结点  <p>
     * 此处level并未使用，只是留作拓展  <p>
     *
     * @param node          当前结点
     * @param groupByPidMap 非根结点Map
     * @param idFn          获取id的方法
     * @param setChildFn    设置子节点的方法
     */
    private static <T, U> void recurseBuildTree(T node, Map<U, List<T>> groupByPidMap, Function<T, U> idFn, BiConsumer<T, List<T>> setChildFn, int level) {
        List<T> childNodeList = groupByPidMap.get(idFn.apply(node));
        setChildFn.accept(node, childNodeList);
        if (!CollectionUtils.isEmpty(childNodeList)) {
            childNodeList.forEach(childNode -> recurseBuildTree(childNode, groupByPidMap, idFn, setChildFn, level + 1));
        }
    }

    /**
     * 获取判断root结点的rootPredicate  <p>
     * 基于科里化思想，转换Function为Predicate  <p>
     *
     * @param pid   根结点属性值
     * @param pidFn 获取父id的方法
     * @return {@link List <T>}
     */
    private static <T, U> Predicate<T> getRootPredicate(U pid, Function<T, U> pidFn) {
        return param -> pidFn.apply(param).equals(pid);
    }

    //---------------------------------- 上面是list转tree，下面是tree转list ----------------------------------

    /**
     * 遍历森林，将森林中符合条件的结点存入结果集并返回（只是浅拷贝，不会将childList置为null）  <p>
     * addResultPredicate例子：  <p>
     * &nbsp;  Objects::nonNull 树中不为空结点的都会存入结果集  <p>
     * &nbsp;  l -> CollectionUtils.isEmpty(l.getChild()) 将所有的叶子结点存入数据集  <p>
     * &nbsp;  l -> l.getPid().equals(0L)  将所有的根结点存入结果集  <p>
     * <p>
     * 思路：类似深度优先遍历，对每一个结点应用addResultPredicate函数，符合条件的就加入到结果集中，不做后置处理  <p>
     *
     * @param nodeList           源数据
     * @param getChildFn         获取子节点的方法
     * @param addResultPredicate 判断是否加入结果集的方法
     * @return {@link List <T>}
     */
    public static <T> List<T> traversalTree(List<T> nodeList, Function<T, List<T>> getChildFn, Predicate<T> addResultPredicate) {
        List<T> result = new ArrayList<>();
        recurseTraversalTree(nodeList, getChildFn, getPreProcessor(result, addResultPredicate), null);
        return result;
    }

    /**
     * 将forest转换成list（虽只是浅拷贝，但会将childList置为null，会影响原数据集）  <p>
     * addResultPredicate例子：  <p>
     * &nbsp;  Objects::nonNull 树中不为空结点的都会存入结果集  <p>
     * 思路：类似深度优先遍历，对每一个结点应用addResultPredicate函数，符合条件的就加入到结果集中，在后置处理中将childList设置为空  <p>
     *
     * @param nodeList           源数据
     * @param getChildFn         获取子节点的方法
     * @param setChildFn         设置子节点的方法
     * @param addResultPredicate 判断是否加入结果集的方法
     * @return {@link List <T>}
     */
    public static <T> List<T> transferToList(List<T> nodeList, Function<T, List<T>> getChildFn, BiConsumer<T, List<T>> setChildFn, Predicate<T> addResultPredicate) {
        List<T> result = new ArrayList<>();
        recurseTraversalTree(nodeList, getChildFn, getPreProcessor(result, addResultPredicate), getPostProcessor(setChildFn));
        return result;
    }

    /**
     * 深度优先递归遍历每个结点，并将符合条件的结点加入结果集  <p>
     *
     * @param nodeList      源数据
     * @param getChildFn    获取子节点的方法
     * @param preProcessor  前置处理器
     * @param postProcessor 后置处理器
     */
    private static <T> void recurseTraversalTree(List<T> nodeList, Function<T, List<T>> getChildFn, Consumer<T> preProcessor, Consumer<T> postProcessor) {
        if (CollectionUtils.isEmpty(nodeList)) {
            return;
        }
        nodeList.forEach(node -> {
            Optional.ofNullable(preProcessor).ifPresent(l -> l.accept(node));
            recurseTraversalTree(getChildFn.apply(node), getChildFn, preProcessor, postProcessor);
            Optional.ofNullable(postProcessor).ifPresent(l -> l.accept(node));
        });
    }

    /**
     * 获取前置处理器  <p>
     */
    private static <T> Consumer<T> getPreProcessor(List<T> result, Predicate<T> addResultPredicate) {
        return node -> {
            if (addResultPredicate.test(node)) {
                result.add(node);
            }
        };
    }

    /**
     * 获取后置处理器  <p>
     */
    private static <T> Consumer<T> getPostProcessor(BiConsumer<T, List<T>> setChildFn) {
        return node -> setChildFn.accept(node, null);
    }
}
