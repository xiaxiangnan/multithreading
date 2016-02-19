ReentrantLock比较synchronized：
    首先他们肯定具有相同的功能和内存语义。
    1、与synchronized相比，ReentrantLock提供了更多，更加全面的功能，具备更强的扩展性。例如：时间锁等候，可中断锁等候，锁投票。
    2、ReentrantLock还提供了条件Condition，对线程的等待、唤醒操作更加详细和灵活，所以在多个条件变量和高度竞争锁的地方，ReentrantLock更加适合。
    3、ReentrantLock提供了可轮询的锁请求。它会尝试着去获取锁，如果成功则继续，否则可以等到下次运行时处理，而synchronized则一旦进入锁请求要么成功要么阻塞，所以相比synchronized而言，ReentrantLock会不容易产生死锁些。
    4、ReentrantLock支持更加灵活的同步代码块，但是使用synchronized时，只能在同一个synchronized块结构中获取和释放。注：ReentrantLock的锁释放一定要在finally中处理，否则可能会产生严重的后果。
    5、ReentrantLock支持中断处理，且性能较synchronized会好些。


Java并发提供了两种加锁模式：共享锁和独占锁。
    ReentrantLock就是独占锁。对于独占锁而言，它每次只能有一个线程持有，而共享锁则不同，它允许多个线程并行持有锁，并发访问共享资源。




