package com.paopao.redis.core;


import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.Tuple;
import redis.clients.jedis.params.SetParams;
import redis.clients.jedis.util.SafeEncoder;

import java.util.*;

/**
 * 基于jedis的redis操作工具类
 *
 * @author loser
 * @date 2023/06/20
 */
public final class RedisHandler {

    /*
    除了该工具类提供的方法外，还可以在外面调用getJedis()方法，获取到jedis实例后，调用它原生的api来操作
     */

    private final JedisPool jedisPool;

    static final Long OPERATE_SUCCESS = 1L;

    /**
     * 定义获取锁的lua脚本
     */
    static final String LOCK_LUA_SCRIPT = "if redis.call('setnx', KEYS[1], ARGV[1]) == 1 then return redis.call('expire', KEYS[1], ARGV[2]) else return 0 end";

    /**
     * 定义释放锁的lua脚本
     */
    static final String UNLOCK_LUA_SCRIPT = "if redis.call('get',KEYS[1]) == ARGV[1] then return redis.call('del',KEYS[1]) else return -1 end";

    /**
     * 获取jedis对象，并选择redis库。jedis默认是0号库，可传入1-16之间的数选择库存放数据
     * 原则上使用一个redis库存放数据，通过特定的key的命令规则来区分不同的数据就行了。
     *
     * @param index redis库号。使用可变参数的目的就是该参数可传可不传。
     * @return 返回jedis对象
     */
    public Jedis getJedis(int... index) {

        Jedis jedis = jedisPool.getResource();
        if (index != null && index.length > 0) {
            if (index[0] > 0 && index[0] <= 16) {
                jedis.select(index[0]);
            }
        }
        return jedis;

    }

    /*########################  key的操作  ################################*/


    /**
     * 删除一个或多个key
     *
     * @param key 一个或多个key
     */
    public Long del(String... key) {

        try (Jedis jedis = getJedis()) {
            return jedis.del(key);
        }

    }

    /**
     * 批量删除
     *
     * @param keyList 要删除的key的集合
     */
    public void mDel(List<String> keyList) {

        Jedis jedis = getJedis();
        //获取pipeline
        Pipeline pipeline = jedis.pipelined();
        for (String key : keyList) {
            pipeline.del(key);
        }
        //执行结果同步，这样才能保证结果的正确性。实际上不执行该方法也执行了上面的命令，但是结果确不一定完全正确。
        //注意
        pipeline.sync();
        //关闭连接
        jedis.close();

    }

    /**
     * 判断某个key是否还存在
     *
     * @param key key
     */
    public Boolean exists(String key) {

        try (Jedis jedis = getJedis()) {
            return jedis.exists(key);
        }

    }

    /**
     * 设置某个key的过期时间，单位秒
     *
     * @param key     key
     * @param seconds 过期时间秒
     */
    public void expire(String key, int seconds) {

        try (Jedis jedis = getJedis()) {
            jedis.expire(key, seconds);
        }

    }

    /**
     * 设置某个key的过期时间，单位秒
     *
     * @param key     key
     * @param seconds 过期时间秒
     */
    public void expire(String key, Long seconds) {

        try (Jedis jedis = getJedis()) {
            jedis.expire(key, seconds.intValue());
        }

    }

    /**
     * 设置某个key的过期时间，单位毫秒
     *
     * @param key     key
     * @param seconds 过期时间秒
     */
    public void expireAt(String key, long seconds) {

        try (Jedis jedis = getJedis()) {
            jedis.expireAt(key, seconds);
        }

    }

    /**
     * 查看某个key还有几秒过期，-1表示永不过期 ，-2表示已过期
     */
    public Long ttl(String key) {

        try (Jedis jedis = getJedis()) {
            return jedis.ttl(key);
        }

    }

    /**
     * 查看某个key对应的value的类型
     */
    public String type(String key) {

        try (Jedis jedis = getJedis()) {
            return jedis.type(key);
        }

    }

    /*########################  string(字符串)的操作  ####################*/

