# TransactionDemo
数据库隔离级别测试demo

    此demo连接数据库为mysql，mysql的默认隔离级别为Repeatable Read（可重读）
 此隔离级别只会出现幻读，要测试不可重复读和脏读需要设置数据库的隔离级别
    查询数据库的隔离界别语句： select @@Global.tx_isolation,@@tx_isolation // 全局 当前会话
    设置数据库隔离级别语句：
            mysql> set global transaction isolation level read committed; //全局的
            mysql> set session transaction isolation level read committed; //当前会话
            SET [SESSION | GLOBAL] TRANSACTION ISOLATION LEVEL {READ UNCOMMITTED | READ COMMITTED | REPEATABLE READ | SERIALIZABLE}

幻读，不可重复读，脏读
