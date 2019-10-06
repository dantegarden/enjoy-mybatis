package com.enjoy.myorm.orm.config;

import lombok.Data;

/** 一个MappedStatement对象对应一条配置文件里的sql语句
 * @description:
 * @author: lij
 * @create: 2019-10-05 22:39
 */
@Data
public class MappedStatement {
    private String namespace;
    private String sourceId;
    private String parameterType;
    private String resultType;
    private String sql;
    private Enum methodType;

    /**建造者模式**/
    public static class Builder{
        private MappedStatement ms = new MappedStatement();

        public Builder setNamespace(String namespace) {
            this.ms.setNamespace(namespace);
            return this;
        }
        public Builder setSourceId(String sourceId) {
            this.ms.setSourceId(sourceId);
            return this;
        }
        public Builder setResultType(String resultType) {
            this.ms.setResultType(resultType);
            return this;
        }
        public Builder setParameterType(String parameterType) {
            this.ms.setParameterType(parameterType);
            return this;
        }
        public Builder setSql(String sql) {
            this.ms.setSql(sql);
            return this;
        }
        public Builder setMethodType(Enum methodType) {
            this.ms.setMethodType(methodType);
            return this;
        }
        public MappedStatement build(){
            return this.ms;
        }
    }
}
