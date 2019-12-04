package com.zhiliao.lock;

import com.zhiliao.dao.TestDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
@Service
public class MysqlLock implements Lock {

    @Autowired
    private TestDao testDao;

    @Override
    public void lock() {
        while (true){
            if (tryLock()){
                return;
            }
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public void lockInterruptibly() throws InterruptedException {

    }

    /**
     * 插入数据成功加锁成功，插入数据失败，加锁失败
     * @return
     */
    @Override
    public boolean tryLock() {
        try {
            testDao.insert();
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return false;
    }

    @Override
    public void unlock() {
        testDao.delete();
    }

    @Override
    public Condition newCondition() {
        return null;
    }
}
