# aspen

## 项目简介：
  项目使用的jdk17、springboot3.1.2、dubbo3.2.10、nacos2.2.1、mybatis-flex1.7.7为基础搭建了一套简易的微服务框架
  做这个项目的初衷就是心血来潮想试试dubbo3.X和springboot3.x和jdk17融合结果，简单来看效果还不错，但是还是没有springcloud alibaba dubbo使用起来顺手和方便

  吐槽一下springcloud alibaba，新版本的springcloud alibaba丢弃dubbo真的让人难受和惋惜(ps：一位喜欢dubbo的小开发)

## 项目已集成的内容：
  1. springboot
  2. dubbo
  3. naocs
  4. mybatis-flex
  5. redis
  6. redisson
  7. spring-security
  8. jwt
  9. mysql

## 项目结构：
  #### aspen-core包：核心包，存放核心配置和工具类等
    aspen-core-cache: 缓存相关的核心包
    aspen-core-foundation：基础内容的核心包
    aspen-core-microservices：微服务相关配置等的核心包
    aspen-core-security：认证鉴权配置等
    aspen-core-service：serivce模块的相关配置，比如mysql查询等等
    aspen-core-web：http相关的配置等
  
  #### aspen-modules包：实际开发的业务模块包
    aspen-module-admin：
      aspen-module-admin-api： 管理后台的对外暴露接口等
      aspen-module-admin-web： 管理后台服务
    aspen-module-auth：
      aspen-module-auth-server： 认真服务，主要做鉴权登录相关
    aspen-module-user：
      aspen-module-user-api： 用户模块对外暴露的接口等
      aspen-module-user-server： 用户模块服务
    

## 最后：
  项目中还存在很多优化点和我没发现的问题吧，感兴趣的可以那去看看，既可以用来学习新版本的内容，也可以改巴改巴用于实际开发
