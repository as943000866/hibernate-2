package com.lmg.hibernate_2;

import java.util.Date;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.lmg.enties.New;

public class test {
	private SessionFactory sessionFactory;
	private Session session;
	private Transaction transaction;
	
	@Before
	public void init(){
		Configuration configuration = new Configuration().configure();
		ServiceRegistry serviceRegistry = new ServiceRegistryBuilder()
				.applySettings(configuration.getProperties())
				.buildServiceRegistry();
		sessionFactory = configuration.buildSessionFactory(serviceRegistry);
		session = sessionFactory.openSession();
		transaction = session.beginTransaction();
	}
	
	@After
	public void destory(){
//		transaction.commit();
//		session.close();
		sessionFactory.close();
	}
	
	/**
	 * update:
	 * 
	 * 
	 */
	@Test
	public void testUpdate(){
		New new1 = (New) session.get(New.class, 1);
		
		transaction.commit();
		session.close();
		
		session = sessionFactory.openSession();
		transaction = session.beginTransaction();
		
		new1.setTitle("Oracle");
		
		session.update(new1);
	}
	
	/**
	 * get VS load:
	 * 
	 * 1. 执行 get 方法: 会立即加载对象. 
	 * 	      而执行 load 方法, 若不使用该对象, 则不会立即执行查询操作, 而返回一个代理对象
	 * 
	 * 	  get 是立即检索, load 是延迟检索.
	 * 
	 * 2. load 方法可能会抛出 LazyInitializationException 异常: 在需要初始化
	 * 代理对象之前已经关闭了 Session
	 * 
	 * 2. 若数据表中没有对应的记录, Session 也没有被关闭
	 * 	  get 返回 null
	 *    load 若不使用该对象的任何属性, 没问题; 若需要初始化了, 抛出异常.
	 *  
	 */
	@Test
	public void testLoad(){
		New new1 = (New) session.load(New.class, 10);
		System.out.println(new1.getClass().getName());
		
		session.close();
		
		System.out.println(new1);
	}
	
	@Test
	public void testGet(){
		New new1 = (New) session.get(New.class, 10);
		System.out.println(new1);
	}
	
	/**
	 * persist(): 也会执行 INSERT 操作
	 * 
	 * 和 save() 的区别:
	 * 在调用 persist 方法之前, 若对象已经有 id 了, 则不会执行 INSERT, 则抛出异常
	 */
	@Test
	public void testPersist(){
		New new1 = new New();
		new1.setTitle("EE");
		new1.setAuthor("ee");
		new1.setDate(new Date());
		new1.setId(200);
		
		
		session.persist(new1);
		
	}
	
	/**
	 * 1. save() 方法
	 * 1). 使一个临时对象变为持久化对象
	 * 2). 为对象分配 ID
	 * 3). 在 flush 缓存时会发送一条 INSERT 语句
	 * 4). 在 save 方法之前的 id 是无效的
	 * 5). 持久化对象的 ID 是不能被修改的
	 */
	@Test
	public void testSave(){
		New new1 = new New();
		new1.setTitle("CC");
		new1.setAuthor("cc");
		new1.setDate(new Date());
		new1.setId(101);
		
		System.out.println(new1);
		
		session.save(new1);
		
		System.out.println(new1);
		
		new1.setId(102);
	}
	
	/**
	 * clear(): 清除缓存
	 */
	@Test
	public void testClear(){
		New new1 = (New) session.get(New.class, 1);
		
		session.clear();
		
		New new2 = (New) session.get(New.class, 1);
		
	}
	
	/**
	 * refresh(): 会强制发送 Select 语句, 以使 Session 缓存中对象的状态和数据表中对应的记录保持一致
	 */
	@Test
	public void testReFlush(){
		New new1 = (New) session.get(New.class, 1);
		System.out.println(new1);
		
		session.refresh(new1);
		System.out.println(new1);
	}
	/**
	 * flush: 使数据表中的记录和 Session 缓存中的对象状态保持一致. 为了保持一致, 则可能会发送对应的SQL 语句
	 * 1. 在 Transaction 的 commit() 方法中: 先调用 session 的 flush 方法, 再提交事务
	 * 2. flush() 方法会可能会发送 SQL 语句,但不会提交事务.
	 * 3. 注意: 在未提交事务或显示的调用 session.flush() 方法之前, 也可能会进行 flush() 操作
	 * 1). 执行 HQL 或 QBC 查询, 会先进行 flush() 操作, 以得到数据表的最新的记录
	 * 2). 若记录的 ID 是由底层数据库使用自增的方式生成的, 则在调用 save() 方法后, 就会立即发送 INSERT 语句
	 * 因为 save 方法后, 必须保证对象的 ID 是存在的!
	 */
	
	@Test
	public void testSessionFlush2(){
		New new1 = new New("Java","SUN",new Date());
		session.save(new1);
		
	}
	
	@Test
	public void testSessionFlush(){
		New new1 = (New) session.get(New.class, 1);
		new1.setTitle("Oracle");
		
//		session.flush();
//		System.out.println("flush");
		
		New new2 = (New) session.createCriteria(New.class).uniqueResult();
		System.out.println(new2);
	}
	
	@Test
	public void testSessionCache(){
		New new1 = (New) session.get(New.class, 1);
		System.out.println(new1);
		
		New new2 = (New) session.get(New.class, 1);
		System.out.println(new2);
	}
}
