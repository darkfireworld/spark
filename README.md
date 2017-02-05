# spark

## 配置流程

1. 用idea打开该项目
2. 勾选maven配置为dev
3. 每个项目的继承`Runner`的类作为启动器

## 特性

1. 使用motan作为RPC中间件（依赖zk）
2. Spring 注解方式配置

## 分层说明

* core 通用配置和工具
* api-xxx xxx业务模块api
* service-xxx xxx业务模块实现
* web-xxx xxx的web功能

## RoadMap

1. 优化日志
2. 优化配置