    /**
     * 获取某个key的value，类型要对，只能value是string的才能获取
     */
    public String get(String key) {

        try (Jedis jedis = getJedis()) {
            return jedis.get(key);
        }

    }

    /**
     * 设置某个key的value
     */
    public String set(String key, String value) {

        try (Jedis jedis = getJedis()) {
            return jedis.set(key, value);
        }

    }

    /**
     * 设置某个key的value
     */
    public String set(String key, String value, int expireSeconds) {

        try (Jedis jedis = getJedis()) {
            return jedis.setex(key, expireSeconds, value);
        }

    }

    /**
     * 设置某个key的value
     */
    public String set(String key, String value, Long expireSeconds) {

        try (Jedis jedis = getJedis()) {
            return jedis.setex(key, expireSeconds.intValue(), value);
        }

    }


//    public String set(String key, String value, String nxxx, String expx, long time) {
//
//        try (Jedis jedis = getJedis()) {
//            SetParams setParams = new SetParams();
//
//            setParams.getByteParams(SafeEncoder.encode(nxxx), SafeEncoder.encode(expx));
//            setParams.
//
//            if ()
////            return jedis.set(key, value, nxxx, expx, time);
//            return jedis.set(key, val).set(key, value, setParams, time);
//        }
//
//    }

    /**
     * 字符串后追加内容
     *
     * @param key           key
     * @param appendContent 要追加的内容
     */
    public Long append(String key, String appendContent) {

        try (Jedis jedis = getJedis()) {
            return jedis.append(key, appendContent);
        }

    }

    /**
     * 返回key的value的长度
     */
    public Long strLen(String key) {

        try (Jedis jedis = getJedis()) {
            return jedis.strlen(key);
        }

    }

    /**
     * value 加1 必
     * 须是字符型数字
     */
    public Long incr(String key) {

        try (Jedis jedis = getJedis()) {
            return jedis.incr(key);
        }

    }

    /**
     * value
     * 须是字符型数字
     */
    public Long incr(String key, int num) {

        try (Jedis jedis = getJedis()) {
            return jedis.incrBy(key, num);
        }

    }

    /**
     * value 减1   必须是字符型数字
     */
    public Long decr(String key) {

        try (Jedis jedis = getJedis()) {
            return jedis.decr(key);
        }

    }

    /**
     * value 加increment
     */
    public Long incrBy(String key, int increment) {

        try (Jedis jedis = getJedis()) {
            return jedis.incrBy(key, increment);
        }

    }

    public Double incrByFloat(String key, double increment) {

        try (Jedis jedis = getJedis()) {
            return jedis.incrByFloat(key, increment);

        }
    }

    /**
     * value 加increment
     */
    public Long incrBy(String key, Long increment) {

        try (Jedis jedis = getJedis()) {
            return jedis.incrBy(key, increment);
        }

    }

    /**
     * value 减increment
     */
    public Long decrBy(String key, int increment) {

        try (Jedis jedis = getJedis()) {
            return jedis.decrBy(key, increment);
        }

    }

    /**
     * 给某个key设置过期时间和value，成功返回OK
     *
     * @param key     key
     * @param seconds 过期时间秒
     * @param value   设置的值
     */
    public String setEx(String key, int seconds, String value) {

        try (Jedis jedis = getJedis()) {
            return jedis.setex(key, seconds, value);
        }

    }

    /*########################  list(列表)的操作  #######################*/

    /**
     * 从左边向列表中添加值
     */
    public void lPush(String key, String str) {

        try (Jedis jedis = getJedis()) {
            jedis.lpush(key, str);
        }

    }

    /**
     * 从左边向列表中添加值
     */
    public void lPushAll(String key, String... strings) {

        try (Jedis jedis = getJedis()) {
            jedis.lpush(key, strings);
        }

    }

    public void rPushAll(String key, String... strings) {

        try (Jedis jedis = getJedis()) {
            jedis.rpush(key, strings);
        }

    }

    /**
     * 从右边向列表中添加值
     */
    public void rPush(String key, String str) {

        try (Jedis jedis = getJedis()) {
            jedis.rpush(key, str);
        }

    }

