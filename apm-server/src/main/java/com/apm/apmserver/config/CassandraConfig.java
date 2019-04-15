//package com.apm.apmserver.config;
//
//
//import org.springframework.cassandra.core.keyspace.CreateKeyspaceSpecification;
//import org.springframework.cassandra.core.keyspace.DropKeyspaceSpecification;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.cassandra.config.SchemaAction;
//import org.springframework.data.cassandra.config.java.AbstractCassandraConfiguration;
//
//import java.util.Arrays;
//import java.util.List;
//
///**
// * Created by jt on 10/6/17.
// */
//@Configuration
//public class CassandraConfig extends AbstractCassandraConfiguration {
//
//    public static final String KEYSPACE = "apm_keyspace";
//
//    @Override
//    public SchemaAction getSchemaAction() {
//        return SchemaAction.CREATE_IF_NOT_EXISTS;
////        return SchemaAction.NONE;
//    }
//
//    @Override
//    protected List<CreateKeyspaceSpecification> getKeyspaceCreations() {
////        CreateKeyspaceSpecification specification = CreateKeyspaceSpecification.createKeyspace(KEYSPACE);
////
////        return Arrays.asList(specification);
//        return null;
//    }
//
//    @Override
//    protected List<DropKeyspaceSpecification> getKeyspaceDrops() {
//        return Arrays.asList(DropKeyspaceSpecification.dropKeyspace(KEYSPACE));
//    }
//
//    @Override
//    protected String getKeyspaceName() {
//        return KEYSPACE;
//    }
//
//    @Override
//    public String[] getEntityBasePackages() {
//        return new String[]{"com.apm.apmserver.bean.cassandra"};
//    }
//}
