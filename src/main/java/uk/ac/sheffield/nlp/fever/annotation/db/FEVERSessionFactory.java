package uk.ac.sheffield.nlp.fever.annotation.db;

import org.hibernate.*;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.boot.spi.SessionFactoryOptions;
import org.hibernate.engine.spi.FilterDefinition;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.metadata.ClassMetadata;
import org.hibernate.metadata.CollectionMetadata;
import org.hibernate.stat.Statistics;

import javax.naming.NamingException;
import javax.naming.Reference;
import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceUnitUtil;
import javax.persistence.SynchronizationType;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.metamodel.EntityType;
import java.sql.Connection;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class FEVERSessionFactory implements SessionFactory {

    private static FEVERSessionFactory instance;
    public synchronized static FEVERSessionFactory getInstance() {
        if (instance == null) {
            try {
                instance = new FEVERSessionFactory();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return instance;
    }

    private SessionFactory sessionFactory;
    private FEVERSessionFactory() throws Exception {
        this.setUp();
    }


    protected void setUp() throws Exception {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure()
                .build();
        sessionFactory = new MetadataSources( registry ).buildMetadata().buildSessionFactory();
    }


    @Override
    public SessionFactoryOptions getSessionFactoryOptions() {
        return sessionFactory.getSessionFactoryOptions();
    }

    @Override
    public SessionBuilder withOptions() {
        return sessionFactory.withOptions();
    }

    @Override
    public Session openSession() throws HibernateException {
        return sessionFactory.openSession();
    }

    @Override
    public Session getCurrentSession() throws HibernateException {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public StatelessSessionBuilder withStatelessOptions() {
        return sessionFactory.withStatelessOptions();
    }

    @Override
    public StatelessSession openStatelessSession() {
        return sessionFactory.openStatelessSession();
    }

    @Override
    public StatelessSession openStatelessSession(Connection connection) {
        return sessionFactory.openStatelessSession(connection);
    }

    @Override
    public Statistics getStatistics() {
        return sessionFactory.getStatistics();
    }

    @Override
    public void close() throws HibernateException {
        sessionFactory.close();
    }

    @Override
    public boolean isClosed() {
        return sessionFactory.isClosed();
    }

    @Override
    public Cache getCache() {
        return sessionFactory.getCache();
    }

    @Override
    public Set getDefinedFilterNames() {
        return sessionFactory.getDefinedFilterNames();
    }

    @Override
    public FilterDefinition getFilterDefinition(String filterName) throws HibernateException {
        return sessionFactory.getFilterDefinition(filterName);
    }

    @Override
    public boolean containsFetchProfileDefinition(String name) {
        return sessionFactory.containsFetchProfileDefinition(name);
    }

    @Override
    public TypeHelper getTypeHelper() {
        return sessionFactory.getTypeHelper();
    }

    @Override
    public EntityManager createEntityManager() {
        return sessionFactory.createEntityManager();
    }

    @Override
    public EntityManager createEntityManager(Map map) {
        return sessionFactory.createEntityManager(map);
    }

    @Override
    public EntityManager createEntityManager(SynchronizationType synchronizationType) {
        return sessionFactory.createEntityManager(synchronizationType);
    }

    @Override
    public EntityManager createEntityManager(SynchronizationType synchronizationType, Map map) {
        return sessionFactory.createEntityManager(synchronizationType, map);
    }

    @Override
    public CriteriaBuilder getCriteriaBuilder() {
        return sessionFactory.getCriteriaBuilder();
    }



    @Override
    public boolean isOpen() {
        return sessionFactory.isOpen();
    }

    @Override
    public Map<String, Object> getProperties() {
        return sessionFactory.getProperties();
    }

    @Override
    public PersistenceUnitUtil getPersistenceUnitUtil() {
        return sessionFactory.getPersistenceUnitUtil();
    }

    @Override
    public void addNamedQuery(String name, javax.persistence.Query query) {
        sessionFactory.addNamedQuery(name, query);
    }

    @Override
    public <T> T unwrap(Class<T> cls) {
        return sessionFactory.unwrap(cls);
    }

    @Override
    public <T> void addNamedEntityGraph(String graphName, EntityGraph<T> entityGraph) {
        sessionFactory.addNamedEntityGraph(graphName, entityGraph);
    }

    @Override
    @Deprecated
    public SessionFactoryImplementor getSessionFactory() {
        return sessionFactory.getSessionFactory();
    }

    @Override
    public <T> List<EntityGraph<? super T>> findEntityGraphsByType(Class<T> entityClass) {
        return sessionFactory.findEntityGraphsByType(entityClass);
    }

    @Override
    public org.hibernate.Metamodel getMetamodel() {
        return sessionFactory.getMetamodel();
    }

    @Override
    @Deprecated
    public EntityType getEntityTypeByName(String entityName) {
        return sessionFactory.getEntityTypeByName(entityName);
    }

    @Override
    public Reference getReference() throws NamingException {
        return sessionFactory.getReference();
    }

    @Override
    @Deprecated
    public ClassMetadata getClassMetadata(Class entityClass) {
        return sessionFactory.getClassMetadata(entityClass);
    }

    @Override
    @Deprecated
    public ClassMetadata getClassMetadata(String entityName) {
        return sessionFactory.getClassMetadata(entityName);
    }

    @Override
    @Deprecated
    public CollectionMetadata getCollectionMetadata(String roleName) {
        return sessionFactory.getCollectionMetadata(roleName);
    }

    @Override
    @Deprecated
    public Map<String, ClassMetadata> getAllClassMetadata() {
        return sessionFactory.getAllClassMetadata();
    }

    @Override
    @Deprecated
    public Map getAllCollectionMetadata() {
        return sessionFactory.getAllCollectionMetadata();
    }


}
