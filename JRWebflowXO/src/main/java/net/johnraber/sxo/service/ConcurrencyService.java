package net.johnraber.sxo.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicReference;

import net.sf.ehcache.util.TimeUtil;


/**
 * Concurrency promises to perform certain task faster as these tasks can be
 * divided into subtasks and these subtasks can be executed in parallel. Of
 * course the runtime is limited by parts of the task which can be performed in
 * parallel. The theoretical possible performance gain can be calculated by
 * Amdahl's Law. If F is the percentage of the program which can not run
 * in parallel and N is the number of processes then the maximum performance
 * gain is 1/ (F+ ((1-F)/n)).
 * 
 * @author jraber
 * 
 */
public class ConcurrencyService
{
	private static final int NTHREDS = 10;
	
	public void doWork()
	{
		ExecutorService executor = Executors.newFixedThreadPool(NTHREDS);
		List<Future<Long>> list = new ArrayList<Future<Long>>();
	    for (int i = 0; i < 20000; i++)
	    {
	      Callable<Long> worker = new MyCallable();
	      Future<Long> submit = executor.submit(worker);
	      list.add(submit);
	    }
	    long sum = 0;
	    System.out.println(list.size());
	    
	    long timeoutVal = 500;
	    TimeUnit timeoutUnit = TimeUnit.MILLISECONDS;
	    
	    // Now retrieve the result
	    for (Future<Long> future : list)
	    {
	      try {
	        sum += future.get(timeoutVal, timeoutUnit);
	      } catch (InterruptedException e) {
	        e.printStackTrace();
	      } catch (ExecutionException e) {
	        e.printStackTrace();
	      } catch (TimeoutException e) {
			//no worries cause did not meet time SLA of 'timeoutVal'
			e.printStackTrace();
		}
	    }
	    System.out.println(sum);
	    executor.shutdown();
	  }

	class MyCallable implements Callable<Long>
	{
	  @Override
	  public Long call() throws Exception
	  {
	    long sum = 0;
	    for (long i = 0; i <= 100; i++)
	    {
	      sum += i;
	    }
	    return sum;
	  }
	} 
	
	
	/**
	 *  Java provides supports for additional atomic operations. This allows to
	 *  develop algorithm which are non-blocking algorithm, e.g. which do not
	 *  require synchronization, but are based on low-level atomic hardware
	 *  primitives such as compare-and-swap (CAS). A compare-and-swap operation
	 *  check if the variable has a certain value and if it has this value it will
	 *  perform this operation.  Non-blocking algorithm are usually much faster
	 *  then blocking algorithms as the synchronization of threads appears on
	 *  a much finer level (hardware). 
	 */
	public void doXOWorkFaster()
	{
		Object oldValue = null;
		Object newValue = null;
		AtomicReference<Object> value = new AtomicReference<Object>();
		
		
		oldValue = value.get();
		
		while( !value.compareAndSet(oldValue, newValue) )
		{
			oldValue = value.get();
		}
	}
	
	public static void main(String[] args) {
		ConcurrencyService cs = new ConcurrencyService();
		cs.doXOWorkForkJoin();
		
	}

	
	/**
	 * Implemented in Java 7 
	 */
	public void doXOWorkForkJoin()
	{
		
		Problem test = new Problem();
		 
	    // Check the number of available processors
	    int nThreads = Runtime.getRuntime().availableProcessors();
	    System.out.println(nThreads);
	    
	    Solver mfj = new Solver(test.getList());
	    
	    ForkJoinPool pool = new ForkJoinPool(nThreads);
	    
	    pool.invoke(mfj);
	    
	    long result = mfj.getResult();
	    
	    System.out.println("Done. Result: " + result);
	    long sum = 0;
	    
	    // Check if the result was ok
	    for (int i = 0; i < test.getList().length; i++)
	    {
	      sum += test.getList()[i];
	    }
	    
	    System.out.println("Done. Result: " + sum);
	}
	

	class Problem
	{
	  private final int[] list = new int[2000000];

	  public Problem()
	  {
	    Random generator = new Random(19580427);
	    for (int i = 0; i < list.length; i++)
	    {
	      list[i] = generator.nextInt(500000);
	    }
	  }

	  public int[] getList()
	  {
	    return list;
	  }
	} 
	
	
	class Solver extends RecursiveAction
	{
	  private int[] list;
	 
	  public long result;

	  public Solver(int[] array)
	  {
	    this.list = array;
	  }

	  public long getResult() {
			return result;
		}
	  
	  @Override
	  protected void compute()
	  {
	    if (list.length == 1)
	    {
	      result = list[0];
	    }
	    else
	    {
	      int midpoint = list.length / 2;
	      int[] l1 = Arrays.copyOfRange(list, 0, midpoint);
	      int[] l2 = Arrays.copyOfRange(list, midpoint, list.length);
	      
	      Solver s1 = new Solver(l1);
	      Solver s2 = new Solver(l2);
	      
	      s1.fork();
	      s2.fork();
	      
	      result = s1.result + s2.result;
	    }
      }
    }
}