    /**
     * 从左边取出一个列表中的值
     */
    public String lPop(String key) {

        try (Jedis jedis = getJedis()) {
            return jedis.lpop(key);
        }

    }

    /**
     * 从右边取出一个列表中的值
     */
    public String rPop(String key) {

        try (Jedis jedis = getJedis()) {
            return jedis.rpop(key);
        }

    }

    /**
     * 取出列表中指定范围内的值，0 到 -1 表示全部
     */
    public List<String> lRange(String key, int startIndex, int endIndex) {

        try (Jedis jedis = getJedis()) {
            return jedis.lrange(key, startIndex, endIndex);
        }

    }

    /**
     * 返回某列表指定索引位置的值
     */
    public String lIndex(String key, int index) {

        try (Jedis jedis = getJedis()) {
            return jedis.lindex(key, index);
        }

    }

    /**
     * 返回某列表的长度
     */
    public Long lLen(String key) {

        try (Jedis jedis = getJedis()) {
            return jedis.llen(key);
        }

    }

    /**
     * 给某列表指定位置设置为指定的值
     */
    public String lSet(String key, Long index, String str) {

        try (Jedis jedis = getJedis()) {
            return jedis.lset(key, index, str);
        }

    }

    /**
     * 对列表进行剪裁，保留指定闭区间的元素(索引位置也会重排)
     */
    public void ltrim(String key, Integer startIndex, Integer endIndex) {

        try (Jedis jedis = getJedis()) {
            jedis.ltrim(key, startIndex, endIndex);
        }

    }

    /**
     * 从列表的左边阻塞弹出一个元素
     */
    public List<String> blpop(String key, Integer timeout) {

        try (Jedis jedis = getJedis()) {
            return jedis.blpop(timeout, key);
        }

    }

    /**
     * 从列表的右边阻塞弹出一个元素
     */
    public List<String> brpop(String key, Integer timeout) {

        try (Jedis jedis = getJedis()) {
            return jedis.brpop(timeout, key);
        }

    }

    /*########################  hash(哈希表)的操作  #######################*/
    //hset hget hmset hmget hgetall hdel hkeys hvals hexists hincrby

    /**
     * 给某个hash表设置一个键值对
     */
    public void hset(String key, String field, String value) {

        try (Jedis jedis = getJedis()) {
            jedis.hset(key, field, value);
        }

    }

    /**
     * 取出某个hash表中某个field对应的value
     */
    public String hget(String key, String field) {

        try (Jedis jedis = getJedis()) {
            return jedis.hget(key, field);
        }

    }

    /**
     * 某个hash表设置一个或多个键值对
     */
    public void hmset(String key, Map<String, String> kvMap) {

        try (Jedis jedis = getJedis()) {
            jedis.hmset(key, kvMap);
        }

    }

    /**
     * 取出某个hash表中任意多个key对应的value的集合
     */
    public List<String> hmget(String key, String... fields) {

        try (Jedis jedis = getJedis()) {
            return jedis.hmget(key, fields);
        }

    }

    /**
     * 取出某个hash表中所有的键值对
     */
    public Map<String, String> hgetAll(String key) {

        try (Jedis jedis = getJedis()) {
            return jedis.hgetAll(key);
        }

    }

    /**
     * 判断某个hash表中的某个key是否存在
     */
    public Boolean hexists(String key, String field) {

        try (Jedis jedis = getJedis()) {
            return jedis.hexists(key, field);
        }

    }

    /**
     * 返回某个hash表中所有的key
     */
    public Set<String> hkeys(String key) {

        try (Jedis jedis = getJedis()) {
            return jedis.hkeys(key);
        }

    }

    /**
     * 返回某个hash表中所有的value
     */
    public List<String> hvals(String key) {

        try (Jedis jedis = getJedis()) {
            return jedis.hvals(key);
        }

    }

    /**
     * 删除某个hash表中的一个或多个键值对
     */
    public Long hdel(String key, String... fields) {

        try (Jedis jedis = getJedis()) {
            return jedis.hdel(key, fields);
        }

    }

