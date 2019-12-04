package com.zhiliao;

import com.zhiliao.dao.TestDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ZhiliaoReadApplicationTests {

	@Autowired
	private TestDao testDao;

	private int count=100;

	@Resource(name = "mysqlLock")
	private Lock lock;

	@Test
	public void testExprie(){
		RedisProperties jedis = new RedisProperties();
		jedis.setDatabase(0);
		jedis.setHost(String.valueOf(6379));
		jedis.setHost("101.132.144.61");

	}



	/**
	 * 线程安全问题
	 * @throws InterruptedException
	 */

	@Test
	public void ticketTest() throws InterruptedException {
		TicketRunnable ticketRunnable = new TicketRunnable();
		Thread t1 = new Thread(ticketRunnable,"窗口");
		Thread t2 = new Thread(ticketRunnable,"窗口");
		Thread t3 = new Thread(ticketRunnable,"窗口");
		Thread t4 = new Thread(ticketRunnable,"窗口");
		t1.start();
		t2.start();
		t3.start();
		t4.start();
		Thread.currentThread().join();
	}

	@Test
	public void contextLoads() {
		int id = testDao.getId();
		System.out.println(id+"==============");
	}

	public class TicketRunnable implements Runnable{

		@Override
		public void run() {
			while (count>0){
				lock.lock();
				try {
					if (count>0){
						System.out.println(Thread.currentThread().getName()+"售出第"+(count--)+"张票");
					}
				} catch (Exception e) {
					e.printStackTrace();
				}finally {
					lock.unlock();
				}

				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
