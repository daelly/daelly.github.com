package org.daelly.sandbox;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

/**
 * @author liqingyang
 * @date 2016-5-9 下午1:35:26
 * 沙盒
 */
public final class SandBox {

	private static MemoryMXBean _memoryBean = ManagementFactory.getMemoryMXBean();
	
	private static ByteArrayOutputStream _baos = new ByteArrayOutputStream(1024);
	
	private static Socket _socket = null;
	
	private static ServerSocket _serverSocket = null;
	
	private static ObjectInputStream _inputStream = null;
	
	private static ObjectOutputStream _outputStream = null;
	
	private static Thread _thread = null;
	
	private static String _classPath = null;
	
	private static long _timeStart = 0;
	
	private static long _timeUsed = 0;
	
	private static int _baseMemory = 0;
	
	private static int _memoryUsed = 0;
	
	private static String _result = null;
	
	private static String process(int runId,final int timeLimit) throws Exception{
		FutureTask<String> task = null;
		final Timer timer = null;
		
		try {
			final Method mainMethod = null;
			
			task = new FutureTask<String>(new Callable<String>() {

				@Override
				public String call() throws Exception {
					try {
						_timeStart = System.currentTimeMillis();
						timer.schedule(null, timeLimit + 1);
						mainMethod.invoke(null, new Object[] { new String[0] });
					} catch (InvocationTargetException e) {
						Throwable targetException = e.getTargetException();
						
						if(targetException instanceof OutOfMemoryError){
							
						}else if(targetException instanceof SecurityException || targetException instanceof ExceptionInInitializerError){
							
						}else if(targetException instanceof InterruptedException){
							
						}else{
							if(e.getCause().toString().contains("Output Limit Exceed"))
								;
							else
								;
						}
						throw new RuntimeException("Runtime Exception");
					}finally{
						timer.cancel();
					}
					return _baos.toString();
				}
			});
		} catch (Exception e) {
			throw new RuntimeException("Initalization Error");
		}
		_baseMemory = (int) _memoryBean.getHeapMemoryUsage().getUsed();
		_thread = new Thread(task);
		_thread.start();
		return task.get();
	}
	
	private static TimerTask getTimerTask(){
		return new TimerTask() {
			
			@Override
			public void run() {
				if(_thread != null)
					_thread.interrupt();
			}
		};
	}
	
	private static int _outputSize = 0;
	
	private static void inita(String classPath,int port)throws Exception{
		_classPath = classPath;
		_serverSocket = new ServerSocket(port);
		_socket = _serverSocket.accept();
		_outputStream = new ObjectOutputStream(_socket.getOutputStream());
		_inputStream = new ObjectInputStream(_socket.getInputStream());
		System.setOut(new PrintStream(new BufferedOutputStream(_baos){
			public void write(byte[] b,int off,int len) throws IOException{
				_outputSize += len - off;
				try {
					super.write(b,off,len);
					if(_outputSize > 999)
						throw new RuntimeException("Output Limit Exceed" + _outputSize);
				} catch (IOException e) {
					if("Output Limit Exceed".equals(e.getMessage()))
						throw e;
				}
			}
		}));
	}
	
	private static SandBoxClassLoader _classLoader = null;
	
	private static Method getMainMethod(int runId) throws Exception{
		_classLoader = new SandBoxClassLoader(_classPath);
		Class<?> targetClass = _classLoader.loaClass(_classLoader.getClassPath()+runId, "Main");
		Method mainMethod = null;
		mainMethod = targetClass.getMethod("main", String[].class);
		
		if(!Modifier.isStatic(mainMethod.getModifiers()))
			throw new RuntimeException("Method main is not static");
		mainMethod.setAccessible(true);
		return mainMethod;
	}
	
	public static void run(int runId,int timeLimit,int memoryLimit,String standardInput,String standardOutput){
		_timeUsed = 0;
		_memoryUsed = 0;
		_baos.reset();
		_outputSize = 0;
		setResult(0);
	}
	
	private static void sendResult(int runId,int timeUsed,int memoryUsed,String result) throws IOException{
		_outputStream.writeInt(runId);
		_outputStream.writeInt(timeUsed);
		_outputStream.writeInt(memoryUsed);
		_outputStream.writeUTF(result);
		
	}
	
	private static void receiveMsg() throws IOException{
		int runId = _inputStream.readInt();
		int timeLimit = _inputStream.readInt();
		int memoryLimit = _inputStream.readInt();
		String standardInput = _inputStream.readUTF();
		String standardOutput = _inputStream.readUTF();
		run(runId,timeLimit,memoryLimit,standardInput,standardOutput);
	}
	
	private static int matchOutput(byte[] standardOutput,byte[] output){
		int i = 0;
		int j = 0;
		do {
			while(i<standardOutput.length && (standardOutput[i]==' '||standardOutput[i]=='\t'||standardOutput[i]=='\r'||standardOutput[i]=='\n'))
				i++;
			while(j<output.length && (output[j]==' '||output[j]=='\t'||output[j]=='\r'||output[j]=='\n'))
				j++;
			if(i<standardOutput.length && j<output.length && standardOutput[i]!=output[j])
				return -1;
			i++;j++;
		} while (j<=i&&i<standardOutput.length&&j<output.length);
		if(i!=j)
			return -1;
		return 0;
	}
	
	private static void setResult(int resultType){
		_result = Integer.valueOf(resultType).toString();
	}
	
	private static void setResult(int resultType,String remark){
		setResult(resultType);
		
		if(remark.endsWith("StackOverflowError")){
			
		}else if(remark.endsWith("/ by zero")){
			
		}else if(remark.contains("ArrayIndexOutOfBoundsException")){
			
		}else{
			
		}
	}
	
	private static void close(){
		try {
			if(_inputStream != null)
				_inputStream.close();
			if(_outputStream != null)
				_outputStream.close();
			if(_socket != null)
				_socket.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) throws Exception{
		inita(args[0],Integer.parseInt(args[1]));
		
		SecurityManager security = System.getSecurityManager();
		if(security == null)
			System.setSecurityManager(new SandBoxSecurityManager());
		while(!_socket.isClosed())
			receiveMsg();
		
		close();
	}
}