    /**
     * 给某个hash表中的某个field的value增加多少
     */
    public Long hincrBy(String key, String field, Long increment) {

        try (Jedis jedis = getJedis()) {
            return jedis.hincrBy(key, field, increment);
        }

    }

    /**
     * 给某个hash表中的某个field的value增加多少
     */
    public Long hincrBy(String key, String field, Integer increment) {

        try (Jedis jedis = getJedis()) {
            return jedis.hincrBy(key, field, increment);
        }

    }

    /**
     * 给某个hash表中的某个field的value增加多少
     */
    public Long hdecrBy(String key, String field, Integer increment) {

        try (Jedis jedis = getJedis()) {
            return jedis.hincrBy(key, field, -increment);
        }

    }

    public Double hincrByFloat(String key, String field, Double increment) {

        try (Jedis jedis = getJedis()) {
            return jedis.hincrByFloat(key, field, increment);
        }

    }

    /*########################  set(集合)的操作  ###########################*/

    /**
     * 往set集合中添加一个或多个元素
     */
    public Long sadd(String key, String... members) {

        try (Jedis jedis = getJedis()) {
            return jedis.sadd(key, members);
        }

    }

    /**
     * 返回set集合中的所有元素，顺序与加入时的顺序一致
     */
    public Set<String> smembers(String key) {

        try (Jedis jedis = getJedis()) {
            return jedis.smembers(key);
        }

    }

    /**
     * 判断集合中是否存在某个元素
     */
    public Boolean sismember(String key, String member) {

        try (Jedis jedis = getJedis()) {
            return jedis.sismember(key, member);
        }

    }

    /**
     * 返回set集合的长度
     */
    public Long scard(String key) {

        try (Jedis jedis = getJedis()) {
            return jedis.scard(key);
        }

    }

    /**
     * 删除set集合中指定的一个或多个元素
     */
    public Long srem(String key, String... members) {

        try (Jedis jedis = getJedis()) {
            return jedis.srem(key, members);
        }

    }

    /**
     * 将key1中的元素key1Member移动到key2中
     */
    public Long smove(String key1, String key2, String key1Member) {

        try (Jedis jedis = getJedis()) {
            return jedis.smove(key1, key2, key1Member);
        }

    }

    /**
     * 随机查询返回集合中的指定个数的元素（若count为负数，返回的元素可能会重复）
     */
    public List<String> srandmember(String key, int count) {

        try (Jedis jedis = getJedis()) {
            return jedis.srandmember(key, count);
        }

    }

    /**
     * 从set集合中随机弹出指定个数个元素
     *
     * @param key   key
     * @param count 要弹出的个数
     * @return 随机弹出的元素
     */
    public Set<String> spop(String key, int count) {

        try (Jedis jedis = getJedis()) {
            return jedis.spop(key, count);
        }

    }

    /**
     * 求交集，返回多个set集合相交的部分
     */
    public Set<String> sinter(String... setKeys) {

        try (Jedis jedis = getJedis()) {
            return jedis.sinter(setKeys);
        }

    }

    /**
     * 求并集，求几个set集合的并集（因为set中不会有重复的元素，合并后的集合也不会有重复的元素）
     */
    public Set<String> sunion(String... setKeys) {

        try (Jedis jedis = getJedis()) {
            return jedis.sunion(setKeys);
        }

    }

    /**
     * 求差集，求几个集合之间的差集
     */
    public Set<String> sdiff(String... setKeys) {

        try (Jedis jedis = getJedis()) {
            return jedis.sdiff(setKeys);
        }

    }


    /*########################  zset(有序集合)的操作  #######################*/

    /**
     * 添加一个元素到zset
     */
    public Long zadd(String key, double score, String member) {

        try (Jedis jedis = getJedis()) {
            return jedis.zadd(key, score, member);
        }

    }

    /**
     * 添加一个或多个元素到zset
     */
    public Long zadd(String key, Map<String, Double> memberScores) {

        try (Jedis jedis = getJedis()) {
            return jedis.zadd(key, memberScores);
        }

    }

