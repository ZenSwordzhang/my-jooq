## 说明

### 生命周期
* 新建
    * 通过Tread类、Runnable接口、线程池等创建线程后
* 就绪
    * 当线程对象调用了start()方法之后
* 运行
    * 就绪状态的线程获取CPU资源，执行run()方法，此时线程便处于运行状态
    * 运行状态的线程可以向阻塞状态、就绪状态和死亡状态转变
* 阻塞
    * 等待阻塞:运行状态中的线程执行wait()方法
    * 同步阻塞:线程在获取synchronized同步锁失败
    * 其他阻塞:通过调用线程的sleep()或join()方法发出了I/O请求时
        * 当sleep状态超时，join等待线程终止或超时，或者I/O处理完毕，线程重新转入就绪状态
* 死亡
    * 运行状态的线程完成任务或者其他终止条件发生时

### 设置线程状态，如果线程处在WAITING/TIMED_WAITING状态，则抛出InterruptedException，并清除中断状态
* public void interrupt()

### 判断当前线程是否中断
* Thread.currentThread().isInterrupted()

### 判断当前线程是否存活
* Thread.currentThread().isAlive()

### 获取当前线程状态
* Thread.currentThread().getState()

### 测试当前线程是否中断，此方法可以清除线程的中断状态，如果连续执行两次，第二次为false
* Thread.interrupted();

### 中断当前线程，如果线程处在WAITING/TIMED_WAITING状态，则抛出InterruptedException，并清除中断状态
* Thread.currentThread().interrupt();


