# @Cache缓存



####   @Cacheable

###### @Cacheabel的作用和配置的方法

| 属性       |                             解析                             | example                                                      |
| :--------- | :----------------------------------------------------------: | ------------------------------------------------------------ |
| value      |     缓存的名字,在spring的配置文件中配置,必须指定至少一个     | @Cacheable(value="mycache"),@Cacheable(value={"mytest1","mytest2"}) |
| key        | 缓存的key,可以为空,如果指定要按照spel的表达式,如果不指定的话,则缺省按照方法的所有的参数进行组合 | @Cache(value="mycache1",key="#userName")                     |
| condititon | 缓存的条件,可以为空,使用spel编写,返回true或者为false,只有为true的时候才进行缓存 | @Cacheable(value=”testcache”,condition=”#userName.length()>2”) |
|            |                                                              |                                                              |
|            |                                                              |                                                              |

```markdown
# 实例:

* @Cacheable(value="accountCache"),这个注解的意思是,当调用这个方法的时候,会从一个名叫mycache的缓存中去查询,如果没有的话,则执行实际的方法(就是查询数据库),并将执行的结果存入缓存中 如果没有指定key的话就是使用的缺省全部的参数


```

```java

@Cacheable(value="accountCache")// 使用了一个缓存名叫 accountCache
public Account getAccountByName(String userName) {
   // 方法内部实现不考虑缓存逻辑，直接实现业务
   System.out.println("real query account."+userName);
   return getFromDB(userName);

```



##### @CachePut

```markdown
# @CachePut的作用是针对方法配置,能够根据方法的请求参数对其结果进行缓存,和@Cacheable不一样的是,它每次都会触发真实方法的调用
```

配置

| 参数      |                       解释                       | example                    |
| --------- | :----------------------------------------------: | -------------------------- |
| value     | 缓存的名字,在spring的配置文件中,必须指定至少一个 | @CachePut(value="mycache") |
| key       |                       同上                       | 同上                       |
| condition |                       同上                       | 同上                       |

实例:这个方法能保证方法被执行,并且存储到缓存中,实现缓存与数据库的数据同步更新

```java

@CachePut(value="accountCache",key="#account.getName()")// 更新accountCache 缓存
public Account updateAccount(Account account) {
  return updateDB(account);
}
```

##### @CacheEvict

```markdown
# 这个注解的作用是针对方法配置,能够根据一定的条件对缓存进行清空

```

配置

| 参数             | 解释                                                         |                           example                            |
| :--------------- | :----------------------------------------------------------- | :----------------------------------------------------------: |
| value            | 缓存的名称，在 spring 配置文件中定义，必须指定至少一个       |                @CacheEvict(value=”my cache”)                 |
| key              | 缓存的 key，可以为空，如果指定要按照 SpEL 表达式编写，如果不指定，则缺省按照方法的所有参数进行组合 |        @CacheEvict(value=”testcache”,key=”#userName”)        |
| condition        | 缓存的条件，可以为空，使用 SpEL 编写，返回 true 或者 false，只有为 true 才进行缓存 | @CacheEvict(value=”testcache”,condition=”#userName.length()>2”) |
| allEntries       | 是否清空所有缓存内容，缺省为 false，如果指定为 true，则方法调用后将立即清空所有缓存 |        @CachEvict(value=”testcache”,allEntries=true)         |
| beforeInvocation | 是否在方法执行前就清空，缺省为 false，如果指定为 true，则在方法还没有执行的时候就清空缓存，缺省情况下，如果方法执行抛出异常，则不会清空缓存 |     @CachEvict(value=”testcache”，beforeInvocation=true)     |

```java
@CacheEvict(value="accountCache",key="#account.getName()")// 清空accountCache 缓存
public void updateAccount(Account account) {
   updateDB(account);
}
 
@CacheEvict(value="accountCache",allEntries=true)// 清空accountCache 缓存
public void reload() {
   reloadAll()
}
 
@Cacheable(value="accountCache",condition="#userName.length() <=4")// 缓存名叫 accountCache
public Account getAccountByName(String userName) {
 // 方法内部实现不考虑缓存逻辑，直接实现业务
 return getFromDB(userName);
}
```





##### @CacheConfig

```markdown
# 这个注解是声明在类上的,可以指定这个类的缓存的名字,不用每一个方法上都写一个缓存的名字,如果方法上也写的话,那么就以方法上的注解为主,
```

```java
@CacheConfig("books")
public class BookRepositoryImpl implements BookRepository {

    //这个的缓存的名字为books
    @Cacheable
    public Book findBook(ISBN isbn) {...}

    //这个的缓存的名字为desk
    @Cacheable(value="desk")
    public Book findBook(ISBN isbn) {...}
}
```



###### 常用的条件缓存

```java
//@Cacheable将在执行方法之前( #result还拿不到返回值)判断condition，如果返回true，则查缓存；
@Cacheable(value = "user", key = "#id", condition = "#id lt 10")
public User conditionFindById(final Long id)

    //@CachePut将在执行完方法后（#result就能拿到返回值了）判断condition，如果返回true，则放入缓存；
    @CachePut(value = "user", key = "#id", condition = "#result.username ne 'zhang'")
    public User conditionSave(final User user) 

    //@CachePut将在执行完方法后（#result就能拿到返回值了）判断unless，如果返回false，则放入缓存；（即跟condition相反）
    @CachePut(value = "user", key = "#user.id", unless = "#result.username eq 'zhang'")
    public User conditionSave2(final User user) 

    //@CacheEvict， beforeInvocation=false表示在方法执行之后调用（#result能拿到返回值了）；且判断condition，如果返回true，则移除缓存；
    @CacheEvict(value = "user", key = "#user.id", beforeInvocation = false, condition = "#result.username ne 'zhang'")
    public User conditionDelete(final User user)
```





##### Caching

```markdown
* 有时候我们可能要组合多个cache注解使用,比如用户新增成功后,我们需要对多个进行缓存,此时就需要@Caching组合多个注解的标签了
```

```java
@Caching(put = {
@CachePut(value = "user", key = "#user.id"),
@CachePut(value = "user", key = "#user.username"),
@CachePut(value = "user", key = "#user.email")
})
public User save(User user) {}
```

###### 自定义缓存注解

```java
@Caching(put = {
@CachePut(value = "user", key = "#user.id"),
@CachePut(value = "user", key = "#user.username"),
@CachePut(value = "user", key = "#user.email")
})
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface UserSaveCache {
}
```



在代码上使用如下的代码

```java
@UserSaveCache
public User save(User user)
```