    /**
     * 查询指定闭区间的元素（根据分数升序）
     * （0,-1表示全部）
     */
    public Set<String> zrange(String key, long start, long end) {

        try (Jedis jedis = getJedis()) {
            return jedis.zrange(key, start, end);
        }

    }

    /**
     * 查询指定闭区间的元素，带着分数
     * （0,-1表示全部）
     */
    public Set<Tuple> zrangeWithScores(String key, long start, long end) {

        try (Jedis jedis = getJedis()) {
            return jedis.zrangeWithScores(key, start, end);
        }

    }

    /**
     * 查询指定索引闭区间的元素（根据分数降序）
     * （0,-1表示全部）
     */
    public Set<String> zrevrange(String key, long start, long end) {

        try (Jedis jedis = getJedis()) {
            return jedis.zrevrange(key, start, end);
        }

    }

    /**
     * 查询指定索引闭区间的元素，带着分数（根据分数降序）
     * （0,-1表示全部）
     */
    public Set<Tuple> zrevrangeWithScores(String key, long start, long end) {

        try (Jedis jedis = getJedis()) {
            return jedis.zrevrangeWithScores(key, start, end);
        }

    }

    /**
     * 返回有序集合(zset)中的元素个数
     */
    public Long zcard(String key) {

        try (Jedis jedis = getJedis()) {
            return jedis.zcard(key);
        }

    }

    /**
     * 返回指定分数区间的元素个数（闭区间）
     */
    public Long zcount(String key, Long startScore, Long endScore) {

        try (Jedis jedis = getJedis()) {
            return jedis.zcount(key, startScore, endScore);
        }

    }

    /**
     * 返回某元素在集合中的排名（根据分数降序排列时）
     */
    public Long zrevrank(String key, String member) {

        try (Jedis jedis = getJedis()) {
            return jedis.zrevrank(key, member);
        }

    }

    /**
     * 返回某元素在集合中的排名（根据分数升序排列时）
     */
    public Long zrank(String key, String member) {

        try (Jedis jedis = getJedis()) {
            return jedis.zrank(key, member);
        }

    }

    /**
     * 升序查询指定分数闭区间的元素
     */
    public Set<String> zrangeByScore(String key, double min, double max) {

        try (Jedis jedis = getJedis()) {
            return jedis.zrangeByScore(key, min, max);
        }

    }

    /**
     * 升序查询指定分数闭区间的元素，并指定偏移量
     */
    public Set<String> zrangeByScore(String key, double min, double max, int offset, int size) {

        try (Jedis jedis = getJedis()) {
            return jedis.zrangeByScore(key, min, max, offset, size);
        }

    }

    /**
     * 升序查询指定分数闭区间的元素，带着分数
     */
    public Set<Tuple> zrangeByScoreWithScores(String key, double min, double max) {

        try (Jedis jedis = getJedis()) {
            return jedis.zrangeByScoreWithScores(key, min, max);
        }

    }

    /**
     * 升序查询指定分数闭区间的元素，并指定偏移量，带着分数
     */
    public Set<Tuple> zrangeByScoreWithScores(String key, double min, double max, int offset, int size) {

        try (Jedis jedis = getJedis()) {
            return jedis.zrangeByScoreWithScores(key, min, max, offset, size);
        }

    }

    /**
     * 降序查询指定分数闭区间的元素
     */
    public Set<String> zrevrangebyscore(String key, double max, double min) {

        try (Jedis jedis = getJedis()) {
            return jedis.zrevrangeByScore(key, max, min);
        }

    }

    /**
     * 降序查询指定分数闭区间的元素，并指定偏移量
     */
    public Set<String> zrevrangebyscore(String key, double max, double min, int offset, int size) {

        try (Jedis jedis = getJedis()) {
            return jedis.zrevrangeByScore(key, max, min, offset, size);
        }

    }

    /**
     * 降序查询指定分数闭区间的元素，并带着分数
     */
    public Set<Tuple> zrevrangeByScoreWithScores(String key, double max, double min) {

        try (Jedis jedis = getJedis()) {
            return jedis.zrevrangeByScoreWithScores(key, max, min);
        }

    }

    /**
     * 降序查询指定分数闭区间的元素，并指定偏移量，带着分数
     */
    public Set<Tuple> zrevrangeByScoreWithScores(String key, double max, double min, int offset, int size) {

        try (Jedis jedis = getJedis()) {
            return jedis.zrevrangeByScoreWithScores(key, min, max, offset, size);
        }

    }

    /**
     * 有序集合中指定删除一或多个元素
     */
    public Long zrem(String key, String... member) {

        try (Jedis jedis = getJedis()) {
            return jedis.zrem(key, member);
        }

    }

    /**
     * 分数升序排名时，删除指定索引区间的元素
     */
    public Long zremrangebyrank(String key, long start, long end) {

        try (Jedis jedis = getJedis()) {
            return jedis.zremrangeByRank(key, start, end);
        }

    }

    /**
     * 分数升序排名时，删除指定分数区间的元素
     */
    public Long zremrangeByScore(String key, long min, long max) {

        try (Jedis jedis = getJedis()) {
            return jedis.zremrangeByScore(key, min, max);
        }

    }

    /**
     * 查询有序集合中某元素的分数
     */
    public Double zscore(String key, String member) {

        try (Jedis jedis = getJedis()) {
            return jedis.zscore(key, member);
        }

    }

    /**
     * 给有序集合中某元素的分数增加(正数)或减少(负数)
     */
    public Double zincrby(String key, double score, String member) {

        try (Jedis jedis = getJedis()) {
            return jedis.zincrby(key, score, member);
        }

    }

    /*########################  lock 相关  #######################*/

    /**
     * 获取一把锁
     */
    public boolean lock(String key, String lockValue, int expire) {

        if (key == null || lockValue == null) {
            return false;
        }
        try (Jedis jedis = getJedis()) {
            List<String> args = new ArrayList<>();
            args.add(lockValue);
            args.add(String.valueOf(expire));
            Object res = jedis.eval(LOCK_LUA_SCRIPT, Collections.singletonList(key), args);
            return res != null && res.equals(OPERATE_SUCCESS);
        } catch (Exception e) {
            return false;
        }

    }

    /**
     * 获取一把锁
     */
    public boolean lock(String key, String lockValue, long expire) {

        if (key == null || lockValue == null) {
            return false;
        }
        try (Jedis jedis = getJedis()) {
            List<String> args = new ArrayList<>();
            args.add(lockValue);
            args.add(String.valueOf(expire));
            Object res = jedis.eval(LOCK_LUA_SCRIPT, Collections.singletonList(key), args);
            return res != null && res.equals(OPERATE_SUCCESS);
        } catch (Exception e) {
            return false;
        }

    }

    public boolean releaseLock(String key, String lockValue) {

        if (key == null || lockValue == null) {
            return false;
        }
        try (Jedis jedis = getJedis()) {
            Object res = jedis.eval(UNLOCK_LUA_SCRIPT, Collections.singletonList(key), Collections.singletonList(lockValue));
            return res != null && res.equals(OPERATE_SUCCESS);
        } catch (Exception e) {
            return false;
        }

    }

    /*########################  管道 相关  #######################*/


    public <T> T executePipeline(PipelineExecutor<T> executor) {

        Jedis jedis = null;
        T result;
        try {
            jedis = getJedis();
            Pipeline pipeline = jedis.pipelined();
            result = executor.execute(pipeline);
            pipeline.sync();
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return result;

    }


    /**
     * 私有化构造器，不让实例化对象
     */
    public RedisHandler(final GenericObjectPoolConfig poolConfig, final String host, int port, int timeout, final String password, int database) {
        this.jedisPool = new JedisPool(poolConfig, host, port, timeout, password, database);

    }

    /**
     * 执行jedis 封装的命令
     * <p>
     * String result = RedisHandler.execute(jedis -> jedis.get("key"));
     */
    public <T> T execute(JedisExecutor<T> executor) {

        try (Jedis jedis = getJedis()) {
            return executor.execute(jedis);
        }
    }

}
